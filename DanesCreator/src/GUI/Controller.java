/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Core.AbsPlace;
import Core.Arc;
import Core.Element;
import Core.Graph;
import Core.Node;
import Core.PetriNet;
import Core.Place;
import Core.PrecedenceGraph;
import Core.Resource;
import Core.Transition;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;

/**
 *
 * @author marek
 */
public class Controller {
    private Graph        graph;
    //private View        mainFrame;

    public Controller(Graph pa_graph) {
        this.graph    = pa_graph;
    }

    public boolean isLocationEmpty(int x, int y) {
        if (getLocationElement(x, y)!=null)
            return false;
        return true;
        /*if (graph instanceof PetriNet)
        {
            for (Element e : ((PetriNet)graph).getListOfPlaces()) {
                if (e.getDiagramElement().getX() == x &&
                    e.getDiagramElement().getY() == y) 
                {
                    return false;
                }
            }
            for (Element e : ((PetriNet)graph).getListOfTransitions()) {
                if (e.getDiagramElement().getX() == x &&
                    e.getDiagramElement().getY() == y) 
                {
                    return false;
                }
            }
            
            return true;
        }
        
        if (graph instanceof PrecedenceGraph)
        {
            for (Element e : ((PrecedenceGraph)graph).getListOfNodes()) {
                if (e.getDiagramElement().getX() == x &&
                    e.getDiagramElement().getY() == y) 
                {
                    return false;
                }
            }
        }
        return true;
        //throw new Exception("Chyba isLocationEmpty");
        //return true;
        */
    }
        
    public void addPlace(String name, int x, int y) {                
        // Check if coordinates place is empty  
        if (!isLocationEmpty(x, y))
            return;        
        if (graph instanceof PetriNet)
        {

            Place place;        

            if(name.equals("Place"))
            {            

                // Generate text name until it is unique
                int _offset=1;
                do
                {
                    place=new Place("Place"+(((PetriNet)graph).getListOfPlaces().size()+_offset));
                    place.setX(x);
                    place.setY(y);
                    _offset++;
                }
                while 
                (!((PetriNet)graph).addPlace(place));
            }
            else // Normal add
            {
                    place=new Place(name);
                    place.setX(x);
                    place.setY(y);
                    ((PetriNet)graph).addPlace(place);
            }
        } // Koniec Petri Net
    }
        public void addResource(String name, int x, int y) {                
        // Check if coordinates place is empty  
        if (!isLocationEmpty(x, y))
            return;        
        if (graph instanceof PetriNet)
        {
            Resource resource;

            if(name.equals("Resource"))
            {            

                // Generate text name until it is unique
                int _offset=1;
                do
                {
                    resource=new Resource("Resource"+(((PetriNet)graph).getListOfResources().size()+_offset));
                    resource.setX(x);
                    resource.setY(y);
                    _offset++;
                    //resource.setDiagramElement(new DiagramElement(x, y));                
                }
                while 
                (!((PetriNet)graph).addResource(resource));
            }
            else // Normal add
            {
                    resource=new Resource(name);
                    resource.setX(x);
                    resource.setY(y);
                    //resource.setDiagramElement(new DiagramElement(x, y));  
                    ((PetriNet)graph).addResource(resource);
            }
        } // Koniec Petri Net
    }

    public void addTransition(String name, int x, int y) {            
        // Check if coordinates place is empty  
        if (!isLocationEmpty(x, y))
            return;
        
        if (graph instanceof PetriNet)
        {
            Transition transition;        

            if (name.equals("Transition"))
            {   
                // Generate text name until it is unique
                int _offset=1;
                do
                {
                    transition=new Transition("Transition"+(((PetriNet)graph).getListOfTransitions().size()+_offset));                    
                    transition.setX(x);
                    transition.setY(y);
                    _offset++;
                    //transition.setDiagramElement(new DiagramElement(x, y));                
                }
                while      
                   (!((PetriNet)graph).addTransition(transition));
            }
            else // Normal add
            {
                transition=new Transition(name);
                transition.setX(x);
                transition.setY(y);
                //transition.setDiagramElement(new DiagramElement(x, y));      
                ((PetriNet)graph).addTransition(transition);
            }  
        } // Koniec Petri Net transition
    }

