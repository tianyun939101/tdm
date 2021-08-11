package com.demxs.tdm.service.resource.zhishi;

import com.demxs.tdm.common.utils.*;
import com.demxs.tdm.dao.resource.zhishi.ZhishiXxDao;
import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.config.GlobalConstans;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.domain.resource.yuangong.Yuangong;
import com.demxs.tdm.domain.resource.zhishi.*;
import com.demxs.tdm.common.utils.*;
import com.demxs.tdm.domain.external.SendTodoParam;
import com.demxs.tdm.domain.resource.attach.AttachmentInfo;
import com.demxs.tdm.domain.resource.zhishi.*;
import com.demxs.tdm.comac.common.constant.MessageConstants;
import com.demxs.tdm.comac.common.constant.ZhishiConstans;
import com.demxs.tdm.service.external.impl.ExternalApi;
import com.demxs.tdm.service.oa.IActAuditService;
import com.demxs.tdm.service.resource.attach.AttachmentInfoService;
import com.demxs.tdm.service.sys.OfficeService;
import com.demxs.tdm.service.sys.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 知识信息操作Service
 * @author 詹小梅
 * @version 2017-06-15
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ZhishiXxService extends CrudService<ZhishiXxDao, ZhishiXx> {
	@Autowired
	private AttachmentInfoService attachmentInfoService;
	@Autowired
	private ZhishiLbService zhishiLbService;
	@Autowired
	private ZhishiKjffService zhishiKjffService;
	@Autowired
	private ZhishiGlffService zhishiGlffService;
	@Autowired
	private ZhishiKjffRyService zhishiKjffRyService;
	@Autowired
	private ZhishiXzjlService zhishiXzjlService;
	private IActAuditService iActAuditService;

	@Autowired
	private OfficeService officeService;
	@Autowired
	private SystemService systemService;

	@Autowired
	private ExternalApi externalApi;

	public ZhishiXx getAllXx(ZhishiXx zsxx){
		if (zsxx!=null) {
			if(StringUtils.isNotBlank(zsxx.getZhishilx())){
                //todo 知识类别名称全名称
				ZhishiLb zhishiLb = zhishiLbService.get(zsxx.getZhishilx());
                //zsxx.setZhishilxmc(zhishiLbService.getAllName(zhishiLb,"leixingmc"));
				zsxx.setZhishilxmc(zhishiLb.getLeixingmc());
            }
			if(attachmentInfoService.get(zsxx.getFujianid())==null){
				zsxx.setFujian(new AttachmentInfo());
			}else {
				zsxx.setFujian(attachmentInfoService.get(zsxx.getFujianid()));
			}

			//获取可见部门范围
			zsxx.setKejianbm(zhishiKjffService.findList(new ZhishiKjff(null,zsxx.getId())));
			zsxx.setKejianry(zhishiKjffRyService.findList(new ZhishiKjffRy(null,zsxx.getId())));

			//获取offices users
			List<Office> offices = new ArrayList<Office>();
			List<User> users = new ArrayList<User>();
			if (!Collections3.isEmpty(zsxx.getKejianbm())) {
				for(ZhishiKjff zhishiKjff:zsxx.getKejianbm()){
                    offices.add(officeService.get(zhishiKjff.getKejianbmid()));
                }
			}

			if (!Collections3.isEmpty(zsxx.getKejianry())) {
				for(ZhishiKjffRy zhishiKjffRy:zsxx.getKejianry()){
                    users.add(systemService.getUser(zhishiKjffRy.getKejianryid()));
                }
			}
			zsxx.setOffices(offices);
			zsxx.setUsers(users);

		}
		return zsxx;
	}

	private ZhishiXx sampleZhishi(ZhishiXx zsxx){
		if(StringUtils.isNotBlank(zsxx.getZhishilx())){
			//todo 知识类别名称全名称
			ZhishiLb zhishiLb = zhishiLbService.get(zsxx.getZhishilx());
			//zsxx.setZhishilxmc(zhishiLbService.getAllName(zhishiLb,"leixingmc"));
            if(null != zhishiLb){
                zsxx.setZhishilxmc(zhishiLb.getLeixingmc());
            }
		}
		return zsxx;
	}

	@Override
	public ZhishiXx get(String id) {
		ZhishiXx zsxx = super.get(id);
		return getAllXx(zsxx);
	}
	
	@Override
	public List<ZhishiXx> findList(ZhishiXx zhishiXx) {
		List<ZhishiXx> zhishiXxList = super.findList(zhishiXx);
		if(!Collections3.isEmpty(zhishiXxList)){
			for(ZhishiXx zx:zhishiXxList){
				sampleZhishi(zx);
			}
		}
		return zhishiXxList;
	}
	
	@Override
	public Page<ZhishiXx> findPage(Page<ZhishiXx> page, ZhishiXx zhishiXx) {
		Page<ZhishiXx> zhishiXxPage = super.findPage(page, zhishiXx);
		if(zhishiXxPage!=null && !Collections3.isEmpty(zhishiXxPage.getList())){
			for(ZhishiXx xx:zhishiXxPage.getList()){
				sampleZhishi(xx);
			}
		}
		return zhishiXxPage;
	}

	/**
	 * 知识撤销
	 * @param entity
	 */
	public void zhishiChexiao(ZhishiXx entity){
		if(entity!=null){
			ZhishiXx zhishiXX = super.get(entity.getId());
			zhishiXX.setZhishizt(ZhishiConstans.optStatus.DAITIJIAO);
			this.dao.update(zhishiXX);
		}
	}

	/**
	 * 更新浏览量（知识查看时 浏览量+1）
	 * 注意：浏览量的更新不考虑是否是同一人重复查看，每查看一次都会更新浏览量
	 * @param entity
	 */
	public void zhishill(ZhishiXx entity){
		if(entity!=null){
			entity = super.get(entity.getId());
			String liulanl  = entity.getLiulanl();
			int newliull=0;
			if(StringUtils.isNotBlank(liulanl)){
				newliull = Integer.parseInt(liulanl);
				newliull+=1;
			}else{
				newliull=1;
			}
			entity.setLiulanl(String.valueOf(newliull));
			super.save(entity);
		}
	}

	/**
	 * 更新下载量（下载量+1）
	 * 注意：下载成功即更新下载量
	 *       每次下载成功都会更新，不考虑是否同一人操作
	 * @param entity
	 */
	public void zhishixz(ZhishiXx entity){
		if(entity!=null){
			String xiazaiip = entity.getXiazaiip();
			entity = super.get(entity.getId());
			String xiazail  = entity.getXiazail();
			int newxiazail=0;
			if(StringUtils.isNotBlank(xiazail)){
				newxiazail = Integer.parseInt(xiazail);
				newxiazail+=1;
			}else{
				newxiazail=1;
			}
			entity.setXiazail(String.valueOf(newxiazail));
			super.save(entity);//更新下载量
			ZhishiXzjl xzjl = new ZhishiXzjl();
			xzjl.setZhishiid(entity.getId());
			xzjl.setXiazairid(UserUtils.getUser().getId());//下载人id
			xzjl.setXiazair(UserUtils.getUser().getName());
			xzjl.setXiazaisj(DateUtils.getDateTime());//下载时间
			xzjl.setXiazaiip(xiazaiip);//下载人IP
			zhishiXzjlService.save(xzjl);//保存下载记录
		}
	}
	/**
	 * 知识信息保存
	 * @param entity
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(ZhishiXx entity) {
		//保存知识信息
		if(entity.getId()==null || entity.getId().equals("")){
			//修改的时候 初始化某些信息
			entity.setLiulanl("0");
			entity.setXiazail("0");
			entity.setPinglunl("0");
			entity.setPingjunf("100");
		}
		//知识状态
		if (entity.getSubmit()!=null) {
			if(entity.getSubmit()){
                entity.setZhishizt(ZhishiConstans.optStatus.DAISHENHE);//待审核
            }else{
                entity.setZhishizt(ZhishiConstans.optStatus.DAITIJIAO);//待提交
            }
		}
		super.save(entity);
		//删除 知识-可见部门关系
		zhishiKjffService.deleteZhishiKjff(entity.getId());
		//删除 知识-可见人员关系
		zhishiKjffRyService.deleteZhishiKjffRy(entity.getId());
		//保存 知识-可见部门关系
		String kjbmids = entity.getKejianbmids();//可见部门ids
		if(StringUtils.isNotEmpty(kjbmids)) {
			String[] kjbmidArray = kjbmids.split(",");
			for (String bmid : kjbmidArray) {
				//保存可见部门知识关系
				zhishiKjffService.save(new ZhishiKjff(null,bmid,entity.getId()));
			}
		}

		//保存 知识-可见人员关系
		//可见人员
		String kjryids = entity.getKejianryids();
		if(StringUtils.isNotEmpty(kjryids)){
			String[] kjryidArray = kjryids.split(",");
			for(String ryid:kjryidArray){
				//保存可见部门知识关系
				zhishiKjffRyService.save(new ZhishiKjffRy(null,ryid,entity.getId()));
			}
		}

		//如果当前为待审核状态，则发起审核流程
		if(ZhishiConstans.optStatus.DAISHENHE.equals(entity.getZhishizt())){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("message", String.format(MessageConstants.zhishiAuditMessage.ADD,systemService.getUser(entity.getUpdateBy().getId()).getName(),entity.getZhishimc()));
			startAudit(entity.getId(),entity.getZhishimc()+MessageConstants.auditMessage.ADD,entity.getYijian(),map);
		}


	}

	/**
	 * 发起流程审核
	 */
	public void startAudit(String id,String title,String comment,Map<String,Object> map){
		try{
			logger.debug("开始发起知识审核流程ID：{}"+id);
			iActAuditService = getiActAuditService();
			iActAuditService.start(id, GlobalConstans.ActProcDefKey.ZHISHISH,title,comment,map);
		}catch (Exception e){
			logger.error("发起知识审核流程失败！",e);
		}
	}

	public IActAuditService getiActAuditService() {
		if(iActAuditService == null){
			iActAuditService = SpringContextHolder.getBean(IActAuditService.class);
		}
		return iActAuditService;
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(ZhishiXx zhishiXx) {
		super.delete(zhishiXx);
	}

	/**
	 * 保存审核信息
	 * @param id     实体id
	 * @param isPass 状态
	 */
	public void saveShenhe(String id,String isPass){
		if(StringUtils.isNoneBlank(id)){
			ZhishiXx entity = super.get(id);
			if(isPass.equals(ZhishiXx.CHEXIAO)){
				entity.setZhishizt(ZhishiConstans.optStatus.DAITIJIAO);//撤销
			}
			if (isPass.equals(ZhishiXx.TONGGUO)) {
				entity.setZhishizt(ZhishiConstans.optStatus.YISHENHE);//通过
				sendTodo(entity);
			}
			if (isPass.equals(ZhishiXx.BOHUI)) {
				entity.setZhishizt(ZhishiConstans.optStatus.BOHUI);//驳回
			}
			entity.preUpdate();
			this.dao.update(entity);

		}
	}

	private void sendTodo(ZhishiXx entity){
		SendTodoParam sendTodoParam = new SendTodoParam();
		sendTodoParam.setType(2);
		sendTodoParam.setSubject("您的知识新增"+entity.getZhishimc()+"申请已通过");

		sendTodoParam.setLink(Global.getConfig("activiti.form.server.url")+"/f/zhishi/detail?id="+entity.getId());
		sendTodoParam.setCreateTime(new Date());
		sendTodoParam.setModelId(IdGen.uuid());
		sendTodoParam.addTarget(systemService.getUser(entity.getUpdateBy().getId()).getLoginName());
		externalApi.sendTodo(sendTodoParam);
	}

	/**
	 * 知识撤销
	 * @param zhishiid
	 */
	public void undo(String zhishiid){

		//判断知识是不是已经审核了
		ZhishiXx zhishiXx = this.get(zhishiid);
		if(zhishiXx.getZhishizt().equals(ZhishiConstans.optStatus.DAISHENHE)){
			this.dao.updateZhishiStatus(zhishiid,ZhishiConstans.optStatus.DAITIJIAO);
		}

	}

	public List<String> getListByCreateBy(String userId){
		return this.dao.getListByCreateBy(userId);
	}


	/**
	 * 判断附件是不是属于知识的
	 * @param fileId
	 * @return
	 */
	public List<ZhishiXx> getZhishixxByFile(String fileId){

		return this.dao.getZhishixxByFile(fileId);
	}

	/**
	 * @Describe:根据分中心统计知识数据
	 * @Author:WuHui
	 * @Date:11:00 2020/8/31
	 * @param centerId
	 * @return:int
	*/
	public Integer countZhishiByCenter(String centerId) {
		//知识统计
		Integer cnt = dao.countZhishiByCenter(centerId);
		return cnt;
	}

	/**
	 * @Describe:根据分中心查询知识数据
	 * @Author:WuHui
	 * @Date:11:00 2020/8/31
	 * @param page
	 * @param centerId
	 * @return:com.demxs.tdm.common.persistence.Page<com.demxs.tdm.domain.resource.zhishi.ZhishiXx>
	*/
	public Page<ZhishiXx> findZhishiByCenter(Page<ZhishiXx> page, String centerId){
		List<ZhishiXx> lists = dao.findZhishiByCenter(page, centerId);
		page.setList(lists);
		if(page!=null){
			for(ZhishiXx s:page.getList()){
				sampleZhishi(s);
			}
		}
		return page;
	}

    /**
     * @author Jason
     * @date 2020/9/3 13:25
     * @params [zhishiXx]
     * 修改流程类型和图像文件id
     * @return int
     */
    public int updateTypeAndImg(ZhishiXx zhishiXx){
	    return this.dao.updateTypeAndImg(zhishiXx);
    }

    /**
    * @author Jason
    * @date 2020/9/3 18:08
    * @params [zhishiXx]
    * 修改流程类型
    * @return int
    */
    public int updateType(ZhishiXx zhishiXx){
	    return this.dao.updateType(zhishiXx);
    }

}