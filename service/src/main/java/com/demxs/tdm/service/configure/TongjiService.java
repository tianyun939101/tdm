package com.demxs.tdm.service.configure;

import com.demxs.tdm.dao.configure.TongjiDao;
import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.FreeMarkers;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.configure.Tongji;
import com.demxs.tdm.domain.configure.TongjiSeries;
import com.demxs.tdm.domain.configure.Tongjibt;
import com.demxs.tdm.domain.configure.Tongjitj;
import com.demxs.tdm.comac.common.constant.PzConstans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 统计配置Service
 * @author 张仁
 * @version 2017-08-07
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class TongjiService extends CrudService<TongjiDao, Tongji> {
	@Autowired
	private TongjiDao tongjiDao;
	@Autowired
	private TongjibtService tongjibtService;
	@Autowired
	private TongjitjService tongjitjService;
	@Override
	public Tongji get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<Tongji> findList(Tongji tongji) {
		return super.findList(tongji);
	}
	
	@Override
	public Page<Tongji> findPage(Page<Tongji> page, Tongji tongji) {
		return super.findPage(page, tongji);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(Tongji tongji) {
		super.save(tongji);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(Tongji tongji) {
		super.delete(tongji);
	}

	public List<Map> findListBySql(String sql){
		return tongjiDao.findListBySql(sql);
	}

	public Map getTongjitinfo(HttpServletRequest request,Tongji tongji){
		Map mapdata = new HashMap();
		mapdata.put("title",tongji.getBiaoti());
		Tongjibt hengbt = new Tongjibt();
		if(StringUtils.isNotBlank(tongji.getHengzuobid())){
			hengbt = this.tongjibtService.get(tongji.getHengzuobid());
		}
		List<Tongjibt> btxulielists = new ArrayList<Tongjibt>();
		if(hengbt!=null){
			btxulielists.add(hengbt);
		}
		String strbartype = "";// 统计图类型
		Tongjibt searchbt = new Tongjibt();
		searchbt.setTongjiid(tongji.getId());
		if(tongji.getShifoujcb().equals(Global.NO)){
		}else{
			searchbt.setHengzuobid(tongji.getHengzuobid());//交叉表才查询
		}
		List<Tongjibt> btlists = tongjibtService.findList(searchbt);//查询非横坐标的
		if(tongji.getTutongjilx()!=null && tongji.getTutongjilx().equals(PzConstans.tutongjilx.ZHUZHUANG)){
			strbartype = "bar";
		}else if(tongji.getTutongjilx()!=null && tongji.getTutongjilx().equals(PzConstans.tutongjilx.ZHEXIAN)){
			strbartype = "line";
		}else if(tongji.getTutongjilx()!=null && tongji.getTutongjilx().equals(PzConstans.tutongjilx.BINGTU)){
			strbartype = "pie";
		}
		//拼接查询语句
		Tongjitj searchtj = new Tongjitj();
		if(tongji.getTiaojianids()!=null){
			searchtj.setArrIDS(tongji.getTiaojianids().split(","));
		}else{
			searchtj.setArrIDS("nostr".split(","));
		}
		List<Tongjitj> listtj = tongjitjService.findList(searchtj);
		Map searchMap = new HashMap();
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.add(Calendar.MONTH, -1);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String startdate=formatter.format(cal1.getTime());
		String enddate=formatter.format(cal2.getTime());

		Calendar caltime1 = Calendar.getInstance();
		Calendar caltime2 = Calendar.getInstance();
		caltime1.add(Calendar.MONTH, -1);
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String starttime=formatter2.format(caltime1.getTime());
		String endtime=formatter2.format(caltime2.getTime());
		if(tongji.getIszitu().equals(Global.YES))
		{
			if(StringUtils.isNotBlank(tongji.getZitutj()))
			{
				listtj.add(this.tongjitjService.get(tongji.getZitutj()));
			}
		}
		for(int i=0;i<listtj.size();i++){
			Tongjitj curTongjitj = listtj.get(i);
			if (curTongjitj.getZiduanlx().equals("riqi")){
				String begindater = request.getParameter(curTongjitj.getEnname()+"begindate");
				String enddater = request.getParameter(curTongjitj.getEnname()+"enddate");
				searchMap.put(curTongjitj.getEnname()+"begindate",begindater!=null ? begindater :startdate);
				searchMap.put(curTongjitj.getEnname()+"enddate",enddater!=null ? enddater :enddate);
			}else if (curTongjitj.getZiduanlx().equals("shijian")){
				String begindater = request.getParameter(curTongjitj.getEnname()+"begintime");
				String enddater = request.getParameter(curTongjitj.getEnname()+"endtime");
				searchMap.put(curTongjitj.getEnname()+"begintime",begindater!=null ? begindater :starttime );
				searchMap.put(curTongjitj.getEnname()+"endtime",enddater!=null ? enddater :endtime);
			}else {
				String paravalue = request.getParameter(curTongjitj.getEnname());
				searchMap.put(curTongjitj.getEnname(),paravalue!=null ? paravalue :"" );
			}
		}
		//数据部分
		String sql = StringUtils.replaceSql(tongji.getSqlstr());
		if (listtj.size()>0){
			sql =  FreeMarkers.renderString(sql,searchMap);
		}
		if(tongji.getShifoujcb().equals(Global.NO)){
			List<String> listzong= new ArrayList<String>();
			List<Map> lists = findListBySql(sql);
			for(int i=0;i<lists.size();i++){
				Map maps = lists.get(i);
				Tongjibt tongjibt = btlists.get(0);
				Object cellValue = maps.get(tongjibt.getBiaotimc());
				listzong.add(cellValue==null?"/":cellValue.toString());
			}

			if(StringUtils.isNotBlank(strbartype)){
				if(tongji.getTutongjifx()!=null && tongji.getTutongjifx().equals(PzConstans.tutongjifx.HENGXIANG)){
					TongjiSeries[] tongjiSeriesArray = new TongjiSeries[lists.size()];
					for(int i=0;i<lists.size();i++){
						Map maps = lists.get(i);
						TongjiSeries curSeries = new TongjiSeries();
						tongjiSeriesArray[i] = curSeries;
						curSeries.setIndex(i);
						curSeries.setName(listzong.get(i));
						curSeries.setType(strbartype);
						Double[] data = new Double[btlists.size()-1];
						for(int j=1;j<btlists.size();j++){
							Tongjibt tongjibt = btlists.get(j);
							Object cellValue = maps.get(tongjibt.getBiaotimc());
							if (cellValue!=null){
								data[j-1] = Double.parseDouble(cellValue.toString());
							}
						}
						curSeries.setData(data);
					}
					mapdata.put("series",tongjiSeriesArray);
				}
				else if(tongji.getTutongjifx()!=null && tongji.getTutongjifx().equals(PzConstans.tutongjifx.ZONGXIANG)){
					TongjiSeries[] tongjiSeriesArray = new TongjiSeries[btlists.size()-1];
					for(int i=1;i<btlists.size();i++){
						Tongjibt tongjibt = btlists.get(i);
						TongjiSeries curSeries = new TongjiSeries();
						tongjiSeriesArray[i-1] = curSeries;
						curSeries.setIndex(i-1);
						curSeries.setName(tongjibt.getBiaotimc());
						curSeries.setType(strbartype);
						Double[] data = new Double[lists.size()];
						for(int j=0;j<lists.size();j++){
							Map maps = lists.get(j);
							Object cellValue = maps.get(tongjibt.getBiaotimc());
							if (cellValue!=null){
								data[j] = Double.parseDouble(cellValue.toString());
							}
						}
					}
					mapdata.put("series",tongjiSeriesArray);
				}
			}
		}else{
			List<String> riqilist = new ArrayList<String>();
			List<String> dierlist = new ArrayList<String>();
			Map riqiMap = new HashMap();
			Map dierMap = new HashMap();
			List<Map> lists = findListBySql(sql);
			for(int i=0;i<lists.size();i++){
				Map maps = lists.get(i);
				Tongjibt tongjibt = hengbt;
				Object cellValue = maps.get(tongjibt.getBiaotimc());
				if(riqiMap.get("riqi"+cellValue)==null){
					riqiMap.put("riqi"+cellValue,cellValue);
					riqilist.add(cellValue+"");
				}
				 tongjibt = btlists.get(0);
				 cellValue = maps.get(tongjibt.getBiaotimc());
				 if(dierMap.get("dier"+cellValue)==null){
					 dierlist.add(cellValue+"");
					 dierMap.put("dier"+cellValue,cellValue);
				 }
			}
			if(StringUtils.isNotBlank(strbartype)){
				List<Map> datalist = new ArrayList<Map>();
				for (int i = 0; i <riqilist.size() ; i++) {
					String riqi = riqilist.get(i);
					for (int j = 0; j <dierlist.size() ; j++) {
						String dier =dierlist.get(j);
						for (int k = 0; k < lists.size() ; k++) {
							Map maps = lists.get(k);
							if(maps.get(hengbt.getBiaotimc()).equals(riqi) && maps.get(btlists.get(0).getBiaotimc()).equals(dier)){
								Map  datam =  new HashMap();
								datam.put(btxulielists.get(0).getBiaotimc(),riqi);
								datam.put(btlists.get(0).getBiaotimc(),dier);
								for(int jj=1;jj<btlists.size();jj++){
									datam.put(btlists.get(jj).getBiaotimc(),maps.get(btlists.get(jj).getBiaotimc()));
								}

								datalist.add(datam);
							}
						}
					}
				}
					TongjiSeries[] tongjiSeriesArray = new TongjiSeries[dierlist.size()];
					for (int j = 0; j <dierlist.size() ; j++) {
						String dier =dierlist.get(j);
						TongjiSeries curSeries = new TongjiSeries();
						tongjiSeriesArray[j] = curSeries;
						curSeries.setIndex(j);
						curSeries.setName(dier);
						curSeries.setType(strbartype);
					}
					mapdata.put("series",tongjiSeriesArray);
			}
		}
		return mapdata;
	}
}