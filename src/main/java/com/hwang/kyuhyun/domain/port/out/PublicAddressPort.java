package com.hwang.kyuhyun.domain.port.out;

public interface PublicAddressPort {
    String findDistrictCodeByAddress(String address);
}