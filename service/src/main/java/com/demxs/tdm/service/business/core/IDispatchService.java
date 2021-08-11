package com.demxs.tdm.service.business.core;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.business.EntrustInfo;

/**
 * 调度业务服务
 * User: wuliepeng
 * Date: 2017-11-08
 * Time: 上午11:52
 */
public interface IDispatchService {

    /**
     * 试验负责人通过申请人修改样品
     * @param entrustId 申请单ID
     * @param auditInfo 审核信息
     * @throws ServiceException
     */
    void confirmSample(String entrustId, AuditInfo auditInfo) throws ServiceException;

    /**
     * 创建计划
     * @param entrustId 申请ID
     * @param emergency 紧急程度
     * @throws ServiceException
     */
    void createPlan(String entrustId,Integer emergency) throws ServiceException;

    /**
     * 确认样品列表
     * @param page  分页信息
     * @param entrustInfo 查询条件封装
     * @return
     * @throws ServiceException
     */
    Page<EntrustInfo> listByConfirmSample(Page<EntrustInfo> page, EntrustInfo entrustInfo) throws ServiceException;

    /**
     * 编制计划列表
     * @param page  分页信息
     * @param entrustInfo 查询条件封装
     * @return
     * @throws ServiceException
     */
    Page<EntrustInfo> listByPlan(Page<EntrustInfo> page,EntrustInfo entrustInfo) throws ServiceException;
}
