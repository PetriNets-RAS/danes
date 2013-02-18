/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Core.Arc;
import Core.Element;
import Core.Graph;
import Core.Node;
import Core.PetriNet;
import Core.Place;
import Core.PrecedenceGraph;
import Core.Transition;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import javax.swing.JOptionPane;

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
        if (graph instanceof PetriNet)
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
                do
                {
                    place=new Place("Place"+Math.random());
                    place.setDiagramElement(new DiagramElement(x, y));                
                }
                while 
                (!((PetriNet)graph).addPlace(place));
            }
            else // Normal add
            {
                    place=new Place(name);
                    place.setDiagramElement(new DiagramElement(x, y));  
                    ((PetriNet)graph).addPlace(place);
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
                do
                {
                    transition=new Transition("Transition"+Math.random());
                    transition.setDiagramElement(new DiagramElement(x, y));                
                }
                while      
                   (!((PetriNet)graph).addTransition(transition));
            }
            else // Normal add
            {
                transition=new Transition(name);
                transition.setDiagramElement(new DiagramElement(x, y));      
                ((PetriNet)graph).addTransition(transition);
            }  
        } // Koniec Petri Net transition
    }

    public void addArc(String name, int x1, int y1, int x2, int y2) 
    {
        Element out     =getLocationElement(x2, y2);
        Element in      =getLocationElement(x1, y1);
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
                do
                {
                    arc=new Arc("Arc"+Math.random(),out,in);
                    if(!log.checkArc(arc,graph)) {
                        break;
                    }           
                }
                while                       
                   (!((PetriNet)graph).addArc(arc));
            }
            else // Normal add
            {
                arc=new Arc(name,out,in);
                ((PetriNet)graph).addArc(arc);
        
            }   
        }// Konice Petri Net
        if (graph instanceof PrecedenceGraph)
        {        
            Arc arc;

            if (name.equals("Arc"))
            {   
                // Generate text name until it is unique
                do
                {
                    arc=new Arc("Arc"+Math.random(),out,in);
                    if(!log.checkArc(arc,graph)) {
                        break;
                    }
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
        e.setDiagramElement(new DiagramElement(x_new_location, y_new_location));            

    }

    public Element getLocationElement(int x, int y) {
        if (graph instanceof PetriNet)
        {
            // Place
            for(Element e:((PetriNet)graph).getListOfPlaces())
            {
                if (    e.getDiagramElement().getX()==x &&
                        e.getDiagramElement().getY()==y )
                {
                    return e;
                }
            }
            // Transition
            for(Element e:((PetriNet)graph).getListOfTransitions())
            {
                if (    e.getDiagramElement().getX()==x &&
                        e.getDiagramElement().getY()==y )
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
                if (    e.getDiagramElement().getX()==x &&
                        e.getDiagramElement().getY()==y )
                {
                    return e;
                }
            }        
        }
        // Nothing found
        return null;
        
    }

    public void deleteElement(int x, int y) {
        Element element=getLocationElement(x, y);
        if (element==null)
        {
            return;
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

    public void deleteArc(double x, double y) {
        Arc arc=getLocationArc(x, y);
        if (arc==null)
            return;
        if(graph instanceof PetriNet)
        {
            ((PetriNet)graph).deleteArc(arc.getName());
        }
        if(graph instanceof PrecedenceGraph)
        {
            ((PrecedenceGraph)graph).deleteArc(arc.getName());
        }
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
                do
                {
                    node=new Node("Node"+Math.random());
                    node.setDiagramElement(new DiagramElement(x, y));                
                }
                while 
                (!((PrecedenceGraph)graph).addNode(node));
            }
            else // Normal add
            {
                    node=new Node(name);
                    node.setDiagramElement(new DiagramElement(x, y));  
                    ((PrecedenceGraph)graph).addNode(node);
            }
        } // End precedence graph
    }

    public Arc getLocationArc(double x, double y) {
      if (graph instanceof PetriNet)
      {
            double x1,y1,x2,y2;
            // Arc
            for(Arc a:((PetriNet)graph).getListOfArcs())
            {
                x1=a.getInElement().getDiagramElement().getX();
                y1=a.getInElement().getDiagramElement().getY();
                x2=a.getOutElement().getDiagramElement().getX();
                y2=a.getOutElement().getDiagramElement().getY();                
                
                Line2D line=new Line2D.Double(x1, y1, x2, y2);
                // If point X,Y lies on Line(x1,y1,x2,y2)
                if (line.relativeCCW(x, y)==0)
                {
                    System.out.println("Nachadzam sa na arc: "+a.getName());
                    return a;
                }
            }
            System.out.println("NEnachadzam sa na arc"); 
        }
        
      //Precedencny graf  
      if (graph instanceof PrecedenceGraph)
        {
            double x1,y1,x2,y2;
            // Arc
            for(Arc a:((PrecedenceGraph)graph).getListOfArcs())
            {
                x1=a.getInElement().getDiagramElement().getX();
                y1=a.getInElement().getDiagramElement().getY();
                x2=a.getOutElement().getDiagramElement().getX();
                y2=a.getOutElement().getDiagramElement().getY();                
                
                Line2D line=new Line2D.Double(x1, y1, x2, y2);
                // If point X,Y lies on Line(x1,y1,x2,y2)
                if (line.relativeCCW(x, y)==0)
                {
                    System.out.println("Nachadzam sa na arc: "+a.getName());
                    return a;
                }
            }
            System.out.println("NEnachadzam sa na arc"); 
        }
        // Nothing found
        return null;
        
    }
}
