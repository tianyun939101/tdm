package com.demxs.tdm.service.resource.knowledge;

import com.demxs.tdm.comac.common.constant.MessageConstants;
import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.config.GlobalConstans;
import com.demxs.tdm.common.dto.ApproveDTO;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.resource.knowledge.KnowledgeConnectDao;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.business.configuration.ConfigurationChangeApplyEnum;
import com.demxs.tdm.domain.resource.kowledge.KnowledgeConnect;
import com.demxs.tdm.service.business.AuditInfoService;
import com.demxs.tdm.service.oa.service.ActTaskService;
import org.apache.axis.components.uuid.UUIDGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class KnowledgeConnectService extends CrudService<KnowledgeConnectDao, KnowledgeConnect> {

    @Autowired
    private KnowledgeConnectDao knowledgeConnectDao;
    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private AuditInfoService auditInfoService;




    public Page<KnowledgeConnect> find(Page<KnowledgeConnect> page , KnowledgeConnect entity) {
        entity.setPage(page);
        List<KnowledgeConnect> list = knowledgeConnectDao.findList(entity);
        List<KnowledgeConnect> list1 = new ArrayList<KnowledgeConnect>();
        String name = entity.getName();
        for(KnowledgeConnect knowledgeConnect : list){
            if(StringUtils.isNotBlank(name)){
                if(name.equals(knowledgeConnect.getName())){
                    String c = knowledgeConnect.getHavePush();  //已发布
                    String a = knowledgeConnect.getMakeUp();   //待编
                    String b = knowledgeConnect.getBeRevised(); //待修订
                    int makeUp = Integer.parseInt(a);
                    int beRevised = Integer.parseInt(b);
                    int push = Integer.parseInt(c);
                    knowledgeConnect.setBeUp(makeUp+beRevised);
                    knowledgeConnect.setPlanConnect(makeUp+beRevised+push);
                    list1.add(knowledgeConnect);
                }
            }else{
                String c = knowledgeConnect.getHavePush();  //已发布
                String a = knowledgeConnect.getMakeUp();   //待编
                String b = knowledgeConnect.getBeRevised(); //待修订
                int makeUp = Integer.parseInt(a);
                int beRevised = Integer.parseInt(b);
                int push = Integer.parseInt(c);
                knowledgeConnect.setBeUp(makeUp+beRevised);
                knowledgeConnect.setPlanConnect(makeUp+beRevised+push);
                list1.add(knowledgeConnect);
            }

        }
        return page.setList(list1);
    }


    public List<KnowledgeConnect> getList( KnowledgeConnect entity) {
       return knowledgeConnectDao.getList(entity);
    }
    public List<KnowledgeConnect> findListAudit( KnowledgeConnect entity) {
        return knowledgeConnectDao.findListAudit(entity);
    }
    public KnowledgeConnect getAudit(String id){
        return knowledgeConnectDao.getAudit(id);
    }
    public void updataFlag(String id){
        knowledgeConnectDao.updataFlag(id);
    }
    public void save(KnowledgeConnect knowledgeConnect,String flag){
        super.save(knowledgeConnect);
        if("submit".equals(flag)){
            knowledgeConnect.setUserStartId(knowledgeConnect.getCreateById());
            User auditUser = UserUtils.get(knowledgeConnect.getUserId());   //负责人
            String title = "请处理"+UserUtils.getUser().getName()+"提交的"+knowledgeConnect.getModuleName()+"新增编辑！";
            Map<String,Object> vars = new HashMap<>(1);
            vars.put("message", title);
            //启动流程
            actTaskService.apiStartProcess("knowledgeConnect", auditUser.getLoginName(), knowledgeConnect.getId(), title, vars);
            knowledgeConnect.setStatus("已发起");
            super.save(knowledgeConnect);
            String num = knowledgeConnect.getNum();
            int integer = Integer.valueOf(num);
            String id = knowledgeConnect.getId();
            for(int i =0;i<integer;i++){
                KnowledgeConnect knowledgeConnect1 = new KnowledgeConnect();
                knowledgeConnect1 = knowledgeConnect;
                knowledgeConnect1.setDelFlag("2");
                UUID uuid = UUID.randomUUID();
                knowledgeConnect1.setId(uuid.toString());
                knowledgeConnect1.setBeloneId(id);
                knowledgeConnectDao.insertAudit(knowledgeConnect);
            }
        }
    }


    //审批
    public void approve(ApproveDTO approveDto) {
        //获取数据
        KnowledgeConnect knowledgeConnect = this.get(approveDto.getId());
        String status=knowledgeConnect.getStatus();
        knowledgeConnect.setId(approveDto.getId());
        //下一节点审批人
        User auditUser = UserUtils.get(knowledgeConnect.getCreateBy().getId());
        Map<String,Object> vars = new HashMap<>(1);
        //审批
        String title = "请处理'"+UserUtils.getUser().getName()+"'提交的"+knowledgeConnect.getModuleName()+"新增编辑！";
        vars.put("message", title);
        actTaskService.apiComplete(knowledgeConnect.getId(),approveDto.getOpinion(), Global.YES, auditUser.getLoginName(),vars);
        //审批履历
        AuditInfo auditInfo = new AuditInfo(approveDto.getId(),auditUser,1,approveDto.getOpinion());
        auditInfoService.save(auditInfo);
        switch (status){
            case "已发起": status = "已编辑";
                break;
            case "已编辑":status = "已审批";
                break;

        }
        knowledgeConnect.setStatus(status);
        this.save(knowledgeConnect);
    }
    //驳回
    public void reject(ApproveDTO approveDto) {
        //获取数据
        KnowledgeConnect k  = this.get(approveDto.getId());
        String status=k.getStatus();
        k.setId(approveDto.getId());
        //驳回编辑人
        User start = UserUtils.get(k.getUserStartId());
        User auditUser = UserUtils.get(k.getUserId());
        Map<String,Object> vars = new HashMap<>(1);
        //审批
        String title = "您提交的知识编辑申请，已被'"+start.getName()+"'驳回！";
        vars.put("message", title);
        String assignee = auditUser== null ? "" : auditUser.getLoginName();
        actTaskService.apiComplete(k.getId(),approveDto.getOpinion(), Global.NO, assignee,vars);
        //审批履历
        AuditInfo auditInfo = new AuditInfo(approveDto.getId(),auditUser,0,approveDto.getOpinion());
        auditInfoService.save(auditInfo);
        //更新状态及当前处理人
        status = "驳回";
        k.setStatus(status);
        this.save(k);
    }

    public void deleteAudit(String id){
        knowledgeConnectDao.deleteAudit(id);
    }
    public void deleteAct(String id){
        knowledgeConnectDao.deleteAct(id);
    }
}
