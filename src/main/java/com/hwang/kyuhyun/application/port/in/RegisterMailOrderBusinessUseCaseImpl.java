package com.hwang.kyuhyun.application.port.in;

import com.hwang.kyuhyun.application.service.CsvDownloadService;
import com.hwang.kyuhyun.application.service.CsvService;
import com.hwang.kyuhyun.common.enums.ErrorCode;
import com.hwang.kyuhyun.common.exception.AppCustomException;
import com.hwang.kyuhyun.domain.CompanyType;
import com.hwang.kyuhyun.domain.model.MailOrderBusiness;
import com.hwang.kyuhyun.domain.port.in.RegisterMailOrderBusinessUseCase;
import com.hwang.kyuhyun.adapter.in.dto.CvsMailOrderBusinessDto;
import com.hwang.kyuhyun.domain.port.out.FtcMailOrderApiPort;
import com.hwang.kyuhyun.domain.port.out.MailOrderBusinessPort;
import com.hwang.kyuhyun.domain.port.out.PublicAddressPort;
import com.hwang.kyuhyun.infrastructure.api.fair_trade_commission.dto.FtcMailOrderBusinessDto;
import com.hwang.kyuhyun.adapter.in.dto.CreateMailOrderBusinessDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterMailOrderBusinessUseCaseImpl implements RegisterMailOrderBusinessUseCase {
    private final CsvService csvService;
    private final CsvDownloadService csvDownloadService;
    private final FtcMailOrderApiPort ftcMailOrderApiPort;
    private final PublicAddressPort publicAddressPort;
    private final MailOrderBusinessPort mailOrderBusinessPort;

    @Override
    public void createMailOrderBusiness(CreateMailOrderBusinessDto reqDto) throws IOException {
        String path = csvDownloadService.download(reqDto.city(), reqDto.district());
        List<CvsMailOrderBusinessDto> cvsMailOrderBusinessDtoList = csvService.readCvs(path);

        List<CvsMailOrderBusinessDto> corporateList = cvsMailOrderBusinessDtoList.stream()
                .filter(mailOrderBusinessDto -> CompanyType.CORPORATE.getValue().equals(mailOrderBusinessDto.getIsCorporation()))
//                .limit(10) // 공정거래위원회 일일 트래픽이 10000개 이기 때문에 제한 걺. 해당 라인을 주석처리하면 전체 cvs 파일 실행
                .toList();

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<CompletableFuture<Optional<MailOrderBusiness>>> futures = corporateList.stream()
                .map(dto ->
                    CompletableFuture
                        .supplyAsync(() -> mergeAndConvertToEntity(dto), executorService)
                        .exceptionally(ex -> {
                            log.warn("비동기 병합 실패 - 사업자 등록 번호: {}, 사유: {}", dto.getBusinessRegistrationNo(), ex.getMessage());
                            return Optional.empty();
                        })
                )
                .toList();

        List<MailOrderBusiness> results = futures.stream()
                .map(CompletableFuture::join)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        executorService.shutdown();
        mailOrderBusinessPort.saveAll(results);
    }

    /**
     * 공정위와 위치 기반으로 주소를 매핑해 Entity로 변환
     * @param dto
     * @return
     */
    private Optional<MailOrderBusiness> mergeAndConvertToEntity(CvsMailOrderBusinessDto dto) {
        try {
            // 공정거래위원회 정보
            FtcMailOrderBusinessDto ftcMailOrderBusinessDto = ftcMailOrderApiPort
                    .findMailOrderBusinessDetailByBrno(dto.getBusinessRegistrationNo())
                    .orElseThrow(() -> new AppCustomException(ErrorCode.NOT_FOUNT_ADDRESS));

            String crno = ftcMailOrderBusinessDto.getCrno();

            if (crno == null || crno.isBlank() || crno.equalsIgnoreCase("N/A") || crno.equals("없음")) {
                log.warn("유효하지 않은 사업자번호(CRNO) - 사업자등록번호: {}, 수신 CRNO: {}", dto.getBusinessRegistrationNo(), crno);
                return Optional.empty();
            }
            String districtCode = publicAddressPort.findDistrictCodeByAddress(ftcMailOrderBusinessDto.getLctnRnOzip());

            return Optional.of(
                    MailOrderBusiness.builder()
                        .companyName(dto.getBusinessName())
                        .businessRegistrationNumber(dto.getBusinessRegistrationNo())
                        .corporationRegistrationNumber(ftcMailOrderBusinessDto.getCrno())
                        .districtCode(districtCode)
                        .mailOrderId(dto.getMailOrderNumber())
                        .build()
            );
        } catch (Exception e) {
            log.warn("병합 실패 - 사업자 등록 번호: {}, 사유: {}", dto.getBusinessRegistrationNo(), e.getMessage());
            return Optional.empty();
        }
    }

}
