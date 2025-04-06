package com.hwang.kyuhyun.mail_order_business.service.ftc;

import com.hwang.kyuhyun.infrastructure.api.fair_trade_commission.dto.FtcMailOrderBusinessDto;
import com.hwang.kyuhyun.infrastructure.api.fair_trade_commission.FtcMailOrderApiClientImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class FtcMailOrderApiClientTest {
    @Autowired
    private FtcMailOrderApiClientImpl ftcApiService;

    @DisplayName("공정 거래 위원회 사업등록번호 가져오기")
    @Test
    void getBusinessInfo() {
        // 2025-서울강남-01934	서울특별시 강남구	주식회사 노스텔지아코퍼레이션(Nostalgia Corporation Inc.)	229-87-03477	법인	강승현	02-2158-5403	hr@plac-official.com	20250404	서울특별시 강남구 신사동 620-2	서울특별시 강남구 압구정로36길 18^ 6층 (신사동)	정상영업	02-3423-5382	인터넷	의류/패션/잡화/뷰티	네이버	null
        String brno = "2298703477";

        Optional<FtcMailOrderBusinessDto> mllBsInfoDetailByBrno = ftcApiService.findMailOrderBusinessDetailByBrno(brno);
        assertTrue(mllBsInfoDetailByBrno.isPresent(), "응답이 존재해야 함.");

        FtcMailOrderBusinessDto ftcMailOrderBusinessDto = mllBsInfoDetailByBrno.get();

        assertEquals(brno, ftcMailOrderBusinessDto.getBrno(), "사업자번호가 일치해야 함.");
        assertNotNull(ftcMailOrderBusinessDto.getCrno(), "법인 등록번호 있어야 함.");
        assertNotNull(ftcMailOrderBusinessDto.getBzmnNm(), "사업자명이 있어야 합니다.");
        assertNotNull(ftcMailOrderBusinessDto.getLctnAddr(), "주소가 있어야 합니다.");
    }
}
