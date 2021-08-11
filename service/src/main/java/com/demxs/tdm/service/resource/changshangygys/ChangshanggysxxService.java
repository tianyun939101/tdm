package com.demxs.tdm.service.resource.changshangygys;

import com.demxs.tdm.dao.resource.changshangygys.ChangshanggysxxDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.dao.lab.LabInfoDao;
import com.demxs.tdm.domain.lab.LabInfo;
import com.demxs.tdm.domain.resource.changshangygys.Changshanggysxx;
import com.demxs.tdm.service.resource.fujian.AttachmentService;
import com.demxs.tdm.service.sys.DictService;
import com.github.ltsopensource.core.commons.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 厂商、供应商信息Service
 * @author zhangdengcai
 * @version 2017-06-10
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ChangshanggysxxService extends CrudService<ChangshanggysxxDao, Changshanggysxx> {

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private DictService dictService;
	@Autowired
	private LabInfoDao labInfoDao;


	@Override
	public Changshanggysxx get(String id) {
		return super.get(id);
	}

	@Override
	public List<Changshanggysxx> findList(Changshanggysxx changshanggysxx) {
		return super.findList(changshanggysxx);
	}

	/**
	 * 分页查询列表
	 * @param page
	 * @param changshanggysxx
	 * @return
	 */
	@Override
	public Page<Changshanggysxx> findPage(Page<Changshanggysxx> page, Changshanggysxx changshanggysxx) {
//		if(UserUtils.getUser()!=null){
//			changshanggysxx.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
//		}
		changshanggysxx.getSqlMap().put("dsf", dataScopeFilter(changshanggysxx.getCurrentUser(), "o", "u"));
		changshanggysxx.setDangqianR(UserUtils.getUser());
		Page<Changshanggysxx> dataValue = super.findPage(page, changshanggysxx);
		if(dataValue!=null && CollectionUtils.isNotEmpty(dataValue.getList())){
			for(Changshanggysxx c:dataValue.getList()){
				c.setLabInfo(labInfoDao.get(c.getLabInfoId()));
			}
		}
		return dataValue;
	}

	/**
	 * 分页查询列表
	 * @param page
	 * @param changshanggysxx
	 * @return
	 */
	public Page<Changshanggysxx> findPageForOther(Page<Changshanggysxx> page, Changshanggysxx changshanggysxx) {
		page.setOrderBy("a.update_date desc");
//		if(UserUtils.getUser()!=null){
//			changshanggysxx.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
//		}
		return super.findPage(page, changshanggysxx);
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(Changshanggysxx changshanggysxx)
	{
		//changshanggysxx.setLabInfoId(changshanggysxx.getLabInfo().getId());

		LabInfo l=labInfoDao.getBaseInfo(changshanggysxx.getLabInfoId());
		//changshanggysxx.setLabInfoName(l.getName());
		super.save(changshanggysxx);
	}


	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(Changshanggysxx changshanggysxx) {
		super.delete(changshanggysxx);
	}


}