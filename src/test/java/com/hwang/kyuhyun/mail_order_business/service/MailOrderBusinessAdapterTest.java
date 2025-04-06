package com.hwang.kyuhyun.mail_order_business.service;

import static org.mockito.Mockito.*;

import com.hwang.kyuhyun.adapter.out.MailOrderBusinessAdapter;
import com.hwang.kyuhyun.domain.model.MailOrderBusiness;
import com.hwang.kyuhyun.domain.port.out.MailOrderBusinessRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class MailOrderBusinessAdapterTest {

    @Mock
    private MailOrderBusinessRepository mailOrderBusinessRepository;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private MailOrderBusinessAdapter mailOrderBusinessAdapter;

    @DisplayName("통신 판매업자 batch 등록")
    @Test
    void saveAllMailOrderBusinessSaveBatch() {
        // 준비 데이터
        MailOrderBusiness mailOrderBusiness = MailOrderBusiness.builder()
                .mailOrderId("2025-서울강남-01934")
                .companyName("주식회사노스텔지아코퍼레이션(NostalgiaCorporationInc.)")
                .businessRegistrationNumber("2298703477")
                .corporationRegistrationNumber("1101110921876")
                .districtCode("")
                .build();

        List<MailOrderBusiness> mailOrderBusinessList = List.of(mailOrderBusiness);

        mailOrderBusinessAdapter.saveAll(mailOrderBusinessList);

        // 메서드 호출 여부
        verify(mailOrderBusinessRepository, times(1)).saveAll(mailOrderBusinessList);
        verify(entityManager, times(1)).flush();
        verify(entityManager, times(1)).clear();
    }
}
