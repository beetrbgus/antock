package com.hwang.kyuhyun.domain.port.out;

import com.hwang.kyuhyun.domain.model.MailOrderBusiness;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailOrderBusinessRepository extends JpaRepository<MailOrderBusiness, Long> {
}
