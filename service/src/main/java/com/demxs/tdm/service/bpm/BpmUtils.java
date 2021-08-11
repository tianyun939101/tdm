package com.demxs.tdm.service.bpm;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demxs.tdm.common.utils.Dom4jUtils;
import com.demxs.tdm.service.bpm.activiti.editor.converter.ExtBpmnJsonConverter;
import com.demxs.tdm.service.bpm.graph.DivShape;
import com.demxs.tdm.service.bpm.graph.ShapeMeta;
import com.demxs.tdm.service.bpm.graph.activiti.BPMNEdge;
import com.demxs.tdm.service.bpm.graph.activiti.BPMNShap;
import com.demxs.tdm.service.bpm.graph.activiti.ProcessDiagramGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.XML;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BpmUtils {

    /**
     * BPM的XML的命名空间
     */
    private final static String BPM_XML_NS = "xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"";

    /**
     * 流程定义JSON文件转换为流程定义文件
     * @param defJson   流程json定义
     * @return
     */
    public static String jsonToXml(String defJson){
        String bpmnXml = "";
        try {
            JsonNode modelNode = (new ObjectMapper()).readTree(defJson);
            BpmnModel bpmnModel = (new ExtBpmnJsonConverter()).convertToBpmnModel(modelNode);
            BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
            byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);
            bpmnXml = new String(bpmnBytes, "utf-8");
        } catch (Exception exception) {}
        return bpmnXml;
    }

    /**
     * 流程定义文件xml字符串转换为流程定义JSON字符串
     * @param xmlStr   流程json定义
     * @return
     */
    public static String XMLtoJSON(String xmlStr){
        try {
            org.json.JSONObject xmlJSONObj = XML.toJSONObject(xmlStr);
            return xmlJSONObj.toString();
        } catch (Exception exception) {}
        return "";
    }

    /**
     * 根据流程定义xml获取流程节点的div数据。 用于显示流程定义页面和页面进行交互，比如设置流程节点的相关信息。
     *
     * @param xml
     * @return
     * @throws Exception
     */
    public static ShapeMeta transGraph(String xml) throws Exception {

        List<BPMNShap> shaps = ProcessDiagramGenerator.extractBPMNShap(xml);
        List<BPMNEdge> edges = ProcessDiagramGenerator.extractBPMNEdge(xml);
        Point2D.Double[] points = ProcessDiagramGenerator.caculateCanvasSize(shaps, edges);
        double shiftX = points[0].getX() < 0 ? points[0].getX() : 0;
        double shiftY = points[0].getY() < 0 ? points[0].getY() : 0;
        int width = (int) Math.round((points[1].getX() + 10 - shiftX));
        int height = (int) Math.round((points[1].getY() + 10 - shiftY));
        int minX = (int) Math.round((points[0].getX() - shiftX));
        ;
        int minY = (int) Math.round((points[0].getY() - shiftY));
        minX = (minX <= 5 ? 5 : minX);
        minY = (minY <= 5 ? 5 : minY);

        xml = xml.replace(BPM_XML_NS, "");
        Document doc = Dom4jUtils.loadXml(xml);
        Element root = doc.getRootElement();
        List sequenceFlows = root.selectNodes("//sequenceFlow");
        Map<String, String> seqIdandName = new HashMap<String, String>();
        StringBuffer sb = new StringBuffer();
        for (Object node : sequenceFlows) {
            String id = ((Element) node).attributeValue("id");
            String name = ((Element) node).attributeValue("name");
            seqIdandName.put(id, name);
        }
        List list = root.selectNodes("//bpmndi:BPMNShape");
        int subProcessNum = 1;// 内嵌子流程的层数
        Map<String, Integer> parentZIndexes = new HashMap<String, Integer>();// 存放父节点的Zindex ，key值为父节点的ID
        for (int i = 0; i < list.size(); i++) {
            Element el = (Element) list.get(i);

            // Exclude Pool and Lane components
            String id = el.attributeValue("bpmnElement");
            Element component = (Element) root.selectSingleNode("//*[@id='" + id + "']");

            if (component == null || component.getName().equalsIgnoreCase("participant") || component.getName().equalsIgnoreCase("lane")) {
                continue;
            }

            Element tmp = (Element) el.selectSingleNode("omgdc:Bounds");
            int x = (int) Float.parseFloat(tmp.attributeValue("x"));
            int y = (int) Float.parseFloat(tmp.attributeValue("y"));

            int w = (int) Float.parseFloat(tmp.attributeValue("width"));
            int h = (int) Float.parseFloat(tmp.attributeValue("height"));
            x = (int) (x - minX + 5 - shiftX);
            y = (int) (y - minY + 5 - shiftY);

            Element procEl = (Element) root.selectSingleNode("//process/descendant::*[@id='" + id + "']");
            if (procEl != null) {
                String type = procEl.getName();
                if (type.equals("serviceTask")) {
                    Element ext = procEl.element("extensionElements");
                    Element service = ext.element("serviceType");
                    type = service.attributeValue("value");
                }
                if (!"subProcess".equals(type) && !"callActivity".equals(type)) {
                    Element multiObj = procEl.element("multiInstanceLoopCharacteristics");
                    if (multiObj != null && !"subProcess".equals(type))
                        type = "multiUserTask";
                }

                Element parent = procEl.getParent();

                String name = procEl.attributeValue("name");

                int zIndex = 10;
                String parentName = parent.getName();
                // 父节点为子流程的情况，zindex 设为父节点ZIndex+1，开始事件类型修改为subStartEvent。
                if (parentName.equals("subProcess")) {
                    if (parent.getParent().getName().equals("subProcess")) {
                        subProcessNum++;
                    }
                    if (type.equalsIgnoreCase("subProcess")) {
                        zIndex = parentZIndexes.get(parent.attributeValue("id")) + 1;
                        parentZIndexes.put(id, zIndex);
                    } else if (type.equalsIgnoreCase("startEvent")) {
                        type = "subStartEvent";
                    } else if (type.equalsIgnoreCase("endEvent")) {
                        type = "subEndEvent";
                    } else {
                        zIndex = 10 + subProcessNum;
                    }
                } else {
                    if (type.equalsIgnoreCase("subProcess")) {
                        parentZIndexes.put(id, zIndex);
                    }
                }
                DivShape shape = new DivShape(name, (float) x, (float) y, w, h, zIndex, id, type);
                sb.append(shape);
            }
        }

        ShapeMeta shapeMeta = new ShapeMeta(width, height, sb.toString());
        return shapeMeta;
    }
}
