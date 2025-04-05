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
    private String mailOrderNumber; // 통신판매번호
    private String reportAgencyName; // 신고기관명
    private String businessName; // 상호
    private String businessRegistrationNo; // 사업자등록번호
    private String isCorporation; // 법인여부
    private String ceoName; // 대표자명
    private String phoneNumber; // 전화번호
    private String email; // 전자우편
    private String reportDate; // 신고일자
    private String location; // 사업장소재지
    private String roadLocation; // 사업장소재지(도로명)
    private String businessStatus; // 업소상태
    private String reportAgencyContact; // 신고기관 대표연락처
    private String salesMethod; // 판매방식
    private String itemsHandled; // 취급품목
    private String domain; // 인터넷도메인
    private String hostServerLocation; // 호스트서버소재지

    public CvsMailOrderBusinessDto(String[] line) {
        this.mailOrderNumber = line[0];
        this.reportAgencyName = line[1];
        this.businessName = line[2];
        this.businessRegistrationNo = line[3].replaceAll("-", "");
        this.isCorporation = line[4];
        this.ceoName = line[5];
        this.phoneNumber = line[6];
        this.email = line[7];
        this.reportDate = line[8];
        this.location = line[9];
        this.roadLocation = line[10].replaceAll(" ", "");
        this.businessStatus = line[11];
        this.reportAgencyContact = line[12];
        this.salesMethod = line[13];
        this.itemsHandled = line[14];
        this.domain = line[15];
        this.hostServerLocation = line[16];
    }
}