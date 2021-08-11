package com.demxs.tdm.service.resource.biaozhunwuzhi;

import com.demxs.tdm.dao.resource.biaozhunwuzhi.BiaozhunwzCkfkdDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.biaozhunwuzhi.Biaozhunwz;
import com.demxs.tdm.domain.resource.biaozhunwuzhi.BiaozhunwzCkfkd;
import com.demxs.tdm.domain.resource.biaozhunwuzhi.BiaozhunwzLzjl;
import com.demxs.tdm.comac.common.constant.BiaozhunwzConstans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 标准物质出库、返库Service
 * @author zhangdengcai
 * @version 2017-06-17
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class BiaozhunwzCkfkdService extends CrudService<BiaozhunwzCkfkdDao, BiaozhunwzCkfkd> {
	@Autowired
	private BiaozhunwzService biaozhunwzService;
	@Autowired
	private BiaozhunwzLzjlService biaozhunwzLzjlService;
	/*@Autowired
	private IShiyanrwYpffjcxgxService iShiyanrwYpffjcxgxService;*/

	@Override
	public BiaozhunwzCkfkd get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<BiaozhunwzCkfkd> findList(BiaozhunwzCkfkd biaozhunwzCkfkd) {
		return super.findList(biaozhunwzCkfkd);
	}
	
	@Override
	public Page<BiaozhunwzCkfkd> findPage(Page<BiaozhunwzCkfkd> page, BiaozhunwzCkfkd biaozhunwzCkfkd) {
		return super.findPage(page, biaozhunwzCkfkd);
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(BiaozhunwzCkfkd biaozhunwzCkfkd) {
		String saveOrUpdate = "";//用于保存或更新流转记录
		if(StringUtils.isNotBlank(biaozhunwzCkfkd.getId())){
			saveOrUpdate = "update";
		}
		//修改标准物质状态. leixing: 出库返库单类型--0、入库；1、出库试验；2、出库核查；3、自行处理；4、返库
		if(StringUtils.isNotBlank(biaozhunwzCkfkd.getBiaozhunzj())){
			String status = "";
			if ((BiaozhunwzConstans.biaoZhunwzLzlx.CHUKUJC).equals(biaozhunwzCkfkd.getLeixing())
					|| String.valueOf(BiaozhunwzConstans.biaoZhunwzLzlx.CHUKUHC).equals(biaozhunwzCkfkd.getLeixing())) {//出库试验、核查
				status = BiaozhunwzConstans.biaoZhunwzzt.SHIYONGZ;//使用中
				if((BiaozhunwzConstans.biaoZhunwzLzlx.CHUKUJC).equals(biaozhunwzCkfkd.getLeixing())){//出库试验，保存标准物质和试验任务关联关系
					//iShiyanrwYpffjcxgxService.saveShiyanrwBiaozhunwz(biaozhunwzCkfkd.getBiaozhunzj(), biaozhunwzCkfkd.getChukuglrwid());
				}
			} else if((BiaozhunwzConstans.biaoZhunwzLzlx.ZIXINGCHULI).equals(biaozhunwzCkfkd.getLeixing())) {//自行处理
				status = BiaozhunwzConstans.biaoZhunwzzt.YICHUL;//已处理
			} else if((BiaozhunwzConstans.biaoZhunwzLzlx.FANKU).equals(biaozhunwzCkfkd.getLeixing())) {//返库
				status = BiaozhunwzConstans.biaoZhunwzzt.KONGXIAN;//空闲
			}

			//更改标准物质状态
			if((biaozhunwzCkfkd.getBiaozhunzj()).contains(",")){
				String[] bzwzIds = (biaozhunwzCkfkd.getBiaozhunzj()).split(",");
				for(int i=0; i<bzwzIds.length; i++){
					Biaozhunwz biaozhunwz =	biaozhunwzService.get(bzwzIds[i]);
					if(biaozhunwz!=null){
						if(!BiaozhunwzConstans.biaoZhunwzzt.KONGXIAN.equals(biaozhunwz.getBiaozhunwzzt()) && !(BiaozhunwzConstans.biaoZhunwzLzlx.FANKU).equals(biaozhunwzCkfkd.getLeixing())){
							throw new RuntimeException("标准物质为出库状态，不能再出库");
						} else if(BiaozhunwzConstans.biaoZhunwzzt.KONGXIAN.equals(biaozhunwz.getBiaozhunwzzt()) && (BiaozhunwzConstans.biaoZhunwzLzlx.FANKU).equals(biaozhunwzCkfkd.getLeixing()))  {
							throw new RuntimeException("标准物质为空闲状态，不能返库");
						}

						if(BiaozhunwzConstans.biaoZhunwzLzlx.FANKU.equals(biaozhunwzCkfkd.getLeixing())){//返库，更改存放位置
							biaozhunwz.setCunchuweiz(biaozhunwzCkfkd.getCunfangwzid());
							if(StringUtils.isNotBlank(biaozhunwzCkfkd.getCunfangwzid())){
                                biaozhunwz.setCunchuweizmc(biaozhunwzService.cunfangwzmc(biaozhunwzCkfkd.getCunfangwzid()));
                            }
						}
						biaozhunwz.setBiaozhunwzzt(status);
						biaozhunwzService.saveOther(biaozhunwz);
					}
				}
			}else{
				Biaozhunwz bzwz = biaozhunwzService.get(biaozhunwzCkfkd.getBiaozhunzj());
				if(bzwz!=null){
					if(!BiaozhunwzConstans.biaoZhunwzzt.KONGXIAN.equals(bzwz.getBiaozhunwzzt()) && !(BiaozhunwzConstans.biaoZhunwzLzlx.FANKU).equals(biaozhunwzCkfkd.getLeixing())){
						throw new RuntimeException("标准物质为出库状态，不能再出库");
					} else if(BiaozhunwzConstans.biaoZhunwzzt.KONGXIAN.equals(bzwz.getBiaozhunwzzt()) && (BiaozhunwzConstans.biaoZhunwzLzlx.FANKU).equals(biaozhunwzCkfkd.getLeixing()))  {
						throw new RuntimeException("标准物质为空闲状态，不能返库");
					}

					if(BiaozhunwzConstans.biaoZhunwzLzlx.FANKU.equals(biaozhunwzCkfkd.getLeixing())){//返库，更改存放位置
						bzwz.setCunchuweiz(biaozhunwzCkfkd.getCunfangwzid());
                        if(StringUtils.isNotBlank(biaozhunwzCkfkd.getCunfangwzid())){
                            bzwz.setCunchuweizmc(biaozhunwzService.cunfangwzmc(biaozhunwzCkfkd.getCunfangwzid()));
                        }
					}
					bzwz.setBiaozhunwzzt(status);
					biaozhunwzService.saveOther(bzwz);
				}
			}
		}
		super.save(biaozhunwzCkfkd);

		saveLzjl(biaozhunwzCkfkd, saveOrUpdate);//保存流转记录
	}

	/**
	 * 保存流转记录
	 * @param biaozhunwzCkfkd
	 * @param saveOrUpdate
	 */
	public void saveLzjl(BiaozhunwzCkfkd biaozhunwzCkfkd, String saveOrUpdate){
		BiaozhunwzLzjl biaozhunwzLzjl = new BiaozhunwzLzjl();//流转记录
		biaozhunwzLzjl.setBiaozhunwzzj(biaozhunwzCkfkd.getBiaozhunzj());
		biaozhunwzLzjl.setLiuxiang(biaozhunwzCkfkd.getLeixing());
		biaozhunwzLzjl.setCaozuormc(biaozhunwzCkfkd.getBiaozhunr());//领取人/归还人(九月底需求)
		biaozhunwzLzjl.setDengjirmc(UserUtils.get(biaozhunwzCkfkd.getCreateById())==null?"":UserUtils.get(biaozhunwzCkfkd.getCreateById()).getName());//登记人
		if("update".equals(saveOrUpdate)){
			List<BiaozhunwzLzjl> lzjls = biaozhunwzLzjlService.findList(biaozhunwzLzjl);
			if(lzjls!=null && !lzjls.isEmpty()){
				biaozhunwzLzjl = lzjls.get(0);
			}
		}

		biaozhunwzLzjl.setRiqi(DateUtils.getDate());
		biaozhunwzLzjl.setLiuxiang(biaozhunwzCkfkd.getLeixing());
		Biaozhunwz biaozwz = new Biaozhunwz();
		biaozwz.setId(biaozhunwzCkfkd.getBiaozhunzj());
		Biaozhunwz bzwz = biaozhunwzService.get(biaozwz);
		biaozhunwzLzjl.setJiliangdw(bzwz.getJiliangdw());
		biaozhunwzLzjl.setZhengshuh(bzwz.getZhengshuh());
		biaozhunwzLzjl.setBiaozhunwzbh(bzwz.getBiaozhunwzbh());
		biaozhunwzLzjl.setBiaozhunwzmc(bzwz.getBiaozhunwzmc());
		biaozhunwzLzjl.setGuigexh(bzwz.getBiaozhunwzxh());
		User user = null;
		if(StringUtils.isNotBlank(biaozhunwzCkfkd.getBiaozhunr())) {
			user = UserUtils.get(biaozhunwzCkfkd.getBiaozhunr());
		}

		if (String.valueOf(BiaozhunwzConstans.biaoZhunwzLzlx.CHUKUJC).equals(biaozhunwzCkfkd.getLeixing())
				|| String.valueOf(BiaozhunwzConstans.biaoZhunwzLzlx.CHUKUHC).equals(biaozhunwzCkfkd.getLeixing())) {//出库试验、核查
			biaozhunwzLzjl.setLingqur(user==null? "": user.getName());
		} else if(String.valueOf(BiaozhunwzConstans.biaoZhunwzLzlx.ZIXINGCHULI).equals(biaozhunwzCkfkd.getLeixing())) {//自行处理
			biaozhunwzLzjl.setLingqur(user==null? "": user.getName());
		} else if(String.valueOf(BiaozhunwzConstans.biaoZhunwzLzlx.FANKU).equals(biaozhunwzCkfkd.getLeixing())) {//返库
			biaozhunwzLzjl.setGuihuanr(user==null? "": user.getName());
		}
		biaozhunwzLzjlService.save(biaozhunwzLzjl);//保存流转记录
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(BiaozhunwzCkfkd biaozhunwzCkfkd) {
		super.delete(biaozhunwzCkfkd);
	}

	/**
	 * 批量删除
	 * @param ids
	 */
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void deleteMore(String ids){
		String[] idArray = null;
		if(StringUtils.isNotBlank(ids)){
			if(ids.contains(",")){
				idArray = ids.split(",");
			}else {
				idArray = new String[1];
				idArray[0] = ids;
			}
		}
		BiaozhunwzCkfkd biaozhunwzCkfkd = new BiaozhunwzCkfkd();
		biaozhunwzCkfkd.setIds(idArray);
		this.dao.deleteMore(biaozhunwzCkfkd);
	}

}