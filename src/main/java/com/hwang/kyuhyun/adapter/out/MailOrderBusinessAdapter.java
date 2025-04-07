package com.hwang.kyuhyun.adapter.out;

import com.hwang.kyuhyun.domain.model.MailOrderBusiness;
import com.hwang.kyuhyun.domain.port.out.MailOrderBusinessPort;
import com.hwang.kyuhyun.domain.port.out.MailOrderBusinessRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailOrderBusinessAdapter implements MailOrderBusinessPort {
    private final MailOrderBusinessRepository mailOrderBusinessRepository;
    private final EntityManager entityManager;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void saveAll(List<MailOrderBusiness> entityList) {
        int batchSize = 500;

        int entityListTotalSize = entityList.size();
        for (int i = 0; i < entityListTotalSize; i += batchSize) {
            int endIndex = Math.min(i + batchSize, entityListTotalSize);
            log.info("배치 저장 시작. 저장하는 총 항목의 크기 : {}", entityListTotalSize);
            List<MailOrderBusiness> batch = entityList.subList(i, endIndex);

            log.info("배치 저장 중. {}번째 부터 {}번 까지 저장. 배치 크기: {}", i, endIndex - 1, batch.size());
            mailOrderBusinessRepository.saveAll(batch);

            // 배치 처리 후 영속성 컨텍스트 비움.
            entityManager.flush();
            entityManager.clear();
            log.info("배치 저장 완료. 영속성 컨텍스트 비움. 저장 범위: {}부터 {}까지 저장 됨", i, endIndex - 1);
        }
    }
}
