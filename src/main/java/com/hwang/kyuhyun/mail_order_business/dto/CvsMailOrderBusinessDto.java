package com.hwang.kyuhyun.mail_order_business.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CvsMailOrderBusinessDto {
    // 통신판매번호
    private String mailOrderNumber;
    // 신고기관명
    private String reportAgencyName;
    // 상호
    private String businessName;
    // 사업자등록번호
    private String businessRegistrationNo;
    // 법인여부
    private String isCorporation;
    // 대표자명
    private String ceoName;
    // 전화번호
    private String phoneNumber;
    // 전자우편
    private String email;
    // 신고일자
    private String reportDate;
    // 사업장소재지
    private String location;
    // 사업장소재지(도로명)
    private String roadLocation;
    // 업소상태
    private String businessStatus;
    // 신고기관 대표연락처
    private String reportAgencyContact;
    // 판매방식
    private String salesMethod;
    // 취급품목
    private String itemsHandled;
    // 인터넷도메인
    private String domain;
    // 호스트서버소재지
    private String hostServerLocation;

    public CvsMailOrderBusinessDto(String[] infos) {
        this.mailOrderNumber = infos[0];
        this.reportAgencyName = infos[1];
        this.businessName = infos[2];
        this.businessRegistrationNo = infos[3].replaceAll("-", "");
        this.isCorporation = infos[4];
        this.ceoName = infos[5];
        this.phoneNumber = infos[6];
        this.email = infos[7];
        this.reportDate = infos[8];
        this.location = infos[9];
        this.roadLocation = infos[10];
        this.businessStatus = infos[11];
        this.reportAgencyContact = infos[12];
        this.salesMethod = infos[13];
        this.itemsHandled = infos[14];
        this.domain = infos[15];
        this.hostServerLocation = infos[16];
    }
}
