package com.demxs.tdm.service.resource.attach;

import com.demxs.tdm.common.file.FileStore;
import com.demxs.tdm.common.file.bean.FileType;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.FileUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.attach.AttachmentInfo;
import com.demxs.tdm.job.JobType;
import com.demxs.tdm.job.JobUtil;
import com.demxs.tdm.comac.common.constant.attach.AttachmentStatus;
import com.demxs.tdm.comac.common.constant.attach.DocType;
import com.demxs.tdm.comac.common.constant.attach.KnowledgeType;
import com.github.ltsopensource.core.commons.utils.CollectionUtils;
import com.github.ltsopensource.core.commons.utils.DateUtils;
import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.core.exception.JobSubmitException;
import com.github.ltsopensource.jobclient.domain.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * 资源管理服务实现
 * User: wuliepeng
 * Date: 2017-07-28
 * Time: 下午2:52
 */
@Service
@Transactional(readOnly = true)
public class AssetInfoService {
    private static final Logger log = LoggerFactory.getLogger(AssetInfoService.class);

    @Autowired
    private AttachmentInfoService attachmentInfoService;
    @Autowired
    private FileStore fileStore;


    @Transactional(readOnly = false)
    public AttachmentInfo save(String fileName, String filePath, Long length) {
        try {
            AttachmentInfo entity = new AttachmentInfo();
            entity.setName(fileName);
            entity.setPath(filePath);
            entity.setType(FileType.ATTACHED_FILE);
            entity.setLength(length);
            entity.setExtName(FileUtils.getFileExtension(fileName));
            entity.setDocType(getDocType(fileName));
            entity.setStatus(AttachmentStatus.Done.getValue());
            entity.setConvertPath(filePath);
            attachmentInfoService.save(entity);
            return entity;
        }catch (Exception e){
            log.error("保存附件出错:",e);
            return null;
        }
    }

    @Transactional(readOnly = false)
    public AttachmentInfo save(String fileName,String remarks, String filePath, Long length) {
        try {
            AttachmentInfo entity = new AttachmentInfo();
            entity.setName(fileName);
            entity.setPath(filePath);
            entity.setType(FileType.ATTACHED_FILE);
            entity.setLength(length);
            entity.setRemarks(remarks);
            entity.setExtName(FileUtils.getFileExtension(fileName));
            entity.setDocType(getDocType(fileName));
            entity.setStatus(AttachmentStatus.Done.getValue());
            entity.setConvertPath(filePath);
            attachmentInfoService.save(entity);
            return entity;
        }catch (Exception e){
            log.error("保存附件出错:",e);
            return null;
        }
    }

    @Transactional(readOnly = false)
    public AttachmentInfo saveAndConvert(String fileName, String filePath, Long length, Boolean indexable) {
        try{
            //保存附件
            AttachmentInfo entity = new AttachmentInfo();
            entity.setName(fileName);
            entity.setPath(filePath);
            entity.setType(FileType.ATTACHED_FILE);
            entity.setLength(length);
            entity.setExtName(FileUtils.getFileExtension(fileName));
            entity.setDocType(getDocType(fileName));
            entity.setStatus(AttachmentStatus.Convert.getValue());
            attachmentInfoService.save(entity);
            log.info("附件保存成功");
            //提交文档转换任务
            addDocConvertJob(entity.getId(), indexable);
            log.info("提交文档转换任务成功");
            return entity;
        }catch (Exception e){
            log.error("附件保存失败",e);
            return null;
        }
    }

    @Transactional(readOnly = false)
    public AttachmentInfo Convert(String attachmentId, Boolean indexable) {
        try {
            AttachmentInfo entity = attachmentInfoService.get(attachmentId);
            addDocConvertJob(entity.getId(), indexable);
            log.info("提交文档转换任务成功");
            return entity;
        } catch (Exception e){
            log.error("文件转换失败",e);
            return null;
        }
    }

