package com.demxs.tdm.service.business;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.dao.OfficeDao;
import com.demxs.tdm.common.sys.dao.UserDao;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.business.PlanDao;
import com.demxs.tdm.domain.business.ModelPlan;
import com.demxs.tdm.domain.business.Plan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class PlanService extends CrudService<PlanDao, Plan> {

    @Autowired
    PlanDao planDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private OfficeDao officeDao;
    //根据部门ID获取其父子级数据
    public Page<Plan> getOneDataByDeptId(Page<Plan> page, Plan entity) {
        List<Plan> rslist=new ArrayList<>();
        entity.setPage(page);
        List<Plan> depts = planDao.findList(entity);//查询当前部门数据库中所有父子级记录
        //先将所有一级部门添加至rslist
        for(Plan model:depts){
            String liableUserId = model.getLiableUserId();
            User user = userDao.get(liableUserId);
            if(user!=null){
                model.setLiableUser(user);
            }
            if(model.getParentid()==null){      //如果父级为空，则为顶级部门
                rslist.add(model);
            }
        }

        // 为父级部门设置子部门，getChild是递归调用的
        for (Plan plan : rslist) {
            //传入父级部门Id,以及所有查询结果
            plan.setChildren(getChild(plan.getId(), depts));
        }
        page.setList(rslist);
        return page;
    }

    public List<Plan> getDataByDeptId(Plan entity) {
        List<Plan> rslist=new ArrayList<>();
        List<Plan> depts = planDao.findList(entity);//查询当前部门数据库中所有父子级记录
        //先将所有一级部门添加至rslist
        for(Plan model:depts){
            String liableUserId = model.getLiableUserId();
            User user = userDao.get(liableUserId);
            if(user!=null){
                model.setLiableUser(user);
            }

             if(model.getOffice() != null && StringUtils.isNotBlank(model.getOffice().getId())){
                 Office office = officeDao.get(model.getOffice().getId());
                 model.setOffice(office);
             }
            if(model.getParentid()==null){      //如果父级为空，则为顶级部门
                rslist.add(model);
            }
        }

        // 为父级部门设置子部门，getChild是递归调用的
        for (Plan plan : rslist) {
            //传入父级部门Id,以及所有查询结果
            plan.setChildren(getChild(plan.getId(), depts));
        }
        return rslist;
    }

    //传进父级ID,查找其子部门
    public List<Plan> getChild(String id,List<Plan> depts) {

        List<Plan> childList = new ArrayList<>();

        for (Plan model : depts) {
            // 遍历所有属于父级节点的子节点
            if (StringUtils.isNotBlank(model.getParentid())) {
                if (model.getParentid().equals(id + "")) {
                    String liableUserId = model.getLiableUserId();
                    User user = userDao.get(liableUserId);
                    if(user!=null){
                        model.setLiableUser(user);
                    }
                    childList.add(model);
                }
            }
        }

        //然后给所有子节点添加子节点
        for (Plan childmodel : childList) {

            // 遍历所有属于父级节点的子节点
            childmodel.setChildren(getChild(childmodel.getId(), depts));
        }
        // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }
    public void saveReName(Plan plan){
        planDao.saveReName(plan);
    }
    public  List<Plan> findReName(String y2){
        return planDao.findReName(y2);

    }
    public void deleteReName(Plan plan){
        planDao.deleteReName(plan);
    }
    public Plan getReName(String id){
        return planDao.getReName(id);
    }

    public void deleteOrder(String workId){
        planDao.delete(workId);
    }

}
