package com.demxs.tdm.service.business.core;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.business.EntrustInfo;
import com.demxs.tdm.domain.business.EntrustSampleGroup;
import com.demxs.tdm.domain.business.vo.MainTotalVO;

/**
 * 申请业务服务
 * User: wuliepeng
 * Date: 2017-11-08
 * Time: 上午10:31
 */
public interface IEntrustService {

    /**
     * 保存申请
     * @param entrustInfo   申请单信息
     * @throws ServiceException
     */
    void save(EntrustInfo entrustInfo) throws ServiceException;

    /**
     * 修改申请
     * @param entrustInfo   申请单信息
     * @throws ServiceException
     */
    void sysSave(EntrustInfo entrustInfo) throws ServiceException;

    /**
     * 修改样品
     * @param entrustInfo   申请单信息
     * @throws ServiceException
     */
    void modifySample(EntrustInfo entrustInfo) throws ServiceException;

    /**
     * 删除申请信息
     * @param entrustId 申请单ID
     * @throws ServiceException
     */
    void delete(String entrustId) throws ServiceException;

    /**
     * 提交申请
     * @param entrustInfo   申请单信息
     * @throws ServiceException
     */
    void submit(EntrustInfo entrustInfo) throws ServiceException;

    /**
     * 撤消申请
     * @param entrustId 申请ID
     * @param userId    撤消人
     * @param reason    撤消原因
     * @throws ServiceException
     */
    void undo(String entrustId,String userId,String reason) throws ServiceException;

    /**
     * 信息反馈
     * @param entrustId 申请ID
     * @param reason    撤消原因
     * @throws ServiceException
     */
    void feedback(String entrustId,String reason) throws ServiceException;

    /**
     * 终止申请
     * @param entrustId 申请单ID
     * @param userId    终止用户
     * @param reason    终止原因
     * @param notify    是否发送通知
     * @throws ServiceException
     */
    void stop(String entrustId,String userId,String reason,Integer notify) throws ServiceException;

    /**
     * 完成申请单
     * @param entrustId 申请单ID
     * @throws ServiceException
     */
    void finish(String entrustId) throws ServiceException;

    /**
     * 申请部门负责人或项目负责人审核申请信息
     * @param entrustId 申请单ID
     * @param auditInfo    审核信息
     * @throws ServiceException
     */
    void audit(String entrustId,AuditInfo auditInfo) throws ServiceException;

    /**
     * 试验室负责人接收(审核)申请
     * @param entrustId 申请单ID
     * @param auditInfo 审核信息
     * @throws ServiceException
     */
    void accept(String entrustId,AuditInfo auditInfo) throws ServiceException;

    /**
     * 试验室负责人指定试验负责人
     * @param entrustId 申请单ID
     * @param testLeader 试验负责人
     * @param reason 审批意见
     * @throws ServiceException
     */
    void assignLeader(String entrustId,String testLeader, String reason) throws ServiceException;

    /**
     * 任务处理人变更
     * @param entrustCode 申请单编号
     * @param oldUserId 原用户ID
     * @param newUserId 新用户ID
     * @throws ServiceException
     */
    void changeUser(String entrustCode,String oldUserId,String newUserId) throws ServiceException;

    /**
     * 获取申请单基础信息
     * @param entrustId 申请单ID
     * @return
     * @throws ServiceException
     */
    EntrustInfo get(String entrustId) throws ServiceException;

    /**
     * 获取申请单基础信息
     * @param code 申请单ID
     * @return
     * @throws ServiceException
     */
    EntrustInfo getByCode(String code) throws ServiceException;

    /**
     * 查看申请单信息
     * @param entrustId 申请单ID
     * @return
     * @throws ServiceException
     */
    EntrustInfo detail(String entrustId) throws ServiceException;

    /**
     * 查看申请单信息
     * @param entrustId 申请单ID
     * @return
     * @throws ServiceException
     */
    EntrustInfo detailTask(String entrustId) throws ServiceException;

    /**
     * 样品组详情
     * @param sampleGroupId 样品组ID
     * @return
     */
    EntrustSampleGroup sampleGroupDetail(String sampleGroupId) throws ServiceException;

    /**
     * 申请列表
     * @param page 条件与分页信息
     * @param entrustInfo 查询条件封装
     * @return
     * @throws ServiceException
     */
    Page<EntrustInfo> list(Page<EntrustInfo> page,EntrustInfo entrustInfo) throws ServiceException;

    /**
     * 申请单报告状态列表
     * @param page 条件与分页信息
     * @param entrustInfo 查询条件封装
     * @return
     * @throws ServiceException
     */
    Page<EntrustInfo> listByReport(Page<EntrustInfo> page,EntrustInfo entrustInfo) throws ServiceException;

    /**
     * 首页申请单信息统计
     * @return
     */
    MainTotalVO getByMainTotal();


    /**
     * 给这个申请单下面的样品添加试验室信息
     * @param entrustId
     */
    void addLabInfoByEntrustId(String entrustId);
}
