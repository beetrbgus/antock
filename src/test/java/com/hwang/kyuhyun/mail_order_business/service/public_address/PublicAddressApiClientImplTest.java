package com.hwang.kyuhyun.mail_order_business.service.public_address;

import com.hwang.kyuhyun.infrastructure.api.public_address.PublicAddressApiClientImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PublicAddressApiClientImplTest {

    @Autowired
    private PublicAddressApiClientImpl publicAddressApiService;

    @DisplayName("주소로 행정구역 코드 찾기")
    @Test
    public void findAddressCode() {
        // 서울특별시 강남구 삼성동 170 번지9 호 산 1
        String address = "서울특별시 강남구 압구정로36길";
        String code = "1168010700";
        String districtCode = publicAddressApiService.findDistrictCodeByAddress(address);

        assertNotNull(districtCode);
        assertEquals(code, districtCode, "행정구역 코드가 일치해야 합니다.");
    }

}