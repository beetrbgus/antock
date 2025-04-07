package com.hwang.kyuhyun.adapter.in.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateMailOrderBusinessDto(
        @NotBlank String city,
        @NotBlank String district
) {
}
