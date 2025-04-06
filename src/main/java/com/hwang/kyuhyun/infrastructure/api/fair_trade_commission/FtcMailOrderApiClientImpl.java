package com.hwang.kyuhyun.infrastructure.api.fair_trade_commission;

import com.hwang.kyuhyun.infrastructure.api.fair_trade_commission.dto.FtcMailOrderBusinessDto;
import com.hwang.kyuhyun.infrastructure.api.fair_trade_commission.dto.FtcMailOrderBusinessResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Slf4j
@Service
public class FtcMailOrderApiClientImpl implements FtcMailOrderApiClient {
    @Value("${open-data-portal.authentication-key}")
    private String authenticationKey;

    @Value("${open-data-portal.base-url}")
    private String baseUrl;

    @Value("${open-data-portal.end-point}")
    private String endPoint;


    @Override
    public Optional<FtcMailOrderBusinessDto> findMailOrderBusinessDetailByBrno(String brno) {
        String apiEndPoint = baseUrl + endPoint;

        RestTemplate restTemplate = new RestTemplate();

        URI uri = UriComponentsBuilder
                .fromUriString(apiEndPoint)
                .queryParam("serviceKey", authenticationKey)
                .queryParam("pageNo", 1)
                .queryParam("numOfRows", 20)
                .queryParam("brno", brno)
                .queryParam("resultType", "json")
                .build(true)
                .toUri();

        log.info("공정거래 위원회 API URI: {}", uri);

        ResponseEntity<FtcMailOrderBusinessResponse> response =
                restTemplate.getForEntity(uri, FtcMailOrderBusinessResponse.class);

        FtcMailOrderBusinessResponse body = response.getBody();

        if (body == null || body.getItems() == null || body.getItems().isEmpty()) {
            log.warn("사업자등록번호로 공정거래위원회 조회 시 반환 데이터가 없음 사업자등록번호: {}", brno);
            return Optional.empty();
        }

        return Optional.ofNullable(body.getItems().get(0));
    }
}
