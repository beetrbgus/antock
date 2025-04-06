package com.hwang.kyuhyun.adapter.out;

import com.hwang.kyuhyun.domain.port.out.FtcMailOrderApiPort;
import com.hwang.kyuhyun.infrastructure.api.fair_trade_commission.FtcMailOrderApiClient;
import com.hwang.kyuhyun.infrastructure.api.fair_trade_commission.dto.FtcMailOrderBusinessDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FtcMailOrderApiAdapter implements FtcMailOrderApiPort {
    private final FtcMailOrderApiClient ftcMailOrderApiClient;

    @Override
    public Optional<FtcMailOrderBusinessDto> findMailOrderBusinessDetailByBrno(String brno) {
        return ftcMailOrderApiClient.findMailOrderBusinessDetailByBrno(brno);
    }
}
