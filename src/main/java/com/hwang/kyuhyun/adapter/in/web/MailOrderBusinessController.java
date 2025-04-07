package com.hwang.kyuhyun.adapter.in.web;

import com.hwang.kyuhyun.common.response.CommonResponse;
import com.hwang.kyuhyun.adapter.in.dto.CreateMailOrderBusinessDto;
import com.hwang.kyuhyun.domain.port.in.RegisterMailOrderBusinessUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Validated
@RestController
@RequiredArgsConstructor
public class MailOrderBusinessController {
    private final RegisterMailOrderBusinessUseCase registerMailOrderBusinessUseCase;

    @GetMapping
    public void findMailOrderBusinessList() {
    }

    @PostMapping
    public CommonResponse<Void> createMailOrderBusiness(@Valid @ModelAttribute CreateMailOrderBusinessDto reqDto) throws IOException {
        registerMailOrderBusinessUseCase.createMailOrderBusiness(reqDto);

        return CommonResponse.ok(null);
    }
}
