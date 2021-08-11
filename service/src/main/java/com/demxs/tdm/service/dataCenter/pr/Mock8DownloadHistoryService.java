package com.demxs.tdm.service.dataCenter.pr;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.dao.UserDao;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.dao.dataCenter.pr.Mock8DownloadHistoryDao;
import com.demxs.tdm.domain.dataCenter.pr.Mock8DownloadHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author wuhui
 * @date 2020/12/7 11:17
 **/
@Service
@Transactional(readOnly = false,rollbackFor = ServiceException.class)
public class Mock8DownloadHistoryService extends CrudService<Mock8DownloadHistoryDao, Mock8DownloadHistory> {
    @Autowired
    private Mock8DownloadHistoryDao mock8DownloadHistoryDao;
    @Autowired
    private UserDao userDao;

    public List<Map<String, Object>> getFraphData(Mock8DownloadHistory history, String flag){
        List<Map<String,Object>> result = new ArrayList<>();
        List<Mock8DownloadHistory> graphData = mock8DownloadHistoryDao.findGraphData(history);
        if(graphData == null || graphData.size() == 0){
            return null;
        }
        for(Mock8DownloadHistory moc : graphData){
            Map<String,Object> map = new HashMap<>();
            String filePath = moc.getFilePath();
            int i = filePath.lastIndexOf("\\");
            int lastSecondIndex = filePath.lastIndexOf("\\", i - 1);
            String substring = filePath.substring(lastSecondIndex + 1, filePath.length() - 1);
            String planGross = moc.getPlanGross();
            map.put("filePath",substring);//下载地址
            map.put("count",planGross);//下载量
            result.add(map);
        }
        return result;
    }
    public List<Map<String, Object>> getFraphUser(Mock8DownloadHistory history, String flag){
        List<Map<String,Object>> result = new ArrayList<>();
        List<Mock8DownloadHistory> graphData = mock8DownloadHistoryDao.findGraphUser(history);
        if(graphData == null || graphData.size() == 0){
            return null;
        }
        for(Mock8DownloadHistory moc : graphData){
            Map<String,Object> map = new HashMap<>();
            String createById = moc.getCreateById();
            User user = userDao.get(createById);
            String planGross = moc.getPlanGross();
            map.put("name",user.getName());//下载地址
            map.put("count",planGross);//下载量
            result.add(map);
        }
        return result;
    }

}
