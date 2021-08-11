package com.demxs.tdm.domain.business;

import com.demxs.tdm.common.persistence.DataEntity;

/**
 * @author: Jason
 * @Date: 2020/6/29 17:22
 * @Description: 标准流程ata章节
 */
public class EntrustAtaChapter extends DataEntity<EntrustAtaChapter> {

    private static final long serialVersionUID = 1L;
    /**
     * 申请单id
     */
    private String entrustId;
    /**
     * ata章节id
     */
    private String ataId;
    /**
     * ata章节对象
     */
    private ATAChapter ataChapter;

    public String getEntrustId() {
        return entrustId;
    }

    public EntrustAtaChapter setEntrustId(String entrustId) {
        this.entrustId = entrustId;
        return this;
    }

    public String getAtaId() {
        return ataId;
    }

    public EntrustAtaChapter setAtaId(String ataId) {
        this.ataId = ataId;
        return this;
    }

    public ATAChapter getAtaChapter() {
        return ataChapter;
    }

    public EntrustAtaChapter setAtaChapter(ATAChapter ataChapter) {
        this.ataChapter = ataChapter;
        return this;
    }

    /**
    * @author Jason
    * @date 2020/6/29 18:20
    * @params []
    * 视图传输name
    * @return java.lang.String
    */
    public String getName(){
        return this.ataChapter == null ? null : this.ataChapter.getName();
    }
}
