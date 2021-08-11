package com.demxs.tdm.service.resource.haocai;

import com.demxs.tdm.common.utils.*;
import com.demxs.tdm.dao.resource.haocai.HaocaiRkxxDao;
import com.demxs.tdm.domain.resource.haocai.HaocaiKc;
import com.demxs.tdm.domain.resource.haocai.HaocaiLzjl;
import com.demxs.tdm.domain.resource.haocai.HaocaiRkxx;
import com.demxs.tdm.service.resource.changshangygys.ChangshanggysxxService;
import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.*;
import com.demxs.tdm.domain.resource.changshangygys.Changshanggysxx;
import com.demxs.tdm.domain.resource.consumeables.HaocaiCfwz;
import com.demxs.tdm.domain.resource.consumeables.HaocaiLx;
import com.demxs.tdm.domain.resource.fujian.Attachment;
import com.demxs.tdm.domain.resource.haocai.*;
import com.demxs.tdm.comac.common.constant.HaocaiConstans;
import com.demxs.tdm.service.resource.fujian.AttachmentService;
import com.demxs.tdm.service.sys.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

/**
 * 耗材入库信息Service
 * @author zhangdengcai
 * @version 2017-07-16
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class HaocaiRkxxService extends CrudService<HaocaiRkxxDao, HaocaiRkxx> {
	@Autowired
	private HaocaiCfwzService haocaiCfwzService;
	@Autowired
	private HaocaiLzjlService haocaiLzjlService;
	@Autowired
	private HaocaiLxService haocaiLxService;
	@Autowired
	private HaocaiKcService haocaiKcService;
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private ChangshanggysxxService changshangService;
	@Autowired
	private SystemService systemService;


	private HaocaiRkxx getAll(HaocaiRkxx haocaiRkxx){

		//获取厂商信息
		if (haocaiRkxx.getHaocaicj()!=null && StringUtils.isNotBlank( haocaiRkxx.getHaocaicj().getId())) {
			haocaiRkxx.setHaocaicj(changshangService.get(haocaiRkxx.getHaocaicj().getId()));
		}else{
			haocaiRkxx.setHaocaicj(new Changshanggysxx());
		}

		//获取提供商信息
		if (haocaiRkxx.getGongyings()!=null && StringUtils.isNotBlank( haocaiRkxx.getGongyings().getId())) {
			haocaiRkxx.setGongyings(changshangService.get(haocaiRkxx.getGongyings().getId()));
		}else{
			haocaiRkxx.setGongyings(new Changshanggysxx());
		}
		//获取耗材类型
		if (haocaiRkxx.getHaocailx()!=null && StringUtils.isNotBlank(haocaiRkxx.getHaocailx().getId())) {
			haocaiRkxx.setHaocailx(haocaiLxService.get(haocaiRkxx.getHaocailx().getId()));
		} else {
			haocaiRkxx.setHaocailx(new HaocaiLx());
		}
		//获取耗材位置
		if (haocaiRkxx.getCunfangwz()!=null && StringUtils.isNotBlank(haocaiRkxx.getCunfangwz().getId())) {
			haocaiRkxx.setCunfangwz(haocaiCfwzService.get(haocaiRkxx.getCunfangwz().getId()));
		} else {
			haocaiRkxx.setCunfangwz(new HaocaiCfwz());
		}
		//获取验收人
		if (haocaiRkxx.getYanshour()!=null && StringUtils.isNotBlank(haocaiRkxx.getYanshour().getId())) {
			haocaiRkxx.setYanshour(systemService.getUser(haocaiRkxx.getYanshour().getId()));
		} else {
			haocaiRkxx.setYanshour(new User());
		}
		return haocaiRkxx;
	}

	private List<HaocaiRkxx> getAll(List<HaocaiRkxx> haocaiRkxxes){
		if(!Collections3.isEmpty(haocaiRkxxes)){
			for(HaocaiRkxx h:haocaiRkxxes){
				getAll(h);
			}
		}
		return haocaiRkxxes;
	}

	@Override
	public HaocaiRkxx get(String id) {
		return getAll(super.get(id));
	}

	@Override
	public List<HaocaiRkxx> findList(HaocaiRkxx haocaiRkxx) {
		return getAll(super.findList(haocaiRkxx));
	}

	@Override
	public Page<HaocaiRkxx> findPage(Page<HaocaiRkxx> page, HaocaiRkxx haocaiRkxx) {
		Page<HaocaiRkxx> dataValue = super.findPage(page,haocaiRkxx);
		if(dataValue!=null & !Collections3.isEmpty(dataValue.getList())){
			getAll(dataValue.getList());
		}
		return dataValue;
	}

	/**
	 * 保存
	 * @param haocaiRkxx
	 */
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(HaocaiRkxx haocaiRkxx,String type) {
		String id = "";
		if (StringUtils.isNotBlank(haocaiRkxx.getId())) {//修改
			if((HaocaiConstans.haoCaisjzt.DAITIJ).equals(haocaiRkxx.getShujuzt())){//将状态为“待提交”的改为已提交
				haocaiRkxx.setShujuzt(HaocaiConstans.haoCaisjzt.YITIJ);
			}
			HaocaiRkxx oldHc = this.get(haocaiRkxx.getId());
			String oldRksl = oldHc.getRukusl();//改前入库数量
			//修改后增量
			String zengliang = String.valueOf( Integer.valueOf(haocaiRkxx.getRukusl()==null||"null".equals(haocaiRkxx.getRukusl())?"0":haocaiRkxx.getRukusl()) - Integer.valueOf(oldRksl==null || "null".equals(oldRksl)?"0":oldRksl));
			super.save(haocaiRkxx);
			id= haocaiRkxx.getId();
			if(type.equals(HaocaiConstans.haoCaiLzlx.RUKU)){
				saveLzjl(haocaiRkxx, "update");//保存流转记录
				saveToKc(haocaiRkxx,"update", zengliang);//保存到库存表中
			}
		} else {
			haocaiRkxx.preInsert();
			id = IdGen.uuid();
			haocaiRkxx.setId(id);
			haocaiRkxx.setHaocaibh(getMaxHaocaibh());//设置耗材编号
			haocaiRkxx.setRukurq(DateUtils.getDate());
			if(type.equals(HaocaiConstans.haoCaiLzlx.RUKU)){
				saveLzjl(haocaiRkxx, "save");//保存流转记录
				saveToKc(haocaiRkxx,"save", "");//保存到库存表中
			}
			this.dao.insert(haocaiRkxx);
		}
		saveQRCodeImg(haocaiRkxx);//生产二维码
	}


	/**
	 * 保存流转记录
	 * @param haocaiRkxx
	 * @param saveOrupdate 新增或修改
	 */
	public void saveLzjl(HaocaiRkxx haocaiRkxx, String saveOrupdate){
		HaocaiLzjl haocaiLzjl = new HaocaiLzjl();
		haocaiLzjl.setHaocailx(HaocaiConstans.haoCaiLzlx.RUKU);
		haocaiLzjl.setHaocaizj(haocaiRkxx.getId());
		if("update".equals(saveOrupdate)){
			List<HaocaiLzjl> lzjls = haocaiLzjlService.findList(haocaiLzjl);
			if(lzjls!=null && !lzjls.isEmpty()){
				haocaiLzjl = lzjls.get(0);
			}
		}

		haocaiLzjl.setShuliang(haocaiRkxx.getRukusl());
		haocaiLzjl.setCaozuor(UserUtils.getUser().getId());
		haocaiLzjl.setCaozuormc(UserUtils.getUser().getName());
		haocaiLzjl.setEipbianhao(haocaiRkxx.getEipbianhao());
		haocaiLzjl.setHaocaibh(haocaiRkxx.getHaocaibh());
		haocaiLzjl.setGuigexh(haocaiRkxx.getGuigexh());
		haocaiLzjl.setRiqi(DateUtils.getDate());
		haocaiLzjl.setHaocaileix(haocaiRkxx.getHaocailx().getId());
		haocaiLzjl.setHaocaimc(haocaiRkxx.getHaocailx().getLeixingmc());
		haocaiLzjl.setJiliangdw(haocaiRkxx.getJiliangdw());
		haocaiLzjlService.save(haocaiLzjl);
	}

	/**
	 * 保存到库存表中（根据耗材名称和型号，查到就累加，否则插入新记录）
	 * @param haocaiRkxx
	 * @param saveOrUpdate 保存或修改
	 * @param ghzengliang 改后增量
	 */
	public void saveToKc(HaocaiRkxx haocaiRkxx, String saveOrUpdate, String ghzengliang){
		HaocaiKc kc = haocaiKcService.findListByMcXh(haocaiRkxx.getHaocaimc(), haocaiRkxx.getGuigexh());
		if(kc!=null){
			int kcsl = 0;
			if("update".equals(saveOrUpdate)){
				kcsl = Integer.valueOf((kc.getKucunsl()==null||"null".equals(kc.getKucunsl()))?"0":kc.getKucunsl()) + Integer.valueOf((ghzengliang==null||"null".equals(ghzengliang))?"0":ghzengliang);
			}else{
				kcsl = Integer.valueOf((kc.getKucunsl()==null||"null".equals(kc.getKucunsl()))?"0":kc.getKucunsl()) + Integer.valueOf(haocaiRkxx.getRukusl());
			}
			kc.setKucunsl(String.valueOf(kcsl));
			kc.setAnquankcsl(haocaiRkxx.getAnquankcsl());
			haocaiKcService.save(kc);
		}else{
			HaocaiKc kuc = new HaocaiKc();
			kuc.setHaocaimc(haocaiRkxx.getHaocaimc());
			kuc.setGuigexh(haocaiRkxx.getGuigexh());
			kuc.setKucunsl(String.valueOf(getRukuslByMcXh(haocaiRkxx.getHaocaimc(), haocaiRkxx.getGuigexh())));
			kuc.setAnquankcsl(haocaiRkxx.getAnquankcsl());
			kuc.setHaocailx(haocaiRkxx.getHaocailx()==null?"":haocaiLxService.get(haocaiRkxx.getHaocailx()).getLeixingmc());
			kuc.setJiliangdw(haocaiRkxx.getJiliangdw());
			kuc.setYongtu(haocaiRkxx.getYongtu());
			kuc.setHaocaicjmc(haocaiRkxx.getHaocaicj().getGongsimc());
			kuc.setGuobie(haocaiRkxx.getGuobie());
			kuc.setGongyingsmc(haocaiRkxx.getGongyings().getGongsimc());
			haocaiKcService.save(kuc);
		}
	}

	/**
	 * 根据耗材编号 获取耗材详情
	 * @param haocaiRkxx
	 * @return
	 */
	public HaocaiRkxx getByHcbh(HaocaiRkxx haocaiRkxx){
		return this.dao.getByHcbh(haocaiRkxx);
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(HaocaiRkxx haocaiRkxx) {
		super.delete(haocaiRkxx);
	}

	/**
	 * 耗材编号
	 * @return
	 */
	public String getMaxHaocaibh(){
		String haocaibh = "HC".concat(DateUtils.getDate().replace("-",""));
		HaocaiRkxx haocaiRkxx = new HaocaiRkxx();
		haocaiRkxx.setHaocaibh(haocaibh);
		String maxNum = this.dao.getMaxHaocaibh(haocaiRkxx);
		if (StringUtils.isNotBlank(maxNum)) {
			int no = Integer.parseInt(maxNum.substring(maxNum.length()-3)) + 1;
			DecimalFormat df = new DecimalFormat("000");
			haocaibh += df.format(no);
		} else {
			haocaibh += "001";
		}
		return haocaibh;
	}

	/**
	 * 批量修改耗材数量
	 * @param haocaiRkxxs
	 */
	public void piliangxgsl(List<HaocaiRkxx> haocaiRkxxs){
		List<HaocaiRkxx> pandianhcs = new ArrayList<HaocaiRkxx>();
		List<HaocaiKc> kcs = new ArrayList<HaocaiKc>();
		if(haocaiRkxxs!=null && !haocaiRkxxs.isEmpty()){
			for (HaocaiRkxx hc : haocaiRkxxs) {
				if(StringUtils.isNotBlank(hc.getRukusl()) && StringUtils.isNotBlank(hc.getId())){
					pandianhcs.add(hc);//放入要改变数量的集合中
				}
			}

			if(pandianhcs!=null && !pandianhcs.isEmpty()){
				for (HaocaiRkxx haocai : pandianhcs) {
					HaocaiRkxx yuanhaoc = this.get(haocai.getId());
					int zengliang = Integer.valueOf(haocai.getRukusl()) - Integer.valueOf((yuanhaoc.getRukusl()==null||"null".equals(yuanhaoc.getRukusl()))?"0":yuanhaoc.getRukusl());
					HaocaiKc hckc = haocaiKcService.findListByMcXh(haocai.getHaocaimc(), haocai.getGuigexh());
					if(hckc!=null){
						int zuizhongkcsl = Integer.valueOf((hckc.getKucunsl()==null||"null".equals(hckc.getKucunsl()))?"0":hckc.getKucunsl()) + zengliang;
						hckc.setKucunsl(String.valueOf(zuizhongkcsl));
						haocaiKcService.save(hckc);//更改库存表中的库存
					}else{//正常操作情况下没，盘点时库存表不可能没有库存信息
						HaocaiKc kc = new HaocaiKc();
						kc.setHaocaimc(haocai.getHaocaimc());
						kc.setGuigexh(haocai.getGuigexh());
						kc.setKucunsl(String.valueOf(getRukuslByMcXh(haocai.getHaocaimc(), haocai.getGuigexh())));
						kc.setAnquankcsl(haocai.getAnquankcsl());
						kcs.add(kc);
					}
				}
				this.dao.piliangxgsl(pandianhcs);//更新数量改变的耗材
				if(kcs!=null && !kcs.isEmpty()){
					for (HaocaiKc kc : kcs) {
						haocaiKcService.save(kc);//这一步必须在更新入库信息表后执行
					}
				}
			}
		}
	}

	/**
	 * 根据耗材名称和型号，获取入库信息表中的入库数量
	 * @param haocaimc 耗材名称
	 * @param haocaixh 耗材型号
	 * @return
	 */
	public Integer getRukuslByMcXh(String haocaimc, String haocaixh){
		HaocaiRkxx haocaiRkxx = new HaocaiRkxx();
		haocaiRkxx.setHaocaimc(haocaimc);
		haocaiRkxx.setGuigexh(haocaixh);
		return this.dao.getRukuslByMcXh(haocaiRkxx);
	}

	/**
	 * 1.生成二维码图片
	 * 2.保存图片信息
	 * @param haocai
	 */
	public void saveQRCodeImg(HaocaiRkxx haocai){
		//文件路径
		String filePath = Global.getUserfilesBaseDir()+ Calendar.getInstance().get(YEAR)+"/"+ Calendar.getInstance().get(MONTH)+"/image/png/";
		FileUtils.createDirectory(filePath);
		filePath=filePath+haocai.getHaocaibh()+".png";
		//一厘米  28*28PX
		int width = 28;
		int height = 28;
		//生成二维码图片
		ZxingHandler.createQRCode(haocai.getHaocaibh(),width,height,filePath);
		//保存文件信息
		Attachment att = new Attachment();
		att.setCodeId(haocai.getId());
		att.setFileName(haocai.getHaocaibh());
		att.setColumnName("haocai_erweimtp");
		att.setFilePath(filePath);
		att.setFileDesc("耗材编号二维码图片");
		att.setCreateTime(haocai.getCreateDate());
		att.setCreateUserid(UserUtils.getUser().getId());
		att.setCreateUsername(UserUtils.getUser().getName());
		attachmentService.save(att);
	}
}