package com.demxs.tdm.service.bpm.activiti.editor.converter;

import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.service.bpm.activiti.editor.constants.ExtStencilConstants;
import com.fasterxml.jackson.databind.JsonNode;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.editor.language.json.converter.BaseBpmnJsonConverter;
import org.activiti.editor.language.json.converter.BpmnJsonConverterUtil;
import org.activiti.editor.language.json.converter.StartEventJsonConverter;

import java.util.Map;

public class ExtStartEventJsonConverter extends StartEventJsonConverter implements ExtStencilConstants {
    public static void fillTypes(Map<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap, Map<Class<? extends BaseElement>, Class<? extends BaseBpmnJsonConverter>> convertersToJsonMap) {
        fillJsonTypes(convertersToBpmnMap);
        fillBpmnTypes(convertersToJsonMap);
    }

    public static void fillJsonTypes(Map<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap) {
        convertersToBpmnMap.put("StartEvent", ExtStartEventJsonConverter.class);
    }

    public static void fillBpmnTypes(Map<Class<? extends BaseElement>, Class<? extends BaseBpmnJsonConverter>> convertersToJsonMap) {
        convertersToJsonMap.put(StartEvent.class, ExtStartEventJsonConverter.class);
    }

    protected String getStencilId(BaseElement baseElement) {
        return "StartEvent";
    }

    protected FlowElement convertJsonToElement(JsonNode elementNode, JsonNode modelNode, Map<String, JsonNode> shapeMap) {
        StartEvent startEvent = new StartEvent();
        startEvent.setInitiator(getPropertyValueAsString("initiator", elementNode));
        String stencilId = BpmnJsonConverterUtil.getStencilId(elementNode);
        if ("StartEvent".equals(stencilId)) {
            String formKey = getPropertyValueAsString("formkeydefinition", elementNode);
            if (StringUtils.isNotEmpty(formKey))
                startEvent.setFormKey(formKey);
            convertJsonToFormProperties(elementNode, (BaseElement)startEvent);
        }
        return (FlowElement)startEvent;
    }
}