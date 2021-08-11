package com.demxs.tdm.service.resource.haocai;

import com.demxs.tdm.dao.resource.haocai.HaocaiLzjlDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.resource.haocai.HaocaiLzjl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 耗材流转记录Service
 * @author zhangdengcai
 * @version 2017-07-17
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class HaocaiLzjlService extends CrudService<HaocaiLzjlDao, HaocaiLzjl> {

	@Override
	public HaocaiLzjl get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<HaocaiLzjl> findList(HaocaiLzjl haocaiLzjl) {
		return super.findList(haocaiLzjl);
	}
	
	@Override
	public Page<HaocaiLzjl> findPage(Page<HaocaiLzjl> page, HaocaiLzjl haocaiLzjl) {

//		if(UserUtils.getUser()!=null){
//			haocaiLzjl.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
//		}
		Page<HaocaiLzjl> dataValue = super.findPage(page, haocaiLzjl);
		List<HaocaiLzjl> lzjls = dataValue.getList();
		/*if(lzjls!=null && !lzjls.isEmpty()){
			for (HaocaiLzjl lzjl : lzjls) {
				if ((HaocaiConstans.haoCaiLzlx.RUKU).equals(lzjl.getHaocailx())) {//入库
					lzjl.setRukusl(lzjl.getShuliang());
				} else if ((HaocaiConstans.haoCaiLzlx.CHUKU).equals(lzjl.getHaocailx())) {//出库
					lzjl.setChukusl(lzjl.getShuliang());
					lzjl.setLingqur(lzjl.getCaozuormc());
				} else if ((HaocaiConstans.haoCaiLzlx.FANKU).equals(lzjl.getHaocailx())) {//返库
					lzjl.setFankusl(lzjl.getShuliang());
					lzjl.setGuihuanr(lzjl.getCaozuormc());
				}
			}
		}*/
		dataValue.setList(lzjls);
		return dataValue;
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(HaocaiLzjl haocaiLzjl) {
		super.save(haocaiLzjl);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(HaocaiLzjl haocaiLzjl) {
		super.delete(haocaiLzjl);
	}
	
}