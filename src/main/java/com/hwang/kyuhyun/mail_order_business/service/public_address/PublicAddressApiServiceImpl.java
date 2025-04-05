package com.hwang.kyuhyun.mail_order_business.service.public_address;

import com.hwang.kyuhyun.mail_order_business.dto.PublicAddressApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class PublicAddressApiServiceImpl implements PublicAddressApiService {
    @Value("${public-address.authentication-key}")
    private String confirmKey;

    @Value("${public-address.base-url}")
    private String baseUrl;

    @Value("${public-address.administrative-district-code-endpoint}")
    private String districtCodeEndpoint;


    /**
     * 주소로 행정구역코드 찾기 단건 API.
     * @param address 주소
     * @return 행정구역코드
     */
    @Override
    public String findDistrictCodeByAddress(String address) {
        RestTemplate restTemplate = new RestTemplate();
        String encodedConfirmKey = URLEncoder.encode(confirmKey, StandardCharsets.UTF_8);
        String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);

        URI uri = UriComponentsBuilder
                .fromUriString(baseUrl + districtCodeEndpoint)
                .queryParam("confmKey", encodedConfirmKey)
                .queryParam("currentPage", 1)
                .queryParam("countPerPage", 10)
                .queryParam("keyword", encodedAddress)
                .queryParam("resultType", "json")
                .encode()
                .build(true)
                .toUri();

        log.info("[주소기반 서비스] API URI: {}", uri);

        try {
            ResponseEntity<PublicAddressApiResponse> response =
                    restTemplate.getForEntity(uri, PublicAddressApiResponse.class);

            log.info("[주소기반 서비스] 응답 코드: {}", response.getStatusCode());

            PublicAddressApiResponse body = response.getBody();
            log.debug("[주소기반 서비스] 응답 바디: {}", body); // DEBUG 레벨로 설정

            if (body == null || body.getResults() == null || body.getResults().getJuso() == null || body.getResults().getJuso().isEmpty()) {
                log.warn("[주소기반 서비스] 행정구역 코드 조회 실패: 결과 없음 (address: {})", address);
                return null;
            }

            String admCd = body.getResults().getJuso().get(0).getAdmCd();
            log.info("[주소기반 서비스] 행정구역 코드: {} 주소: {}", admCd, address);
            return admCd;
        } catch (Exception e) {
            log.error("[주소기반 서비스] 조회 중 예외 발생 주소 : {}", address, e);
            return null;
        }
    }
}
