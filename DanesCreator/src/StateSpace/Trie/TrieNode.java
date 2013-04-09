/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StateSpace.Trie;

import StateSpace.State;
import java.util.Collection;
import java.util.LinkedList;

/**
 * @author Atarin
 */
public class TrieNode {

    char content;
    boolean marker;
    Collection<TrieNode> child;
    State data;

    public TrieNode(char c) {
        child = new LinkedList<TrieNode>();
        marker = false;
        content = c;
    }

    public TrieNode subNode(char c) {
        if (child != null) {
            for (TrieNode eachChild : child) {
                if (eachChild.content == c) {
                    return eachChild;
                }
            }
        }
        return null;
    }
}
