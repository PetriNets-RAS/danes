/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import GUI.DiagramElement;
import java.util.ArrayList;

/**
 *
 * @author Michal Skovajsa
 */
public class PrecedenceGraph extends Graph {

    private String name;
    private ArrayList<Node> listOfNodes;
    private ArrayList<Arc> listOfArcs;

    /**
     * @Class constructor
     */
    public PrecedenceGraph(String paName) {
        this.name = paName;

        this.listOfArcs = new ArrayList<Arc>();
        this.listOfNodes = new ArrayList<Node>();
    }

    /**
     * @Add a node to the Precedence Graph
     */
    public boolean addNode(Node paNode) {

        for (Node actNode : listOfNodes) {
            if (actNode.getName().equals(paNode.getName())) {
                return false;
            }
        }
        listOfNodes.add(paNode);
        return true;
    }

    /**
     * @Delete a place from Precedence Graph
     */
    public boolean deleteNode(String paName) {

        for (Node actNode : listOfNodes) {
            if (actNode.getName().equals(paName)) {

        for (Arc actArc : actNode.getListOfInArcs()) {
            Node temp = (Node) actArc.getOutElement();
            temp.getListOfOutArcs().remove(actArc);
            temp.getListOfOutNodes().remove(actNode);
            listOfArcs.remove(actArc);
        }

        for (Arc actArc : actNode.getListOfOutArcs()) {
            Node temp = (Node) actArc.getInElement();
            temp.getListOfInArcs().remove(actArc);
            temp.getListOfInNodes().remove(actNode);
            listOfArcs.remove(actArc);
        }

        listOfNodes.remove(actNode);
        return true;
            }
        }
        return false;
    }

    /**
     * @Add an arc to the Precedence Graph
     */
    public boolean addArc(Arc paArc) {

        for (Arc actArc : listOfArcs) {
            if (actArc.getName().equals(paArc.getName())) {
                return false;
            }
        }
        listOfArcs.add(paArc);

        Node outNode = (Node) paArc.getOutElement();
        Node inNode = (Node) paArc.getInElement();

        outNode.getListOfOutArcs().add(paArc);
        outNode.getListOfOutNodes().add(inNode);

        inNode.getListOfInArcs().add(paArc);
        inNode.getListOfInNodes().add(outNode);

        return true;
    }

    /**
     * @Delete an arc from Precedence Graph
     */
    public boolean deleteArc(String paName) {
        for (Arc actArc : listOfArcs) {
            if (actArc.getName().equals(paName)) {
                Node outNode = (Node) actArc.getOutElement();
                Node inNode = (Node) actArc.getInElement();

                outNode.getListOfOutArcs().remove(actArc);
                outNode.getListOfOutNodes().remove(inNode);

                inNode.getListOfInArcs().remove(actArc);
                inNode.getListOfInNodes().remove(outNode);

                //getTreeOfArcs().delete(actArc.getKey());
                listOfArcs.remove(actArc);
                return true;
            }
        }
        return false;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the listOfArcs
     */
    public ArrayList<Arc> getListOfArcs() {
        return listOfArcs;
    }

    /**
     * @return the listOfPlaces
     */
    public ArrayList<Core.Node> getListOfNodes() {
        return listOfNodes;
    }
    
    public PetriNet changePrecedenceGraphToPN(){
        PetriNet pn=new PetriNet(this.getName());
        
        for(Node n: this.listOfNodes){
            Transition tr=new Transition(n.getName());
            tr.setColor(n.getColor());
            tr.setFontSize(n.getFontSize());
            tr.setHeight(n.getHeight());
            tr.setNote(n.getNote());
            tr.setWidth(n.getWidth());
            tr.setDiagramElement(new DiagramElement(n.getDiagramElement().getX(),n.getDiagramElement().getY()));
            pn.addTransition(tr);
        }
        
        int temp=0;
        
        for(Arc a: this.listOfArcs){
            Place pl = new Place(a.getName());
            pl.setCapacity(a.getCapacity());
            pn.addPlace(pl);
            //pl.setDiagramElement(new DiagramElement(5,6));
           
            //vystupny prechod
            int x1=a.getOutElement().getDiagramElement().getX();
            int y1=a.getOutElement().getDiagramElement().getY();
            Transition tempTrIn=pn.getTransition(x1, y1);
            tempTrIn.getListOfOutPlaces().add(pl);
            pl.getListOfInTransitions().add(tempTrIn);
            Arc tempAr=new Arc("Arc"+temp,tempTrIn,pl);
            tempTrIn.getListOfOutArcs().add(tempAr);
            pl.getListOfInArcs().add(tempAr);
            pn.addArc(tempAr);
            
            temp++;
            
            //vystupny prechod
            int x2=a.getInElement().getDiagramElement().getX();
            int y2=a.getInElement().getDiagramElement().getY();
            Transition tempTrOut=pn.getTransition(x2, y2);
            tempTrOut.getListOfInPlaces().add(pl);
            pl.getListOfOutTransitions().add(tempTrOut);
            tempAr=new Arc("Arc"+temp,pl,tempTrOut);
            tempTrOut.getListOfInArcs().add(tempAr);
            pl.getListOfOutArcs().add(tempAr);
            pn.addArc(tempAr);
            
            temp++;
            
            //nastav umiestnenie miesta
            int x=(x1+x2)/2;
            int y=(y1+y2)/2;
            pl.setDiagramElement(new DiagramElement(x, y));
        }
        
        return pn;
    }
    
}
