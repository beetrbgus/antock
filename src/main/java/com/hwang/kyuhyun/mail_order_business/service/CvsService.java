package com.hwang.kyuhyun.mail_order_business.service;

import java.io.IOException;
import java.util.List;

public interface CvsService {
    String downloadCvs(String city, String district) throws IOException;
    List<String[]> readCvs(String path) throws IOException;
}
