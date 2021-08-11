package com.demxs.tdm.service.business.nostandard;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.dao.business.nostandard.NoStandardBeforeTestDao;
import com.demxs.tdm.domain.business.nostandard.NoStandardBeforeTest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther: Jason
 * @Date: 2020/3/3 18:52
 * @Description:
 */
@Service
@Transactional(readOnly = true)
public class NoStandardBeforeTestService extends CrudService<NoStandardBeforeTestDao, NoStandardBeforeTest> {


}
