package com.demxs.tdm.service.bpm.activiti.editor.converter;

import com.demxs.tdm.service.bpm.activiti.editor.constants.ExtStencilConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.ExtensionElement;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.UserTask;
import org.activiti.editor.language.json.converter.BaseBpmnJsonConverter;
import org.activiti.editor.language.json.converter.UserTaskJsonConverter;
import org.activiti.editor.language.json.converter.util.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class SignTaskJsonConverter extends UserTaskJsonConverter implements ExtStencilConstants {
    public static void fillTypes(Map<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap, Map<Class<? extends BaseElement>, Class<? extends BaseBpmnJsonConverter>> convertersToJsonMap) {
        fillJsonTypes(convertersToBpmnMap);
        fillBpmnTypes(convertersToJsonMap);
    }

    public static void fillJsonTypes(Map<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap) {
        convertersToBpmnMap.put("SignTask", SignTaskJsonConverter.class);
    }

    public static void fillBpmnTypes(Map<Class<? extends BaseElement>, Class<? extends BaseBpmnJsonConverter>> convertersToJsonMap) {
        convertersToJsonMap.put(UserTask.class, SignTaskJsonConverter.class);
    }

    protected String getStencilId(BaseElement baseElement) {
        return "SignTask";
    }

    protected void convertElementToJson(ObjectNode propertiesNode, BaseElement baseElement) {
        UserTask userTask = (UserTask)baseElement;
        String assignee = userTask.getAssignee();
        if (StringUtils.isNotEmpty(assignee) || CollectionUtils.isNotEmpty(userTask.getCandidateUsers()) || CollectionUtils.isNotEmpty(userTask.getCandidateGroups())) {
            ObjectNode assignmentNode = this.objectMapper.createObjectNode();
            ObjectNode assignmentValuesNode = this.objectMapper.createObjectNode();
            List<ExtensionElement> idmAssigneeList = (List<ExtensionElement>)userTask.getExtensionElements().get("activiti-idm-assignee");
            List<ExtensionElement> idmAssigneeFieldList = (List<ExtensionElement>)userTask.getExtensionElements().get("activiti-idm-assignee-field");
            if (CollectionUtils.isNotEmpty(idmAssigneeList) || CollectionUtils.isNotEmpty(idmAssigneeFieldList) ||
                    CollectionUtils.isNotEmpty((Collection)userTask.getExtensionElements().get("activiti-idm-candidate-user")) ||
                    CollectionUtils.isNotEmpty((Collection)userTask.getExtensionElements().get("activiti-idm-candidate-group"))) {
                assignmentValuesNode.put("type", "idm");
                ObjectNode idmNode = this.objectMapper.createObjectNode();
                assignmentValuesNode.set("idm", (JsonNode)idmNode);
                List<ExtensionElement> canCompleteList = (List<ExtensionElement>)userTask.getExtensionElements().get("initiator-can-complete");
                if (CollectionUtils.isNotEmpty(canCompleteList))
                    assignmentValuesNode.put("initiatorCanCompleteTask", Boolean.valueOf(((ExtensionElement)canCompleteList.get(0)).getElementText()));
                if (StringUtils.isNotEmpty(userTask.getAssignee())) {
                    ObjectNode assigneeNode = this.objectMapper.createObjectNode();
                    if (userTask.getAssignee().contains("${taskAssignmentBean.assignTaskToAssignee(")) {
                        idmNode.set("assigneeField", (JsonNode)assigneeNode);
                        idmNode.put("type", "user");
                        fillProperty("id", "activiti-idm-assignee-field", assigneeNode, userTask);
                        fillProperty("name", "assignee-field-info-name", assigneeNode, userTask);
                    } else {
                        assigneeNode.put("id", userTask.getAssignee());
                        idmNode.set("assignee", (JsonNode)assigneeNode);
                        idmNode.put("type", "user");
                        fillProperty("externalId", "assignee-info-externalid", assigneeNode, userTask);
                        fillProperty("email", "assignee-info-email", assigneeNode, userTask);
                        fillProperty("firstName", "assignee-info-firstname", assigneeNode, userTask);
                        fillProperty("lastName", "assignee-info-lastname", assigneeNode, userTask);
                    }
                }
                List<ExtensionElement> idmCandidateUserList = (List<ExtensionElement>)userTask.getExtensionElements().get("activiti-idm-candidate-user");
                if (CollectionUtils.isNotEmpty(userTask.getCandidateUsers()) && CollectionUtils.isNotEmpty(idmCandidateUserList)) {
                    List<String> candidateUserIds = new ArrayList<>();
                    if (userTask.getCandidateUsers().size() == 1 && ((String)userTask.getCandidateUsers().get(0)).contains("${taskAssignmentBean.assignTaskToCandidateUsers(")) {
                        idmNode.put("type", "users");
                        String candidateUsersString = userTask.getCandidateUsers().get(0);
                        candidateUsersString = candidateUsersString.replace("${taskAssignmentBean.assignTaskToCandidateUsers('", "");
                        candidateUsersString = candidateUsersString.replace("', execution)}", "");
                        List<String> candidateFieldIds = new ArrayList<>();
                        String[] candidateUserArray = candidateUsersString.split(",");
                        for (String candidate : candidateUserArray) {
                            if (candidate.contains("field(")) {
                                candidateFieldIds.add(candidate.trim().substring(6, candidate.length() - 1));
                            } else {
                                candidateUserIds.add(candidate.trim());
                            }
                        }
                        if (candidateFieldIds.size() > 0) {
                            ArrayNode candidateUserFieldsNode = this.objectMapper.createArrayNode();
                            idmNode.set("candidateUserFields", (JsonNode)candidateUserFieldsNode);
                            for (String fieldId : candidateFieldIds) {
                                ObjectNode fieldNode = this.objectMapper.createObjectNode();
                                fieldNode.put("id", fieldId);
                                candidateUserFieldsNode.add((JsonNode)fieldNode);
                                fillProperty("name", "user-field-info-name-" + fieldId, fieldNode, userTask);
                            }
                        }
                    } else {
                        for (String candidateUser : userTask.getCandidateUsers())
                            candidateUserIds.add(candidateUser);
                    }
                    if (candidateUserIds.size() > 0) {
                        ArrayNode candidateUsersNode = this.objectMapper.createArrayNode();
                        idmNode.set("candidateUsers", (JsonNode)candidateUsersNode);
                        idmNode.put("type", "users");
                        for (String candidateUser : candidateUserIds) {
                            ObjectNode candidateUserNode = this.objectMapper.createObjectNode();
                            candidateUserNode.put("id", candidateUser);
                            candidateUsersNode.add((JsonNode)candidateUserNode);
                            fillProperty("externalId", "user-info-externalid-" + candidateUser, candidateUserNode, userTask);
                            fillProperty("email", "user-info-email-" + candidateUser, candidateUserNode, userTask);
                            fillProperty("firstName", "user-info-firstname-" + candidateUser, candidateUserNode, userTask);
                            fillProperty("lastName", "user-info-lastname-" + candidateUser, candidateUserNode, userTask);
                        }
                    }
                }
                List<ExtensionElement> idmCandidateGroupList = (List<ExtensionElement>)userTask.getExtensionElements().get("activiti-idm-candidate-group");
                if (CollectionUtils.isNotEmpty(userTask.getCandidateGroups()) && CollectionUtils.isNotEmpty(idmCandidateGroupList)) {
                    List<String> candidateGroupIds = new ArrayList<>();
                    if (userTask.getCandidateGroups().size() == 1 && ((String)userTask.getCandidateGroups().get(0)).contains("${taskAssignmentBean.assignTaskToCandidateGroups(")) {
                        idmNode.put("type", "groups");
                        String candidateGroupsString = userTask.getCandidateGroups().get(0);
                        candidateGroupsString = candidateGroupsString.replace("${taskAssignmentBean.assignTaskToCandidateGroups('", "");
                        candidateGroupsString = candidateGroupsString.replace("', execution)}", "");
                        List<String> candidateFieldIds = new ArrayList<>();
                        String[] candidateGroupArray = candidateGroupsString.split(",");
                        for (String candidate : candidateGroupArray) {
                            if (candidate.contains("field(")) {
                                candidateFieldIds.add(candidate.trim().substring(6, candidate.length() - 1));
                            } else {
                                candidateGroupIds.add(candidate.trim());
                            }
                        }
                        if (candidateFieldIds.size() > 0) {
                            ArrayNode candidateGroupFieldsNode = this.objectMapper.createArrayNode();
                            idmNode.set("candidateGroupFields", (JsonNode)candidateGroupFieldsNode);
                            for (String fieldId : candidateFieldIds) {
                                ObjectNode fieldNode = this.objectMapper.createObjectNode();
                                fieldNode.put("id", fieldId);
                                candidateGroupFieldsNode.add((JsonNode)fieldNode);
                                fillProperty("name", "group-field-info-name-" + fieldId, fieldNode, userTask);
                            }
                        }
                    } else {
                        for (String candidateGroup : userTask.getCandidateGroups())
                            candidateGroupIds.add(candidateGroup);
                    }
                    if (candidateGroupIds.size() > 0) {
                        ArrayNode candidateGroupsNode = this.objectMapper.createArrayNode();
                        idmNode.set("candidateGroups", (JsonNode)candidateGroupsNode);
                        idmNode.put("type", "groups");
                        for (String candidateGroup : candidateGroupIds) {
                            ObjectNode candidateGroupNode = this.objectMapper.createObjectNode();
                            candidateGroupNode.put("id", candidateGroup);
                            candidateGroupsNode.add((JsonNode)candidateGroupNode);
                            fillProperty("externalId", "group-info-externalid-" + candidateGroup, candidateGroupNode, userTask);
                            fillProperty("name", "group-info-name-" + candidateGroup, candidateGroupNode, userTask);
                        }
                    }
                }
            } else {
                assignmentValuesNode.put("type", "static");
                if (StringUtils.isNotEmpty(assignee))
                    assignmentValuesNode.put("assignee", assignee);
                if (CollectionUtils.isNotEmpty(userTask.getCandidateUsers())) {
                    ArrayNode candidateArrayNode = this.objectMapper.createArrayNode();
                    for (String candidateUser : userTask.getCandidateUsers()) {
                        ObjectNode candidateNode = this.objectMapper.createObjectNode();
                        candidateNode.put("value", candidateUser);
                        candidateArrayNode.add((JsonNode)candidateNode);
                    }
                    assignmentValuesNode.set("candidateUsers", (JsonNode)candidateArrayNode);
                }
                if (CollectionUtils.isNotEmpty(userTask.getCandidateGroups())) {
                    ArrayNode candidateArrayNode = this.objectMapper.createArrayNode();
                    for (String candidateGroup : userTask.getCandidateGroups()) {
                        ObjectNode candidateNode = this.objectMapper.createObjectNode();
                        candidateNode.put("value", candidateGroup);
                        candidateArrayNode.add((JsonNode)candidateNode);
                    }
                    assignmentValuesNode.set("candidateGroups", (JsonNode)candidateArrayNode);
                }
            }
            assignmentNode.set("assignment", (JsonNode)assignmentValuesNode);
            propertiesNode.set("usertaskassignment", (JsonNode)assignmentNode);
        }
        if (userTask.getPriority() != null)
            setPropertyValue("prioritydefinition", userTask.getPriority().toString(), propertiesNode);
        if (userTask.getPriority() != null)
            setPropertyValue("prioritydefinition", userTask.getPriority().toString(), propertiesNode);
        setPropertyValue("duedatedefinition", userTask.getDueDate(), propertiesNode);
        setPropertyValue("categorydefinition", userTask.getCategory(), propertiesNode);
        addFormProperties(userTask.getFormProperties(), propertiesNode);
    }

    protected int getExtensionElementValueAsInt(String name, UserTask userTask) {
        int intValue = 0;
        String value = getExtensionElementValue(name, userTask);
        if (value != null && NumberUtils.isNumber(value))
            intValue = Integer.valueOf(value).intValue();
        return intValue;
    }

    protected String getExtensionElementValue(String name, UserTask userTask) {
        String value = "";
        if (CollectionUtils.isNotEmpty((Collection)userTask.getExtensionElements().get(name))) {
            ExtensionElement extensionElement = ((List<ExtensionElement>)userTask.getExtensionElements().get(name)).get(0);
            value = extensionElement.getElementText();
        }
        return value;
    }

    protected FlowElement convertJsonToElement(JsonNode elementNode, JsonNode modelNode, Map<String, JsonNode> shapeMap) {
        UserTask task = new UserTask();
        task.setDueDate(getPropertyValueAsString("duedatedefinition", elementNode));
        task.setCategory(getPropertyValueAsString("categorydefinition", elementNode));
        JsonNode assignmentNode = getProperty("usertaskassignment", elementNode);
        if (assignmentNode != null) {
            JsonNode assignmentDefNode = assignmentNode.get("assignment");
            if (assignmentDefNode != null) {
                JsonNode assigneeNode = assignmentDefNode.get("assignee");
                if (assigneeNode != null && !assigneeNode.isNull())
                    task.setAssignee(assigneeNode.asText());
                JsonNode ownerNode = assignmentDefNode.get("owner");
                if (ownerNode != null && !ownerNode.isNull())
                    task.setOwner(ownerNode.asText());
                task.setCandidateUsers(getValueAsList("candidateUsers", assignmentDefNode));
                task.setCandidateGroups(getValueAsList("candidateGroups", assignmentDefNode));
            }
        }
        convertJsonToFormProperties(elementNode, (BaseElement)task);
        return (FlowElement)task;
    }

    protected void fillAssigneeInfo(JsonNode idmDefNode, JsonNode canCompleteTaskNode, UserTask task) {
        JsonNode assigneeNode = idmDefNode.get("assignee");
        JsonNode assigneeFieldNode = idmDefNode.get("assigneeField");
        if (assigneeNode != null && !assigneeNode.isNull()) {
            JsonNode idNode = assigneeNode.get("id");
            JsonNode emailNode = assigneeNode.get("email");
            if (idNode != null && !idNode.isNull() && StringUtils.isNotEmpty(idNode.asText())) {
                task.setAssignee(idNode.asText());
                addExtensionElement("activiti-idm-assignee", String.valueOf(true), task);
                addExtensionElement("assignee-info-email", emailNode, task);
                addExtensionElement("assignee-info-firstname", assigneeNode.get("firstName"), task);
                addExtensionElement("assignee-info-lastname", assigneeNode.get("lastName"), task);
                addExtensionElement("assignee-info-externalid", assigneeNode.get("externalId"), task);
            } else if (emailNode != null && !emailNode.isNull() && StringUtils.isNotEmpty(emailNode.asText())) {
                task.setAssignee(emailNode.asText());
                addExtensionElement("activiti-assignee-email", task.getAssignee(), task);
                addExtensionElement("activiti-idm-assignee", String.valueOf(true), task);
            }
        } else if (assigneeFieldNode != null && !assigneeFieldNode.isNull()) {
            JsonNode idNode = assigneeFieldNode.get("id");
            if (idNode != null && !idNode.isNull() && StringUtils.isNotEmpty(idNode.asText())) {
                task.setAssignee("${taskAssignmentBean.assignTaskToAssignee('" + idNode.asText() + "', execution)}");
                addExtensionElement("activiti-idm-assignee-field", idNode.asText(), task);
                addExtensionElement("assignee-field-info-name", assigneeFieldNode.get("name"), task);
            }
        }
        if (canCompleteTaskNode != null && !canCompleteTaskNode.isNull()) {
            addInitiatorCanCompleteExtensionElement(Boolean.valueOf(canCompleteTaskNode.asText()).booleanValue(), task);
        } else {
            addInitiatorCanCompleteExtensionElement(false, task);
        }
    }

    protected void fillCandidateUsers(JsonNode idmDefNode, JsonNode canCompleteTaskNode, UserTask task) {
        List<String> candidateUsers = new ArrayList<>();
        JsonNode candidateUsersNode = idmDefNode.get("candidateUsers");
        if (candidateUsersNode != null && candidateUsersNode.isArray()) {
            List<String> emails = new ArrayList<>();
            for (JsonNode userNode : candidateUsersNode) {
                if (userNode != null && !userNode.isNull()) {
                    JsonNode idNode = userNode.get("id");
                    JsonNode emailNode = userNode.get("email");
                    if (idNode != null && !idNode.isNull() && StringUtils.isNotEmpty(idNode.asText())) {
                        String id = idNode.asText();
                        candidateUsers.add(id);
                        addExtensionElement("user-info-email-" + id, emailNode, task);
                        addExtensionElement("user-info-firstname-" + id, userNode.get("firstName"), task);
                        addExtensionElement("user-info-lastname-" + id, userNode.get("lastName"), task);
                        addExtensionElement("user-info-externalid-" + id, userNode.get("externalId"), task);
                        continue;
                    }
                    if (emailNode != null && !emailNode.isNull() && StringUtils.isNotEmpty(emailNode.asText())) {
                        String email = emailNode.asText();
                        candidateUsers.add(email);
                        emails.add(email);
                    }
                }
            }
            if (emails.size() > 0)
                addExtensionElement("activiti-candidate-users-emails", StringUtils.join(emails, ","), task);
            if (candidateUsers.size() > 0) {
                addExtensionElement("activiti-idm-candidate-user", String.valueOf(true), task);
                if (canCompleteTaskNode != null && !canCompleteTaskNode.isNull()) {
                    addInitiatorCanCompleteExtensionElement(Boolean.valueOf(canCompleteTaskNode.asText()).booleanValue(), task);
                } else {
                    addInitiatorCanCompleteExtensionElement(false, task);
                }
            }
        }
        JsonNode candidateUserFieldsNode = idmDefNode.get("candidateUserFields");
        if (candidateUserFieldsNode != null && candidateUserFieldsNode.isArray())
            for (JsonNode fieldNode : candidateUserFieldsNode) {
                JsonNode idNode = fieldNode.get("id");
                if (idNode != null && !idNode.isNull() && StringUtils.isNotEmpty(idNode.asText())) {
                    String id = idNode.asText();
                    candidateUsers.add("field(" + id + ")");
                    addExtensionElement("user-field-info-name-" + id, fieldNode.get("name"), task);
                }
            }
        if (candidateUsers.size() > 0)
            if (candidateUserFieldsNode != null && candidateUserFieldsNode.isArray() && candidateUserFieldsNode.size() > 0) {
                String candidateUsersString = StringUtils.join(candidateUsers, ",");
                candidateUsersString = "${taskAssignmentBean.assignTaskToCandidateUsers('" + candidateUsersString + "', execution)}";
                candidateUsers.clear();
                candidateUsers.add(candidateUsersString);
                task.setCandidateUsers(candidateUsers);
            } else {
                task.setCandidateUsers(candidateUsers);
            }
    }

    protected void fillCandidateGroups(JsonNode idmDefNode, JsonNode canCompleteTaskNode, UserTask task) {
        List<String> candidateGroups = new ArrayList<>();
        JsonNode candidateGroupsNode = idmDefNode.get("candidateGroups");
        if (candidateGroupsNode != null && candidateGroupsNode.isArray())
            for (JsonNode groupNode : candidateGroupsNode) {
                if (groupNode != null && !groupNode.isNull()) {
                    JsonNode idNode = groupNode.get("id");
                    JsonNode nameNode = groupNode.get("name");
                    if (idNode != null && !idNode.isNull() && StringUtils.isNotEmpty(idNode.asText())) {
                        String id = idNode.asText();
                        candidateGroups.add(id);
                        addExtensionElement("group-info-name-" + id, nameNode, task);
                        addExtensionElement("group-info-externalid-" + id, groupNode.get("externalId"), task);
                    }
                }
            }
        JsonNode candidateGroupFieldsNode = idmDefNode.get("candidateGroupFields");
        if (candidateGroupFieldsNode != null && candidateGroupFieldsNode.isArray())
            for (JsonNode fieldNode : candidateGroupFieldsNode) {
                JsonNode idNode = fieldNode.get("id");
                if (idNode != null && !idNode.isNull() && StringUtils.isNotEmpty(idNode.asText())) {
                    String id = idNode.asText();
                    candidateGroups.add("field(" + id + ")");
                    addExtensionElement("group-field-info-name-" + id, fieldNode.get("name"), task);
                }
            }
        if (candidateGroups.size() > 0) {
            if (candidateGroupFieldsNode != null && candidateGroupFieldsNode.isArray() && candidateGroupFieldsNode.size() > 0) {
                String candidateGroupsString = StringUtils.join(candidateGroups, ",");
                candidateGroupsString = "${taskAssignmentBean.assignTaskToCandidateGroups('" + candidateGroupsString + "', execution)}";
                candidateGroups.clear();
                candidateGroups.add(candidateGroupsString);
                task.setCandidateGroups(candidateGroups);
            } else {
                task.setCandidateGroups(candidateGroups);
            }
            addExtensionElement("activiti-idm-candidate-group", String.valueOf(true), task);
            if (canCompleteTaskNode != null && !canCompleteTaskNode.isNull()) {
                addInitiatorCanCompleteExtensionElement(Boolean.valueOf(canCompleteTaskNode.asText()).booleanValue(), task);
            } else {
                addInitiatorCanCompleteExtensionElement(false, task);
            }
        }
    }

    protected void addInitiatorCanCompleteExtensionElement(boolean canCompleteTask, UserTask task) {
        addExtensionElement("initiator-can-complete", String.valueOf(canCompleteTask), task);
    }

    protected void addExtensionElement(String name, JsonNode elementNode, UserTask task) {
        if (elementNode != null && !elementNode.isNull() && StringUtils.isNotEmpty(elementNode.asText()))
            addExtensionElement(name, elementNode.asText(), task);
    }

    protected void addExtensionElement(String name, String elementText, UserTask task) {
        ExtensionElement extensionElement = new ExtensionElement();
        extensionElement.setNamespace("http://activiti.com/modeler");
        extensionElement.setNamespacePrefix("modeler");
        extensionElement.setName(name);
        extensionElement.setElementText(elementText);
        task.addExtensionElement(extensionElement);
    }

    protected void fillProperty(String propertyName, String extensionElementName, ObjectNode elementNode, UserTask task) {
        List<ExtensionElement> extensionElementList = (List<ExtensionElement>)task.getExtensionElements().get(extensionElementName);
        if (CollectionUtils.isNotEmpty(extensionElementList))
            elementNode.put(propertyName, ((ExtensionElement)extensionElementList.get(0)).getElementText());
    }
}

