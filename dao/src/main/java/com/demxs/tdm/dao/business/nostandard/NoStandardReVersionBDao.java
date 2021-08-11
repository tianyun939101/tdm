package com.demxs.tdm.dao.business.nostandard;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.nostandard.*;

@MyBatisDao
public interface NoStandardReVersionBDao extends CrudDao<NoStandardReVerSionB> {

   int insertBasic(NoStandardReVerSionB sionB);
    int updateBasic(NoStandardReVerSionB sionB);

    int insertExamination(ExaminationApply ex);
    int updateExamination(ExaminationApply ex);

    int insertCheckOutLine(CheckOutline ol);
    int updateCheckOutLine(CheckOutline ol);

    int insertRecord(TestingRecord rec);
    int updateRecord(TestingRecord rec);

  int insertMe(MeasureCheck me);
  int updateMe(MeasureCheck me);

    int deleteBasic(NoStandardReVerSionB sionB);
    int deleteExamination(ExaminationApply ex);
    int deleteCheckOutLine(CheckOutline ol);
    int deleteRecord(TestingRecord rec);
     int deleteMe(MeasureCheck me);

 NoStandardReVerSionB getById(NoStandardReVerSionB sionB);
}
