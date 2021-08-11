package com.demxs.tdm.service.dataCenter.pr;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.service.TreeService;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.*;
import com.demxs.tdm.dao.dataCenter.pr.DirectoryDao;
import com.demxs.tdm.domain.dataCenter.pr.Directory;
import com.demxs.tdm.domain.dataCenter.pr.DownLoadHistory;
import com.demxs.tdm.domain.dataCenter.pr.Token;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: Jason
 * @Date: 2020/4/28 10:04
 * @Description:
 */
@Service
@Transactional(readOnly = false,rollbackFor = ServiceException.class)
public class DirectoryService extends TreeService<DirectoryDao, Directory> {

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private DownLoadHistoryService historyService;

    private final static String downUrl = "";
    private final static String FILE_TYPE = "File";

    @Transactional(readOnly = true)
    public int updateActive(Directory directory){
        return this.dao.updateActive(directory);
    }

    @Override
    public List<Directory> findList(Directory entity) {
        if (StringUtils.isNotBlank(entity.getParentIds())){
            entity.setParentIds(","+entity.getParentIds()+",");
        }
        return super.findList(entity);
    }

    @Override
    public Directory get(String id) {
        Directory c = this.dao.get(id);
        if(c != null && c.getParent()!=null && StringUtils.isNotBlank(c.getParent().getId())){
            c.setParent(this.dao.get(c.getParentId()));
        }
        return c;
    }

    public List<Directory> findDirList(Directory directory){
        return this.dao.findDirList(directory);
    }

    public List<Directory> findFileList(Directory directory){
        return this.dao.findFileList(directory);
    }

    /**
    * @author Jason
    * @date 2020/7/17 13:24
    * @params [t]
    * 验证权限方法
    * @return com.demxs.tdm.domain.dataCenter.pr.Token
    */
    public Token validate(Token t){
        User u = UserUtils.getUser();
        String ids = u.getOffice().getId() + "," + u.getOffice().getParentIds();
        List<String> deptId = Arrays.asList(ids.split(","));
        if(StringUtils.isNotBlank(t.getType())){
            /*String type = t.getType();
            if(FILE_TYPE.equals(type) || type.equals(String.valueOf(FtpClientUtil.DEFAULT))){
                //验证权限
                if(permissionService.validate(deptId,t.getFileId())){
                    tokenService.save(t);
                    return OpResult.buildSuccess().setData(t);
                }else{
                    return OpResult.buildError("无权限下载该文件！！");
                }
            }else if(type.equals(String.valueOf(FtpClientUtil.YL_1)) ||
                    type.equals(String.valueOf(FtpClientUtil.YL_2)) ||
                    type.equals(String.valueOf(FtpClientUtil.YL_3)) ||
                    type.equals(String.valueOf(FtpClientUtil.DY)) ||
                    type.equals(String.valueOf(FtpClientUtil.SH))){*/
            //验证权限
            if(permissionService.validateFtp(deptId,t.getFileId())){
                tokenService.save(t);
                return t;
            }else{
                return null;
            }
            //}
        }
        return null;
    }

