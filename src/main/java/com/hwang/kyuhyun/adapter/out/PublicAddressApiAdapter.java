package com.hwang.kyuhyun.adapter.out;

import com.hwang.kyuhyun.domain.port.out.PublicAddressPort;
import com.hwang.kyuhyun.infrastructure.api.public_address.PublicAddressApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PublicAddressApiAdapter implements PublicAddressPort {
    private final PublicAddressApiClient publicAddressApiClient;

    @Override
    public String findDistrictCodeByAddress(String address) {
        return publicAddressApiClient.findDistrictCodeByAddress(address);
    }
}
