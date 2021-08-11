package com.demxs.tdm.service.ability;

import com.demxs.tdm.domain.ability.AbilityLabPredict;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wuhui
 * @date 2020/12/19 13:39
 **/
public class SavePredictThread implements Runnable {

    List<AbilityLabPredict> predicts;

    AbilityLabPredictService predictService;

    public SavePredictThread(AbilityLabPredictService predictService,List<AbilityLabPredict> predicts) {
        this.predicts = predicts;
        this.predictService = predictService;
    }

    @Override
    public void run() {
        predictService.batchSave(predicts);
    }
}
