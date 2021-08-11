package com.demxs.tdm.service.resource.shebei;

import com.demxs.tdm.dao.resource.shebei.ShebeiCgqdDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.common.utils.SpringContextHolder;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.shebei.ShebeiCgqd;
import com.demxs.tdm.domain.resource.shebei.ShebeiCgqdmx;
import com.demxs.tdm.comac.common.constant.ShebeiConstans;
import com.demxs.tdm.service.oa.IActAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 设备采购清单管理Service
 * @author zhangdengcai
 * @version 2017-07-15
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ShebeiCgqdService extends CrudService<ShebeiCgqdDao, ShebeiCgqd> {
	@Autowired
	private ShebeiCgqdmxService shebeiCgqdmxService;
	private IActAuditService iActAuditService;

	@Override
	public ShebeiCgqd get(String id) {
		return super.get(id);
	}

	@Override
	public List<ShebeiCgqd> findList(ShebeiCgqd shebeiCgqd) {
		return super.findList(shebeiCgqd);
	}

	/**
	 * 分页列表
	 * @param page 分页对象
	 * @param shebeiCgqd
	 * @return
	 */
	@Override
	public Page<ShebeiCgqd> findPage(Page<ShebeiCgqd> page, ShebeiCgqd shebeiCgqd) {
//		page.setOrderBy("a.update_date desc");
		/*if(UserUtils.getUser()!=null){
			shebeiCgqd.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		}*/
		return super.findPage(page, shebeiCgqd);
	}

	/**
	 * 保存
	 * @param shebeiCgqd
	 *  @param saveOrCommit
	 */
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(ShebeiCgqd shebeiCgqd, String saveOrCommit) {
		String id = shebeiCgqd.getId();
		if(StringUtils.isNotBlank(id)){//修改
			super.save(shebeiCgqd);
		} else{
			String caigoudid = "CG".concat(DateUtils.getDate().replace("-",""));
			shebeiCgqd.setCaigoudid(caigoudid);
			String maxNum = this.dao.getMaxCaigoudid(shebeiCgqd);
			if (StringUtils.isBlank(maxNum)) {
				caigoudid += "001";
			} else {
				int no = Integer.parseInt(maxNum.substring(maxNum.length()-3)) + 1;
				DecimalFormat df = new DecimalFormat("000");
				caigoudid += df.format(no);
			}
			shebeiCgqd.setCaigoudid(caigoudid);//采购单id
			shebeiCgqd.setShenqingrq(DateUtils.getDate());//申请日期
			shebeiCgqd.setCaigouzt(ShebeiConstans.sheBeicgdzt.DAITIJ);//采购状态：待提交

			shebeiCgqd.preInsert();
			id = IdGen.uuid();
			shebeiCgqd.setId(id);
			this.dao.insert(shebeiCgqd);
		}

		//保存采购的设备
		List<ShebeiCgqdmx> shebeis = shebeiCgqd.getShebei();
		if(shebeis!=null && !shebeis.isEmpty()){
			for (ShebeiCgqdmx shebei: shebeis) {
				shebei.setCaigouqdzj(id);
				shebeiCgqdmxService.save(shebei);
			}
		}

		//提交流程
		if("commit".equals(saveOrCommit)){
			startAudit(id);
		}
	}

	/**
	 * 详情
	 * @param shebeiCgqd
	 * @return
	 */
	@Override
	public ShebeiCgqd get(ShebeiCgqd shebeiCgqd) {
		ShebeiCgqd cgqd = super.get(shebeiCgqd);
		if(cgqd!=null){
			List<ShebeiCgqdmx> cgdmxs = shebeiCgqdmxService.listByShebeicgqdzj(cgqd.getId());
			cgqd.setShebei(cgdmxs);
		}
		return cgqd;
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(ShebeiCgqd shebeiCgqd) {
		super.delete(shebeiCgqd);
	}

	/**
	 * 删除
	 * @param ids
	 */
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void deleteMore(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] idsArr = ids.split(",");
			ShebeiCgqd shebeiCgqd = new ShebeiCgqd();
			shebeiCgqd.setArrIDS(idsArr);
			this.dao.batchDelete(shebeiCgqd);
		}
	}

	/**
	 * 发起流程审核
	 * @param id
	 */
	public void startAudit(String id){
		try{
			logger.debug("开始发起方法验证流程ID：{}"+id);
			iActAuditService = getiActAuditService();
//			iActAuditService.start(id, GlobalConstans.ActProcDefKey.SHEBEICGSH);

			ShebeiCgqd shebeiCgqd = new ShebeiCgqd();
			shebeiCgqd.setId(id);
			ShebeiCgqd cgqd = get(shebeiCgqd);
			if(cgqd!=null){
				cgqd.setCaigouzt(ShebeiConstans.sheBeicgdzt.DAISHENH);//待审核
				this.dao.update(cgqd);
			}
		}catch (Exception e){
			logger.error("发起方法验证流程失败！",e);
		}
	}

	/**
	 * 审批通过，改变状态
	 * @param id
	 * @param isPass 是否通过
	 */
	public void changeCgzt(String id, boolean isPass){
		ShebeiCgqd shebeiCgqd = new ShebeiCgqd();
		shebeiCgqd.setId(id);
		ShebeiCgqd cgqd = get(shebeiCgqd);
		if(cgqd!=null){
			cgqd.setCaigouzt(ShebeiConstans.sheBeicgdzt.YISHENH);//已审核
			cgqd.setShenher(UserUtils.getUser()==null?"":UserUtils.getUser().getName());
			cgqd.setShenherid(UserUtils.getUser()==null?"":UserUtils.getUser().getId());
			if (isPass) {
				cgqd.setShenhejg(ShebeiConstans.sheBeishjg.TONGGUO);//通过
			} else {
				cgqd.setShenhejg(ShebeiConstans.sheBeishjg.BUTONGG);//不通过
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