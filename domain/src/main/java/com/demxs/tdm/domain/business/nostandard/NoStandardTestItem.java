package com.demxs.tdm.domain.business.nostandard;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.ability.TestItem;
import com.demxs.tdm.domain.business.AuditInfo;

import java.util.List;

public class NoStandardTestItem extends DataEntity<NoStandardTestItem> {

    private static final long serialVersionUID = 1L;

    private String entrustId;

    private String itemId;

    private TestItem testItem;

    private String sort;

    public NoStandardTestItem(){
        super();
    }

    public NoStandardTestItem(String id){
        super(id);
    }

    public String getEntrustId() {
        return entrustId;
    }

    public void setEntrustId(String entrustId) {
        this.entrustId = entrustId;
    }

    public TestItem getTestItem() {
        return testItem;
    }

    public void setTestItem(TestItem testItem) {
        this.testItem = testItem;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getItemId() {
        return itemId;
    }

    public NoStandardTestItem setItemId(String itemId) {
        this.itemId = itemId;
        return this;
    }
}
