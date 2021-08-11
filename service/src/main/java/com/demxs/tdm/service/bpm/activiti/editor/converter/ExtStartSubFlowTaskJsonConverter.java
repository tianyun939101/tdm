package com.demxs.tdm.service.bpm.activiti.editor.converter;

import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.service.bpm.activiti.editor.constants.ExtStencilConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.model.*;
import org.activiti.editor.language.json.converter.BaseBpmnJsonConverter;
import org.activiti.editor.language.json.converter.BpmnJsonConverterUtil;
import org.activiti.editor.language.json.converter.CallActivityJsonConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExtStartSubFlowTaskJsonConverter extends CallActivityJsonConverter implements ExtStencilConstants {
    public static void fillTypes(Map<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap, Map<Class<? extends BaseElement>, Class<? extends BaseBpmnJsonConverter>> convertersToJsonMap) {
        fillJsonTypes(convertersToBpmnMap);
        fillBpmnTypes(convertersToJsonMap);
    }

    public static void fillJsonTypes(Map<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap) {
        convertersToBpmnMap.put("StartSubFlowTask", ExtStartSubFlowTaskJsonConverter.class);
    }

    public static void fillBpmnTypes(Map<Class<? extends BaseElement>, Class<? extends BaseBpmnJsonConverter>> convertersToJsonMap) {
        convertersToJsonMap.put(UserTask.class, ExtStartSubFlowTaskJsonConverter.class);
    }

    protected String getStencilId(BaseElement baseElement) {
        return "StartSubFlowTask";
    }

    protected void convertElementToJson(ObjectNode propertiesNode, BaseElement baseElement) {
        CallActivity callActivity = (CallActivity)baseElement;
        if (StringUtils.isNotEmpty(callActivity.getCalledElement()))
            propertiesNode.put("callactivitycalledelement", callActivity.getCalledElement());
        addJsonParameters("callactivityinparameters", "inParameters", callActivity.getInParameters(), propertiesNode);
        addJsonParameters("callactivityoutparameters", "outParameters", callActivity.getOutParameters(), propertiesNode);
    }

    private void addJsonParameters(String propertyName, String valueName, List<IOParameter> parameterList, ObjectNode propertiesNode) {
        ObjectNode parametersNode = this.objectMapper.createObjectNode();
        ArrayNode itemsNode = this.objectMapper.createArrayNode();
        for (IOParameter parameter : parameterList) {
            ObjectNode parameterItemNode = this.objectMapper.createObjectNode();
            if (StringUtils.isNotEmpty(parameter.getSource())) {
                parameterItemNode.put("source", parameter.getSource());
            } else {
                parameterItemNode.putNull("source");
            }
            if (StringUtils.isNotEmpty(parameter.getTarget())) {
                parameterItemNode.put("target", parameter.getTarget());
            } else {
                parameterItemNode.putNull("target");
            }
            if (StringUtils.isNotEmpty(parameter.getSourceExpression())) {
                parameterItemNode.put("sourceExpression", parameter.getSourceExpression());
            } else {
                parameterItemNode.putNull("sourceExpression");
            }
            itemsNode.add((JsonNode)parameterItemNode);
        }
        parametersNode.put(valueName, (JsonNode)itemsNode);
        propertiesNode.put(propertyName, (JsonNode)parametersNode);
    }

    protected FlowElement convertJsonToElement(JsonNode elementNode, JsonNode modelNode, Map<String, JsonNode> shapeMap) {
        CallActivity callActivity = new CallActivity();
        if (StringUtils.isNotEmpty(getPropertyValueAsString("callactivitycalledelement", elementNode)))
            callActivity.setCalledElement(getPropertyValueAsString("callactivitycalledelement", elementNode));
        callActivity.getInParameters().addAll(convertToIOParameters("callactivityinparameters", "inParameters", elementNode));
        callActivity.getOutParameters().addAll(convertToIOParameters("callactivityoutparameters", "outParameters", elementNode));
        return (FlowElement)callActivity;
    }

    private List<IOParameter> convertToIOParameters(String propertyName, String valueName, JsonNode elementNode) {
        List<IOParameter> ioParameters = new ArrayList<>();
        JsonNode parametersNode = getProperty(propertyName, elementNode);
        if (parametersNode != null) {
            parametersNode = BpmnJsonConverterUtil.validateIfNodeIsTextual(parametersNode);
            JsonNode itemsArrayNode = parametersNode.get(valueName);
            if (itemsArrayNode != null)
                for (JsonNode itemNode : itemsArrayNode) {
                    JsonNode sourceNode = itemNode.get("source");
                    JsonNode sourceExpressionNode = itemNode.get("sourceExpression");
                    if ((sourceNode != null && StringUtils.isNotEmpty(sourceNode.asText())) || (sourceExpressionNode != null &&
                            StringUtils.isNotEmpty(sourceExpressionNode.asText()))) {
                        IOParameter parameter = new IOParameter();
                        if (StringUtils.isNotEmpty(getValueAsString("source", itemNode))) {
                            parameter.setSource(getValueAsString("source", itemNode));
                        } else if (StringUtils.isNotEmpty(getValueAsString("sourceExpression", itemNode))) {
                            parameter.setSourceExpression(getValueAsString("sourceExpression", itemNode));
                        }
                        if (StringUtils.isNotEmpty(getValueAsString("target", itemNode)))
                            parameter.setTarget(getValueAsString("target", itemNode));
                        ioParameters.add(parameter);
                    }
                }
        }
        return ioParameters;
    }
}
