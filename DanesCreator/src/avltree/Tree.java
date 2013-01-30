/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package avltree;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Atarin
 */
public class Tree {

    protected  Node root;
    protected  int amount;
    
    public Tree(Node root) {
        this.root = root;
        amount = 1;
        if(root==null) amount=0;
    }

    public boolean addNode(Node node) {
        if(this.amount==0 && this.root==null){
            amount++;
            root=node;
            root.compensate();
            return true;
        }
        
        if (this.find(node.getKey()) != null) {
            return false;
        }
        if (this.getRoot().setSon(node)) {
            //System.out.println("vkladam: "+node.getKey().getKey());
            this.amount++;
            node.setLeftHigh(0);
            node.setRightHigh(0);

            //vyvazit vsetky prvky az po koren
            Node temp = node.getFather();
            Node saved = node;
            do {
                //System.out.println("Vyvzujem"+temp.getKey().getKey());
                temp.compensate();
                //kontrola
                if (temp.getEquanimity() == 2 && temp.getRightSon() == saved && saved.getEquanimity() == -1) {
                    //System.out.println("PL tocim okolo "+temp.getKey().getKey());
                    this.rightLeftRotation(temp);
                    break;
                }
                if (temp.getEquanimity() == -2 && temp.getLeftSon() == saved && saved.getEquanimity() == 1) {
                    //System.out.println("LP tocim okolo "+temp.getKey().getKey());
                    this.leftRightRotation(temp);
                    break;
                }

                if (temp.getEquanimity() == 2) {
                    //System.out.println("vyvazenost je 2, rotujem okolo"+temp.getKey().getKey());                    
                    this.leftRotation(temp);
                    break;
                }

                if (temp.getEquanimity() == -2) {
                    //System.out.println("vyvazenost je -2, rotujem okolo"+temp.getKey().getKey());                   
                    this.rightRotation(temp);
                    //System.out.println("vyvazenost je -2");
                    break;
                }
                saved = temp;
                temp = temp.getFather();
            } while (temp != null);
            return true;
        }
        return false;
    }

    public Node maxLeftonRight(Node paNode) {
        //System.out.println("pouzivam LoR");
        if (this.amount == 0) {
            return null;
        }
        Node temp;
        if (paNode == null) {
            return null;
        } else {
            paNode = paNode.getRightSon();
            do {
                temp = paNode;
                if (paNode != null) {
                    paNode = paNode.getLeftSon();
                }

            } while (paNode != null);
            return temp;
        }
    }

    public Node maxRightonLeft(Node paNode) {
        //System.out.println("pouzivam RoL");
        if (this.amount == 0) {
            return null;
        }
        Node temp;
        if (paNode == null) {
            return null;
        } else {
            paNode = paNode.getLeftSon();
            do {
                temp = paNode;
                if (paNode != null) {
                    paNode = paNode.getRightSon();
                }

            } while (paNode != null);
            return temp;
        }
    }

