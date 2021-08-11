package com.demxs.tdm.service.oa;

import com.demxs.tdm.service.resource.biaozhunwuzhi.BiaozhunwzCgqdService;
import com.demxs.tdm.service.resource.haocai.HaocaiCgqdService;
import com.demxs.tdm.service.resource.shebei.ShebeiCgqdService;
import com.demxs.tdm.service.resource.zhishi.ZhishiXxService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.SpringContextHolder;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by guojinlong on 2017/8/3.
 */
@Service
@Transactional(rollbackFor = ServiceException.class)
public class ActAuditListener implements ExecutionListener {
    private static final long serialVersionUID = 1L;

   /* @Autowired
    private FangfaService fangfaService;*/
    @Autowired
    private ZhishiXxService zhishiXxService;
    /*@Autowired
    private FanganService fanganService;*/
//    @Autowired
//    private YichangClxxService fanganService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private BiaozhunwzCgqdService biaozhunwzCgqdService;
    @Autowired
    private HaocaiCgqdService haocaiCgqdService;
    @Autowired
    private ShebeiCgqdService shebeiCgqdService;


    @Override
    public void notify(DelegateExecution execution) throws Exception {
       /* String processInstanceId = execution.getProcessInstanceId();
        historyService = getHistoryService();
        fangfaService = getFangfaService();
        zhishiXxService = getZhishiXxService();
        fanganService = getFanganService();
        runtimeService = getRuntimeService();
        biaozhunwzCgqdService = getBiaozhunwzCgqdService();
        haocaiCgqdService = getHaocaiCgqdService();
        shebeiCgqdService = getShebeiCgqdService();
        historyService = SpringContextHolder.getBean(HistoryService.class);
//        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
//        HistoricActivityInstance processInstance = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).singleResult();
//        HistoricActivityInstanceQuery aa = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId);
//        List<HistoricActivityInstance> bb = aa.list();
//        HistoricActivityInstance processInstance = processInstance = bb.get(0);

        //获取业务ID
//        String bussinessId = processInstance.getBusinessKey();
        Map<String, Object> variables = execution.getVariables();
        String bussinessId = (String)variables.get("busId");
        String pass = (String)variables.get("pass");//流程审核结果
        String type = (String) variables.get("type");//流程类型
        //根据业务类型改变业务状态
        if(GlobalConstans.ActProcDefKey.FANGANYZ.equals(type)){//方案验证
            fanganService.saveShenhe(bussinessId,Global.YES.equals(pass));
        }else if(GlobalConstans.ActProcDefKey.FANGFASH.equals(type)){//方法审核
            //todo 改变方法状态
            fangfaService.saveShenhe(bussinessId,Global.YES.equals(pass));
        }else if(GlobalConstans.ActProcDefKey.FANGFAYZ.equals(type)){//方法验证
            fangfaService.saveShenhe(bussinessId,Global.YES.equals(pass));
        }else if(GlobalConstans.ActProcDefKey.YICHANGSH.equals(type)){//异常审核
            //todo 改变异常状态
        }else if(GlobalConstans.ActProcDefKey.ZHISHISH.equals(type)){//知识审核
            zhishiXxService.saveShenhe(bussinessId,Global.YES.equals(pass));
        }else if(GlobalConstans.ActProcDefKey.BIAOZHUNWZCGSH.equals(type)){//标准物质采购审核
            biaozhunwzCgqdService.changeCgzt(bussinessId,Global.YES.equals(pass));
        }else if(GlobalConstans.ActProcDefKey.SHEBEICGSH.equals(type)){//设备采购审核
            shebeiCgqdService.changeCgzt(bussinessId,Global.YES.equals(pass));
        }else if(GlobalConstans.ActProcDefKey.HAOCAICGSH.equals(type)){//耗材采购审核
            haocaiCgqdService.changeCgzt(bussinessId,Global.YES.equals(pass));
        }
        System.out.println("pass"+pass);*/
    }

    /*public FangfaService getFangfaService() {
        if(fangfaService == null){
            fangfaService = SpringContextHolder.getBean(FangfaService.class);
        }
        return fangfaService;
    }

    public ZhishiXxService getZhishiXxService() {
        if(zhishiXxService == null){
            zhishiXxService = SpringContextHolder.getBean(ZhishiXxService.class);
        }
        return zhishiXxService;
    }

    public FanganService getFanganService() {
        if(fanganService == null){
            fanganService = SpringContextHolder.getBean(FanganService.class);
        }
        return fanganService;
    }*/

    public HistoryService getHistoryService() {
        if(historyService == null){
            historyService = SpringContextHolder.getBean(HistoryService.class);
        }
        return historyService;
    }

    public RuntimeService getRuntimeService() {
        if(runtimeService == null){
            runtimeService = SpringContextHolder.getBean(RuntimeService.class);
        }
        return runtimeService;
    }

    public BiaozhunwzCgqdService getBiaozhunwzCgqdService() {
        if(biaozhunwzCgqdService == null){
            biaozhunwzCgqdService = SpringContextHolder.getBean(BiaozhunwzCgqdService.class);
        }
        return biaozhunwzCgqdService;
    }

    public HaocaiCgqdService getHaocaiCgqdService() {
        if(haocaiCgqdService == null){
            haocaiCgqdService = SpringContextHolder.getBean(HaocaiCgqdService.class);
        }
        return haocaiCgqdService;
    }

    public ShebeiCgqdService getShebeiCgqdService() {
        if(shebeiCgqdService == null){
            shebeiCgqdService = SpringContextHolder.getBean(ShebeiCgqdService.class);
        }
        return shebeiCgqdService;
    }
}
