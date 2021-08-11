package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.service.TreeService;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.*;
import com.demxs.tdm.dao.business.instrument.ApparatusLocationDao;
import com.demxs.tdm.dao.dataCenter.ZyLabInfoDao;
import com.demxs.tdm.dao.dataCenter.ZyLabPersonDao;
import com.demxs.tdm.dao.dataCenter.ZyLabTionDao;
import com.demxs.tdm.domain.business.instrument.ApparatusLocation;
import com.demxs.tdm.domain.dataCenter.ZyLabInfo;
import com.demxs.tdm.domain.dataCenter.ZyLabPerson;
import com.demxs.tdm.domain.dataCenter.ZyLabTion;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/5/6 16:57
 * @Description:
 */
@Service
@Transactional(readOnly = false,rollbackFor = ServiceException.class)
public class ZyLabInfoService extends TreeService<ZyLabInfoDao, ZyLabInfo> {

    @Autowired
    private ZyLabPersonDao   zyLabPersonDao;

    @Autowired
    private ZyLabInfoDao   zyLabInfoDao;

    @Autowired
    private ZyLabTionService  zyLabTionService;

    @Override
    public List<ZyLabInfo> findList(ZyLabInfo entity) {
        if (StringUtils.isNotBlank(entity.getParentIds())){
            entity.setParentIds(","+entity.getParentIds()+",");
        }
        return super.findList(entity);
    }

    @Override
    public ZyLabInfo get(String id) {
        ZyLabInfo c = this.dao.get(id);
        if(c != null && c.getParent()!=null && StringUtils.isNotBlank(c.getParent().getId())){
            c.setParent(this.dao.get(c.getParentId()));
        }
        return c;
    }

    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void save(ZyLabInfo entity) {
        Class<ZyLabInfo> entityClass = Reflections.getClassGenricType(getClass(), 1);

        // 如果没有设置父节点，则代表为跟节点，有则获取父节点实体
        if (entity.getParent() == null || StringUtils.isBlank(entity.getParentId())
                || "0".equals(entity.getParentId())){
            entity.setParent(null);
        }else{
            entity.setParent(super.get(entity.getParentId()));
        }
        if (entity.getParent() == null){
            ZyLabInfo parentEntity = null;
            try {
                parentEntity = entityClass.getConstructor(String.class).newInstance("0");
            } catch (Exception e) {
                throw new ServiceException(e);
            }
            entity.setParent(parentEntity);
            entity.getParent().setParentIds(StringUtils.EMPTY);
        }

        // 获取修改前的parentIds，用于更新子节点的parentIds
        String oldParentIds = entity.getParentIds();

        // 设置新的父节点串
        entity.setParentIds(entity.getParent().getParentIds()+entity.getParent().getId()+",");

        // 保存或更新实体
       //  super.save(entity);
        User user = UserUtils.getUser();
        Date d=new Date();
        ZyLabInfo  ziList=zyLabInfoDao.get(entity.getId());
        if(entity.getParentIds().equals("0,")){
            List<ZyLabInfo> list=zyLabInfoDao.findByParent("0,");
            if(CollectionUtils.isNotEmpty(list)){
                String company=list.get(0).getCompany();
                entity.setCompany(String.valueOf(Integer.parseInt(company)+1));
            }else{
                entity.setCompany("1");
            }
        }else{
            ZyLabInfo  s=new ZyLabInfo();
            s.setId(entity.getParent().getId());
            List<ZyLabInfo> list=zyLabInfoDao.findList(s);
            if(CollectionUtils.isNotEmpty(list)){
                String company=list.get(0).getCompany();
                entity.setCompany(company);
            }
        }
        if(ziList ==null){
            entity.setCreateBy(user);
            entity.setUpdateBy(user);
            entity.setCreateDate(d);
            entity.setUpdateDate(d);
            zyLabInfoDao.insert(entity);
        }else{
            entity.setUpdateBy(user);
            entity.setUpdateDate(d);
            zyLabInfoDao.update(entity);
        }

        if(StringUtils.isNotEmpty(entity.getParentInfo())){
            ZyLabTion  zt=new ZyLabTion();
            zt.setLabId(entity.getParentInfo());
            zt.setLeafId(entity.getId());
            zt.setDelFlag("0");
            List<ZyLabTion>  zyLabTionList=zyLabTionService.findList(zt);
            if(CollectionUtils.isNotEmpty(zyLabTionList)){
                zt.setName(entity.getName());
                zt.setIsFlag(entity.getIsFlag());
                zt.setId(zyLabTionList.get(0).getId());
            }else{
                zt.setName(entity.getName());
                zt.setIsFlag(entity.getIsFlag());
            }
            zyLabTionService.save(zt);
        }

        // 更新子节点 parentIds
        ZyLabInfo o = null;
        try {
            o = entityClass.newInstance();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        o.setParentIds("%,"+entity.getId()+",%");
        List<ZyLabInfo> list = zyLabInfoDao.findByParentIdsLike(o);
        for (ZyLabInfo e : list){
            if (e.getParentIds() != null && oldParentIds != null){
                e.setParentIds(e.getParentIds().replace(oldParentIds, entity.getParentIds()));
                preUpdateChild(entity, e);
                zyLabInfoDao.updateParentIds(e);
            }
        }
       // super.save(entity);
        /*ZyLabPerson  zp=new ZyLabPerson();
        zp.setLabId(entity.getId());
        List<ZyLabPerson> list1=zyLabPersonDao.findList(zp);
        if(CollectionUtils.isNotEmpty(list1)){
            for(ZyLabPerson  zlp:list1){
                zyLabPersonDao.deleteInfo(zlp);
            }
        }
        if(CollectionUtils.isNotEmpty(entity.getZyLabPersonList())){
            for(ZyLabPerson  z:entity.getZyLabPersonList()){
                z.setLabId(entity.getId());
                zyLabPersonDao.save(z);
            }
        }*/
    }

    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void delete(ZyLabInfo entity) {
        super.delete(entity);

    }


    public  ZyLabInfo  getZyLabInfo(ZyLabInfo  zyLabInfo){

        if(StringUtils.isNotEmpty(zyLabInfo.getId())){
            zyLabInfo=zyLabInfoDao.get(zyLabInfo.getId());

        }else{
            //根节点
            zyLabInfo.setId("0");
            zyLabInfo.setParent(zyLabInfo);
            zyLabInfo.setCompany(zyLabInfo.getCompany());
            List<ZyLabInfo> list=zyLabInfoDao.findList(zyLabInfo);
            if(CollectionUtils.isNotEmpty(list)){
                zyLabInfo=list.get(0);
            }

        }
        ZyLabPerson   zp=new  ZyLabPerson();
        ZyLabTion     zt=new ZyLabTion();
        zp.setLabId(zyLabInfo.getId());
        zt.setLabId(zyLabInfo.getId());
        List<ZyLabPerson>  personList=zyLabPersonDao.findList(zp);
        List<ZyLabTion>    labTIonList=zyLabTionService.findList(zt);
        zyLabInfo.setZyLabPersonList(personList);
        zyLabInfo.setZyLabTionList(labTIonList);

        return zyLabInfo;
    }

}
