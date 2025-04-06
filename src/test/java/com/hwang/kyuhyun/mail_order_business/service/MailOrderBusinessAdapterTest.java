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
        MailOrderBusiness daechiTopEdu = MailOrderBusiness.builder()
                .mailOrderId("2025-서울강남-01937")
                .companyName("서울대치탑에듀")
                .registrationNumber("7021402305")
                .businessRegistNo("")
                .districtCode("")
                .build();

        MailOrderBusiness hyunsMade = MailOrderBusiness.builder()
                .mailOrderId("2025-서울강남-01938")
                .companyName("현스메이드")
                .registrationNumber("3450802926")
                .businessRegistNo("")
                .districtCode("")
                .build();
        List<MailOrderBusiness> mailOrderBusinessList = List.of(daechiTopEdu, hyunsMade);

        mailOrderBusinessAdapter.saveAll(mailOrderBusinessList);

        // 메서드 호출 여부
        verify(mailOrderBusinessRepository, times(1)).saveAll(mailOrderBusinessList);
        verify(entityManager, times(1)).flush();
        verify(entityManager, times(1)).clear();
    }
}
