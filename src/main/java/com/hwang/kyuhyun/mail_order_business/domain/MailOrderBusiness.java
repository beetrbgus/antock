package com.hwang.kyuhyun.mail_order_business.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mail_order_business")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MailOrderBusiness {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    // 통신판매번호
    private Long mailOrderId;
    // 회사명
    private String companyName;
    // 사업자 등록 번호
    private String registrationNumber;
    // 법인 등록 번호
    private String businessRegistNo;
    // 행정 구역 번호
    private String districtCode;
}
