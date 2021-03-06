package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.dataCenter.DataBackUpDao;
import com.demxs.tdm.dao.dataCenter.DataBackUpFileDao;
import com.demxs.tdm.domain.dataCenter.DataBackUp;
import com.demxs.tdm.domain.dataCenter.DataBackUpFile;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
@Component
public class DataBackUpFileService extends  CrudService<DataBackUpFileDao, DataBackUpFile> {

    public Logger  log= LoggerFactory.getLogger(DataBackUpFileService.class);

    @Autowired
    DataBackUpFileDao dataBackUpFileDao;

    @Autowired
    DataBackUpDao dataBackUpDao;


    private  static  final String  BACK_DAY="day";
    private  static  final String  BACK_MONTH="month";
    private  static  final String  BACK_WEEK="week";
    private  static  final String  SAVE_PATH= "D:/backUp/temp";

    public Page<DataBackUpFile> list(Page<DataBackUpFile> page, DataBackUpFile entity) {
        Page<DataBackUpFile> Page = super.findPage(page, entity);
        return Page;
    }

    public Page<DataBackUpFile> findPage(Page<DataBackUpFile> page, DataBackUpFile entity) {
        entity.setPage(page);
        page.setList(dao.findList(entity));
        return page;
    }


    public DataBackUpFile get(String id) {
        DataBackUpFile entity = super.dao.get(id);
        return entity;
    }


    public void save(DataBackUpFile entity) {

        super.save(entity);

    }


    //???????????????????????????
   public  String  getDateDay(String  backTime){
      return  dataBackUpFileDao.getDateDay(backTime);
   }


    //???????????????????????????
    public  String  getDateMonth(String  backTime){
        return dataBackUpFileDao.getDateMonth(backTime);
    }

