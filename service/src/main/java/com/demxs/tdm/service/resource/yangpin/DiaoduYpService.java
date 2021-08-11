package com.demxs.tdm.service.resource.yangpin;

import com.demxs.tdm.dao.resource.dto.YangPinDto;
import com.demxs.tdm.dao.resource.yangpin.DiaoduYpDao;
import com.demxs.tdm.dao.resource.yangpin.WeituoYpDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.yangpin.DiaoduYp;
import com.demxs.tdm.domain.resource.yangpin.WeituoYp;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 调度关联样品组Service
 * @author 詹小梅
 * @version 2017-06-22
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class DiaoduYpService extends CrudService<DiaoduYpDao, DiaoduYp> {
	@Autowired
	private WeituoYpDao weituoYpDao;
	@Autowired
	private YangpinService yangpinService;

	@Override
	public DiaoduYp get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<DiaoduYp> findList(DiaoduYp diaoduYp) {
		return super.findList(diaoduYp);
	}
	
	@Override
	public Page<DiaoduYp> findPage(Page<DiaoduYp> page, DiaoduYp diaoduYp) {
		return super.findPage(page, diaoduYp);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(DiaoduYp diaoduYp) {
		super.save(diaoduYp);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(DiaoduYp diaoduYp) {
		super.delete(diaoduYp);
	}

	/**
	 * @Author：詹小梅
	 * @param： * @param null
	 * @Description：样品确认后更新样品状态为【待入库】
	 * @Date：18:23 2017/6/22
	 * @Return：
	 * @Exception：
	 */
	public void yangpinqr(String weituodzj){
		//将申请单的样品组插入样品库，且样品状态为待入库
		this.yangpinService.plinsert( weituodzj);
	//	this.weituoYpDao.updateYangpinzt(weituodzj, YpConstans.optStatus.DAIRUKU);

	}

	/**
	 * 获取申请单样品组（剩余数量大于0）--调度时
	 * @param weituodzj 申请单主键
	 * @return
	 */
	public List<YangPinDto> findWeituosyyp(String weituodzj){
		WeituoYp entity = new WeituoYp();
		entity.setWeituodzj(weituodzj);
		return this.weituoYpDao.findWeituosyyp(entity);
	}

	/**
	 * 获取调度关联样品组
	 * @param weituodzj
	 * @param fangfazj
	 * @return
	 */
	public List<YangPinDto> findDiaoduyp(String fangfazj,String weituodzj){
		DiaoduYp entity = new DiaoduYp();
		entity.setWeituodzj(weituodzj);
		entity.setFangfazj(fangfazj);
		return this.dao.findDiaoduyp(entity);
	}

	/**
	 * 从申请单样品组-调度样品组
	 * @param weituodzj
	 * @param userId
	 * @param time
	 */
	public void copyWeituoYptoRw(String weituodzj,String userId,Date time){
		this.dao.copyWeituoYptoRw(weituodzj,userId,time);
	}

	/**
	 * 调度保存时建立调度样品组关联
	 * 需要提供如下：
	 * 申请样品组id（将这个表zy_weituo_yp的id赋值给yangpinzid），数量
	 * @param diaoduYps
	 */
	public void saveDiaoduYp(List<YangPinDto> diaoduYps,String weituodzj){
		//调度关联样品组先将历史删掉，还原样品组剩余数量
		updateDiaodu(diaoduYps,weituodzj);
		String diaoduypid = "";
		int i = 0;
		for(YangPinDto yangPinDto:diaoduYps){
			DiaoduYp diaoduYp = new DiaoduYp();
			BeanUtils.copyProperties(yangPinDto,diaoduYp);
			//获取申请单样品组信息
			if(StringUtils.isNotBlank(diaoduYp.getYangpinzid())){
				WeituoYp wtypentity = weituoYpDao.get(diaoduYp.getYangpinzid());
				if(wtypentity!=null){
					diaoduYp.setId("");
//					diaoduYp.setYangpinsysl(diaoduYp.getShuliang());
//					diaoduYp.setYangpinbh(wtypentity.getYangpinbh());
					diaoduYp.setYangpinmc(wtypentity.getYangpinmc());
					diaoduYp.setYangpinlb(wtypentity.getYangpinlb());
					diaoduYp.setYangpinxh(wtypentity.getYangpinxh());
					diaoduYp.setShifouly(wtypentity.getShifouly());
					diaoduYp.setShifouxysyszy(wtypentity.getShifouxysyszy());
					diaoduYp.setYangpinbz(wtypentity.getYangpinbz());
					//diaoduYp.setChushibm(wtypentity.getChushibm());
					i++;
					if(i<10){
						diaoduYp.setChushibm(wtypentity.getBianmaqz().concat("0-").concat(String.valueOf(i)));
					}else {
						diaoduYp.setChushibm(wtypentity.getBianmaqz().concat("-").concat(String.valueOf(i)));
					}
					diaoduYp.setBianmaqz(wtypentity.getBianmaqz());
				}
			}
			diaoduYp.preInsert();
			this.dao.insert(diaoduYp);
//			updateYangpinsl(diaoduYp.getYangpinzid(),diaoduYp.getShuliang());//更新申请样品组剩余数量
		}
	}

	/**
	 * 重新关联调度样品组时有两个操作
	 * 1：删掉历史关联关系
	 * 2：还原申请单样品组剩余数量（还原为样品组初始数量）
	 *  @param diaoduYps
	 */
	public void updateDiaodu(List<YangPinDto> diaoduYps,String weituodzj){
		if(diaoduYps.size()>0){
			if(diaoduYps.get(0)!=null){
				this.dao.deleteDiaodu(diaoduYps.get(0).getWeituodzj(),diaoduYps.get(0).getFangfazj());//删除
//				weituoYpDao.restoreSl(weituodzj,fangfazj);//还原
			}
		}else{
			this.dao.deleteDiaodu(weituodzj,null);//删除
		}
	}


	/**
	 * 更新样品数量，剩余数量
	 * @param yangpinid 样品组id
	 * @param shuliang 调度时更新后数量
	 */
	public void updateYangpinsl(String yangpinid,String shuliang){
		this.weituoYpDao.updateYangpinsl(yangpinid,shuliang);
	}


	
}