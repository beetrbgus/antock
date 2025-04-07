package com.hwang.kyuhyun.mail_order_business.service;

import com.hwang.kyuhyun.adapter.in.dto.CreateMailOrderBusinessDto;
import com.hwang.kyuhyun.application.port.in.RegisterMailOrderBusinessUseCaseImpl;
import com.hwang.kyuhyun.domain.model.MailOrderBusiness;
import com.hwang.kyuhyun.domain.port.out.MailOrderBusinessRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CreateMailOrderBusinessUseCaseImplTest {
    @Autowired
    private RegisterMailOrderBusinessUseCaseImpl mailOrderService;

    @Autowired
    private MailOrderBusinessRepository mailOrderBusinessRepository;

    private String city;
    private String district;

    @BeforeEach
    void initialize() {
        city = "서울특별시";
        district = "강남구";
    }

    @Test
    void createMailOrderBusinessTest() throws IOException {
        CreateMailOrderBusinessDto createMailOrderBusinessDto = new CreateMailOrderBusinessDto(city, district);
        mailOrderService.createMailOrderBusiness(createMailOrderBusinessDto);
        List<MailOrderBusiness> mailOrderBusinessAll = mailOrderBusinessRepository.findAll();

        assertFalse(mailOrderBusinessAll.isEmpty(), "저장된 값이 빈 값일 수 없음.");
    }
}