    public boolean delete(Key paKey) {
        if (this.amount == 0) {
            return false;
        }
        Node delete = this.find(paKey);

        if (delete == null) {
            return false;
        } else {
            this.amount--;
            //ak mazem posledny prvok
            //System.out.println("maazem prvok: "+delete.getKey().getKey());
            if (this.amount == 0) {
                //this.amount--;
                this.root = null;
                return true;
            }
            //mazem list
            if (delete.getLeftSon() == null && delete.getRightSon() == null && delete.getFather() != null) {
                //if (delete.getFather().getKey() > delete.getKey()) {
                
                if (delete.getFather().getKey().compareTo( delete.getKey().getKey())<0) {
                    Node father = delete.getFather();
                    delete.getFather().setLeftSon(null);
                    father.compensate();
                }
                //if (delete.getFather().getKey() < delete.getKey()) {
                if (delete.getFather().getKey().compareTo( delete.getKey().getKey())>0) {
                    //delete.getFather().setPravaVyska(0);
                    Node father = delete.getFather();
                    delete.getFather().setRightSon(null);
                    father.compensate();
                    //delete.getFather().compensate();
                }

                Node temp3 = delete.getFather();
                Node saved = temp3;
                do {
                    //System.out.println("vyvazujem: "+temp3.getKey());
                    temp3.compensate();
                    if (temp3.getEquanimity() == 2 && temp3.getRightSon() == saved && saved.getEquanimity() == -1) {
                        //System.out.println("PL tocim okolo "+temp3.getKey());
                        this.rightLeftRotation(temp3);
                    }
                    if (temp3.getEquanimity() == -2 && temp3.getLeftSon() == saved && saved.getEquanimity() == 1) {
                        //System.out.println("LP tocim okolo "+temp3.getKey());
                        this.leftRightRotation(temp3);
                    }

                    if (temp3.getEquanimity() == 2) {
                        //System.out.println("vyvazenost je 2, rotujem okolo"+temp3.getKey());                    
                        this.leftRotation(temp3);
                    }

                    if (temp3.getEquanimity() == -2) {
                        //System.out.println("vyvazenost je -2, rotujem okolo"+temp3.getKey());                   
                        this.rightRotation(temp3);
                    }
                    if (temp3.getFather() != null) //System.out.println("**************"+temp3.getFather().getEquanimity());
                    {
                        saved = temp3;
                    }
                    temp3 = temp3.getFather();
                } while (temp3 != null);
                return true;
            }

            Node temp;
            //ktory vyuzijem
            if (delete.getRightSon() != null) {
                temp = this.maxLeftonRight(delete);
            } else {
                temp = this.maxRightonLeft(delete);
            }
            //System.out.println("nahradazam ho prvkom "+temp.getKey());
            // nastavujem otca noveho na null
            //if (temp.getFather().getKey() > temp.getKey()) {
            if (temp.getFather().getKey().compareTo(temp.getKey().getKey()) >0 ) {
                //System.out.println("nastavujem otcovi  deletnuteho "+temp.getFather().getKey()  +" laveho syna na null");
                Node father = temp.getFather();
                temp.getFather().setLeftSon(null);
                father.compensate();
            }
            //if (temp.getFather().getKey() < temp.getKey()) {
            if (temp.getFather().getKey().compareTo(temp.getKey().getKey()) <0 ) {
                //System.out.println("nastavujem otcovi  noveho "+temp.getFather().getKey()  +" praveho syna na null"); 
                Node father = temp.getFather();
                temp.getFather().setRightSon(null);
                father.compensate();
            }

            //nastavim otca synovi noveho, ktoreho som vynal
            if (temp.getLeftSon() != null) {
                temp.getLeftSon().setFather(temp.getFather());
            }
            if (temp.getRightSon() != null) {
                temp.getRightSon().setFather(temp.getFather());
            }

            //nastavim synovi noveho prvku otca, ktoreho mal predty novy prvok
            if (temp.getLeftSon() == null && temp.getRightSon() != null) {
                //if (temp.getFather().getKey() > temp.getKey()) {
                if (temp.getFather().getKey().compareTo(temp.getKey().getKey()) >0 ) {
                    temp.getFather().setLeftSon(temp.getRightSon());
                }
                //if (temp.getFather().getKey() < temp.getKey()) {
                if (temp.getFather().getKey().compareTo(temp.getKey().getKey()) <0 ) {
                    temp.getFather().setRightSon(temp.getRightSon());
                }
            }
            if (temp.getRightSon() == null && temp.getLeftSon() != null) {
                //if (temp.getFather().getKey() > temp.getKey()) {
                if (temp.getFather().getKey().compareTo(temp.getKey().getKey()) >0 ) {
                    temp.getFather().setLeftSon(temp.getLeftSon());
                }
                //if (temp.getFather().getKey() < temp.getKey()) {
                if (temp.getFather().getKey().compareTo(temp.getKey().getKey()) <0 ) {
                    temp.getFather().setRightSon(temp.getLeftSon());
                }
            }
            //ak je otec deletnuteho null, nastavim na root na noveho a novemu otca na null
            if (delete.getFather() == null) {
                this.root = temp;
                temp.setFather(null);
            } else {//ak nie je null, nastavim otcovi deletnuteho syna(noveho) a nastavim novemu otca
                //if (delete.getFather().getKey() > delete.getKey()) {
                if (delete.getFather().getKey().compareTo(delete.getKey().getKey()) >0 ) {
                    delete.getFather().setLeftSon(temp);
                }
                //if (delete.getFather().getKey() < delete.getKey()) {
                if (delete.getFather().getKey().compareTo(delete.getKey().getKey()) <0 ) {
                    delete.getFather().setRightSon(temp);
                }
                temp.setFather(delete.getFather());
            }
            //nastavim otca synom deletnuteho na noveho
            if (delete.getLeftSon() != null) {
                delete.getLeftSon().setFather(temp);
            }
            if (delete.getRightSon() != null) {
                delete.getRightSon().setFather(temp);
            }

            //nastavit novemu synov deletnuteho
            if (delete.getLeftSon() != temp) {
                temp.setLeftSon(delete.getLeftSon());
            } else {
                temp.setLeftSon(null);
            }
            if (delete.getRightSon() != temp) {
                temp.setRightSon(delete.getRightSon());
            } else {
                temp.setRightSon(null);
            }
            temp.compensate();

            Node temp2 = temp;
            Node saved = temp2;
            if (temp2 != null) {
                do {
                    temp2.compensate();
                    //System.out.println("vyvazujem: "+temp2.getKey());
                    if (temp2.getEquanimity() == 2 && temp2.getRightSon() == saved && saved.getEquanimity() == -1) {
                        //System.out.println("PL tocim okolo "+temp.getKey());
                        this.rightLeftRotation(temp2);
                    }
                    if (temp2.getEquanimity() == -2 && temp2.getLeftSon() == saved && saved.getEquanimity() == 1) {
                        //System.out.println("LP tocim okolo "+temp.getKey());
                        this.leftRightRotation(temp2);
                    }

                    if (temp2.getEquanimity() == 2) {
                        //System.out.println("vyvazenost je*** 2, --rotujem okolo"+temp2.getKey()); 
                        this.leftRotation(temp2);
                    }

                    if (temp2.getEquanimity() == -2) {
                        //System.out.println("vyvazenost je*** -2, --rotujem okolo"+temp2.getKey());                   
                        this.rightRotation(temp2);
                    }

                    saved = temp2;
                    temp2 = temp2.getFather();
                } while (temp2 != null);
            }

            delete.setFather(null);
            delete.setLeftSon(null);
            delete.setRightSon(null);
            return true;
        }
    }

   
    public void levelOrder() {
        Queue q = new LinkedList();
        if (root != null);
        q.add(root);

        while (!q.isEmpty() && this.amount > 0) {

            Node temp = (Node) q.remove();
            System.out.print(temp.getKey().getKey() + " ");
            if (temp.getLeftSon() != null) {
                q.add(temp.getLeftSon());
            }
            if (temp.getRightSon() != null) {
                q.add(temp.getRightSon());
            }
        }
    }