    //???????????? ---flag=true --????????????  ??????????????????
    public Boolean backUpOracle(){
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        //??????????????????1
        int months = cal.get(Calendar.MONTH)+1;
        int days = cal.get(Calendar.DATE);
        int hourOfDay = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        String nowDate=""+year+months+days+hourOfDay+minute+second;
        String userName= Global.getConfig("jdbc.username");
        String password= Global.getConfig("jdbc.password");
        String SID=Global.getConfig("backup.sid");
        DataBackUp  d=new DataBackUp();
        d.setAttribute1("0");
        //????????????????????????
        List<DataBackUp> dataBackUpList = dataBackUpDao.findDataList(d);

        if (!CollectionUtils.isEmpty(dataBackUpList)) {
            for (DataBackUp db : dataBackUpList) {
                String backPeroid = db.getBackPeroid();
                String backTime = "";
                DataBackUpFile dbc = new DataBackUpFile();
                dbc.setBackId(db.getId());
                dbc.setBackPeroid(db.getBackPeroid());
                dbc.setBackTime(db.getBackTime());
                dbc.setFilePath(SAVE_PATH);

                try {
                    backTime = sdf.format(db.getBackTime());

                    if (BACK_DAY.equals(backPeroid)) {
                        String day = dataBackUpFileDao.getDateDay(backTime);
                        String fileName = nowDate+".dmp";
                        dbc.setFileName(fileName);
                        if(Double.parseDouble(day)<0){
                            return false;
                        }
                        //??????????????????-?????????????????????--????????????
                        if (isInteger(day)) {
                            Boolean b = exportDatabaseTool(userName, password, SID, SAVE_PATH, fileName);
                            if (b) {
                                String fileSize = String.valueOf(new File(fileName).length());
                                dbc.setFileSize(fileSize);
                                dbc.preInsert();
                                dataBackUpFileDao.insert(dbc);
                            }else{
                                log.error("-----backUpOracleMunue---??????????????????-----------");
                            }
                        }

                    }
                    if (BACK_WEEK.equals(backPeroid)) {
                        String day = dataBackUpFileDao.getDateDay(backTime);
                        String fileName = nowDate+".dmp";
                        dbc.setFileName(fileName);
                        BigDecimal bd1 = new BigDecimal(day);
                        BigDecimal bd2 = new BigDecimal("7");
                        if(Double.parseDouble(day)<0){
                            return false;
                        }
                        String count = bd1.divide(bd2,4, RoundingMode.HALF_UP).toPlainString();
                        //??????????????????-?????????????????????--????????????
                        if (isInteger(count)) {
                            Boolean b = exportDatabaseTool(userName, password, SID, SAVE_PATH, fileName);
                            if (b) {
                                String fileSize = String.valueOf(new File(fileName).length());
                                dbc.setFileSize(fileSize);
                                dbc.preInsert();
                                dataBackUpFileDao.insert(dbc);
                            }else{
                                log.error("-----backUpOracleMunue---??????????????????-----------");
                            }
                        }
                    }
                    if (BACK_MONTH.equals(backPeroid)) {
                        String month = dataBackUpFileDao.getDateMonth(backTime);
                        String fileName = nowDate+".dmp";
                        dbc.setFileName(fileName);
                        if(Double.parseDouble(month)<0){
                            return false;
                        }
                        //??????????????????-?????????????????????--????????????
                        if (isInteger(month)) {
                            Boolean b = exportDatabaseTool(userName, password, SID, SAVE_PATH, fileName);
                            if (b) {
                                String fileSize = String.valueOf(new File(fileName).length());
                                dbc.setFileSize(fileSize);
                                dbc.preInsert();
                                dataBackUpFileDao.insert(dbc);
                            }else{
                                log.error("-----backUpOracleMunue---??????????????????-----------");
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("-----backUpOracleMunue---??????????????????-----------");
                    return false;
                }

            }
        }
        return true;
    }

    public  DataBackUpFile  backUpOracleMunue(DataBackUp dataBackUp){
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        //??????????????????1
        int months = cal.get(Calendar.MONTH)+1;
        int days = cal.get(Calendar.DATE);
        int hourOfDay = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        String nowDate=""+year+months+days+hourOfDay+minute+second;
        String userName= Global.getConfig("jdbc.username");
        String password= Global.getConfig("jdbc.password");
        String SID=Global.getConfig("backup.sid");
        DataBackUpFile  dbcFile=new DataBackUpFile();
        try{
            String fileName=nowDate+".dmp";
            dbcFile.setFileName(fileName);
            dbcFile.setBackId(dataBackUp.getId());
            dbcFile.setBackTime(dataBackUp.getBackTime());
            dbcFile.setBackPeroid(dataBackUp.getBackPeroid());
            dbcFile.setFilePath(SAVE_PATH);
            dbcFile.setAttribute1("1");
            Boolean b= exportDatabaseTool(userName,password,SID,SAVE_PATH,fileName);
            if(b){
                String fileSize=String.valueOf(new File(fileName).length());
                dbcFile.setFileSize(fileSize);
                dbcFile.preInsert();
                dataBackUpFileDao.insert(dbcFile);
            }else{
                log.error("-----backUpOracleMunue---??????????????????-----------");
                return new DataBackUpFile();
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("-----backUpOracleMunue---??????????????????-----------");
            return new DataBackUpFile();
        }
        return dbcFile;
    }

    public void download(DataBackUpFile d, HttpServletResponse response) {
        File file = new File(d.getFilePath());
        FileInputStream fis = null;
        try {
            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-Disposition","attachment; filename="+new String((d.getFileName()).getBytes("gb2312"), "ISO8859-1"));
            fis = new FileInputStream(file);
            byte[] data = new byte[1024];
            int len=0;
            ServletOutputStream os = response.getOutputStream();
            while ((len = fis.read(data)) != -1){
                os.write(data,0,len);
            }
            os.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(null != fis){
                    fis.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    private  static boolean isInteger(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public boolean exportDatabaseTool(String userName, String password, String SID, String savePath, String fileName) throws InterruptedException {
        File saveFile = new File(savePath);
        if (!saveFile.exists()) {// ?????????????????????
            saveFile.mkdirs();// ???????????????
        }
        try {
            String s="exp " + userName + "/" + password + "@" + SID + " file=" + savePath + "/" + fileName+"  owner="+userName;
            System.out.println("----"+s);
            Process process = Runtime.getRuntime().exec(s);
           /* BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            process.getOutputStream().close();*/

            final InputStream is1 = process.getInputStream();

            new Thread(new Runnable() {
                public void run() {
                    BufferedReader br = new BufferedReader(new InputStreamReader(is1));
                    String info;
                    try {
                        while ((info=br.readLine()) != null){
                            System.out.println("info: "+info);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start(); // ??????????????????????????????process.getInputStream()????????????
            InputStream is2 = process.getErrorStream();
            BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));
            // ??????????????????
            StringBuilder buf = new StringBuilder();
            String line = null;
            int i=0;
            while ((line = br2.readLine()) != null){
                // ????????????ffmpeg????????????
                System.out.println("info: " +line);
                buf.append(line);
            }
            try {
                if(buf.toString().contains("ORA-")&&buf.toString().contains("EXP-")){
                    System.err.println("???????????????");
                    process.destroy();
                }else{
                    i=process.waitFor();
                    if(i == 0){//0 ???????????????????????????
                        return true;
                    }else{
                        log.error("-----exportDatabaseTool---??????????????????-----------");
                        return false;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }



        } catch (IOException e) {
            e.printStackTrace();
            log.error("--------exportDatabaseTool--------","??????????????????");
            return false;
        }
        return false;
    }

}
