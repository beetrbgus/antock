package com.hwang.kyuhyun.infrastructure.api.public_address;

public interface PublicAddressApiClient {
    String findDistrictCodeByAddress(String address);
}
