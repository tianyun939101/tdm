package com.demxs.tdm.domain.external.oa.orgmsg;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class UserMsg {

    @XmlElement(name = "user")
    private List<OAUser> list;

    public List<OAUser> getList() {
        return list;
    }

    public void setList(List<OAUser> list) {
        this.list = list;
    }
}
