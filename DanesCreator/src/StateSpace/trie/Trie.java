/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StateSpace.Trie;

import StateSpace.State;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Atarin
 */
public class Trie {

    private TrieNode root;
    private int count;

    public Trie() {
        root = new TrieNode(' ');
        count=0;
    }

    public boolean insert(String s, State data) {
        TrieNode current = root;
        if (s.length() == 0) //For an empty character
        {
            if (!current.isMarker()) {
                current.setMarker(true);
            } else {
                return false;
            }

        }
        for (int i = 0; i < s.length(); i++) {
            TrieNode child = current.subNode(s.charAt(i));
            if (child != null) {
                current = child;
            } else {
                current.getChild().add(new TrieNode(s.charAt(i)));
                current = current.subNode(s.charAt(i));
            }
            // Set marker to indicate end of the word
            if (i == s.length() - 1) {
                if (!current.isMarker()) {
                    current.setMarker(true);
                    current.setData(data);
                    current.setName(s);
                    count++;
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public TrieNode search(String s) {
        TrieNode current = root;
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
            if (current.isMarker() == true) {
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
        System.out.println("Count:"+ getCount()+" ");
        System.out.println("Lever order :");
        Queue q = new LinkedList();
        if (root != null);
        q.add(root);

        while (!q.isEmpty()) {
            TrieNode temp = (TrieNode) q.remove();
            if (temp.getData()!=null) {
                System.out.println(temp.getData() + " PREDCHODCA: "+temp.getData().getParent());
                //System.out.println(temp.getName() + " ");
            }
            for (TrieNode eachChild : temp.getChild()) {
                q.add(eachChild);
            }
        }
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }
}
