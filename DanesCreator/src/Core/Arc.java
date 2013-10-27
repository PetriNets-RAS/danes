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
        this.outElement = paOutElement;
        this.inElement = paInElement;
        this.capacity = 1;
        this.bendPoints = new ArrayList<Point>();
        //this.setX(paOutElement.getX());
        //this.setX(paOutElement.getX());

        if (paOutElement instanceof Transition) {
            this.setX(((Transition) paOutElement).getWidth() / 2 + paOutElement.getX());
            this.setY(((Transition) paOutElement).getHeight() / 2 + paOutElement.getY());
        } else if (paOutElement instanceof Place) {
            this.setX(((Place) paOutElement).getWidth() / 2 + paOutElement.getX());
            this.setY(((Place) paOutElement).getHeight() / 2 + paOutElement.getY());
        } else if (paOutElement instanceof Resource) {
            this.setX(((Resource) paOutElement).getWidth() / 2 + paOutElement.getX());
            this.setY(((Resource) paOutElement).getHeight() / 2 + paOutElement.getY());
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

    public void addBendPoint(Point p){
        bendPoints.add(p);
    }
    
    public void addBendPoints(Point p) {
        System.out.println("000");
        if (bendPoints.size() != 0) {
            for (int i = 0; i < bendPoints.size(); i++) {
                Point actPoint = bendPoints.get(i);
                System.out.println("+++");

                
                if (p.getX() > actPoint.getX() - 5 && p.getX() < actPoint.getX() + 5
                        && p.getY() > actPoint.getY() - 5 && p.getY() < actPoint.getY() + 5) {
                    //bendPoints.remove(actPoint);
                    System.out.println("111");
                    if (i + 1 < bendPoints.size()-1) {
                        bendPoints.add(i + 1, p);
                        System.out.println("aaa");
                    } else {
                        bendPoints.add(p);
                        System.out.println("bbb");
                    }
                    break;
                }


            }
        }else{
            bendPoints.add(p);
        }

    }

    public void removeBendPoint(Point p) {
        for (Point actPoint : bendPoints) {
            if (p.getX() > actPoint.getX() - 5 && p.getX() < actPoint.getX() + 5
                    && p.getY() > actPoint.getY() - 5 && p.getY() < actPoint.getY() + 5) {
                bendPoints.remove(actPoint);
                break;
            }


        }
    }
}