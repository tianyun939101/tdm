package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.dao.RoleDao;
import com.demxs.tdm.common.sys.dao.UserDao;
import com.demxs.tdm.common.sys.entity.Role;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.dao.dataCenter.DcServerProvideDao;
import com.demxs.tdm.domain.dataCenter.DcServerProvide;
import com.demxs.tdm.service.sys.SystemService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class DcServerProvideService extends  CrudService<DcServerProvideDao, DcServerProvide> {

    @Autowired
    DcServerProvideDao DcServerProvideDao;

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    SystemService  systemService;



    public Page<DcServerProvide> list(Page<DcServerProvide> page, DcServerProvide dzProviderDirectories) {
        Page<DcServerProvide> Page = super.findPage(page, dzProviderDirectories);
        return Page;
    }

    public List<DcServerProvide> getList() {
        List<DcServerProvide> List = DcServerProvideDao.getList();
        return List;
    }
    public List<DcServerProvide> selectList(String name, String state) {
        List<DcServerProvide> List = DcServerProvideDao.selectList(name, state);
        return List;
    }

    public DcServerProvide selectOnetInfo(String name, String contacts) {
        DcServerProvide   one = DcServerProvideDao.selectOneInfo(name,contacts);
        return one;
    }

    public DcServerProvide selectListInfo(String name) {
        DcServerProvide   List = DcServerProvideDao.selectListInfo(name);
        return List;
    }

    public List<DcServerProvide> listCooperation(String name, String state) {
        List<DcServerProvide> List = DcServerProvideDao.listCooperation(name, state);
        return List;
    }


    public boolean findServiceByPhonePassword(String phone, String password) {
        boolean isExist = false;
        List<String> list = DcServerProvideDao.findServiceByPhonePassword(phone,password);
        for(String pw:list){
            if(SystemService.validatePassword(password,pw)){
                isExist = true;
                break;
            }
        }
        return isExist;
    }

    public DcServerProvide get(String id) {
        DcServerProvide dzDevelopmentPlanning = super.dao.get(id);
        return dzDevelopmentPlanning;
    }


    public void save(DcServerProvide dzDevelopmentPlanning) {

        super.save(dzDevelopmentPlanning);

    }

    public  User   getUserInfo(User user,DcServerProvide  ds){

        User us=userDao.getUserInfoMess(user);
        String flag="";
        if(null!=ds){
            flag=ds.getState();
        }
        if(null == us){
            user.preInsert();
            userDao.insert(user);
            User userInfo=userDao.getUserInfoMess(user);
            Role r=new Role();
            r.setName("试验服务商");
            Role  role= roleDao.getByName(r);
            List<Role> list=new ArrayList<>();
            list.add(role);
            List<String> listRole=new ArrayList<>();
            listRole.add(role.getId());
            userInfo.setRoleList(list);
            userInfo.setRoleIdList(listRole);
            userDao.insertUserRole(userInfo);
            systemService.saveActivitiUser(user);
        }else{
            if("3".equals(flag)){
                user.setLoginFlag("0");
            }else{
                user.setLoginFlag("1");
            }
            user.setId(us.getId());
            // 更新用户数据
            user.preUpdate();
            userDao.update(user);
            systemService.saveActivitiUser(user);
        }
        return user;
    }


}
