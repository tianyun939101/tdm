package com.demxs.tdm.service.dataCenter.pr;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.dataCenter.pr.DirectoryDao;
import com.demxs.tdm.dao.dataCenter.pr.PermissionDao;
import com.demxs.tdm.domain.dataCenter.pr.Directory;
import com.demxs.tdm.domain.dataCenter.pr.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/4/28 10:02
 * @Description:
 */
@Service
@Transactional(readOnly = false,rollbackFor = ServiceException.class)
public class PermissionService extends CrudService<PermissionDao, Permission> {

    @Autowired
    private DirectoryDao directoryDao;

    @Override
    public void save(Permission entity) {
        super.save(entity);
    }

    public void save(List<Permission> permissionList){
        if(permissionList != null && !permissionList.isEmpty()) {
            for (Permission permission : permissionList) {
                this.save(permission);
            }
        }
    }

    @Transactional(readOnly = true)
    public int updateActive(Permission permission){
        return this.dao.updateActive(permission);
    }

    @Transactional(readOnly = true)
    public int deleteByDeptIdAndDirId(Permission permission){
        return this.dao.deleteByDeptIdAndDirId(permission);
    }

    public List<Permission> findByDeptId(String deptId){
        return this.dao.findByDeptId(deptId);
    }

    public List<Permission> findByDirId(Permission permission){
        return this.dao.findByDirId(permission);
    }

    public List<String> findDirIdByDeptId(String id){
        return this.dao.findDirIdByDeptId(id);
    }

    /**
    * @author Jason
    * @date 2020/6/15 18:09
    * @params [deptId, dirId]
    * 校验用户是否有指定文件夹操作权
    * @return boolean
    */
    /*public boolean validate(List<String> deptId,String dirId){
        Directory directory = directoryDao.get(dirId);
        if(directory == null){
            return false;
        }
        List<Permission> list = this.findByDirId(new Permission().setDirId(directory.getParentId()));
        if(null != list && !list.isEmpty()){
            for (Permission permission : list) {
                if(deptId.contains(permission.getDeptId())){
                    return true;
                }
            }
            return false;
        }else{
            return this.validate(deptId,directory.getParentId());
        }
    }*/

    /**
     * @author Jason
     * @date 2020/6/15 18:09
     * @params [deptId, dirId]
     * 校验用户是否有指定文件夹操作权
     * @return boolean
     */
    public boolean validateFtp(List<String> deptId,String path){
        while (path.contains("/")){
            path = path.substring(0,path.lastIndexOf("/"));
            List<Permission> list = this.findByDirId(new Permission().setDirId(path+"/"));
            if(null != list && !list.isEmpty()) {
                for (Permission permission : list) {
                    if (deptId.contains(permission.getDeptId())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
