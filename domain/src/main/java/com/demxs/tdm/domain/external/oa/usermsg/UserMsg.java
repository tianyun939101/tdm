package com.demxs.tdm.domain.external.oa.usermsg;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "usermsg")
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
