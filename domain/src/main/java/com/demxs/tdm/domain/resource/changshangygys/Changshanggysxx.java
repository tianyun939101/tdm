package com.demxs.tdm.domain.resource.changshangygys;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.lab.LabInfo;
import org.hibernate.validator.constraints.Length;

/**
 * 厂商、供应商信息Entity
 * @author zhangdengcai
 * @version 2017-06-10
 */
public class Changshanggysxx extends DataEntity<Changshanggysxx> {
	
	private static final long serialVersionUID = 1L;
	private String changshanggysbh;		// 厂商/供应商编号
	private String gongsimc;		// 公司名称
	private String guobie;		// 国别
	private String lianxir;		// 联系人
	private String lianxifs;		// 联系方式
	private String gongsidz;		// 公司地址
	private String wangzhi;		// 网址
	private String hezuozt;		// 合作状态：用户在下拉列表框中选择，可选项有合作中、已终止
	private String changshanggyszz;		// 厂商/供应商资质:附件
	private String pingjia;		// 评价
	private String leixing;		// 类型:厂商，供应商


	private Integer type;//0提交 1保存

	private User dangqianR;//当前人
	private String caozuoqx;//操作权限 1 可编辑 ，0 可查看 ,空 无任何权限


	private LabInfo labInfo;


	private String subCenter;

	private String labInfoId;

	private String labInfoName;


	@Override
	public String getLabInfoId() {
		return labInfoId;
	}

	@Override
	public void setLabInfoId(String labInfoId) {
		this.labInfoId = labInfoId;
	}

	@Override
	public String getLabInfoName() {
		return labInfoName;
	}

	@Override
	public void setLabInfoName(String labInfoName) {
		this.labInfoName = labInfoName;
	}

	public String getSubCenter() {
		return subCenter;
	}

	public void setSubCenter(String subCenter) {
		this.subCenter = subCenter;
	}

	public Changshanggysxx() {
		super();
	}

	public Changshanggysxx(String id){
		super(id);
	}

	@Length(min=0, max=200, message="厂商/供应商编号长度必须介于 0 和 200 之间")
	public String getChangshanggysbh() {
		return changshanggysbh;
	}

	public void setChangshanggysbh(String changshanggysbh) {
		this.changshanggysbh = changshanggysbh;
	}
	
	@Length(min=0, max=200, message="公司名称长度必须介于 0 和 200 之间")
	public String getGongsimc() {
		return gongsimc;
	}

	public void setGongsimc(String gongsimc) {
		this.gongsimc = gongsimc;
	}
	
	@Length(min=0, max=200, message="国别长度必须介于 0 和 200 之间")
	public String getGuobie() {
		return guobie;
	}

	public void setGuobie(String guobie) {
		this.guobie = guobie;
	}
	
	@Length(min=0, max=200, message="联系人长度必须介于 0 和 200 之间")
	public String getLianxir() {
		return lianxir;
	}

	public void setLianxir(String lianxir) {
		this.lianxir = lianxir;
	}
	
	@Length(min=0, max=200, message="联系方式长度必须介于 0 和 200 之间")
	public String getLianxifs() {
		return lianxifs;
	}

	public void setLianxifs(String lianxifs) {
		this.lianxifs = lianxifs;
	}
	
	@Length(min=0, max=200, message="公司地址长度必须介于 0 和 200 之间")
	public String getGongsidz() {
		return gongsidz;
	}

	public void setGongsidz(String gongsidz) {
		this.gongsidz = gongsidz;
	}
	
	@Length(min=0, max=200, message="网址长度必须介于 0 和 200 之间")
	public String getWangzhi() {
		return wangzhi;
	}

	public void setWangzhi(String wangzhi) {
		this.wangzhi = wangzhi;
	}
	
	@Length(min=0, max=200, message="合作状态：用户在下拉列表框中选择，可选项有合作中、已终止长度必须介于 0 和 200 之间")
	public String getHezuozt() {
		return hezuozt;
	}

	public void setHezuozt(String hezuozt) {
		this.hezuozt = hezuozt;
	}
	
	public String getChangshanggyszz() {
		return changshanggyszz;
	}

	public void setChangshanggyszz(String changshanggyszz) {
		this.changshanggyszz = changshanggyszz;
	}
	
	@Length(min=0, max=200, message="评价长度必须介于 0 和 200 之间")
	public String getPingjia() {
		return pingjia;
	}

	public void setPingjia(String pingjia) {
		this.pingjia = pingjia;
	}
	
	@Length(min=0, max=200, message="类型:厂商，供应商长度必须介于 0 和 200 之间")
	public String getLeixing() {
		return leixing;
	}

	public void setLeixing(String leixing) {
		this.leixing = leixing;
	}

	public User getDangqianR() {
		return dangqianR;
	}

	public void setDangqianR(User dangqianR) {
		this.dangqianR = dangqianR;
	}

	public String getCaozuoqx() {
		return caozuoqx;
	}

	public void setCaozuoqx(String caozuoqx) {
		this.caozuoqx = caozuoqx;
	}

	public LabInfo getLabInfo() {
		return labInfo;
	}

	public void setLabInfo(LabInfo labInfo) {
		this.labInfo = labInfo;
	}
}