package com.demxs.tdm.service.bpm.activiti.editor.converter;

import com.demxs.tdm.service.bpm.activiti.editor.constants.ExtStencilConstants;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.Event;
import org.activiti.bpmn.model.EventDefinition;
import org.activiti.editor.language.json.converter.BaseBpmnJsonConverter;
import org.activiti.editor.language.json.converter.EndEventJsonConverter;

import java.util.List;
import java.util.Map;

public class ExtEndEventJsonConverter extends EndEventJsonConverter implements ExtStencilConstants {
    public static void fillTypes(Map<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap, Map<Class<? extends BaseElement>, Class<? extends BaseBpmnJsonConverter>> convertersToJsonMap) {
        fillJsonTypes(convertersToBpmnMap);
        fillBpmnTypes(convertersToJsonMap);
    }

    public static void fillJsonTypes(Map<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap) {
        convertersToBpmnMap.put("EndEvent", ExtEndEventJsonConverter.class);
    }

    public static void fillBpmnTypes(Map<Class<? extends BaseElement>, Class<? extends BaseBpmnJsonConverter>> convertersToJsonMap) {
        convertersToJsonMap.put(EndEvent.class, ExtEndEventJsonConverter.class);
    }

    protected String getStencilId(BaseElement baseElement) {
        EndEvent endEvent = (EndEvent)baseElement;
        List<EventDefinition> eventDefinitions = endEvent.getEventDefinitions();
        if (eventDefinitions.size() != 1)
            return "EndEvent";
        return "EndEvent";
    }

    protected void convertElementToJson(ObjectNode propertiesNode, BaseElement baseElement) {
        EndEvent endEvent = (EndEvent)baseElement;
        addEventProperties((Event)endEvent, propertiesNode);
    }
}
