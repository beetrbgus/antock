package com.hwang.kyuhyun.infrastructure.api.fair_trade_commission;

import com.hwang.kyuhyun.infrastructure.api.fair_trade_commission.dto.FtcMailOrderBusinessDto;
import java.util.Optional;

public interface FtcMailOrderApiClient {
    Optional<FtcMailOrderBusinessDto> findMailOrderBusinessDetailByBrno(String brno);
}