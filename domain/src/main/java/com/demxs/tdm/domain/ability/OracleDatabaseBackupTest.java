package com.demxs.tdm.domain.ability;
import com.demxs.tdm.common.config.Global;

import java.io.*;

public class OracleDatabaseBackupTest {
    /**
     * Java代码实现Oracle数据库导出
     *
     * @author GaoHuanjie
     * @param userName 进入数据库所需要的用户名
     * @param password 进入数据库所需要的密码
     * @param SID 用户所在的SID
     * @param savePath 数据库导出文件保存路径
     * @param fileName 数据库导出文件文件名
     * @return 返回true表示导出成功，否则返回false。
     */


    public static boolean exportDatabaseTool(String userName, String password, String SID, String savePath, String fileName) throws InterruptedException {
        File saveFile = new File(savePath);
        if (!saveFile.exists()) {// 如果目录不存在
            saveFile.mkdirs();// 创建文件夹
        }
        try {
            String exp = "exp " + userName + "/" + password + "@" + SID + " file="
                    + savePath + "/" + fileName + ".dmp";

            System.out.println("----"+exp);

            Process process = Runtime.getRuntime().exec(exp);

            if(process.waitFor() == 0){//0 表示线程正常终止。
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) throws InterruptedException {

        String  savePath="D:/111";
        String SID="192.168.1.188:1521/ORCL";
        if (exportDatabaseTool("demxs", "demxs", SID, savePath, "oracledb")) {
            System.out.println("数据库成功备份！！！");
        } else {
            System.out.println("数据库备份失败！！！");
        }
    }
}

