package com.demxs.tdm.service.business.core;

import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.business.AuditInfo;

/**
 * 报告服务
 * User: wuliepeng
 * Date: 2017-11-08
 * Time: 下午3:28
 */
public interface IReportService {

    /**
     * 保存报告
     * @param entrustId 申请单ID
     * @param reportFile 报告文件
     * @throws ServiceException
     */
    void saveReport(String entrustId,String reportFile) throws ServiceException;

    /**
     * 创建报告
     * @param entrustId 申请单ID
     * @param reportFile 报告文件
     * @throws ServiceException
     */
    void submitReport(String entrustId,String reportFile) throws ServiceException;

    /**
     * 审核报告
     * @param entrustId 申请单ID
     * @param auditInfo 审核信息
     * @throws ServiceException
     */
    void auditReport(String entrustId,String reportFile,AuditInfo auditInfo) throws ServiceException;

    /**
     * 批准报告
     * @param entrustId 申请单ID
     * @param auditInfo 审核信息
     * @throws ServiceException
     */
    void paasReport(String entrustId,String reportFile,AuditInfo auditInfo) throws ServiceException;

    /**
     * 申请查看报告
     * @param entrustId 申请单ID
     * @throws ServiceException
     */
    String applyReport(String entrustId) throws ServiceException;
}
