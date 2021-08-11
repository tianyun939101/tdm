package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.config.GlobalConstans;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.Dict;
import com.demxs.tdm.common.sys.entity.Role;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.DictUtils;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.FreeMarkers;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.dataCenter.DataReportDao;
import com.demxs.tdm.domain.business.ATAChapter;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.business.constant.DataCenterConstants;
import com.demxs.tdm.domain.business.constant.EntrustConstants;
import com.demxs.tdm.domain.dataCenter.DataBaseInfo;
import com.demxs.tdm.domain.dataCenter.DataReport;
import com.demxs.tdm.domain.dataCenter.DataReportPermission;
import com.demxs.tdm.service.business.ATAChapterService;
import com.demxs.tdm.service.business.AuditInfoService;
import com.demxs.tdm.service.oa.service.ActTaskService;
import com.demxs.tdm.service.sys.SystemService;
import com.github.ltsopensource.core.commons.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = false)
public class DataReportService extends CrudService<DataReportDao, DataReport> {

    @Autowired
    private DataReportPermissionService dataReportPermissionService;

    @Autowired
    private ActTaskService actTaskService;

    @Autowired
    private SystemService systemService;
    @Autowired
    private AuditInfoService auditInfoService;
    @Autowired
    private ATAChapterService ataChapterService;
    @Autowired
    private DataBaseInfoService dataBaseInfoService;

    private final static String KEY_ROLE_ID = "21fe661ba90c4c099503afcf46e23e7d";

    public void saveDataReport(DataReport dataReport){
        Boolean b = false;
        if(StringUtils.isBlank(dataReport.getId())){
            b = true;
        }
        super.save(dataReport);
        //保存时默认权限信息
        if(b){
            //申请人
            DataReportPermission apply = new DataReportPermission();
            apply.setTestId(dataReport.getId());
            apply.setType(DataCenterConstants.PermissionType.REPORT);
            apply.setUserId(UserUtils.getUser().getId());
            dataReportPermissionService.save(apply);
            //部门领导
            if(null != UserUtils.getUser().getOffice().getPrimaryPerson() &&
                    !UserUtils.getUser().getId().equals(UserUtils.getUser().getOffice().getPrimaryPerson().getId())){
                DataReportPermission audit = new DataReportPermission();
                audit.setTestId(dataReport.getId());
                audit.setType(DataCenterConstants.PermissionType.REPORT);
                audit.setUserId(UserUtils.getUser().getOffice().getPrimaryPerson().getId());
                dataReportPermissionService.save(audit);
            }
        }
    }

    @Override
    public void save(DataReport dataReport){
        dataReport.setStatus(DataCenterConstants.DataReportStatus.saved);
        this.saveDataReport(dataReport);
    }

    public void submit(DataReport dataReport){
        String status = dataReport.getStatus();
        boolean flag = false;
        if(DataCenterConstants.DataReportStatus.resubmit.equals(status)){
            flag = true;
        }
        dataReport.setStatus(DataCenterConstants.DataReportStatus.audit);
        this.saveDataReport(dataReport);
        //User audit = systemService.getUser(UserUtils.getUser().getOffice().getPrimaryPerson().getId());
        //发起流程，指定审核人审批
        User audit = UserUtils.get(dataReport.getAuditUser().getId());
        this.checkUserRole(audit);
        User createUser = UserUtils.getUser();
        String taskTile = createUser.getName() +"数据提报："+dataReport.getTestName();
        Map<String,Object> model = new HashMap<>();
        model.put("userName",createUser.getName());
        model.put("code",dataReport.getTestName()==null?"":dataReport.getTestName());
        String title = FreeMarkers.renderString(DataCenterConstants.MessageTemplate1.DATA_REPORT_APPLY,model);

        Map<String,Object> vars = new HashMap<>();
        vars.put("message", title);
        vars.put("code", dataReport.getTestName()==null?"":dataReport.getTestName());
        if(flag){
            actTaskService.apiComplete(dataReport.getId(), null, null, audit.getLoginName(), vars);
        }else{
            actTaskService.apiStartProcess(GlobalConstans.ActProcDefKey.REPORT,
                    audit.getLoginName(),dataReport.getId(),taskTile,vars);
        }

    }