    /**
    * @author Jason
    * @date 2020/7/17 13:24
    * @params [idArray, type, fileTypeArray]
    * 验证权限方法
    * @return java.util.List<java.lang.String>
    */
    public List<String> batchValidate(String idArray,String type,String fileTypeArray){
        String[] idArr = idArray.split(",");
        String[] fileTypeArr = fileTypeArray.split(",");
        User u = UserUtils.getUser();
        String ids = u.getOffice().getId() + "," + u.getOffice().getParentIds();
        List<String> deptId = Arrays.asList(ids.split(","));

        /*if(FILE_TYPE.equals(type) || type.equals(String.valueOf(FtpClientUtil.DEFAULT))){
            //验证权限
            if(permissionService.validate(deptId,idArr[0])){
                List<String> tokenIds = new ArrayList<>(idArr.length);
                for (String id : idArr) {
                    Directory directory = directoryService.get(id);
                    if(null == directory){
                        return OpResult.buildError("错误");
                    }
                    String uuid = IdGen.uuid();
                    tokenIds.add(uuid);
                    Token token = new Token(uuid);
                    token.setIsNewRecord(true);
                    token.setFileId(directory.getId());
                    token.setFileName(directory.getName());
                    token.setFileSize(directory.getFileLength());
                    tokenService.save(token);
                }
                return OpResult.buildSuccess().setData(tokenIds);
            }else{
                return OpResult.buildError("无权限下载该文件！！");
            }
        }else if(type.equals(String.valueOf(FtpClientUtil.YL_1)) ||
                type.equals(String.valueOf(FtpClientUtil.YL_2)) ||
                type.equals(String.valueOf(FtpClientUtil.YL_3)) ||
                type.equals(String.valueOf(FtpClientUtil.DY)) ||
                type.equals(String.valueOf(FtpClientUtil.SH))){*/
        //验证权限
        if(permissionService.validateFtp(deptId,idArr[0])){
            List<String> result = new ArrayList<>(idArr.length);
            for (int i = 0; i < idArr.length; i++) {
                String path = idArr[i];
                String fileType = fileTypeArr[i];
                String parentDir = path.substring(0,path.lastIndexOf("/"));
                //去除命名空间
                parentDir = parentDir.substring(parentDir.indexOf("/"));
                String name = path.substring(path.lastIndexOf("/") + 1);
                if(FtpClientUtil.TYPE.equals(fileType)){
                    FTPFile file = FtpClientUtil.getFile(Integer.parseInt(type),parentDir,name);
                    if(null == file){
                        throw new ServiceException("文件不存在！！");
                    }
                    String uuid = IdGen.uuid();
                    Token token = new Token(uuid);
                    token.setIsNewRecord(true);
                    token.setFileId(path);
                    token.setFileName(file.getName());
                    token.setFileSize(String.valueOf(file.getSize()));
                    token.setFileType(fileType);
                    tokenService.save(token);
                    result.add(uuid);
                }else if(FlightDataFileUtil.TYPE.equals(fileType)){
                    String nameSpace = FlightDataFileUtil.getNameSpace(type);
                    File file = new File(FlightDataFileUtil.PREFIX + nameSpace + parentDir + "/" + name);
                    if(file.exists()){
                        String uuid = IdGen.uuid();
                        Token token = new Token(uuid);
                        token.setIsNewRecord(true);
                        token.setFileId(path);
                        token.setFileName(file.getName());
                        token.setFileSize(String.valueOf(file.length()));
                        token.setFileType(fileType);
                        tokenService.save(token);
                        result.add(uuid);
                    }else{
                        throw new ServiceException("文件不存在！！");
                    }
                }
            }
            return result;
        }else{
            return null;
        }
        /*}else{
            return OpResult.buildError("错误！！");
        }*/
    }

