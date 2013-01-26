/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import java.util.ArrayList;

/**
 *
 * @author Michal Skovajsa
 */
public class PrecedenceGraph {

    private String name;
    private ArrayList<Node> listOfNodes;
    private ArrayList<Arc> listOfArcs;
    
    /**
     * @Class constructor
     */
    public PrecedenceGraph(String paName) {
        this.name = paName;
        this.listOfArcs = new ArrayList<>();
        this.listOfNodes = new ArrayList<>();
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
                    getListOfArcs().remove(actArc);
                }

                for (Arc actArc : actNode.getListOfOutArcs()) {
                    Node temp = (Node) actArc.getInElement();
                    temp.getListOfInArcs().remove(actArc);
                    temp.getListOfInNodes().remove(actNode);
                    getListOfArcs().remove(actArc);
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
        for (Arc actArc : getListOfArcs()) {
            if (actArc.getName().equals(paArc.getName())) {
                return false;
            }
        }
        getListOfArcs().add(paArc);

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
        for (Arc actArc : getListOfArcs()) {
            if (actArc.getName().equals(paName)) {

                Node outNode = (Node) actArc.getOutElement();
                Node inNode = (Node) actArc.getInElement();

                outNode.getListOfOutArcs().remove(actArc);
                outNode.getListOfOutNodes().remove(inNode);

                inNode.getListOfInArcs().remove(actArc);
                inNode.getListOfInNodes().remove(outNode);

                getListOfArcs().remove(actArc);
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
     * @return the listOfNodes
     */
    public ArrayList<Node> getListOfNodes() {
        return listOfNodes;
    }

    /**
     * @return the listOfArcs
     */
    public ArrayList<Arc> getListOfArcs() {
        return listOfArcs;
    }
}
