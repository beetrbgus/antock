package com.hwang.kyuhyun.mail_order_business.service.federal_trade_commission;

import com.hwang.kyuhyun.mail_order_business.dto.FtcMailOrderBusinessDto;

import java.util.Optional;

public interface FtcMailOrderApiService {
    Optional<FtcMailOrderBusinessDto> getMllBsInfoDetailByBrno(String brno);
}