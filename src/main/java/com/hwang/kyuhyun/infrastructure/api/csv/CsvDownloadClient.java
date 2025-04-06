package com.hwang.kyuhyun.infrastructure.api.csv;

import com.hwang.kyuhyun.common.enums.ErrorCode;
import com.hwang.kyuhyun.common.exception.AppCustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Component
@RequiredArgsConstructor
public class CsvDownloadClient {
    @Value("${download.base-url}")
    private String baseUrl;

    @Value("${download.file-url}")
    private String fileUrl;

    @Value("${download.file-prefix}")
    private String filePrefix;
    @Value("${download.file-subfix}")
    private String fileSuffix;

    private final RestTemplate restTemplate;

    /**
     * 외부 url을 접속했을 때 CSV 파일을 반환 함.
     * @param city 시/도
     * @param district 군/구
     * @return 저장한 파일 경로
     * @throws IOException
     */
    public String downloadCvs(String city, String district) throws IOException {
        String downloadUrl = UriComponentsBuilder
                .fromHttpUrl(baseUrl + fileUrl)
                .pathSegment(filePrefix + "_" + city + "_" + district + fileSuffix)
                .toUriString();

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
        try (InputStream inputStream = response.getBody().getInputStream()) {
            Files.copy(inputStream, downloaded, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        }

        log.info("CSV 다운로드를 성공적으로 마쳤습니다. 저장된 경로: {}", downloaded.toAbsolutePath());

        return downloaded.toAbsolutePath().toString();
    }
}

