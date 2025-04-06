package com.hwang.kyuhyun.domain.port.out;

import com.hwang.kyuhyun.infrastructure.api.fair_trade_commission.dto.FtcMailOrderBusinessDto;

import java.util.Optional;

public interface FtcMailOrderApiPort {
    Optional<FtcMailOrderBusinessDto> findMailOrderBusinessDetailByBrno(String brno);
}
