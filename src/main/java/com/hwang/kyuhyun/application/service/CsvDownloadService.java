package com.hwang.kyuhyun.application.service;

import com.hwang.kyuhyun.infrastructure.api.csv.CsvDownloadClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CsvDownloadService {
    private final CsvDownloadClient csvDownloadClient;

    public String download(String city, String district) throws IOException {
        return csvDownloadClient.downloadCvs(city, district);
    }
}
