package com.demxs.tdm.service.external;

import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.external.DeleteTodoParam;
import com.demxs.tdm.domain.external.EhrDeptResult;
import com.demxs.tdm.domain.external.EhrEmployeeResult;
import com.demxs.tdm.domain.external.MesResult;
import com.demxs.tdm.domain.external.SendTodoParam;

import java.util.List;

/**
 * 外部服务接品
 * User: wuliepeng
 * Date: 2017-11-30
 * Time: 上午9:24
 */
public interface IExternalApi {

    /**
     * 根据样品编码获取样品BOM信息
     * @param apiAddress api地址接口
     * @param sn 样品编码信息
     * @return
     * @throws ServiceException
     */
    List<MesResult> findSampleBomBySn(String apiAddress,String sn) throws ServiceException;

    /**
     * 发送待办任务
     * @param todoParam 待办任务参数
     * @return
     * @throws ServiceException
     */
    Boolean sendTodo(SendTodoParam todoParam) throws ServiceException;

    /**
     * 删除待办任务参数
     * @param todoParam 待办任务参数
     * @return
     * @throws ServiceException
     */
    Boolean deleteTodo(DeleteTodoParam todoParam) throws ServiceException;

    /**
     * 设为已办
     * @param todoParam
     * @return
     * @throws ServiceException
     */
    Boolean completeTodo(DeleteTodoParam todoParam) throws ServiceException;

    /**
     * 部门/组织
     * @param ehrAddress     ehr/ps 接口地址
     * @param userName        需要传入接口 的userId
     * @param password         需要传入的密码
     * @param synDate         同步时需要传入的日期，ps根据日期去查询或得哪天的数据
     * @param synFlag       全量增量标记   A为全量，
     * @return
     */
    List<EhrDeptResult> getEhrDeptList(String ehrAddress,String userName,String password,String synDate,String synFlag);

    /**
     *人员
     * @param ehrAddress     ehr/ps 接口地址
     * @param userName        需要传入接口 的userId
     * @param password         需要传入的密码
     * @param synDate         同步时需要传入的日期，ps根据日期去查询或得哪天的数据
     * @param synFlag         全量增量标记   A为全量，
     * @return
     */
    List<EhrEmployeeResult> getEhrEmployeeList(String ehrAddress,String userName,String password,String synDate,String synFlag);
}
