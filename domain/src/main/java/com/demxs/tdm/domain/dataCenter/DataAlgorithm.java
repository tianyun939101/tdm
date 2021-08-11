package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.lab.LabInfo;

import java.util.List;

/**
 * 算法实体类
 */
public class DataAlgorithm extends DataEntity<DataAlgorithm>{

    private static final long serialVersionUID = 1L;

    private String algorithmName;//算法名称

    private String algorithmType;//算法类型

    private String algorithmLibrary;//算法库

    private String className;//类名

    private String methodName;//方法名

    private AlgorithmType type;

    private String labId;//试验室id

    private LabInfo labInfo;//试验室

    private String sampleData;//样例数据

    private List<DataAlgorithmParameter> parameterList;//入参集合

    private List<DataAlgorithmReturn> returnList;//返回值集合

    public DataAlgorithm() {
        super();
    }

    public DataAlgorithm(String id){
        super(id);
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public String getAlgorithmType() {
        return algorithmType;
    }

    public void setAlgorithmType(String algorithmType) {
        this.algorithmType = algorithmType;
    }

    public String getAlgorithmLibrary() {
        return algorithmLibrary;
    }

    public void setAlgorithmLibrary(String algorithmLibrary) {
        this.algorithmLibrary = algorithmLibrary;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List<DataAlgorithmParameter> getParameterList() {
        return parameterList;
    }

    public void setParameterList(List<DataAlgorithmParameter> parameterList) {
        this.parameterList = parameterList;
    }

    public List<DataAlgorithmReturn> getReturnList() {
        return returnList;
    }

    public void setReturnList(List<DataAlgorithmReturn> returnList) {
        this.returnList = returnList;
    }

    public AlgorithmType getType() {
        return type;
    }

    public void setType(AlgorithmType type) {
        this.type = type;
    }

    public LabInfo getLabInfo() {
        return labInfo;
    }

    public void setLabInfo(LabInfo labInfo) {
        this.labInfo = labInfo;
    }

    public String getSampleData() {
        return sampleData;
    }

    public void setSampleData(String sampleData) {
        this.sampleData = sampleData;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }
}