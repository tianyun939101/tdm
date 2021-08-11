package com.demxs.tdm.service.bpm.activiti.editor.converter;

import com.demxs.tdm.service.bpm.activiti.editor.constants.ExtStencilConstants;
import com.fasterxml.jackson.databind.JsonNode;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.ParallelGateway;
import org.activiti.editor.language.json.converter.BaseBpmnJsonConverter;
import org.activiti.editor.language.json.converter.EventGatewayJsonConverter;

import java.util.Map;

public class ExtAndGatewayJsonConverter extends EventGatewayJsonConverter implements ExtStencilConstants {
    public static void fillTypes(Map<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap, Map<Class<? extends BaseElement>, Class<? extends BaseBpmnJsonConverter>> convertersToJsonMap) {
        fillJsonTypes(convertersToBpmnMap);
        fillBpmnTypes(convertersToJsonMap);
    }

    public static void fillJsonTypes(Map<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap) {
        convertersToBpmnMap.put("ANDGateway", ExtAndGatewayJsonConverter.class);
    }

    public static void fillBpmnTypes(Map<Class<? extends BaseElement>, Class<? extends BaseBpmnJsonConverter>> convertersToJsonMap) {
        convertersToJsonMap.put(ParallelGateway.class, ExtAndGatewayJsonConverter.class);
    }

    protected String getStencilId(BaseElement baseElement) {
        return "ANDGateway";
    }

    protected FlowElement convertJsonToElement(JsonNode elementNode, JsonNode modelNode, Map<String, JsonNode> shapeMap) {
        ParallelGateway gateway = new ParallelGateway();
        return (FlowElement)gateway;
    }
}
