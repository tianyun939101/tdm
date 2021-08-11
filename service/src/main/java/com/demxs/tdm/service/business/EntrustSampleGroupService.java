package com.demxs.tdm.service.business;

import com.demxs.tdm.dao.business.EntrustInfoDao;
import com.demxs.tdm.dao.business.EntrustSampleGroupDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.Collections3;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.business.EntrustInfo;
import com.demxs.tdm.domain.business.EntrustSampleGroup;
import com.demxs.tdm.domain.business.EntrustSampleGroupItem;
import com.demxs.tdm.domain.resource.sample.SampleLocation;
import com.demxs.tdm.comac.common.constant.SampleConstants;
import com.demxs.tdm.service.sys.SystemService;
import com.github.ltsopensource.core.commons.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 申请试验组中的样品组信息Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class EntrustSampleGroupService extends CrudService<EntrustSampleGroupDao, EntrustSampleGroup> {

	@Autowired
	private EntrustSampleGroupItemService entrustSampleGroupItemService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private EntrustInfoDao entrustInfoDao;
	@Override
	public EntrustSampleGroup get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<EntrustSampleGroup> findList(EntrustSampleGroup entrustSampleGroup) {
		return super.findList(entrustSampleGroup);
	}
	
	@Override
	public Page<EntrustSampleGroup> findPage(Page<EntrustSampleGroup> page, EntrustSampleGroup entrustSampleGroup) {
		return super.findPage(page, entrustSampleGroup);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(EntrustSampleGroup entrustSampleGroup) {
		if (entrustSampleGroup.getSampleStatus()==null) {
			entrustSampleGroup.setSampleStatus(EntrustInfo.NOTPUTIN);
		}
		super.save(entrustSampleGroup);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(EntrustSampleGroup entrustSampleGroup) {
		super.delete(entrustSampleGroup);
	}

	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void deleteByEntrustId(String entrustId) throws ServiceException{
		this.dao.deleteByEntrustId(entrustId);
	}

	/**
	 * 根据试验组ID加载样品组数据
	 * @param testGroupId 试验组ID
	 * @return
	 */
	public List<EntrustSampleGroup> listByTestGroupId(String testGroupId) throws ServiceException{
		return this.dao.listByTestGroupId(testGroupId);
	}

	/**
	 * 根据申请ID获取样品组数据
	 * @param entrustId 申请单ID
     */
	public List<EntrustSampleGroup> listByEntrustId(String entrustId) throws ServiceException{
		return this.dao.listByEntrustId(entrustId);
	}

	/**
	 * 样品组入库
	 * @param sampleGroup
	 */
	public void storageGoup(EntrustSampleGroup sampleGroup){
		//样品组入库
		String sampleIds = sampleGroup.getSampleIds();
		String sampleLocationIds = sampleGroup.getSampleLocationIds();
		if(StringUtils.isNotBlank(sampleIds)){
			String[] sampleIdArr = sampleIds.split(",");
			String[] sampleLocationArr = sampleLocationIds.split(",");
			for(int i=0;i<sampleIdArr.length;i++){
				//修改入库状态 存放位置
				this.dao.updateStatus(sampleIdArr[i], SampleConstants.sampleStatus.WAIT_INSPECTION);
				//获取这个样品组下面的所有样品进行入库动作
				EntrustSampleGroupItem groupItem = new EntrustSampleGroupItem();
				groupItem.setSGroupId(sampleIdArr[i]);
				List<EntrustSampleGroupItem> sampleinfos = entrustSampleGroupItemService.findList(groupItem);

				if(!Collections3.isEmpty(sampleinfos)){
					for(EntrustSampleGroupItem s:sampleinfos){
						s.setSampleLocation(new SampleLocation(sampleLocationArr[i]));
						s.setOperater(systemService.getUser(sampleGroup.getOperater().getId()));
						s.setEntrustId(s.getEntrustId());
						entrustSampleGroupItemService.storage(s);
					}
				}
			}


		}
		//判断该申请单下面还有没有样品组入库 判断申请单要不要入库

		List<EntrustSampleGroup> groupList = this.dao.listBySampleStatus(sampleGroup.getEntrustId(), EntrustInfo.NOTPUTIN);
		if(CollectionUtils.isNotEmpty(groupList)){

		}else{
			//更改该申请单的状态
			entrustInfoDao.storageEntrust(sampleGroup.getEntrustId(),EntrustInfo.PUTIN);
		}
	}
}