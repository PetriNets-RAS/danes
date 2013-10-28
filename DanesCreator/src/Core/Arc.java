/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import java.awt.Point;
import java.awt.geom.Line2D;
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

    public void addBendPoint(Point p) {
        bendPoints.add(p);
    }

    public void addBendPoints(Point p) {

        //if (!bendPoints.isEmpty()) {
            Point first = new Point(outElement.getX(), outElement.getY());
            Point last = new Point(inElement.getX(), inElement.getY());
            
            System.out.println("IN: "+inElement.getX()+" "+inElement.getY());
            System.out.println("OUT: "+outElement.getX()+" "+outElement.getY());
            bendPoints.add(last);
            bendPoints.add(0, first);


            for (int i = 0; i < bendPoints.size() - 1; i++) {
                
                Point A = bendPoints.get(i);
                Point B = bendPoints.get(i + 1);
                
                System.out.println("A: "+A.x+" "+A.y);
                System.out.println("B: "+B.x+" "+B.y);
                System.out.println("p: "+p.x+" "+p.y);
                
                double distance = pointToLineDistance(A, B, p);
                Line2D.Double ln=new Line2D.Double(A,B);
                
                System.out.println("+++");
                System.out.println("Distance: "+distance);
                
                if (ln.contains(p)) {
                    bendPoints.add(i+1, p);
                    System.out.println("Pridavam");
                    bendPoints.remove(first);
                    bendPoints.remove(last);
                    break;

                }

//                bendPoints.add(p);
//                bendPoints.remove(first);
//                bendPoints.remove(last);
//                System.out.println("bbb");

            }
            bendPoints.remove(first);
            bendPoints.remove(last);
//        } else {
//            bendPoints.add(p);
//        }

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

    public double pointToLineDistance(Point A, Point B, Point P) {
        double normalLength = Math.sqrt((B.x - A.x) * (B.x - A.x) + (B.y - A.y) * (B.y - A.y));
        return Math.abs((P.x - A.x) * (B.y - A.y) - (P.y - A.y) * (B.x - A.x)) / normalLength;
    }
}