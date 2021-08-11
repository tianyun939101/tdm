package com.demxs.tdm.service.bpm.activiti.editor.converter;

import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.service.bpm.activiti.editor.constants.ExtStencilConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.model.*;
import org.activiti.editor.language.json.converter.BaseBpmnJsonConverter;
import org.activiti.editor.language.json.converter.ServiceTaskJsonConverter;

import java.util.Map;

public class ExtMessageTaskJsonConverter extends ServiceTaskJsonConverter implements ExtStencilConstants {
    public static void fillTypes(Map<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap, Map<Class<? extends BaseElement>, Class<? extends BaseBpmnJsonConverter>> convertersToJsonMap) {
        fillJsonTypes(convertersToBpmnMap);
        fillBpmnTypes(convertersToJsonMap);
    }

    public static void fillJsonTypes(Map<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap) {
        convertersToBpmnMap.put("MessageTask", ExtMessageTaskJsonConverter.class);
    }

    public static void fillBpmnTypes(Map<Class<? extends BaseElement>, Class<? extends BaseBpmnJsonConverter>> convertersToJsonMap) {
        convertersToJsonMap.put(ServiceTask.class, ExtMessageTaskJsonConverter.class);
    }

    protected String getStencilId(BaseElement baseElement) {
        return "MessageTask";
    }

    protected void convertElementToJson(ObjectNode propertiesNode, BaseElement baseElement) {
        ServiceTask serviceTask = (ServiceTask)baseElement;
        if ("mail".equalsIgnoreCase(serviceTask.getType())) {
            setPropertyFieldValue("mailtaskto", serviceTask, propertiesNode);
            setPropertyFieldValue("mailtaskfrom", serviceTask, propertiesNode);
            setPropertyFieldValue("mailtasksubject", serviceTask, propertiesNode);
            setPropertyFieldValue("mailtaskcc", serviceTask, propertiesNode);
            setPropertyFieldValue("mailtaskbcc", serviceTask, propertiesNode);
            setPropertyFieldValue("mailtasktext", serviceTask, propertiesNode);
            setPropertyFieldValue("mailtaskhtml", serviceTask, propertiesNode);
            setPropertyFieldValue("mailtaskcharset", serviceTask, propertiesNode);
        } else if ("camel".equalsIgnoreCase(serviceTask.getType())) {
            setPropertyFieldValue("cameltaskcamelcontext", "camelContext", serviceTask, propertiesNode);
        } else if ("mule".equalsIgnoreCase(serviceTask.getType())) {
            setPropertyFieldValue("muletaskendpointurl", "endpointUrl", serviceTask, propertiesNode);
            setPropertyFieldValue("muletasklanguage", "language", serviceTask, propertiesNode);
            setPropertyFieldValue("muletaskpayloadexpression", "payloadExpression", serviceTask, propertiesNode);
            setPropertyFieldValue("muletaskresultvariable", "resultVariable", serviceTask, propertiesNode);
        } else {
            if (ImplementationType.IMPLEMENTATION_TYPE_CLASS.equals(serviceTask.getImplementationType())) {
                propertiesNode.put("servicetaskclass", serviceTask.getImplementation());
            } else if (ImplementationType.IMPLEMENTATION_TYPE_EXPRESSION.equals(serviceTask.getImplementationType())) {
                propertiesNode.put("servicetaskexpression", serviceTask.getImplementation());
            } else if (ImplementationType.IMPLEMENTATION_TYPE_DELEGATEEXPRESSION.equals(serviceTask.getImplementationType())) {
                propertiesNode.put("messagetaskdelegateexpression", serviceTask.getImplementation());
            }
            if (StringUtils.isNotEmpty(serviceTask.getResultVariableName()))
                propertiesNode.put("servicetaskresultvariable", serviceTask.getResultVariableName());
            addFieldExtensions(serviceTask.getFieldExtensions(), propertiesNode);
        }
    }

    protected FlowElement convertJsonToElement(JsonNode elementNode, JsonNode modelNode, Map<String, JsonNode> shapeMap) {
        ServiceTask task = new ServiceTask();
        if (StringUtils.isNotEmpty(getPropertyValueAsString("servicetaskclass", elementNode))) {
            task.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_CLASS);
            task.setImplementation(getPropertyValueAsString("servicetaskclass", elementNode));
        } else if (StringUtils.isNotEmpty(getPropertyValueAsString("servicetaskexpression", elementNode))) {
            task.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_EXPRESSION);
            task.setImplementation(getPropertyValueAsString("servicetaskexpression", elementNode));
        } else if (StringUtils.isNotEmpty(getPropertyValueAsString("messagetaskdelegateexpression", elementNode))) {
            task.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_DELEGATEEXPRESSION);
            task.setImplementation(getPropertyValueAsString("messagetaskdelegateexpression", elementNode));
        }
        if (StringUtils.isNotEmpty(getPropertyValueAsString("servicetaskresultvariable", elementNode)))
            task.setResultVariableName(getPropertyValueAsString("servicetaskresultvariable", elementNode));
        JsonNode fieldsNode = getProperty("servicetaskfields", elementNode);
        if (fieldsNode != null) {
            JsonNode itemsArrayNode = fieldsNode.get("fields");
            if (itemsArrayNode != null)
                for (JsonNode itemNode : itemsArrayNode) {
                    JsonNode nameNode = itemNode.get("name");
                    if (nameNode != null && StringUtils.isNotEmpty(nameNode.asText())) {
                        FieldExtension field = new FieldExtension();
                        field.setFieldName(nameNode.asText());
                        if (StringUtils.isNotEmpty(getValueAsString("stringValue", itemNode))) {
                            field.setStringValue(getValueAsString("stringValue", itemNode));
                        } else if (StringUtils.isNotEmpty(getValueAsString("string", itemNode))) {
                            field.setStringValue(getValueAsString("string", itemNode));
                        } else if (StringUtils.isNotEmpty(getValueAsString("expression", itemNode))) {
                            field.setExpression(getValueAsString("expression", itemNode));
                        }
                        task.getFieldExtensions().add(field);
                    }
                }
        }
        return (FlowElement)task;
    }

    protected void setPropertyFieldValue(String name, ServiceTask task, ObjectNode propertiesNode) {
        for (FieldExtension extension : task.getFieldExtensions()) {
            if (name.substring(8).equalsIgnoreCase(extension.getFieldName())) {
                if (StringUtils.isNotEmpty(extension.getStringValue())) {
                    setPropertyValue(name, extension.getStringValue(), propertiesNode);
                    continue;
                }
                if (StringUtils.isNotEmpty(extension.getExpression()))
                    setPropertyValue(name, extension.getExpression(), propertiesNode);
            }
        }
    }

    protected void setPropertyFieldValue(String propertyName, String fieldName, ServiceTask task, ObjectNode propertiesNode) {
        for (FieldExtension extension : task.getFieldExtensions()) {
            if (fieldName.equalsIgnoreCase(extension.getFieldName())) {
                if (StringUtils.isNotEmpty(extension.getStringValue())) {
                    setPropertyValue(propertyName, extension.getStringValue(), propertiesNode);
                    continue;
                }
                if (StringUtils.isNotEmpty(extension.getExpression()))
                    setPropertyValue(propertyName, extension.getExpression(), propertiesNode);
            }
        }
    }
}