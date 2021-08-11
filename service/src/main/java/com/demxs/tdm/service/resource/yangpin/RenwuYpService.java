package com.demxs.tdm.service.resource.yangpin;

import com.demxs.tdm.dao.resource.yangpin.DiaoduYpDao;
import com.demxs.tdm.dao.resource.yangpin.RenwuYpDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.yangpin.DiaoduYp;
import com.demxs.tdm.domain.resource.yangpin.RenwuYp;
import com.demxs.tdm.domain.resource.yangpin.Yangpin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 任务关联样品组Service
 * @author 詹小梅
 * @version 2017-06-22
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class RenwuYpService extends CrudService<RenwuYpDao, RenwuYp> {
	@Autowired
	private DiaoduYpDao diaoduYpDao;
	@Autowired
	private YangpinService yangpinService;
	private final String yangpinxlh = "1";
	private final String yangpinzt = "0";

	@Override
	public RenwuYp get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<RenwuYp> findList(RenwuYp renwuYp) {
		return super.findList(renwuYp);
	}
	
	@Override
	public Page<RenwuYp> findPage(Page<RenwuYp> page, RenwuYp renwuYp) {
		return super.findPage(page, renwuYp);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(RenwuYp renwuYp) {
		super.save(renwuYp);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(RenwuYp renwuYp) {
		super.delete(renwuYp);
	}
	/**
	 * 获取调度样品组（剩余数量大于0）--分配任务时
	 * @param fangfazj  方法主键
	 * @param diaoduzj 调度主键
	 * @return
	 */
	public List<DiaoduYp> findDiaoduyp(String diaoduzj, String fangfazj){
		DiaoduYp entity = new DiaoduYp();
		entity.setDiaoduzj(diaoduzj);
		entity.setFangfazj(fangfazj);
		entity.setYangpinsysl("0");
		return this.diaoduYpDao.findList(entity);
	}

	/**
	 * 获取任务关联样品组
	 * @param diaoduzj
	 * @param renwuzj
	 * @return
	 */
	public List<RenwuYp> findRenwuyp(String diaoduzj, String renwuzj){
		RenwuYp entity = new RenwuYp();
		entity.setDiaoduzj(diaoduzj);
		entity.setRenwuzj(renwuzj);
		return super.findList(entity);
	}

	/**
	 * 任务关联样品
	 * 调度样品组id（将这个表zy_diaodu_yp的id赋值给yangpinzid）
	 * @param renwuYps
	 */
	public void saveRenwYpz(List<RenwuYp> renwuYps,String diaoduzj){
		updateRenwu(renwuYps);//先将任务样品组关联删除再还原调度样品组数量
		String renwuid = "";
		for(RenwuYp renwuyp:renwuYps){
//			renwuid= IdGen.uuid();
			String yangpinid =  renwuyp.getYangpinid();
			//获取调度样品组信息
			if(StringUtils.isNotBlank(yangpinid)){
				Yangpin ddEntity = yangpinService.get(yangpinid);
				if(ddEntity!=null){
					renwuyp.setId(null);
					renwuyp.setDiaoduzj(diaoduzj);//业务yw_diaodud 表id
					renwuyp.setYangpinid(yangpinid);//关联的样品ID
					renwuyp.setYangpinzid(renwuyp.getYangpinzid());
					renwuyp.setYangpinbh(ddEntity.getYangpinbh());
					renwuyp.setYangpinmc(ddEntity.getYangpinmc());
					renwuyp.setYangpinlb(ddEntity.getYangpinlb());
					renwuyp.setYangpinxh(ddEntity.getYangpinxh());
					renwuyp.setShifouly(ddEntity.getShifouly());
					renwuyp.setShifouxysyszy(ddEntity.getShifouxysyszy());
					renwuyp.setYangpinbz(ddEntity.getYangpinbz());
					renwuyp.setChushibm(ddEntity.getChushibm());
					renwuyp.setBianmaqz(ddEntity.getBianmaqz());
				}
				super.save(renwuyp);//保存
				//updateYangpinsl(renwuyp.getYangpinzid(),ddEntity != null ? ddEntity.getShuliang():"0");//更新调度关联样品组数量
			}
		}
	}


	/**
	 * 重新关联任务样品组时有两个操作
	 * 1：删掉历史关联关系(根据调度id删除)
	 * 2：还原调度样品组剩余数量（还原为样品组初始数量）
	 *  @param renwuYps
	 */
	public void updateRenwu(List<RenwuYp> renwuYps){
		if(renwuYps.size()>0){
			if(renwuYps.get(0)!=null){
				this.dao.deleteRenwuYpz(renwuYps.get(0).getDiaoduzj());//删除
//				diaoduYpDao.restoreSl(renwuYps.get(0).getYangpinzid());//还原（调度样品组表主键）
			}
		}
	}

	/**
	 * 删除任务样品组关联
	 * @param renwuzjs
	 */
	public void deleteRenwuYpz(List<RenwuYp> renwuzjs){
		if(renwuzjs.size()>0){
			if(renwuzjs.get(0)!=null){
				this.dao.deleteRenwuYpz(renwuzjs.get(0).getWeituodzj());
			}
		}
	}

	/**
	 * 更新样品数量，剩余数量
	 * @param yangpinzid 样品组id
	 * @param shuliang 分配任务时更新后数量
	 */
	public void updateYangpinsl(String yangpinzid,String shuliang){
		this.diaoduYpDao.updateYangpinsl(yangpinzid,shuliang);
	}



	/**
	 * 样品组出库后更新任务状态为【1：待执行】
	 * @param renwuzj
	 */
	public void updateRenwuzt(String renwuzj){
		this.dao.updateRenwuzt(renwuzj);
	}


}