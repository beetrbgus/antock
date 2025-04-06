package com.hwang.kyuhyun.mail_order_business.service;

import com.hwang.kyuhyun.mail_order_business.dto.CvsMailOrderBusinessDto;

import java.io.IOException;
import java.util.List;

public interface CvsService {
    String downloadCvs(String city, String district) throws IOException;
    List<CvsMailOrderBusinessDto> readCvs(String path) throws IOException;
}
