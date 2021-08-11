package com.demxs.tdm.service.resource.xiangmu;

import com.demxs.tdm.dao.resource.xiangmu.XiangmuDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.fujian.Attachment;
import com.demxs.tdm.domain.resource.xiangmu.Xiangmu;
import com.demxs.tdm.domain.resource.xiangmu.XiangmuCzjl;
import com.demxs.tdm.domain.resource.xiangmu.XiangmuMk;
import com.demxs.tdm.comac.common.constant.XiangmuConstans;
import com.demxs.tdm.service.resource.fujian.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目信息，树维护Service
 * @author 詹小梅
 * @version 2017-06-14
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class XiangmuService extends CrudService<XiangmuDao, Xiangmu> {
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private XiangmuMkService xiangmuMkService;
	@Autowired
	private XiangmuLbService xiangmuLbService;
	@Autowired
	private XiangmuCzjlService xiangmuCzjlService;
	@Override
	public Xiangmu get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<Xiangmu> findList(Xiangmu xiangmu) {
		return super.findList(xiangmu);
	}
	
	@Override
	public Page<Xiangmu> findPage(Page<Xiangmu> page, Xiangmu xiangmu) {
//		page.setOrderBy("a.update_date desc");
		/*if(UserUtils.getUser()!=null){
			//xiangmu.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
			xiangmu.setDsf(dataScopeFilter(UserUtils.getUser(), "o", "u"));
		}*/
		return super.findPage(page, xiangmu);
	}

	//保存项目操作记录
   public void saveCzjl(String caozuolx){

   }
	/**
	 * 项目保存  保存，暂停，结项，异常终止
	 * @param entity
	 */
	public void save(Xiangmu entity,String caozuolx) {

		if(StringUtils.isNotBlank(caozuolx) && caozuolx.equals(XiangmuConstans.Caozuolx.JIXU)){
			super.save(entity);
		}else{
			String xiangmuid = "";
			if(StringUtils.isNotBlank(entity.getId())){
				xiangmuid = entity.getId();
				//如果项目状态为正常完结将【结束时间】置为系统当前时间
				if(entity.getXiangmuzt().equals(XiangmuConstans.Xiangmuzt.ZHENGCHANGWANJIE)){
					entity.setJieshusj(DateUtils.getDateTime());
				}
				super.save(entity);
				//更新数据中心 项目类别父主键
				/*IDataSjzxService iDataSjzxService = SpringContextHolder.getBean(IDataSjzxService.class);
				XiangmuLb xiangmuLb = this.xiangmuLbService.get(entity.getXiangmulb());
				iDataSjzxService.updateXMLBbyXM(xiangmuLb.getFuzhujs(),xiangmuLb.getId(),xiangmuLb.getLeixingmc(),xiangmuid);*/
			}else{	//新增
				String xmbh="P"+DateUtils.getDate("yyyyMMdd");
				xiangmuid= IdGen.uuid();
				entity.preInsert();
				entity.setId(xiangmuid);
				entity.setDate(DateUtils.getDate());
				//List<Xiangmu> xiangmu= xiangmuService.findxlhList(entity);
				//项目编号规则P2017010101 （P当前年月日+01...）01当天流水号
				String ss = this.dao.findxlhList1(DateUtils.getDate());//获取当前项目编号序列号
				if(StringUtils.isNotBlank(ss)){
					int xlh = Integer.parseInt(ss);//当前值
					if(xlh<10){
						entity.setXiangmubh(xmbh+"0"+xlh);
						entity.setXiangmuxlh(String.valueOf(xlh+=1));
					}else{
						entity.setXiangmubh(xmbh+xlh);
						entity.setXiangmuxlh(String.valueOf(xlh+=1));
					}
				}else{
					entity.setXiangmubh(xmbh+"01");
					entity.setXiangmuxlh("2");
				}
				//如果项目状态为正常完结 将【结束时间】置为系统当前时间
				if(entity.getXiangmuzt().equals(XiangmuConstans.Xiangmuzt.ZHENGCHANGWANJIE)){
					entity.setJieshusj(DateUtils.getDateTime());
				}
				this.dao.insert(entity);


			}
			//多附件
			if(entity.getXiangmuwj()!=null){
				attachmentService.guanlianfujian(entity.getXiangmuwj(),xiangmuid,"xiangmuwjs");
			}

			XiangmuMk[] xiangmuMks = entity.getOnemklist();
			if(xiangmuMks!=null && xiangmuMks.length>0)
			{
				List ids = new ArrayList();
				for (int i = 0; i <xiangmuMks.length ; i++) {
					String isDel = xiangmuMks[i].getDelFlagMe();
					String id = xiangmuMks[i].getId();
					if(StringUtils.isNotBlank(isDel) && isDel.equals("1") && StringUtils.isNotBlank(id)){
						ids.add(id);
					}else{
						xiangmuMks[i].setXiangmuzj(entity.getId());
						this.xiangmuMkService.save(xiangmuMks[i]);
					}

				}
				if(ids.size()>0)
				{
					XiangmuMk xiangmuMk = new XiangmuMk();
					xiangmuMk.setArrIDS((String[])ids.toArray(new String[ids.size()]));
					this.xiangmuMkService.delete(xiangmuMk);
				}

			}
		}
		if(StringUtils.isNotBlank(caozuolx))
		{
			XiangmuCzjl xiangmuCzjl =  new XiangmuCzjl();
			String yuanyin = "";
			//插入操作记录
			if(caozuolx.equals(XiangmuConstans.Caozuolx.ZANTING)){
				yuanyin = entity.getZantingyy();
			}
			if(caozuolx.equals(XiangmuConstans.Caozuolx.YICHANGZHOGNZHI)){
				yuanyin = entity.getYichangzzyy();
			}
			xiangmuCzjl.setCaozuolx(caozuolx);
			xiangmuCzjl.setXiangmuid(entity.getId());
			xiangmuCzjl.setYuanyin(yuanyin);
			xiangmuCzjl.setCaozuosj(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
			xiangmuCzjlService.save(xiangmuCzjl);
		}
	}
	//查询项目的操作记录
	public Page<XiangmuCzjl> findCzjlList(XiangmuCzjl xiangmuCzjl,Page page){
		page= this.xiangmuCzjlService.findPage(page,xiangmuCzjl);
		return page;
	}
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(Xiangmu xiangmu) {
		super.delete(xiangmu);
	}

	public List<Xiangmu> findNianfenList(Xiangmu xiangmu){
		return dao.findNianfenList(xiangmu);
	}

	public List<Xiangmu> findXiangmuList (Xiangmu xiangmu){
		return dao.findXiangmuList(xiangmu);
	}
	/**
	 * 获取项目编号当前值
	 * @param
	 * @return
	 */
	public List<Xiangmu> findxlhList(Xiangmu xiangmu){
		return dao.findxlhList(xiangmu);
	}
	public String findxlhList1(String date){
		return this.dao.findxlhList1(date);
	}
	/**
	 * @Author：詹小梅
	 * @param： * @param null
	 * @Description：
	 * @Date：11:13 2017/6/22
	 * @Return：
	 * @Exception：
	 */
	public Attachment[] fujiansz(String attIds){
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
		return attList.size()==0? new Attachment[0] : attList.toArray(attArray);
	}
	/**
	 * 获取数据中心项目列表
	 * @param entity
	 * @return
	 */
	public  Page<Xiangmu> findXMList(int pageNo,int pageSize,Xiangmu entity){
        Page<Xiangmu> page = new Page<Xiangmu>(pageNo,pageSize);
        page.setOrderBy("a.update_Date desc ");
        List<Xiangmu> list = this.dao.findXMList(page);
        page.setList(list);
        return page;
	}

}