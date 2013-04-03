/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StateSpace.Trie;

import java.util.LinkedList;
import java.util.Queue;




public class Trie {

    private Node root;

    public Trie() {
        root = new Node(' ');
    }

    public boolean insert(String s, int data) {
        Node current = root;
        if (s.length() == 0) //For an empty character
        {
            if (!current.marker) {
                current.marker = true;
            } else {
                return false;
            }

        }
        for (int i = 0; i < s.length(); i++) {
            Node child = current.subNode(s.charAt(i));
            if (child != null) {
                current = child;
            } else {
                current.child.add(new Node(s.charAt(i)));
                current = current.subNode(s.charAt(i));
            }
            // Set marker to indicate end of the word
            if (i == s.length() - 1) {
                if (!current.marker) {
                    current.marker = true;
                    current.data = data;
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public Node search(String s) {
        Node current = root;
        while (current != null) {
            for (int i = 0; i < s.length(); i++) {
                if (current.subNode(s.charAt(i)) == null) {
                    return null;
                    //return false;
                } else {
                    current = current.subNode(s.charAt(i));
                }
            }
            /* 
             * This means that a string exists, but make sure its
             * a word by checking its 'marker' flag
             */
            if (current.marker == true) {
                //return true;
                return current;
            } else {
                //return false;
                return null;
            }
        }
        return null;
    }
    
     public void levelOrder() {
        Queue q = new LinkedList();
        if (root != null);
        q.add(root);

        while (!q.isEmpty()) {

            Node temp = (Node) q.remove();
            if (temp.marker) {
                System.out.print(temp.data + " ");
            }
            for (Node eachChild : temp.child) {
                q.add(eachChild);
            }
        }
    }
    
}
