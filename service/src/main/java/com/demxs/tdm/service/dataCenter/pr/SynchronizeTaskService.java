package com.demxs.tdm.service.dataCenter.pr;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.FtpClientUtil;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.dataCenter.pr.DirectoryDao;
import com.demxs.tdm.dao.dataCenter.pr.SynchronizeTaskDao;
import com.demxs.tdm.domain.dataCenter.pr.Directory;
import com.demxs.tdm.domain.dataCenter.pr.SynchronizeTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/7/8 16:14
 * @Description:
 */
@Service
@Transactional(readOnly = false,rollbackFor = ServiceException.class)
public class SynchronizeTaskService extends CrudService<SynchronizeTaskDao,SynchronizeTask> {

    @Autowired
    private DirectoryDao directoryDao;

    public SynchronizeTask getParentByPath(SynchronizeTask task){
        return this.dao.getParentByPath(task);
    }

    public int deleteByNameSpace(SynchronizeTask task){
        if(String.valueOf(FtpClientUtil.DEFAULT).equals(task.getNameSpace())){
            //需要从数据库中查找数据
            Directory directory = directoryDao.get(task.getDataId());
            if(null != directory){
                String filePath;
                filePath = directory.getFileUrl();
                String symbol = "://";
                int index = filePath.indexOf(symbol);
                if (-1 != index) {
                    index = index + symbol.length();
                    int index1 = filePath.indexOf("/", index);
                    if (-1 != index1) {
                        filePath = filePath.substring(index1 + 1);
                    }
                }
                task.setPath(filePath);
            }
        }else{
            //直接处理
            if(StringUtils.isNotBlank(task.getPath())){
                String path = task.getPath();
                if(!path.contains("/")){
                    //代表为根目录
                    path = task.getDataId();
                    task.setName(null);
                }
                //去除命名空间
                path = path.substring(path.indexOf("/"));
                //去掉第一个斜杠
                path = path.substring(path.indexOf("/") + 1);
                //去除最后一个斜杠
                path = path.substring(0,path.lastIndexOf("/"));
                task.setPath(path);
            }
        }
        return this.dao.deleteByNameSpace(task);
    }

    @Override
    public void save(SynchronizeTask task) {
        if(String.valueOf(FtpClientUtil.DEFAULT).equals(task.getNameSpace())){
            //需要从数据库中查找数据
            Directory directory = directoryDao.get(task.getDataId());
            if(null != directory){
                String filePath;
                filePath = directory.getFileUrl();
                String symbol = "://";
                int index = filePath.indexOf(symbol);
                if (-1 != index) {
                    index = index + symbol.length();
                    int index1 = filePath.indexOf("/", index);
                    if (-1 != index1) {
                        filePath = filePath.substring(index1 + 1);
                    }
                }
                task.setPath(filePath);
            }
        }else{
            //直接处理
            if(StringUtils.isNotBlank(task.getPath())){
                String path = task.getPath();
                if(!path.contains("/")){
                    //代表为根目录
                    path = task.getDataId();
                    task.setName(null);
                }
                //去除命名空间
                path = path.substring(path.indexOf("/"));
                //去掉第一个斜杠
                path = path.substring(path.indexOf("/") + 1);
                //去除最后一个斜杠
                path = path.substring(0,path.lastIndexOf("/"));
                task.setPath(path);
            }
        }
        this.dao.deleteByNameSpace(task);
        super.save(task);
    }

    @Transactional(readOnly = true)
    public void batchSave(List<SynchronizeTask> taskList) {
        if(null != taskList){
            for (SynchronizeTask task : taskList) {
                this.save(task);
            }
        }
    }

    @Transactional(readOnly = false)
    public int batchDelete(List<SynchronizeTask> taskList) {
        int count = 0;
        if(null != taskList){
            for (SynchronizeTask task : taskList) {
                count += this.deleteByNameSpace(task);
            }
        }
        return count;
    }
}
