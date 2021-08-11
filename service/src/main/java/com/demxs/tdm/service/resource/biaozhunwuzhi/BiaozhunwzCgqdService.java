package com.demxs.tdm.service.resource.biaozhunwuzhi;

import com.demxs.tdm.dao.resource.biaozhunwuzhi.BiaozhunwzCgqdDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.common.utils.SpringContextHolder;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.biaozhunwuzhi.BiaozhunwzCgqd;
import com.demxs.tdm.domain.resource.biaozhunwuzhi.BiaozhunwzCgqdmx;
import com.demxs.tdm.comac.common.constant.BiaozhunwzConstans;
import com.demxs.tdm.service.oa.IActAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

/**
 * 标准物质采购清单Service
 * @author zhangdengcai
 * @version 2017-07-18
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class BiaozhunwzCgqdService extends CrudService<BiaozhunwzCgqdDao, BiaozhunwzCgqd> {

	@Autowired
	private BiaozhunwzCgqdmxService biaozhunwzCgqdmxService;
	private IActAuditService iActAuditService;

	@Override
	public BiaozhunwzCgqd get(String id) {
		return super.get(id);
	}

	@Override
	public List<BiaozhunwzCgqd> findList(BiaozhunwzCgqd biaozhunwzCgqd) {
		return super.findList(biaozhunwzCgqd);
	}

	@Override
	public Page<BiaozhunwzCgqd> findPage(Page<BiaozhunwzCgqd> page, BiaozhunwzCgqd biaozhunwzCgqd) {
		return super.findPage(page, biaozhunwzCgqd);
	}

	public Page<BiaozhunwzCgqd> findPageForOther(Page<BiaozhunwzCgqd> page, BiaozhunwzCgqd biaozhunwzCgqd) {
		page.setOrderBy("a.update_date desc");
		return super.findPage(page, biaozhunwzCgqd);
	}

	/**
	 * 保存
	 * @param biaozhunwzCgqd
	 * @param saveOrCommit 保存或提交
	 */
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(BiaozhunwzCgqd biaozhunwzCgqd,String saveOrCommit) {
		String id = biaozhunwzCgqd.getId();
		if (StringUtils.isNotBlank(id)) {//修改
			super.save(biaozhunwzCgqd);
		} else {//新增
			biaozhunwzCgqd.setCaigoudid(getMaxCaigoudid());//保存采购单id
			biaozhunwzCgqd.setShenqingsj(DateUtils.formatDateTime(new Date()));//申请日期
			biaozhunwzCgqd.setQingdanzt(BiaozhunwzConstans.biaoZhunwzcgdzt.DAITIJ);//状态：待提交
			biaozhunwzCgqd.preInsert();
			id = IdGen.uuid();
			biaozhunwzCgqd.setId(id);
			this.dao.insert(biaozhunwzCgqd);
		}

		List<BiaozhunwzCgqdmx> cgqdmxes = biaozhunwzCgqd.getBiaozhunwz();
		if(cgqdmxes!=null && !cgqdmxes.isEmpty()){
			for (BiaozhunwzCgqdmx cgqdmx: cgqdmxes) {
				cgqdmx.setCaigoudzj(biaozhunwzCgqd.getId());
				biaozhunwzCgqdmxService.save(cgqdmx);
			}
		}

		//提交流程
		if("commit".equals(saveOrCommit)){
			startAudit(id);
		}
	}

	/**
	 * 详情
	 * @param biaozhunwzCgqd
	 * @return
	 */
	@Override
	public BiaozhunwzCgqd get(BiaozhunwzCgqd biaozhunwzCgqd) {
		BiaozhunwzCgqd dataValue = super.get(biaozhunwzCgqd);
		String cgdzj = biaozhunwzCgqd.getId();

		List<BiaozhunwzCgqdmx> cgqdmxes = biaozhunwzCgqdmxService.listByCgdzj(cgdzj);
		dataValue.setBiaozhunwz(cgqdmxes);
		return dataValue;
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(BiaozhunwzCgqd biaozhunwzCgqd) {
		super.delete(biaozhunwzCgqd);
	}

	/**
	 * 删除
	 * @param ids
	 */
	public void deleteMore(String ids) {
		if (StringUtils.isNotBlank(ids)) {
			String[] idsArr = ids.split(",");
			BiaozhunwzCgqd biazhunwzCgqd = new BiaozhunwzCgqd();
			biazhunwzCgqd.setArrIDS(idsArr);
			this.dao.batchDelete(biazhunwzCgqd);
		}
	}

	/**
	 * 采购单id
	 * @return
	 */
	public String getMaxCaigoudid(){
		String caigoudid = "CG".concat(DateUtils.getDate().replace("-",""));
		BiaozhunwzCgqd biaozhunwzCgqd = new BiaozhunwzCgqd();
		biaozhunwzCgqd.setCaigoudid(caigoudid);
		String maxNum = this.dao.getMaxCaigoudid(biaozhunwzCgqd);
		if (StringUtils.isNotBlank(maxNum)) {
			int no = Integer.parseInt(maxNum.substring(maxNum.length()-3)) + 1;
			DecimalFormat df = new DecimalFormat("000");
			caigoudid += df.format(no);
		} else {
			caigoudid += "001";
		}
		return caigoudid;
	}

	/**
	 * 发起流程审核
	 * @param id
	 */
	public void startAudit(String id){
		try{
			logger.debug("开始发起方法验证流程ID：{}"+id);
			iActAuditService = getiActAuditService();
//			iActAuditService.start(id, GlobalConstans.ActProcDefKey.BIAOZHUNWZCGSH);

			BiaozhunwzCgqd biaozhunwzCgqd = new BiaozhunwzCgqd();
			biaozhunwzCgqd.setId(id);
			BiaozhunwzCgqd cgqd = get(biaozhunwzCgqd);
			if(cgqd!=null){
				cgqd.setQingdanzt(BiaozhunwzConstans.biaoZhunwzcgdzt.DAISHENH);//待审核
				cgqd.setShenher(UserUtils.getUser()==null?"":UserUtils.getUser().getName());
				cgqd.setShenherid(UserUtils.getUser()==null?"":UserUtils.getUser().getId());
				this.dao.update(cgqd);
			}
		}catch (Exception e){
			logger.error("发起方法验证流程失败！",e);
		}
	}

	/**
	 * 审核结束，改变状态
	 * @param id
	 * @param isPass
	 */
	public void changeCgzt(String id, boolean isPass){
		BiaozhunwzCgqd biaozhunwzCgqd = new BiaozhunwzCgqd();
		biaozhunwzCgqd.setId(id);
		BiaozhunwzCgqd cgqd = get(biaozhunwzCgqd);
		if(cgqd!=null){
			cgqd.setQingdanzt(BiaozhunwzConstans.biaoZhunwzcgdzt.YISHENH);//已审核
			cgqd.setShenher(UserUtils.getUser()==null?"":UserUtils.getUser().getName());
			cgqd.setShenherid(UserUtils.getUser()==null?"":UserUtils.getUser().getId());
			if (isPass) {
				cgqd.setShenhejg(BiaozhunwzConstans.biaoZhunwzshjg.TONGGUO);//通过
			} else {
				cgqd.setShenhejg(BiaozhunwzConstans.biaoZhunwzshjg.BUTONGG);//不通过
			}
			this.dao.update(cgqd);
		}
	}

	/**
	 * 获取IActAuditService
	 * @return
	 */
	public IActAuditService getiActAuditService() {
		if(iActAuditService == null){
			iActAuditService = SpringContextHolder.getBean(IActAuditService.class);
		}
		return iActAuditService;
	}
}