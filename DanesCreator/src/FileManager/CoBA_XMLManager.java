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
import GUI.DiagramElement;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
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
public class CoBA_XMLManager {

    DocumentBuilderFactory docFactory;
    DocumentBuilder docBuilder;
    Document doc;

    public CoBA_XMLManager() {

        try {
            docFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docFactory.newDocumentBuilder();

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(CoBA_XMLManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean createPetriXML(PetriNet pn, File outputFile) {
        try {
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
            Logger.getLogger(CoBA_XMLManager.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(CoBA_XMLManager.class.getName()).log(Level.SEVERE, null, ex);
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
                res.setQuantity(quantity);
                res.setDiagramElement(new DiagramElement(x, y));
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
                    System.out.println(t.getName());
                    if ("".equals(resourceProfession)) {
                        p = pn.getPlace(x1, y1);
                        a = new Arc("ARC" + i, t, p);
                        System.out.println(t.getName());
                        System.out.println(p.getName());

                    } else {
                        r = pn.getResource(x1, y1);
                        a = new Arc("ARC" + i, t, r);
                        System.out.println(r.getName());
                        System.out.println(t.getName());
                    }
                } else {
                    Transition t = pn.getTransition(x2, y2);
                    if ("".equals(resourceProfession)) {
                        p = pn.getPlace(x1, y1);
                        a = new Arc("ARC" + i, p, t);
                        System.out.println(p.getName());
                        System.out.println(t.getName());
                    } else {
                        r = pn.getResource(x1, y1);
                        a = new Arc("ARC" + i, r, t);
                        System.out.println(r.getName());
                        System.out.println(t.getName());
                    }
                }
                System.out.println("-------------------------");
                System.out.println(a.getName());
                System.out.println(a.getOutElement().getName());
                System.out.println(a.getInElement().getName());
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
                int tokens = Integer.parseInt(eElement.getAttribute("tokens"));
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

                pl.setQuantity(tokens);
                pl.setDiagramElement(new DiagramElement(x, y));
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
                tr.setDiagramElement(new DiagramElement(x, y));
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
                X1.setValue(a.getInElement().getDiagramElement().getX() + "");
                Y1.setValue(a.getInElement().getDiagramElement().getY() + "");
                X2.setValue(a.getOutElement().getDiagramElement().getX() + "");
                Y2.setValue(a.getOutElement().getDiagramElement().getY() + "");
            } else {
                type = "PT";
                if (a.getOutElement() instanceof Resource) {
                    resourceType = "P_" + a.getOutElement().getName();
                }
                X1.setValue(a.getOutElement().getDiagramElement().getX() + "");
                Y1.setValue(a.getOutElement().getDiagramElement().getY() + "");
                X2.setValue(a.getInElement().getDiagramElement().getX() + "");
                Y2.setValue(a.getInElement().getDiagramElement().getY() + "");

            }
            edgeType.setValue(type);
            edge.setAttributeNode(edgeType);
            edge.setAttributeNode(X1);
            edge.setAttributeNode(Y1);
            edge.setAttributeNode(X2);
            edge.setAttributeNode(Y2);



//            
//            //X1.setValue("");
//            edge.setAttributeNode(X1);
//
//            
//            
//            //Y1.setValue("");
//            edge.setAttributeNode(Y1);
//
//            
//            //X2.setValue("");
//            edge.setAttributeNode(X2);
//
//            
//            //Y2.setValue("");
//            edge.setAttributeNode(Y2);

            Attr power = doc.createAttribute("power");
            //?????????????
            power.setValue(a.getCapacity() + "");
            edge.setAttributeNode(power);

            Attr resourceProfession = doc.createAttribute("ResourceProfession");
            //?????????????
            resourceProfession.setValue(resourceType);
            edge.setAttributeNode(resourceProfession);

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
            X.setValue(p.getDiagramElement().getX() + "");
            //X.setValue("");
            place.setAttributeNode(X);

            Attr Y = doc.createAttribute("y");
            Y.setValue(p.getDiagramElement().getY() + "");
            //Y.setValue("");
            place.setAttributeNode(Y);

            Attr tokens = doc.createAttribute("tokens");
            tokens.setValue(p.getTokens() + "");
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

            places.appendChild(place);
        }
        return places;
    }

    private Element getTransationsElement(ArrayList<Transition> listOfTransitions, Document doc) {
        Element transitions = doc.createElement("transitions");

        for (Transition p : listOfTransitions) {
            Element transition = doc.createElement("transition");

            Attr name = doc.createAttribute("name");
            name.setValue(p.getName());
            transition.setAttributeNode(name);

            Attr X = doc.createAttribute("x");
            X.setValue(p.getDiagramElement().getX() + "");
            //X.setValue("");
            transition.setAttributeNode(X);

            Attr Y = doc.createAttribute("y");
            Y.setValue(p.getDiagramElement().getY() + "");
            //Y.setValue("");
            transition.setAttributeNode(Y);

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
            quantity.setValue(r.getQuantity() + "");
            resource.setAttributeNode(quantity);

            Attr resX = doc.createAttribute("x");
            resX.setValue(r.getDiagramElement().getX() + "");
            //resX.setValue("");
            resource.setAttributeNode(resX);

            Attr resY = doc.createAttribute("y");
            resY.setValue(r.getDiagramElement().getY() + "");
            //resY.setValue("");
            resource.setAttributeNode(resY);
            resources.appendChild(resource);
        }
        return resources;
    }
}