    /**
    * @author Jason
    * @date 2020/7/17 13:25
    * @params [token, request, response]
    * 下载文件
    * @return void
    */
    public void download(Token token,HttpServletRequest request, HttpServletResponse response) {
        String type = token.getType();
        token = tokenService.get(token);
        if(token != null){
            try {
                response.setContentType("APPLICATION/OCTET-STREAM");
                response.setHeader("Content-Disposition","attachment; filename="+new String((token.getFileName()).getBytes("gb2312"), "ISO8859-1"));
                //String filePath = null;
                if(StringUtils.isNotBlank(type)){
                    /*if(FILE_TYPE.equals(type) || type.equals(String.valueOf(FtpClientUtil.DEFAULT))){
                        Directory directory = directoryService.get(token.getFileId());
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
                        if(type.equals(FILE_TYPE)){
                            type = String.valueOf(FtpClientUtil.DEFAULT);
                        }
                    }else if(type.equals(String.valueOf(FtpClientUtil.YL_1)) ||
                            type.equals(String.valueOf(FtpClientUtil.YL_2)) ||
                            type.equals(String.valueOf(FtpClientUtil.YL_3)) ||
                            type.equals(String.valueOf(FtpClientUtil.DY)) ||
                            type.equals(String.valueOf(FtpClientUtil.SH))){*/
                    //去除命名空间
                    String originalId = token.getFileId().substring(token.getFileId().indexOf("/"));
                    String ftpPath = originalId.substring(0,originalId.lastIndexOf("/"));
                    String nameSpace = FlightDataFileUtil.getNameSpace(type);
                    String localPath = FlightDataFileUtil.PREFIX + nameSpace + originalId;
                    //优先从ftp服务器下载文件，ftp不存在该文件，则尝试从本地下载
                    if(FtpClientUtil.fileExist(Integer.parseInt(type),ftpPath, token.getFileName())){
                        Map<String, String> map = FtpClientUtil.downFile(Integer.parseInt(type),ftpPath, token.getFileName(), response.getOutputStream());
                        if (ResultMapUtil.isSuccessFully(map)) {
                            //保存历史记录
                            this.saveHistory(token,type,request);
                            tokenService.delete(token);
                        }else {
                            response.setHeader("Content-Disposition","attachment; filename="+new String((token.getFileName()+"不存在.txt").getBytes("gb2312"), "ISO8859-1"));
                        }
                    }else if(FlightDataFileUtil.fileExist(localPath)){
                        Map<String, String> map = FlightDataFileUtil.downFileFromLocal(localPath, response.getOutputStream());
                        if(ResultMapUtil.isSuccessFully(map)){
                            //保存历史记录
                            this.saveHistory(token,type,request);
                            tokenService.delete(token);
                            response.setHeader("Content-Disposition","attachment; filename="+new String((token.getFileName()).getBytes("gb2312"), "ISO8859-1"));
                        }else if(ResultMapUtil.isFailed(map)){
                            response.setHeader("Content-Disposition","attachment; filename="+new String((token.getFileName()+"不存在.txt").getBytes("gb2312"), "ISO8859-1"));
                        }else{
                            response.setHeader("Content-Disposition","attachment; filename="+new String(("系统异常.txt").getBytes("gb2312"), "ISO8859-1"));
                        }
                    }else{
                        response.setHeader("Content-Disposition","attachment; filename="+new String((token.getFileName()+"不存在.txt").getBytes("gb2312"), "ISO8859-1"));
                    }
                }else{
                    response.setHeader("Content-Disposition","attachment; filename="+new String(("系统异常.txt").getBytes("gb2312"), "ISO8859-1"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                tokenService.delete(token);
            }
        }
    }

    private void saveHistory(Token token,String type,HttpServletRequest request){
        DownLoadHistory history = new DownLoadHistory();
        User user = UserUtils.getUser();
        history.setDownLoadDate(new Date());
        history.setDownLoaderId(user.getId());
        history.setDownLoaderName(user.getName());
        history.setDownLoadFileId(token.getFileId());
        history.setDownLoadFileName(token.getFileName());
        history.setDownLoadFileSize(token.getFileSize());
        history.setIpAddress(IpAddressUtil.getIpAddress(request));
        history.setIsValid("1");
        history.setType(type);
        history.setFileType(token.getFileType());
        historyService.save(history);
    }

    /**
    * @author Jason
    * @date 2020/7/17 13:25
    * @params [directory]
    * 点击ztree节点，尝试获取下一节点
    * @return java.util.List<java.util.Map<java.lang.String,java.lang.String>>
    */
    public List<Map<String, String>> getSubDirectory(Directory directory){
        List<Map<String, String>> dirList = null;
        String path = directory.getId();
        if(StringUtils.isNotBlank(path) && path.contains("/")){
            //去除命名空间
            path = path.substring(path.indexOf("/"));
            //判断点击的是ftp文件还是本地文件，如果点击了ftp文件，则不仅需要加载ftp文件，还需要加载本地文件
            if(FtpClientUtil.TYPE.equals(directory.getFileType())){
                dirList = FtpClientUtil.getDirList(Integer.parseInt(directory.getType()),
                        FtpClientUtil.connection(Integer.parseInt(directory.getType())),
                        new ArrayList<>(), path, 1, 0);
                String nameSpace = FlightDataFileUtil.getNameSpace(directory.getType());
                //去除最后一个斜杠
                String localPath = FlightDataFileUtil.PREFIX + nameSpace + path.substring(0,path.length() - 1);
                dirList.addAll(FlightDataFileUtil.getDirList(Integer.parseInt(directory.getType()),localPath,nameSpace,
                        new ArrayList<>(),1,0));
                //根据id去重，ftp和本地同时存在同一文件夹时，保留ftp文件夹
                dirList = Collections3.distinctListMap(dirList,"id");
            }else if(FlightDataFileUtil.TYPE.equals(directory.getFileType())){
                String nameSpace = FlightDataFileUtil.getNameSpace(directory.getType());
                //拼接本地物理路径
                path = FlightDataFileUtil.PREFIX + nameSpace + "/" + path;
                dirList = FlightDataFileUtil.getDirList(Integer.parseInt(directory.getType()),path,nameSpace,
                        new ArrayList<>(),1,0);
            }
        }
        return dirList;
    }

    /**
    * @author Jason
    * @date 2020/7/17 13:26
    * @params [type]
    * 获取文件夹，包含ftp服务器文件和本地文件
    * @return java.util.List<java.util.Map<java.lang.String,java.lang.String>>
    */
    public List<Map<String,String>> directoryList(Integer type){
        if(null != type){
            /*if(type.equals(FtpClientUtil.DEFAULT)){
                return directoryAllList();
            }else if(type.equals(FtpClientUtil.YL_1)){
            }else if(type.equals(FtpClientUtil.YL_2)){
            }else if(type.equals(FtpClientUtil.YL_3)){
            }else if(type.equals(FtpClientUtil.DY)){
            }else if(type.equals(FtpClientUtil.SH)){
            }*/
            try {
                List<Map<String, String>> list = FtpClientUtil.getDirList(type);
                list.addAll(FlightDataFileUtil.getDirList(type));
                //根据id去重，ftp和本地同时存在同一文件时，保留ftp文件
                list = Collections3.distinctListMap(list,"id");
                return list;
            }catch (Exception e){
                return null;
            }
        }
        return null;
    }

    /**
    * @author Jason
    * @date 2020/7/17 13:25
    * @params [directory, request, response]
    * 获取指定文件夹下的所有文件，包含ftp服务器文件和本地文件
    * @return com.demxs.tdm.common.persistence.Page<com.demxs.tdm.domain.dataCenter.pr.Directory>
    */
    public Page<Directory> fileList(Directory directory, HttpServletRequest request, HttpServletResponse response){
        Page<Directory> page = new Page<>(request,response);
        if(null != directory && StringUtils.isNotBlank(directory.getType())){
            /*if(directory.getType().equals(String.valueOf(FtpClientUtil.DEFAULT))){
                directory.setPage(page);
                List<Directory> list = directoryService.findFileList(directory.setpId(directory.getId()));
                page.setList(list);
            }else if(directory.getType().equals(String.valueOf(FtpClientUtil.YL_1)) ||
                     directory.getType().equals(String.valueOf(FtpClientUtil.YL_2)) ||
                     directory.getType().equals(String.valueOf(FtpClientUtil.YL_3)) ||
                     directory.getType().equals(String.valueOf(FtpClientUtil.DY)) ||
                     directory.getType().equals(String.valueOf(FtpClientUtil.SH))){*/
            //去除命名空间
            String originalId = "";
            if(directory.getId().contains("/")){
                originalId = directory.getId().substring(directory.getId().indexOf("/"));
            }
            try {
                //ftp服务器文件
                List<FTPFile> ftpFiles = null;
                //本地文件
                List<File> localFiles = null;
                //判断点击的是ftp文件还是本地文件，如果点击了ftp文件，则不仅需要加载ftp文件，还需要加载本地文件
                if(FtpClientUtil.TYPE.equals(directory.getFileType())){
                    ftpFiles = FtpClientUtil.getFiles(Integer.parseInt(directory.getType()), originalId,directory.getName());
                    String nameSpace = FlightDataFileUtil.getNameSpace(directory.getType());
                    //拼接本地物理路径
                    String path = FlightDataFileUtil.PREFIX + nameSpace + "/" + originalId;
                    localFiles = FlightDataFileUtil.getFiles(path,directory.getName());
                }else if(FlightDataFileUtil.TYPE.equals(directory.getFileType())){
                    String nameSpace = FlightDataFileUtil.getNameSpace(directory.getType());
                    //拼接本地物理路径
                    String path = FlightDataFileUtil.PREFIX + nameSpace + "/" + originalId;
                    localFiles = FlightDataFileUtil.getFiles(path,directory.getName());
                }
                if(null != ftpFiles){
                    //List<FTPFile> ftpFileList = ftpFiles.subList((page.getPageNo() - 1) * page.getPageSize(), range);
                    List<Directory> dataList = new ArrayList<>(ftpFiles.size());
                    for (FTPFile ftpFile : ftpFiles) {
                        Directory dir = new Directory(directory.getId()+ftpFile.getName());
                        dir.setType(directory.getType()).setName(ftpFile.getName());
                        dir.setFileLength(String.valueOf(ftpFile.getSize()));
                        dir.setFileType(FtpClientUtil.TYPE);
                        dir.setFileUrl(originalId+ftpFile.getName());
                        dataList.add(dir);
                    }
                    if(null != localFiles){
                        for (File file : localFiles) {
                            Directory dir = new Directory(directory.getId() + file.getName());
                            dir.setType(directory.getType()).setName(file.getName());
                            dir.setFileLength(String.valueOf(file.length()));
                            dir.setFileType(FlightDataFileUtil.TYPE);
                            dir.setFileUrl(originalId+file.getName());
                            dataList.add(dir);
                        }
                    }
                    if(null != localFiles){
                        //根据id去重，ftp和本地同时存在同一文件时，保留ftp文件
                        dataList = dataList.stream().collect(
                                Collectors.collectingAndThen(Collectors.toCollection(
                                        () -> new TreeSet<>(Comparator.comparing(Directory::getId))),ArrayList::new));
                    }
                    //手动分页
                    int count = dataList.size();
                    int range = Math.min(page.getPageSize() * page.getPageNo(), dataList.size());
                    dataList = dataList.subList((page.getPageNo() - 1) * page.getPageSize(), range);
                    page.setList(dataList);
                    page.setCount(count);
                }else if(null != localFiles){
                    List<Directory> dataList = new ArrayList<>(localFiles.size());
                    for (File file : localFiles) {
                        Directory dir = new Directory(directory.getId() + file.getName());
                        dir.setType(directory.getType()).setName(file.getName());
                        dir.setFileLength(String.valueOf(file.length()));
                        dir.setFileType(FlightDataFileUtil.TYPE);
                        dir.setFileUrl(originalId+file.getName());
                        dataList.add(dir);
                    }
                    //手动分页
                    int range = Math.min(page.getPageSize() * page.getPageNo(), localFiles.size());
                    dataList = dataList.subList((page.getPageNo() - 1) * page.getPageSize(), range);
                    page.setList(dataList);
                    page.setCount(dataList.size());
                }
            }catch (Exception e){
                return null;
            }
            //}
        }
        return page;
    }
}