    public String InOrder(Node actuall) {
        String temp="";
        if (actuall == null) {
            return temp;
        }
        temp=temp+InOrder(actuall.getLeftSon());
        temp=temp+actuall.toString()+"\n";
        System.out.println(actuall.getKey().getKey());
        temp=temp+InOrder(actuall.getRightSon());
        
        return temp;
    }

    public Node find(Key paKey) {
        if (this.amount == 0) {
            return null;
        }
        return this.getRoot().find(paKey);
      //return null;
    }

    public void leftRotation(Node nodeAround) {
        //System.out.println("Tocim vlavo nodeAround "+nodeAround.getKey());
        //nastavenie otca nodeAround ktoreho sa tocim 
        if (nodeAround != this.getRoot()) {
            //if (nodeAround.getFather().getKey() > nodeAround.getKey()) {
            if (nodeAround.getFather().getKey().compareTo(nodeAround.getKey().getKey()) > 0) {
                nodeAround.getFather().setLeftSon(nodeAround.getRightSon());
            } else {
                nodeAround.getFather().setRightSon(nodeAround.getRightSon());
            }
            //nastavenie novemu otca
            nodeAround.getRightSon().setFather(nodeAround.getFather());
        } else {
            this.root = nodeAround.getRightSon();
            nodeAround.getRightSon().setFather(null);
        }
        //nastavenie staremu otca na noveho
        nodeAround.setFather(nodeAround.getRightSon());
        //nastavenie staremu praveho syna
        nodeAround.setRightSon(nodeAround.getRightSon().getLeftSon());
        //nastavenie novemu laveho syna
        if (nodeAround.getFather().getLeftSon() != null) {
            nodeAround.getFather().getLeftSon().setFather(nodeAround);
        }
        nodeAround.getFather().setLeftSon(nodeAround);

        nodeAround.compensate();
        nodeAround.getFather().compensate();
    }

    public void rightRotation(Node nodeAround) {
        //System.out.println("Tocim vpravo nodeAround "+nodeAround.getKey());
        //nastavenie otca nodeAround ktoreho sa tocim 
        //System.out.println("prava otocka nodeAround "+nodeAround.getKey());
        if (nodeAround != this.getRoot()) {
            if (nodeAround.getFather().getKey().compareTo(nodeAround.getKey().getKey()) > 0) {
                nodeAround.getFather().setLeftSon(nodeAround.getLeftSon());
            } else {
                nodeAround.getFather().setRightSon(nodeAround.getLeftSon());
            }
            //nastavenie novemu otca
            nodeAround.getLeftSon().setFather(nodeAround.getFather());
        } else {
            this.root = nodeAround.getLeftSon();
            nodeAround.getLeftSon().setFather(null);
        }
        //nastavenie staremu otca na noveho
        nodeAround.setFather(nodeAround.getLeftSon());
        //nastavenie staremu praveho syna
        nodeAround.setLeftSon(nodeAround.getLeftSon().getRightSon());
        //nastavenie novemu laveho syna
        if (nodeAround.getFather().getRightSon() != null) {
            nodeAround.getFather().getRightSon().setFather(nodeAround);
        }
        nodeAround.getFather().setRightSon(nodeAround);

        //treba este vyvazit
        nodeAround.compensate();
        nodeAround.getFather().compensate();
    }

    public void rightLeftRotation(Node temp) {
        this.rightRotation(temp.getRightSon());
        this.leftRotation(temp);
    }

    public void leftRightRotation(Node temp) {
        this.leftRotation(temp.getLeftSon());
        this.rightRotation(temp);
    }

    /**
     * @return the root
     */
    public Node getRoot() {
        return (Node) root;
    }

    /**
     * @return the amount
     */
   
    public int getPocet() {
        return amount;
    }

 

}
