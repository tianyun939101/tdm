package com.demxs.tdm.service.business.nostandard;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.dao.business.nostandard.NoStandardProblemDao;
import com.demxs.tdm.domain.business.nostandard.NoStandardExecution;
import com.demxs.tdm.domain.business.nostandard.NoStandardProblem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: Jason
 * @Date: 2020/3/6 09:19
 * @Description:
 */

@Service
@Transactional(readOnly = true)
public class NoStandardProblemService extends CrudService<NoStandardProblemDao, NoStandardProblem> {


    @Autowired
    private  NoStandardProblemDao noStandardProblemDao;
    public int save(NoStandardExecution execution, List<NoStandardProblem> problems) {
        int count = 0;
        if(null != problems && !problems.isEmpty()){
            for (NoStandardProblem problem : problems) {
                problem.setExecutionId(execution.getId());
                super.save(problem);
                count++;
            }
        }
        return count;
    }

   public List<NoStandardProblem> getListByExecutionId(String executionId){
        return  noStandardProblemDao.getListByExecutionId(executionId);
    }
}
