package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.sys.dao.DictDao;
import com.demxs.tdm.common.sys.entity.Dict;
import com.demxs.tdm.common.utils.FileUtils;
import com.demxs.tdm.common.utils.IpAddressUtil;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.dataCenter.pr.Mock8DownloadHistory;
import com.demxs.tdm.service.dataCenter.pr.Mock8DownloadHistoryService;
import com.demxs.tdm.service.sys.DictService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

/**
 * @author: Jason
 * @Date: 2020/6/5 14:42
 * @Description:
 */
@Service
@Transactional(readOnly = true)
public class FlightMoc8DataService {
    private final static String DIC_DRI = "drive";
    private final static String ROOT_PATH = "D:\\temp\\temp";
    //测试获取目录大小
   //private final static String ROOT_PATH_length = "E:\\testTdm";
    @Autowired
    private Mock8DownloadHistoryService mock8Service;
    @Autowired
    private DictDao dictDao;
    @Autowired
    private DictService di;
  //  private final static String ROOT_PATH = "/Users/zhengweiming/Downloads/123";

    public List<Map<String,String>> dirList(String drive){
        if(StringUtils.isBlank(drive)){
            Dict dict = new Dict();
            dict.setType(DIC_DRI);
            List<Dict> list = dictDao.findList(dict);
            if(CollectionUtils.isNotEmpty(list)){
                drive = list.get(0).getValue();
            }

        }
       // Integer length = getLength(new File(ROOT_PATH_length));
        File file = new File(drive);
        File[] files = file.listFiles();
        List<Map<String,String>> list = new ArrayList<>();
        int depth = 0;
        this.recursionFind(list,files,null,depth);
        //测试获取目录大小

        return list;
    }

    //获取文件大小
    /*private Integer getLength(File file){
        long l = FileUtils.sizeOfDirectory(file);
        System.out.println(l);
        return 0;
    }*/



    private void recursionFind(List<Map<String,String>> list,File[] files,String pId,int depth){
        for (File file : files) {
            if(file.isDirectory() && depth < 5){
                Map<String, String> map = new HashMap<>(3);
                map.put("name",file.getName());
                map.put("id",file.getPath());
                map.put("pId",pId);
                list.add(map);
                int tempCurDepth = depth + 1;
                this.recursionFind(list,file.listFiles(),file.getPath(),tempCurDepth);
            }/*else{
                Map<String, String> map = new HashMap<>(3);
                map.put("name",file.getName());
                map.put("id",file.getPath());
                map.put("pId",pId);
                list.add(map);
            }*/
        }
    }

    public List<Map<String,String>> fileList(String dirPath){
        File[] files = new File(dirPath).listFiles();
        List<Map<String,String>> list = new ArrayList<>();
        if(null != files){
            for (File file : files) {
                if(file.isFile()){
                    Map<String, String> map = new HashMap<>(3);
                    map.put("name",file.getName());
                    String path = file.getPath();
                    //js传参反斜杠问题
                    String[] split = path.split("\\\\");
                    StringBuilder sb = new StringBuilder();
                    for (String s : split) {
                        sb.append(s).append("\\\\");
                    }
                    map.put("id",sb.substring(0,sb.length() - 2));
                    map.put("pId",dirPath);
                    map.put("fileLength",String.valueOf(file.length()));
                    list.add(map);
                }
            }
        }
        return list;
    }

    public void download(String path, HttpServletResponse response, HttpServletRequest request) {
        String pathStr=path.replace("$","+");
        File file = new File(pathStr);
        String fileName=file.getName().replace("$","+");
        FileInputStream fis = null;
        try {
            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-Disposition","attachment; filename="+new String((fileName).getBytes("gb2312"), "ISO8859-1"));
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


    public List<Map<String,String>> getSubDirectory(String path){
        File file = new File(path);
        File[] files = file.listFiles();
        List<Map<String,String>> list = new ArrayList<>();
        int depth = 4;
        this.recursionFind(list,files,null,depth);
        return list;
    }

    public Page<Dict> findDirve(Page<Dict> page,Dict dict){
        dict.setType(DIC_DRI);
        List<Dict> list = dictDao.findList(dict);
        page.setList(list);
        return page;

    }

    public void saveDirve(Dict dict){
        UUID uuid = UUID.randomUUID();
        String s = uuid.toString();
        String dirve = di.findSortByType("dirve");
        // dict.setId(s);
        dict.setDescription("盘符");
        dict.setDelFlag("0") ;
        if(StringUtils.isNotBlank(dirve)){
            dict.setSort(Integer.valueOf(dirve)+1);
        }else{
            dict.setSort(1);
        }
        dict.setType(DIC_DRI);
        di.save(dict);
    }
    public void deleteDirve(Dict dict) {
        di.delete(dict);
    }

   }
