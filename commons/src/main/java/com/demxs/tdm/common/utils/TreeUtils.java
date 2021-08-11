package com.demxs.tdm.common.utils;

import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.persistence.DataEntity;
import com.google.common.collect.Maps;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author： 谭冬梅
 * @Description：树结构
 * @Date：Create in 2017-07-06 9:45
 * @Modefied By：
 */

public  class TreeUtils < T extends DataEntity<T>>{
    private String  labelName;//节点定义显示值名
    private String  nodeValueName;//节点定义值名
    private boolean showRoot; //是否展示根  true 是   false  否
    private String  rootLabel;//根节点显示值
    private String  rootValue;//根节点值
    private String fuzhujFieldName;//父主键 字段名
    private String labelFieldName; //节点显示值 字段名
    private List<T> dataList; //要转换成树的所有节点数据
    private String  nodeValue; //树级别
    public TreeUtils(List<T> dataList, String nodeValue, boolean showRoot, String rootLabel, String rootValue,  String fuzhujFieldName,  String labelFieldName, String labelName,String nodeValueName){
        this.dataList = dataList;
        this.nodeValue = nodeValue;
        this.showRoot = showRoot;
        this.rootLabel = rootLabel;
        this.rootValue=rootValue;
        this.fuzhujFieldName = fuzhujFieldName;
        this.labelFieldName = labelFieldName;
        this.labelName = labelName;
        this.nodeValueName =  nodeValueName;
    }
    /**
     * @Author：谭冬梅
     * @param：
     * @Description：将所有节点数据转换成树
     * @Date：9:59 2017/7/6
     * @Return：
     * @Exception：
     */
    public  List<Map> formatTree()throws Exception{
        List mapList = new ArrayList();
        if (showRoot)
        {
            Map<String, Object> treeEntityMap = Maps.newHashMap();
            treeEntityMap.put(labelName,rootLabel);
            treeEntityMap.put(nodeValueName,rootValue);
            treeEntityMap.put("children",returnTreeList(rootValue,dataList,fuzhujFieldName,labelFieldName ));
            mapList.add(treeEntityMap);
        }else{
            Object fuzhuj =null;
            Object label =null;
            Field  field = null;
            for (int i=0; i<dataList.size(); i++)
            {
                T treeEntity = dataList.get(i);
                field =treeEntity.getClass().getDeclaredField(fuzhujFieldName);
                field.setAccessible(true);
                fuzhuj = field.get(treeEntity);
                if (fuzhuj.equals(nodeValue)){
                    field = treeEntity.getClass().getDeclaredField(labelFieldName);
                    field.setAccessible(true);
                    label = field.get(treeEntity);
                    Map<String, Object> treeEntityMap = Maps.newHashMap();
                    treeEntityMap.put(labelName,label);
                    treeEntityMap.put(nodeValueName,treeEntity.getId());
                    treeEntityMap.put("children",returnTreeList(treeEntity.getId(),dataList,fuzhujFieldName,labelFieldName ));
                    mapList.add(treeEntityMap);
                }
            }
        }
        return mapList;
    }
    public   List returnTreeList(String nodeValue,List<T> allList,String fuzhujFiledName,String labelFieldName) throws Exception{
        List newList = new ArrayList();
        Object fuzhuj =null;
        Object label =null;
        Field  field = null;
        for (int i = 0; i < allList.size(); i++) {
            T treeEntity =  allList.get(i);
            field =treeEntity.getClass().getDeclaredField(fuzhujFiledName);
            field.setAccessible(true);
            fuzhuj = field.get(treeEntity);
            if (fuzhuj.equals(nodeValue)){
                field =treeEntity.getClass().getDeclaredField("youxiaox");
                field.setAccessible(true);
                String youxiaox = getStringValue(treeEntity,field);
                if(Global.NO.equals(youxiaox)){
                    continue;
                }
                field = treeEntity.getClass().getDeclaredField(labelFieldName);
                field.setAccessible(true);
                label = field.get(treeEntity);
                Map<String, Object> treeEntityMap = Maps.newHashMap();
                treeEntityMap.put(labelName, label);
                treeEntityMap.put(nodeValueName, treeEntity.getId());
                treeEntityMap.put("children", returnTreeList(treeEntity.getId(), allList,fuzhujFiledName,labelFieldName));
                newList.add(treeEntityMap);
            }
        }
        return newList;
    }

    /**
     * 获取String 类型的属性值
     * @param object
     * @param field
     * @return
     * @throws Exception
     */
    public String getStringValue(Object object,Field field) throws Exception {
        String strVal = "";
        // 如果类型是String
        if (field.getGenericType().toString().endsWith("String")) {
            // 获取属性的gettet方法
            Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
            strVal = (String) m.invoke(object);// 调用getter方法获取属性值
        }
        return  strVal;
    }

    // 把一个字符串的第一个字母大写
    private static String getMethodName(String fildeName) throws Exception{
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getNodeValueName() {
        return nodeValueName;
    }

    public void setNodeValueName(String nodeValueName) {
        this.nodeValueName = nodeValueName;
    }

    public boolean isShowRoot() {
        return showRoot;
    }

    public void setShowRoot(boolean showRoot) {
        this.showRoot = showRoot;
    }

    public String getRootLabel() {
        return rootLabel;
    }

    public void setRootLabel(String rootLabel) {
        this.rootLabel = rootLabel;
    }

    public String getRootValue() {
        return rootValue;
    }

    public void setRootValue(String rootValue) {
        this.rootValue = rootValue;
    }

    public String getFuzhujFieldName() {
        return fuzhujFieldName;
    }

    public void setFuzhujFieldName(String fuzhujFieldName) {
        this.fuzhujFieldName = fuzhujFieldName;
    }

    public String getLabelFieldName() {
        return labelFieldName;
    }

    public void setLabelFieldName(String labelFieldName) {
        this.labelFieldName = labelFieldName;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public String getNodeValue() {
        return nodeValue;
    }

    public void setNodeValue(String nodeValue) {
        this.nodeValue = nodeValue;
    }
}
