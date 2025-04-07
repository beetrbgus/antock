package com.hwang.kyuhyun.adapter.out.persistence;

import com.hwang.kyuhyun.domain.MailOrderBusinessVO;
import com.hwang.kyuhyun.domain.model.MailOrderBusiness;
import org.springframework.stereotype.Component;

@Component
public class MailOrderBusinessEntityMapper {
    public MailOrderBusiness toEntity(MailOrderBusinessVO vo) {
        return MailOrderBusiness.builder()
                .id(vo.getId())
                .mailOrderId(vo.getMailOrderId())
                .companyName(vo.getBusinessName()) // VO: businessName → Entity: companyName
                .businessRegistrationNumber(vo.getRegistrationNumber())
                .corporationRegistrationNumber(vo.getCompanyRegistrationNo()) // VO: companyRegistrationNo → Entity: businessRegistNo
                .districtCode(vo.getDistrictCode())
                .build();
    }
}
