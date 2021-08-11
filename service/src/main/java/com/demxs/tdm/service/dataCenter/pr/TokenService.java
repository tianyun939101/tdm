package com.demxs.tdm.service.dataCenter.pr;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.dataCenter.pr.TokenDao;
import com.demxs.tdm.domain.dataCenter.pr.Token;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Jason
 * @Date: 2020/4/28 10:01
 * @Description:
 */
@Service
@Transactional(readOnly = false,rollbackFor = ServiceException.class)
public class TokenService extends CrudService<TokenDao, Token> {

    @Transactional(readOnly = true)
    public int updateActive(Token token){
        return this.dao.updateActive(token);
    }
}
