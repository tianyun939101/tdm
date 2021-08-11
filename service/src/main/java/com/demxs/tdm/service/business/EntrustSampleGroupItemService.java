package com.demxs.tdm.service.business;

import com.demxs.tdm.dao.business.EntrustSampleGroupItemDao;
import com.demxs.tdm.service.resource.sample.SampleLocationService;
import com.demxs.tdm.service.resource.sample.SampleOperateService;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.business.EntrustSampleGroupItem;
import com.demxs.tdm.domain.resource.sample.SampleOperate;
import com.demxs.tdm.comac.common.constant.SampleConstants;
import com.demxs.tdm.service.sys.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 申请试验组中样品组的样品信息Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class EntrustSampleGroupItemService extends CrudService<EntrustSampleGroupItemDao, EntrustSampleGroupItem> {

	@Autowired
	private SampleOperateService sampleOperateService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private SampleLocationService sampleLocationService;
	@Override
	public EntrustSampleGroupItem get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<EntrustSampleGroupItem> findList(EntrustSampleGroupItem entrustSampleGroupItem) {
		return super.findList(entrustSampleGroupItem);
	}
	
	@Override
	public Page<EntrustSampleGroupItem> findPage(Page<EntrustSampleGroupItem> page, EntrustSampleGroupItem entrustSampleGroupItem) {
		entrustSampleGroupItem.getSqlMap().put("dsf", dataScopeFilter(entrustSampleGroupItem.getCurrentUser(), "o8", "u8"));
		return super.findPage(page, entrustSampleGroupItem);
	}

	public Page<EntrustSampleGroupItem> printPage(Page<EntrustSampleGroupItem> page, EntrustSampleGroupItem entrustSampleGroupItem) {

		entrustSampleGroupItem.getSqlMap().put("dsf", dataScopeFilter(entrustSampleGroupItem.getCurrentUser(), "o8", "u8"));
		entrustSampleGroupItem.setPage(page);
		page.setList(dao.printPage(entrustSampleGroupItem));
		return page;
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(EntrustSampleGroupItem entrustSampleGroupItem) {
		if(entrustSampleGroupItem.getSampleStatus()==null){
			entrustSampleGroupItem.setSampleStatus(SampleConstants.sampleStatus.NO_IN);
		}
		super.save(entrustSampleGroupItem);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(EntrustSampleGroupItem entrustSampleGroupItem) {
		super.delete(entrustSampleGroupItem);
	}

	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void deleteByEntrustId(String entrustId) throws ServiceException{
		this.dao.deleteByEntrustId(entrustId);
	}

	public List<EntrustSampleGroupItem> findByIds(String ids){
		return this.dao.joinCollectionByIds(ids);
	}


	/**
	 * 样品入库
	 * @param s
	 */
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void storage(EntrustSampleGroupItem s){
		//样品入库
		this.dao.updateSampleStatus(s.getId(), SampleConstants.sampleStatus.WAIT_INSPECTION,s.getSampleLocation().getId(),new Date(),s.getOperater(),new Date());

		EntrustSampleGroupItem sampleinfo = get(s.getId());
		SampleOperate sampleOperate = SampleOperate.copySampleOperate(sampleinfo.getId(),sampleinfo.getSn(),sampleinfo.getNo(),sampleinfo.getName(),
				sampleinfo.getOperater().getId(),sampleinfo.getOperater().getName(),sampleinfo.getEntrustId(),
				sampleinfo.getEntrustInfo().getCode(),sampleinfo.getEntrustInfo().getOrg()==null?null:sampleinfo.getEntrustInfo().getOrg().getId(),sampleinfo.getEntrustInfo().getOrg()==null?null:sampleinfo.getEntrustInfo().getOrg().getName(),
				sampleinfo.getEntrustInfo().getUser().getId(),sampleinfo.getEntrustInfo().getUser().getName(),SampleConstants.sampleOperateType.STORAGE,
				null,null,null,sampleinfo.getSampleLocation().getId(),sampleinfo.getSampleLocation().getName(),sampleinfo.getLabInfoId(),sampleinfo.getLabInfoName()
		);
		//保存流转记录
		sampleOperateService.save(sampleOperate);

	}

	/**
	 * 样品出库
	 * @param ids
	 * @param operater
	 */
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void outBound(String ids, User operater){

		if(StringUtils.isNotBlank(ids)){
			String[] idArr = ids.split(",");
			for(String id:idArr){
				EntrustSampleGroupItem sampleinfo = get(id);
				this.dao.updateSampleStatus(sampleinfo.getId(),SampleConstants.sampleStatus.IN_INSPECTION,null,null,null,new Date());
				//保存出库记录
				SampleOperate sampleOperate = SampleOperate.copySampleOperate(sampleinfo.getId(),sampleinfo.getSn(),sampleinfo.getNo(),sampleinfo.getName(),
						operater.getId(),systemService.getUser(operater.getId()).getName(),sampleinfo.getEntrustId(),
						sampleinfo.getEntrustInfo().getCode(),sampleinfo.getEntrustInfo().getOrg().getId(),sampleinfo.getEntrustInfo().getOrg().getName(),
						sampleinfo.getEntrustInfo().getUser().getId(),sampleinfo.getEntrustInfo().getUser().getName(),SampleConstants.sampleOperateType.OUTCHECK,
						null,null,null,null,null,sampleinfo.getLabInfoId(),sampleinfo.getLabInfoName()
				);

				sampleOperateService.save(sampleOperate);
			}
		}
	}


	/**
	 * 样品返库(检毕入库)
	 * @param ids
	 * @param operater
	 */
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void sampleOut(String ids, User operater,String sampleLocation){

		if(StringUtils.isNotBlank(ids)){
			String[] idArr = ids.split(",");
			for(String id:idArr){
				EntrustSampleGroupItem sampleinfo = get(id);
				//样品状态更改
				this.dao.updateSampleStatus(id,SampleConstants.sampleStatus.CHECKED,sampleLocation,null,null,new Date());
				//保存反库记录
				SampleOperate sampleOperate = SampleOperate.copySampleOperate(sampleinfo.getId(),sampleinfo.getSn(),sampleinfo.getNo(),sampleinfo.getName(),
						operater.getId(),systemService.getUser(operater.getId()).getName(),sampleinfo.getEntrustId(),
						sampleinfo.getEntrustInfo().getCode(),sampleinfo.getEntrustInfo().getOrg().getId(),sampleinfo.getEntrustInfo().getOrg().getName(),
						sampleinfo.getEntrustInfo().getUser().getId(),sampleinfo.getEntrustInfo().getUser().getName(),SampleConstants.sampleOperateType.INCHECK,
						null,null,null,sampleLocation,sampleLocationService.get(sampleLocation).getName(),sampleinfo.getLabInfoId(),sampleinfo.getLabInfoName()
				);
				sampleOperateService.save(sampleOperate);
			}
		}

	}


	/**
	 * 样品归还
	 * @param ids
	 * @param operater
	 */
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void sampleReturn(String ids, User operater){

		if(StringUtils.isNotBlank(ids)){
			String[] idArr = ids.split(",");
			for(String id:idArr){
				EntrustSampleGroupItem sampleinfo = get(id);
				//样品状态更改成返还
				this.dao.updateSampleStatus(id,SampleConstants.sampleStatus.RETURN,null,null,null,new Date());
				//保存返还记录
				SampleOperate sampleOperate = SampleOperate.copySampleOperate(sampleinfo.getId(),sampleinfo.getSn(),sampleinfo.getNo(),sampleinfo.getName(),
						operater.getId(),systemService.getUser(operater.getId()).getName(),sampleinfo.getEntrustInfo().getId(),
						sampleinfo.getEntrustInfo().getCode(),sampleinfo.getEntrustInfo().getOrg().getId(),sampleinfo.getEntrustInfo().getOrg().getName(),
						sampleinfo.getEntrustInfo().getUser().getId(),sampleinfo.getEntrustInfo().getUser().getName(),SampleConstants.sampleOperateType.RETURNED,
						null,null,null,null,null,sampleinfo.getLabInfoId(),sampleinfo.getLabInfoName()
				);

				sampleOperateService.save(sampleOperate);
			}
		}

	}

	/**
	 * 试验室处理
	 * @param ids
	 */
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void sampleLabHandling(String ids){
		if(StringUtils.isNotBlank(ids)){
			String[] idArr = ids.split(",");
			for(String id:idArr){
				EntrustSampleGroupItem sampleinfo = get(id);
				//样品状态更改成返还
				this.dao.updateSampleStatus(id,SampleConstants.sampleStatus.SCRAPPING,null,null,null,new Date());
				//保存记录
				//保存返还记录
				SampleOperate sampleOperate = SampleOperate.copySampleOperate(sampleinfo.getId(),sampleinfo.getSn(),sampleinfo.getNo(),sampleinfo.getName(),
						null,null,sampleinfo.getEntrustInfo().getId(),
						sampleinfo.getEntrustInfo().getCode(),sampleinfo.getEntrustInfo().getOrg().getId(),sampleinfo.getEntrustInfo().getOrg().getName(),
						sampleinfo.getEntrustInfo().getUser().getId(),sampleinfo.getEntrustInfo().getUser().getName(),SampleConstants.sampleOperateType.LABHANDLING,
						null,null,null,null,null,sampleinfo.getLabInfoId(),sampleinfo.getLabInfoName()
				);
				sampleOperateService.save(sampleOperate);
			}
		}

	}

}