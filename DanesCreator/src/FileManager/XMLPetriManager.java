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
import StateSpace.State;
import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Atarin
 */
public class XMLPetriManager {

    private DocumentBuilderFactory docFactory;
    private DocumentBuilder docBuilder;
    private Document doc;
    private ArrayList<String> resProf;
    private boolean cobaFile;

    public XMLPetriManager() {
        resProf = new ArrayList<String>();
        try {
            docFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docFactory.newDocumentBuilder();

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLPetriManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertProf(String rp) {
        if (!resProf.contains(rp)) {
            resProf.add(rp);
        };
    }

    public boolean createPetriXML(Core.Graph g, File outputFile, boolean coba) {
        try {
            cobaFile = coba;
            PetriNet pn = (PetriNet) g;
            doc = (Document) docBuilder.newDocument();
            Element rootElement = doc.createElement("process");
            doc.appendChild(rootElement);

            Element title = doc.createElement("title");
            title.appendChild(doc.createTextNode(pn.getName()));
            rootElement.appendChild(title);

            Element resources = this.getResourcesElement(doc, pn.getListOfResources());
            rootElement.appendChild(resources);

            Element places = this.getPlacesElement(pn.getListOfPlaces(), doc);
            Element transitions = this.getTransationsElement(pn.getListOfTransitions(), doc);
            Element edges = getEdgesElement(pn.getListOfArcs(), doc);
            rootElement.appendChild(places);
            rootElement.appendChild(transitions);
            rootElement.appendChild(edges);
            if (pn.getStates() != null) {
                Element states = getStatesElement(pn.getStates(), doc);
                rootElement.appendChild(states);
            }

            Element prof = getProfElement(this.resProf, doc);

            rootElement.appendChild(prof);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("encoding", "WINDOWS-1256");
            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(outputFile);
            transformer.transform(source, result);

            return true;
        } catch (TransformerException ex) {
            Logger.getLogger(XMLPetriManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public PetriNet getPetriNetFromXML(File inputFile, boolean fromCoBA) {
        try {
            cobaFile = fromCoBA;
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList titleList = doc.getElementsByTagName("title");
            String petriNetName = titleList.item(0).getTextContent();
            PetriNet pn = new PetriNet(petriNetName);

            this.getResourcesFromXML(doc, pn);
            this.getPlacesFromXML(doc, pn);
            this.getTransitionsFromXML(doc, pn);
            this.getArcsFromXML(doc, pn);

            return pn;
        } catch (Exception ex) {
            Logger.getLogger(XMLPetriManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private void getResourcesFromXML(Document doc, PetriNet pn) {

        NodeList resourcesList = doc.getElementsByTagName("resource");
        for (int i = 0; i < resourcesList.getLength(); i++) {
            Node nNode = resourcesList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                Resource res = new Resource(eElement.getAttribute("name"));
                int x = Integer.parseInt(eElement.getAttribute("x"));
                int y = Integer.parseInt(eElement.getAttribute("y"));
                if (cobaFile) {
                    x = (x * 60) + 20;
                    y = (y * 38) + 20;
                }

                int quantity = Integer.parseInt(eElement.getAttribute("quantity"));
                if (eElement.getAttribute("width") != "") {
                    res.setWidth(Integer.parseInt(eElement.getAttribute("width")));
                } else {
                    res.setWidth(53);
                }
                if (eElement.getAttribute("height") != "") {
                    res.setHeight(Integer.parseInt(eElement.getAttribute("height")));
                } else {
                    res.setHeight(38);
                }
                res.setNote(eElement.getAttribute("note"));
                if (eElement.getAttribute("fontSize") != "") {
                    res.setFontSize(Integer.parseInt(eElement.getAttribute("fontSize")));
                } else {
                    res.setFontSize(16);
                }
                if (eElement.getAttribute("red1") != "") {
                    res.setColor(new Color(Integer.parseInt(eElement.getAttribute("red1")),
                            Integer.parseInt(eElement.getAttribute("green1")),
                            Integer.parseInt(eElement.getAttribute("blue1"))));
                } else {
                    res.setColor(new Color(10, 10, 10));
                }
                if (eElement.getAttribute("red2") != "") {
                    res.setColor2(new Color(Integer.parseInt(eElement.getAttribute("red2")),
                            Integer.parseInt(eElement.getAttribute("green2")),
                            Integer.parseInt(eElement.getAttribute("blue2"))));
                } else {
                    res.setColor2(new Color(255, 255, 255));
                }


                res.setMarking(quantity);
                res.setX(x);
                res.setY(y);
                //res.setDiagramElement(new DiagramElement(x, y));
                pn.addResource(res);
            }
        }
    }

    private void getArcsFromXML(Document doc, PetriNet pn) {

        NodeList edgeList = doc.getElementsByTagName("edge");
        for (int i = 0; i < edgeList.getLength(); i++) {
            Node nNode = edgeList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                Place p;
                Resource r;
                Arc a;

                int x1 = Integer.parseInt(eElement.getAttribute("x1"));
                int y1 = Integer.parseInt(eElement.getAttribute("y1"));
                int x2 = Integer.parseInt(eElement.getAttribute("x2"));
                int y2 = Integer.parseInt(eElement.getAttribute("y2"));
                if (cobaFile) {
                    x1 = (x1 * 60) + 20;
                    y1 = (y1 * 38) + 20;
                    x2 = (x2 * 60) + 20;
                    y2 = (y2 * 38) + 20;
                }

                int power = Integer.parseInt(eElement.getAttribute("power"));
                String type = eElement.getAttribute("type");
                String resourceProfession = eElement.getAttribute("ResourceProfession");


                if ("TP".equals(type)) {
                    Transition t = pn.getTransition(x2, y2);
                    if ("".equals(resourceProfession)) {
                        p = pn.getPlace(x1, y1);
                        a = new Arc("ARC" + i, t, p);

                    } else {
                        r = pn.getResource(x1, y1);
                        a = new Arc("ARC" + i, t, r);
                    }
                } else {
                    Transition t = pn.getTransition(x2, y2);
                    if ("".equals(resourceProfession)) {
                        p = pn.getPlace(x1, y1);
                        a = new Arc("ARC" + i, p, t);

                    } else {
                        r = pn.getResource(x1, y1);
                        a = new Arc("ARC" + i, r, t);
                    }
                }
                if (eElement.getAttribute("fontSize") != "") {
                    a.setFontSize(Integer.parseInt(eElement.getAttribute("fontSize")));
                } else {
                    a.setFontSize(16);
                }
                if (eElement.getAttribute("red1") != "") {
                    a.setColor(new Color(Integer.parseInt(eElement.getAttribute("red1")),
                            Integer.parseInt(eElement.getAttribute("green1")),
                            Integer.parseInt(eElement.getAttribute("blue1"))));
                } else {
                    a.setColor(new Color(10, 10, 10));
                }
                if (eElement.getAttribute("red2") != "") {
                    a.setColor2(new Color(Integer.parseInt(eElement.getAttribute("red2")),
                            Integer.parseInt(eElement.getAttribute("green2")),
                            Integer.parseInt(eElement.getAttribute("blue2"))));
                } else {
                    a.setColor2(new Color(255, 255, 255));
                }
                for (int j = 0; j < eElement.getChildNodes().getLength(); j++) {
                    Node pNode = eElement.getChildNodes().item(j);

                    if (pNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element pElement = (Element) pNode;
                        int x = Integer.parseInt(pElement.getAttribute("x"));
                        int y = Integer.parseInt(pElement.getAttribute("y"));
                        Point newPoint = new Point(x, y);
                        a.getBendPoints().add(newPoint);
                    }
                }
                pn.addArc(a);
            }
        }
    }

    private void getPlacesFromXML(Document doc, PetriNet pn) {

        NodeList resourcesList = doc.getElementsByTagName("place");
        for (int i = 0; i < resourcesList.getLength(); i++) {
            Node nNode = resourcesList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                Place pl = new Place(eElement.getAttribute("name"));

                ArrayList<Integer> marking = new ArrayList<Integer>();
                int numb = Integer.parseInt(eElement.getAttribute("tokens"));
                for (int k = 1; k <= numb; k++) {
                    marking.add(k);
                }


//                ArrayList<String> list = new ArrayList<String>(Arrays.asList(eElement.getAttribute("tokens").split(";")));
//                for(String s : list){
//                    if(s.length()>0)marking.add(Integer.parseInt(s));
//                }


                //int initialMarking = Integer.parseInt(eElement.getAttribute("tokens"));
                int x = Integer.parseInt(eElement.getAttribute("x"));
                int y = Integer.parseInt(eElement.getAttribute("y"));
                if (cobaFile) {
                    x = (x * 60) + 20;
                    y = (y * 38) + 20;
                }

                if ("yes".equals(eElement.getAttribute("start"))) {
                    pl.setStart(true);
                } else {
                    pl.setStart(false);
                }

                if ("yes".equals(eElement.getAttribute("end"))) {
                    pl.setEnd(true);
                } else {
                    pl.setEnd(false);
                }
                if (eElement.getAttribute("width") != "") {
                    pl.setWidth(Integer.parseInt(eElement.getAttribute("width")));
                } else {
                    pl.setWidth(53);
                }
                if (eElement.getAttribute("height") != "") {
                    pl.setHeight(Integer.parseInt(eElement.getAttribute("height")));
                } else {
                    pl.setHeight(38);
                }
                pl.setNote(eElement.getAttribute("note"));
                pl.getMarkings().setMarkings(marking);
                //pl.setMarking(initialMarking);
                pl.setX(x);
                pl.setY(y);
                if (eElement.getAttribute("fontSize") != "") {
                    pl.setFontSize(Integer.parseInt(eElement.getAttribute("fontSize")));
                } else {
                    pl.setFontSize(16);
                }
                if (eElement.getAttribute("red1") != "") {
                    pl.setColor(new Color(Integer.parseInt(eElement.getAttribute("red1")),
                            Integer.parseInt(eElement.getAttribute("green1")),
                            Integer.parseInt(eElement.getAttribute("blue1"))));
                } else {
                    pl.setColor(new Color(10, 10, 10));
                }
                if (eElement.getAttribute("red2") != "") {
                    pl.setColor2(new Color(Integer.parseInt(eElement.getAttribute("red2")),
                            Integer.parseInt(eElement.getAttribute("green2")),
                            Integer.parseInt(eElement.getAttribute("blue2"))));
                } else {
                    pl.setColor2(new Color(255, 255, 255));
                }
                //pl.setDiagramElement(new DiagramElement(x, y));
                pn.addPlace(pl);
            }
        }
    }

    private void getTransitionsFromXML(Document doc, PetriNet pn) {

        NodeList resourcesList = doc.getElementsByTagName("transition");
        for (int i = 0; i < resourcesList.getLength(); i++) {
            Node nNode = resourcesList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                Transition tr = new Transition(eElement.getAttribute("name"));
                int x = Integer.parseInt(eElement.getAttribute("x"));
                int y = Integer.parseInt(eElement.getAttribute("y"));
                if (cobaFile) {
                    x = (x * 60) + 20;
                    y = (y * 38) + 20;
                }

                tr.setNote(eElement.getAttribute("note"));
                tr.setX(x);
                tr.setY(y);
                if (eElement.getAttribute("fontSize") != "") {
                    tr.setFontSize(Integer.parseInt(eElement.getAttribute("fontSize")));
                } else {
                    tr.setFontSize(16);
                }
                if (eElement.getAttribute("red1") != "") {
                    tr.setColor(new Color(Integer.parseInt(eElement.getAttribute("red1")),
                            Integer.parseInt(eElement.getAttribute("green1")),
                            Integer.parseInt(eElement.getAttribute("blue1"))));
                } else {
                    tr.setColor(new Color(10, 10, 10));
                }
                if (eElement.getAttribute("red2") != "") {
                    tr.setColor2(new Color(Integer.parseInt(eElement.getAttribute("red2")),
                            Integer.parseInt(eElement.getAttribute("green2")),
                            Integer.parseInt(eElement.getAttribute("blue2"))));
                } else {
                    tr.setColor2(new Color(255, 255, 255));
                }
                if (eElement.getAttribute("width") != "") {
                    tr.setWidth(Integer.parseInt(eElement.getAttribute("width")));
                } else {
                    tr.setWidth(53);
                }
                if (eElement.getAttribute("height") != "") {
                    tr.setHeight(Integer.parseInt(eElement.getAttribute("height")));
                } else {
                    tr.setHeight(38);
                }
                //tr.setDiagramElement(new DiagramElement(x, y));
                pn.addTransition(tr);
            }
        }
    }

    private Element getEdgesElement(ArrayList<Arc> listOfArcs, Document doc) {
        Element edges = doc.createElement("edges");

        for (Arc a : listOfArcs) {
            Element edge = doc.createElement("edge");

            String type = "";
            String resourceType = "";

            Attr edgeType = doc.createAttribute("type");


            Attr X1 = doc.createAttribute("x1");
            Attr Y1 = doc.createAttribute("y1");
            Attr X2 = doc.createAttribute("x2");
            Attr Y2 = doc.createAttribute("y2");

            if (a.getOutElement() instanceof Transition) {
                type = "TP";
                if (a.getInElement() instanceof Resource) {
                    resourceType = "P_" + a.getInElement().getName();
                    insertProf("P_" + a.getInElement().getName());
                }
                if (cobaFile) {
                    X1.setValue((a.getInElement().getX() / 10) + "");
                    Y1.setValue((a.getInElement().getY() / 10) + "");
                    X2.setValue((a.getOutElement().getX() / 10) + "");
                    Y2.setValue((a.getOutElement().getY() / 10) + "");
                } else {
                    X1.setValue(a.getInElement().getX() + "");
                    Y1.setValue(a.getInElement().getY() + "");
                    X2.setValue(a.getOutElement().getX() + "");
                    Y2.setValue(a.getOutElement().getY() + "");
                }

            } else {
                type = "PT";
                if (a.getOutElement() instanceof Resource) {
                    resourceType = "P_" + a.getOutElement().getName();
                }
                if (cobaFile) {
                    X1.setValue((a.getOutElement().getX() / 10) + "");
                    Y1.setValue((a.getOutElement().getY() / 10) + "");
                    X2.setValue((a.getInElement().getX() / 10) + "");
                    Y2.setValue((a.getInElement().getY() / 10) + "");
                } else {
                    X1.setValue(a.getOutElement().getX() + "");
                    Y1.setValue(a.getOutElement().getY() + "");
                    X2.setValue(a.getInElement().getX() + "");
                    Y2.setValue(a.getInElement().getY() + "");
                }

            }
            edgeType.setValue(type);
            edge.setAttributeNode(edgeType);
            edge.setAttributeNode(X1);
            edge.setAttributeNode(Y1);
            edge.setAttributeNode(X2);
            edge.setAttributeNode(Y2);

            Attr power = doc.createAttribute("power");
            //?????????????
            power.setValue(a.getCapacity() + "");
            edge.setAttributeNode(power);

            Attr resourceProfession = doc.createAttribute("ResourceProfession");
            //?????????????
            resourceProfession.setValue(resourceType);
            edge.setAttributeNode(resourceProfession);


            Attr red1 = doc.createAttribute("red1");
            red1.setValue(a.getColor().getRed() + "");
            edge.setAttributeNode(red1);

            Attr green1 = doc.createAttribute("green1");
            green1.setValue(a.getColor().getGreen() + "");
            edge.setAttributeNode(green1);

            Attr blue1 = doc.createAttribute("blue1");
            blue1.setValue(a.getColor().getBlue() + "");
            edge.setAttributeNode(blue1);

            Attr green2 = doc.createAttribute("green2");
            green2.setValue(a.getColor2().getGreen() + "");
            edge.setAttributeNode(green2);

            Attr red2 = doc.createAttribute("red2");
            red2.setValue(a.getColor2().getRed() + "");
            edge.setAttributeNode(red2);

            Attr blue2 = doc.createAttribute("blue2");
            blue2.setValue(a.getColor2().getBlue() + "");
            edge.setAttributeNode(blue2);

            Attr fontSize = doc.createAttribute("fontSize");
            fontSize.setValue(a.getFontSize() + "");
            edge.setAttributeNode(fontSize);

            for (Point p : a.getBendPoints()) {
                Element bendPoint = doc.createElement("bendPoint");
                Attr x = doc.createAttribute("x");
                Attr y = doc.createAttribute("y");
                if (cobaFile) {
                    x.setValue((p.x/10) + "");
                    y.setValue((p.y/10) + "");
                } else {
                    x.setValue(p.x + "");
                    y.setValue(p.y + "");
                }

                bendPoint.setAttributeNode(x);
                bendPoint.setAttributeNode(y);
                edge.appendChild(bendPoint);
            }

            edges.appendChild(edge);
        }
        return edges;
    }

    private Element getStatesElement(ArrayList<State> listOfStates, Document doc) {
        Element markings = doc.createElement("markings");
        int inc = 1;
        for (State s : listOfStates) {
            Element marking = doc.createElement("marking");
            Attr name = doc.createAttribute("name");
            name.setValue(inc + "");
            marking.setAttributeNode(name);

            Attr vector = doc.createAttribute("vector");
            vector.setValue(s.toString() + "");
            marking.setAttributeNode(vector);

            markings.appendChild(marking);
            inc++;
        }
        return markings;
    }

    private Element getProfElement(ArrayList<String> listOfProf, Document doc) {
        Element ResourceProfessions = doc.createElement("ResourceProfessions");
        for (String s : listOfProf) {
            Element ResourceProfession = doc.createElement("ResourceProfession");
            Element resourceset = doc.createElement("resourceset");

            Attr name = doc.createAttribute("name");
            name.setValue(s);
            ResourceProfession.setAttributeNode(name);

            Attr RSname = doc.createAttribute("name");
            RSname.setValue(s.substring(2));
            resourceset.setAttributeNode(RSname);

            ResourceProfession.appendChild(resourceset);
            ResourceProfessions.appendChild(ResourceProfession);
        }
        return ResourceProfessions;
    }

    private Element getPlacesElement(ArrayList<Place> listOfPlaces, Document doc) {
        Element places = doc.createElement("places");

        for (Place p : listOfPlaces) {
            Element place = doc.createElement("place");

            Attr name = doc.createAttribute("name");
            name.setValue(p.getName());
            place.setAttributeNode(name);

            Attr X = doc.createAttribute("x");
            Attr Y = doc.createAttribute("y");
            //X.setValue(t.getDiagramElement().getX() + "");
            if (cobaFile) {
                X.setValue((p.getX() / 10) + "");
                Y.setValue((p.getY() / 10) + "");
            } else {
                X.setValue(p.getX() + "");
                Y.setValue(p.getY() + "");
            }
            //X.setValue("");
            place.setAttributeNode(X);
            place.setAttributeNode(Y);

            Attr tokens = doc.createAttribute("tokens");
            String tokensTemp = "";
            StringBuilder sb = new StringBuilder(tokensTemp);

            sb.append(p.getMarkings().getMarkings().size());
//            for(Integer i : p.getMarkings().getMarkings()){
//                sb.append(i).append(";");
//            }


            //tokens.setValue(p.getMarking() + "");
            tokens.setValue(sb.toString() + "");
            place.setAttributeNode(tokens);

            Attr start = doc.createAttribute("start");
            if (p.isStart()) {
                start.setValue("yes");
            } else {
                start.setValue("no");
            }
            place.setAttributeNode(start);

            Attr end = doc.createAttribute("end");
            if (p.isEnd()) {
                end.setValue("yes");
            } else {
                end.setValue("no");
            }
            place.setAttributeNode(end);

            Attr width = doc.createAttribute("width");
            width.setValue(p.getWidth() + "");
            place.setAttributeNode(width);

            Attr height = doc.createAttribute("height");
            height.setValue(p.getHeight() + "");
            place.setAttributeNode(height);

            Attr note = doc.createAttribute("note");
            note.setValue(p.getNote());
            place.setAttributeNode(note);

            Attr red1 = doc.createAttribute("red1");
            red1.setValue(p.getColor().getRed() + "");
            place.setAttributeNode(red1);

            Attr green1 = doc.createAttribute("green1");
            green1.setValue(p.getColor().getGreen() + "");
            place.setAttributeNode(green1);

            Attr blue1 = doc.createAttribute("blue1");
            blue1.setValue(p.getColor().getBlue() + "");
            place.setAttributeNode(blue1);

            Attr green2 = doc.createAttribute("green2");
            green2.setValue(p.getColor2().getGreen() + "");
            place.setAttributeNode(green2);

            Attr red2 = doc.createAttribute("red2");
            red2.setValue(p.getColor2().getRed() + "");
            place.setAttributeNode(red2);

            Attr blue2 = doc.createAttribute("blue2");
            blue2.setValue(p.getColor2().getBlue() + "");
            place.setAttributeNode(blue2);

            Attr fontSize = doc.createAttribute("fontSize");
            fontSize.setValue(p.getFontSize() + "");
            place.setAttributeNode(fontSize);

            places.appendChild(place);
        }
        return places;
    }

    private Element getTransationsElement(ArrayList<Transition> listOfTransitions, Document doc) {
        Element transitions = doc.createElement("transitions");

        for (Transition t : listOfTransitions) {
            Element transition = doc.createElement("transition");

            Attr name = doc.createAttribute("name");
            name.setValue(t.getName());
            transition.setAttributeNode(name);

            Attr X = doc.createAttribute("x");
            Attr Y = doc.createAttribute("y");
            //X.setValue(t.getDiagramElement().getX() + "");
            if (cobaFile) {
                X.setValue((t.getX() / 10) + "");
                Y.setValue((t.getY() / 10) + "");
            } else {
                X.setValue(t.getX() + "");
                Y.setValue(t.getY() + "");
            }

            transition.setAttributeNode(X);
            transition.setAttributeNode(Y);

            Attr width = doc.createAttribute("width");
            width.setValue(t.getWidth() + "");
            transition.setAttributeNode(width);

            Attr height = doc.createAttribute("height");
            height.setValue(t.getHeight() + "");
            transition.setAttributeNode(height);

            Attr note = doc.createAttribute("note");
            note.setValue(t.getNote());
            transition.setAttributeNode(note);

            Attr red1 = doc.createAttribute("red1");
            red1.setValue(t.getColor().getRed() + "");
            transition.setAttributeNode(red1);

            Attr green1 = doc.createAttribute("green1");
            green1.setValue(t.getColor().getGreen() + "");
            transition.setAttributeNode(green1);

            Attr blue1 = doc.createAttribute("blue1");
            blue1.setValue(t.getColor().getBlue() + "");
            transition.setAttributeNode(blue1);

            Attr green2 = doc.createAttribute("green2");
            green2.setValue(t.getColor2().getGreen() + "");
            transition.setAttributeNode(green2);

            Attr red2 = doc.createAttribute("red2");
            red2.setValue(t.getColor2().getRed() + "");
            transition.setAttributeNode(red2);

            Attr blue2 = doc.createAttribute("blue2");
            blue2.setValue(t.getColor2().getBlue() + "");
            transition.setAttributeNode(blue2);

            Attr fontSize = doc.createAttribute("fontSize");
            fontSize.setValue(t.getFontSize() + "");
            transition.setAttributeNode(fontSize);

            transitions.appendChild(transition);
        }
        return transitions;
    }

    private Element getResourcesElement(Document doc, ArrayList<Resource> listOfResources) {
        Element resources = doc.createElement("resources");

        for (Resource r : listOfResources) {
            Element resource = doc.createElement("resource");

            Attr resName = doc.createAttribute("name");
            resName.setValue(r.getName());
            resource.setAttributeNode(resName);

            Attr quantity = doc.createAttribute("quantity");
            quantity.setValue(r.getMarking() + "");
            resource.setAttributeNode(quantity);

            Attr resX = doc.createAttribute("x");
            //resX.setValue(r.getDiagramElement().getX() + "");\
            resX.setValue(r.getX() + "");
            //resX.setValue("");
            resource.setAttributeNode(resX);

            Attr resY = doc.createAttribute("y");
            //resY.setValue(r.getDiagramElement().getY() + "");
            resY.setValue(r.getY() + "");
            //resY.setValue("");
            resource.setAttributeNode(resY);

            Attr width = doc.createAttribute("width");
            width.setValue(r.getWidth() + "");
            resource.setAttributeNode(width);

            Attr height = doc.createAttribute("height");
            height.setValue(r.getHeight() + "");
            resource.setAttributeNode(height);

            Attr note = doc.createAttribute("note");
            note.setValue(r.getNote());
            resource.setAttributeNode(note);

            Attr red1 = doc.createAttribute("red1");
            red1.setValue(r.getColor().getRed() + "");
            resource.setAttributeNode(red1);

            Attr green1 = doc.createAttribute("green1");
            green1.setValue(r.getColor().getGreen() + "");
            resource.setAttributeNode(green1);

            Attr blue1 = doc.createAttribute("blue1");
            blue1.setValue(r.getColor().getBlue() + "");
            resource.setAttributeNode(blue1);

            Attr green2 = doc.createAttribute("green2");
            green2.setValue(r.getColor2().getGreen() + "");
            resource.setAttributeNode(green2);

            Attr red2 = doc.createAttribute("red2");
            red2.setValue(r.getColor2().getRed() + "");
            resource.setAttributeNode(red2);

            Attr blue2 = doc.createAttribute("blue2");
            blue2.setValue(r.getColor2().getBlue() + "");
            resource.setAttributeNode(blue2);

            Attr fontSize = doc.createAttribute("fontSize");
            fontSize.setValue(r.getFontSize() + "");
            resource.setAttributeNode(fontSize);

            resources.appendChild(resource);
        }
        return resources;
    }
}