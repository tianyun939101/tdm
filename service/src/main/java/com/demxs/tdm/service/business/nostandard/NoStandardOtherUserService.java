package com.demxs.tdm.service.business.nostandard;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.dao.business.nostandard.NoStandardOtherUserDao;
import com.demxs.tdm.domain.business.nostandard.NoStandardOtherUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: Jason
 * @Date: 2020/3/5 14:23
 * @Description:
 */
@Service
@Transactional(readOnly = true)
public class NoStandardOtherUserService extends CrudService<NoStandardOtherUserDao, NoStandardOtherUser> {

    @Transactional(readOnly = false)
    public int deleteByResourceId(String id){
        return super.dao.deleteByResourceId(id);
    }

    public List<NoStandardOtherUser> getByResourceId(String resourceId){
        return this.dao.findListByResourceId(resourceId);
    }

}
