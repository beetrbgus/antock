package com.hwang.kyuhyun.mail_order_business.service;

import com.hwang.kyuhyun.common.enums.ErrorCode;
import com.hwang.kyuhyun.common.exception.AppCustomException;
import com.hwang.kyuhyun.mail_order_business.dto.CvsMailOrderBusinessDto;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class CvsServiceImpl implements CvsService {
    @Value("${download.base-url}")
    private String baseUrl;

    @Value("${download.file-url}")
    private String fileUrl;

    @Value("${download.file-prefix}")
    private String filePrefix;
    @Value("${download.file-subfix}")
    private String fileSuffix;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * 외부 url을 접속했을 때 CSV 파일을 반환 함.
     * @param city 시/도
     * @param district 군/구
     * @return 저장한 파일 경로
     * @throws IOException
     */
    public String downloadCvs(String city, String district) throws IOException {
        String downloadUrl = baseUrl + fileUrl + filePrefix + "_" + city + "_" + district + fileSuffix;
        log.info("CSV 다운로드 URL: {}", downloadUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.USER_AGENT, "Mozilla/5.0");
        headers.set(HttpHeaders.REFERER, baseUrl);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Resource> response;

        try {
            response = restTemplate.exchange(downloadUrl, HttpMethod.GET, entity, Resource.class);
        } catch (Exception e) {
            log.error("CSV 파일 다운로드를 실패했습니다. URL: {}, Error: {}", downloadUrl, e.getMessage(), e);
            throw new AppCustomException(ErrorCode.EXTERNAL_SERVER_ERROR);
        }

        HttpStatusCode statusCode = response.getStatusCode();
        if (statusCode != HttpStatus.OK || response.getBody() == null) {
            log.warn("CSV 파일 다운로드를 실패했습니다. 상태코드 : {}", statusCode);
            throw new AppCustomException(ErrorCode.EXTERNAL_SERVER_ERROR);
        }

        Path downloaded = Files.createTempFile("downloaded", fileSuffix);
        try (InputStream in = response.getBody().getInputStream()) {
            Files.copy(in, downloaded, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        }

        log.info("CSV 다운로드를 성공적으로 마쳤습니다. 저장된 경로: {}", downloaded.toAbsolutePath());

        return downloaded.toAbsolutePath().toString();
    }

    /**
     * CVS 파일을 읽고 문자열 배열 List를 반환함.
     * @param path 저장된 파일 경로
     * @return CSV 파일을 문자열 배열 List로 저장한 값
     * @throws IOException
     */
    @Override
    public List<CvsMailOrderBusinessDto> readCvs(String path) throws IOException {
        log.info("CSV 파일을 읽습니다. 경로: {}", path);

        List<String[]> rawLines = new ArrayList<>();

        try(CSVReader reader = new CSVReader(new FileReader(path, Charset.forName("CP949")))) {
            String[] line;
            while((line = reader.readNext()) != null) {
                rawLines.add(line);
            }
        } catch (CsvValidationException e) {
            log.error("CsvValidationException 예외발생. 경로: {}", path, e);
            throw new AppCustomException(ErrorCode.CVS_NOT_VALIDATED);
        }

        if(rawLines.isEmpty()) {
            log.warn("CSV 파일이 비어있습니다.");
            return Collections.emptyList();
        }
        String[] header = rawLines.remove(0);
        int headerLineLength = header.length;

        log.debug("첫 행은 표시될 항목들 입니다.");
        log.debug(Arrays.toString(header));

        List<CvsMailOrderBusinessDto> resultList = rawLines.parallelStream()
                .map(line -> processConvertLineToDto(line, headerLineLength))
                .filter(dto -> dto != null)
                .toList();

        log.info("CSV 파일 읽기가 성공했습니다. 총 유효 행: {}", resultList.size());
        return resultList;
    }

    private CvsMailOrderBusinessDto processConvertLineToDto(String[] line, int headerLineLength) {
        int lineLength = line.length;

        try {
            if(headerLineLength == lineLength) {
                return new CvsMailOrderBusinessDto(line);
            } else if (headerLineLength < lineLength) {
                log.warn("CSV 유효하지 않은 행을 발견했습니다. 컬럼 병합 시도. 행: {}", Arrays.toString(line));

                String merged = line[lineLength - 3] + " " + line[lineLength - 2];
                String[] mergedLine = new String[headerLineLength];

                System.arraycopy(line, 0, mergedLine, 0, headerLineLength - 1);
                mergedLine[headerLineLength - 1] = merged;

                return new CvsMailOrderBusinessDto(mergedLine);

            } else if(headerLineLength > lineLength && lineLength >= 15) {
                String[] paddedLine = new String[headerLineLength];
                System.arraycopy(line, 0, paddedLine, 0, lineLength);

                return new CvsMailOrderBusinessDto(paddedLine);
            } else {
                log.warn("CSV 유효하지 않은 행을 건너뜁니다. 행: {}", Arrays.toString(line));
                return null;
            }
        } catch (Exception e) {
            log.error("CSV 라인 파싱 실패. 행: {}, 에러: {}", Arrays.toString(line), e.getMessage());
            return null;
        }
    }
}
