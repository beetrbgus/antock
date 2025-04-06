package com.hwang.kyuhyun.infrastructure.api.fair_trade_commission.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FtcMailOrderBusinessResponse {
    private String resultCode;
    private String resultMsg;
    private int numOfRows;
    private int pageNo;
    private int totalCount;
    private List<FtcMailOrderBusinessDto> items;
}
