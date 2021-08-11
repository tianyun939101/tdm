package com.demxs.tdm.service.business.util;

import com.demxs.tdm.common.utils.CacheUtils;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.common.utils.SpringContextHolder;
import com.demxs.tdm.common.utils.*;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * 系统业务编号生成工具类
 * User: wuliepeng
 * Date: 2017-11-29
 * Time: 上午9:48
 */
public class CodeUtil {

    private static final int ENTRUST_SER_LEN = 3;//样品编号的流水号长度
    private static final int SAMPLE_SER_LEN = 3;//样品编号的流水号长度
    private static final int TASK_CODE_LEN = 4;//任务编号号长度
    private static final int SUB_TASK_CODE_LEN = 3;//样品编号的流水号长度

    /**
     * 生成申请单编号
     * @return
     */
    public static String genEntrustCode(){
        JdbcTemplate jdbcTemplate = SpringContextHolder.getBean("jdbcTemplate");
        List<String> result = jdbcTemplate.queryForList(
                "select * from (select code from BS_ENTRUST_INFO WHERE CODE LIKE '"+ DateUtils.getDate("yyyyMMdd") +"%' ORDER BY CODE DESC) WHERE ROWNUM = 1",String.class);
        String prefix = DateUtils.getDate("yyyyMMdd");
        String seqStr;
        if(result.isEmpty()){
            seqStr = IdGen.getSeqNo(1,ENTRUST_SER_LEN);
        }else{
            String maxCode = result.get(0);
            seqStr = IdGen.getSeqNo(Integer.parseInt(maxCode.substring(maxCode.length()-3))+1,ENTRUST_SER_LEN);
        }
        return prefix + seqStr;
    }

    /**
    * @author Jason
    * @date 2020/6/29 17:10
    * @params []
    * 非标任务申请单编号
    * @return java.lang.String
    */
    public static String getNoStandardEntrustCode(){
        JdbcTemplate jdbcTemplate = SpringContextHolder.getBean("jdbcTemplate");
        List<String> result = jdbcTemplate.queryForList(
                "select * from (select code from BS_NOSTANDARD_ENTRUST_INFO WHERE CODE LIKE '"+ DateUtils.getDate("yyyyMMdd") +"%' ORDER BY CODE DESC) WHERE ROWNUM = 1",String.class);
        String prefix = DateUtils.getDate("yyyyMMdd");
        String seqStr;
        if(result.isEmpty()){
            seqStr = IdGen.getSeqNo(1,ENTRUST_SER_LEN);
        }else{
            String maxCode = result.get(0);
            seqStr = IdGen.getSeqNo(Integer.parseInt(maxCode.substring(maxCode.length()-3))+1,ENTRUST_SER_LEN);
        }
        return prefix + seqStr;
    }

    /**
     * @author Jason
     * @date 2020/6/29 17:10
     * @params []
     * 非标任务申请单编号
     * @return java.lang.String
     */
    public static String getNoNewStandardEntrustCode(){
        JdbcTemplate jdbcTemplate = SpringContextHolder.getBean("jdbcTemplate");
        List<String> result = jdbcTemplate.queryForList(
                "select * from (select code from BS_NOSTANDARD_TEST_CHECK WHERE CODE LIKE '"+ DateUtils.getDate("yyyyMMdd") +"%' ORDER BY CODE DESC) WHERE ROWNUM = 1",String.class);
        String prefix = DateUtils.getDate("yyyyMMdd");
        String seqStr;
        if(result.isEmpty()){
            seqStr = IdGen.getSeqNo(1,ENTRUST_SER_LEN);
        }else{
            String maxCode = result.get(0);
            seqStr = IdGen.getSeqNo(Integer.parseInt(maxCode.substring(maxCode.length()-3))+1,ENTRUST_SER_LEN);
        }
        return prefix + seqStr;
    }

    /**
     * 生成试验组编号
     * @return
     */
    public static String genTestGroupCode(String prefix){
        String cache_prefix = "test_group_code_" + prefix;
        Object obj = CacheUtils.get(cache_prefix);
        int curNum = 1;
        if(obj == null){
            CacheUtils.put(cache_prefix,curNum);
        }else{
            curNum = Integer.valueOf(obj.toString());
            CacheUtils.put(cache_prefix,curNum+1);
        }
        String seqStr = IdGen.getSeqNo(curNum,SAMPLE_SER_LEN);
        return prefix + "JCZ" + seqStr;
    }

    /**
     * 生成样品编号
     * @return
     */
    public static String genSampleCode(String prefix){
        JdbcTemplate jdbcTemplate = SpringContextHolder.getBean("jdbcTemplate");
        List<String> result = jdbcTemplate.queryForList(
                "select * from (SELECT SN FROM BS_ENTRUST_SAMPLE_GROUP_ITEM WHERE SN LIKE '"+prefix +"%' ORDER BY SN DESC) WHERE ROWNUM = 1",String.class);
        String seqStr;
        if(result.isEmpty()){
            seqStr = IdGen.getSeqNo(1,SAMPLE_SER_LEN);
        }else{
            String maxCode = result.get(0);
            seqStr = IdGen.getSeqNo(Integer.parseInt(maxCode.substring(maxCode.length()-3))+1,SAMPLE_SER_LEN);
        }
        return prefix + seqStr;

        /*String cache_prefix = "sample_code_" + prefix;
        Object obj = CacheUtils.get(cache_prefix);
        int curNum = 1;
        if(obj == null){
            CacheUtils.put(cache_prefix,curNum);
        }else{
            curNum = Integer.valueOf(obj.toString());
            CacheUtils.put(cache_prefix,curNum+1);
        }
        String seqStr = IdGen.getSeqNo(curNum,SAMPLE_SER_LEN);
        return prefix + seqStr;*/
    }

