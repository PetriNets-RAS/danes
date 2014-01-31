/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import javax.sound.sampled.Line;

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
    private Point outPoint;
    private Point inPoint;

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
        
        super.color = new Color(super.generalSettingsManager.getArcCol2());
        super.color2 = new Color(super.generalSettingsManager.getArcCol1());
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

    public void addBendPointSimple(Point p) {
        bendPoints.add(p);
    }

    public void addBendPoint(Point p) {
        Point first = new Point(outElement.getX(), outElement.getY());
        Point last = new Point(inElement.getX(), inElement.getY());

        int x = 0;
        int y = 0;
        int x2 = 0;
        int y2 = 0;
        if (inElement instanceof Transition) {
            Transition t = (Transition) inElement;
            x = t.getX() + (t.getWidth() / 2);
            y = t.getY() + (t.getHeight() / 2);
        } else if (inElement instanceof AbsPlace) {
            AbsPlace ap = (AbsPlace) inElement;
            x = ap.getX() + (ap.getWidth() / 2);
            y = ap.getY() + (ap.getHeight() / 2);
        } else if (inElement instanceof Node) {
            Node n = (Node) inElement;
            x = n.getX() + (n.getWidth() / 2);
            y = n.getY() + (n.getHeight() / 2);
        }

        if (outElement instanceof Transition) {
            Transition t = (Transition) outElement;
            x2 = t.getX() + (t.getWidth() / 2);
            y2 = t.getY() + (t.getHeight() / 2);
        } else if (outElement instanceof AbsPlace) {
            AbsPlace ap = (AbsPlace) outElement;
            x2 = ap.getX() + (ap.getWidth() / 2);
            y2 = ap.getY() + (ap.getHeight() / 2);
        } else if (outElement instanceof Node) {
            Node n = (Node) outElement;
            x2 = n.getX() + (n.getWidth() / 2);
            y2 = n.getY() + (n.getHeight() / 2);
        }

        Point in = new Point(x, y);
        Point out = new Point(x2, y2);

        bendPoints.add(in);
        bendPoints.add(0, out);


        for (int i = 0; i < bendPoints.size() - 1; i++) {

            Point A = bendPoints.get(i);
            Point B = bendPoints.get(i + 1);
            double distance = pointToLineDistance(A, B, p);
            Line2D.Double ln = new Line2D.Double(A, B);
            double dist=lineDist(ln);
            Line2D.Double ln1 = new Line2D.Double(A, p);
            double dist1=lineDist(ln1);
            Line2D.Double ln2 = new Line2D.Double(B, p);
            double dist2=lineDist(ln2);

            if (distance < 5 && dist1<dist && dist2<dist) {
                bendPoints.add(i + 1, p);
                bendPoints.remove(in);
                bendPoints.remove(out);
                System.out.println("ADDED");
                return;
            }

        }

        bendPoints.remove(in);
        bendPoints.remove(out);
    }
    
    public double lineDist(Line2D l){
        return Math.sqrt( Math.pow(l.getY2()-l.getY1(),2)+  Math.pow(l.getX2()-l.getX2(),2)  );        
    }
    
    public Point getPoint(int x, int y){
        for(Point actPoint:bendPoints){
            if (x >= actPoint.getX() - 5 && x<= actPoint.getX() + 5
                    && y >= actPoint.getY() - 5 && y <= actPoint.getY() + 5) {
                return actPoint;
            }            
        }             
        return null;
    }
    

    public void removeBendPoint(Point p) {
//        for (Point actPoint : bendPoints) {
//            if (p.getX() > actPoint.getX() - 5 && p.getX() < actPoint.getX() + 5
//                    && p.getY() > actPoint.getY() - 5 && p.getY() < actPoint.getY() + 5) {
//                
//                break;
//            }
//
//
//        }
        bendPoints.remove(p);
    }

    public double pointToLineDistance(Point A, Point B, Point P) {
        double normalLength = Math.sqrt((B.x - A.x) * (B.x - A.x) + (B.y - A.y) * (B.y - A.y));
        return Math.abs((P.x - A.x) * (B.y - A.y) - (P.y - A.y) * (B.x - A.x)) / normalLength;
    }

    /**
     * @return the outPoint
     */
    public Point getOutPoint() {
        return outPoint;
    }

    /**
     * @param outPoint the outPoint to set
     */
    public void setOutPoint(Point outPoint) {
        this.outPoint = outPoint;
    }

    /**
     * @return the inPoint
     */
    public Point getInPoint() {
        return inPoint;
    }

    /**
     * @param inPoint the inPoint to set
     */
    public void setInPoint(Point inPoint) {
        this.inPoint = inPoint;
    }
}