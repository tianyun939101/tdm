package com.demxs.tdm.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 对象操作工具类, 继承org.apache.commons.lang3.ObjectUtils类
 * @author ThinkGem
 * @version 2014-6-29
 */
public class ObjectUtils extends org.apache.commons.lang3.ObjectUtils {

	/**
	 * 注解到对象复制，只复制能匹配上的方法。
	 * @param annotation
	 * @param object
	 */
	public static void annotationToObject(Object annotation, Object object){
		if (annotation != null){
			Class<?> annotationClass = annotation.getClass();
			if (null == object) {
				return;
			}
			Class<?> objectClass = object.getClass();
			for (Method m : objectClass.getMethods()){
				if (StringUtils.startsWith(m.getName(), "set")){
					try {
						String s = StringUtils.uncapitalize(StringUtils.substring(m.getName(), 3));
						Object obj = annotationClass.getMethod(s).invoke(annotation);
						if (obj != null && !"".equals(obj.toString())){
							m.invoke(object, obj);
						}
					} catch (Exception e) {
						// 忽略所有设置失败方法
					}
				}
			}
		}
	}
	
	/**
	 * 序列化对象
	 * @param object
	 * @return
	 */
	public static byte[] serialize(Object object) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			if (object != null){
				baos = new ByteArrayOutputStream();
				oos = new ObjectOutputStream(baos);
				oos.writeObject(object);
				return baos.toByteArray();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 反序列化对象
	 * @param bytes
	 * @return
	 */
	public static Object unserialize(byte[] bytes) {
		ByteArrayInputStream bais = null;
		try {
			if (bytes != null && bytes.length > 0){
				bais = new ByteArrayInputStream(bytes);
				ObjectInputStream ois = new ObjectInputStream(bais);
				return ois.readObject();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @Author：谭冬梅
	 * @param： * @param yuanObject
	 * @param updateObject
	 * @Description：更新时只更新修改的对象属性
	 * @Date：9:54 2017/5/12
	 * @Return：
	 * @Exception：
	 */
	public static Object returnForUpdateObject(Object yuanObject, Object updateObject)  {

		for (Field field : updateObject.getClass().getDeclaredFields()){
			field.setAccessible(true); // 设置些属性是可以访问的
			//  String type = field.getType().toString();// 得到此属性的类型
			String key = field.getName();// key:得到属性名
			Object value = null;// 得到此属性的值
			try {
				value = field.get(updateObject);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

			if(value!=null){
				//       System.out.println(key+"--key-------value--"+value);
				for (Field yfield : yuanObject.getClass().getDeclaredFields()){
					try {
						yfield.setAccessible(true); // 设置些属性是可以访问的
						if (yfield.getName().equals(key) )
						{
							yfield.set(yuanObject,value);
						}
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return yuanObject;
	}
}
