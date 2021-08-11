package com.demxs.tdm.service.resource.consumeables;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import com.demxs.tdm.dao.lab.LabInfoDao;
import com.demxs.tdm.dao.resource.changshangygys.ChangshanggysxxDao;
import com.demxs.tdm.dao.resource.consumeables.HaocaiInfoDao;
import com.demxs.tdm.dao.resource.haocai.HaocaiCfwzDao;
import com.demxs.tdm.dao.resource.haocai.HaocaiLxDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.utils.Collections3;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.common.web.OpResult;
import com.demxs.tdm.domain.resource.changshangygys.Changshanggysxx;
import com.demxs.tdm.domain.resource.consumeables.*;
import com.demxs.tdm.domain.resource.consumeables.*;
import com.demxs.tdm.comac.common.constant.HaocaiConstans;
import com.demxs.tdm.service.sys.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * 耗材库Service
 * @author sunjunhui
 * @version 2017-11-30
 */
@Service
@Transactional(readOnly = true)
public class HaocaiInfoService extends CrudService<HaocaiInfoDao, Haocaiku> {

	@Autowired
	private ChangshanggysxxDao changshanggysxxDao;
	@Autowired
	private HaocaiLxDao haocaiLxDao;
	@Autowired
	private HaocaiCfwzDao haocaiCfwzDao;
	@Autowired
	private SystemService systemService;
	@Autowired
	private HaocaiRecordService haocaiRecordService;
	@Autowired
	private LabInfoDao labInfoDao;

	private Haocaiku getAll(Haocaiku haocaiku){
		//厂商
		if (haocaiku.getChangshang()!=null && StringUtils.isNotBlank(haocaiku.getChangshang().getId())) {
			haocaiku.setChangshang(changshanggysxxDao.get(haocaiku.getChangshang().getId()));
		} else {
			haocaiku.setChangshang(new Changshanggysxx());
		}
		//供应商
		if (haocaiku.getGongyingshang()!=null && StringUtils.isNotBlank(haocaiku.getGongyingshang().getId())) {
			haocaiku.setGongyingshang(changshanggysxxDao.get(haocaiku.getGongyingshang().getId()));
		} else {
			haocaiku.setGongyingshang(new Changshanggysxx());
		}
		//耗材类型
		if (haocaiku.getHaocaiLx()!=null && StringUtils.isNotBlank(haocaiku.getHaocaiLx().getId())) {
			haocaiku.setHaocaiLx(haocaiLxDao.get(haocaiku.getHaocaiLx().getId()));
		} else {
			haocaiku.setHaocaiLx(new HaocaiLx());
		}
		//存放位置
		if (haocaiku.getHaocaiCfwz()!=null && StringUtils.isNotBlank(haocaiku.getHaocaiCfwz().getId())) {
			haocaiku.setHaocaiCfwz(haocaiCfwzDao.get(haocaiku.getHaocaiCfwz().getId()));
		} else {
			haocaiku.setHaocaiCfwz(new HaocaiCfwz());
		}
		//验收人
		if (haocaiku.getYanshour()!=null && StringUtils.isNotBlank(haocaiku.getYanshour().getId())) {
			haocaiku.setYanshour(systemService.getUser(haocaiku.getYanshour().getId()));
		} else {
			haocaiku.setYanshour(new User());
		}
		if(haocaiku.getCreateBy()!=null && StringUtils.isNotBlank(haocaiku.getCreateBy().getId())){
			haocaiku.setCreateBy(systemService.getUser(haocaiku.getCreateBy().getId()));
		}
		haocaiku.setLabInfo(labInfoDao.get(haocaiku.getLabInfoId()));
		return  haocaiku;
	}

	private List<Haocaiku> getAll(List<Haocaiku> haocaikus){
		if(!Collections3.isEmpty(haocaikus)){
			for(Haocaiku h:haocaikus){
				getAll(h);
			}
		}
		return haocaikus;
	}

	public Haocaiku get(String id) {
		return getAll(super.get(id));
	}

	public List<Haocaiku> findList(Haocaiku haocaiInfo) {
		return getAll(super.findList(haocaiInfo));
	}

	public Page<Haocaiku> findPage(Page<Haocaiku> page, Haocaiku haocaiInfo) {
		haocaiInfo.getSqlMap().put("dsf", dataScopeFilter(haocaiInfo.getCurrentUser(), "o", "u8"));
		Page<Haocaiku> dataValue = super.findPage(page, haocaiInfo);
		if(dataValue!=null && !Collections3.isEmpty(dataValue.getList())){
			getAll(dataValue.getList());
		}
		return dataValue;
	}
	
