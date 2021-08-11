package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.FlightDataFileUtil;
import com.demxs.tdm.common.utils.FtpClientUtil;
import com.demxs.tdm.dao.dataCenter.FlightBindTestInfoDataDao;
import com.demxs.tdm.domain.dataCenter.FlightBindTestInfoData;
import com.demxs.tdm.domain.dataCenter.pr.Directory;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/5/15 11:30
 * @Description:
 */
@Service
@Transactional(readOnly = false,rollbackFor = ServiceException.class)
public class FlightBindTestInfoDataService extends CrudService<FlightBindTestInfoDataDao, FlightBindTestInfoData> {

    /**
    * @author Jason
    * @date 2020/7/17 13:24
    * @params [bindTestInfoData]
    * 根据数据中心树形菜单点击的试验项目id查询关联的ftp文件
    * @return java.util.List<com.demxs.tdm.domain.dataCenter.FlightBindTestInfoData>
    */
    public List<FlightBindTestInfoData> findByTestInfoId(FlightBindTestInfoData bindTestInfoData){
        //List<FlightBindTestInfoData> list = this.dao.findByTestInfoId(bindTestInfoData);
        List<FlightBindTestInfoData> list = this.dao.findFtpFileByTestInfoId(bindTestInfoData);
        if(null != list){
            for (FlightBindTestInfoData infoData : list) {
                String parentDir = infoData.getFlightDirId().substring(0,infoData.getFlightDirId().lastIndexOf("/"));
                //去除命名空间
                parentDir = parentDir.substring(parentDir.indexOf("/"));
                String name = infoData.getFlightDirId().substring(infoData.getFlightDirId().lastIndexOf("/") + 1);
                String nameSpace = FlightDataFileUtil.getNameSpace(infoData.getType());
                String localPath = FlightDataFileUtil.PREFIX + nameSpace + "/" + parentDir + "/" + name;
                //优先加载ftp文件，ftp不存在该文件，则尝试从本地加载
                if(FtpClientUtil.fileExist(Integer.parseInt(infoData.getType()),parentDir,name)){
                    FTPFile file = FtpClientUtil.getFile(Integer.parseInt(infoData.getType()), parentDir, name);
                    Directory dir = new Directory();
                    dir.setId(infoData.getFlightDirId());
                    dir.setName(name);
                    dir.setFileLength(String.valueOf(file.getSize()));
                    infoData.setDirectory(dir);
                }else if(FlightDataFileUtil.fileExist(localPath)){
                    File file = new File(localPath);
                    Directory dir = new Directory();
                    dir.setId(infoData.getFlightDirId());
                    dir.setName(name);
                    dir.setFileLength(String.valueOf(file.length()));
                    infoData.setDirectory(dir);
                }
            }
        }
        return list;
    }

    @Transactional(readOnly = true)
    public int deleteByTestInfoIdAndDirId(FlightBindTestInfoData bindTestInfoData) {
        return this.dao.deleteByTestInfoIdAndDirId(bindTestInfoData);
    }

    @Transactional(readOnly = true)
    public void save(List<FlightBindTestInfoData> list){
        if(null != list && !list.isEmpty()){
            for (FlightBindTestInfoData bindTestInfoData : list) {
                List<FlightBindTestInfoData> l = this.dao.findByTestInfoIdAndDirId(bindTestInfoData);
                if(null == l || l.isEmpty()){
                    super.save(bindTestInfoData);
                }
            }
        }
    }
}
