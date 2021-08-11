package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.common.utils.excel.annotation.ExcelField;
import com.demxs.tdm.domain.lab.LabInfo;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class EquipmentSpecial extends DataEntity<EquipmentSpecial> {

    private static final long serialVersionUID = 1L;
    private String serialNum; //序号

    private String equipmentName;   //设备名称

    private String equipmentCode;   //设备代码

    private String  factoryInCode; //场内编号

    private String registryCode;   //使用登记编号

    private String model;   //型号规格

    private String currentState;//目前状态

    private String address;   //address

    private String equipmentUser;   //设备负责人

    private Date preCheckDate;   //本次检验日期

    private Date nextCheckDate;   //下次检验日期

    private String fileId;

    private String equipmentType;   //设备类型

    private User user;

    private String remarks;	// 备注

    private LabInfo labInfo;

    private String labId;

    private String subCenter;


    public String getSubCenter() {
        return subCenter;
    }

    public void setSubCenter(String subCenter) {
        this.subCenter = subCenter;
    }

    public LabInfo getLabInfo() {
        return labInfo;
    }

    public void setLabInfo(LabInfo labInfo) {
        this.labInfo = labInfo;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public User getUser() {
        return user;
    }
    public void setPreCheckDate(Date preCheckDate) {

        this.preCheckDate = preCheckDate;
    }
    public void setUser(User user) {
        this.user = user;
    }
    @ExcelField(title = "序号",sort = 0,type = 1)
    public String getSerialNum() {
        return serialNum;
    }
    @ExcelField(title = "序号",sort = 0,type = 2)
    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    @ExcelField(title = "设备名称",sort = 1,type = 1)
    public String getEquipmentName() {
        return equipmentName;
    }
    @ExcelField(title = "设备名称",sort = 1,type = 2)
    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }
    @ExcelField(title = "设备代码",sort = 2,type = 1)
    public String getEquipmentCode() {
        return equipmentCode;
    }
    @ExcelField(title = "设备代码",sort = 2,type = 2)
    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode;
    }
    @ExcelField(title = "场内编号",sort = 3,type = 1)
    public String getFactoryInCode() {
        return factoryInCode;
    }
    @ExcelField(title = "场内编号",sort = 3,type = 2)
    public void setFactoryInCode(String factoryInCode) {
        this.factoryInCode = factoryInCode;
    }
    @ExcelField(title = "使用登记证编号",sort = 4,type = 1)
    public String getRegistryCode() {
        return registryCode;
    }
    @ExcelField(title = "使用登记证编号",sort = 4,type = 2)
    public void setRegistryCode(String registryCode) {
        this.registryCode = registryCode;
    }
    @ExcelField(title = "规格型号",sort = 5,type = 1)
    public String getModel() {
        return model;
    }
    @ExcelField(title = "规格型号",sort = 5,type = 2)
    public void setModel(String model) {
        this.model = model;
    }
    @ExcelField(title = "使用地点",sort = 6,type = 1)
    public String getAddress() {
        return address;
    }
    @ExcelField(title = "使用地点",sort = 6,type = 2)
    public void setAddress(String address) {
        this.address = address;
    }

    public String getEquipmentUser() {
        return equipmentUser;
    }

    public void setEquipmentUser(String equipmentUser) {
        this.equipmentUser = equipmentUser;
    }
    @ExcelField(title = "本次检验日期",sort = 7,type = 1)
    public Date getPreCheckDate() {
        return preCheckDate;
    }
    @ExcelField(title = "本次检验日期",sort = 7,type = 2)
    public void setPreCheckDate1(String preCheckDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy/MM");
        Calendar c = new GregorianCalendar(1900,0,-1);
        if(StringUtils.isNotBlank(preCheckDate)&&preCheckDate.length()>=4){
            String bigDecimal = new BigDecimal(preCheckDate).toString();
            String year = bigDecimal.substring(0,4);
            String month = "";
            String day = "";
            if(bigDecimal.length()>6){
                 month = bigDecimal.substring(4,6);
                day = bigDecimal.substring(6,8);
                String date = year+"/"+month+"/"+day;
                try {
                    this.preCheckDate  = simpleDateFormat.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }else if(bigDecimal.length()<=6){
                month = bigDecimal.substring(4,6);
                String date = year+"/"+month;
                try {
                    this.preCheckDate  = simpleDateFormat1.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        }else{
            this.preCheckDate = null;
        }
    }
    @ExcelField(title = "下次检验日期",sort = 8,type = 1)
    public Date getNextCheckDate() {
        return nextCheckDate;
    }
    @ExcelField(title = "下次检验日期",sort = 8,type = 2)
    public void setNextCheckDate(Date nextCheckDate) {
        this.nextCheckDate = nextCheckDate;
    }
    @ExcelField(title = "目前状态",sort = 9,type = 1)
    public String getCurrentState() {
        return currentState;
    }
    @ExcelField(title = "目前状态",sort = 9,type = 2)
    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }
    @ExcelField(title = "备注",sort = 10,type = 1)
    @Override
    public String getRemarks() {
        return remarks;
    }
    @ExcelField(title = "备注",sort = 10,type = 2)
    @Override
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

}