    /**
    * @author Jason
    * @date 2020/7/23 10:48
    * @params [user]
    * 检查用户是否有数据共享菜单权限，没有则新增
    * @return void
    */
    private void checkUserRole(User user){
        //判断该审核人是否拥有数据共享菜单权限
        List<Role> roleList = user.getRoleList();
        boolean hasKeyRole = false;
        if(null != roleList){
            for (Role role : roleList) {
                if(KEY_ROLE_ID.equals(role.getId())){
                    hasKeyRole = true;
                }
            }
        }
        //保存审批人新增的角色信息
        if(!hasKeyRole){
            Role role = systemService.getRole(KEY_ROLE_ID);
            if(null == roleList){
                roleList = new ArrayList<>(1);
            }
            roleList.add(role);
            user.setRoleList(roleList);
            systemService.saveUser(user);
        }
    }

    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void audit(String id, AuditInfo auditInfo) {
        DataReport dataReport = super.dao.get(id);
        User createUser = systemService.getUser(dataReport.getCreateById());
        String auditor = "";
        Map<String, Object> model = new HashMap<>();
        model.put("code", dataReport.getTestName() == null ? "" : dataReport.getTestName());
        model.put("userName", createUser.getName());
        String title = "";
        if (dataReport != null) {
            //审核通过
            if (EntrustConstants.AuditResult.PASS.equals(auditInfo.getResult())) {
                auditInfo.setReason(StringUtils.isEmpty(auditInfo.getReason()) ? "同意" : auditInfo.getReason());
                title = FreeMarkers.renderString(DataCenterConstants.MessageTemplate1.AUDIT_PASS, model);
                //修改当前状态
                dataReport.setStatus(DataCenterConstants.DataReportStatus.approved);
            }
            //审核不通过
            if (EntrustConstants.AuditResult.RETURN.equals(auditInfo.getResult())) {
                dataReport.setStatus(DataCenterConstants.DataReportStatus.resubmit);
                title = FreeMarkers.renderString(DataCenterConstants.MessageTemplate1.AUDIT_RETURN, model);
                auditor = createUser.getLoginName();
            }
            super.save(dataReport);
            //设置审核信息
            auditInfo.setBusinessKey(id);
            auditInfo.setAuditUser(UserUtils.getUser());
            auditInfoService.save(auditInfo);
            //流程
            Map<String, Object> vars = new HashMap<>();
            vars.put("message", title);
            actTaskService.apiComplete(id, auditInfo.getReason(), auditInfo.getResult() + "", auditor, null);
        }
    }
    @Override
    public void delete(DataReport dataReport){
        dataReportPermissionService.deleteByTestId(dataReport.getId());
        AuditInfo auditInfo = new AuditInfo();
        auditInfo.setBusinessKey(dataReport.getId());
        auditInfoService.delete(auditInfo);
        super.delete(dataReport);
    }

    public void savePermission(DataReport dataReport) {
        dataReportPermissionService.deleteByTestId(dataReport.getId());
        String operatingStr = dataReport.getOperatingUserStr();
        String viewStr = dataReport.getViewUserStr();
        if(StringUtils.isNotBlank(operatingStr)){
            for(String o:operatingStr.split(",")){
                this.checkUserRole(UserUtils.get(o));
                DataReportPermission d = new DataReportPermission();
                d.setTestId(dataReport.getId());
                d.setType(DataCenterConstants.PermissionType.REPORT);
                d.setUserId(o);
                dataReportPermissionService.save(d);
            }
        }
        if(StringUtils.isNotBlank(viewStr)){
            for(String v:viewStr.split(",")){
                this.checkUserRole(UserUtils.get(v));
                DataReportPermission dv = new DataReportPermission();
                dv.setTestId(dataReport.getId());
                dv.setType(DataCenterConstants.PermissionType.SEARCH);
                dv.setUserId(v);
                dataReportPermissionService.save(dv);
            }
        }
    }

