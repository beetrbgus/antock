package com.hwang.kyuhyun.mail_order_business.repository;

import com.hwang.kyuhyun.mail_order_business.domain.MailOrderBusiness;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailOrderBusinessRepository extends JpaRepository<MailOrderBusiness, Long> {
}
