package com.demxs.tdm.domain.business.nostandard;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.business.ATAChapter;

/**
 * 非标ATA章节关联
 */
public class NoStandardATAChapter extends DataEntity<NoStandardATAChapter> {

    private static final long serialVersionUID = 1L;

    private String ataId;
    private String entrustId;
    private String name;

    private ATAChapter ataChapter;

    public String getAtaId() {
        return ataId;
    }

    public void setAtaId(String ataId) {
        this.ataId = ataId;
    }

    public String getEntrustId() {
        return entrustId;
    }

    public void setEntrustId(String entrustId) {
        this.entrustId = entrustId;
    }

    public ATAChapter getAtaChapter() {
        return ataChapter;
    }

    public void setAtaChapter(ATAChapter ataChapter) {
        this.ataChapter = ataChapter;
    }

    public String getName() {
        if(ataChapter!=null){
            return ataChapter.getName();
        }
        return name;
    }

    public NoStandardATAChapter setName(String name) {
        this.name = name;
        return this;
    }
}