    public void addArc(String name, int x1, int y1, int x2, int y2) 
    {
        Element out     =getLocationElement(x1, y1);
        Element in      =getLocationElement(x2, y2);
        Core.Logic log=new Core.Logic();
        
        // Null
        if (in == null || out ==  null)
            return;
        
        // Petri Net
        if (graph instanceof PetriNet)
        {        
            Arc arc;

            if (name.equals("Arc"))
            {   
                // Generate text name until it is unique
                int _offset=1;
                do
                {
                    int prefix=(((PetriNet)graph).getListOfArcs().size()+_offset);
                    arc=new Arc("Arc"+prefix,out,in);
                    if(!log.checkArc(arc,graph)) {
                        break;
                    }           
                    _offset++;
                }
                while                       
                   (!((PetriNet)graph).addArc(arc));
            }
            else // Normal add
            {
                arc=new Arc(name,out,in);
                ((PetriNet)graph).addArc(arc);
        
            }   
        }// Koniec Petri Net
        if (graph instanceof PrecedenceGraph)
        {        
            Arc arc;

            if (name.equals("Arc"))
            {   
                // Generate text name until it is unique
                int _offset=1;
                do
                {
                    int prefix=(((PrecedenceGraph)graph).getListOfArcs().size()+_offset);
                    arc=new Arc("Arc"+prefix,out,in);
                    if(!log.checkArc(arc,graph)) {
                        break;                        
                    }
                    _offset++;
                }
                while                       
                   (!((PrecedenceGraph)graph).addArc(arc));
            }
            else // Normal add
            {
                arc=new Arc(name,out,in);
                ((PrecedenceGraph)graph).addArc(arc);        
            }   
        }// End Precedence graph        
        
    }
    public void moveElement(int x_old_location, int y_old_location, int x_new_location, int y_new_location) {
        
        // ! vykricnik pri 2 ifoch by mal by naopak, ale takto to funguje
        // Source location can not be empty
        if (isLocationEmpty(x_old_location, y_old_location))
            return;
        // Destination location should be empty
        if (!isLocationEmpty(x_new_location, y_new_location))
            return;       
        
        Element e = getLocationElement(x_old_location, y_old_location);
        //e.setDiagramElement(new DiagramElement(x_new_location, y_new_location));
        e.setX(x_new_location);
        e.setY(y_new_location);

    }

    public Element getLocationElement(int x, int y) {
        if (graph instanceof PetriNet)
        {
            // Place
            for(Element e:((PetriNet)graph).getListOfPlaces())
            {
                // Ak sa X nachadza medzi bodom a jeho sirkou
                // Ak sa Y nachadza medzi bodom a jeho vyskou
                if (    e.getX()                  <=x &&
                        e.getX()+((Place)e).getWidth()     >=x &&
                        e.getY()                  <=y &&
                        e.getY()+((Place)e).getHeight()    >=y
                    )
                {
                    return e;
                }
            }
            // Resource
            for(Element e:((PetriNet)graph).getListOfResources())
            {
                // Ak sa X nachadza medzi bodom a jeho sirkou
                // Ak sa Y nachadza medzi bodom a jeho vyskou
                if (    e.getX()                                      <=x &&
                        e.getX()+((Resource)e).getWidth()     >=x &&
                        e.getY()                                      <=y &&
                        e.getY()+((Resource)e).getHeight()    >=y
                    )
                {
                    return e;
                }
            }            
            // Transition
            for(Element e:((PetriNet)graph).getListOfTransitions())
            {
                // Ak sa X nachadza medzi bodom a jeho sirkou
                // Ak sa Y nachadza medzi bodom a jeho vyskou
                if (    e.getX()                                      <=x &&
                        e.getX()+((Transition)e).getWidth()     >=x &&
                        e.getY()                                      <=y &&
                        e.getY()+((Transition)e).getHeight()    >=y
                    )
                {
                    return e;
                }
            }  
        } // End Petri Net
      //Precedencny graf  
      if (graph instanceof PrecedenceGraph)
        {
            // Node
            for(Element e:((PrecedenceGraph)graph).getListOfNodes())
            {
                // Ak sa X nachadza medzi bodom a jeho sirkou
                // Ak sa Y nachadza medzi bodom a jeho vyskou
                if (    e.getX()                                      <=x &&
                        e.getX()+((Node)e).getWidth()     >=x &&
                        e.getY()                                      <=y &&
                        e.getY()+((Node)e).getHeight()    >=y
                    )
                {
                    return e;
                }
            }        
        }
        // Nothing found
        return null;
        
    }

    public boolean deleteElement(int x, int y) {
        Element element=getLocationElement(x, y);
        if (element==null)
        {
            return false;
        }
        
        // Place
        if (element instanceof Place)
        {
            ((PetriNet)graph).deletePlace(((Place)element).getName());
        }
        // Transition
        if (element instanceof Transition)
        {
            ((PetriNet)graph).deleteTransition(((Transition)element).getName());
        }
        // Node
        if (element instanceof Node)
        {
            ((PrecedenceGraph)graph).deleteNode(((Node)element).getName());
        }    
        // Place
        if (element instanceof Resource)
        {
            ((PetriNet)graph).deleteResource(((Resource)element).getName());
        }     
        
        return true;
    }            

