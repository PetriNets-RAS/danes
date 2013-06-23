/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StateSpace.trie;

import StateSpace.State;
import StateSpace.Trie.Trie;

/**
 *
 * @author Atarin
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Trie t=new StateSpace.Trie.Trie();
        
        t.insert("polko", new State(null, 0, null,null));
        t.insert("ivan", new State(null, 1, null,null));
        t.insert("jakub", new State(null, 2, null,null));
        t.insert("maly", new State(null, 3, null,null));
        t.insert("mala", new State(null, 4, null,null));
        t.insert("mala", new State(null, 9, null,null));
        
        t.levelOrder();
        
    }
}
