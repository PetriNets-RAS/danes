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
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Atarin
 */
public class XMLPetriManager {

    DocumentBuilderFactory docFactory;
    DocumentBuilder docBuilder;
    Document doc;

    public XMLPetriManager() {

        try {
            docFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docFactory.newDocumentBuilder();

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLPetriManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean createPetriXML(Core.Graph g, File outputFile) {
        try {
            PetriNet pn=(PetriNet) g;
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

    public PetriNet getPetriNetFromXML(File inputFile) {
        try {
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
                int quantity = Integer.parseInt(eElement.getAttribute("quantity"));
                res.setWidth(Integer.parseInt(eElement.getAttribute("width")));
                res.setHeight(Integer.parseInt(eElement.getAttribute("height")));
                res.setNote(eElement.getAttribute("note"));
                res.setFontSize(Integer.parseInt(eElement.getAttribute("fontSize")));
                res.setColor(new Color(Integer.parseInt(eElement.getAttribute("red1")),
                                     Integer.parseInt(eElement.getAttribute("green1")),
                                     Integer.parseInt(eElement.getAttribute("blue1"))));
                res.setColor2(new Color(Integer.parseInt(eElement.getAttribute("red2")),
                                     Integer.parseInt(eElement.getAttribute("green2")),
                                     Integer.parseInt(eElement.getAttribute("blue2"))));
                res.setMarking(quantity);
                res.setX(x);
                res.setY(y);
                //res.setDiagramElement(new DiagramElement(x, y));
                pn.addResource(res);
            }
        }
    }

    private void getArcsFromXML(Document doc, PetriNet pn) {

        NodeList resourcesList = doc.getElementsByTagName("edge");
        for (int i = 0; i < resourcesList.getLength(); i++) {
            Node nNode = resourcesList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                Place p;
                Resource r;
                Arc a;
                
                int x1 = Integer.parseInt(eElement.getAttribute("x1"));
                int y1 = Integer.parseInt(eElement.getAttribute("y1"));
                int x2 = Integer.parseInt(eElement.getAttribute("x2"));
                int y2 = Integer.parseInt(eElement.getAttribute("y2"));
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
                a.setFontSize(Integer.parseInt(eElement.getAttribute("fontSize")));
                a.setColor(new Color(Integer.parseInt(eElement.getAttribute("red1")),
                                     Integer.parseInt(eElement.getAttribute("green1")),
                                     Integer.parseInt(eElement.getAttribute("blue1"))));
                a.setColor2(new Color(Integer.parseInt(eElement.getAttribute("red2")),
                                     Integer.parseInt(eElement.getAttribute("green2")),
                                     Integer.parseInt(eElement.getAttribute("blue2"))));

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
                
                ArrayList<Integer> marking=new ArrayList<Integer>();
                ArrayList<String> list = new ArrayList<String>(Arrays.asList(eElement.getAttribute("tokens").split(";")));
                for(String s : list){
                    if(s.length()>0)marking.add(Integer.parseInt(s));
                }
                
                
                //int initialMarking = Integer.parseInt(eElement.getAttribute("tokens"));
                int x = Integer.parseInt(eElement.getAttribute("x"));
                int y = Integer.parseInt(eElement.getAttribute("y"));

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
                pl.setWidth(Integer.parseInt(eElement.getAttribute("width")));
                pl.setHeight(Integer.parseInt(eElement.getAttribute("height")));
                pl.setNote(eElement.getAttribute("note"));
                pl.getMarkings().setMarkings(marking);
                //pl.setMarking(initialMarking);
                pl.setX(x);
                pl.setY(y);
                pl.setFontSize(Integer.parseInt(eElement.getAttribute("fontSize")));
                pl.setColor(new Color(Integer.parseInt(eElement.getAttribute("red1")),
                                     Integer.parseInt(eElement.getAttribute("green1")),
                                     Integer.parseInt(eElement.getAttribute("blue1"))));
                pl.setColor2(new Color(Integer.parseInt(eElement.getAttribute("red2")),
                                     Integer.parseInt(eElement.getAttribute("green2")),
                                     Integer.parseInt(eElement.getAttribute("blue2"))));
                
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
                
                tr.setNote(eElement.getAttribute("note"));
                tr.setX(x);
                tr.setY(y);
                tr.setFontSize(Integer.parseInt(eElement.getAttribute("fontSize")));
                tr.setColor(new Color(Integer.parseInt(eElement.getAttribute("red1")),
                                     Integer.parseInt(eElement.getAttribute("green1")),
                                     Integer.parseInt(eElement.getAttribute("blue1"))));
                tr.setColor2(new Color(Integer.parseInt(eElement.getAttribute("red2")),
                                     Integer.parseInt(eElement.getAttribute("green2")),
                                     Integer.parseInt(eElement.getAttribute("blue2"))));
                tr.setWidth(Integer.parseInt(eElement.getAttribute("width")));
                tr.setHeight(Integer.parseInt(eElement.getAttribute("height")));
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
                }
                /*X1.setValue(a.getInElement().getDiagramElement().getX() + "");
                Y1.setValue(a.getInElement().getDiagramElement().getY() + "");
                X2.setValue(a.getOutElement().getDiagramElement().getX() + "");
                Y2.setValue(a.getOutElement().getDiagramElement().getY() + "");*/
                X1.setValue(a.getInElement().getX() + "");
                Y1.setValue(a.getInElement().getY() + "");
                X2.setValue(a.getOutElement().getX() + "");
                Y2.setValue(a.getOutElement().getY() + "");                
            } else {
                type = "PT";
                if (a.getOutElement() instanceof Resource) {
                    resourceType = "P_" + a.getOutElement().getName();
                }
                /*X1.setValue(a.getOutElement().getDiagramElement().getX() + "");
                Y1.setValue(a.getOutElement().getDiagramElement().getY() + "");
                X2.setValue(a.getInElement().getDiagramElement().getX() + "");
                Y2.setValue(a.getInElement().getDiagramElement().getY() + "");*/
                X1.setValue(a.getOutElement().getX() + "");
                Y1.setValue(a.getOutElement().getY() + "");
                X2.setValue(a.getInElement().getX() + "");
                Y2.setValue(a.getInElement().getY() + "");
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
            red1.setValue(a.getColor().getRed()+"");
            edge.setAttributeNode(red1);
            
            Attr green1 = doc.createAttribute("green1");
            green1.setValue(a.getColor().getGreen()+"");
            edge.setAttributeNode(green1);
            
            Attr blue1 = doc.createAttribute("blue1");
            blue1.setValue(a.getColor().getBlue()+"");
            edge.setAttributeNode(blue1);
            
            Attr green2 = doc.createAttribute("green2");
            green2.setValue(a.getColor2().getGreen()+"");
            edge.setAttributeNode(green2);
            
            Attr red2 = doc.createAttribute("red2");
            red2.setValue(a.getColor2().getRed()+"");
            edge.setAttributeNode(red2);
            
            Attr blue2 = doc.createAttribute("blue2");
            blue2.setValue(a.getColor2().getBlue()+"");
            edge.setAttributeNode(blue2);
            
            Attr fontSize = doc.createAttribute("fontSize");
            fontSize.setValue(a.getFontSize()+"");
            edge.setAttributeNode(fontSize);

            edges.appendChild(edge);
        }
        return edges;
    }

    private Element getPlacesElement(ArrayList<Place> listOfPlaces, Document doc) {
        Element places = doc.createElement("places");

        for (Place p : listOfPlaces) {
            Element place = doc.createElement("place");

            Attr name = doc.createAttribute("name");
            name.setValue(p.getName());
            place.setAttributeNode(name);

            Attr X = doc.createAttribute("x");
            //X.setValue(t.getDiagramElement().getX() + "");
            X.setValue(p.getX() + "");
            //X.setValue("");
            place.setAttributeNode(X);

            Attr Y = doc.createAttribute("y");
            //Y.setValue(t.getDiagramElement().getY() + "");
            Y.setValue(p.getY() + "");
            //Y.setValue("");
            place.setAttributeNode(Y);

            Attr tokens = doc.createAttribute("tokens");
            String tokensTemp="";
            StringBuilder sb=new StringBuilder(tokensTemp);
            for(Integer i : p.getMarkings().getMarkings()){
                sb.append(i).append(";");
            }
            
            
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
                end.setValue("true");
            } else {
                end.setValue("no");
            }
            place.setAttributeNode(end);
            
            Attr width = doc.createAttribute("width");
            width.setValue(p.getWidth()+"");
            place.setAttributeNode(width);
            
            Attr height = doc.createAttribute("height");
            height.setValue(p.getHeight()+"");
            place.setAttributeNode(height);
            
            Attr note = doc.createAttribute("note");
            note.setValue(p.getNote());
            place.setAttributeNode(note);
            
            Attr red1 = doc.createAttribute("red1");
            red1.setValue(p.getColor().getRed()+"");
            place.setAttributeNode(red1);
            
            Attr green1 = doc.createAttribute("green1");
            green1.setValue(p.getColor().getGreen()+"");
            place.setAttributeNode(green1);
            
            Attr blue1 = doc.createAttribute("blue1");
            blue1.setValue(p.getColor().getBlue()+"");
            place.setAttributeNode(blue1);
            
            Attr green2 = doc.createAttribute("green2");
            green2.setValue(p.getColor2().getGreen()+"");
            place.setAttributeNode(green2);
            
            Attr red2 = doc.createAttribute("red2");
            red2.setValue(p.getColor2().getRed()+"");
            place.setAttributeNode(red2);
            
            Attr blue2 = doc.createAttribute("blue2");
            blue2.setValue(p.getColor2().getBlue()+"");
            place.setAttributeNode(blue2);
            
            Attr fontSize = doc.createAttribute("fontSize");
            fontSize.setValue(p.getFontSize()+"");
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
            //X.setValue(t.getDiagramElement().getX() + "");
            X.setValue(t.getX() + "");
            //X.setValue("");
            transition.setAttributeNode(X);

            Attr Y = doc.createAttribute("y");
            //Y.setValue(t.getDiagramElement().getY() + "");
            Y.setValue(t.getY() + "");
            //Y.setValue("");
            transition.setAttributeNode(Y);
            
            Attr width = doc.createAttribute("width");
            width.setValue(t.getWidth()+"");
            transition.setAttributeNode(width);
            
            Attr height = doc.createAttribute("height");
            height.setValue(t.getHeight()+"");
            transition.setAttributeNode(height);
            
            Attr note = doc.createAttribute("note");
            note.setValue(t.getNote());
            transition.setAttributeNode(note);
            
            Attr red1 = doc.createAttribute("red1");
            red1.setValue(t.getColor().getRed()+"");
            transition.setAttributeNode(red1);
            
            Attr green1 = doc.createAttribute("green1");
            green1.setValue(t.getColor().getGreen()+"");
            transition.setAttributeNode(green1);
            
            Attr blue1 = doc.createAttribute("blue1");
            blue1.setValue(t.getColor().getBlue()+"");
            transition.setAttributeNode(blue1);
            
            Attr green2 = doc.createAttribute("green2");
            green2.setValue(t.getColor2().getGreen()+"");
            transition.setAttributeNode(green2);
            
            Attr red2 = doc.createAttribute("red2");
            red2.setValue(t.getColor2().getRed()+"");
            transition.setAttributeNode(red2);
            
            Attr blue2 = doc.createAttribute("blue2");
            blue2.setValue(t.getColor2().getBlue()+"");
            transition.setAttributeNode(blue2);
            
            Attr fontSize = doc.createAttribute("fontSize");
            fontSize.setValue(t.getFontSize()+"");
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
            width.setValue(r.getWidth()+"");
            resource.setAttributeNode(width);
            
            Attr height = doc.createAttribute("height");
            height.setValue(r.getHeight()+"");
            resource.setAttributeNode(height);
            
            Attr note = doc.createAttribute("note");
            note.setValue(r.getNote());
            resource.setAttributeNode(note);
                              
            Attr red1 = doc.createAttribute("red1");
            red1.setValue(r.getColor().getRed()+"");
            resource.setAttributeNode(red1);
            
            Attr green1 = doc.createAttribute("green1");
            green1.setValue(r.getColor().getGreen()+"");
            resource.setAttributeNode(green1);
            
            Attr blue1 = doc.createAttribute("blue1");
            blue1.setValue(r.getColor().getBlue()+"");
            resource.setAttributeNode(blue1);
            
            Attr green2 = doc.createAttribute("green2");
            green2.setValue(r.getColor2().getGreen()+"");
            resource.setAttributeNode(green2);
            
            Attr red2 = doc.createAttribute("red2");
            red2.setValue(r.getColor2().getRed()+"");
            resource.setAttributeNode(red2);
            
            Attr blue2 = doc.createAttribute("blue2");
            blue2.setValue(r.getColor2().getBlue()+"");
            resource.setAttributeNode(blue2);
            
            Attr fontSize = doc.createAttribute("fontSize");
            fontSize.setValue(r.getFontSize()+"");
            resource.setAttributeNode(fontSize);
            
            resources.appendChild(resource);
        }
        return resources;
    }
}