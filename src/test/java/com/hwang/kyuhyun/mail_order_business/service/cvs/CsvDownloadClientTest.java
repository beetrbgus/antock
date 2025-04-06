package com.hwang.kyuhyun.mail_order_business.service.cvs;

import com.hwang.kyuhyun.infrastructure.api.csv.CsvDownloadClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CsvDownloadClientTest {
    @Autowired
    private CsvDownloadClient csvDownloadClient;

    private String city;
    private String district;

    @BeforeEach
    void setUp() {
        city = "서울특별시";
        district = "강남구";
    }

    @DisplayName("CVS 파일 읽기 성공")
    @Test
    void downloadCvs_Success() throws IOException {
        String path = csvDownloadClient.downloadCvs(city, district);

        assertNotNull(path);
        System.out.println(path);
    }
}
