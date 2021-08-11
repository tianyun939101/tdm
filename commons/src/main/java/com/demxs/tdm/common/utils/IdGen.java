package com.demxs.tdm.common.utils;

import org.activiti.engine.impl.cfg.IdGenerator;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.UUID;

/**
 * 封装各种生成唯一性ID算法的工具类.
 * @author ThinkGem
 * @version 2013-01-15
 */
@Service
@Lazy(false)
public class IdGen implements IdGenerator, SessionIdGenerator {

	private static SecureRandom random = new SecureRandom();
	
	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 使用SecureRandom随机生成Long. 
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}

	/**
	 * 基于Base62编码的SecureRandom随机生成bytes.
	 */
	public static String randomBase62(int length) {
		byte[] randomBytes = new byte[length];
		random.nextBytes(randomBytes);
		return Encodes.encodeBase62(randomBytes);
	}
	
	/**
	 * Activiti ID 生成
	 */
	@Override
	public String getNextId() {
		return IdGen.uuid();
	}

	@Override
	public Serializable generateId(Session session) {
		return IdGen.uuid();
	}

	/**
	 * 根据当前流水号的值和流水号显示的长度。
	 * <pre>
	 * 比如：当前流水号为55 ，显示长度为5那么这个方法返回：00055。
	 * </pre>
	 * @param curValue		当前流水号的值。
	 * @param length		显示的长度。
	 * @return
	 */
	public static String getSeqNo(int curValue,int length){
		return String.format("%0" + length + "d", curValue);
		/*int len=length-tmp.length();
		String rtn="";
		switch (len) {
			case 1:
				rtn= "0";
				break;
			case 2:
				rtn= "00";
				break;
			case 3:
				rtn= "000";
				break;
			case 4:
				rtn= "0000";
				break;
			case 5:
				rtn= "00000";
				break;
			case 6:
				rtn= "000000";
				break;
			case 7:
				rtn= "0000000";
				break;
			case 8:
				rtn= "00000000";
				break;
			case 9:
				rtn= "000000000";
				break;
			case 10:
				rtn= "0000000000";
				break;
			case 11:
				rtn= "00000000000";
				break;
			case 12:
				rtn= "000000000000";
				break;
		}

		return rtn + tmp;*/
	}

	/**
	 * 根据当前流水号的值和流水号显示的长度。
	 * <pre>
	 * 比如：当前流水号为55 ，显示长度为5那么这个方法返回：00055。
	 * </pre>
	 * @param curValue		当前流水号的值。
	 * @param length		显示的长度。
	 * @return
	 */
	public static String getSubSeqNo(String parentValue,int curValue,int length){
		String tmp=curValue +"";
		int len=length-tmp.length();
		String rtn="";
		switch (len) {
			case 1:
				rtn= "0";
				break;
			case 2:
				rtn= "00";
				break;
			case 3:
				rtn= "000";
				break;
			case 4:
				rtn= "0000";
				break;
			case 5:
				rtn= "00000";
				break;
			case 6:
				rtn= "000000";
				break;
			case 7:
				rtn= "0000000";
				break;
			case 8:
				rtn= "00000000";
				break;
			case 9:
				rtn= "000000000";
				break;
			case 10:
				rtn= "0000000000";
				break;
			case 11:
				rtn= "00000000000";
				break;
			case 12:
				rtn= "000000000000";
				break;
		}

		return parentValue + rtn + tmp;
	}
	
	public static void main(String[] args) {
		System.out.println(IdGen.getSeqNo(0,2));
		System.out.println(IdGen.uuid());
		System.out.println(IdGen.uuid().length());
		System.out.println(new IdGen().getNextId());
		/*for (int i=0; i<1000; i++){
			System.out.println(IdGen.randomLong() + "  " + IdGen.randomBase62(5));
		}*/
	}

}
