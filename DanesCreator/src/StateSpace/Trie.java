/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StateSpace;

import java.util.ArrayList;
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
                    current.setIDnode(count);
                    //data.setID(count);
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

    public ArrayList<State> levelOrder() {
        ArrayList<State> result=new ArrayList<State>();
        //System.out.println("Count:"+ getCount()+" ");
        //System.out.println("Lever order :");
        Queue q = new LinkedList();
        if (root != null);
        q.add(root);

        while (!q.isEmpty()) {
            TrieNode temp = (TrieNode) q.remove();
            if (temp.getState()!=null) {
                result.add(temp.getState());
                //System.out.println(temp.getState().toString());
                
                //String out="ID: "+(temp.getIDnode()+1)+ " "+ temp.getName()+ "Parent ";
                //System.out.print("ID: "+(temp.getIDnode())+ " "+ temp.getName()+ "Parent ");
//                if(temp.getState().getParent()!=null){
//                System.out.println((search(temp.getState().getParent().getKey()).getIDnode()));
//                }else{
//                    System.out.println("null");
//                }
            }
            for (TrieNode eachChild : temp.getChild()) {
                q.add(eachChild);
            }
        }
        return result;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }
}
