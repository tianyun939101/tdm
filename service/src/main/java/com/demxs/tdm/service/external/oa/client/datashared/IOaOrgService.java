package com.demxs.tdm.service.external.oa.client.datashared;

import com.demxs.tdm.domain.external.oa.RequestMsg;

/**
 * @author: Jason
 * @Date: 2020/5/18 14:46
 * @Description:
 */
public interface IOaOrgService {

    /**
    * @author Jason
    * @date 2020/7/22 16:18
    * @params [orgCode]
    * 同步OA系统指定机构编码下的所有子级机构和用户
    * @return int
    */
    int updateFromOaSysByOrgCode(String orgCode);
}
