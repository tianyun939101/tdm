package com.demxs.tdm.common.utils.zrutils;

import com.demxs.tdm.common.utils.CacheUtils;
import com.google.common.collect.Maps;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Map;

/**
 * @Author： 张仁
 * @Description：
 * @Date：Create in 2017-08-15 10:51.
 * @Modefied By：
 */
public class ClientApp {
    static ClientApp ca;

    private static Socket socket;

    private static DataInputStream netIn;

    private static DataOutputStream netOut;

    private static int index = 1;// 消息流水号

    public static String str = null;//判断验证是否成功的变更

    private static String strg = null;//包头中的序列号

    private String retValue = "";

    public static int in = 0;

    static String SPNumber = "13010108976";// 接入号(20字节)

    static String DestUsr = "13023000000";// 接收者,用户号码(30字节)

    static String Service = "888";// 业务代码(12字节)

    static String FeeType = "9";// 资费类别(2字节)

    static String FeeCode = "555555";// 资费代码(8字节)

    static String Msg = "终于等到天晴啦!";// 发送内容(10字节)

    public String ip;

    public int port;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ClientApp(){}

    /**
     * 提供发送短信功能
     * @param DestUsr 接收者
     * @param Msg 发送短信内容
     * @throws
     */
    public void sendSms(String DestUsr, String Msg){
        try {
            byte[] bt = new byte[0];//初始化包体数据
            byte[] a = getBodyBytes(bt, SPNumber, 20);
            byte[] b = getBodyBytes(a, DestUsr, 30);
            byte[] c = getBodyBytes(b, Service, 12);
            byte[] d = getBodyBytes(c, FeeType, 2);
            byte[] e = getBodyBytes(d, FeeCode, 8);
            int t = Msg.getBytes().length;
            byte[] f = getBodyBytes(e, Msg, t);
            byte[] bb = getHeaderBytes(f,5);
            if(netOut!=null){
                netOut.write(bb);// 客户端发送数据
                netOut.flush();
            }
            System.out.println("客户端发送:" + new String(bb, "GBK"));//ISO_8859_1
        } catch (Exception e) {
            System.err.println("连接不到服务器");
            e.printStackTrace();
            System.exit(-1);
        }
    }
    /**
     * 接收返回数据
     *@param k 类型区别,28是身份验证,70是发送短信
     */
    int f=0;
    public synchronized void receiveSms(int k){
        byte buf[]=new byte[k];;
        int read = 0;
        strg = "";
        byte[] by = new byte[4];
        retValue = "";
        try {
            if(netIn!=null){
                read=netIn.read(buf);
                if (read == -1)System.exit(-1);
            }
            for(int i=0;i<buf.length;i++){
                retValue+=byteToString(buf[i])+" ";
                if(i>=8&&i<=11){//包头中的序列号
                    strg = strg+byteToString(buf[i]);
                }
                if(i>=32&&i<=34&&k==78){  //判断验证是否成功的变更
                    by[i-32]=buf[i];
                }
            }
            str = new String(by,"GBK");
            in = Integer.parseInt(strg.trim(),16);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 获得包头字节数据
     * @param b 包体字节数据
     * @param k 类型区别,1是身份验证,5是发送短信
     * @return
     */
    public byte[] getHeaderBytes(byte[] b,int k) {
        int m = b.length + 24;
        byte[] bytes = new byte[m];
        bytes[0] = (byte) (255 & 0XFF);//FF
        bytes[1] = (byte) (238 & 0XFF);//EE
        bytes[2] = (byte) (m >>> 8 & 0XFF);
        bytes[3] = (byte) (m & 0XFF);
        bytes[4] = (byte) (k >>> 24 & 0XFF); // 发送短信请求
        bytes[5] = (byte) (k >>> 16 & 0XFF);
        bytes[6] = (byte) (k >>> 8 & 0XFF);
        bytes[7] = (byte) (k & 0XFF);
        bytes[8] = (byte) (index >>> 24 & 0XFF);
        bytes[9] = (byte) (index >>> 16 & 0XFF);
        bytes[10] = (byte) (index >>> 8 & 0XFF);
        bytes[11] = (byte) (index & 0XFF);
        bytes[12] = (byte) (0 >>> 24 & 0XFF);
        bytes[13] = (byte) (0 >>> 16 & 0XFF);
        bytes[14] = (byte) (0 >>> 8 & 0XFF);
        bytes[15] = (byte) (0 & 0XFF);
        bytes[16] = (byte) (0 >>> 24 & 0XFF);
        bytes[17] = (byte) (0 >>> 16 & 0XFF);
        bytes[18] = (byte) (0 >>> 8 & 0XFF);
        bytes[19] = (byte) (0 & 0XFF);
        bytes[20] = (byte) (0 >>> 24 & 0XFF);
        bytes[21] = (byte) (0 >>> 16 & 0XFF);
        bytes[22] = (byte) (0 >>> 8 & 0XFF);
        bytes[23] = (byte) (0 & 0XFF);
        for (int i = 24; i < m; i++) {
            bytes[i] = b[i - 24];
        }
        index++;
        return bytes;
    }

    /**
     * 获得包体数据
     * @param bt 上一个包体字节数据
     * @param str 包体字符数据
     * @param n 字节数
     * @return
     */
    public byte[] getBodyBytes(byte[] bt, String str, int n) {
        byte[] strBytes = str.getBytes();
        int H = bt.length + n;
        byte[] bytes = new byte[H];
        int c = bt.length;
        int b = c + strBytes.length;
        if (strBytes.length <= n) {
            for (int i = 0; i < bt.length; i++) {
                bytes[i] = bt[i];
            }
            for (int i = 0; i < strBytes.length; i++) {
                bytes[c] = strBytes[i];
                c++;
            }
            for (int i = strBytes.length; i < n; i++) {
                bytes[b] = (byte) (32 & 0XFF);// 补空格
                b++;
            }
        }
        return bytes;
    }

    /**
     * 字节转换成字符
     * @param b
     * @return
     */
    public static String byteToString(byte b) {
        byte high, low;
        byte maskHigh = (byte) 0xf0;
        byte maskLow = 0x0f;
        high = (byte) ((b & maskHigh) >> 4);
        low = (byte) (b & maskLow);
        StringBuffer buf = new StringBuffer();
        buf.append(findHex(high));
        buf.append(findHex(low));
        return buf.toString();
    }
    private static char findHex(byte b) {
        int t = new Byte(b).intValue();
        t = t < 0 ? t + 16 : t;
        if ((0 <= t) && (t <= 9)) {
            return (char) (t + '0');
        }
        return (char) (t - 10 + 'A');
    }

    /**
     * 连接socket
     * @param ip
     * @param port
     * @return
     */
    public Socket getSocket(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            socket.setSoTimeout(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return socket;
    }

    /**
     * 连接socket(全局容器，缓存)
     * @param ip
     * @param port
     * @return
     */
    Map<String, Socket> socketMap = Maps.newHashMap();
    public Socket getSocketFromPool(String ip, int port) {
        try {
            Map<String, Socket> cacheSocketMap = (Map<String, Socket>)CacheUtils.get("socketCache", "socket");
            if (cacheSocketMap!=null && cacheSocketMap.get(ip.concat("-").concat(String.valueOf(port)))!=null){//缓存中有，直接取
                socket = cacheSocketMap.get(ip.concat("-").concat(String.valueOf(port)));
            } else {//缓存中没有，重新创建，并将其放入缓存
                socket = new Socket(ip, port);
                socket.setSoTimeout(0);
                socketMap.put(ip.concat("-").concat(String.valueOf(port)), socket);
                CacheUtils.put("socketCache", "socket", socketMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return socket;
    }

    /**
     * 获得输入输出流
     *
     */
    public void getStreamBySocket() {
        try {
            netOut = new DataOutputStream(socket.getOutputStream());
            netIn = new DataInputStream(socket.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭socket和io流
     *
     */
    public void close() {
        try {
            netIn.close();
            netOut.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] getWenduOrder(){
        byte[] b = new byte[12];
        b[0] = (byte) 0x00;
        b[1] = (byte) 0x00;
        b[2] = (byte) 0x00;
        b[3] = (byte) 0x00;
        b[4] = (byte) 0x00;
        b[5] = (byte) 0x00;
        b[6] = (byte) 0x01;
        b[7] = (byte) 0x03;
        b[8] = (byte) 0x00;
        b[9] = (byte) 0x00;
        b[10] = (byte) 0x00;
        b[11] = (byte) 0x02;
        return b;
    }

    public String getWendu(String v){
        String[] datas = v.split(" ");
        if (datas.length==13){
            String d = datas[9]+datas[10];
            BigInteger srch = new BigInteger(d, 16);
            Double dd = Double.parseDouble(srch.toString())/10;
            return dd.toString();
        }
        return "";
    }

    public String getShidu(String v){
        String[] datas = v.split(" ");
        if (datas.length==13){
            String d = datas[11]+datas[12];
            BigInteger srch = new BigInteger(d, 16);
            Double dd = Double.parseDouble(srch.toString())/10;
            return dd.toString();
        }
        return "";
    }

    public static void main(String[] args) throws IOException {
		ClientApp ca = new ClientApp();
		ca.setIp("192.168.0.108");
		ca.setPort(502);
		ca.getSocket(ca.getIp(), ca.getPort());
		ca.getStreamBySocket();
		byte[] b = ca.getWenduOrder();
		netOut.write(b);// 客户端发送数据
		netOut.flush();
		ca.receiveSms(13);
		ca.close();
		//System.out.print(ca.retValue);

		//ClientApp ca = new ClientApp();
		System.out.println(ca.getWendu(ca.retValue));
        System.out.println(ca.getShidu(ca.retValue));
    }

    /**
     * 获取数据
     * @param ip
     * @param port
     * @return
     * @throws IOException
     */
    public static Map<String, String> getData(String ip, int port) throws IOException {
        ClientApp ca = new ClientApp();
        ca.setIp(ip);
        ca.setPort(port);
        ca.getSocketFromPool(ca.getIp(), ca.getPort());
        ca.getStreamBySocket();
        byte[] b = ca.getWenduOrder();
        netOut.write(b);// 客户端发送数据
        netOut.flush();
        ca.receiveSms(13);
        ca.close();
        //System.out.print(ca.retValue);

        //ClientApp ca = new ClientApp();
        System.out.println(ca.getWendu(ca.retValue));
        System.out.println(ca.getShidu(ca.retValue));
        Map<String, String> map = Maps.newHashMap();
        map.put("wendu", ca.getWendu(ca.retValue));
        map.put("shidu", ca.getShidu(ca.retValue));
        return map;
    }
}