    public DataReport getPermission(DataReport dataReport) {
        List<DataReportPermission> list = dataReportPermissionService.getByTestId(dataReport.getId());
        List<User> views = new ArrayList<>();
        List<User> operatings = new ArrayList<>();
        for(DataReportPermission d : list){
            if(DataCenterConstants.PermissionType.SEARCH.equals(d.getType())){
                views.add(d.getUser());
            }
            if(DataCenterConstants.PermissionType.REPORT.equals(d.getType())){
                operatings.add(d.getUser());
            }
        }
        dataReport.setViewUserList(views);
        dataReport.setOperatingUserList(operatings);
//        List<String> views = new ArrayList<>();
//        List<String> operatings = new ArrayList<>();
//        for(DataReportPermission d : list){
//            if(DataCenterConstants.PermissionType.SEARCH.equals(d.getType())){
//                views.add(d.getUserId());
//            }
//            if(DataCenterConstants.PermissionType.REPORT.equals(d.getType())){
//                operatings.add(d.getUserId());
//            }
//        }
//        String viewStr = StringUtils.join(views,",");
//        String operatingStr = StringUtils.join(operatings,",");
//        dataReport.setViewUserStr(viewStr);
//        dataReport.setOperatingUserStr(operatingStr);
        return dataReport;
    }

    public void saveFile(DataReport dataReport) {
        super.save(dataReport);
    }

    @Override
    public Page<DataReport> findPage(Page<DataReport> page, DataReport entity) {
        entity.setPage(page);
        page.setList(this.findList(entity));
        return page;
    }

    @Override
    public List<DataReport> findList(DataReport dataReport) {
        List<DataReport> list = this.dao.findList(dataReport);
        list = list.stream().distinct().collect(Collectors.toList());
        return list;
    }

