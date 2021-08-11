package org.activiti.image;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.List;

import org.activiti.bpmn.model.BpmnModel;

/**
 * This interface declares methods to generate process diagram
 *
 * @author martin.grofcik
 * @author Tijs Rademakers
 */
public interface ProcessDiagramGeneratorMine extends ProcessDiagramGenerator{

    /**
     * Generates a diagram of the given process definition, using the
     * diagram interchange information of the process.
     * @param bpmnModel bpmn model to get diagram for
     * @param imageType type of the image to generate.
     * @param highLightedActivities activities to highlight
     * @param highLightedFlows flows to highlight
     * @param activityFontName override the default activity font
     * @param labelFontName override the default label font
     * @param annotationFontName override the default annotation font
     * @param customClassLoader provide a custom classloader for retrieving icon images
     */
    public InputStream generateDiagram(BpmnModel bpmnModel, String imageType, List<String> highLightedActivities, List<String> highLightedFlows,
                                       String activityFontName, String labelFontName, String annotationFontName, ClassLoader customClassLoader, double scaleFactor);

    /**
     * Generates a diagram of the given process definition, using the
     * diagram interchange information of the process.
     * @param bpmnModel bpmn model to get diagram for
     * @param imageType type of the image to generate.
     * @param highLightedActivities activities to highlight
     * @param highLightedFlows flows to highlight
     */
    public InputStream generateDiagram(BpmnModel bpmnModel, String imageType, List<String> highLightedActivities, List<String> highLightedFlows);

    public InputStream generateDiagram(BpmnModel bpmnModel, String imageType, List<String> highLightedActivities,
                                       List<String> highLightedFlows, double scaleFactor);

    public InputStream generateDiagram(BpmnModel bpmnModel, String imageType, List<String> highLightedActivities);

    public InputStream generateDiagram(BpmnModel bpmnModel, String imageType, List<String> highLightedActivities, double scaleFactor);

    public InputStream generateDiagram(BpmnModel bpmnModel, String imageType, String activityFontName, String labelFontName, String annotationFontName, ClassLoader customClassLoader);

    public InputStream generateDiagram(BpmnModel bpmnModel, String imageType, String activityFontName,
                                       String labelFontName, String annotationFontName, ClassLoader customClassLoader, double scaleFactor);

    public InputStream generatePngDiagram(BpmnModel bpmnModel);

    public InputStream generatePngDiagram(BpmnModel bpmnModel, double scaleFactor);

    public InputStream generateJpgDiagram(BpmnModel bpmnModel);

    public InputStream generateJpgDiagram(BpmnModel bpmnModel, double scaleFactor);

    public BufferedImage generatePngImage(BpmnModel bpmnModel, double scaleFactor);


    public InputStream generateDiagram(BpmnModel bpmnModel, String imageType, List<String> currentActivity,List<String> highLightedActivities, List<String> highLightedFlows,
                                       String activityFontName, String labelFontName, String annotationFontName, ClassLoader customClassLoader, double scaleFactor);

}
