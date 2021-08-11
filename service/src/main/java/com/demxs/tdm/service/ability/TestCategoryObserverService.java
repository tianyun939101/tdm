package com.demxs.tdm.service.ability;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.ability.TestCategoryDao;
import com.demxs.tdm.dao.ability.TestCategoryObserverDao;
import com.demxs.tdm.domain.ability.TestCategory;
import com.demxs.tdm.domain.ability.TestCategoryObserver;
import com.demxs.tdm.service.oa.service.ActTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author: Jason
 * @Date: 2020/9/1 14:58
 * @Description:
 */
@Service
@Transactional(readOnly = false,rollbackFor = ServiceException.class)
public class TestCategoryObserverService extends CrudService<TestCategoryObserverDao, TestCategoryObserver> {

    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private TestCategoryDao categoryDao;

    @Override
    public void save(TestCategoryObserver observer) {
        List<TestCategoryObserver> list = this.dao.findList(observer.setUserId(UserUtils.getUser().getId()));
        if(null != list){
            TestCategory category = categoryDao.get(observer.getcId());
            if(null != category && StringUtils.isNotBlank(category.getParentIds())){
                for (TestCategoryObserver categoryObserver : list) {
                    if(category.getId().equals(categoryObserver.getcId())){
                        throw new ServiceException("已关注该节点！！请勿重复操作");
                    }
                    if(category.getParentIds().contains(categoryObserver.getcId())){
                        throw new ServiceException("已关注该父级节点：[" + category.getCode() + "]" + category.getName());
                    }
                }
            }
            User curUser = UserUtils.getUser();
            //查询该关注项全部子级
            List<TestCategory> childrenList = categoryDao.fuzzyQueryByParentId(category);
            //删除子级的关注项
            if (childrenList != null) {
                for (TestCategory children : childrenList) {
                    this.dao.deleteByUserIdAndCId(new TestCategoryObserver().setUserId(curUser.getId()).setcId(children.getId()));
                }
            }
        }
        observer.setUserId(UserUtils.getUser().getId());
        super.save(observer);
    }

    /**
    * @author Jason
    * @date 2020/9/2 10:46
    * @params [observer]
    * 关注试验能力树形结构
    * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    */
    public List<Map<String,Object>> observerTree(TestCategoryObserver observer){
        //以当前登陆人为基准查询关注项
        List<TestCategoryObserver> list = this.dao.findList(observer.setUserId(UserUtils.getUser().getId()));
        if(null != list){
            List<Map<String,Object>> tree = new ArrayList<>(100);
            for (TestCategoryObserver categoryObserver : list) {
                TestCategory category = categoryDao.get(categoryObserver.getcId());
                Map<String, Object> map = new HashMap<>(5);
                map.put("id", category.getId());
                map.put("pId", category.getParentId());
                map.put("parentIds", category.getParentIds());
                map.put("code", category.getCode());
                map.put("name", "[" + category.getCode() + "]" + category.getName());
                tree.add(map);
                //查询该关注项全部子级
                List<TestCategory> childrenList = categoryDao.fuzzyQueryByParentId(new TestCategory(categoryObserver.getcId()));
                if(null != childrenList){
                    for (TestCategory children : childrenList) {
                        Map<String, Object> childrenMap = new HashMap<>(5);
                        childrenMap.put("id", children.getId());
                        childrenMap.put("pId", children.getParentId());
                        childrenMap.put("parentIds", children.getParentIds());
                        childrenMap.put("code", children.getCode());
                        childrenMap.put("name", "[" + children.getCode() + "]" + children.getName());
                        tree.add(childrenMap);
                    }
                }
            }
            return tree;
        }
        return null;
    }

    public int deleteByUserIdAndCId(TestCategoryObserver observer){
        return this.dao.deleteByUserIdAndCId(observer);
    }
}