    /**
     * 提交文档转换任务
     * @param attachmentId 文档ID
     * @throws JobSubmitException
     */
    private void addDocConvertJob(String attachmentId,Boolean indexable)  {
        try {
            Job job = new Job();
            job.setParam("type", JobType.DocConvert.getType());
            job.setTaskId(String.format("%s-%s", JobType.DocConvert.getType(), DateUtils.format(new Date(), DateUtils.YMD_HMS)));
            job.setParam("attachmentId", attachmentId);
            job.setParam("indexable", indexable.toString());
            job.setTaskTrackerNodeGroup(JobType.DocConvert.getTaskTracker());
            job.setNeedFeedback(true);
            Response response = JobUtil.getJobClient().submitJob(job);
            if (!response.isSuccess()) {
                String msg = String.format("提交文档转换任务失败: %s",response.toString());
                log.error(msg);
            }
        }catch (JobSubmitException e){
        }
    }

    public AttachmentInfo get(String attachmentId) {
        AttachmentInfo entity = attachmentInfoService.get(attachmentId);
        return entity;
    }

    @Transactional(readOnly = false)
    public void convert(String attachmentId,Boolean indexable)  {
        AttachmentInfo entity = attachmentInfoService.get(attachmentId);
        if(entity == null){
            log.info("附件不存在: {}",attachmentId);
            return;
        }

        for (KnowledgeType type : KnowledgeType.values()){
            //Document类型才需要进行转换,其它类型的文档不进行转换
            if(type.getValue().equals(entity.getDocType()) /*&&
                    type.equals(KnowledgeType.Document*/ /*&&
                    AttachmentType.Knowlage.getValue().equals(entity.getType())*/){
                if(type.equals(KnowledgeType.Document) || type.equals(KnowledgeType.Pdf)){
                    try {

                        //下载文档到本地
                        String filePath = fileStore.downloadFile(entity.getPath());
                        //获取文档类型
                        String originType = FileUtils.getFileExtension(filePath);
                        String fileType = "pdf";
                        //转换后的文档
                        File targetFile = FileUtils.createTempFile(fileType);
                        type.getConvertor().run(filePath, targetFile.getPath());
                        FileUtils.forceDelete(new File(filePath));
                        //转换完之后进行索引中的状态
                        entity.setStatus(indexable ? AttachmentStatus.Index.getValue() : AttachmentStatus.Done.getValue());
                        //转换后的文件
                        //转换路径
                        String convertPath = fileStore.saveFile(targetFile);
                        entity.setConvertPath(convertPath);
                        attachmentInfoService.save(entity);
                    }catch (Exception e){

                    }
                }else if(type.equals(KnowledgeType.Video)||type.equals(KnowledgeType.Image)|| type.equals(KnowledgeType.Text) || type.equals(KnowledgeType.Other)){
                    entity.setConvertPath(entity.getPath());
                    entity.setStatus(AttachmentStatus.Done.getValue());
                    attachmentInfoService.save(entity);
                }


            }
        }
    }


    /**
     * 根据文件名称获取文档类型
     * @param fileName 文件名称
     * @return
     */
    private Integer getDocType(String fileName){
        String extName = FileUtils.getFileExtension(fileName);
        for(KnowledgeType type : KnowledgeType.values()){
            DocType docType = type.getDocType();
            if (docType.contains(extName)){
                return type.getValue();
            }
        }
        return KnowledgeType.Other.getValue();
    }


    /**
     * 业务附件保存
     * @param businessId
     * @param attchIds
     */
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void saveAttachBusiness(String businessId,String attchIds){

        //先获取该业务id所关联的附件列表
        List<AttachmentInfo> attachmentInfos = getAttachListByBusiness(businessId);
        if(CollectionUtils.isNotEmpty(attachmentInfos)){
            for(AttachmentInfo attach :attachmentInfos){
                attachmentInfoService.updateBusinessId(attach.getId(),null);
            }
        }
        if(StringUtils.isNotBlank(attchIds)){
            String[] idArr = attchIds.split(",");
            for(String attachId:idArr){
                attachmentInfoService.updateBusinessId(attachId,businessId);
            }
        }
    }

    /**
     * 根据业务id获取附件列表
     * @param businessId
     * @return
     */
    public List<AttachmentInfo> getAttachListByBusiness(String businessId){
        return attachmentInfoService.findList(new AttachmentInfo(null,businessId));
    }
}
