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

    private char content;
    private boolean marker;
    private Collection<TrieNode> child;
    private State data;
    private String name;

    public TrieNode(char c) {
        child = new LinkedList<TrieNode>();
        marker = false;
        content = c;
    }

    public TrieNode subNode(char c) {
        if (getChild() != null) {
            for (TrieNode eachChild : getChild()) {
                if (eachChild.getContent() == c) {
                    return eachChild;
                }
            }
        }
        return null;
    }

    /**
     * @return the content
     */
    public char getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(char content) {
        this.content = content;
    }

    /**
     * @return the marker
     */
    public boolean isMarker() {
        return marker;
    }

    /**
     * @param marker the marker to set
     */
    public void setMarker(boolean marker) {
        this.marker = marker;
    }

    /**
     * @return the child
     */
    public Collection<TrieNode> getChild() {
        return child;
    }

    /**
     * @param child the child to set
     */
    public void setChild(Collection<TrieNode> child) {
        this.child = child;
    }

    /**
     * @return the data
     */
    public State getData() {
        return data;
    }

    /**
     * @param data the data to seta
     */
    public void setData(State data) {
        this.data=new State(data.getPlaceMarkings(), data.getLastMarkedItem(), data.getParent());
        //this.data = data;
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
}
