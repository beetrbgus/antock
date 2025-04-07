package com.hwang.kyuhyun.domain.port.in;

import com.hwang.kyuhyun.adapter.in.dto.CreateMailOrderBusinessDto;

import java.io.IOException;

public interface RegisterMailOrderBusinessUseCase {
    void createMailOrderBusiness(CreateMailOrderBusinessDto reqDto) throws IOException;
}