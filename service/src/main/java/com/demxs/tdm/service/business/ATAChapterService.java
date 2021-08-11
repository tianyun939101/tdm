package com.demxs.tdm.service.business;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.service.TreeService;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.business.ATAChapterDao;
import com.demxs.tdm.domain.business.ATAChapter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ATAChapterService extends TreeService<ATAChapterDao, ATAChapter> {

    @Override
    public ATAChapter get(String id) {
        ATAChapter ataChapter = super.dao.get(id);
        if(ataChapter.getParent() != null &&
                StringUtils.isNotBlank(ataChapter.getParent().getId())){
            ataChapter.setParent(super.dao.get(ataChapter.getParent().getId()));
        }
        return ataChapter;
    }

    @Override
    public Page<ATAChapter> findPage(Page<ATAChapter> page, ATAChapter ataChapter) {
        ataChapter.getSqlMap().put("dsf", dataScopeFilter(ataChapter.getCurrentUser(), "o", "u8"));
        Page<ATAChapter> ATAPage = super.findPage(page, ataChapter);
        return ATAPage;
    }

    @Override
    public List<ATAChapter> findList(ATAChapter entity) {
        if (StringUtils.isNotBlank(entity.getParentIds())){
            entity.setParentIds(","+entity.getParentIds()+",");
        }
        return super.findList(entity);
    }

    public List<ATAChapter> findATAList(ATAChapter entity) {

        return this.dao.findATAList(entity);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = ServiceException.class)
    public void save(ATAChapter ataChapter) {
        if(StringUtils.isNotBlank(ataChapter.getParent().getId())){
            ataChapter.setParentId(ataChapter.getParent().getId());
        }
        super.save(ataChapter);
    }


    /**
     * @author Jason
     * @date 2020/7/2 14:51
     * @params [list, parentId]
     * 获取父级名称
     * @return java.util.List<java.lang.String>
     */
    public List<String> getParentName(List<String> list,String parentId){
        ATAChapter ataChapter = this.get(parentId);
        if(null == ataChapter){
            return list;
        }else{
            list.add(ataChapter.getName());
            if(null == ataChapter.getParent() || null == ataChapter.getParentId()){
                return list;
            }
            return getParentName(list,ataChapter.getParentId());
        }
    }
}
