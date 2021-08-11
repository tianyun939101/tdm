package com.demxs.tdm.domain.external.oa.orgmsg;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "orgmsg")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrgMsg {

    @XmlElement(name = "org")
    private List<OAOrg> list;

    public List<OAOrg> getList() {
        return list;
    }

    public void setList(List<OAOrg> list) {
        this.list = list;
    }
}
