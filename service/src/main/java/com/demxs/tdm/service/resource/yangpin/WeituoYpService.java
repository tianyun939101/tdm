package com.demxs.tdm.service.resource.yangpin;

import com.demxs.tdm.dao.resource.dto.YangPinDto;
import com.demxs.tdm.dao.resource.yangpin.WeituoYpDao;
import com.demxs.tdm.dao.resource.yangpin.YangpinCkfkdDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.Collections3;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.yangpin.WeituoYp;
import com.demxs.tdm.comac.common.constant.YpConstans;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 申请单关联样品组Service
 * @author 詹小梅
 * @version 2017-06-22
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class WeituoYpService extends CrudService<WeituoYpDao, WeituoYp> {
	@Autowired
	private YangpinCkfkdDao yangpinCkfkdDao;
	private final String yangpinxlh = "1";
	int yangpinzh = 0;//样品组编号




	@Override
	public WeituoYp get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<WeituoYp> findList(WeituoYp weituoYp) {
		return super.findList(weituoYp);
	}
	
	@Override
	public Page<WeituoYp> findPage(Page<WeituoYp> page, WeituoYp weituoYp) {
		return super.findPage(page, weituoYp);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(WeituoYp weituoYp) {
		super.save(weituoYp);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(WeituoYp weituoYp) {
		super.delete(weituoYp);
	}

	/**
	 * @Author：詹小梅
	 * @param： * @param null
	 * @Description：获取样品对应的申请单信息
	 * @Date：17:55 2017/6/22
	 * @Return：
	 * @Exception：
	 */
	public WeituoYp getYangpinssyw(String yangpinzj){
		return this.dao.findList(yangpinzj);
	}



	/**
	 * @Author：詹小梅
	 * @param： * @param null
	 * @Description：获取申请单,方法关联样品组
	 * @Date：16:59 2017/6/22
	 * @Return：
	 * @Exception：
	 */
	public List<YangPinDto> findweituoyp(String fanganzj, String fangfazj, String weituodzj){
		WeituoYp entity = new WeituoYp();
		entity.setFanganid(fanganzj);
		entity.setFangfazj(fangfazj);
		entity.setWeituodzj(weituodzj);
		return this.dao.findweituoyp(entity);
	}


	/**
	 * @Author：詹小梅
	 * @param： * @param null
	 * @Description：保存样品组
	 * 流水号均为两位
	 * 样品组编号规则：申请单业务单号+组号（未入库） ，入库时样品编号会在该编号的基础上加上两位流水号（01,02,03.......）
	 * @Date：19:57 2017/6/22
	 * @Return：
	 * @Exception：
	 */
	public void saveWeituodypz(List<YangPinDto> weituoyps){
		if(weituoyps.size()>0){
			WeituoYp find = new WeituoYp();
			deleteWeituodypz(weituoyps);
			int yangpinzhone = 0;
			//数据库查询当前申请最大的样品组号

			String maxNo = this.dao.findMaxYangpinzh(weituoyps.get(0).getWeituodzj(),"");
			if(StringUtils.isNotBlank(maxNo)){
				yangpinzhone = Integer.parseInt(maxNo);
			}
			for(YangPinDto weituoyp:weituoyps){
				WeituoYp weituoYp = new WeituoYp();
				BeanUtils.copyProperties(weituoyp,weituoYp);
				//初始编码是否存在
				find.setChushibm(weituoyp.getChushibm());
				List<WeituoYp> listypz = super.findList(find);
//			if(listypz.size()>0 && !weituoYp.getWeituodzj().equals(listypz.get(0).getWeituodzj())){
//				//存在相同的初始编码
//				throw new RuntimeException("初始编码："+weituoyp.getChushibm()+"已存在，请重新填写！");
//			}else{
				String yewudh = weituoyp.getYewudh();
				if(StringUtils.isNotBlank(weituoyp.getYewudh())){
					//样品组编号规则：申请单业务单号+组号（未入库） ，入库时样品编号会在该编号的基础上加上两位流水号（01,02,03.......）
					yangpinzhone+=1;
					//样品组号&&样品编号
					if(yangpinzh<9){
						weituoYp.setYangpinzh("0"+yangpinzhone);
						weituoYp.setYangpinbh("S"+yewudh+"0"+yangpinzhone);
						weituoYp.setChushibm(weituoYp.getBianmaqz().concat("-0").concat(String.valueOf(yangpinzhone)));
					}else{
						weituoYp.setYangpinzh(""+yangpinzhone);
						weituoYp.setYangpinbh("S"+yewudh+yangpinzhone);
						weituoYp.setChushibm(weituoYp.getBianmaqz().concat("-").concat(String.valueOf(yangpinzhone)));
					}
					//编码前缀默认为样品组入库时的初始编码，之后不会被修改
					//weituoYp.setBianmaqz(weituoyp.getChushibm());09/27 去掉之前的规则（操作人：zdc）
				}
				weituoYp.setYangpinsysl("0");//样品剩余数量
				weituoYp.setId(null);
				weituoYp.setYangpinzid(IdGen.uuid());//样品组id
				this.save(weituoYp);
//			}
			}
		}

	}

	/**
	 * 判断申请样品组的初始编码是否重复
	 * 本次保存的样品组与上次的样品组和非本次申请单的样品组作比较 如果存在则有重复 反之不重复
	 * @param weituoyps 样品组
	 * @param chushibm  初始编码
	 * @return
	 */
	public Boolean isCunzaiCsbm(List<YangPinDto> weituoyps,String chushibm){
		//todo 判断申请样品组的初始编码是否重复
		Boolean isCunzai =false;
		WeituoYp find = new WeituoYp();
		find.setChushibm(chushibm);
		List<WeituoYp> listypz = super.findList(find);
		if(listypz.size()>0){

		}
		return isCunzai;
	}

	/**
	 * @Author：詹小梅
	 * @param： * @param null
	 * @Description：删除申请单关联样品组
	 * @Date：17:10 2017/6/22
	 * @Return：
	 * @Exception：
	 */
	public void deleteWeituodypz(List<YangPinDto> weituoyps){
	    if(weituoyps.size()>0){
            if(weituoyps.get(0)!=null){
				String fangfazjs = Collections3.extractToString(weituoyps,"fangfazj",",");
				if(StringUtils.isEmpty(fangfazjs.replaceAll(",",""))){
					fangfazjs = "";
				}
				this.dao.deleteWeituodypz(fangfazjs.split(","),weituoyps.get(0).getWeituodzj());
                //20170628zxm this.YangpinCkfkdDao.deleteYangpinrkjl(weituoyps.get(0).getWeituodzj());
            }
        }

	}

	/**
	 *  @Author：郭金龙
	 * 拷贝方法使用设备
	 * @param id 申请单ID
	 * @param newId 新申请单ID
	 * @param userId 用户ID
	 * @param time 创建时间
	 */
	public void copyypz(String id,String  newId,String  userId,Date time){
		this.dao.copyypz(id,newId,userId,time);
	}

	/**
	 * 根据申请单主键将申请样品状态变更为待检
	 * @param weituodzj 申请单主键
	 */
	public void updateStatusToDaiJian(String weituodzj){
		this.dao.updateStatusToDaiJian(weituodzj, YpConstans.optStatus.DAIJIAN,UserUtils.getUser().getId(),new Date());
	}

	/**
	 * 获取样品组总数
	 * @param weituodzj 申请单主键
	 * @return
	 */
	public String getYangpinzs(String weituodzj){
		return this.dao.getYangpinzs(weituodzj);
	}

	/**
	 * 更新样品组剩余数量
	 * @param allYangPinzsl
	 */
	public void updateYangPinsl(Map<String,String> allYangPinzsl){
		this.dao.updateYangPinSysl(allYangPinzsl);
	}
}