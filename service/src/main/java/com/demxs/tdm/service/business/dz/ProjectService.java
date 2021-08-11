package com.demxs.tdm.service.business.dz;

import com.demxs.tdm.domain.business.dz.DzPorsvisile;
import com.demxs.tdm.domain.business.dz.DzProject;

public interface ProjectService {
    DzProject getAuditInfolist(String id);
    void delectsvisile(String id);
    void insertsvisile(DzPorsvisile dzPorsvisile);
}
