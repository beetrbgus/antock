package com.hwang.kyuhyun.infrastructure.api.fair_trade_commission.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 공정거래위원회 통신판매사업자 정보 DTO
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FtcMailOrderBusinessDto {
    private String ntslMthdCn; // 판매방식내용
    private String trtmntPrdlstNm; // 취급품목
    private String ntslPrdlstCn; // 취급품목설명
    private String dclrCn; // 신고내용
    private String chgCn; // 변경내용
    private String chgRsnCn; // 변경사유
    private String tcbizBgngDate; // 휴업시작일자
    private String tcbizEndDate; // 휴업종료일자
    private String clsbizDate; // 폐업일자
    private String bsnResmptDate; // 영업재개일자
    private String spcssRsnCn; // 휴폐업사유
    private String dclrDate; // 신고일자
    private String lctnRnOzip; // 도로명_우편번호
    private String rnAddr; // 도로명주소
    private String opnMdfcnDt; // 최종수정시점
    private String prcsDeptDtlNm; // 처리부서
    private String prcsDeptAreaNm; // 처리부서지역명
    private String prcsDeptNm; // 처리부서명
    private String chrgDeptTelno; // 관리부서전화번호
    private String rprsvNm; // 대표자명
    private String rprsvEmladr; // 대표자전자우편
    private String opnSn; // 개방일련번호
    private String prmmiYr; // 인허가번호_년도
    private String prmmiMnno; // 인허가관리번호
    private String ctpvNm; // 시도명
    private String dclrInstNm; // 신고기관지역명
    private String operSttusCdNm; // 운영상태코드명
    private String smtxTrgtYnCn; // 간이과세대상자여부내용
    private String corpYnNm; // 법인여부명
    private String bzmnNm; // 법인명
    private String crno; // 법인등록번호
    private String brno; // 사업자등록번호
    private String telno; // 전화번호
    private String fxno; // 팩스번호
    private String lctnRnAddr; // 소재지도로명주소
    private String lctnAddr; // 소재지주소
    private String domnCn; // 도메인명
    private String opnServerPlaceAladr; // 호스트서버주소
    private String ntslMthdNm; // 판매방식명
}