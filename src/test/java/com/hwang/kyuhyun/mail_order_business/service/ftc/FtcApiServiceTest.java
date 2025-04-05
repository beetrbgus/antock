package com.hwang.kyuhyun.mail_order_business.service.ftc;

import com.hwang.kyuhyun.mail_order_business.dto.FtcMailOrderBusinessDto;
import com.hwang.kyuhyun.mail_order_business.service.federal_trade_commission.FtcMailOrderApiServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class FtcApiServiceTest {
    @Autowired
    private FtcMailOrderApiServiceImpl ftcApiService;

    @DisplayName("공정 거래 위원회 사업등록번호 가져오기")
    @Test
    void getBusinessInfo() {
        // [2017-서울강남-03850, 서울특별시 강남구, 주식회사 놀러와, 871-81-00930, 법인, 한수정, '02-353-5504, crystal411@naver.com, 20170925, 서울특별시 강남구 삼성동 170번지 9호  , 서울특별시 강남구 테헤란로 625^ 17층 1717호 (삼성동), 정상영업, 02-3423-5382, 인터넷, 종합몰, 30일이내 보완, null]
        String brno = "8718100930";

        Optional<FtcMailOrderBusinessDto> mllBsInfoDetailByBrno = ftcApiService.findMailOrderBusinessDetailByBrno(brno);
        assertTrue(mllBsInfoDetailByBrno.isPresent(), "응답이 존재해야 함.");

        FtcMailOrderBusinessDto ftcMailOrderBusinessDto = mllBsInfoDetailByBrno.get();

        assertEquals(brno, ftcMailOrderBusinessDto.getBrno(), "사업자번호가 일치해야 함.");
        assertNotNull(ftcMailOrderBusinessDto.getCrno(), "법인 등록번호 있어야 함.");
        assertNotNull(ftcMailOrderBusinessDto.getBzmnNm(), "사업자명이 있어야 합니다.");
        assertNotNull(ftcMailOrderBusinessDto.getLctnAddr(), "주소가 있어야 합니다.");
    }
}
