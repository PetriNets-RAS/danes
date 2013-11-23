/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Core.Element;
import Core.PetriNet;
import Core.PrecedenceGraph;
import GUI.View.DiagramPanel;
import java.awt.Point;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import javax.swing.JScrollPane;

/**
 *
 * @author SYSTEM
 */
public class ScaleHandler implements MouseWheelListener{
    private DiagramPanel diagramPanel;
    private JScrollPane diagramScrollPane;
    public ScaleHandler(DiagramPanel pDiagramPanel,JScrollPane pScrollPane){
        this.diagramPanel = pDiagramPanel;
        this.diagramScrollPane = pScrollPane;
    }
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
            if(e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL && e.isControlDown()) {
                        /*
                        ellipseButton.setSelected(false);
                        rectangleButton.setSelected(false);
                        resuorceButton.setSelected(false);
                        rectangleButton.setSelected(false);
                        lineButton.setSelected(false); 
                        */ 
                        int minX = Integer.MAX_VALUE;
                        int minY = Integer.MAX_VALUE;
                        int maxX = 0;
                        int maxY = 0;
                        ArrayList<Element> elements = new ArrayList<Element>();
                        if(this.diagramPanel.getGraph() instanceof PetriNet){
                            PetriNet pn = (PetriNet)this.diagramPanel.getGraph();
                            elements = new ArrayList<Element>();
                            elements.addAll(pn.getListOfArcs());
                            elements.addAll(pn.getListOfPlaces());
                            elements.addAll(pn.getListOfResources());
                            elements.addAll(pn.getListOfTransitions());
                        }
                        if(this.diagramPanel.getGraph() instanceof PrecedenceGraph){
                            PrecedenceGraph pg = (PrecedenceGraph)this.diagramPanel.getGraph();
                            elements = new ArrayList<Element>();
                            elements.addAll(pg.getListOfArcs());
                            elements.addAll(pg.getListOfNodes());
                        }
                        for (Element element : elements) {
                            if(minX>element.getX()){
                                minX = element.getX();
                            }
                            if(minY>element.getY()){
                                minY = element.getY();
                            }
                            if(maxX<element.getX()){
                                maxX = element.getX();
                            }
                            if(maxY<element.getY()){
                                maxY = element.getY();
                            }
                        }
                    Point mousePosition = this.diagramPanel.getMousePosition();
                    double scale = (.1 * e.getWheelRotation());
                    //System.out.println(""+mousePosition.x+", "+mousePosition.y+" "+ maxX+" "+maxY);
                    if(this.diagramPanel.getScaleRatio()[0] > 0.3 || scale > 0){
                        this.diagramPanel.getScaleRatio()[0] += scale;
                        this.diagramPanel.getScaleRatio()[1] += scale;
                    }else{
                        this.diagramPanel.getScaleRatio()[0] = 0.3;
                        this.diagramPanel.getScaleRatio()[1]= 0.3;
                    }
                    if(scale > 0){
                        int graphWidth = maxX - minX;
                        int graphHeight = maxY - minY;
                        int w = (int) (((diagramScrollPane.getWidth()/2)-(graphWidth/2))/this.diagramPanel.getScaleRatio()[0]);
                        int h = (int) (((diagramScrollPane.getHeight()/2)-(graphHeight/2))/ this.diagramPanel.getScaleRatio()[1]);
                        int newX = (int) ((minX-w)* this.diagramPanel.getScaleRatio()[0]);
                        int newY = (int) ((minY-h)* this.diagramPanel.getScaleRatio()[1]);
                        diagramScrollPane.getViewport().setViewPosition(new java.awt.Point(newX,newY));
                    }else{
                        int graphWidth = maxX - minX;
                        int graphHeight = maxY - minY;
                        int w = (int) (((diagramScrollPane.getWidth()/2)-(graphWidth/2))/this.diagramPanel.getScaleRatio()[0]);
                        int h = (int) (((diagramScrollPane.getHeight()/2)-(graphHeight/2))/this.diagramPanel.getScaleRatio()[1]);
                        int newX = (int) ((minX-w-50/this.diagramPanel.getScaleRatio()[0])*this.diagramPanel.getScaleRatio()[0]);
                        int newY = (int) ((minY-h-50/this.diagramPanel.getScaleRatio()[1])*this.diagramPanel.getScaleRatio()[1]);
                        diagramScrollPane.getViewport().setViewPosition(new java.awt.Point(newX,newY));                              
                    } 
                    this.diagramPanel.repaint();
            }
    }
}
