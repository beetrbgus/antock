package com.hwang.kyuhyun.mail_order_business.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CompanyType {
    CORPORATE("법인"),
    INDIVIDUAL("개인"),
    ;
    private final String value;
}
