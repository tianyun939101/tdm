package com.demxs.tdm.service.business.instrument.impl;

import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.business.instrument.DzCirculationRecordDao;
import com.demxs.tdm.dao.business.instrument.DzInstrumentsApparatusesDao;
import com.demxs.tdm.domain.business.instrument.DzCirculationRecord;
import com.demxs.tdm.domain.business.instrument.DzInstrumentsApparatuses;
import com.demxs.tdm.domain.resource.yuangong.Yuangong;
import com.demxs.tdm.service.sys.OfficeService;
import com.demxs.tdm.service.sys.SystemService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ApparatusesServiceImpl   extends CrudService<DzInstrumentsApparatusesDao, DzInstrumentsApparatuses> {
    @Autowired
    DzInstrumentsApparatusesDao dzInstrumentsApparatusesDao;
    @Autowired
    DzCirculationRecordDao dzCirculationRecordDao;
    @Autowired
    private OfficeService officeService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private ApparatusCategoryService categoryService;
    @Autowired
    private ApparatusLocationService locationService;

    @Autowired
    private JavaMailSenderImpl javaMailSenderImpl;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(DzInstrumentsApparatuses dzInstrumentsApparatuses) {
        if(StringUtils.isNotEmpty(dzInstrumentsApparatuses.getId())){
            DzCirculationRecord ds = new DzCirculationRecord();
            ds.setRelationId(dzInstrumentsApparatuses.getId());
            ds.setMotion("1");
            dzInstrumentsApparatuses.setStatus("1");
            List<DzCirculationRecord> list=dzCirculationRecordDao.getRecordInfo(ds);
            if(CollectionUtils.isNotEmpty(list)) {
                if(dzInstrumentsApparatuses.getState().equals("4")){
                    String id=list.get(0).getId();
                    ds.setId(id);
                    ds.setMotion("2");
                    ds.setRestitutionDate(null);
                    ds.setRestitution("");
                    ds.setCurrentType("1");
                    dzCirculationRecordDao.updateInfo(ds);
                    dzInstrumentsApparatuses.setStatus("0");
                }
            }

          //  dzInstrumentsApparatuses.preUpdate();
            dzInstrumentsApparatusesDao.updateIncreate(dzInstrumentsApparatuses);
        }else{
            dzInstrumentsApparatuses.setStatus("1");
            dzInstrumentsApparatuses.preInsert();
            dzInstrumentsApparatusesDao.insertIncreate(dzInstrumentsApparatuses);
        }




    }
    @Override
    public void delete(DzInstrumentsApparatuses dzInstrumentsApparatuses) {
        super.delete(dzInstrumentsApparatuses);
    }

    public List<DzInstrumentsApparatuses> selectfindList(String name, String manageId, String departments) {
        List<DzInstrumentsApparatuses> aa =  dzInstrumentsApparatusesDao.selectfindList(name,manageId,departments);
        return aa;
    }
    public List<DzInstrumentsApparatuses> selectfindListJG(String state,String name,String manageId,String departments) {
        List<DzInstrumentsApparatuses> aa =  dzInstrumentsApparatusesDao.selectfindListJG(state,name,manageId,departments);
        return aa;
    }

    public List<DzInstrumentsApparatuses> selectAllList(DzInstrumentsApparatuses dzInstrumentsApparatuses) {
        List<DzInstrumentsApparatuses> aa =  dzInstrumentsApparatusesDao.selectAllList(dzInstrumentsApparatuses);
        return aa;
    }

    public List<DzInstrumentsApparatuses> selectfindInfoList(DzInstrumentsApparatuses dzInstrumentsApparatuses) {
        List<DzInstrumentsApparatuses> aa =  dzInstrumentsApparatusesDao.selectfindInfoList(dzInstrumentsApparatuses);
        return aa;
    }

    public List<DzInstrumentsApparatuses> selectfindListJGfor(String state) {
        List<DzInstrumentsApparatuses> aa =  dzInstrumentsApparatusesDao.selectfindListJGfor(state);
        return aa;
    }

    public List<DzCirculationRecord> selectfindListLYJL(String name,String leader,String restitution) {
        List<DzCirculationRecord> aa =  dzCirculationRecordDao.selectfindListLYJL1(name,leader,restitution);
        return aa;
    }

    public List<DzInstrumentsApparatuses> selectBycodeList1(String code,String name) {
        List<DzInstrumentsApparatuses>  returnToolManagement = dzInstrumentsApparatusesDao.selectBycodeList1(code,name);
        return returnToolManagement;
    }
    public List<DzInstrumentsApparatuses> selectBycodeList2(String code) {
        List<DzInstrumentsApparatuses>  returnToolManagement = dzInstrumentsApparatusesDao.selectBycodeList2(code);
        return returnToolManagement;
    }

    public List<DzInstrumentsApparatuses> dzToolManagement(String[] ids) {
        List<DzInstrumentsApparatuses>  dzToolManagement = dzInstrumentsApparatusesDao.selectByTgList(ids);
        return dzToolManagement;
    }

    public List<DzInstrumentsApparatuses> returnToolManagement(String[] ids) {
        List<DzInstrumentsApparatuses>  returnToolManagement = dzInstrumentsApparatusesDao.returnselectByTgList(ids);
        return returnToolManagement;
    }

    public DzInstrumentsApparatuses getAuditInfolist(String id) {
        DzInstrumentsApparatuses selectlist = dzInstrumentsApparatusesDao.selectAuditInfolist(id);

        if (selectlist != null && StringUtils.isNotBlank(selectlist.getManageId())) {
            selectlist.setUser(systemService.getUser(selectlist.getManageId()));
        }
        if (selectlist != null && StringUtils.isNotBlank(selectlist.getLossUser())) {
            selectlist.setLossUserInfo(systemService.getUser(selectlist.getLossUser()));
        }
        if (selectlist != null && StringUtils.isNotBlank(selectlist.getDepartments())) {
            selectlist.setLabInfo(officeService.get(selectlist.getDepartments()));
        }
        if (selectlist != null && StringUtils.isNotBlank(selectlist.getCategoryId())) {
            selectlist.setCategory(categoryService.get(selectlist.getCategoryId()));
        }
        if (selectlist != null && StringUtils.isNotBlank(selectlist.getPositionId())) {
            selectlist.setStorageLocation(locationService.get(selectlist.getPositionId()));
        }
        return selectlist;
    }

    /**
     * @Describe:根据办公室汇总仪表
     * @Author:WuHui
     * @Date:10:48 2020/8/31
     * @param departmentId
     * @return:int
    */
    public Integer countInstrumentByDepartment(String departmentId) {
        //获取实验设备
        Integer cnt = dao.countInstrumentByDepartment(departmentId);
        return cnt;
    }

    /**
     * @Describe:根据办公室查询仪表数据
     * @Author:WuHui
     * @Date:10:48 2020/8/31
     * @param page
     * @param centerId
     * @return:com.demxs.tdm.common.persistence.Page<com.demxs.tdm.domain.business.instrument.DzInstrumentsApparatuses>
    */
    public Page<DzInstrumentsApparatuses> findInstrumentByDepartment(Page<DzInstrumentsApparatuses> page, String centerId, String labinfoId){
        List<DzInstrumentsApparatuses> lists = dao.findInstrumentByDepartment(page, centerId,labinfoId);
        page.setList(lists);
        return page;
    }
    public void updateState(DzInstrumentsApparatuses  dzInstrumentsApparatuses){
        dzInstrumentsApparatusesDao.updateState(dzInstrumentsApparatuses);
    }

    public List<DzInstrumentsApparatuses> selectByState(@Param("ids") String[] ids){
        List<DzInstrumentsApparatuses>  returnToolManagement = dzInstrumentsApparatusesDao.selectByState(ids);
        return returnToolManagement;
    }

    public DzInstrumentsApparatuses selectById(@Param("id") String id){
         return dzInstrumentsApparatusesDao.selectById(id);
    }


    //仪器仪表到期前三周进行发邮件通知
    public   void   sendMail(){

        String fromMail= Global.getConfig("mail.from");
        MimeMessage message = javaMailSenderImpl.createMimeMessage();
        try {
            List<DzCirculationRecord>  userList=dzCirculationRecordDao.getUserName();
            if(!CollectionUtils.isEmpty(userList)){
                for(DzCirculationRecord  name:userList){
                    String  email=name.getEmail();
                    String userName=name.getUname();
                    MimeMessageHelper helper = new MimeMessageHelper(message, true);
                    List<DzCirculationRecord>   list=dzCirculationRecordDao.getInOrOut(userName);
                    //发送方
                    helper.setFrom(fromMail);
                    //接收方
                    helper.setTo(email);
                    //主题
                    helper.setSubject("仪器仪表");
                    //邮件内容
                    StringBuilder content = new StringBuilder("<html><head></head><body><h2>您有下表中未归还的仪器仪表设备，请尽快归还!</h2>");
                    content.append("<table border=\"5\" style=\"border:solid 1px #E8F2F9;font-size=14px;;font-size:18px;\">");
                    content.append("<tr style=\"background-color: #428BCA; color:#ffffff\"><th>设备名称</th><th>设备型号</th><th>责任人</th></tr>");
                    for (DzCirculationRecord data : list) {
                        content.append("<tr>");
                        content.append("<td>" + data.getName() + "</td>"); //第一列设备名称
                        content.append("<td>" + data.getModel()+ "</td>"); //第二列设备类型
                        content.append("<td>" + data.getUname() + "</td>"); //第三列负责人
                        content.append("</tr>");
                    }
                    content.append("</table>");
                    content.append("</body></html>");
                    helper.setText(content.toString(), true);
                    javaMailSenderImpl.send(message);
                }
            }
            /*MimeMessageHelper helper = new MimeMessageHelper(message, true,"utf-8");
            List<String> list=new ArrayList<>();
            list.add("1");list.add("2");list.add("3");
            //发送方
            helper.setFrom(fromMail);
            //接收方
            helper.setTo("yushaokang@comac.intra");
            //主题
            helper.setSubject("仪器仪表");
            //邮件内容
            StringBuilder content = new StringBuilder("<html><head></head><body><h2>您有下表中未归还的仪器仪表设备，请尽快归还</h2>");
            content.append("<table border=\"5\" style=\"border:solid 1px #E8F2F9;font-size=14px;;font-size:18px;\">");
            content.append("<tr style=\"background-color: #428BCA; color:#ffffff\"><th>设备名称</th><th>设备型号</th><th>责任人</th></tr>");
            for (String data : list) {
                content.append("<tr>");
                content.append("<td>" + data+ "</td>"); //第一列设备名称
                content.append("<td>" + data+ "</td>"); //第二列设备类型
                content.append("<td>" +data+ "</td>"); //第三列负责人
                content.append("</tr>");
            }
            content.append("</table>");
            // content.append("<h3></h3>");
            content.append("</body></html>");
            helper.setText(content.toString(), true);
            javaMailSenderImpl.send(message);*/

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    //仪器仪表数据若到期未归还，则将计量状态变为未计量。
    public  void  recordJob(){
        List<String> lits=dzInstrumentsApparatusesDao.getAllLendingRecord();
        if(!CollectionUtils.isEmpty(lits)){
            for(String  id:lits){
                dzInstrumentsApparatusesDao.updateStatus(id);
            }
        }
    }
}
