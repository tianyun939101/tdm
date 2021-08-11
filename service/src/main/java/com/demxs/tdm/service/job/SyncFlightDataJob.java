package com.demxs.tdm.service.job;

import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.sys.entity.Dict;
import com.demxs.tdm.common.sys.utils.DictUtils;
import com.demxs.tdm.common.utils.FtpClientUtil;
import com.demxs.tdm.common.utils.IOUtil;
import com.demxs.tdm.domain.dataCenter.pr.SynchronizeTask;
import com.demxs.tdm.domain.dataCenter.pr.SynchronizeTaskRecord;
import com.demxs.tdm.service.dataCenter.pr.SynchronizeTaskRecordService;
import com.demxs.tdm.service.dataCenter.pr.SynchronizeTaskService;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author: Jason
 * @Date: 2020/7/9 14:07
 * @Description: 同步试飞数据任务
 */
@Component
public class SyncFlightDataJob implements ApplicationListener<ContextRefreshedEvent> {

    private SyncFlightDataJob(){
    }

    private static final Logger log = LoggerFactory.getLogger(SyncFlightDataJob.class);

    @Autowired
    private SynchronizeTaskService taskService;
    @Autowired
    private SynchronizeTaskRecordService recordService;

    private final static String BASE_DIE = Global.getConfig("sourceDir");
    private final static String FLIGHT_DATA_DIR = Global.getConfig("pr.flightData.synchronizeData.dir");
    private final static String SYMBOL = "/";
    private final static String PREFIX = BASE_DIE + SYMBOL + FLIGHT_DATA_DIR + SYMBOL;
    private final static String EXECUTE_TIME = Global.getConfig("pr.flightData.synchronizeData.time");
    private final static String ENABLED = Global.getConfig("pr.flightData.enableSynchronizeData");
    /**
     * 转换命名空间
     */
    private final static Map<String,String> NAME_SPACE_MAP = new HashMap<>(16);
    /**
     * 定时任务单例线程池
     */
    private final static ScheduledExecutorService SINGLE_POOL = Executors.newSingleThreadScheduledExecutor();

