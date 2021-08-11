package com.demxs.tdm.domain.ability;


import java.io.*;
import java.sql.Connection;
//import web.db.DBConnector;

public class DadaBackup4Oracle {
    static class innerBackupClass implements Runnable {
        private String user; //用户名
        private String password; //密码
        private String url; //链接地址
        private String outFilePath; //输出的文件路径
        private String filename; //文件名
        private String tableNames; //表名

        public innerBackupClass(String user, String password, String url, String outFilePath, String filename, String tableNames) {
            this.user = user;
            this.password = password;
            this.tableNames = tableNames;
            this.url = url;
            this.outFilePath = outFilePath;
            this.filename = filename;
        }
        /**
         * 线程执行入口
         */
        public void run() {
            // Connection con = DBConnector.getconecttion(); // 取得一个数据库连接
            Runtime rt = Runtime.getRuntime();
            Process processexp = null;
            checkCreatDir(outFilePath);

            //这里拼装的是exp导出  当然还可以使用其它的 你懂得
            String exp = "exp " + user + "/" + password + "@" + url + " file="
                    + outFilePath + "/" + filename + ".dmp";
            if (!"".equals(tableNames)) {
                exp += " tables=(" + tableNames + ")";
            }
            try {
                processexp = rt.exec(exp);

                new Thread(new StreamDrainer(processexp.getInputStream())).start();
                new Thread(new StreamDrainer(processexp.getErrorStream())).start();
                processexp.getOutputStream().close();
                int exitValue = processexp.waitFor();
//				databackup.setBackupstate("1"); //1为成功 0为失败
                if (exitValue != 0) {
//					databackup.setBackupstate("0");
                }
                //将备份DMP文件压缩为zip
                //删除dmp文件及所在的文件夹
                //将备份的结果持久化
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                //  DBConnector.freecon(con); // 释放数据库连接
            }
        }

        public void checkCreatDir(String dirPath) { // 目录是否存在
            File file = new File(dirPath);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
    }
    /**
     * 输出控制台信息
     */
    static class StreamDrainer implements Runnable {
        private InputStream ins;

        public StreamDrainer(InputStream ins) {
            this.ins = ins;
        }

        public void run() {
            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(ins));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public  static void main(String[] args){
        innerBackupClass ibc = new innerBackupClass("demxs", "demxs", "192.168.1.188:1521/orcl", "D:/111", "oracledmp", "");
        Thread td = new Thread(ibc);
        td.start();
    }
}
