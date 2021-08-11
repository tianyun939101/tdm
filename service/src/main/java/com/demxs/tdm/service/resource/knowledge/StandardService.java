package com.demxs.tdm.service.resource.knowledge;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.service.TreeService;
import com.demxs.tdm.dao.resource.knowledge.StandardDao;
import com.demxs.tdm.domain.resource.kowledge.Standard;
import com.sun.tools.doclets.formats.html.resources.standard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class StandardService extends TreeService<StandardDao, Standard> {
    @Autowired
    StandardDao standardDao;

    List<Standard> standardList = new ArrayList<Standard>();
    public Page<Standard> find(Page<Standard> page, Standard standard) {
       if(standard.getParent()!=null){
           String id = standard.getParent().getId();
           standard.setParentIds(id);
       }
        Page<Standard> page1 = super.findPage(page, standard);
        return page1;
    }

    public Page<Standard>  findListParent(Standard standard){

        List<Standard> listParent = standardDao.findListParent(standard);
        Page<Standard> standardPage = new Page<>();
        standardPage.setList(listParent);
        return standardPage;
    }





    //目录
    public List<Standard> menu(String parentId){
        //目录结构
        List<List<Standard>> list = new ArrayList<>();
        //获取目录
        List<Standard> byParentId = standardDao.findByParentIdMenu(parentId,"t");
        List<Standard> childdata = childdata(parentId);
        list.add(childdata);
        //遍历目录
        // 1、查询出数据

            for(Standard standard1 : byParentId){
                list.add(childdata(standard1.getId()));
                menu(standard1.getId());
            }
        for( List<Standard> menu : list){
            for(Standard standard1: menu){
                standardList.add(standard1);
            }
        }
        //递归头
        if(byParentId.size() == 0){
            return null;
        }
        return standardList;
    }

    //初始数据，点击层级
    public List<Standard> data(Standard standard){
        standard.setType("s");
        List<Standard> list = standardDao.findList(standard);
        return list;
    }
    //子级数据
    public List<Standard> childdata(String parentId){
        List<Standard> byParentId = standardDao.findByParentIdMenu(parentId,"s");
        return byParentId;
    }

    public void changeName(String name, String id){
        standardDao.changeName(id,name);
    }
}




