package com.demxs.tdm.common.sys.utils;

import com.demxs.tdm.common.mapper.JsonMapper;
import com.demxs.tdm.common.sys.dao.DictDao;
import com.demxs.tdm.common.sys.entity.Dict;
import com.demxs.tdm.common.utils.CacheUtils;
import com.demxs.tdm.common.utils.Collections3;
import com.demxs.tdm.common.utils.SpringContextHolder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 字典工具类
 * @author ThinkGem
 * @version 2013-5-29
 */
public class DictUtils {
	
	private static DictDao dictDao = SpringContextHolder.getBean(DictDao.class);

	public static final String CACHE_DICT_MAP = "dictMap";
	
	public static String getDictLabel(String value, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)){
			for (Dict dict : getDictList(type)){
				if (type.equals(dict.getType()) && value.equals(dict.getValue())){
					return dict.getLabel();
				}
			}
		}
		return defaultValue;
	}

	public static String getDictLabel(String value, String type){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)){
			for (Dict dict : getDictList(type)){
				if (type.equals(dict.getType()) && value.equals(dict.getValue())){
					return dict.getLabel();
				}
			}
		}
		return "";
	}
	
	public static String getDictLabels(String values, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(values)){
			List<String> valueList = Lists.newArrayList();
			for (String value : StringUtils.split(values, ",")){
				valueList.add(getDictLabel(value, type, defaultValue));
			}
			return StringUtils.join(valueList, ",");
		}
		return defaultValue;
	}

	public static String getDictValue(String label, String type, String defaultLabel){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(label)){
			for (Dict dict : getDictList(type)){
				if (type.equals(dict.getType()) && label.equals(dict.getLabel())){
					return dict.getValue();
				}
			}
		}
		return defaultLabel;
	}
	
	public static List<Dict> getDictList(String type){
		@SuppressWarnings("unchecked")
		Map<String, List<Dict>> dictMap = (Map<String, List<Dict>>) CacheUtils.get(CACHE_DICT_MAP);
		if (dictMap==null){
			dictMap = Maps.newHashMap();
			for (Dict dict : dictDao.findAllList(new Dict())){
				List<Dict> dictList = dictMap.get(dict.getType());
				if (dictList != null){
					dictList.add(dict);
				}else{
					dictMap.put(dict.getType(), Lists.newArrayList(dict));
				}
			}
			CacheUtils.put(CACHE_DICT_MAP, dictMap);
		}
		List<Dict> dictList1 = dictMap.get("entrust_nature");
		List<Dict> dictList = dictMap.get(type);
		if (dictList == null){
			dictList = Lists.newArrayList();
		}
		return dictList;
	}

	public static List<Dict> getDictList(String type,Boolean flag){
		@SuppressWarnings("unchecked")
		Map<String, List<Dict>> dictMap = (Map<String, List<Dict>>) CacheUtils.get(CACHE_DICT_MAP);
		if (dictMap==null){
			dictMap = Maps.newHashMap();
			for (Dict dict : dictDao.findAllList(new Dict())){
				List<Dict> dictList = dictMap.get(dict.getType());
				if (dictList != null){
					dictList.add(dict);
				}else{
					dictMap.put(dict.getType(), Lists.newArrayList(dict));
				}
			}
			CacheUtils.put(CACHE_DICT_MAP, dictMap);
		}
		List<Dict> dictList = dictMap.get(type);



		if (dictList == null){
			dictList = Lists.newArrayList();
		}

		List<Dict> copyDictList = Collections3.deepCopyList(dictList);
		if(flag) {
			for (Dict dict : copyDictList) {
				dict.setLabel(dict.getEnlabel());
			}
		}
		return copyDictList;
	}
	
	/**
	 * 返回字典列表（JSON）
	 * @param type
	 * @return
	 */
	public static String getDictListJson(String type){
		return JsonMapper.toJsonString(getDictList(type));
	}


	public static Map<String, List<Dict>> getDicts(){
		@SuppressWarnings("unchecked")
		Map<String, List<Dict>> dictMap = (Map<String, List<Dict>>) CacheUtils.get(CACHE_DICT_MAP);
		if (dictMap==null){
			dictMap = Maps.newHashMap();
			for (Dict dict : dictDao.findAllList(new Dict())){
				List<Dict> dictList = dictMap.get(dict.getType());
				if (dictList != null){
					dictList.add(dict);
				}else{
					dictMap.put(dict.getType(), Lists.newArrayList(dict));
				}
			}
			CacheUtils.put(CACHE_DICT_MAP, dictMap);
		}
		return dictMap;
	}
	
}
