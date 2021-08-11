package com.demxs.tdm.service.business.core;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.business.EntrustTestItemCodition;
import com.demxs.tdm.domain.business.TestTask;
import com.demxs.tdm.domain.business.TestTaskAssignUser;
import com.demxs.tdm.domain.business.vo.TaskInfoVO;
import com.demxs.tdm.domain.business.vo.TaskParam;
import com.demxs.tdm.domain.business.vo.TaskVO;

import java.util.List;

/**
 * 试验任务ID
 * User: wuliepeng
 * Date: 2017-11-08
 * Time: 下午2:40
 */
public interface ITaskService {
    /**
     * 创建任务
     * @param entrustId 申请单ID
     * @throws ServiceException
     */
    void createTask(String entrustId) throws ServiceException;

    /**
     * 创建任务
     * @param entrustId 申请单ID
     * @param planId 计划ID
     * @throws ServiceException
     */
    void createTask(String entrustId,String planId) throws ServiceException;

    /**
     * 分配任务
     * @param taskId 任务ID
     * @param userIds 用户ID
     * @param userNames 用户名称
     * @throws ServiceException
     */
    TestTask assignTask(String taskId,String userIds,String userNames) throws ServiceException;

    /**
     * 开始任务
     * @param taskId 任务ID
     * @param taskUser  用户ID
     * @throws ServiceException
     */
    void startTask(String taskId,String taskUser) throws ServiceException;

    /**
     * 暂停任务
     * @param taskId 任务ID
     * @param taskUser  用户ID
     * @throws ServiceException
     */
    void suspendTask(String taskId,String taskUser) throws ServiceException;

    /**
     * 恢复执行
     * @param taskId    任务ID
     * @throws ServiceException
     */
    void recoverTask(String taskId,String taskUser) throws ServiceException;

    /**
     * 完成任务
     * @param taskId    任务ID
     * @throws ServiceException
     */
    void complateTask(String taskId) throws ServiceException;

    /**
     * 提交任务
     * @param taskId 任务ID
     * @param fileIds  任务数据
     * @param auditUser 任务审核人
     * @throws ServiceException
     */
    void submitTask(String taskId,String fileIds) throws ServiceException;

    void submitTask(TestTask testTask) throws ServiceException;

    /**
     * 保存任务
     * @param taskId 任务ID
     * @param fileIds  任务数据
     * @throws ServiceException
     */
    void saveTask(String taskId,String fileIds) throws ServiceException;

    /**
     * 保存任务
     * @throws ServiceException
     */
    void saveTask(TestTask testTask) throws ServiceException;

    /**
     * 加载试验任务的样品详情
     * @param taskId 任务ID
     * @return
     * @throws ServiceException
     */
    TestTask getSampleDetails(String taskId) throws ServiceException;

    /**
     * 审核任务
     * @param taskId 任务ID
     * @param auditInfo 审核信息
     * @throws ServiceException
     */
    void auditTask(String taskId, AuditInfo auditInfo) throws ServiceException;

    /**
     * 终止任务
     * @param taskId 任务ID
     * @param reason 终止原因
     * @throws ServiceException
     */
    void stopTask(String taskId,String reason) throws ServiceException;

    /**
     * 任务分配记录
     * @param taskId    任务ID
     * @return
     * @throws ServiceException
     */
    List<TestTaskAssignUser> taskAssignRecord(String taskId) throws ServiceException;

    /**
     * 任务分配列表
     * @param page 分页信息
     * @param taskParam 任务查询参数
     * @return
     * @throws ServiceException
     */
    Page<TaskVO> findPage(Page<TaskVO> page, TaskParam taskParam) throws ServiceException;

    /**
     * 已完成任务列表
     * @param page 分页信息
     * @param testTask  查询条件信息
     * @return
     * @throws ServiceException
     */
    Page<TestTask> listByFinsh(Page<TestTask> page,TestTask testTask) throws ServiceException;

    /**
     * 任务列表
     * @param page  分页信息
     * @param testTask 查询条件信息
     * @return
     * @throws ServiceException
     */
    Page<TestTask> list(Page<TestTask> page,TestTask testTask) throws ServiceException;

    /**
     * 任务明细表
     * @param taskId 任务ID
     * @return
     * @throws ServiceException
     */
    List<TestTask> listByDetail(String taskId) throws ServiceException;

    /**
     * 任务详细信息
     * @param taskId 任务ID
     * @return
     * @throws ServiceException
     */
    TestTask get(String taskId) throws ServiceException;

    /**
     * 加载试验任务信息
     * @param taskId 任务ID
     * @throws ServiceException
     */
    TaskInfoVO loadTaskInfo(String taskId) throws ServiceException;

	List<List<EntrustTestItemCodition>> getEntrustTestItemConditions(List<String> testItemPlanDetailList);

	/**
     * 加载试验任务信息
     * @param taskId 任务ID
     * @throws ServiceException
     */
    TaskInfoVO loadSubTaskInfo(String taskId) throws ServiceException;

    /**
     * 更改设备
     * @param taskId 设备ID
     * @param oldDeviceId 原设备ID
     * @param newDeviceId 新设备ID
     * @return
     */
    void changeDevice(String taskId, String oldDeviceId, String newDeviceId) throws ServiceException;

    /**
     * 保存原始记录数据
     * @param taskId 任务ID
     * @param originId 原始记录数据ID
     * @throws ServiceException
     */
    void saveOriginRecord(String taskId, String originId) throws ServiceException;

    /**
     * 删除任务样品
     * @param taskId    任务ID
     * @param sampleId  样品ID
     * @return
     */
    void removeTaskSample(String taskId, String sampleId);


    /**
     * 添加任务样品
     * @param taskId    任务ID
     * @param sampleId  样品ID
     * @return
     */
    void addTaskSample(String taskId, String sampleId);
}
