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
import java.io.IOException;
import java.io.StringReader;
import java.text.NumberFormat;
import java.text.ParseException;
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
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Michal
 */
public class XMLCPNManager {

    private DocumentBuilderFactory docFactory;
    private DocumentBuilder docBuilder;
    private Document doc;
    private ArrayList<NotUsedPlaceInfo> placesInfo;
    private ArrayList<AdditionalArcInfo> arcsInfo;
    private ArrayList<Integer> tranIDs;
    private int minX;
    private int minY;
    private int maxX;
    private int maxY;

    public XMLCPNManager() {

        minX = Integer.MAX_VALUE;
        minY = Integer.MAX_VALUE;
        maxX = Integer.MIN_VALUE;
        maxY = Integer.MIN_VALUE;
        placesInfo = new ArrayList<NotUsedPlaceInfo>();
        arcsInfo = new ArrayList<AdditionalArcInfo>();
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

            dBuilder.setEntityResolver(new EntityResolver() {
                @Override
                public InputSource resolveEntity(String publicId,
                        String systemId) throws SAXException, IOException {
                    return new InputSource(new StringReader(""));
                }
            });


            doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            String petriNetName = inputFile.getName().substring(inputFile.getName().length() - 3);
            PetriNet pn = new PetriNet(petriNetName);

            //this.getResourcesFromCPN(doc, pn);
            this.getPlacesFromCPN(doc, pn);
            this.getTransitionsFromCPN(doc, pn);
            this.getArcsFromCPN(doc, pn);
            findResource(pn);
            /*
             for (int i = 0; i < pn.getListOfPlaces().size(); i++) {
             System.out.println(pn.getListOfPlaces().get(i).getMarkings());
             }
             */

            if (minX < 0 || minY < 0) {
                reMoveElements(pn, minX, maxY);
            }


            return pn;
        } catch (Exception ex) {
            Logger.getLogger(XMLPetriManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void findResource(PetriNet pn) {
        ArrayList<Integer> listOfPlaceTypes = new ArrayList<Integer>();
        ArrayList<Integer> listOfResourceTypes = new ArrayList<Integer>();
        String type1 = null;
        String type2 = null;

        for (int i = 0; i < placesInfo.size(); i++) {
            NotUsedPlaceInfo nupi = placesInfo.get(i);
            if (type1 == null) {
                type1 = nupi.getType();
            }
            if (type1 != null && type1.equals(nupi.getType())) {
                listOfPlaceTypes.add(i);
            }
            if (type1 != null && !type1.equals(nupi.getType()) && type2 == null) {
                type2 = nupi.getType();
            }
            if (type2 != null && type2.equals(nupi.getType())) {
                listOfResourceTypes.add(i);
            }
        }
        /*
         System.out.println("List of all");
         for (int i = 0; i < placesInfo.size(); i++) {
         System.out.println(i + " - " + placesInfo.get(i).getPlace().getName());
         }
         System.out.println("List of places");
         for (int i = 0; i < listOfPlaceTypes.size(); i++) {
         System.out.println(" - " + listOfPlaceTypes.get(i));
         }
         System.out.println("List of resources");
         for (int i = 0; i < listOfResourceTypes.size(); i++) {
         System.out.println(" + " + listOfResourceTypes.get(i));
         }
         */

        for (int index : listOfPlaceTypes) {
            setPlaceMarking(placesInfo.get(index));
        }
        if (type2 != null && "UNIT".equals(type2)) {
            for (int i = 0; i < listOfResourceTypes.size(); i++) {
                Resource r = new Resource(placesInfo.get(listOfResourceTypes.get(i)).getPlace().getName());
                r.setX(placesInfo.get(listOfResourceTypes.get(i)).getPlace().getX());
                r.setY(placesInfo.get(listOfResourceTypes.get(i)).getPlace().getY());
                r.setWidth(placesInfo.get(listOfResourceTypes.get(i)).getPlace().getWidth());
                r.setHeight(placesInfo.get(listOfResourceTypes.get(i)).getPlace().getHeight());
                r.setColor(placesInfo.get(listOfResourceTypes.get(i)).getPlace().getColor());
                r.setColor2(placesInfo.get(listOfResourceTypes.get(i)).getPlace().getColor2());
                r.setFontSize(16);
                try {
                    int count = ((Number) NumberFormat.getInstance().parse((placesInfo).get(listOfResourceTypes.get(i)).getInitmarking())).intValue();
                    r.setMarking(count);
                } catch (ParseException ex) {
                }
                for (Arc a : placesInfo.get(listOfResourceTypes.get(i)).getPlace().getListOfOutArcs()) {
                    a.setOutElement(r);
                    r.getListOfOutArcs().add(a);
                }
                for (Arc a : placesInfo.get(listOfResourceTypes.get(i)).getPlace().getListOfInArcs()) {
                    a.setInElement(r);
                    r.getListOfInArcs().add(a);
                }
                pn.getListOfPlaces().remove(placesInfo.get(listOfResourceTypes.get(i)).getPlace());
                pn.addResource(r);
            }
        } else if (type2 != null) {
            ArrayList<Resource> listOfAddedResources = new ArrayList<Resource>();
            NotUsedPlaceInfo nupi = placesInfo.get(listOfResourceTypes.get(0));
            String[] tokens = nupi.getInitmarking().split("\\+\\+");
            for (int i = 0; i < tokens.length; i++) {
                int count = 1;
                try {
                    count = ((Number) NumberFormat.getInstance().parse(tokens[i])).intValue();
                } catch (ParseException ex) {
                }
                //String[] markingPart = nupi.getInitmarking().split("`");
                String[] markingPart = tokens[i].split("`");
                String name = markingPart[1];
                Resource r = new Resource(nupi.getPlace().getName() + "_" + name);
                r.setX(nupi.getPlace().getX());
                r.setY(nupi.getPlace().getY() - i * 70);
                r.setWidth(nupi.getPlace().getWidth());
                r.setHeight(nupi.getPlace().getHeight());
                r.setColor(nupi.getPlace().getColor());
                r.setColor2(nupi.getPlace().getColor2());
                r.setMarking(count);

                for (Arc a : placesInfo.get(listOfResourceTypes.get(0)).getPlace().getListOfOutArcs()) {
                    for (AdditionalArcInfo aai : arcsInfo) {
                        if (aai.getArc() == a && aai.getText().equals(name)) {
                            a.setOutElement(r);
                            r.getListOfOutArcs().add(a);
                        }
                    }
                }
                for (Arc a : placesInfo.get(listOfResourceTypes.get(0)).getPlace().getListOfInArcs()) {
                    for (AdditionalArcInfo aai : arcsInfo) {
                        if (aai.getArc() == a && aai.getText().equals(name)) {
                            a.setInElement(r);
                            r.getListOfInArcs().add(a);
                        }
                    }
                }
                listOfAddedResources.add(r);


            }
            pn.getListOfPlaces().remove(nupi.getPlace());
            for (int i = 0; i < listOfAddedResources.size(); i++) {
                pn.addResource(listOfAddedResources.get(i));
            }
        }
    }

    public void setPlaceMarking(NotUsedPlaceInfo nupi) {
        Place p = nupi.getPlace();
        if (!"".equals(nupi.getInitmarking())) {
            String[] tokens = nupi.getInitmarking().split("\\+\\+");
            for (int i = 0; i < tokens.length; i++) {
                p.addMarking(i + 1);
            }
        }
    }

    public void reMoveElements(PetriNet pn, int x, int y) {
        for (Place p : pn.getListOfPlaces()) {
            p.setX(p.getX() + x + 30);
            p.setY(y - p.getY() + 30);
        }
        for (Transition t : pn.getListOfTransitions()) {
            t.setX(t.getX() + x + 30);
            t.setY(y - t.getY() + 30);
        }
        for (Resource r : pn.getListOfResources()) {
            r.setX(r.getX() + x + 30);
            r.setY(y - r.getY() + 30);
        }
        for (Arc a : pn.getListOfArcs()) {
            for (Point p : a.getBendPoints()) {
                p.setLocation(p.x + x + 30, y - p.y + 30);
            }
        }
    }

    public void getPlacesFromCPN(Document doc, PetriNet pn) {
        NodeList placeList = doc.getElementsByTagName("place");
        for (int i = 0; i < placeList.getLength(); i++) {
            Node nNode = placeList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                int id = Integer.parseInt(eElement.getAttribute("id").substring(2));
                int x, y, width, height, capacity;
                String name;
                capacity = 1;
                x = (int) Double.parseDouble(((Element) eElement.getElementsByTagName("posattr").item(0)).getAttribute("x"));
                y = (int) Double.parseDouble(((Element) eElement.getElementsByTagName("posattr").item(0)).getAttribute("y"));
                width = (int) Double.parseDouble(((Element) eElement.getElementsByTagName("ellipse").item(0)).getAttribute("w"));
                height = (int) Double.parseDouble(((Element) eElement.getElementsByTagName("ellipse").item(0)).getAttribute("h"));
                name = eElement.getElementsByTagName("text").item(0).getTextContent();

                String text = ((Element) eElement.getElementsByTagName("type").item(0)).getElementsByTagName("text").item(0).getTextContent();
                //System.out.println(text);
                String sign = ((Element) eElement.getElementsByTagName("initmark").item(0)).getElementsByTagName("text").item(0).getTextContent();
                //System.out.println(sign);

                if (x < minX) {
                    minX = x;
                }
                if (y < minY) {
                    minY = y;
                }
                if (x > maxX) {
                    maxX = x;
                }
                if (y > maxY) {
                    maxY = y;
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
                placesInfo.add(new NotUsedPlaceInfo(id, text, sign, p));
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
                if (x > maxX) {
                    maxX = x;
                }
                if (y > maxY) {
                    maxY = y;
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
                int x, y, width, height, capacity;
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

                String text = eElement.getElementsByTagName("text").item(0).getTextContent();
                try {
                    capacity = ((Number) NumberFormat.getInstance().parse(text)).intValue();
                    a.setCapacity(capacity);
                } catch (ParseException ex) {
                }

                if (text.length() >= 3) {

                    String[] markingPart = text.split("`");
                    String markingName = markingPart[1];
                    arcsInfo.add(new AdditionalArcInfo(a, markingName));
                }

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
                        if (x > maxX) {
                            maxX = x;
                        }
                        if (y > maxY) {
                            maxY = y;
                        }

                        if (arcOrientation.equals("PtoT")) {
                            a.getBendPoints().add(0, new Point(bendX,bendY));
                            
                        } else {
                            a.getBendPoints().add(new Point(bendX,bendY));
                        }
                    }
                }
                pn.addArc(a);
            }
        }


    }

    private Place getPlace(int id, PetriNet pn) {
        int index = -1;
        for (int i = 0; i < placesInfo.size(); i++) {
            if (placesInfo.get(i).getID() == id) {
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
