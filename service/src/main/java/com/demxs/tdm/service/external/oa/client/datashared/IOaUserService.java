package com.demxs.tdm.service.external.oa.client.datashared;

import com.demxs.tdm.domain.external.oa.usermsg.OAUser;
import com.demxs.tdm.domain.external.oa.RequestMsg;

import javax.jws.WebService;
import java.util.Collection;

/**
 * @author: Jason
 * @Date: 2020/5/18 14:43
 * @Description:
 */
@WebService(name = "IUserService")
public interface IOaUserService {

    int updateFromOaSysByOrgCode(String orgCode);

    int updateFromOaSys(Collection<OAUser> collection);
}
