package com.hwang.kyuhyun.mail_order_business.service.cvs;


import com.hwang.kyuhyun.adapter.in.dto.CvsMailOrderBusinessDto;
import com.hwang.kyuhyun.application.service.CsvService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CsvServiceTest {

    @Autowired
    private CsvService csvService;


    @DisplayName("CSV 정상 파싱 테스트")
    @Test
    void readCvs_success() throws Exception {
        // given
        String userHome = System.getProperty("user.home");
        String path = userHome + "/Downloads/통신판매사업자_서울특별시_강남구.csv";

        List<CvsMailOrderBusinessDto> result = csvService.readCvs(path);

        assertEquals(18983, result.size());
        assertEquals("마인드풀", result.get(0).getBusinessName());
        assertEquals("크리스천 온리유", result.get(1).getBusinessName());
    }
}