    public void deleteArc(int x1,int y1,int x2,int y2)
    {
        Element out     =getLocationElement(x2, y2);
        Element in      =getLocationElement(x1, y1);
        
        if (out==null || in==null)
            return;
        
        // Petri Net Arcs
        if (graph instanceof PetriNet)
        {
            for(Arc a : ((PetriNet)graph).getListOfArcs())
            {
                if (a.getInElement()==in && a.getOutElement()==out)
                {
                    ((PetriNet)graph).deleteArc(a.getName());
                    break;
                }
            }
        }
        
       // Precedence Graph Arcs
        if (graph instanceof PrecedenceGraph)
        {
            for(Arc a : ((PrecedenceGraph)graph).getListOfArcs())
            {
                if (a.getInElement()==in && a.getOutElement()==out)
                {
                    ((PrecedenceGraph)graph).deleteArc(a.getName());
                    break;
                }
            }
        }        
    }

    public boolean deleteArc(double x, double y) {
        Arc arc=getLocationArc(x, y);
        if (arc==null)
        {
            return false;
        }
        if(graph instanceof PetriNet)
        {
            ((PetriNet)graph).deleteArc(arc.getName());
        }
        if(graph instanceof PrecedenceGraph)
        {
            ((PrecedenceGraph)graph).deleteArc(arc.getName());
        }
        return true;
    }     
    public void setModel(Graph graph) {
        this.graph=graph;
    }

    public void addNode(String name, int x, int y) {
        // Check if coordinates place is empty  
        if (!isLocationEmpty(x, y))
            return;        
        if (graph instanceof PrecedenceGraph)
        {
            Node node;        

            if(name.equals("Node"))
            {            
                // Generate text name until it is unique
                int _offset=1;
                do
                {
                    int prefix=(((PrecedenceGraph)graph).getListOfArcs().size()+_offset);                    
                    node=new Node("Node"+prefix);
                    node.setX(x);
                    node.setY(y);
                    _offset++;
                    //node.setDiagramElement(new DiagramElement(x, y));                
                }
                while 
                (!((PrecedenceGraph)graph).addNode(node));
            }
            else // Normal add
            {
                    node=new Node(name);
                    node.setX(x);
                    node.setY(y);
                    ((PrecedenceGraph)graph).addNode(node);
            }
        } // End precedence graph
    }

