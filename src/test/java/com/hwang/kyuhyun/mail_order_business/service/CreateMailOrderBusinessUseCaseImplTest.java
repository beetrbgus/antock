package com.hwang.kyuhyun.mail_order_business.service;

import com.hwang.kyuhyun.application.port.in.RegisterMailOrderBusinessUseCaseImpl;
import com.hwang.kyuhyun.domain.model.MailOrderBusiness;
import com.hwang.kyuhyun.adapter.in.dto.CvsMailOrderBusinessDto;
import com.hwang.kyuhyun.infrastructure.api.fair_trade_commission.dto.FtcMailOrderBusinessDto;
import com.hwang.kyuhyun.adapter.in.dto.CreateMailOrderBusinessDto;
import com.hwang.kyuhyun.domain.port.out.MailOrderBusinessRepository;
import com.hwang.kyuhyun.infrastructure.api.fair_trade_commission.FtcMailOrderApiClient;
import com.hwang.kyuhyun.infrastructure.api.public_address.PublicAddressApiClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateMailOrderBusinessUseCaseImplTest {
    @Mock
    private FtcMailOrderApiClient ftcMailOrderApiClient;

    @Mock
    private PublicAddressApiClient publicAddressApiClient;

    @Mock
    private MailOrderBusinessRepository mailOrderBusinessRepository;

    @InjectMocks
    private RegisterMailOrderBusinessUseCaseImpl mailOrderService;

    @Test
    void enrichAndConvertToEntity_성공_케이스() throws IOException {
        // given
        // 2021-서울강남-06669,서울특별시 강남구,주식회사 라이트하우스 엔터,819-81-02338,법인,유주영,02 - 3443 - 1955,lighthouse2020@daum.net,20211129,서울특별시 강남구 논현동 97-7  ,서울특별시 강남구 언주로148길 19^ 청호빌딩 3층 (논현동),정상영업,02-3423-5382,TV홈쇼핑 인터넷 카달로그 신문잡지,종합몰 교육/도서/완구/오락 가전 컴퓨터/사무용품 가구/수납용품 건강/식품 의류/패션/잡화/뷰티 레저/여행/공연 자동차/자동차용품 상품권,https://smartstore.naver.com/lighthouse-ent,null
        CvsMailOrderBusinessDto dto = CvsMailOrderBusinessDto.builder()
                .mailOrderNumber("2021-서울강남-06669")
                .businessName("주식회사 라이트하우스 엔터")
                .businessRegistrationNo("8198102338")
                .roadLocation("서울특별시 강남구 논현동 97-7")
                .build();

        FtcMailOrderBusinessDto ftcDto = FtcMailOrderBusinessDto.builder()
                .crno("CORP-123456")
                .build();

        given(ftcMailOrderApiClient.findMailOrderBusinessDetailByBrno(dto.getBusinessRegistrationNo()))
                .willReturn(Optional.of(ftcDto));
        given(publicAddressApiClient.findDistrictCodeByAddress(dto.getLocation()))
                .willReturn("11680");

        CreateMailOrderBusinessDto createMailOrderBusinessDto = new CreateMailOrderBusinessDto("서울특별시", "강남구");
        // when
        mailOrderService.createMailOrderBusiness(createMailOrderBusinessDto);

        // then
        then(mailOrderBusinessRepository).should().save(any(MailOrderBusiness.class));

    }

//    @Test
//    void enrichAndConvertToEntity_실패_케이스_API응답없음() {
//        // given
//        CvsMailOrderBusinessDto dto = CvsMailOrderBusinessDto.builder()
//                .businessName("테스트상호")
//                .businessRegistrationNo("9999999999")
//                .roadLocation("서울 강남구 강남대로 2")
//                .mailOrderNumber("2025-서울-9999")
//                .build();
//
//        given(ftcMailOrderApiService.findMailOrderBusinessDetailByBrno("9999999999"))
//                .willReturn(Optional.empty());
//
//        // when
//        Optional<MailOrderBusiness> result = mailOrderService.enrichAndConvertToEntity(dto);
//
//        // then
//        assertThat(result).isEmpty();
//    }
//
//    @Test
//    void enrichAndConvertToEntity_실패_케이스_주소변환_예외() {
//        // given
//        CvsMailOrderBusinessDto dto = CvsMailOrderBusinessDto.builder()
//                .businessName("테스트상호")
//                .businessRegistrationNo("1234567890")
//                .roadLocation("서울 강남구 도로 없음")
//                .mailOrderNumber("2025-서울-0002")
//                .build();
//
//        FtcMailOrderBusinessDto ftcDto = FtcMailOrderBusinessDto.builder()
//                .crno("CORP-987654")
//                .build();
//
//        given(ftcMailOrderApiService.findMailOrderBusinessDetailByBrno("1234567890"))
//                .willReturn(Optional.of(ftcDto));
//        given(publicAddressApiService.findDistrictCodeByAddress(any()))
//                .willThrow(new RuntimeException("주소 매핑 실패"));
//
//        // when
//        Optional<MailOrderBusiness> result = mailOrderService.enrichAndConvertToEntity(dto);
//
//        // then
//        assertThat(result).isEmpty();
//    }
}