    /**
     * 生成计划编号
     * @return
     */
    public static String genPlanCode(){
        return "";
    }

    /**
     * 生成任务编号
     * @return
     */
    public static String genTaskCode(String planCode){
        JdbcTemplate jdbcTemplate = SpringContextHolder.getBean("jdbcTemplate");
        List<String> result = jdbcTemplate.queryForList(
                "select * from (SELECT TASK_CODE FROM BS_TEST_TASK WHERE TASK_CODE LIKE '"+planCode+"%' AND length(TASK_CODE)=20 ORDER BY TASK_CODE DESC) WHERE " +
                "ROWNUM = 1",String.class);
        String prefix = DateUtils.getDate("yyyyMMdd");
        String seqStr;
        if(result.isEmpty()){
            seqStr = IdGen.getSeqNo(1,TASK_CODE_LEN);
        }else{
            String maxCode = result.get(0);
            seqStr = IdGen.getSeqNo(Integer.parseInt(maxCode.substring(maxCode.length()-4))+1,TASK_CODE_LEN);
        }
        return planCode + seqStr;
    }

    /**
     * 生成原始记录单编号
     * @return
     */
    public static String genOriginRecordCode(){
        return "";
    }

    /**
     * 临时试验编号(新增)create / 编辑  edit   /  变更  chang
     * @return
     */
    public static String genShortCode(String shortCode,String nature,String status){
        JdbcTemplate jdbcTemplate = SpringContextHolder.getBean("jdbcTemplate");
        String suffix="-00";
        List<String> resultCreate = jdbcTemplate.queryForList(
                "select lab_code from ZY_SHORT_LAB WHERE  nature='"+nature+"' and  lab_code like '%-00' and  del_flag='0'  ORDER BY create_date DESC",String.class);

        String newCode="";
        if(StringUtils.isNotEmpty(shortCode)){
            String[] code=shortCode.split("-");
             newCode=code[0]+"-"+code[1]+"-"+code[2];
        }

        List<String> resultChange = jdbcTemplate.queryForList(
                "select lab_code from ZY_SHORT_LAB WHERE  nature='"+nature+"' and  lab_code like '"+newCode+"'||'%' and  del_flag='0' ORDER BY create_date DESC",String.class);
        List<Map<String,Object>> natureList = jdbcTemplate.queryForList("select  LABEL from sys_dict   where  type='product_model' and value='"+nature+"'");
        String seqStr="0001";
        String  version="00";
        String natrueInfo=natureList.get(0).get("LABEL").toString();
        String prefix ="LSSY-"+natrueInfo+"-";
        if("create".equals(status)){
            if(resultCreate.isEmpty()){
                seqStr = IdGen.getSeqNo(1,4);
                version = IdGen.getSeqNo(0,2);
            }else{
                String maxCode = resultCreate.get(0);
                String[] ss=maxCode.split("-");
                System.out.println(ss[2]);
                seqStr = IdGen.getSeqNo(Integer.parseInt(ss[2])+1,4);

                //version = IdGen.getSeqNo(0,2);
                version=ss[3];

            }
            return prefix+seqStr+"-00";
        }
        /*if("edit".equals(status)){
            if(result.isEmpty()){
                seqStr = IdGen.getSeqNo(1,4);
                version = IdGen.getSeqNo(0,2);
            }else{
                String maxCode = result.get(0);
                String[] ss=maxCode.split("-");
                seqStr = ss[2];
                version =ss[3];
            }
            return prefix+seqStr+"-"+version;
        }*/
        if("change".equals(status)){
            seqStr = IdGen.getSeqNo(1,4);
            String maxCode = resultChange.get(0);
            String[] ss=maxCode.split("-");
            version = IdGen.getSeqNo(Integer.parseInt(ss[3])+1,2);
            return prefix+ss[2]+"-"+version;
        }

          return prefix+seqStr+"-"+version;
    }


    /**
     * 建设项目(新增)create / 编辑  edit   /  变更  chang
     * @return
     */
    public static String getCreateCode(String code,String nature,String status,String labId,String num,String version){
        JdbcTemplate jdbcTemplate = SpringContextHolder.getBean("jdbcTemplate");
        String suffix="-00";
        List<String> resultCreate = jdbcTemplate.queryForList(
                "select  CODE from ABILITY_PROJECT   ap  where  ap.PRODUCT_TYPE='"+nature+"'  and ap.LAB_ID='"+labId+"' and  ap.CODE like  '%'||'"+num+"'|| '%' and ap.DEL_FLAG='0' order by create_date DESC",String.class);

        String seqStr="0001";
        List<Map<String,Object>> natureList = jdbcTemplate.queryForList("select  LABEL from sys_dict   where  type='product_model' and value='"+nature+"'");
        String natrueInfo=natureList.get(0).get("LABEL").toString();
        String prefix =natrueInfo+"-"+version+"-"+num+"-";
        if("create".equals(status)){
            if(resultCreate.isEmpty()){
                seqStr = IdGen.getSeqNo(1,4);
            }else{
                String maxCode = resultCreate.get(0);
                String[] ss=maxCode.split("-");
                System.out.println(ss[3]);
                seqStr = IdGen.getSeqNo(Integer.parseInt(ss[3])+1,4);
            }
        }else{
            String maxCode = resultCreate.get(0);
            String[] ss=maxCode.split("-");
            seqStr=ss[3];
        }

        return  prefix+seqStr;

    }
}
