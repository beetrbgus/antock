package com.hwang.kyuhyun.mail_order_business.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MailOrderBusinessVO {
    private Long id;
    // 통신판매번호
    private String mailOrderId;
    // 회사명
    private String businessName;
    // 사업자 등록 번호
    private String registrationNumber;
    // 법인 등록 번호
    private String companyRegistrationNo;
    // 행정 구역 번호
    private String districtCode;

    public void setCompanyRegistrationNo(String companyRegistrationNo) {}
}
