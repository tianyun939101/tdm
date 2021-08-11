package com.demxs.tdm.service.resource.huanjing;

import com.demxs.tdm.dao.resource.dto.HuanjingSjDto;
import com.demxs.tdm.dao.resource.huanjing.HuanjingSjDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.common.utils.zrutils.ClientApp;
import com.demxs.tdm.domain.resource.huanjing.HuanjingSbwh;
import com.demxs.tdm.domain.resource.huanjing.HuanjingSj;
import com.demxs.tdm.domain.resource.shebei.ShebeiCfwz;
import com.demxs.tdm.comac.common.constant.HuanjingConstans;
import com.demxs.tdm.service.resource.shebei.ShebeiCfwzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

/**
 * 环境数据Service
 * @author zhangdengcai
 * @version 2017-07-19
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class HuanjingSjService extends CrudService<HuanjingSjDao, HuanjingSj> implements IHuanjingSj {

    @Autowired
    private ShebeiCfwzService shebeiCfwzService;
    @Autowired
	private HuanjingSbwhService huanjingSbwhService;
    @Autowired
	private HuanjingZsfjService huanjingZsfjService;

	@Override
	public HuanjingSj get(String id) {
		HuanjingSj sj = super.get(id);
		ShebeiCfwz cfwz = shebeiCfwzService.get(sj.getFangjianid());
		setShangxiax(sj);
		return sj;
	}
	
	@Override
	public List<HuanjingSj> findList(HuanjingSj huanjingSj) {
		return super.findList(huanjingSj);
	}
	
	@Override
	public Page<HuanjingSj> findPage(Page<HuanjingSj> page, HuanjingSj huanjingSj) {
		page.setOrderBy("a.update_date desc");
        if(UserUtils.getUser()!=null){
            huanjingSj.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
        }
		Page<HuanjingSj> pageList = super.findPage(page, huanjingSj);
		List<HuanjingSj> sjList = pageList.getList();
		if(sjList!=null && !sjList.isEmpty()){
			for (HuanjingSj sj: sjList) {
				//ShebeiCfwz cfwz = shebeiCfwzService.get(sj.getFangjianid());
				setShangxiax(sj);
				sj.setCreateBy(UserUtils.get(sj.getCreateBy().getId()));
				if((HuanjingConstans.hjsjLy.CAIJI).equals(sj.getShujuly())){
					User u = new User();
					u.setName("系统");
					sj.setCreateBy(u);
				}
			}
		}
		pageList.setList(sjList);
		return pageList;
	}

	/**
	 * 设置房间上下限
	 * @param sj
	 */
	public void setShangxiax(HuanjingSj sj){
//		ShebeiCfwz cfwz = shebeiCfwzService.get(sj.getFangjianid());
//		if(cfwz!=null){
//			sj.setWendusx(cfwz.getWendusx());
//			sj.setWenduxx(cfwz.getWenduxx());
//			sj.setShidusx(cfwz.getShidusx());
//			sj.setShiduxx(cfwz.getShiduxx());
//			sj.setZaoshengsx(cfwz.getZaoshengsx());
//			sj.setZaoshengxx(cfwz.getZaoshengsx());
//			sj.setZhendongsx(cfwz.getZhendongsx());
//			sj.setZhendongxx(cfwz.getZhendongxx());
//		}
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(HuanjingSj huanjingSj) {
		super.save(huanjingSj);
	}

	/**
	 * 批量保存环境数据
	 * @param huanjingSj
	 */
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void batchSave(List<HuanjingSj> huanjingSj, String shujulx) {
		if(!huanjingSj.isEmpty()){
			for(HuanjingSj sj : huanjingSj){
				sj.setShujulx(shujulx);
				sj.setShujuly(HuanjingConstans.hjsjLy.LURU);
				sj.preInsert();
			}
			this.dao.batchSave(huanjingSj);
		}
	}

	/**
	 * 获取试验室环境数据（折线图数据。9月底需求：只展示温室度）
	 * @return
	 */
	public List<Map<String, Object>> linedata(){
		return null;
//		HuanjingZsfj huanjingZsfj = new HuanjingZsfj();
//		huanjingZsfj.setShifouzs(Global.YES);
//		List<HuanjingZsfj> huanjingZsfjs = huanjingZsfjService.findList(huanjingZsfj);//设置好的要展示的房间
//		Collections.sort(huanjingZsfjs, new Comparator<HuanjingZsfj>() {
//			public int compare(HuanjingZsfj o1, HuanjingZsfj o2) {
//				/*int a = o1.getFangjianmc().compareTo(o2.getFangjianmc());//按房间名称排序
//				a = a>0 ? 1 : (a<0?-1:0);*/
//				return o1.getFangjianmc().compareTo(o2.getFangjianmc());//按房间名称排序
//			}
//		});
//
//		Set<String> zhanshifjs = new HashSet<String>();
//		if(huanjingZsfjs!=null && !huanjingZsfjs.isEmpty()){
//			for (HuanjingZsfj zsfj: huanjingZsfjs) {
//				zhanshifjs.add(zsfj.getFangjianid());
//			}
//		}
//
//		List<Map<String, Object>> dataValue = new ArrayList<Map<String, Object>>();
//
//		List<HuanjingSjDto> huanjingSjs = this.dao.linedata(HuanjingConstans.hjsjLx.WSD);
//		if(huanjingSjs!=null && !huanjingSjs.isEmpty()){
//            if(zhanshifjs!=null && !zhanshifjs.isEmpty()){
//				for (String fj : zhanshifjs) {
//					Map<String, Object> fangjianMap = new HashMap<String, Object>();
//					List<Map<String, Object>> shujulx = new ArrayList<Map<String, Object>>();
//					List<HuanjingSjDto> wenduList = new ArrayList<HuanjingSjDto>();
//					List<HuanjingSjDto> shiduList = new ArrayList<HuanjingSjDto>();
//					if(huanjingSjs!=null && !huanjingSjs.isEmpty()){
//						for (HuanjingSjDto sj : huanjingSjs) {
//							if(fj.equals(sj.getFangjianid())){
//								if(StringUtils.isNotBlank(sj.getWendu())){
//									wenduList.add(sj);
//								}
//								if(StringUtils.isNotBlank(sj.getShidu())){
//									shiduList.add(sj);
//								}
//							}
//						}
//					}
//					Map<String, Object> wenduMap = new HashMap<String, Object>();
//					returnShijian(wenduList);//没有数据时，返回横坐标值
//					wenduMap.put("wendu",wenduList);
//					shujulx.add(wenduMap);
//					Map<String, Object> shiduMap = new HashMap<String, Object>();
//					returnShijian(shiduList);//没有数据时，返回横坐标值
//					shiduMap.put("shidu",shiduList);
//					shujulx.add(shiduMap);
//
//					ShebeiCfwz cfwz = new ShebeiCfwz();
//					cfwz.setId(fj);
//					ShebeiCfwz sbcfwz = shebeiCfwzService.get(cfwz);
//					HuanjingSjFinalDto huanjingSjFinal = new HuanjingSjFinalDto();
//					huanjingSjFinal.setFangjian(sbcfwz==null?"":sbcfwz.getWeizhimc());//房间名称
//					huanjingSjFinal.setWendusx(sbcfwz==null?"":sbcfwz.getWendusx());//温度上限
//					huanjingSjFinal.setWenduxx(sbcfwz==null?"":sbcfwz.getWenduxx());
//					huanjingSjFinal.setShidusx(sbcfwz==null?"":sbcfwz.getShidusx());//湿度上限
//					huanjingSjFinal.setShiduxx(sbcfwz==null?"":sbcfwz.getShiduxx());
//					huanjingSjFinal.setShuju(shujulx);
//					fangjianMap.put("data", huanjingSjFinal);
//					dataValue.add(fangjianMap);
//				}
//			}
//        }
//		return dataValue;
	}

	/**
	 * 没有数据时，返回横坐标值
	 * @param shujuList
	 */
	public void returnShijian(List<HuanjingSjDto> shujuList){
		if(shujuList.size()<1){
			for(int i=1; i<=24; i++){
				HuanjingSjDto sj = new HuanjingSjDto();
				sj.setShijian(String.valueOf(i));
				shujuList.add(sj);
			}
		}
	}

	/**
	 * 获取（采集）温湿度数据，并保存
	 * @param shebeiid
	 */
	public void caijiWsSj(String shebeiid){
		try {
			HuanjingSbwh shebei = new HuanjingSbwh();
			shebei.setId(shebeiid);
			HuanjingSbwh sb = huanjingSbwhService.get(shebei);
			String ip = "";
			Integer port = null;
			if(sb!=null){
				ip = sb.getIpAddress();
				port = Integer.valueOf(sb.getPort()).intValue();
			}

			if(StringUtils.isNotBlank(ip) && port!=null && port>0){
				Map<String, String> data = ClientApp.getData(ip, port);//采集数据
				String wendu = data.get("wendu");
				String shidu = data.get("shidu");
				saveSj(shebeiid, DateUtils.getDateTime(), wendu, shidu);//保存数据
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 定时采集温室度
	 */
	public void dingshiCjWs(){
		List<HuanjingSbwh> sbs = huanjingSbwhService.findList(new HuanjingSbwh());
		if(sbs!=null && !sbs.isEmpty()){
			for (HuanjingSbwh sb: sbs) {
				caijiWsSj(sb.getId());
			}
		}
	}

	/**
	 * 测试方法：定时采集温室度
	 */
	public void caijiWsSjTest(){
		try {
			Map<String, String> data = ClientApp.getData("192.168.0.105",502);//采集数据
			String wendu = data.get("wendu");
			String shidu = data.get("shidu");
			saveSj("ba10188b2a9c4c468b5fe921531e4de4", DateUtils.getDateTime(), wendu, shidu);//保存数据
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存环境(温湿度)数据
	 * @param shebeiid;		// 设备id
	 * @param shijian;		// 时间
	 * @param wendu;		// 温度
	 * @param shidu;		// 湿度
	 */
	@Override
	public void saveSj(String shebeiid, String shijian, String wendu, String shidu) {
		HuanjingSj huanjingSj = new HuanjingSj();
		huanjingSj.setShujuly(HuanjingConstans.hjsjLy.CAIJI);
		huanjingSj.setShujulx(HuanjingConstans.hjsjLx.WSD);
		huanjingSj.setShebieid(shebeiid);
		huanjingSj.setShijian(shijian);
		huanjingSj.setWendu(wendu);
		huanjingSj.setShidu(shidu);

		HuanjingSbwh shebei = new HuanjingSbwh();
		shebei.setId(shebeiid);
		HuanjingSbwh sb = huanjingSbwhService.get(shebei);
		if(sb!=null){
			huanjingSj.setShebeibh(sb.getShebeibh());
			huanjingSj.setShebeimc(sb.getShebeimc());
			huanjingSj.setFangjianid(sb.getShebeifj());

			ShebeiCfwz cfwz = new ShebeiCfwz();
			cfwz.setId(sb.getShebeifj());
			huanjingSj.setFangjian(shebeiCfwzService.get(cfwz)==null?"":shebeiCfwzService.get(cfwz).getWeizhimc());
		}

		super.save(huanjingSj);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(HuanjingSj huanjingSj) {
		super.delete(huanjingSj);
	}

	/**
	 * 批量删除
	 * @param ids
	 */
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void deleteMore(String ids) {
		String[] idArray = null;
		if(StringUtils.isNotBlank(ids)){
			if(ids.contains(",")){
				idArray = ids.split(",");
			}else {
				idArray = new String[1];
				idArray[0] = ids;
			}
		}
		HuanjingSj huanjingSj = new HuanjingSj();
		huanjingSj.setArrIDS(idArray);
		this.dao.deleteMore(huanjingSj);
	}
}