    public Arc getLocationArc(double x, double y) {
      if (graph instanceof PetriNet)
      {
            double x1,y1,x2,y2;
            // Arcs
            for(Arc a:((PetriNet)graph).getListOfArcs())
            {
                x1=a.getInElement().getX();
                y1=a.getInElement().getY();
                x2=a.getOutElement().getX();
                y2=a.getOutElement().getY();                
                
                // Width and Height depends on element
                x1+=25;
                y1+=25;                
                x2+=25;
                y2+=25;
                
                // Get line between 2 points
                Line2D.Double ln = new Line2D.Double(x1,y1,x2,y2);
          
                // Distance from central line
                double indent = 10.0; 
                double length = ln.getP1().distance(ln.getP2());
                
                double dx_li = (ln.getX2() - ln.getX1()) / length * indent;
                double dy_li = (ln.getY2() - ln.getY1()) / length * indent;

                // moved p1 point
                //double p1X = ln.getX1() - dx_li;
                //double p1Y = ln.getY1() - dy_li;

                // line moved to the left
                double lX1 = ln.getX1() - dy_li;
                double lY1 = ln.getY1() + dx_li;
                double lX2 = ln.getX2() - dy_li;
                double lY2 = ln.getY2() + dx_li;

                // moved p2 point
                //double p2X = ln.getX2() + dx_li;
                //double p2Y = ln.getY2() + dy_li;

                // line moved to the right
                double rX1_ = ln.getX1() + dy_li;
                double rY1 = ln.getY1() - dx_li;
                double rX2 = ln.getX2() + dy_li;
                double rY2 = ln.getY2() - dx_li;
                    
                Path2D path = new Path2D.Double();
                path.moveTo(lX1, lY1);

                path.lineTo(lX1, lY1);
                path.lineTo(lX2, lY2);
                //path.lineTo(p2X, p2Y);
                path.lineTo(rX2, rY2);
                path.lineTo(rX1_, rY1);
                //path.lineTo(p1X, p1Y);


               
                // Check if clickPoint(x,y) lies inside path
                if (path.contains(new Point((int)x, (int)y)))
                {
                    //System.out.println(("Ano: "+x+" " +y));
                    return a;
                }
                

                 // Draw results
                //Area area = new Area();
                //area.add(new Area(path));
                
               // g2d.draw(ln);
                //g2d.draw(p);            /*Rectangle2D rec=p.getBounds();
              //   g2d.draw(area);
                //g2d.draw(rec);*/
                //}
 
               // System.out.println(Math.ceil(23.46)); // Prints 24
                // System.out.println(Math.floor(23.46)); // Prints 23
/*
                x1=Math.ceil(x1/10.0);
                y1=Math.ceil(y1/10.0);
                x2=Math.ceil(x2/10.0);
                y2=Math.ceil(y2/10.0);
                
                double x_round=Math.ceil(x/10.0);
                double y_round=Math.ceil(y/10.0);
                                    
                //xx.contains(null);
                
                Line2D line=new Line2D.Double(x1, y1, x2, y2);
                // If point X,Y lies on Line(x1,y1,x2,y2)
                //if (line.relativeCCW(x, y)==0)
                if (line.relativeCCW(x_round, y_round)==0)
                {
                    System.out.println("Nachadzam sa na arc: "+a.getName());
                    return a;
                }
            }
            System.out.println("NEnachadzam sa na arc");*/ 
            }
            // No arc found
            return null;
        }
        
      //Precedencny graf  
      if (graph instanceof PrecedenceGraph)
        {
            double x1,y1,x2,y2;
            // Arc
            for(Arc a:((PrecedenceGraph)graph).getListOfArcs())
            {
                x1=a.getInElement().getX();
                y1=a.getInElement().getY();
                x2=a.getOutElement().getX();
                y2=a.getOutElement().getY();                
             
            // Width and Height depends on element
                x1+=25;
                y1+=25;                
                x2+=25;
                y2+=25;
                
                // Get line between 2 points
                Line2D.Double ln = new Line2D.Double(x1,y1,x2,y2);
          
                // Distance from central line
                double indent = 10.0; 
                double length = ln.getP1().distance(ln.getP2());
                
                double dx_li = (ln.getX2() - ln.getX1()) / length * indent;
                double dy_li = (ln.getY2() - ln.getY1()) / length * indent;

                // moved p1 point
                //double p1X = ln.getX1() - dx_li;
                //double p1Y = ln.getY1() - dy_li;

                // line moved to the left
                double lX1 = ln.getX1() - dy_li;
                double lY1 = ln.getY1() + dx_li;
                double lX2 = ln.getX2() - dy_li;
                double lY2 = ln.getY2() + dx_li;

                // moved p2 point
                //double p2X = ln.getX2() + dx_li;
                //double p2Y = ln.getY2() + dy_li;

                // line moved to the right
                double rX1_ = ln.getX1() + dy_li;
                double rY1 = ln.getY1() - dx_li;
                double rX2 = ln.getX2() + dy_li;
                double rY2 = ln.getY2() - dx_li;
                    
                Path2D path = new Path2D.Double();
                path.moveTo(lX1, lY1);

                path.lineTo(lX1, lY1);            
                path.lineTo(lX2, lY2);
                //path.lineTo(p2X, p2Y);
                path.lineTo(rX2, rY2);
                path.lineTo(rX1_, rY1);
                //path.lineTo(p1X, p1Y);


               
                // Check if clickPoint(x,y) lies inside path
                if (path.contains(new Point((int)x, (int)y)))
                {
                    //System.out.println(("Ano: "+x+" " +y));
                    return a;
                }
                                
            }
        }
        // Nothing found
        return null;
        
    }

