package com.hwang.kyuhyun.mail_order_business.service;

import com.hwang.kyuhyun.common.enums.ErrorCode;
import com.hwang.kyuhyun.common.exception.AppCustomException;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
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
import java.util.List;

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

    public String downloadCvs(String city, String district) throws IOException {
        String downloadUrl = baseUrl + fileUrl + filePrefix + "_" + city + "_" + district + fileSuffix;

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.USER_AGENT, "Mozilla/5.0");
        headers.set(HttpHeaders.REFERER, baseUrl);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Resource> response =
            restTemplate.exchange(downloadUrl, HttpMethod.GET, entity, Resource.class);

        HttpStatusCode statusCode = response.getStatusCode();

        Path downloaded = Files.createTempFile("downloaded", fileSuffix);

        if (statusCode == HttpStatus.OK && response.getBody() != null) {
            try (InputStream in = response.getBody().getInputStream()) {
                Files.copy(in, downloaded, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }
        } else {
            throw new AppCustomException(ErrorCode.EXTERNAL_SERVER_ERROR);
        }

        return downloaded.toAbsolutePath().toString();
    }

    @Override
    public List<String[]> readCvs(String path) throws IOException {
        List<String[]> cvsMailOrderBusinessList = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(path, Charset.forName("CP949")));) {
            String[] line;

            while ((line = reader.readNext()) != null) {
                System.out.println("Line: " + Arrays.toString(line));
                cvsMailOrderBusinessList.add(line);
            }
            reader.close();
            return cvsMailOrderBusinessList;
        } catch (CsvValidationException e) {
            throw new AppCustomException(ErrorCode.CVS_NOT_VALIDATED);
        }
    }

}
