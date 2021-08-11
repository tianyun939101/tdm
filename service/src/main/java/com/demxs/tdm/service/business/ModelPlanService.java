package com.demxs.tdm.service.business;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.dao.OfficeDao;
import com.demxs.tdm.common.sys.dao.UserDao;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.business.ModelPlanDao;
import com.demxs.tdm.domain.business.ModelPlan;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = false, rollbackFor = ServiceException.class)
public class ModelPlanService extends CrudService<ModelPlanDao, ModelPlan> {
    @Autowired
    private ModelPlanDao modelPlanDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private OfficeDao officeDao;
    //根据部门ID获取其父子级数据
    public Page<ModelPlan> getOneDataByDeptId(Page<ModelPlan> page, ModelPlan entity) {
        List<ModelPlan> rslist=new ArrayList<>();
        entity.setPage(page);
        List<ModelPlan> depts = modelPlanDao.findList(entity);//查询当前部门数据库中所有父子级记录
        //先将所有一级部门添加至rslist
        for(ModelPlan model:depts){
            //获取责任人ID
            String liableUserId = model.getLiableUser();
            User user = userDao.get(liableUserId);
            if(user!=null){
                model.setLiableUserMap(user);
            }
            if(model.getParentId()==null){      //如果父级为空，则为顶级部门
                rslist.add(model);
            }
        }

        // 为父级部门设置子部门，getChild是递归调用的
        for (ModelPlan plan : rslist) {
            //传入父级部门Id,以及所有查询结果
            plan.setChildren(getChild(plan.getId(), depts));
        }
        page.setList(rslist);
        return page;
    }

    public List<ModelPlan> getDataByDeptId(ModelPlan entity) {
        List<ModelPlan> rslist=new ArrayList<>();
            List<ModelPlan> depts = modelPlanDao.findList(entity);//查询当前部门数据库中所有父子级记录
          /*for(ModelPlan modelPlan1 : depts){

          if(StringUtils.isNotBlank(modelPlan1.getUserOutorg())){
                modelPlan1.setUserOutorg(officeDao.get(modelPlan1.getUserOutorg()).getName());
            }
        }*/
        //先将所有一级部门添加至rslist
        for(ModelPlan model:depts){
            switch(model.getStatus()){
                case "0": model.setStatus("已完成");break;
                case "1": model.setStatus("执行中");break;
                case "2": model.setStatus("已响应");break;
                case "3": model.setStatus("已发布");break;
            }
            String liableUserId = model.getLiableUser();
            User user = userDao.get(liableUserId);
            if(user!=null){
                model.setLiableUser(user.getName());
            }
            if(model.getParentId()==null ){      //如果父级为空，则为顶级部门
                rslist.add(model);
            }
        }

        // 为父级部门设置子部门，getChild是递归调用的
        for (ModelPlan plan : rslist) {
            //传入父级部门Id,以及所有查询结果
            plan.setChildren(getChild(plan.getId(), depts));
        }
        if(CollectionUtils.isEmpty(rslist) && CollectionUtils.isNotEmpty(depts)){
            return depts;
        }
        return rslist;
    }

    //传进父级ID,查找其子部门
    public List<ModelPlan> getChild(String id,List<ModelPlan> depts) {

        List<ModelPlan> childList = new ArrayList<>();

        for (ModelPlan model : depts) {
            // 遍历所有属于父级节点的子节点
            if (StringUtils.isNotBlank(model.getParentId())) {
                if (model.getParentId().equals(id)) {
                    String liableUserId = model.getLiableUser();
                    User user = userDao.get(liableUserId);
                    if(user!=null){
                        model.setLiableUserMap(user);
                    }
                    childList.add(model);
                }
            }
        }

        //然后给所有子节点添加子节点
        for (ModelPlan childmodel : childList) {

            // 遍历所有属于父级节点的子节点
            childmodel.setChildren(getChild(childmodel.getId(), depts));
        }
        // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }


    public void saveReName(ModelPlan modelPlan){
        modelPlanDao.saveReName(modelPlan);
    }
    public  List<ModelPlan> findReName(String y2){
        return modelPlanDao.findReName(y2);

    }
    public void deleteReName(ModelPlan modelPlan){
        modelPlanDao.deleteReName(modelPlan);
    }
    public ModelPlan getReName(String id){
       return modelPlanDao.getReName(id);
    }

    public void deleteOrder(String workId){
         modelPlanDao.delete(workId);
    }
}