    public int[] getMinXYMaxXY() {
        int minX=Integer.MAX_VALUE,minY=Integer.MAX_VALUE,maxX=Integer.MIN_VALUE,maxY=Integer.MIN_VALUE;
        
        if(graph instanceof PetriNet)
        {
            PetriNet pn=(PetriNet)graph;
            for (Place p : pn.getListOfPlaces()) {
                if (p.getX()<minX)
                    minX=p.getX();
                if (p.getX()+p.getWidth()>maxX)
                    maxX=p.getX();

                if (p.getY()<minY)
                    minY=p.getY();
                if (p.getY()+p.getHeight()>maxY)
                    maxY=p.getY();                
            }
            for (Resource p : pn.getListOfResources()) {
                if (p.getX()<minX)
                    minX=p.getX();
                if (p.getX()+p.getWidth()>maxX)
                    maxX=p.getX();

                if (p.getY()<minY)
                    minY=p.getY();
                if (p.getY()+p.getHeight()>maxY)
                    maxY=p.getY();                
            }
            
            for (Transition p : pn.getListOfTransitions()) {
                if (p.getX()<minX)
                    minX=p.getX();
                if (p.getX()+p.getWidth()>maxX)
                    maxX=p.getX();

                if (p.getY()<minY)
                    minY=p.getY();
                if (p.getY()+p.getHeight()>maxY)
                    maxY=p.getY();                
            }            
        }


        if(graph instanceof PrecedenceGraph)
        {
            PrecedenceGraph pn=(PrecedenceGraph)graph;
            for (Node p : pn.getListOfNodes()) {
                if (p.getX()<minX)
                    minX=p.getX();
                if (p.getX()+p.getWidth()>maxX)
                    maxX=p.getX();

                if (p.getY()<minY)
                    minY=p.getY();
                if (p.getY()+p.getHeight()>maxY)
                    maxY=p.getY();                
            }    
        }
        if(minX==Integer.MAX_VALUE || minY==Integer.MAX_VALUE || maxX==Integer.MIN_VALUE || maxY==Integer.MIN_VALUE)
            return new int[]{0,0,0,0};
        return new int[]{minX,minY,maxX,maxY};        
    }

    public void alignTop(ArrayList<Element> selectedElements) {
        /* Most higher - value 0 */
        int minTop=Integer.MAX_VALUE;
        for(Element e : selectedElements)
        {
            if (e.getY() <minTop)
                minTop=e.getY();
            // Arc is nothing //
        }
        for(Element e:selectedElements)
        {
            e.setY(minTop);
        }
    }    
    public void alignLeft(ArrayList<Element> selectedElements) {
        /* Most higher - value 0 */
        int minLeft=Integer.MAX_VALUE;
        for(Element e : selectedElements)
        {
            if (e.getX() <minLeft)
                minLeft=e.getX();
            // Arc is nothing //
        }
        for(Element e:selectedElements)
        {
            e.setX(minLeft);
        }
    }        
    public void alignRight(ArrayList<Element> selectedElements) {
        /* Most higher - value 0 */
        int maxRight=Integer.MIN_VALUE;
        int currentRight=0;
        for(Element e : selectedElements)
        {
            if (e instanceof AbsPlace)
            {
                AbsPlace a=(AbsPlace)e;
                currentRight=a.getX()+a.getWidth();
            }
            if (e instanceof Node)
            {
                Node n=(Node)e;
                currentRight=n.getX()+n.getWidth();                
            }
            if (e instanceof Transition)
            {
                Transition t=(Transition)e;
                currentRight=t.getX()+t.getWidth();
            }
            
            if(currentRight>maxRight)
                maxRight=currentRight;
            // Arc is nothing //
        }
        for(Element e : selectedElements)
        {
            if (e instanceof AbsPlace)
            {
                AbsPlace a=(AbsPlace)e;
                a.setX(maxRight-a.getWidth());
            }
            if (e instanceof Node)
            {
                Node n=(Node)e;
                n.setX(maxRight-n.getWidth());                
            }
            if (e instanceof Transition)
            {
                Transition t=(Transition)e;
                t.setX(maxRight-t.getWidth()); 
            }            
            // Arc is nothing //
        }
    }     
    public void alignBottom(ArrayList<Element> selectedElements) {
        /* Most lowest */
        int minBottom=Integer.MIN_VALUE;
        int currentBottom=0;
        for(Element e : selectedElements)
        {
            if (e instanceof AbsPlace)
            {
                AbsPlace a=(AbsPlace)e;
                currentBottom=a.getY()+a.getHeight();
            }
            if (e instanceof Node)
            {
                Node n=(Node)e;
                currentBottom=n.getY()+n.getHeight();             
            }
            if (e instanceof Transition)
            {
                Transition t=(Transition)e;
                currentBottom=t.getY()+t.getHeight();
            }
            
            if(currentBottom>minBottom)
                minBottom=currentBottom;
            // Arc is nothing //
        }
        for(Element e : selectedElements)
        {
            if (e instanceof AbsPlace)
            {
                AbsPlace a=(AbsPlace)e;
                a.setY(minBottom-a.getHeight());
            }
            if (e instanceof Node)
            {
                Node n=(Node)e;
                n.setY(minBottom-n.getHeight());           
            }
            if (e instanceof Transition)
            {
                Transition t=(Transition)e;
                t.setY(minBottom-t.getHeight());           
            }            
            // Arc is nothing //
        }
    }     
}