    /**
     * @Describe:根据类型获取试验室数据
     * @Author:WuHui
     * @Date:18:56 2020/11/3
     * @param
     * @return:java.util.List<java.util.Map<java.lang.String,java.lang.String>>
    */
    public List<Map<String, String>> getLabInfoDataList(String company) {
        List<Map<String,String>> list = new ArrayList<>();
        //获取产品型号
        List<Dict> products = DictUtils.getDictList("product_model");
        //获取试验性质
        List<Dict> natures = DictUtils.getDictList("entrust_nature");
        //获取试验数据
        List<Dict> res = DictUtils.getDictList("research_test");
        List<Dict> checks = DictUtils.getDictList("check_test");
        List<ATAChapter> ataList = ataChapterService.findATAList(new ATAChapter());
        //获取提报数据
        Map<String, List<DataBaseInfo>> dataBases = this.buildDataReportMap(company);
        //当前提报索引编号
        String dataId;
        List<Dict> l3List = null;
        for(Dict product:products){
            String l1Id = "l1:" + product.getValue();
            list.add(this.buildTreeNode(l1Id,"",product.getLabel()));
            for(Dict nature:natures){
                if(nature.getValue().equals("3") || nature.getValue().equals("5")) continue;
                String l2Id = "";
                if(nature.getValue().equals("1")){
                    l2Id = "l2c:" + product.getValue();
                    l3List = checks;
                }else if(nature.getValue().equals("2")){
                    l2Id = "l2r:" + product.getValue();
                    l3List = res;
                }
                list.add(this.buildTreeNode(l2Id,l1Id,nature.getLabel()));
                String pId="";
                for(Dict l3:l3List){
                    String tmp = IdGen.uuid();
                    String l3Id = tmp + ":" + l3.getValue();
                    list.add(this.buildTreeNode(l3Id,l2Id,l3.getLabel()));
                    for(ATAChapter cha:ataList){
                        if(StringUtils.isNotEmpty(cha.getModel())){
                            if(cha.getModel().equals(product.getLabel())){
                                String id = IdGen.uuid() + ":"+ cha.getId();
                                dataId = product.getValue() + ":"+ nature.getValue() + ":"+l3.getValue()+":"+cha.getId();
                                if(StringUtils.isEmpty(cha.getParentId())){
                                     pId = StringUtils.isEmpty(cha.getParentId()) ? l3Id : (tmp +":"+cha.getParentId());
                                    list.add(this.buildTreeNode(id,pId,cha.getName()));
                                    pId=id;
                                }
                                //添加提报子节点
                                List<DataBaseInfo> dbis = dataBases.get(dataId);
                                if(CollectionUtils.isNotEmpty(dbis)){

                                    list.add(this.buildTreeNode(id,pId,cha.getName()));
                                    for(DataBaseInfo dbi:dbis){
                                        list.add(this.buildTreeNode(dbi.getId(),id,dbi.getTestName()));
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
        System.out.println(list);
        return list;
    }

    private Map<String,String> buildTreeNode(String id,String pId,String name){
        Map<String,String> node = new HashMap<>();
        node.put("id",id);
        node.put("pId",pId);
        node.put("name",name);
        return node;
    }

    /**
     * @Describe:构建数据提报数据集
     * @Author:WuHui
     * @Date:10:28 2020/11/4
     * @param company
     * @return:java.util.Map<java.lang.String,com.demxs.tdm.domain.dataCenter.DataBaseInfo>
    */
    private Map<String, List<DataBaseInfo>> buildDataReportMap(String company){
        Map<String, List<DataBaseInfo>>  map = new HashMap<>();
        //获取数据提报

        DataBaseInfo   dataBaseInfo=new DataBaseInfo();
        User u= UserUtils.getUser();
        String subCenter=systemService.getUserBelongTestCenter(u.getLoginName());
        dataBaseInfo.setTestCenter(subCenter);
        dataBaseInfo.setCompany(company);
        List<DataBaseInfo> list = dataBaseInfoService.findDataByInfo(dataBaseInfo);
       // List<DataBaseInfo> list = dataBaseInfoService.findAllByCompany(company);
        String id = "";
        for(DataBaseInfo dbi:list){
            id = dbi.getProductModel() + ":" + dbi.getTestNature()+ ":" + dbi.getTestDetailName() + ":" + dbi.getAtaChapter();
            if(map.get(id) == null){
                map.put(id,new ArrayList<>());
            }
            map.get(id).add(dbi);
        }
        return map;
    }

    /**
     * @Describe:获取提报树形结构
     * @Author:WuHui
     * @Date:14:07 2021/1/8
     * @return:java.util.List<java.util.Map<java.lang.String,java.lang.String>>
    */
    public List<Map<String, String>> getDataReportTree() {
        List<Map<String,String>> list = new ArrayList<>();
        //获取产品型号
        List<Dict> products = DictUtils.getDictList("product_model");
        //获取试验性质
        List<Dict> natures = DictUtils.getDictList("entrust_nature");
        //获取试验数据
        List<Dict> res = DictUtils.getDictList("research_test");
        List<Dict> checks = DictUtils.getDictList("check_test");
        List<ATAChapter> ataList = ataChapterService.findList(new ATAChapter());
        //当前提报索引编号
        String dataId;
        List<Dict> l3List = null;
        for(Dict product:products){
            String l1Id = "l1:" + product.getValue();
            list.add(this.buildTreeNode(l1Id,"",product.getLabel()));
            for(Dict nature:natures){
                if(nature.getValue().equals("3")) continue;
                String l2Id = "";
                if(nature.getValue().equals("1")){
                    l2Id = "l2c:" + product.getValue();
                    l3List = checks;
                }else if(nature.getValue().equals("2")){
                    l2Id = "l2r:" + product.getValue();
                    l3List = res;
                }
                list.add(this.buildTreeNode(l2Id,l1Id,nature.getLabel()));
                for(Dict l3:l3List){
                    String tmp = String.valueOf(System.currentTimeMillis());
                    String l3Id = tmp + ":" + l3.getValue();
                    list.add(this.buildTreeNode(l3Id,l2Id,l3.getLabel()));
                    for(ATAChapter cha:ataList){
                        if(StringUtils.isNotBlank(cha.getModel())){
                            if(cha.getModel().equals(product.getLabel())){
                                String pId = StringUtils.isEmpty(cha.getParentId()) ? l3Id : (tmp +":"+cha.getParentId());
                                String id = tmp + ":"+ cha.getId();
                                dataId = product.getValue() + ":"+ product.getValue() + ":"+l3.getValue()+":"+cha.getId();
                                list.add(this.buildTreeNode(id,pId,cha.getName()));
                            }
                        }
                    }
                }
            }
        }
        return list;
    }
}
