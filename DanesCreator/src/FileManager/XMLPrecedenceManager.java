/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FileManager;

import Core.Arc;
import Core.PetriNet;
import Core.Place;
import Core.PrecedenceGraph;
import Core.Resource;
import Core.Transition;
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
public class XMLPrecedenceManager {

    DocumentBuilderFactory docFactory;
    DocumentBuilder docBuilder;
    Document doc;

    public XMLPrecedenceManager() {

        try {
            docFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docFactory.newDocumentBuilder();

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLPetriManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean createPrecedenceXML(Core.Graph g, File outputFile) {
        try {
            PrecedenceGraph pg=(PrecedenceGraph) g;
            doc = (Document) docBuilder.newDocument();
            Element rootElement = doc.createElement("process");
            doc.appendChild(rootElement);

            Element title = doc.createElement("title");
            title.appendChild(doc.createTextNode(pg.getName()));
            rootElement.appendChild(title);

            Element places = this.getNodesElement(pg.getListOfNodes(), doc);
            Element edges = getEdgesElement(pg.getListOfArcs(), doc);
            rootElement.appendChild(places);
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

    public PrecedenceGraph getPrecedenceFromXML(File inputFile) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList titleList = doc.getElementsByTagName("title");
            String petriNetName = titleList.item(0).getTextContent();
            PrecedenceGraph pg = new PrecedenceGraph(petriNetName);

            this.getNodesFromXML(doc, pg);
            this.getArcsFromXML(doc, pg);

            return pg;
        } catch (Exception ex) {
            Logger.getLogger(XMLPetriManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private void getArcsFromXML(Document doc, PrecedenceGraph pg) {

        NodeList resourcesList = doc.getElementsByTagName("edge");
        for (int i = 0; i < resourcesList.getLength(); i++) {
            Node nNode = resourcesList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                Place p;
                //Resource r;
                
                int x1 = Integer.parseInt(eElement.getAttribute("x1"));
                int y1 = Integer.parseInt(eElement.getAttribute("y1"));
                int x2 = Integer.parseInt(eElement.getAttribute("x2"));
                int y2 = Integer.parseInt(eElement.getAttribute("y2"));
                int power = Integer.parseInt(eElement.getAttribute("power"));

                Core.Node outNode=pg.getNode(x1, y1);
                Core.Node inNode=pg.getNode(x2, y2);
                Arc a=new Arc("ARC" + i, outNode, inNode);             
              
               outNode.getListOfOutArcs().add(a);
               outNode.getListOfOutNodes().add(inNode);
               inNode.getListOfInArcs().add(a);
               inNode.getListOfInNodes().add(outNode);

                System.out.println("-------------------------");
                System.out.println(a.getName());
                System.out.println(a.getOutElement().getName());
                System.out.println(a.getInElement().getName());
                pg.addArc(a);
            }
        }
    }

    private void getNodesFromXML(Document doc, PrecedenceGraph pg) {

        NodeList resourcesList = doc.getElementsByTagName("node");
        for (int i = 0; i < resourcesList.getLength(); i++) {
            Node nNode = resourcesList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                Core.Node nd = new Core.Node(eElement.getAttribute("name"));
                int tokens = Integer.parseInt(eElement.getAttribute("tokens"));
                int x = Integer.parseInt(eElement.getAttribute("x"));
                int y = Integer.parseInt(eElement.getAttribute("y"));

                nd.setCapacity(tokens);
                nd.setX(x);
                nd.setY(y);
                
                System.out.println("-------------------------");
                System.out.println(nd.getName());
                System.out.println(nd.getX());
                System.out.println(nd.getY());
                
                //pl.setDiagramElement(new DiagramElement(x, y));
                pg.addNode(nd);
            }
        }
    }

    private Element getEdgesElement(ArrayList<Arc> listOfArcs, Document doc) {
        Element edges = doc.createElement("edges");

        for (Arc a : listOfArcs) {
            Element edge = doc.createElement("edge");

            String type = "";
            String resourceType = "";

            //Attr edgeType = doc.createAttribute("type");

            Attr X1 = doc.createAttribute("x1");
            Attr Y1 = doc.createAttribute("y1");
            Attr X2 = doc.createAttribute("x2");
            Attr Y2 = doc.createAttribute("y2");

            X1.setValue(a.getOutElement().getX() + "");
            Y1.setValue(a.getOutElement().getY() + "");
            X2.setValue(a.getInElement().getX() + "");
            Y2.setValue(a.getInElement().getY() + "");

            //edgeType.setValue(type);
            //edge.setAttributeNode(edgeType);
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

            edges.appendChild(edge);
        }
        return edges;
    }

    private Element getNodesElement(ArrayList<Core.Node> listOfNodes, Document doc) {
        Element nodes = doc.createElement("nodes");

        for (Core.Node p : listOfNodes) {
            Element node = doc.createElement("node");

            Attr name = doc.createAttribute("name");

            name.setValue(p.getName());
            node.setAttributeNode(name);

            Attr X = doc.createAttribute("x");
            //X.setValue(p.getDiagramElement().getX() + "");
            X.setValue(p.getX() + "");
            //X.setValue("");
            node.setAttributeNode(X);

            Attr Y = doc.createAttribute("y");
            //Y.setValue(p.getDiagramElement().getY() + "");
            Y.setValue(p.getY() + "");
            //Y.setValue("");
            node.setAttributeNode(Y);

            Attr tokens = doc.createAttribute("tokens");
            tokens.setValue(p.getCapacity() + "");
            node.setAttributeNode(tokens);
//
//            Attr start = doc.createAttribute("start");
//            if (p.isStart()) {
//                start.setValue("yes");
//            } else {
//                start.setValue("no");
//            }
//            place.setAttributeNode(start);
//
//            Attr end = doc.createAttribute("end");
//            if (p.isEnd()) {
//                end.setValue("true");
//            } else {
//                end.setValue("no");
//            }
//            place.setAttributeNode(end);

            nodes.appendChild(node);
        }
        return nodes;
    }
}