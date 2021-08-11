package com.demxs.tdm.dao.resource.fujian;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.fujian.Attachment;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 附件DAO接口
 * @author 郭金龙
 * @version 2017-06-15
 */
@MyBatisDao
public interface AttachmentDao extends CrudDao<Attachment> {

    /**
     * 业务关联附件
     * @param codeId 业务ID
     * @param map key:附件ID value:附件类型
     */
    public void associatedAttachments(@Param(value = "codeId") String codeId,
                                      @Param(value = "map") Map map);

    /**
     * 批量删除
     * @param attachIds 附件列表集合
     */
    public void batchDelete(@Param(value = "attachIds") String[] attachIds);

    /**
     * @Author：谭冬梅
     * @param：
     * @param attachment
     * @Description： 根据codeId 和 columnName 获取 文件id集合 和 文件名称集合
     * @Date：16:46 2017/6/28
     * @Return：map = {fileNames:"附件1，附件2",ids:"1,2"}
     * @Exception：
     */
    public Map findConcatList(Attachment attachment);


}