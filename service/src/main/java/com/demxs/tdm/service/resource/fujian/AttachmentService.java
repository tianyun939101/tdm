package com.demxs.tdm.service.resource.fujian;


import com.demxs.tdm.dao.resource.fujian.AttachmentDao;
import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.FileUtils;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.fujian.Attachment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;


/**
 * 附件Service
 * @author 郭金龙
 * @version 2017-06-15
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class AttachmentService extends CrudService<AttachmentDao, Attachment> {

	@Override
	public Attachment get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<Attachment> findList(Attachment attachment) {
		return super.findList(attachment);
	}
	
	@Override
	public Page<Attachment> findPage(Page<Attachment> page, Attachment attachment) {
		return super.findPage(page, attachment);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(Attachment attachment) {
		super.save(attachment);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(Attachment attachment) {
		super.delete(attachment);
	}

	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public String saveUploadFile(String columnName,String fileType,String fileDesc,String codeId,MultipartFile attach) throws IOException {
		Attachment attachment = new Attachment();
		//上传
		uploadFile(attach,fileType,attachment);
		//入库
		User user = UserUtils.getUser();
		attachment.setFileType(fileType);
		attachment.setCodeId(codeId);
		attachment.setFileDesc(fileDesc);
		attachment.setColumnName(columnName);
		attachment.setCreateTime(new Date());
		attachment.setCreateUserid(user.getId());
		attachment.setCreateUsername(user.getName());
		this.save(attachment);
		return attachment.getId();
	}
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public String saveUploadFileFORYSJLBG(String fileName,String columnName,String fileType,String fileDesc,String codeId,MultipartFile attach) throws IOException {
		Attachment attachment = new Attachment();
		//上传
		uploadFileForYSBG(fileName,attach,fileType,attachment);
		//入库
		User user = UserUtils.getUser();
		attachment.setFileType(fileType);
		attachment.setCodeId(codeId);
		attachment.setFileDesc(fileDesc);
		attachment.setColumnName(columnName);
		attachment.setCreateTime(new Date());
		attachment.setCreateUserid(user.getId());
		attachment.setCreateUsername(user.getName());
		this.save(attachment);
		return attachment.getId();
	}
	public void uploadFileForYSBG(String fileName,MultipartFile attach,String fileType,Attachment attachment) throws IOException {
		if(attach.isEmpty()) throw new IOException("附件为空！");
		//拼装附件存储路径
		String filePath = Global.getUserfilesBaseDir()+ Calendar.getInstance().get(YEAR)+"/"+Calendar.getInstance().get(MONTH)+"/";
		//如果文件类型不为空则磁盘路径拼接文件类型
		if(StringUtils.isNotEmpty(fileType)){
			filePath = filePath + fileType + "/";
		}
		//修正路径
		filePath = FileUtils.path(filePath);
		//创建文件夹
		FileUtils.createDirectory(filePath);
		//获取附件原文件名
		//String fileName = attach.getOriginalFilename();
		//写入磁盘变更文件名称防止文件重名
		filePath = filePath+ IdGen.uuid()+"."+FileUtils.getFileExtension(fileName);
		File file = new File(filePath);
		//写入磁盘
		attach.transferTo(file);
		//设置实体值
		attachment.setFileName(fileName);
		attachment.setFilePath(filePath);
		attachment.setFileLength(String.valueOf(attach.getSize()));
	}
	public void uploadFile(MultipartFile attach,String fileType,Attachment attachment) throws IOException {
		if(attach.isEmpty()) throw new IOException("附件为空！");
		//拼装附件存储路径
		String filePath = Global.getUserfilesBaseDir()+ Calendar.getInstance().get(YEAR)+"/"+Calendar.getInstance().get(MONTH)+"/";
		//如果文件类型不为空则磁盘路径拼接文件类型
		if(StringUtils.isNotEmpty(fileType)){
			filePath = filePath + fileType + "/";
		}
		//修正路径
		filePath = FileUtils.path(filePath);
		//创建文件夹
		FileUtils.createDirectory(filePath);
		//获取附件原文件名
		String fileName = attach.getOriginalFilename();
		//写入磁盘变更文件名称防止文件重名
		filePath = filePath+ IdGen.uuid()+"."+FileUtils.getFileExtension(fileName);
		File file = new File(filePath);
		//写入磁盘
		attach.transferTo(file);
		//设置实体值
		attachment.setFileName(fileName);
		attachment.setFilePath(filePath);
		attachment.setFileLength(String.valueOf(attach.getSize()));
	}

	/**
	 * 将已上传的文件信息保存到附件中
	 * @param fileName 文件名称
	 * @param filePath 文件路径
	 * @param fileType 文件类型
	 * @param fileDesc 文件描述
	 * @return
	 * @throws IOException
	 */
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public Attachment saveFileToAtt(String fileName, String filePath, String fileType, String fileDesc){
		Attachment attachment = new Attachment();
		//入库
		User user = UserUtils.getUser();
		attachment.setFileName(fileName);
		attachment.setFilePath(filePath);
		attachment.setFileType(fileType);
		attachment.setFileDesc(fileDesc);
		attachment.setCreateTime(new Date());
		attachment.setCreateUserid(user.getId());
		attachment.setCreateUsername(user.getName());
		this.save(attachment);
		return attachment;
	}

	/**
	 * @Author：谭冬梅
	 * @param： * @param nowfuj 需关联的附件列表
	 * @param codeId 业务id
	 * @param columnName 业务类型
	 * @Description：关联附件之前先判断，将之前关联过的文件id查出来,然后关联
	 * @Date：18:06 2017/6/16
	 * @Return：
	 * @Exception：
	 */
	public void guanlianfujian(Attachment[] nowfuj,String codeId,String columnName){
		if(nowfuj!=null && nowfuj.length>0) {
			List<Attachment> nowfujlist = new ArrayList<Attachment>();
			Collections.addAll(nowfujlist, nowfuj);
			Attachment find = new Attachment();
			Map map = new HashMap();
			List<Attachment> attachments = null;
			find.setCodeId(codeId);
			find.setColumnName(columnName);
			attachments = findList(find);
			String oldIds = "";
			List<String> sameids = new ArrayList<String>();
			if (attachments.size() > 0) {

				for (int i = 0; i < attachments.size(); i++) {
					for (int j = 0; j < nowfujlist.size(); j++) {
						if (attachments.get(i).getId().equals(nowfujlist.get(j).getId())) {
							sameids.add(nowfujlist.get(j).getId());
						}
					}
				}
				if (sameids.size() > 0)//如果有相同的附件  则获取已有附件的不同的附件进行删除
				{
					for (int i = 0; i < attachments.size(); i++) {
						for (int j = 0; j < sameids.size(); j++) {
							if (attachments.get(i).getId().equals(sameids.get(j))) {
								attachments.remove(attachments.get(i));
							}
						}
					}

				}
				for (int i = 0; i < attachments.size(); i++) {
					oldIds += attachments.get(i).getId() + ",";
				}

				if (StringUtils.isNotBlank(oldIds)) {
					oldIds = oldIds.substring(0, oldIds.length() - 1);
				}
			}
			for (int i = 0; i < nowfujlist.size(); i++) {
				map.put(nowfujlist.get(i).getId(), columnName);
			}
			associatedAttachments(codeId, oldIds, map);
		}
	}
	/**
	 * 业务关联附件
	 * @param codeId 业务ID
	 * @param oldIds 原附件ID集合 用“,”分隔
	 * @param map key:附件ID value:附件类型
	 */
	public void associatedAttachments(String codeId,String oldIds,Map<String,String> map){
		//如果原附件ID存在则删除附件
		if (StringUtils.isNotBlank(oldIds)) {
			this.dao.batchDelete(oldIds.split(","));
		}
		if(map.size()==0||map==null){

		}else {

			//关联业务
			this.dao.associatedAttachments(codeId, map);
		}
	}
	/**
	 * @Author：谭冬梅
	 * @param：
	 * @param attachment
	 * @Description： 根据codeId 和 columnName 获取 文件id集合 和 文件名称集合
	 * @Date：16:46 2017/6/28
	 * @Return：map = {names:"附件1，附件2",ids:"1,2"}
	 * @Exception：
	 */
	public Map findConcatList(Attachment attachment){
		return this.dao.findConcatList(attachment);
	}

	/**
	 * 批量删除附件
	 * @param ids 附件ID集合
	 */
	public void deleteFile(String[] ids){
		this.dao.batchDelete(ids);
	}
}