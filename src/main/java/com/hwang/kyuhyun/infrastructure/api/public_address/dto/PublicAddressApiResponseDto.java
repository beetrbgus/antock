package com.hwang.kyuhyun.infrastructure.api.public_address.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublicAddressApiResponseDto {
    private Results results;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Results {
        private PublicAddressCommon common;
        private List<PublicAddressJuso> juso;
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class PublicAddressCommon {
        private String totalCount; // 총 검색 데이터 수
        private int currentPage; // 현재 페이지 번호
        private int countPerPage; // 페이지당 출력할 결과 행 수
        private String errorCode; // 에러 코드
        private String errorMessage; // 에러 메시지
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PublicAddressJuso {
        private String roadAddr; // 전체 도로명주소
        private String roadAddrPart1; // 도로명주소(참고항목 제외)
        private String roadAddrPart2; // 도로명주소 참고항목
        private String jibunAddr; // 지번주소
        private String engAddr; // 도로명주소(영문)
        private String zipNo; // 우편번호
        private String admCd; // 행정구역코드
        private String rnMgtSn; // 도로명코드
        private String bdMgtSn; // 건물관리번호
        private String detBdNmList; // 상세건물명
        private String bdNm; // 건물명
        private String bdKdcd; // 공동주택여부 (1: 공동주택, 0: 비공동주택)
        private String siNm; // 시도명
        private String sggNm; // 시군구명
        private String emdNm; // 읍면동명
        private String liNm; // 법정리명
        private String rn; // 도로명
        private String udrtYn; // 지하여부 (0: 지상, 1: 지하)
        private Integer buldMnnm; // 건물본번
        private Integer buldSlno; // 건물부번
        private String mtYn; // 산여부 (0: 대지, 1: 산)
        private Integer lnbrMnnm; // 지번본번
        private Integer lnbrSlno; // 지번부번
        private String emdNo; // 읍면동일련번호
        private String hstryYn; // 변동이력여부 (0: 현행, 1: 변동)
        private String relJibun; // 관련지번
        private String hemdNm; // 관할주민센터
    }
}
