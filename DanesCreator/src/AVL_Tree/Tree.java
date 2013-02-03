/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AVL_Tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Atarin
 */
public class Tree {

    protected  Node root;
    protected  int pocet;
    public Tree(Node root) {
        this.root = root;
        pocet = 1;
        if(root==null) pocet=0;
    }

    public boolean addNode(Node node) {
        if(this.pocet==0 && this.root==null){
            pocet++;
            root=node;
            root.compensate();
            return true;
        }
        
        if (this.find(node.getKey()) != null) {
            return false;
        }
        if (this.getRoot().setSon(node)) {
            //System.out.println("vkladam: "+node.getKey().getKey());
            this.pocet++;
            node.setLeftHigh(0);
            node.setRightHigh(0);

            //compensateit vsetky prvky az po koren
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
                    //System.out.println("compensateenost je 2, rotujem okolo"+temp.getKey().getKey());                    
                    this.leftRotation(temp);
                    break;
                }

                if (temp.getEquanimity() == -2) {
                    //System.out.println("compensateenost je -2, rotujem okolo"+temp.getKey().getKey());                   
                    this.rightRotation(temp);
                    //System.out.println("compensateenost je -2");
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
        if (this.pocet == 0) {
            return null;
        }
        Node pom;
        if (paNode == null) {
            return null;
        } else {
            paNode = paNode.getRightSon();
            do {
                pom = paNode;
                if (paNode != null) {
                    paNode = paNode.getLeftSon();
                }

            } while (paNode != null);
            return pom;
        }
    }

    public Node maxRightonLeft(Node paNode) {
        //System.out.println("pouzivam RoL");
        if (this.pocet == 0) {
            return null;
        }
        Node pom;
        if (paNode == null) {
            return null;
        } else {
            paNode = paNode.getLeftSon();
            do {
                pom = paNode;
                if (paNode != null) {
                    paNode = paNode.getRightSon();
                }

            } while (paNode != null);
            return pom;
        }
    }

    public boolean delete(Key paKey) {
        if (this.pocet == 0) {
            return false;
        }
        Node delete = this.find(paKey);

        if (delete == null) {
            return false;
        } else {
            this.pocet--;
            //ak mazem posledny prvok
            //System.out.println("maazem prvok: "+delete.getKey().getKey());
            if (this.pocet == 0) {
                //this.pocet--;
                this.root = null;
                return true;
            }
            //mazem list
            if (delete.getLeftSon() == null && delete.getRightSon() == null && delete.getFather() != null) {
                //if (delete.getFather().getKey() > delete.getKey()) {
                
                if (delete.getFather().getKey().compareTo( delete.getKey().getKey())>0) {
                    Node father = delete.getFather();
                    delete.getFather().setLeftSon(null);
                    father.compensate();
                }
                //if (delete.getFather().getKey() < delete.getKey()) {
                if (delete.getFather().getKey().compareTo( delete.getKey().getKey())<0) {
                    //delete.getFather().setRightHigh(0);
                    Node father = delete.getFather();
                    delete.getFather().setRightSon(null);
                    father.compensate();
                    //delete.getFather().compensate();
                }

                Node temp3 = delete.getFather();
                Node saved = temp3;
                do {
                    //System.out.println("compensateujem: "+temp3.getKey());
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
                        //System.out.println("compensateenost je 2, rotujem okolo"+temp3.getKey());                    
                        this.leftRotation(temp3);
                    }

                    if (temp3.getEquanimity() == -2) {
                        //System.out.println("compensateenost je -2, rotujem okolo"+temp3.getKey());                   
                        this.rightRotation(temp3);
                    }
                    if (temp3.getFather() != null) //System.out.println("**************"+temp3.getFather().getEquanimity());
                    {
                        saved = temp3;
                    }
                    temp3 = temp3.getFather();
                } while (temp3 != null);
                System.out.println("zmazal som list");
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
                    //System.out.println("compensateujem: "+temp2.getKey());
                    if (temp2.getEquanimity() == 2 && temp2.getRightSon() == saved && saved.getEquanimity() == -1) {
                        //System.out.println("PL tocim okolo "+temp.getKey());
                        this.rightLeftRotation(temp2);
                    }
                    if (temp2.getEquanimity() == -2 && temp2.getLeftSon() == saved && saved.getEquanimity() == 1) {
                        //System.out.println("LP tocim okolo "+temp.getKey());
                        this.leftRightRotation(temp2);
                    }

                    if (temp2.getEquanimity() == 2) {
                        //System.out.println("compensateenost je*** 2, --rotujem okolo"+temp2.getKey()); 
                        this.leftRotation(temp2);
                    }

                    if (temp2.getEquanimity() == -2) {
                        //System.out.println("compensateenost je*** -2, --rotujem okolo"+temp2.getKey());                   
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

        while (!q.isEmpty() && this.pocet > 0) {

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
    public ArrayList<Node> inOrder(){
        return(InOrder(root));
    }
    

    public ArrayList<Node> InOrder(Node actuall) {
        
        ArrayList<Node> ret=new ArrayList<>();
        
        //a.addAll(b);
        
        String temp="";
        StringBuilder sb=new StringBuilder(temp);
        if (actuall == null) {
            //temp=temp+"\n"+this.pocet;
            //return temp;
            return ret;
        }
        ret.addAll(InOrder(actuall.getLeftSon()));
        //sb.append(InOrder(actuall.getLeftSon()));
        //temp=temp+InOrder(actuall.getLeftSon());
        //sb.append(actuall.toString()).append("\n");
        ret.add(actuall);
        //temp=temp+actuall.toString()+"\n";
        //System.out.println(actuall.getKey().getKey());
        sb.append(InOrder(actuall.getRightSon()));
        ret.addAll(InOrder(actuall.getRightSon()));
        //temp=temp+InOrder(actuall.getRightSon());
        //temp=temp+"\n"+this.pocet;
        return ret;
        //return temp;
    }

    public Node find(Key paKey) {
        if (this.pocet == 0) {
            return null;
        }
        return this.getRoot().find(paKey);
      //return null;
    }

    public void leftRotation(Node okolo) {
        //System.out.println("Tocim vlavo okolo "+okolo.getKey());
        //nastavenie otca okolo ktoreho sa tocim 
        if (okolo != this.getRoot()) {
            //if (okolo.getFather().getKey() > okolo.getKey()) {
            if (okolo.getFather().getKey().compareTo(okolo.getKey().getKey()) > 0) {
                okolo.getFather().setLeftSon(okolo.getRightSon());
            } else {
                okolo.getFather().setRightSon(okolo.getRightSon());
            }
            //nastavenie novemu otca
            okolo.getRightSon().setFather(okolo.getFather());
        } else {
            this.root = okolo.getRightSon();
            okolo.getRightSon().setFather(null);
        }
        //nastavenie staremu otca na noveho
        okolo.setFather(okolo.getRightSon());
        //nastavenie staremu praveho syna
        okolo.setRightSon(okolo.getRightSon().getLeftSon());
        //nastavenie novemu laveho syna
        if (okolo.getFather().getLeftSon() != null) {
            okolo.getFather().getLeftSon().setFather(okolo);
        }
        okolo.getFather().setLeftSon(okolo);

        okolo.compensate();
        okolo.getFather().compensate();
    }

    public void rightRotation(Node okolo) {
        //System.out.println("Tocim vpravo okolo "+okolo.getKey());
        //nastavenie otca okolo ktoreho sa tocim 
        //System.out.println("prava otocka okolo "+okolo.getKey());
        if (okolo != this.getRoot()) {
            if (okolo.getFather().getKey().compareTo(okolo.getKey().getKey()) > 0) {
                okolo.getFather().setLeftSon(okolo.getLeftSon());
            } else {
                okolo.getFather().setRightSon(okolo.getLeftSon());
            }
            //nastavenie novemu otca
            okolo.getLeftSon().setFather(okolo.getFather());
        } else {
            this.root = okolo.getLeftSon();
            okolo.getLeftSon().setFather(null);
        }
        //nastavenie staremu otca na noveho
        okolo.setFather(okolo.getLeftSon());
        //nastavenie staremu praveho syna
        okolo.setLeftSon(okolo.getLeftSon().getRightSon());
        //nastavenie novemu laveho syna
        if (okolo.getFather().getRightSon() != null) {
            okolo.getFather().getRightSon().setFather(okolo);
        }
        okolo.getFather().setRightSon(okolo);

        //treba este compensateit
        okolo.compensate();
        okolo.getFather().compensate();
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
     * @return the pocet
     */
   
    public int getPocet() {
        return pocet;
    }

    public void addTree(Tree tr){
        Queue q = new LinkedList();
        if (tr.getRoot() != null)
        q.add(tr.getRoot());

        while (!q.isEmpty() && tr.getPocet() > 0) {

            Node temp = (Node) q.remove();
            
            System.out.print(temp.getKey().getKey() + " ");
            
            if (temp.getLeftSon() != null) {
                q.add(temp.getLeftSon());
            }
            if (temp.getRightSon() != null) {
                q.add(temp.getRightSon());
            }
            temp.setLeftSon(null);
            temp.setRightSon(null);
            temp.setFather(null);
            this.addNode(temp);
            
        }
        
    }
 

}
