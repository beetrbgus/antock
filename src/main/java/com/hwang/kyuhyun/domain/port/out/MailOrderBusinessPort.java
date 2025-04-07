package com.hwang.kyuhyun.domain.port.out;

import com.hwang.kyuhyun.domain.model.MailOrderBusiness;

import java.util.List;

public interface MailOrderBusinessPort {
    void saveAll(List<MailOrderBusiness> mailOrderBusinessVO);
}
