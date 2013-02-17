/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import AVL_Tree.StringKey;
import AVL_Tree.Tree;
import java.util.ArrayList;

/**
 *
 * @author Michal Skovajsa
 */
public class PrecedenceGraph extends Graph{

    private String name;
    private Tree treeOfNodes;
    private Tree treeOfArcs;

//    private ArrayList<Node> treeOfNodes;
//    private ArrayList<Arc> treeOfArcs;
    /**
     * @Class constructor
     */
    public PrecedenceGraph(String paName) {
        this.name = paName;
        this.treeOfArcs = new Tree(null);
        this.treeOfNodes = new Tree(null);

//        this.treeOfArcs = new ArrayList<>();
//        this.treeOfNodes = new ArrayList<>();
    }

    /**
     * @Add a node to the Precedence Graph
     */
    public boolean addNode(Node paNode) {

        boolean temp = getTreeOfNodes().addNode(paNode);
        return temp;

//        for (Node actNode : treeOfNodes) {
//            if (actNode.getName().equals(paNode.getName())) {
//                return false;
//            }
//        }
//        treeOfNodes.add(paNode);
//        return true;
    }

    /**
     * @Delete a place from Precedence Graph
     */
    public boolean deleteNode(String paName) {

        Node actNode = (Node) getTreeOfNodes().find(new StringKey(paName));
        if (actNode == null) {
            return false;
        }
//        for (Node actNode : treeOfNodes) {
//            if (actNode.getName().equals(paName)) {

        for (Arc actArc : actNode.getListOfInArcs()) {
            Node temp = (Node) actArc.getOutElement();
            temp.getListOfOutArcs().remove(actArc);
            temp.getListOfOutNodes().remove(actNode);
            getTreeOfArcs().delete(actArc.getKey());
            //listOfArcs.remove(actArc);
        }

        for (Arc actArc : actNode.getListOfOutArcs()) {
            Node temp = (Node) actArc.getInElement();
            temp.getListOfInArcs().remove(actArc);
            temp.getListOfInNodes().remove(actNode);
            getTreeOfArcs().delete(actArc.getKey());
            //listOfArcs.remove(actArc);
        }

        getTreeOfNodes().delete(actNode.getKey());
        //listOfNodes.remove(actNode);
        return true;
//            }
//        }
//        return false;
    }

    /**
     * @Add an arc to the Precedence Graph
     */
    public boolean addArc(Arc paArc) {

        boolean temp = getTreeOfArcs().addNode(paArc);
        if (temp == false) {
            return false;
        }

//        for (Arc actArc : getListOfArcs()) {
//            if (actArc.getName().equals(paArc.getName())) {
//                return false;
//            }
//        }
//        getListOfArcs().add(paArc);

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
//        for (Arc actArc : getListOfArcs()) {
//            if (actArc.getName().equals(paName)) {

        Arc actArc = (Arc) getTreeOfArcs().find(new StringKey(paName));
        if (actArc == null) {
            //System.out.println("aaa");
            return false;
        }


        Node outNode = (Node) actArc.getOutElement();
        Node inNode = (Node) actArc.getInElement();

        outNode.getListOfOutArcs().remove(actArc);
        outNode.getListOfOutNodes().remove(inNode);

        inNode.getListOfInArcs().remove(actArc);
        inNode.getListOfInNodes().remove(outNode);

        getTreeOfArcs().delete(actArc.getKey());
        //getListOfArcs().remove(actArc);
        return true;
//    }
//}
//return false;
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

//    /**
//     * @return the treeOfNodes
//     */
//    public ArrayList<Node> getListOfNodes() {
//        return treeOfNodes;
//    }
//
//    /**
//     * @return the treeOfArcs
//     */
//    public ArrayList<Arc> getListOfArcs() {
//        return treeOfArcs;
//    }

    /**
     * @return the treeOfNodes
     */
    public Tree getTreeOfNodes() {
        return treeOfNodes;
    }

    /**
     * @return the treeOfArcs
     */
    public Tree getTreeOfArcs() {
        return treeOfArcs;
    }
    
    /**
     * @return the listOfArcs
     */
    public ArrayList<Arc> getListOfArcs() {
        ArrayList<Arc> ret=new ArrayList<>() ;
        
        for(AVL_Tree.Node n : treeOfArcs.inOrder() ){
            Arc tempArc=(Arc) n;
            ret.add(tempArc);
        }
        
        return ret;
    }
    
    /**
     * @return the listOfPlaces
     */
    public ArrayList<Core.Node> getListOfNodes() {
        
        ArrayList<Core.Node> ret=new ArrayList<>() ;
        
        for(AVL_Tree.Node n : treeOfNodes.inOrder() ){
            Core.Node tempNode=(Core.Node) n;
            ret.add(tempNode);
        }
        
        return ret;
    }
    
        public Arc getArc(String name) {
        return (Arc) treeOfArcs.find(new StringKey(name));
    }
        
    public Node getPlace(String name) {
        return (Node) treeOfNodes.find(new StringKey(name));
    }

    public void changeNameOfPlace(String oldName, String newName) {
        Node temp = (Node) treeOfNodes.find(new StringKey(oldName));
        treeOfNodes.delete(temp.getKey());
        temp.setName(newName);
        treeOfNodes.addNode(temp);
    }

    public void changeNameOfArc(String oldName, String newName) {
        Arc temp = (Arc) treeOfArcs.find(new StringKey(oldName));
        treeOfArcs.delete(temp.getKey());
        temp.setName(newName);
        treeOfArcs.addNode(temp);
    }
    
}
