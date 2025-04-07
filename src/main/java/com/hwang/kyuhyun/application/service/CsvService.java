package com.hwang.kyuhyun.application.service;

import com.hwang.kyuhyun.adapter.in.dto.CvsMailOrderBusinessDto;
import com.hwang.kyuhyun.common.enums.ErrorCode;
import com.hwang.kyuhyun.common.exception.AppCustomException;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CsvService {
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

        List<CvsMailOrderBusinessDto> resultList = rawLines.parallelStream()
                .map(line -> processConvertLineToDto(line, headerLineLength))
                .filter(dtoOptional -> dtoOptional.isPresent())
                .map(dtoOptional -> dtoOptional.get())
                .toList();

        log.info("CSV 파일 읽기가 성공했습니다. 총 유효 행: {}", resultList.size());
        return resultList;
    }

    private Optional<CvsMailOrderBusinessDto> processConvertLineToDto(String[] line, int headerLineLength) {
        int lineLength = line.length;

        try {
            if(headerLineLength == lineLength) {
                return Optional.of(new CvsMailOrderBusinessDto(line));
            } else if (headerLineLength < lineLength) {
                log.warn("CSV 유효하지 않은 행을 발견했습니다. 컬럼 병합 시도. 행: {}", Arrays.toString(line));

                String merged = line[lineLength - 3] + " " + line[lineLength - 2];
                String[] mergedLine = new String[headerLineLength];

                System.arraycopy(line, 0, mergedLine, 0, headerLineLength - 1);
                mergedLine[headerLineLength - 1] = merged;

                return Optional.of(new CvsMailOrderBusinessDto(mergedLine));

            } else if(headerLineLength > lineLength && lineLength >= 15) {
                String[] paddedLine = new String[headerLineLength];
                System.arraycopy(line, 0, paddedLine, 0, lineLength);

                return Optional.of(new CvsMailOrderBusinessDto(paddedLine));
            } else {
                log.warn("CSV 유효하지 않은 행을 건너뜁니다. 행: {}", Arrays.toString(line));
                return Optional.empty();
            }
        } catch (Exception e) {
            log.error("CSV 라인 파싱 실패. 행: {}, 에러: {}", Arrays.toString(line), e.getMessage());
            return Optional.empty();
        }
    }
}
