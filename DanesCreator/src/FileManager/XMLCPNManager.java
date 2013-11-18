/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FileManager;

import Core.Arc;
import Core.PetriNet;
import Core.Place;
import Core.Resource;
import Core.Transition;
import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Michal
 */
public class XMLCPNManager {

    private DocumentBuilderFactory docFactory;
    private DocumentBuilder docBuilder;
    private Document doc;
    private ArrayList<Integer> placeIDs;
    private ArrayList<Integer> tranIDs;
    private int minX;
    private int minY;

    public XMLCPNManager() {

        minX = Integer.MAX_VALUE;
        minY = Integer.MAX_VALUE;
        placeIDs = new ArrayList<Integer>();
        tranIDs = new ArrayList<Integer>();
        try {
            docFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docFactory.newDocumentBuilder();

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLPetriManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public PetriNet getPetriNetFromCPN(File inputFile) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            String petriNetName = inputFile.getName().substring(inputFile.getName().length() - 3);
            PetriNet pn = new PetriNet(petriNetName);

            //this.getResourcesFromCPN(doc, pn);
            this.getPlacesFromCPN(doc, pn);
            this.getTransitionsFromCPN(doc, pn);
            this.getArcsFromCPN(doc, pn);
            if (minX < 0 || minY < 0) {
                reMoveElements(pn, Math.abs(minX), Math.abs(minY));
            }


            for (int k = 0; k < pn.getListOfArcs().size(); k++) {
                Arc a = pn.getListOfArcs().get(k);

                System.out.println("Arc: " + a.getName());
                for (int l = 0; l < a.getBendPoints().size(); l++) {
                    System.out.println(a.getBendPoints().get(l));
                }
                System.out.println();

            }

            return pn;
        } catch (Exception ex) {
            Logger.getLogger(XMLPetriManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void reMoveElements(PetriNet pn, int x, int y) {
        for (Place p : pn.getListOfPlaces()) {
            p.setX(p.getX() + x + 30);
            p.setY(p.getY() + y + 30);
        }
        for (Transition t : pn.getListOfTransitions()) {
            t.setX(t.getX() + x + 30);
            t.setY(t.getY() + y + 30);
        }
        for (Resource r : pn.getListOfResources()) {
            r.setX(r.getX() + x + 30);
            r.setY(r.getY() + y + 30);
        }
        for (Arc a : pn.getListOfArcs()) {
            for (Point p : a.getBendPoints()) {
                p.setLocation(p.x + x + 30, p.y + y + 30);
            }
        }


    }

    public void getPlacesFromCPN(Document doc, PetriNet pn) {
        NodeList placeList = doc.getElementsByTagName("place");
        for (int i = 0; i < placeList.getLength(); i++) {
            Node nNode = placeList.item(i);

            //System.out.println(nNode.getFirstChild().);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                int id = Integer.parseInt(eElement.getAttribute("id").substring(2));
                int x, y, width, height;
                String name;

                x = (int) Double.parseDouble(((Element) eElement.getElementsByTagName("posattr").item(0)).getAttribute("x"));
                y = (int) Double.parseDouble(((Element) eElement.getElementsByTagName("posattr").item(0)).getAttribute("y"));
                width = (int) Double.parseDouble(((Element) eElement.getElementsByTagName("ellipse").item(0)).getAttribute("w"));
                height = (int) Double.parseDouble(((Element) eElement.getElementsByTagName("ellipse").item(0)).getAttribute("h"));
                name = eElement.getElementsByTagName("text").item(0).getTextContent();

                if (x < minX) {
                    minX = x;
                }
                if (y < minY) {
                    minY = y;
                }

                Place p = new Place(name);
                p.setX(x);
                p.setY(y);
                p.setWidth(width);
                p.setHeight(height);
                p.setColor(new Color(10, 10, 10));
                p.setColor2(new Color(255, 255, 255));
                p.setStart(false);
                p.setEnd(false);
                p.setFontSize(16);
                placeIDs.add(id);
                pn.addPlace(p);
            }
        }

    }

    public void getTransitionsFromCPN(Document doc, PetriNet pn) {
        NodeList transList = doc.getElementsByTagName("trans");
        for (int i = 0; i < transList.getLength(); i++) {
            Node nNode = transList.item(i);

            //System.out.println(nNode.getFirstChild().);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                int id = Integer.parseInt(eElement.getAttribute("id").substring(2));
                int x, y, width, height;
                String name;

                x = (int) Double.parseDouble(((Element) eElement.getElementsByTagName("posattr").item(0)).getAttribute("x"));
                y = (int) Double.parseDouble(((Element) eElement.getElementsByTagName("posattr").item(0)).getAttribute("y"));
                width = (int) Double.parseDouble(((Element) eElement.getElementsByTagName("box").item(0)).getAttribute("w"));
                height = (int) Double.parseDouble(((Element) eElement.getElementsByTagName("box").item(0)).getAttribute("h"));
                name = eElement.getElementsByTagName("text").item(0).getTextContent();

                if (x < minX) {
                    minX = x;
                }
                if (y < minY) {
                    minY = y;
                }

                Transition t = new Transition(name);
                t.setX(x);
                t.setY(y);
                t.setWidth(width);
                t.setHeight(height);
                t.setColor(new Color(10, 10, 10));
                t.setColor2(new Color(255, 255, 255));
                t.setFontSize(16);
                tranIDs.add(id);
                pn.addTransition(t);
            }
        }
    }

    public void getArcsFromCPN(Document doc, PetriNet pn) {
        NodeList arcList = doc.getElementsByTagName("arc");
        for (int i = 0; i < arcList.getLength(); i++) {
            Node nNode = arcList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                int x, y, width, height;
                String name;

                x = (int) Double.parseDouble(((Element) eElement.getElementsByTagName("posattr").item(0)).getAttribute("x"));
                y = (int) Double.parseDouble(((Element) eElement.getElementsByTagName("posattr").item(0)).getAttribute("y"));
                name = eElement.getElementsByTagName("text").item(0).getTextContent();

                String arcOrientation = eElement.getAttribute("orientation");
                int id1 = Integer.parseInt(((Element) eElement.getElementsByTagName("transend").item(0)).getAttribute("idref").substring(2));
                int id2 = Integer.parseInt(((Element) eElement.getElementsByTagName("placeend").item(0)).getAttribute("idref").substring(2));
                int index = 0;

                Place place = getPlace(id2, pn);
                Transition trans = getTrans(id1, pn);

                if (place == null || trans == null) {
                    System.out.println("CHYBA!!!!!");
                    return;
                }

                Arc a;
                if (arcOrientation.equals("PtoT")) {
                    a = new Arc("Arc" + (i + 1), place, trans);
                } else {
                    a = new Arc("Arc" + (i + 1), trans, place);
                }

                a.setX(x);
                a.setY(y);
                a.setColor(new Color(10, 10, 10));
                a.setColor2(new Color(255, 255, 255));
                a.setFontSize(16);

                NodeList bendNodes = eElement.getElementsByTagName("bendpoint");

                for (int k = 0; k < bendNodes.getLength(); k++) {
                    Node bendNode = bendNodes.item(k);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element bendElement = (Element) bendNode;
                        int bendX = (int) Double.parseDouble(((Element) bendElement.getElementsByTagName("posattr").item(0)).getAttribute("x"));
                        int bendY = (int) Double.parseDouble(((Element) bendElement.getElementsByTagName("posattr").item(0)).getAttribute("y"));

                        if (bendX < minX) {
                            minX = bendX;
                        }
                        if (bendY < minY) {
                            minY = bendY;
                        }

                        a.addBendPointSimple(new Point(bendX, bendY));
                        
                    }
                }
                pn.addArc(a);
            }
        }


    }

    private Place getPlace(int id, PetriNet pn) {
        int index = -1;
        for (int i = 0; i < placeIDs.size(); i++) {
            if (placeIDs.get(i) == id) {
                index = i;
                break;
            }

        }
        if (index == -1) {
            return null;
        }
        return pn.getListOfPlaces().get(index);

    }

    private Transition getTrans(int id, PetriNet pn) {
        int index = -1;
        for (int i = 0; i < tranIDs.size(); i++) {
            if (tranIDs.get(i) == id) {
                index = i;
                break;
            }

        }
        if (index == -1) {
            return null;
        }
        return pn.getListOfTransitions().get(index);

    }
}
