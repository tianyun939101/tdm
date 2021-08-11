package com.demxs.tdm.service.resource.knowledge;

import com.demxs.tdm.comac.common.act.utils.EmailUtil;
import com.github.ltsopensource.core.logger.Logger;
import com.github.ltsopensource.core.logger.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class KnowledgeEmailUpJob {

    private Logger log= LoggerFactory.getLogger(KnowledgeEmailUpJob.class);

    @Autowired
    KnowledgeViewService knowledgeViewService;

    @Autowired
    EmailUtil emailUtil;

    public void  work(){

        try{
            knowledgeViewService.sendMail();
            log.debug("======邮件发送成功=====");
        }catch (Exception  e){
            e.printStackTrace();
            log.error("======邮件发送失败=====");
        }


    }
}