    /**
    * @author Jason
    * @date 2020/7/10 14:24
    * @params [event]
    * spring启动后执行此方法，单例线程池定时处理任务
    * @return void
    */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //spring有两个容器，不做判断将导致被执行两次，
        if (event.getApplicationContext().getParent() == null) {
            if("true".equals(ENABLED)){
                //需要执行的方法
                long oneDay = 24 * 60 * 60 * 1000;
                long initDelay = getTimeMillis(EXECUTE_TIME) - System.currentTimeMillis();
                //计算时间差异后定时
                initDelay = initDelay > 0 ? initDelay : oneDay + initDelay;
                SINGLE_POOL.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        execute();
                    }
                },initDelay,oneDay, TimeUnit.MILLISECONDS);
            }
        }
    }

    /**
    * @author Jason
    * @date 2020/7/10 14:24
    * @params [time]
    * 获取时间差异
    * @return long
    */
    private static long getTimeMillis(String time) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
            Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);
            return curDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    static {
        List<Dict> dictList = DictUtils.getDictList("flight_data_server_type");
        for (Dict dict : dictList) {
            NAME_SPACE_MAP.put(dict.getValue(),dict.getLabel());
        }
    }

    /**
    * @author Jason
    * @date 2020/7/10 14:25
    * @params []
    * 同步ftp数据
    * @return void
    */
    public void execute(){
        List<SynchronizeTask> list = taskService.findList(new SynchronizeTask());
        log.debug("同步试飞数据开始");
        if(null != list){
            for (SynchronizeTask task : list) {
                SynchronizeTaskRecord taskRecord = new SynchronizeTaskRecord();
                taskRecord.setTaskId(task.getId());
                String taskName = task.getName() == null ? "" : task.getName();
                String infoHead = "同步["+task.getPath()+SYMBOL+taskName+"]";
                int count = 0;
                int error = 0;
                try {
                    if(task.getName() == null || !this.taskExists(task)){
                        FTPClient ftpClient = FtpClientUtil.connection(Integer.parseInt(task.getNameSpace()));
                        if(null == ftpClient){
                            log.error("连接ftp服务器失败");
                            taskRecord.setInfo(infoHead+"失败，连接至数据中心服务器失败");
                            taskRecord.setStatus(SynchronizeTaskRecord.FAILED);
                        }else{
                            ftpClient.enterLocalPassiveMode();
                            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                            if(task.isDirectory()){
                                boolean changed = ftpClient.changeWorkingDirectory(SYMBOL +task.getPath() + SYMBOL + taskName);
                                String nameSpace = NAME_SPACE_MAP.get(task.getNameSpace());
                                //创建本地物理路径
                                File file = new File(PREFIX + nameSpace + SYMBOL +
                                        task.getPath() + SYMBOL + taskName);
                                if(!file.exists()){
                                    file.mkdirs();
                                }
                                if(changed){
                                    FTPFile[] ftpFiles = ftpClient.listFiles();
                                        for (FTPFile ftpFile : ftpFiles) {
                                            try {
                                                String taskPrefixPath = SYMBOL + task.getPath() + SYMBOL + taskName;
                                                if(ftpFile.isDirectory()){
                                                    count += synFile( nameSpace,taskPrefixPath + SYMBOL + ftpFile.getName(), ftpFile, ftpClient, 0);
                                                }else{
                                                    InputStream is = ftpClient.retrieveFileStream(taskPrefixPath + SYMBOL + ftpFile.getName());
                                                    IOUtil.writeByteToFile(is,PREFIX + nameSpace + SYMBOL + taskPrefixPath + SYMBOL + ftpFile.getName());
                                                    completeDownload(is,ftpClient);
                                                    count++;
                                                }
                                            }catch (Exception e){
                                                e.printStackTrace();
                                                error++;
                                                log.error(infoHead+"子文件"+ ftpFile.getName() +"失败:"+e.getMessage());
                                            }
                                        }
                                    if(error > 0){
                                        taskRecord.setInfo(infoHead+"成功，共计同步"+count+"个文件，"+error+"个文件同步失败：该文件不存在");
                                    }else {
                                        taskRecord.setInfo(infoHead+"成功，共计同步"+count+"个文件");
                                    }
                                    taskRecord.setStatus(SynchronizeTaskRecord.SUCCESS);
                                }else{
                                    taskRecord.setInfo(infoHead+"失败，该文件不存在");
                                    taskRecord.setStatus(SynchronizeTaskRecord.FAILED);
                                }
                            }else{
                                //创建本地物理路径
                                String nameSpace = NAME_SPACE_MAP.get(task.getNameSpace());
                                File file = new File(PREFIX + nameSpace + SYMBOL +
                                        task.getPath());
                                if(!file.exists()){
                                    file.mkdirs();
                                }
                                InputStream is = ftpClient.retrieveFileStream(SYMBOL + task.getPath() + SYMBOL + task.getName());
                                if(null != is){
                                    IOUtil.writeByteToFile(is,
                                            PREFIX + nameSpace + SYMBOL + task.getPath() + SYMBOL + task.getName());
                                    completeDownload(is,ftpClient);
                                    taskRecord.setInfo(infoHead+"成功");
                                    taskRecord.setStatus(SynchronizeTaskRecord.SUCCESS);
                                }else{
                                    taskRecord.setInfo(infoHead+"失败，该文件不存在");
                                    taskRecord.setStatus(SynchronizeTaskRecord.FAILED);
                                }
                            }
                            //登出
                            ftpClient.logout();
                        }
                    }else{
                        taskRecord.setStatus(SynchronizeTaskRecord.SKIP);
                        taskRecord.setInfo(infoHead+"被跳过:该任务存在父级任务");
                    }
                }catch (Exception e){
                    log.error(infoHead+"失败:"+e.getMessage());
                    e.printStackTrace();
                    taskRecord.setInfo(e.getMessage());
                    taskRecord.setStatus(SynchronizeTaskRecord.FAILED);
                }
                recordService.save(taskRecord);
            }
        }
    }

    /**
    * @author Jason
    * @date 2020/7/10 14:28
    * @params [task]
    * 检查同步任务是否已有父级任务声明
    * @return boolean
    */
    private boolean taskExists(SynchronizeTask task){
        SynchronizeTask parent = taskService.getParentByPath(task);
        if(!task.getPath().contains(SYMBOL)){
            if(parent != null && parent.getName() == null){
                //解决根目录问题
                return true;
            }else {
                return false;
            }
        }
        if(parent != null && parent.getName() == null){
            //解决根目录问题
            return true;
        }else if(parent == null){
            parent = new SynchronizeTask();
            parent.setPath(task.getPath().substring(0,task.getPath().lastIndexOf("/")));
            parent.setName(task.getPath().substring(task.getPath().lastIndexOf("/")));
            parent.setNameSpace(task.getNameSpace());
            return this.taskExists(parent);
        }else{
            return true;
        }
    }

    /**
    * @author Jason
    * @date 2020/7/10 14:28
    * @params [nameSpace, path, originalFtpFile, ftpClient, count]
    * 递归下载文件至本地，返回下载文件数量
    * @return int
    */
    private int synFile(String nameSpace,String path, FTPFile originalFtpFile, FTPClient ftpClient,int count) throws IOException {
        //创建本地物理路径
        File file = new File(PREFIX + nameSpace + SYMBOL + path);
        if(!file.exists()){
            file.mkdirs();
        }
        if(originalFtpFile.isDirectory()){
            boolean changed = ftpClient.changeWorkingDirectory(path + SYMBOL);
            if(changed){
                FTPFile[] ftpFiles = ftpClient.listFiles();
                if(null != ftpFiles){
                    for (FTPFile ftpFile : ftpFiles) {
                        if(ftpFile.isDirectory()){
                            count += synFile(nameSpace,path + SYMBOL + ftpFile.getName(),ftpFile,ftpClient,0);
                        }else{
                            String ftpFilePath = path + SYMBOL + ftpFile.getName();
                            InputStream is = ftpClient.retrieveFileStream(ftpFilePath);
                            IOUtil.writeByteToFile(is,
                                    PREFIX + nameSpace + SYMBOL + path + SYMBOL + ftpFile.getName());
                            completeDownload(is,ftpClient);
                            count++;
                        }
                    }
                }
            }
        }else{
            InputStream is = ftpClient.retrieveFileStream(path + SYMBOL);
            IOUtil.writeByteToFile(is,PREFIX + nameSpace + SYMBOL + path + SYMBOL + originalFtpFile.getName());
            completeDownload(is,ftpClient);
            count++;
        }
        return count;
    }

    /**
    * @author Jason
    * @date 2020/7/10 14:28
    * @params [is, ftpClient]
    * ftpClient retrieveFileStream方法执行后，必须关闭流，并通知服务器完成情况
    * 否则下一次再次尝试下载时抛出空指针异常
    * @return void
    */
    private void completeDownload(InputStream is,FTPClient ftpClient){
        try {
            is.close();
            ftpClient.completePendingCommand();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
