package com.demxs.tdm.service.configure;

import java.util.List;
import java.util.Map;

import com.demxs.tdm.dao.configure.BaogaombDao;
import com.demxs.tdm.dao.configure.BaogaombYxbqDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.domain.configure.Baogaomb;
import com.demxs.tdm.domain.configure.BaogaombYxbq;
import com.demxs.tdm.comac.common.constant.PzConstans;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 报告模板已选标签Service
 * @author TDM
 * @version 2017-12-13
 */
@Service
@Transactional(readOnly = true)
public class BaogaombYxbqService extends CrudService<BaogaombYxbqDao, BaogaombYxbq> {
	@Autowired
	private BaogaombDao baogaombDao;
	public BaogaombYxbq get(String id) {
		return super.get(id);
	}
	
	public List<BaogaombYxbq> findList(BaogaombYxbq baogaombYxbq) {
		return super.findList(baogaombYxbq);
	}
	
	public Page<BaogaombYxbq> findPage(Page<BaogaombYxbq> page, BaogaombYxbq baogaombYxbq) {
		return super.findPage(page, baogaombYxbq);
	}
	
	@Transactional(readOnly = false)
	public void save(BaogaombYxbq baogaombYxbq) {
		super.save(baogaombYxbq);
	}
	
	@Transactional(readOnly = false)
	public void delete(BaogaombYxbq baogaombYxbq) {
		super.delete(baogaombYxbq);
	}
	/**
	 * 查询某个试验项报告模板的已选标签
	 * @param jcxid  试验项id
	 * @return
	 */
	public Map getJcxmbyxbqJson(String jcxid){
		Baogaomb baogaomb = new Baogaomb();
		baogaomb.setJiancexid(jcxid);
		List<Baogaomb> mblist = baogaombDao.findList(baogaomb);
		if(mblist.size()>0){
			String mbid = mblist.get(0).getId();
			Map returnMap = Maps.newHashMap();
			returnMap.put("mbid",mbid);
			returnMap.put("isliezh",mblist.get(0).getIsliezh());
			BaogaombYxbq find =new BaogaombYxbq();
			find.setMbid(mbid);
			find.setBiaoqianlx(PzConstans.Biaoqianlx.BIAODAN);
			List<BaogaombYxbq>  list  = this.findList(find);
			returnMap.put("yxbqFormlist",list);
			find =new BaogaombYxbq();
			find.setMbid(mbid);
			find.setBiaoqianlx(PzConstans.Biaoqianlx.LIEBIAO);
			list  = this.findList(find);
			returnMap.put("yxbqListlist",list);

			return  returnMap;
		}else{
			return null;
		}

	}
	/**
	 * 查询某个报告模板的已选标签
	 * @param mbid  模板id
	 * @return
	 */
	public Map getAllBaogaombyxbqJson(String mbid){
		Map returnMap = Maps.newHashMap();
		BaogaombYxbq find =new BaogaombYxbq();
		find.setMbid(mbid);
		find.setBiaoqianlx(PzConstans.Biaoqianlx.BIAODAN);
		find.setBiaoqianfs(PzConstans.Biaoqianfs.FUZHI);
		List<BaogaombYxbq>  list  = this.findList(find);
		returnMap.put("form",list);
		find =new BaogaombYxbq();
		find.setMbid(mbid);
		find.setBiaoqianfs(PzConstans.Biaoqianfs.BIAOGE);
		list  = this.findList(find);
		for (int i = 0; i <list.size() ; i++) {
			BaogaombYxbq finSelf =new BaogaombYxbq();
			finSelf.setMbid(list.get(i).getBqid());
			finSelf.setBiaoqianlx(PzConstans.Biaoqianlx.BIAODAN);
			List<BaogaombYxbq>  listSelf  = this.findList(finSelf);
			list.get(i).setYxbqFormlist(listSelf);
			finSelf =new BaogaombYxbq();
			finSelf.setMbid(list.get(i).getBqid());
			finSelf.setBiaoqianlx(PzConstans.Biaoqianlx.LIEBIAO);
			listSelf  = this.findList(finSelf);
			list.get(i).setYxbqListlist(listSelf);
		}
		returnMap.put("biaoge",list);

		return  returnMap;
	}
	
}