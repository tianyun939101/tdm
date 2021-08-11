package com.demxs.tdm.service.resource.haocai;

import com.demxs.tdm.dao.resource.haocai.HaocaiCkfkdDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.consumeables.HaocaiLx;
import com.demxs.tdm.domain.resource.haocai.*;
import com.demxs.tdm.domain.resource.haocai.*;
import com.demxs.tdm.comac.common.constant.HaocaiConstans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 耗材出库返库Service
 * @author zhangdengcai
 * @version 2017-07-17
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class HaocaiCkfkdService extends CrudService<HaocaiCkfkdDao, HaocaiCkfkd> {

	@Autowired
	private HaocaiCkfkdmxService haocaiCkfkdmxService;
	@Autowired
	private HaocaiRkxxService haocaiRkxxService;
	@Autowired
	private HaocaiLzjlService haocaiLzjlService;
	@Autowired
	private HaocaiLxService haocaiLxService;
	@Autowired
	private HaocaiKcService haocaiKcService;
	@Autowired
	private HaocaiCfwzService haocaiCfwzService;

	@Override
	public HaocaiCkfkd get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<HaocaiCkfkd> findList(HaocaiCkfkd haocaiCkfkd) {
		return super.findList(haocaiCkfkd);
	}
	
	@Override
	public Page<HaocaiCkfkd> findPage(Page<HaocaiCkfkd> page, HaocaiCkfkd haocaiCkfkd) {
		return super.findPage(page, haocaiCkfkd);
	}

	/**
	 * 保存出库返库单
	 * @param haocaiCkfkd
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(HaocaiCkfkd haocaiCkfkd) {
		if(haocaiCkfkd.getSubmit()){//提交
			haocaiCkfkd.setShujuzt(HaocaiConstans.haoCaisjzt.YITIJ);
		}else{//暂存
			haocaiCkfkd.setShujuzt(HaocaiConstans.haoCaisjzt.DAITIJ);
		}
		super.save(haocaiCkfkd);

		List<HaocaiCkfkdmx> ckfkmxes = haocaiCkfkd.getHaocai();
		if(ckfkmxes!=null){
			for (HaocaiCkfkdmx ckfkmx : ckfkmxes) {
				haocaiCkfkdmxService.save(ckfkmx);
				if(haocaiCkfkd.getSubmit()){//提交
					//增减数量
					HaocaiRkxx haocaiRkxx = new HaocaiRkxx();
					haocaiRkxx.setId(ckfkmx.getHaocaiid());
					HaocaiRkxx hcxx = haocaiRkxxService.get(haocaiRkxx);
					if((HaocaiConstans.haoCaiLzlx.CHUKU).equals(haocaiCkfkd.getHaocailx())){//出库减数量
						if(Integer.valueOf(ckfkmx.getChukufksl()) > Integer.valueOf(hcxx.getRukusl())){
							throw new RuntimeException("出库数量大于库存数量");
						}
						hcxx.setRukusl(String.valueOf(Integer.valueOf(hcxx.getRukusl()) - Integer.valueOf(ckfkmx.getChukufksl()==null?"0":ckfkmx.getChukufksl())));
						saveToKc(hcxx, ckfkmx, "ck");//保存到耗材库
					}else if((HaocaiConstans.haoCaiLzlx.FANKU).equals(haocaiCkfkd.getHaocailx())){//返库加数量
						hcxx.setRukusl(String.valueOf(Integer.valueOf(hcxx.getRukusl()) + Integer.valueOf(ckfkmx.getChukufksl() == null ? "0" : ckfkmx.getChukufksl())));
						//修改存放位置
						hcxx.setCunfangwz(haocaiCfwzService.get(haocaiCkfkd.getCunfangwzId()));
						saveToKc(hcxx, ckfkmx, "fk");//保存到耗材库
					}
					haocaiRkxxService.save(hcxx,"");
					//保存流转记录
					saveLzjl(haocaiCkfkd, ckfkmx,hcxx);
				}
			}
		}
	}

	/**
	 * 保存流转记录 目前出库返库不涉及修改，所以流转记录也只新增，不修改
	 * @param haocaiCkfkd
	 * @param ckfkmx
	 */
	public void saveLzjl(HaocaiCkfkd haocaiCkfkd, HaocaiCkfkdmx ckfkmx,HaocaiRkxx hcxx){
		//保存流转记录
		HaocaiLzjl haocaiLzjl = new HaocaiLzjl();
		if((HaocaiConstans.haoCaiLzlx.CHUKU).equals(haocaiCkfkd.getHaocailx())){//出库
			haocaiLzjl.setHaocailx(HaocaiConstans.haoCaiLzlx.CHUKU);//设置流向
		}else if((HaocaiConstans.haoCaiLzlx.FANKU).equals(haocaiCkfkd.getHaocailx())){//返库
			haocaiLzjl.setHaocailx(HaocaiConstans.haoCaiLzlx.FANKU);//设置流向
		}
		haocaiLzjl.setHaocaizj(ckfkmx.getHaocaiid());
		haocaiLzjl.setShuliang(ckfkmx.getChukufksl());
		haocaiLzjl.setCaozuor(haocaiCkfkd.getLingqughrid());
		haocaiLzjl.setCaozuormc(haocaiCkfkd.getLingqughr());//领取人/归还人
		haocaiLzjl.setDengjirmc(UserUtils.get(haocaiCkfkd.getCreateById())==null?"":UserUtils.get(haocaiCkfkd.getCreateById()).getName());//登记人
		haocaiLzjl.setEipbianhao(ckfkmx.getEipbianhao());
		haocaiLzjl.setGuigexh(ckfkmx.getGuigexh());
		haocaiLzjl.setRiqi(DateUtils.getDate());
		haocaiLzjl.setHaocaileix(hcxx.getHaocailx().getId());//耗材类型，不是耗材流转类型
		if(StringUtils.isNotBlank(ckfkmx.getHaocailx())){
			HaocaiLx haocaiLx = new HaocaiLx();
			haocaiLx.setId(ckfkmx.getHaocailx());
			HaocaiLx lx = haocaiLxService.get(haocaiLx);
			haocaiLzjl.setHaocaileixmc(lx==null ? "": lx.getLeixingmc());
		}
		haocaiLzjl.setJiliangdw(ckfkmx.getJiliangdw());
		haocaiLzjlService.save(haocaiLzjl);
	}

	/**
	 * * 保存到库存表
	 * @param hcxx 耗材
	 * @param ckfkmx 出库返库明细
	 * @param type 出库或返库
	 */
	public void saveToKc(HaocaiRkxx hcxx, HaocaiCkfkdmx ckfkmx, String type){
		HaocaiKc kc = haocaiKcService.findListByMcXh(hcxx.getHaocaimc(), hcxx.getGuigexh());
		if(kc!=null){
			if("fk".equals(type)){//返库
				String a = kc.getKucunsl();
				String b = ckfkmx.getChukufksl();
				int kcsl = Integer.valueOf(kc.getKucunsl()==null?"0":kc.getKucunsl()) +  Integer.valueOf(ckfkmx.getChukufksl()==null?"0":ckfkmx.getChukufksl());
				kc.setKucunsl(String.valueOf(kcsl));
			}else if("ck".equals(type)){//出库
				int kcsl = Integer.valueOf(kc.getKucunsl()==null?"0":kc.getKucunsl()) -  Integer.valueOf(ckfkmx.getChukufksl()==null?"0":ckfkmx.getChukufksl());
				kc.setKucunsl(String.valueOf(kcsl));
			}
			haocaiKcService.save(kc);
		}
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(HaocaiCkfkd haocaiCkfkd) {
		super.delete(haocaiCkfkd);
	}

}