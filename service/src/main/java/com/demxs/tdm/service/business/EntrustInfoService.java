package com.demxs.tdm.service.business;

import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.sys.dao.UserDao;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.business.EntrustInfoDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.business.EntrustAtaChapter;
import com.demxs.tdm.domain.business.EntrustInfo;
import com.demxs.tdm.domain.business.TestTask;
import com.demxs.tdm.domain.business.vo.MainTotalVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 申请信息Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class EntrustInfoService extends CrudService<EntrustInfoDao, EntrustInfo> {

    @Autowired
    private ATAChapterService ataChapterService;

	@Autowired
	private JavaMailSenderImpl javaMailSenderImpl;

	@Autowired
	private UserDao userDao;

	@Override
	public EntrustInfo get(String id) {
        EntrustInfo entrustInfo = super.get(id);
        List<EntrustAtaChapter> ataChapterList = entrustInfo.getAtaChapterList();
        if(null != ataChapterList && !ataChapterList.isEmpty()){
            StringBuilder sb = new StringBuilder();
            for (EntrustAtaChapter ataChapter : ataChapterList) {
                if(null != ataChapter.getAtaChapter() && StringUtils.isNotBlank(ataChapter.getAtaChapter().getParentId())){
                    //设置ata章节完全限定名
                    String parentId = ataChapter.getAtaChapter().getParentId();
                    List<String> list = ataChapterService.getParentName(new ArrayList<>(), parentId);
                    for (int i = list.size() -1; i >= 0; i--) {
                        sb.append(list.get(i)).append("：");
                    }
                    sb.append(ataChapter.getName()).append("。");
                }
            }
            entrustInfo.setAtaChapterFullName(sb.toString());
        }
        return super.get(id);
	}
	
	@Override
	public List<EntrustInfo> findList(EntrustInfo entrustInfo) {
		return super.findList(entrustInfo);
	}
	
	@Override
	public Page<EntrustInfo> findPage(Page<EntrustInfo> page, EntrustInfo entrustInfo) {
		return super.findPage(page, entrustInfo);
	}

	/**
	 * 查询可以入库的申请单
	 * @param page
	 * @param entrustInfo
	 * @return
	 */
	public Page<EntrustInfo> findPutPage(Page<EntrustInfo> page, EntrustInfo entrustInfo) {
		entrustInfo.getSqlMap().put("dsf", dataScopeFilter(entrustInfo.getCurrentUser(), "o8", "u8"));
		entrustInfo.setPage(page);
		page.setList(dao.findPutList(entrustInfo));
		return page;
	}

	/**
	 * 查询计划
	 * @param page
	 * @param entrustInfo
	 * @return
	 */
	public Page<EntrustInfo> findPlanPage(Page<EntrustInfo> page, EntrustInfo entrustInfo) {
		entrustInfo.setPage(page);
		page.setList(dao.findPlan(entrustInfo));
		return page;
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(EntrustInfo entrustInfo) {
		if (entrustInfo.getPutInStatus() == null) {
			entrustInfo.setPutInStatus(EntrustInfo.NOTPUTIN);
		}
		super.save(entrustInfo);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(EntrustInfo entrustInfo) {
		super.delete(entrustInfo);
	}

	public EntrustInfo getByCode(String businessKey) {
		return this.dao.getByCode(businessKey);
	}


	public Page<EntrustInfo> findByReport(Page<EntrustInfo> page, EntrustInfo entrustInfo) {
		entrustInfo.setPage(page);
		page.setList(dao.findByReport(entrustInfo));
		return page;
	}
	/**
	 * 首页统计
	 * @return
     */
	public MainTotalVO getByMainTotal(){
		return this.dao.getByMainTotal();
	}

	/**
	* @author Jason
	* @date 2020/6/30 15:24
	* @params [labId]
	* 根据试验室id查询所有正在执行的任务
	* @return java.util.List<com.demxs.tdm.domain.business.TestTask>
	*/
	public List<TestTask> findTestTaskByLabId(String labId){
	    return this.dao.findTestTaskByLabId(labId);
    }

    /**
    * @author Jason
    * @date 2020/6/30 15:30
    * @params [entrustId]
    * 根据申请单id查询该申请单所有任务
    * @return java.util.List<com.demxs.tdm.domain.business.TestTask>
    */
    public List<TestTask> findTestTaskByEntrustId(String entrustId){
	    return this.dao.findTestTaskByEntrustId(entrustId);
    }

	@Async(value = "asyncExecutor")
	public  boolean  sendAduitMail(String  name){

		try{
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			String fromMail= Global.getConfig("mail.from");
			User userList=userDao.findByName(name);
			mailMessage.setFrom(fromMail);
			//接收方
			if(userList !=null){
				mailMessage.setTo(userList.getEmail());
				//主题
				mailMessage.setSubject("试验申请单通知");
				//内容
				mailMessage.setText("您好，您提交的试验申请已经提交到试验室，请知悉！");
				javaMailSenderImpl.send(mailMessage);
			}
		}catch (Exception  e){
			e.printStackTrace();
		}
		return true;

	}

	@Async(value = "asyncExecutor")
	public  boolean  sendFinishMail(String  name){

		try{
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			String fromMail= Global.getConfig("mail.from");
			User userList=userDao.findByName(name);
			mailMessage.setFrom(fromMail);
			//接收方
			if(userList !=null){
				mailMessage.setTo(userList.getEmail());
				//主题
				mailMessage.setSubject("试验申请单通知");
				//内容
				mailMessage.setText("您好，您提交的试验申请已完成，请知悉！");
				javaMailSenderImpl.send(mailMessage);
			}
		}catch (Exception  e){

			e.printStackTrace();
		}
		return true;

	}

}