package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.dataCenter.FlightTestInfoDao;
import com.demxs.tdm.domain.business.ATAChapter;
import com.demxs.tdm.domain.business.constant.DataCenterConstants;
import com.demxs.tdm.domain.dataCenter.DataFlightPerm;
import com.demxs.tdm.domain.dataCenter.FlightTestInfo;
import com.demxs.tdm.service.business.ATAChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = false)
public class FlightTestInfoService extends CrudService<FlightTestInfoDao, FlightTestInfo> {

    @Autowired
    private ATAChapterService ataChapterService;

    @Autowired
    private DataFlightPermService dataFlightPermService;

    @Override
    @Transactional(readOnly = true)
    public FlightTestInfo get(String id){
        FlightTestInfo flightTestInfo = super.dao.get(id);
        //获取ATA章节信息
        String ataChapter = flightTestInfo.getAtaChapter();
        if(StringUtils.isNotBlank(ataChapter)){
            List<ATAChapter> ataList = new ArrayList<>();
            String[] ataArr = ataChapter.split(",");
            for(String ataId:ataArr){
                ATAChapter ata = ataChapterService.get(ataId);
                if(ata!=null){
                    ataList.add(ata);
                }
            }
            flightTestInfo.setAtaList(ataList);
        }
        return flightTestInfo;
    }

    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void save(FlightTestInfo flightTestInfo){
        Boolean isNewRecord = false;
        if(StringUtils.isBlank(flightTestInfo.getId())){
            isNewRecord = true;
        }
        //保存数据中心基础信息
        super.save(flightTestInfo);
        //新增信息保存默认权限当前用户
        if(isNewRecord){
            DataFlightPerm dataFlightPerm = new DataFlightPerm();
            dataFlightPerm.setFlightId(flightTestInfo.getId());
            dataFlightPerm.setAuthorizationId(UserUtils.getUser().getId());
            dataFlightPerm.setPermissionType(DataCenterConstants.PermissionType.REPORT);//权限类型 1查看权限 2操作权限
            dataFlightPerm.setAuthorizationType(DataCenterConstants.AuthorizationType.PERSON);//授权类型 1人员 2 部门
            dataFlightPerm.setAuthorizer(UserUtils.getUser().getId());//授权人取当前用户
            dataFlightPermService.save(dataFlightPerm);
        }

    }


    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void delete(FlightTestInfo flightTestInfo){
        //删除权限信息
        dataFlightPermService.detetePermByFlightId(flightTestInfo.getId());
        //删除数据中心基础信息
        super.delete(flightTestInfo);
    }

    /**
     * 数据权限操作
     * @param flightTestInfo
     */
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void saveAuthority(FlightTestInfo flightTestInfo) {
        //保存前删除原有权限
        dataFlightPermService.detetePermByFlightId(flightTestInfo.getId());
        String reportUserStr = flightTestInfo.getReportUserStr();
        String reportOfficeStr = flightTestInfo.getReportOfficeStr();
        String searchUserStr = flightTestInfo.getSearchUserStr();
        String searchOfficeStr = flightTestInfo.getSearchOfficeStr();
        Date date = flightTestInfo.getPermValidDate();
        saveAuthorityByStr(flightTestInfo.getId(),reportUserStr,DataCenterConstants.PermissionType.REPORT,DataCenterConstants.AuthorizationType.PERSON,null);
        saveAuthorityByStr(flightTestInfo.getId(),reportOfficeStr,DataCenterConstants.PermissionType.REPORT,DataCenterConstants.AuthorizationType.ORG,null);
        saveAuthorityByStr(flightTestInfo.getId(),searchUserStr,DataCenterConstants.PermissionType.SEARCH,DataCenterConstants.AuthorizationType.PERSON,date);
        saveAuthorityByStr(flightTestInfo.getId(),searchOfficeStr,DataCenterConstants.PermissionType.SEARCH,DataCenterConstants.AuthorizationType.ORG,date);
    }

    private void saveAuthorityByStr(String baseId,String str,String permType, String authorizationType,Date date){
        if(StringUtils.isBlank(str)){
            return;
        }
        String[] arr = str.split(",");
        for(String authorizationId:arr){
            DataFlightPerm dataFlightPerm = new DataFlightPerm();
            dataFlightPerm.setFlightId(baseId);
            dataFlightPerm.setPermissionType(permType);
            dataFlightPerm.setAuthorizationId(authorizationId);
            dataFlightPerm.setAuthorizationType(authorizationType);
            dataFlightPerm.setValidDate(date);
            dataFlightPerm.setAuthorizer(UserUtils.getUser().getId());//授权人取当前用户
            dataFlightPermService.save(dataFlightPerm);
        }
    }
    /**
     * 根据试飞信息ID获取数据权限信息
     * @param flightTestInfo
     * @return
     */
    @Transactional(readOnly = true)
    public FlightTestInfo getAuthorityByFlightId(FlightTestInfo flightTestInfo) {
        List<User> reportUser = new ArrayList<>();//操作权限用户集合
        List<Office> reportOffice = new ArrayList<>();;//操作权限部部门集合
        List<User> searchUser = new ArrayList<>();;//查看权限用户集合
        List<Office> searchOffice = new ArrayList<>();;//查看权限部部门集合
        Date searchEndDate = null;//查看权限截止日期
        //获取用户权限数据
        List<DataFlightPerm> userPermList = dataFlightPermService.getUserPermByFlightId(flightTestInfo.getId());
        for(DataFlightPerm data:userPermList){
            if("1".equals(data.getPermissionType())){//查看权限
                searchUser.add(data.getUser());
                searchEndDate = data.getValidDate();//部门和人员权限有效期一致
            }else if("2".equals(data.getPermissionType())){//操作权限
                reportUser.add(data.getUser());
            }
        }
        //获取部门权限数据
        List<DataFlightPerm> officePermList = dataFlightPermService.getOfficePermByFlightId(flightTestInfo.getId());
        for(DataFlightPerm data:officePermList){
            if("1".equals(data.getPermissionType())){//查看权限
                searchOffice.add(data.getOffice());
            }else if("2".equals(data.getPermissionType())){//操作权限
                reportOffice.add(data.getOffice());
            }

        }
        flightTestInfo.setReportUser(reportUser);
        flightTestInfo.setReportOffice(reportOffice);
        flightTestInfo.setSearchUser(searchUser);
        flightTestInfo.setSearchOffice(searchOffice);
        flightTestInfo.setPermValidDate(searchEndDate);

        return flightTestInfo;
    }

}
