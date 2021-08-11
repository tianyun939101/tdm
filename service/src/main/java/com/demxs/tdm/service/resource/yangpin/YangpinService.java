package com.demxs.tdm.service.resource.yangpin;

import com.demxs.tdm.common.utils.*;
import com.demxs.tdm.dao.resource.dto.YangPinDto;
import com.demxs.tdm.dao.resource.fujian.AttachmentDao;
import com.demxs.tdm.dao.resource.yangpin.YangpinCkfkdDao;
import com.demxs.tdm.dao.resource.yangpin.YangpinDao;
import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.*;
import com.demxs.tdm.domain.resource.fujian.Attachment;
import com.demxs.tdm.domain.resource.yangpin.WeituoYp;
import com.demxs.tdm.domain.resource.yangpin.Yangpin;
import com.demxs.tdm.domain.resource.yangpin.YangpinCkfkd;
import com.demxs.tdm.comac.common.constant.YpConstans;
import com.demxs.tdm.service.resource.fujian.AttachmentService;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

/**
 * 样品信息操作Service
 * @author 詹小梅
 * @version 2017-06-15
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class YangpinService extends CrudService<YangpinDao, Yangpin> {

	@Autowired
	private YangpinCkfkdDao yangpinCkfkdDao;
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private YangpinCkfkdService yangpinCkfkdService;
	@Autowired
	private WeituoYpService weituoYpService;
	@Autowired
	private AttachmentDao attachmentDao;

	private final String yangpinxlh = "1";
	private final String yangpinzt = "0";

	@Override
	public Yangpin get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<Yangpin> findList(Yangpin yangpin) {
		return super.findList(yangpin);
	}

	public List<YangPinDto> findYangPinDtoList(Yangpin yangpin) {
		return this.dao.findYangPinDtoList(yangpin);
	}
	
	@Override
	public Page<Yangpin> findPage(Page<Yangpin> page, Yangpin yangpin) {
		if(UserUtils.getUser()!=null){
			//yangpin.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "a4"));
			yangpin.setDsf(dataScopeFilter(UserUtils.getUser(), "o", "a4"));
			}
		return super.findPage(page, yangpin);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(Yangpin yangpin) {
		super.save(yangpin);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(Yangpin yangpin) {
		super.delete(yangpin);
	}

	/**
	 * 样品确认插入库
	 * @param weituodzj 申请单主键
	 ** 样品编号规则：S+业务单号+组号+流水号（01,02.......）
	 */
	public void plinsert(String weituodzj){
		WeituoYp weituoyp = new WeituoYp();
		weituoyp.setWeituodzj(weituodzj);
		List<WeituoYp> list1 = this.weituoYpService.findList(weituoyp);
		for(int i=0;i<list1.size();i++){
			weituoyp = list1.get(i);
			int shuliang = Integer.parseInt(weituoyp.getShuliang());
			for(int j = 1;j<shuliang+1;j++){
				Yangpin yangpin = new Yangpin();
				BeanUtils.copyProperties(weituoyp, yangpin);
				saveYangpin(j,weituoyp,yangpin);//j 同时代表这个样品组的序列
			}
		}
	}
	/**
	 * 样品插入库
	 * @param i
	 * @param weituoyp 申请样品
	 * @param entity   样品实体
	 * 样品编号规则：S+业务单号+组号+流水号（01,02.......）
	 */
	public void saveYangpin(int i, WeituoYp weituoyp, Yangpin entity){
		String yangpinid = IdGen.uuid();
		entity.setId(null);
		entity.setShuliang("1");
		entity.setYangpinzid(weituoyp.getId());
		entity.setShifoumy(YpConstans.YesOrNo.YES);//是否母样
		entity.setYangpinzt(YpConstans.optStatus.DAIRUKU);//样品入库后 状态为：在库
		entity.setWeituodzj(weituoyp.getWeituodzj());
		entity.setYewudh(weituoyp.getYewudh());
		entity.setYewudmc(weituoyp.getYewudmc());
		String chushibm=weituoyp.getBianmaqz()==null ? "" :weituoyp.getBianmaqz();//初始编码前缀
		if(i==1 || i<10){
			entity.setYangpinbh(weituoyp.getYangpinbh()+"0"+i);
			entity.setChushibm(chushibm.concat("-0")+i);
		}else{
			entity.setYangpinbh(weituoyp.getYangpinbh()+i);
			entity.setChushibm(chushibm.concat("-")+i);
		}
		entity.preInsert();
		entity.setId(yangpinid);
		this.dao.insert(entity);
	}
	/**
	 * 样品入库
	 * @param entity   样品实体
	 */
	public void plsaveyangpin(Yangpin entity){
		String[] arrids = entity.getArrIDS();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=new Date();
		//样品照片
		Attachment fujian[] = entity.getYangpinzplist();
		Attachment attypzp = null;
		for (int i = 0; i <arrids.length ; i++) {
			Yangpin oldY = this.get(arrids[i]);
			ObjectUtils.returnForUpdateObject(oldY,entity);
			oldY.setYangpinzt(YpConstans.optStatus.ZAIKU);//样品入库后 状态为：在库
			oldY.setShifouly(Global.NO);
			oldY.setRukusj(sdf.format(date));//入库时间
			if(StringUtils.isNotBlank(entity.getLiuyangsl())){//留样数量
				if(Integer.valueOf(entity.getLiuyangsl())>0 && i>=(arrids.length-Integer.valueOf(entity.getLiuyangsl()))){//最后“留样数量”个
					oldY.setShifouly(Global.YES);//是否留样：留样
					oldY.setYangpinzt(YpConstans.optStatus.LIUYANG);
				}
			}
			this.save(oldY);
			if(fujian!=null){
				for (int j = 0; j < fujian.length; j++) {
					Attachment ypzp =attachmentService.get(fujian[j].getId()) ;
					attypzp = new Attachment();
					BeanUtils.copyProperties(ypzp,attypzp);
					attypzp.setColumnName("yangpinzplist");
					attypzp.setCodeId(arrids[i]);
					attypzp.preInsert();
					attypzp.setId(IdGen.uuid());
					attachmentDao.insert(attypzp);
				}

			}
			saveQRCodeImg(oldY);
			yangpinCkfkdService.saveYprkjl(oldY);//保存样品入库记录
		}



	}

	/**
	 * 模拟样品入库用于内部测试和预约业务
	 * 一次性将申请单中所有的样品组全部入库，存放位置为空
	 * 内部测试业务的样品如需要放入样品库管理则在转库（代替返库）时填写存放位置
	 * 预约业务的样品基本不放入样品库管理
	 * @param weituodzj 业务单主键
	 */
	public void moNiYangPinRuKu(String weituodzj){
		//1、查找申请样品组
		WeituoYp weituoYpSearch = new WeituoYp();
		weituoYpSearch.setWeituodzj(weituodzj);
		List<WeituoYp> weituoYpList = weituoYpService.findList(weituoYpSearch);
		//2、根据申请样品组入库
		for(WeituoYp weituoYp : weituoYpList){
			String sypShuliang = weituoYp.getShuliang();
			if(StringUtils.isNotBlank(sypShuliang)){
//				List<Yangpin> yangpins = Lists.newArrayList();
				int ypShuliang = Integer.parseInt(sypShuliang);
				for(int i = 1; i <= ypShuliang; i++){
					Yangpin yangpin = new Yangpin();
					BeanUtils.copyProperties(weituoYp,yangpin);
					yangpin.setShuliang("1");
					yangpin.setYangpinzid(weituoYp.getId());
					yangpin.setYangpinzt(YpConstans.optStatus.ZAIKU);
					yangpin.setRukusj(DateUtils.getDateTime());
					yangpin.setShifoumy(YpConstans.YesOrNo.YES);//是否母样
					//获取流水号
					String liushuhao = StringUtils.frontCompWithZore(i,2);
					yangpin.setYangpinbh(yangpin.getYangpinbh()+yangpin.getYangpinzh()+liushuhao);//样品编号
					yangpin.setChushibm(yangpin.getBianmaqz()+"-"+liushuhao);//初始编码
					//送样人就是下申请人
					User user = UserUtils.getUser();
					yangpin.setSongyangr(user.getName());
					yangpin.setSongyangrid(user.getId());
					yangpin.preInsert();
					this.dao.insert(yangpin);//入库
					yangpinCkfkdService.saveYprkjl(yangpin);//保存样品入库记录
//					yangpins.add(yangpin);
				}
			}
		}
		//3、将申请样品状态变更为待检
		weituoYpService.updateStatusToDaiJian(weituodzj);
	}

	/**
	 * @Author：詹小梅
	 * @param： * @param null
	 * @Description：出库时批量更新样品状态【在检】
	 * @Date：16:56 2017/6/19
	 * @Return：
	 * @Exception：
	 */
	/*public void batchUpdate(String[] yangpinzids,String yangpinzt){
		this.dao.batchUpdate(yangpinzids, yangpinzt);
	}*/

	/**
	 *  批量保存样品组(暂不用)
	 * @param yangpins
	 */
	public void batchInsert(List<Yangpin> yangpins){
		this.dao.batchInsert((Yangpin[])yangpins.toArray());
	}


	/**
	 * @Author：詹小梅
	 * @param： * @param null
	 * @Description： 样品组入库
	 * @Date：10:12 2017/6/20
	 * @Return：
	 * @Exception：
	 */
	public void saveYpz(List<Yangpin> yangpins){
		//修改或提交时先删掉原样品组，再重新插入
		deleteGlx(yangpins);
		String yangpinid = "";
		int yangpinbh = 0;//样品组编号
		for(Yangpin yangpin:yangpins){
			//保存样品组
			yangpinid= IdGen.uuid();
			String yewudh = yangpin.getYewudh().substring(1);
			//样品组编号：申请单业务单号+.....
			if(yangpinbh<10){
				yangpin.setYangpinbh("S"+yewudh+"0"+yangpinbh++);
			}else{
				yangpin.setYangpinbh("S"+yewudh+yangpinbh++);
			}
			yangpin.setId(yangpinid);
			yangpin.setYangpinxlh(yangpinxlh);//样品编号序列号
			yangpin.setChushibmxlh(yangpinxlh);//初始编码序列号
			yangpin.preInsert();
			yangpin.setId(yangpinid);
			this.dao.insert(yangpin);//保存样品组信息
			//this.save(yangpin);
			//保存样品组入库记录
			YangpinCkfkd ckfkd = new YangpinCkfkd();
			ckfkd.setYangpinzid(yangpinid);
			ckfkd.setYangpinlx(yangpinzt);//入库
			ckfkd.setYangpinr(yangpin.getSongyangr());//送样人
			ckfkd.setLeixing(yangpinzt);//入库
			ckfkd.setShuliang(yangpin.getShuliang());//入库数量
			yangpinCkfkdDao.insert(ckfkd);
		}
	}

	public void deleteGlx(List<Yangpin> yangpins){
		if (yangpins.size()>0){
			if(yangpins.get(0)!=null){
				this.dao.deleteYangpinzu(yangpins.get(0).getWeituodzj());
				this.yangpinCkfkdDao.deleteYangpinrkjl(yangpins.get(0).getWeituodzj());
			}
		}
		/*String[] yangpinids;
		List ypjlList = new ArrayList();
		List mapList = new ArrayList();
		Map<String, Object> entityMap = Maps.newHashMap();
		for(Yangpin yangpin:yangpins){
			entityMap.put("weituodzj",yangpin.getWeituodzj());
			entityMap.put("fangfazj",yangpin.getFangfazj());
			entityMap.put("shifouwtdypz",yangpin.getShifoubzwz());
			ypjlList.add(yangpin.getId());
			mapList.add(entityMap);
		}*/
	}


	/**
	 * 获取样品的拆样最大编号
	 * @return
	 */
	public int getChaiyangmaxno(String yangpinbh){
		return this.dao.getChaiyangmaxno(yangpinbh);
	}
	/**
	 * 批量保存样品信息
	 * @param yangpins
	 */
	public void batchInsertYangpin( List<Yangpin> yangpins){
		this.dao.batchInsertYangpin(yangpins);
	}


	/**
	 * 根据样品编号获取样品列表
	 * @param entity
	 * @return
	 */
	public Yangpin getYangpinxxByYpbh(Yangpin entity){
		return this.dao.getYangpinxxByYpbh(entity);
	}

	/**
	 * 样品标签打印数据集
	 * @param yangpins
	 * @return
	 */
	public  List<Map<String,String>>  getYangpinBqData(Yangpin yangpins){
		List<Map<String,String>> dataValue = new ArrayList<Map<String, String>>();
		if(yangpins.getArrIDS()!=null){
			String[] ids = yangpins.getArrIDS();
			for(String id:ids){
				//获取数据
				Yangpin yangpin = super.get(id);
				Map<String,String> yangpinMap = Maps.newHashMap();
				yangpinMap = this.dao.getMapData(id);
				dataValue.add(yangpinMap);
			}
		}
		return dataValue;
	}

	/**
	 * 1.生成二维码图片
	 * 2.保存图片信息
	 * @param yangpin
	 */
	public void saveQRCodeImg(Yangpin yangpin){
		//文件路径
		String filePath = Global.getUserfilesBaseDir()+ Calendar.getInstance().get(YEAR)+"/"+Calendar.getInstance().get(MONTH)+"/image/png/";
        FileUtils.createDirectory(filePath);
        filePath=filePath+yangpin.getYangpinbh()+".png";
		//一厘米  28*28PX
		int width = 28;
		int height = 28;
		//生成二维码图片
		ZxingHandler.createQRCode(yangpin.getYangpinbh(),width,height,filePath);
		//保存文件信息
		Attachment att = new Attachment();
		att.setCodeId(yangpin.getId());
		att.setFileName(yangpin.getYangpinbh());
		att.setColumnName("yp_erweimtp");
		att.setFilePath(filePath);
		att.setFileDesc("样品编号二维码图片！");
        att.setCreateTime(yangpin.getCreateDate());
        att.setCreateUserid(UserUtils.getUser().getId());
        att.setCreateUsername(UserUtils.getUser().getName());
        att.preInsert();
        this.attachmentDao.insert(att);
	}

}