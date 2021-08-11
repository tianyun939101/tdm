package com.demxs.tdm.service.resource.biaozhunwuzhi;

import com.demxs.tdm.common.utils.*;
import com.demxs.tdm.dao.resource.biaozhunwuzhi.BiaozhunwzDao;
import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.Dict;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.DictUtils;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.*;
import com.demxs.tdm.domain.resource.biaozhunwuzhi.Biaozhunwz;
import com.demxs.tdm.domain.resource.biaozhunwuzhi.BiaozhunwzKc;
import com.demxs.tdm.domain.resource.biaozhunwuzhi.BiaozhunwzLzjl;
import com.demxs.tdm.domain.resource.fujian.Attachment;
import com.demxs.tdm.comac.common.constant.BiaozhunwzConstans;
import com.demxs.tdm.service.resource.fujian.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

/**
 * 标准物质Service
 * @author zhangdengcai
 * @version 2017-06-16
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class BiaozhunwzService extends CrudService<BiaozhunwzDao, Biaozhunwz> {

	@Autowired
	private BiaozhunwzDao biaozhunwzDao;
	@Autowired
	private BiaozhunwzCfwzService biaozhunwzCfwzService;
	@Autowired
	private BiaozhunwzLzjlService biaozhunwzLzjlService;
	@Autowired
	private AttachmentService attachmentService;

	@Override
	public Biaozhunwz get(String id) {
		return super.get(id);
	}

	@Override
	public List<Biaozhunwz> findList(Biaozhunwz biaozhunwz) {
		return super.findList(biaozhunwz);
	}

	@Override
	public Page<Biaozhunwz> findPage(Page<Biaozhunwz> page, Biaozhunwz biaozhunwz) {
//		if(UserUtils.getUser()!=null){
//
//			biaozhunwz.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
//		}
		return super.findPage(page, biaozhunwz);
	}

	public Page<Biaozhunwz> findPageForOther(Page<Biaozhunwz> page, Biaozhunwz biaozhunwz) {
		page.setOrderBy("a.update_date desc");
//		if(UserUtils.getUser()!=null){
//			biaozhunwz.setDangqianR(UserUtils.getUser());
//			biaozhunwz.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
//		}
		return super.findPage(page, biaozhunwz);
	}

	/**
	 * 保存
	 * @param biaozhunwz
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(Biaozhunwz biaozhunwz) {
		String id = "";
		String zls = biaozhunwz.getZiliao()==null?"":biaozhunwz.getZiliao();//资料附件ID
		Attachment[] zlAtt = fujianshuzu(zls);
		biaozhunwz.setZiliao("");//清空、不存库
		if(StringUtils.isNotBlank(biaozhunwz.getCunchuweiz())){
			biaozhunwz.setCunchuweizmc(this.cunfangwzmc(biaozhunwz.getCunchuweiz()));//存储位置完整名称
		}
		if(StringUtils.isNotBlank(biaozhunwz.getId())){//修改
			id = biaozhunwz.getId();
			//标准物质编号/名称/证书号/型号/计量单位 有改变时，修改入库记录
			Biaozhunwz oldBzwz = get(id);//原数据
			if((oldBzwz.getBiaozhunwzmc()!=null && !oldBzwz.getBiaozhunwzmc().equals(biaozhunwz.getBiaozhunwzmc()))
					|| (oldBzwz.getBiaozhunwzbh()!=null && !oldBzwz.getBiaozhunwzbh().equals(biaozhunwz.getBiaozhunwzbh()))
					|| (oldBzwz.getBiaozhunwzxh()!=null && !oldBzwz.getBiaozhunwzxh().equals(biaozhunwz.getBiaozhunwzxh()))
					|| (oldBzwz.getJiliangdw()!=null && !oldBzwz.getJiliangdw().equals(biaozhunwz.getJiliangdw()))
					){
				if(biaozhunwz.getSubmit()){//提交
					saveLzjl(biaozhunwz, "update");//保存流转记录
				}
			}
			if(biaozhunwz.getSubmit()){//操作为“提交”，并且状态为“待提交”的，改变其状态为“空闲”
                biaozhunwz.setBiaozhunwzzt(BiaozhunwzConstans.biaoZhunwzzt.KONGXIAN);
            }
			super.save(biaozhunwz);
		}else{//新增
			//不允许保存相同编号的标准物质
			Biaozhunwz bzwz = getByBianh(biaozhunwz.getBiaozhunwzbh());
			if(bzwz!=null){
				throw new RuntimeException("已存在具有该编号的标准物质！");
			}else{
				User crrentUser = UserUtils.getUser();
				if(crrentUser!=null){
					biaozhunwz.setDengjir(crrentUser==null?"":crrentUser.getName());
				}
				biaozhunwz.setDengjirq(DateUtils.getDate());
				biaozhunwz.preInsert();
				id = IdGen.uuid();
				biaozhunwz.setId(id);//重新覆盖ID
				if(!biaozhunwz.getSubmit()){//暂存
					biaozhunwz.setBiaozhunwzzt(BiaozhunwzConstans.biaoZhunwzzt.DAITIJ);
				}
				this.dao.insert(biaozhunwz);
				if(biaozhunwz.getSubmit()) {//提交
					saveLzjl(biaozhunwz, "save");//保存流转记录
				}
			}
		}

		//附件关联业务ID
		if(zlAtt.length>0){
			attachmentService.guanlianfujian(zlAtt, id, "");
		}
        saveQRCodeImg(biaozhunwz);//生产二维码
	}

	/**
	 * 保存 用于出库调用
	 * @param biaozhunwz
	 */
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void saveOther(Biaozhunwz biaozhunwz) {
		super.save(biaozhunwz);
	}


	/**
	 * 获得附件数组
	 * @param attIds
	 * @return
	 */
	public Attachment[] fujianshuzu(String attIds){
		List<Attachment> attList = new ArrayList<Attachment>();
		if(attIds!=null && attIds!=""){
			if(attIds.contains(",")){
				String[] tpArr = attIds.split(",");
				for(int i=0; i<tpArr.length; i++){
					Attachment att = new Attachment();
					att.setId(tpArr[i]);
					attList.add(att);
				}
			}else{
				Attachment att = new Attachment();
				att.setId(attIds);
				attList.add(att);
			}
		}

		Attachment[] attArray = new Attachment[attList.size()];
		return attList.size()==0 ? new Attachment[0] : attList.toArray(attArray);
	}

	/**
	 * 保存流转记录
	 * @param biaozhunwz
	 * @param saveOrupdate 保存或新增
	 */
	public void saveLzjl(Biaozhunwz biaozhunwz, String saveOrupdate){
		BiaozhunwzLzjl biaozhunwzLzjl = new BiaozhunwzLzjl();//流转记录
		biaozhunwzLzjl.setLiuxiang(BiaozhunwzConstans.biaoZhunwzLzlx.RUKU);
		biaozhunwzLzjl.setBiaozhunwzzj(biaozhunwz.getId());
		if("update".equals(saveOrupdate)){
			List<BiaozhunwzLzjl> lzjls = biaozhunwzLzjlService.findList(biaozhunwzLzjl);
			if(lzjls!=null && !lzjls.isEmpty()){
				biaozhunwzLzjl = lzjls.get(0);
			}
		}
		biaozhunwzLzjl.setRiqi(DateUtils.getDate());
		biaozhunwzLzjl.setZhengshuh(biaozhunwz.getZhengshuh());
		biaozhunwzLzjl.setBiaozhunwzbh(biaozhunwz.getBiaozhunwzbh());
		biaozhunwzLzjl.setBiaozhunwzmc(biaozhunwz.getBiaozhunwzmc());
		biaozhunwzLzjl.setGuigexh(biaozhunwz.getBiaozhunwzxh());
		biaozhunwzLzjl.setJiliangdw(biaozhunwz.getJiliangdw());
		biaozhunwzLzjl.setCaozuormc(biaozhunwz.getYanshour());//入库人 （9月底需求）
		biaozhunwzLzjl.setDengjirmc(UserUtils.get(biaozhunwz.getCreateById())==null?"":UserUtils.get(biaozhunwz.getCreateById()).getName());//（9月底需求）
		biaozhunwzLzjlService.save(biaozhunwzLzjl);
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(Biaozhunwz biaozhunwz) {
		super.delete(biaozhunwz);
	}

	/**
	 * 批量删除
	 * @param ids
	 */
	public void deleteMore(String ids){
		String[] idArray = null;
		if(StringUtils.isNotBlank(ids)){
			if(ids.contains(",")){
				idArray = ids.split(",");
			}else{
				idArray = new String[1];
				idArray[0] = ids;
			}
		}
		Biaozhunwz biaozhunwz = new Biaozhunwz();
		biaozhunwz.setArrIDS(idArray);
		biaozhunwzDao.deleteMore(biaozhunwz);
	}

	/**
	 * 获取存放位置全名称
	 * @param cunfangddId
	 * @return
	 */
	public String cunfangwzmc(String cunfangddId){
		return biaozhunwzCfwzService.cunfangwzmc(cunfangddId);
	}

	/**
	 * 根据标准物质编号 获取标准物质
	 * @param biaozhunwzbh
	 * @return
	 */
	public Biaozhunwz getByBianh(String biaozhunwzbh){
		Biaozhunwz bzwz = null;
		if(StringUtils.isNotBlank(biaozhunwzbh)){
			Biaozhunwz biaozhunwz = new Biaozhunwz();
			biaozhunwz.setBiaozhunwzbh(biaozhunwzbh);
			List<Biaozhunwz> bzwzs = this.dao.getByBianh(biaozhunwz);
			if(bzwzs!=null && !bzwzs.isEmpty()){
				bzwz = bzwzs.get(0);
			}
		}
		return bzwz;
	}

	/**
	 * 库存
	 * @param biaozhunwzKc
	 * @return
	 */
	public  Page<BiaozhunwzKc> kuncun(Page<BiaozhunwzKc> page, BiaozhunwzKc biaozhunwzKc) {
		if(UserUtils.getUser()!=null){
			biaozhunwzKc.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		}
		biaozhunwzKc.setBiaozhunwzzt(BiaozhunwzConstans.biaoZhunwzzt.KONGXIAN);//只统计空闲状态的
		biaozhunwzKc.setPage(page);
		List<BiaozhunwzKc> kcs = this.dao.kucun(biaozhunwzKc);
		page.setCount(kcs==null?0:kcs.size());
		page.setList(kcs);
		return page;
	}

	/**
	 * 批量修改标准物质状态
	 * @param biaozhunwzs
	 */
	public void piliangXgzt(List<Biaozhunwz> biaozhunwzs){
		List<Biaozhunwz> pandianbzwzs = new ArrayList<Biaozhunwz>();//状态发生改变的标准物质集合
		if(biaozhunwzs!=null && !biaozhunwzs.isEmpty()){
			for (Biaozhunwz bzwz  : biaozhunwzs) {
				if(StringUtils.isNotBlank(bzwz.getBiaozhunwzzt()) && StringUtils.isNotBlank(bzwz.getId())){
					List<Dict> zts = DictUtils.getDictList("biaozhunwzzt");
					if(zts!=null && !zts.isEmpty()){
						for (Dict zt : zts) {
							if(zt.getLabel().equals(bzwz.getBiaozhunwzzt())){
								bzwz.setBiaozhunwzzt(zt.getValue());
								pandianbzwzs.add(bzwz);
							}
						}
					}
				}
			}
		}
		this.dao.piliangXgzt(pandianbzwzs);
	}

	/**
	 * 1.生成二维码图片
	 * 2.保存图片信息
	 * @param biaozhunwz
	 */
	public void saveQRCodeImg(Biaozhunwz biaozhunwz){
		//文件路径
		String filePath = Global.getUserfilesBaseDir()+ Calendar.getInstance().get(YEAR)+"/"+ Calendar.getInstance().get(MONTH)+"/image/png/";
		FileUtils.createDirectory(filePath);
		filePath=filePath+biaozhunwz.getBiaozhunwzbh()+".png";
		//一厘米  28*28PX
		int width = 28;
		int height = 28;
		//生成二维码图片
		ZxingHandler.createQRCode(biaozhunwz.getBiaozhunwzbh(),width,height,filePath);
		//保存文件信息
		Attachment att = new Attachment();
		att.setCodeId(biaozhunwz.getId());
		att.setFileName(biaozhunwz.getBiaozhunwzbh());
		att.setColumnName("bzwz_erweimtp");
		att.setFilePath(filePath);
		att.setFileDesc("标准物质编号二维码图片");
		att.setCreateTime(biaozhunwz.getCreateDate());
		att.setCreateUserid(UserUtils.getUser().getId());
		att.setCreateUsername(UserUtils.getUser().getName());
		attachmentService.save(att);
	}

	/**
	 * 更改状态为“已过期”
	 * 将过“有效期至”小于当当前日期，且状态不为“已过期”的改为"已过期"
	 */
	public void changeStatusToGuoqi(){
		Biaozhunwz bzwz = new Biaozhunwz();
		this.dao.changeStatusToGuoqi(bzwz);
	}

	/**
	 * 出库两天后，“是否消耗”为是、且状态仍为“使用中”的，状态改为“已消耗”
	 */
	public void changeStatusToXiaohao(){
		Biaozhunwz bzwz = new Biaozhunwz();
		String ids = this.dao.changeToXhIds(bzwz);
		String[] idArr = ids.split(",");
		if(idArr!=null && idArr.length>0){
			bzwz.setArrIDS(idArr);
			this.dao.changeStatusToXiaohao(bzwz);
		}
	}
}