	@Transactional(readOnly = false)
	public void save(Haocaiku haocaiInfo) {
		super.save(haocaiInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(Haocaiku haocaiInfo) {
		super.delete(haocaiInfo);
	}

	/**
	 * 入库 新增耗材
	 * @param haocaiInfo
	 */
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void storage(Haocaiku haocaiInfo){
		haocaiInfo.setLabInfoId(haocaiInfo.getLabInfo().getId());
		haocaiInfo.setLabInfoName(labInfoDao.get(haocaiInfo.getLabInfoId()).getName());
		//添加记录
		if(StringUtils.isNotBlank(haocaiInfo.getHaocaibh())){
		}else{
			haocaiInfo.setHaocaibh(getMaxHaocaibh());
		}
		if (haocaiInfo.getYongjiu()!=null) {
			if(haocaiInfo.getYongjiu()==1){
				haocaiInfo.setYouxiaoqi(null);
			}
		} else {
			haocaiInfo.setYongjiu(0);
		}


		if(StringUtils.isNotBlank(haocaiInfo.getId())){//修改　没有入库记录

		}else{//表示新增
			HaocaiRecord haocaiRecord = new HaocaiRecord();
			haocaiRecord.setHaocaibh(haocaiInfo.getHaocaibh());
			haocaiRecord.setHaocaiguige(haocaiInfo.getHaocaiguige());
			haocaiRecord.setHaocaiLx(haocaiInfo.getHaocaiLx());
			haocaiRecord.setHaocaimc(haocaiInfo.getHaocaimc());
			haocaiRecord.setJiliangdw(haocaiInfo.getJiliangdw());
			haocaiRecord.setType(HaocaiConstans.haoCaiLzlx.RUKU);
			haocaiRecord.setInNumber(haocaiInfo.getShuliang());
			haocaiRecordService.save(haocaiRecord);
		}
		super.save(haocaiInfo);

	}


	/**
	 * 出库
	 */
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public OpResult outBound(HaocaiOperateVO haocaiOperateVO){
		OpResult opResult = new OpResult(OpResult.OP_SUCCESS,OpResult.OpMsg.OP_SUCCESS);
		if(!Collections3.isEmpty(haocaiOperateVO.getHaocaikuList())){
			//对每个耗材进行出库
			for(Haocaiku h:haocaiOperateVO.getHaocaikuList()){
				Integer num = h.getShuliang();
				Haocaiku hku = this.dao.getByCode(h.getHaocaibh());
				HaocaiRecord haocaiRecord = new HaocaiRecord();
				haocaiRecord.setHaocaibh(hku.getHaocaibh());
				haocaiRecord.setHaocaiguige(hku.getHaocaiguige());
				haocaiRecord.setHaocaiLx(hku.getHaocaiLx());
				haocaiRecord.setHaocaimc(hku.getHaocaimc());
				haocaiRecord.setJiliangdw(hku.getJiliangdw());
				haocaiRecord.setType(HaocaiConstans.haoCaiLzlx.CHUKU);
				haocaiRecord.setOutNumber(num);
				haocaiRecord.setAcceptUser(haocaiOperateVO.getOperateUser());
				if(hku.getShuliang()-num<0){
					opResult.setStatus(OpResult.OP_FAILED);
					opResult.setMessage("耗材编号:"+hku.getHaocaibh()+"库存不足,出库失败");
					throw new ServiceException("耗材编号:"+hku.getHaocaibh()+"库存不足,出库失败");
				}
				else{
					haocaiRecordService.save(haocaiRecord);
					//给这个耗材进行库存减少
					this.dao.updateKucun(hku.getId(),hku.getShuliang()-num,new Date());
				}
			}
		}
		return opResult;
	}


	/**
	 * 反库
	 */
	@Transactional(readOnly = false)
	public void back(HaocaiOperateVO haocaiOperateVO){
		if(!Collections3.isEmpty(haocaiOperateVO.getHaocaikuList())){
			//对每个耗材进行出库
			for(Haocaiku h:haocaiOperateVO.getHaocaikuList()){
				Integer num = h.getShuliang();
				Haocaiku hku = this.dao.getByCode(h.getHaocaibh());
				HaocaiRecord haocaiRecord = new HaocaiRecord();
				haocaiRecord.setHaocaibh(hku.getHaocaibh());
				haocaiRecord.setHaocaiguige(hku.getHaocaiguige());
				haocaiRecord.setHaocaiLx(hku.getHaocaiLx());
				haocaiRecord.setHaocaimc(hku.getHaocaimc());
				haocaiRecord.setJiliangdw(hku.getJiliangdw());
				haocaiRecord.setType(HaocaiConstans.haoCaiLzlx.FANKU);
				haocaiRecord.setBackNumber(num);
				haocaiRecord.setBackUser(haocaiOperateVO.getOperateUser());
				haocaiRecordService.save(haocaiRecord);
				//给这个耗材进行库存减少
				this.dao.updateKucun(hku.getId(),hku.getShuliang()+num,new Date());
			}
		}
	}

	/**
	 * 入库
	 */
	@Transactional(readOnly = false)
	public void ruku(HaocaiOperateVO haocaiOperateVO){
		if(!Collections3.isEmpty(haocaiOperateVO.getHaocaikuList())){
			//对每个耗材进行出库
			for(Haocaiku h:haocaiOperateVO.getHaocaikuList()){
				Integer num = h.getShuliang();
				Haocaiku hku = this.dao.getByCode(h.getHaocaibh());
				HaocaiRecord haocaiRecord = new HaocaiRecord();
				haocaiRecord.setHaocaibh(hku.getHaocaibh());
				haocaiRecord.setHaocaiguige(hku.getHaocaiguige());
				haocaiRecord.setHaocaiLx(hku.getHaocaiLx());
				haocaiRecord.setHaocaimc(hku.getHaocaimc());
				haocaiRecord.setJiliangdw(hku.getJiliangdw());
				haocaiRecord.setType(HaocaiConstans.haoCaiLzlx.RUKU);
				haocaiRecord.setInNumber(num);
				haocaiRecord.setBackUser(haocaiOperateVO.getOperateUser());
				haocaiRecordService.save(haocaiRecord);
				//给这个耗材进行库存减少
				this.dao.updateKucun(hku.getId(),hku.getShuliang()+num,new Date());
			}
		}
	}


	/**
	 * 耗材编号
	 * @return
	 */
	public String getMaxHaocaibh(){
		String haocaibh = "HC".concat(DateUtils.getDate().replace("-",""));
		String maxNum = this.dao.getMaxHaocaibh(haocaibh);
		if (StringUtils.isNotBlank(maxNum)) {
			int no = Integer.parseInt(maxNum.substring(maxNum.length()-3)) + 1;
			DecimalFormat df = new DecimalFormat("000");
			haocaibh += df.format(no);
		} else {
			haocaibh += "001";
		}
		return haocaibh;
	}
	
}