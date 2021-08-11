package com.demxs.tdm.service.resource.biaozhunwuzhi;

import com.demxs.tdm.dao.resource.biaozhunwuzhi.BiaozhunwzLzjlDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.resource.biaozhunwuzhi.BiaozhunwzLzjl;
import com.demxs.tdm.comac.common.constant.BiaozhunwzConstans;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 标准物质流转记录Service
 * @author zhangdengcai
 * @version 2017-07-18
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class BiaozhunwzLzjlService extends CrudService<BiaozhunwzLzjlDao, BiaozhunwzLzjl> {

	/*@Autowired
	private IShiyanrwYpffjcxgxService iShiyanrwYpffjcxgxService;*/

	@Override
	public BiaozhunwzLzjl get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<BiaozhunwzLzjl> findList(BiaozhunwzLzjl biaozhunwzLzjl) {
		List<BiaozhunwzLzjl> lzjls = super.findList(biaozhunwzLzjl);
		renwuxx(lzjls);//出库试验时，存入任务名称和试验工程师
		return lzjls;
	}
	
	@Override
	public Page<BiaozhunwzLzjl> findPage(Page<BiaozhunwzLzjl> page, BiaozhunwzLzjl biaozhunwzLzjl) {
//		if(UserUtils.getUser()!=null){
//			biaozhunwzLzjl.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
//		}
		Page<BiaozhunwzLzjl> listPage = super.findPage(page, biaozhunwzLzjl);
		if(listPage!=null){
			List<BiaozhunwzLzjl> lzjls = listPage.getList();
			listPage.setList(renwuxx(lzjls));//出库试验时，存入任务名称和试验工程师
		}
		return listPage;
	}

	public Page<BiaozhunwzLzjl> findPageForOther(Page<BiaozhunwzLzjl> page, BiaozhunwzLzjl biaozhunwzLzjl) {
		page.setOrderBy("a.update_date desc");
		Page<BiaozhunwzLzjl> listPage = super.findPage(page, biaozhunwzLzjl);
		if(listPage!=null){
			List<BiaozhunwzLzjl> lzjls = listPage.getList();
			listPage.setList(renwuxx(lzjls));//出库试验时，存入任务名称和试验工程师
		}
		return listPage;
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(BiaozhunwzLzjl biaozhunwzLzjl) {
		super.save(biaozhunwzLzjl);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(BiaozhunwzLzjl biaozhunwzLzjl) {
		super.delete(biaozhunwzLzjl);
	}

	/**
	 * 出库试验时，存入任务名称和试验工程师
	 * @param lzjls
	 * @return
	 */
	public List<BiaozhunwzLzjl> renwuxx(List<BiaozhunwzLzjl> lzjls){
		if(lzjls!=null && !lzjls.isEmpty()){
			for (BiaozhunwzLzjl lzjl: lzjls) {
				/*if(BiaozhunwzConstans.biaoZhunwzLzlx.CHUKUJC.equals(lzjl.getLiuxiang())){
					Map<String, String> map = iShiyanrwYpffjcxgxService.getShiyanrwByBiaozhunwzId(lzjl.getBiaozhunwzzj());
					lzjl.setJiancegcs(map.get("jiancegcs"));
					lzjl.setRenwumc(map.get("renwumc"));
				}else{
					lzjl.setJiancegcs("");
					lzjl.setRenwumc("");
				}*/
				if(!BiaozhunwzConstans.biaoZhunwzLzlx.CHUKUJC.equals(lzjl.getLiuxiang())){
					lzjl.setJiancegcs("");
					lzjl.setRenwumc("");
				}
			}
		}
		return lzjls;
	}
}