/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Michal Skovajsa
 */
public class Arc extends Element {

    private int capacity;
    private Element inElement;
    private Element outElement;
    private int intercectionX;
    private int intercectionY;
    private ArrayList<Point> bendPoints;
    
     /**
     * @Class constructor.
     */
    public Arc(String paName, Element paOutElement, Element paInElement) {
        super(paName);
        //this.setName(paName);
        this.outElement=paOutElement;
        this.inElement=paInElement;
        this.capacity=1;
        this.bendPoints=new ArrayList<Point>();
        //this.setX(paOutElement.getX());
        //this.setX(paOutElement.getX());
        
        if(paOutElement instanceof Transition){
            this.setX(((Transition) paOutElement).getWidth()/2+paOutElement.getX());
            this.setY(((Transition) paOutElement).getHeight()/2+paOutElement.getY());
        }
        else if(paOutElement instanceof Place){
            this.setX(((Place) paOutElement).getWidth()/2+paOutElement.getX());
            this.setY(((Place) paOutElement).getHeight()/2+paOutElement.getY());
        }
        else if(paOutElement instanceof Resource){
            this.setX(((Resource) paOutElement).getWidth()/2+paOutElement.getX());
            this.setY(((Resource) paOutElement).getHeight()/2+paOutElement.getY());
        }
    }

    /**
     * @return the inElement
     */
    public Element getInElement() {
        return inElement;
    }

    /**
     * @param inElement the inElement to set
     */
    public void setInElement(Element inElement) {
        this.inElement = inElement;
    }

    /**
     * @return the outElement
     */
    public Element getOutElement() {
        return outElement;
    }

    /**
     * @param outElement the outElement to set
     */
    public void setOutElement(Element outElement) {
        this.outElement = outElement;
    }

    /**
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * @param capacity the capacity to set
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * @return the intercectionX
     */
    public int getIntercectionX() {
        return intercectionX;
    }

    /**
     * @param intercectionX the intercectionX to set
     */
    public void setIntercectionX(int intercectionX) {
        this.intercectionX = intercectionX;
    }

    /**
     * @return the intercectionY
     */
    public int getIntercectionY() {
        return intercectionY;
    }

    /**
     * @param intercectionY the intercectionY to set
     */
    public void setIntercectionY(int intercectionY) {
        this.intercectionY = intercectionY;
    }

    /**
     * @return the bendPoints
     */
    public ArrayList<Point> getBendPoints() {
        return bendPoints;
    }

    /**
     * @param bendPoints the bendPoints to set
     */
    public void setBendPoints(ArrayList<Point> bendPoints) {
        this.bendPoints = bendPoints;
    }
    
    public void removeBendPoint(Point p){
        for(Point actPoint : bendPoints){
            if( p.getX()>actPoint.getX()-5 && p.getX()<actPoint.getX()+5
                    && p.getY()>actPoint.getY()-5 && p.getY()<actPoint.getY()+5){
                bendPoints.remove(actPoint);
                break;
            }
                
            
        }
    }
    
}