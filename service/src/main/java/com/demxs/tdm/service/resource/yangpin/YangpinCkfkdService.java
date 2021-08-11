package com.demxs.tdm.service.resource.yangpin;

import com.demxs.tdm.dao.resource.fujian.AttachmentDao;
import com.demxs.tdm.dao.resource.yangpin.YangpinCkfkdDao;
import com.demxs.tdm.dao.resource.yangpin.YangpinDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.fujian.Attachment;
import com.demxs.tdm.domain.resource.yangpin.*;
import com.demxs.tdm.domain.resource.yangpin.*;
import com.demxs.tdm.comac.common.constant.YpConstans;
import com.demxs.tdm.service.resource.fujian.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 样品入库/出库/返库操作Service
 * @author 詹小梅
 * @version 2017-06-15
 * 记录样品流转记录：入库，出库，返库，归还，自行处理
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class YangpinCkfkdService extends CrudService<YangpinCkfkdDao, YangpinCkfkd> implements IYangpinService{
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private YangpinDao yangpinDao;
	@Autowired
	private AttachmentDao attachmentDao;
	@Autowired
	private YangpinLbsxService yangpinLbsxService;
	@Autowired
	private YangpinLbService yangpinLbService;
	/*@Autowired
	private FangfaService fangfaService;*/
	@Autowired
	private YangpinBqService yangpinBqService;


	@Override
	public YangpinCkfkd get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<YangpinCkfkd> findList(YangpinCkfkd yangpinCkfkd) {
		return super.findList(yangpinCkfkd);
	}
	
	@Override
	public Page<YangpinCkfkd> findPage(Page<YangpinCkfkd> page, YangpinCkfkd yangpinCkfkd) {
		if(UserUtils.getUser()!=null){
			//yangpinCkfkd.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
			yangpinCkfkd.setDsf(dataScopeFilter(UserUtils.getUser(), "o", "u"));
		}
		return super.findPage(page, yangpinCkfkd);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(YangpinCkfkd yangpinCkfkd) {
		super.save(yangpinCkfkd);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(YangpinCkfkd yangpinCkfkd) {
		super.delete(yangpinCkfkd);
	}

	/**
	 * 获取样品属性，同时往出库单中加一列
	 * @param yangpinSx 属性实体
	 */
	public void saveYpsx(YangpinSx yangpinSx){
		String strLength = "200";//动态列字段长度
		if(StringUtils.isNoneBlank(yangpinSx.getZiduancd())){
			strLength = yangpinSx.getZiduancd();
		}else{
			strLength = "200";
		}
		//先把现出库单中属性的字段后缀
		String maxSx=dao.getMaxshuxing("FILED");//数据库字段名称前缀，后缀是1,2,3,4......N-1，N
		this.dao.addColumn("FILED"+maxSx,strLength);//字段
		this.dao.addComment("FILED"+maxSx);//注释
		//回写到样品属性，新增的属性和出库单中的属性就对应上了。
		/*IYangpinSxService iYangpinSxService =(IYangpinSxService) SpringContextHolder.getBean(IYangpinSxService.class);
		iYangpinSxService.updateFiled(yangpinSx.getId(),"FILED"+maxSx);*/
		//yangpinSx.setFiled("FILED"+maxSx);
		//yangpinSxDao.update(yangpinSx);//回写到样品属性，新增的属性和出库单中的属性就对应上了。
	}

	/**
	 * 样品入库记录
	 * @param yangpin
	 */
	public void saveYprkjl(Yangpin yangpin){
		YangpinCkfkd entity = new YangpinCkfkd();
		//String rkdid = IdGen.uuid();
		User user = UserUtils.getUser();
		entity.setLeixing(YpConstans.optStatusYplz.RUKU);//入库
		entity.setYangpinlx(yangpinLbService.get(yangpin.getYangpinlb())==null?"":yangpinLbService.get(yangpin.getYangpinlb()).getLeixingmc());//样品类型
		entity.setYangpinzj(yangpin.getId());
		entity.setYangpinzid(yangpin.getYangpinzid());//样品组id
		entity.setDengjirid(user.getId());
		entity.setDengjir(user.getName());
		/*SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date date=new java.util.Date();
		entity.setChukurq(sdf.format(date));//入库日期*/
		entity.setChukurq(DateUtils.getDate());
		entity.preInsert();
		this.dao.insert(entity);
		//entity.setId(rkdid);
	}

	/**
	 * 样品-自行处理
	 * 1.更新样品状态【已处理】
	 * 2.批量处理
	 * @param entity
	 */
	public void ypzxclSave(YangpinCkfkd entity){
		ArrayList list = new ArrayList();
		List<YangpinCkfkd> yangpinlz = new ArrayList<YangpinCkfkd>();
		YangpinCkfkd yangpinCkfkd =null;
		Yangpin[] yangpins = entity.getYangpinzjs();
		if(yangpins!=null) {
			for (int i = 0; i < yangpins.length; i++) {
				yangpinCkfkd = new YangpinCkfkd();
				yangpinCkfkd.setYangpinzj(yangpins[i].getYangpinid());
				yangpinCkfkd.setLeixing(YpConstans.optStatusYplz.ZIXINGCHULI);//流向：自行处理
				yangpinCkfkd.setYangpinlx(yangpinLbService.get(yangpins[i].getYangpinlb())==null?"":yangpinLbService.get(yangpins[i].getYangpinlb()).getLeixingmc());//样品类型
				yangpinCkfkd.setChukurq(DateUtils.getDate());//自行处理日期
				yangpinCkfkd.preInsert();
				list.add(yangpinCkfkd);
				yangpinlz.add(yangpinCkfkd);
			}
		}
		this.dao.batchInsert(yangpinlz);//保存流转记录
		//批量更新样品状态【已处理】
		batchUpdate(entity.getYangpinzjs(), YpConstans.optStatus.YICHULI);
	}
	/**
	 * 返库更新样品状态
	 * @param yangpinids 样品id
	 * @param yangpinzt  样品状态
	 * @param cunfangwz  存放位置
	 */
	public void batchUpdateForFK(Yangpin[] yangpinids,String yangpinzt,String cunfangwz,String cunfangwzid){
		List yangpinlist = new ArrayList();
		if(yangpinids!=null) {
			for (int i = 0; i < yangpinids.length; i++) {
				yangpinlist.add(yangpinids[i].getYangpinid());
			}
		}
		yangpinDao.batchUpdateForFK(yangpinlist,yangpinzt,cunfangwz,cunfangwzid);
	}
	/**
	 * 更新样品状态
	 * @param yangpinids 样品id
	 * @param yangpinzt  样品状态
	 */
	public void batchUpdate(Yangpin[] yangpinids,String yangpinzt){
		List yangpinlist = new ArrayList();
		if(yangpinids!=null) {
			for (int i = 0; i < yangpinids.length; i++) {
				yangpinlist.add(yangpinids[i].getYangpinid());
			}
		}
		yangpinDao.batchUpdate(yangpinlist,yangpinzt);
	}
	/**
	 * 更新样品是否留样
	 * @param yangpinids 样品id
	 * @param shifouly  是否留样
	 */
    public void batchUpdateLY (Yangpin[] yangpinids,String shifouly){
		List yangpinlist = new ArrayList();
		if(yangpinids!=null) {
			for (int i = 0; i < yangpinids.length; i++) {
				yangpinlist.add(yangpinids[i].getYangpinid());
			}
		}
		yangpinDao.batchUpdateLY(yangpinlist,shifouly);
	}
	/**
	 * 样品归还
	 * 1.归还后更新样品状态【已归还】
	 * 2.批量
	 * @param entity
	 */
	public void ypghSave(YangpinCkfkd entity){
		ArrayList list = new ArrayList();
		List<YangpinCkfkd> yangpinlz = new ArrayList<YangpinCkfkd>();
		YangpinCkfkd yangpinCkfkd =null;
		Yangpin[] yangpins = entity.getYangpinzjs();
		if(yangpins!=null) {
			for (int i = 0; i < yangpins.length; i++) {
				yangpinCkfkd = new YangpinCkfkd();
				yangpinCkfkd.setYangpinzj(yangpins[i].getYangpinid());
				yangpinCkfkd.setLeixing(YpConstans.optStatusYplz.GUIHUAN);//流向：归还
				yangpinCkfkd.setYangpinlx(yangpinLbService.get(yangpins[i].getYangpinlb())==null?"":yangpinLbService.get(yangpins[i].getYangpinlb()).getLeixingmc());//样品类型
				yangpinCkfkd.setGuihuanrid(entity.getGuihuanrid());
				yangpinCkfkd.setGuihuanr(entity.getGuihuanr());
				yangpinCkfkd.setChukurq(DateUtils.getDate());//归还日期
				yangpinCkfkd.preInsert();
				list.add(yangpinCkfkd);
				yangpinlz.add(yangpinCkfkd);
			}
		}
		this.dao.batchInsert(yangpinlz);//保存流转记录
		//批量更新样品状态【已归还】
		batchUpdate(entity.getYangpinzjs(), YpConstans.optStatus.YIGUIHUAN);
	}

	/**
	 * 样品出库
	 * 1.更新样品状态为【待检】
	 * 2.批量出库（出库记录）
	 * @param entity
	 */
	public void ypcksave(YangpinCkfkd entity){
		ArrayList list = new ArrayList();
		List<YangpinCkfkd> yangpinlz = new ArrayList<YangpinCkfkd>();
		YangpinCkfkd yangpinCkfkd =null;
		Yangpin[] yangpins = entity.getYangpinzjs();
		User user =null;
		if(StringUtils.isNotBlank(entity.getYangpinrid())){
			user= UserUtils.get(entity.getYangpinrid());
		}
		String chukuglrwid = entity.getChukuglrwid();
		if(yangpins!=null){
			for (int i = 0; i <yangpins.length ; i++) {
				yangpinCkfkd = new YangpinCkfkd();
				yangpinCkfkd.setYangpinzj(yangpins[i].getYangpinid());//样品id
				//yangpinCkfkd.setYangpinlx("1");//样品流向 出库试验
				if(user!=null){
					yangpinCkfkd.setYangpinrid(user.getId());
					yangpinCkfkd.setYangpinr(user.getName());
				}
				yangpinCkfkd.setLeixing(YpConstans.optStatusYplz.CHUKUJIANCE);//流向：出库
				yangpinCkfkd.setYangpinlx(yangpinLbService.get(yangpins[i].getYangpinlb())==null?"":yangpinLbService.get(yangpins[i].getYangpinlb()).getLeixingmc());//样品类型
				yangpinCkfkd.setChukurq(DateUtils.getDate());//出库日期
				yangpinCkfkd.setChukuglrwid(chukuglrwid); //出库关联任务id
				yangpinCkfkd.setShujubq(YpConstans.optShuJuBq.DANGQIANXMKY);//数据标签 默认：当前项目可用
				yangpinCkfkd.preInsert();
				list.add(yangpinCkfkd);
				yangpinlz.add(yangpinCkfkd);
			}
			this.dao.batchInsert(yangpinlz);
			batchUpdate(entity.getYangpinzjs(),YpConstans.optStatus.DAIJIAN);//更新样品状态：待检  更新样品的是否留样 为否

			batchUpdateLY(entity.getYangpinzjs(),YpConstans.YesOrNo.NO);//更新样品的是否留样 为否
		}
		/*IShiyanrwService iShiyanrwService = SpringContextHolder.getBean(IShiyanrwService.class);
		iShiyanrwService.chukuUpdateRwzt(entity.getChukuglrwid());//出库后更新任务状态*/
	}

	/**
	 *样品返库  样品状态更新为【在库】，并记录样品流转
	 */
	public void ypfksave(YangpinCkfkd entity){
		ArrayList list = new ArrayList();
		List<YangpinCkfkd> yangpinlz = new ArrayList<YangpinCkfkd>();
		YangpinCkfkd yangpinCkfkd =null;
		Yangpin[] yangpins = entity.getYangpinzjs();
		if(yangpins!=null){
			for (int i = 0; i <yangpins.length ; i++) {
				yangpinCkfkd=new YangpinCkfkd();
				yangpinCkfkd.setYangpinzj(yangpins[i].getYangpinid());
				yangpinCkfkd.setLeixing(YpConstans.optStatusYplz.FANKU);//流向：返库
				yangpinCkfkd.setYangpinlx(yangpinLbService.get(yangpins[i].getYangpinlb())==null?"":yangpinLbService.get(yangpins[i].getYangpinlb()).getLeixingmc());//样品类型
				yangpinCkfkd.setChukurq(DateUtils.getDate());//返库日期
				yangpinCkfkd.preInsert();
				list.add(yangpinCkfkd);
				yangpinlz.add(yangpinCkfkd);
			}
		}
		this.dao.batchInsert(yangpinlz);//保存流转记录
		batchUpdateForFK(entity.getYangpinzjs(),YpConstans.optStatus.ZAIKU,entity.getCunfangwz(),entity.getCunfangwzid());//更新样品状态：在库
	}

	/**
	 * 临时样品库-------单个样品返库
	 * 1.更新样品状态{在库}
	 * 2.插入流转记录 类型{入库}
	 * @param entity
	 */
	public void dgypfksave(YangpinCkfkd entity){
		if(entity!=null){
			entity.setLeixing(YpConstans.optStatusYplz.RUKU);
			super.save(entity);
			yangpinDao.updateYpzt(entity.getYangpinzj(),YpConstans.optStatus.ZAIKU,entity.getCunfangwzid());
		}
	}

	/**
	 * 批量保存样品出库/返库记录
	 * @param yangpinlzs
	 */
	public void batchInsert(List<YangpinCkfkd> yangpinlzs){
		this.dao.batchInsert(yangpinlzs);
	}


	/**
	 * 获取某一类样品的属性
	 * @param yangpinlbzj 样品类别id
	 *
	 * @return
	 */
	public Map getYangpinsx(String yangpinlbzj,String yangpinzj,String reneuzj,int type){
		String filedValue="";
		YangpinSx entity = new YangpinSx();
		entity.setYangpinlbzj(yangpinlbzj);
		YangpinLbsx lbsxs = new YangpinLbsx();
		lbsxs.setYangpinlbzj(yangpinlbzj);
		//获取属性
		List<YangpinLbsx> sx = yangpinLbsxService.findyangpinLbsxsList(lbsxs);
		Map<String,String> sxfiledMap = new HashMap<String, String>();
		Map sxMap = new HashMap();
		String filedNames = "";
		for(int i=0;i<sx.size();i++){
			filedNames = sx.get(i).getFiled();
			sxfiledMap.put(sx.get(i).getYangpinzwmc(),sx.get(i).getFiled());
			if(StringUtils.isNoneBlank(filedNames)){
				filedValue = this.dao.getFiledValue(filedNames,yangpinzj,reneuzj);
				//属性-属性值
				if(type==1){
					sxMap.put(sx.get(i).getYangpinzwmc(),filedValue);
				}else{
					//属性英文名-属性值
					sxMap.put(sx.get(i).getFiled(),filedValue);
				}
			}else{
				sxMap.put(sx.get(i).getFiled(),"");
			}
		}
		return sxMap;
	}
	/**
	 * 根据任务id获取样品出库记录
	 * 1.2样品信息：编号，名称，数据文件
	 * @param renwuzj 任务主键
	 * @return
	 */
   public  List<Map<String,Object>> getYangpinckxxForBaogao(String renwuzj){
	   List<Map<String,Object>> yangpinxx = new ArrayList<Map<String, Object>>();
	   YangpinCkfkd find = new YangpinCkfkd();
	   find.setChukuglrwid(renwuzj);
	   find.setType("2");
	   //获取任务出库单(包含出库单id（样品出库返库主键），样品信息：编号，名称，图片，数据文件（原始记录，报告）)
	   List<YangpinCkfkd> ckfkd = this.dao.findckd(find);
	   Attachment attachment = null;
	   for(int i=0;i<ckfkd.size();i++) {
		   Map<String, Object> dataValue = new HashMap<String, Object>();
		   Yangpin yangp = yangpinDao.get(ckfkd.get(i).getYangpinzj());
		   if(yangp!=null){
               dataValue.put("name",yangp.getYangpinbh()+"_"+yangp.getYangpinmc());
           }else{
               dataValue.put("name","出库样品为空");
           }
		   attachment = new Attachment();
		   attachment.setCodeId(ckfkd.get(i).getCkdid());
		   attachment.setColumnName("shujulist");
		   List<Attachment> fujian = attachmentDao.findList(attachment);
		   dataValue.put("fujian",fujian.toArray());
		   yangpinxx.add(dataValue);
	   }
	   return yangpinxx;
   }

	/**
	 * 根据任务id获取样品出库记录
	 * 获取任务出库单
	 * 1.1包含出库单id（样品出库返库主键），
	 * 1.2样品信息：编号，名称，图片，数据文件（原始记录，报告)
	 * 1.3样品属性（一类样品对应一组属性）
	 * @param renwuzj 任务主键
	 * @return
	 */
	public List<Map<String,Object>> getYangpinckxx(String renwuzj){
		List<Map<String,Object>> yangpinxx = new ArrayList<Map<String, Object>>();
		YangpinCkfkd find = new YangpinCkfkd();
		find.setChukuglrwid(renwuzj);
		find.setType("2");
		//获取任务出库单(包含出库单id（样品出库返库主键），样品信息：编号，名称，图片，数据文件（原始记录，报告）)
		List<YangpinCkfkd> ckfkd = this.dao.findckd(find);
		YangpinCkfkd entity =null;//样品出库单
		Attachment attachment = null;
		for(int i=0;i<ckfkd.size();i++){
			Map<String, Object> dataValue = new HashMap<String, Object>();
			entity=ckfkd.get(i);
			if(StringUtils.isNotBlank(entity.getYangpinzj())){
				Yangpin yangp = this.yangpinDao.get(entity.getYangpinzj());
				if(yangp!=null)
				{
					Map sxMap  = getYangpinsx(yangp.getYangpinlb(),entity.getYangpinzj(),renwuzj,1);//样品属性
					//试验样品数据附件
					attachment = new Attachment();
					attachment.setCodeId(entity.getCkdid());
					attachment.setColumnName("shujulist");
					List<Attachment> fujian = attachmentDao.findList(attachment);
					dataValue.put("ckdid",entity.getCkdid());
					dataValue.put("yangpinbh",yangp.getYangpinbh());
					dataValue.put("yangpinmc",yangp.getYangpinmc());
					dataValue.put("chushibm", yangp.getChushibm());
					dataValue.put("yangpinlbmc", yangp.getYangpinlbmc());
					dataValue.put("yangpincz", yangp.getYangpincz());
					dataValue.put("yangpinbz", yangp.getYangpinbz());
					dataValue.put("yangpinid",yangp.getId());
					dataValue.put("shujulist",fujian.toArray());//数据附件
					dataValue.put("shujubq",entity.getShujubq());//数据标签
					Map<String,String> sxmMap  = getYangpinsx(yangp.getYangpinlb(),entity.getYangpinzj(),renwuzj,2);//样品属性-值
					for (Map.Entry<String,String>  entry : sxmMap.entrySet()) {
						dataValue.put(entry.getKey(),entry.getValue());//属性A,B...AA,AB...
					}
					dataValue.put("shuxing",getyangpinsx(yangp.getYangpinlb()));//样品属性列表
					//出库任务样品标签
					dataValue.put("yangpinbq",getShujuBqByid(entity.getCkdid()));//数据标签
					yangpinxx.add(dataValue);
				}

			}
		}
		return yangpinxx;
	}



	/**
	 *  给大梅提供的API
	 *  根据任务ID 获取出库的样品信息 （样品编号、样品名称、样品ID、样品分类、附件列表（shujulist））
	 * @param renwuzj 任务is
	 * @return
	 */
	@Override
	public List<Map<String,Object>> getYangpinxx(String renwuzj){
		List<Map<String,Object>> yangpinxx = new ArrayList<Map<String, Object>>();
		YangpinCkfkd find = new YangpinCkfkd();
		find.setChukuglrwid(renwuzj);
		find.setType("2");
		//获取任务出库单(包含出库单id（样品出库返库主键），样品信息：编号，名称，图片，数据文件（原始记录，报告）)
		List<YangpinCkfkd> ckfkd = this.dao.findckd(find);
		//样品出库单
		YangpinCkfkd entity =null;
		for(int i=0;i<ckfkd.size();i++){
			Map<String, Object> dataValue = new HashMap<String, Object>();
			entity=ckfkd.get(i);
			Yangpin yangp = this.yangpinDao.get(entity.getYangpinzj());
			dataValue.put("ckdid",entity.getCkdid());//出库单id
			dataValue.put("yangpinbh",yangp.getYangpinbh());//样品编号
			dataValue.put("yangpinmc",yangp.getYangpinmc());//样品名称
			dataValue.put("id",yangp.getId());//样品id
			//样品数据文件
			Attachment attachment = new Attachment();
			attachment.setCodeId(entity.getId());
			attachment.setColumnName("shujulist");
			List<Attachment> fujian = attachmentDao.findList(attachment);
			dataValue.put("shujulist",fujian.toArray());
			dataValue.put("shujulist",fujian.toArray(new Attachment[fujian.size()]));//数据附件
			//样品 ：属性-属性值
			Map sxMap  = getYangpinsx(yangp.getYangpinlb(),entity.getYangpinzj(),renwuzj,1);
			dataValue.put("sxMap",sxMap);
			//样品：属性英文名-属性值
			Map<String,String> sxmMap  = getYangpinsx(yangp.getYangpinlb(),entity.getYangpinzj(),renwuzj,2);
			for (Map.Entry<String,String>  entry : sxmMap.entrySet()) {
				dataValue.put(entry.getKey(),entry.getValue());//属性A,B...AA,AB...
			}
			//样品属性列表
			dataValue.put("shuxing",getyangpinsx(yangp.getYangpinlb()));//样品属性列表
			yangpinxx.add(dataValue);
		}
		return yangpinxx;
	}
	/**
	 * 样品属性
	 * @param yangpinlbzj 样品类别主键
	 * @return
	 */
	public YangpinLbsx[] getyangpinsx(String yangpinlbzj){
		YangpinLbsx entity =  new YangpinLbsx();
		entity.setYangpinlbzj(yangpinlbzj);
		List<YangpinLbsx> lis = yangpinLbsxService.findyangpinLbsxsList(entity);
		YangpinLbsx[] arr = new YangpinLbsx[lis.size()];
		if(lis.size()>0){
			arr = lis.toArray(arr);
		}
		return  arr ;
	}



	/**
	 * 任务-保存出库单样品 数据文件
	 * 任务
	 * 样品属性：根据属性的英文名和出库单id，去更新出库单中属性值
	 * 数据文件
	 * @param
	 */
	public void saveCkyp(List<Map<String,Object>> yplist ){
		String chukudid = "";
		if(yplist.size()>0){
			for(int i=0;i<yplist.size();i++){
				Map<String, Object> map = new HashMap<String, Object>();
				map = yplist.get(i);
                //1.出库单id
                chukudid = map.get("ckdid").toString();
				Iterator it=map.entrySet().iterator();
				String key;
				String value;
				while(it.hasNext()){
					Map.Entry entry = (Map.Entry)it.next();
					key=entry.getKey().toString();
					if(key.contains("FILED")){
						//属性FILED（不是属性英文名，是出库单中动态列对应的属性标识）
						value=entry.getValue().toString();
						if(StringUtils.isNoneBlank(chukudid)){
							//todo 更新样品属性值（使用的方法比较笨，如果有好的方法可以优化！！）
							//更新样品属性值
							this.dao.updateFiledValues(key,value,chukudid);
						}
					}else if("shujulist".equals(key)){
					    if(!entry.getKey().equals("")){
							List<Map<String,Object>>  fujianlist =(ArrayList<Map<String,Object>>) map.get(key);
                            List<Attachment> attList = new ArrayList<Attachment>();
                            if(fujianlist.size()>0){
                               for(int j=0;j<fujianlist.size();j++){
                                   Attachment att = new Attachment();
                                   LinkedHashMap<String,Object> attMap = (LinkedHashMap<String,Object>) fujianlist.get(j);
                                   if(attMap.get("id")!=null && StringUtils.isNotBlank(attMap.get("id").toString())){
                                       String attid =  attMap.get("id").toString();
                                       att.setId(attid);
                                       attList.add(att);
                                   }
                               }
                               if(attList.size()>0){
                                   Attachment[] attArray = new Attachment[attList.size()];
                                   //数据文件
                                   if (attArray!=null){
                                       attachmentService.guanlianfujian(attList.toArray(attArray),chukudid,"shujulist");
                                   }
                               }
                           }
                        }
					}
				}
			}
		}
	}


	/**
	 * 任务完成后更新样品状态
	 * 更新依据：已消耗：当样品执行的任务使用的方法中，“是否消耗样品”字段为“是”时，任务完成后，样品状态置为“已消耗”。
	 *           已检
	 * 样品中没有（是否消耗样品）这个字段，方法中有；
	 * 1.任务和方法有关系，任务和样品有关系
	 * 2.根据任务主键去出库单中找相应的样品
	 * @param renwuzj   任务主键
	 * @param fangfazj  方法主键
	 */
	@Override
	public void updateYpzt(String renwuzj, String fangfazj){
		if(StringUtils.isNoneBlank(renwuzj) && StringUtils.isNoneBlank(fangfazj)){
			/*Fangfa fangfa = fangfaService.get(fangfazj);//方法
			YangpinCkfkd find = new YangpinCkfkd();
			find.setChukuglrwid(renwuzj);
			List<YangpinCkfkd> list = super.findList(find);//根据任务id获取出库单信息
			List listyp = new ArrayList();
			String yangpinzj = null;
			String yangpinzt = "";
			//是消耗样品才会更新样品装填为【已消耗】

			if(fangfa.getYangpinsfxh().equals(NengliConstans.YesOrNo.YES)){
				yangpinzt = YpConstans.optStatus.YIXIAOHAO;
			}else{
				yangpinzt = YpConstans.optStatus.YIJIAN;
			}
			if(list.size()>0){
				for(int i=0;i<list.size();i++){
					yangpinzj = list.get(i).getYangpinzj();
					listyp.add(yangpinzj);
				}
				yangpinDao.batchUpdate(listyp,yangpinzt);//更新样品状态为【已消耗】
			}*/
		}
	}
	 /**
		* 任务完成后更新样品状态 在检
     * @param renwuzj   任务主键
     * @param fangfazj  方法主键
     */
	@Override
	public void updateYpztZJ(String renwuzj, String fangfazj) {
		if(StringUtils.isNoneBlank(renwuzj) && StringUtils.isNoneBlank(fangfazj)){
			/*Fangfa fangfa = fangfaService.get(fangfazj);//方法
			YangpinCkfkd find = new YangpinCkfkd();
			find.setChukuglrwid(renwuzj);
			List<YangpinCkfkd> list = super.findList(find);//根据任务id获取出库单信息
			List listyp = new ArrayList();
			String yangpinzj = null;
			String yangpinzt =YpConstans.optStatus.ZAIJIAN;
			if(list.size()>0){
				for(int i=0;i<list.size();i++){
					yangpinzj = list.get(i).getYangpinzj();
					listyp.add(yangpinzj);
				}
				yangpinDao.batchUpdate(listyp,yangpinzt);//更新样品状态为【已消耗】
			}*/
		}
	}

	/**
	 * 根据任务id获取出库任务样品数量
	 * @param renwuzj 任务主键
	 * @return
	 */
	@Override
	public int getChukuYpslByrwid(String renwuzj){
		String ypcount = this.dao.getChukuYpslByrwid(renwuzj);
		int yangpinsl = 0;
		if(StringUtils.isNoneBlank(ypcount)){
			yangpinsl = Integer.parseInt(ypcount);
		}
		return yangpinsl;
	}
	/**
	 * 根据业务id 获取样品图片
	 * @param renwuids
	 * @return
	 */
	@Override
	public List<Map> getYanpginPic(List<String> renwuids) {
		List<Map> returnMap = new ArrayList<Map>();
		Map bucf = new HashMap();
		for (int ii = 0; ii < renwuids.size() ; ii++) {
			YangpinCkfkd  find = new YangpinCkfkd();
			find.setChukuglrwid(renwuids.get(ii));
			List<YangpinCkfkd> lis = findList(find);
			for (int i = 0; i <lis.size() ; i++) {
				YangpinCkfkd yangpin = lis.get(i);
				Attachment attachment = new Attachment();
				attachment.setCodeId(yangpin.getYangpinzj());
				attachment.setColumnName("yangpinzplist");
				List<Attachment> zplist =this.attachmentService.findList(attachment);
				for (int j = 0; j <zplist.size() ; j++) {
					Attachment attachment1 =zplist.get(j);
					String path = attachment1.getFilePath();
					if(bucf.get("path"+path)==null){
						bucf.put("path"+path,path);
						Map atamap = new HashMap();
						atamap.put("fileName",attachment1.getFileName());
						atamap.put("id",attachment1.getId());
						atamap.put("filePath",path);
						returnMap.add(atamap);
					}
				}
			}
		}
		return returnMap;
	}

	/**
	 * 更新出库任务数据标签(数据中心)
	 * @param yangpinCkfkd 出库单
	 */
	public void updateCkShujubq(YangpinCkfkd yangpinCkfkd){
		if(yangpinCkfkd!=null){
			YangpinCkfkd entity = super.get(yangpinCkfkd.getId());
			entity.setShujubq(yangpinCkfkd.getShujubq());
			super.save(entity);
		}
	}

	/**
	 * 根据出库单id获取样品标签
	 * @param chukudid 出库单id
	 * @return
	 */
	public YangpinBq[] getShujuBqByid(String chukudid){
		YangpinBq find = new YangpinBq();
		find.setChukudzj(chukudid);
		List<YangpinBq> listypbq = yangpinBqService.findList(find);
		Map<String,String> ypbqMap = new HashMap<String, String>();
		YangpinBq ypbq = null;
		YangpinBq[] bqarr = new YangpinBq[listypbq.size()];
		if(listypbq.size()>0){
			bqarr = listypbq.toArray(bqarr);
		}
		return bqarr;
	}





	/**
	 * 对象转map
	 * @param o
	 * @param map
	 * @return
	 */
	public Map ObjectTOMap(Object o,Map map){
		for (Field field : o.getClass().getDeclaredFields()){
			field.setAccessible(true); // 设置些属性是可以访问的
			//  String type = field.getType().toString();// 得到此属性的类型
			String key = field.getName();// key:得到属性名
			Object value = null;// 得到此属性的值
			try {
				value = field.get(o);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			if(value!=null){
				map.put(key,value);
			}
		}
		return  map;
	}

}