/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FileManager;

import Core.Arc;
import Core.Place;
import Core.PrecedenceGraph;
import java.awt.Color;
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

                a.setFontSize(Integer.parseInt(eElement.getAttribute("fontSize")));
                a.setColor(new Color(Integer.parseInt(eElement.getAttribute("red1")),
                                     Integer.parseInt(eElement.getAttribute("green1")),
                                     Integer.parseInt(eElement.getAttribute("blue1"))));
                a.setColor2(new Color(Integer.parseInt(eElement.getAttribute("red2")),
                                     Integer.parseInt(eElement.getAttribute("green2")),
                                     Integer.parseInt(eElement.getAttribute("blue2"))));
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
                
                nd.setFontSize(Integer.parseInt(eElement.getAttribute("fontSize")));
                nd.setColor(new Color(Integer.parseInt(eElement.getAttribute("red1")),
                                     Integer.parseInt(eElement.getAttribute("green1")),
                                     Integer.parseInt(eElement.getAttribute("blue1"))));
                nd.setColor2(new Color(Integer.parseInt(eElement.getAttribute("red2")),
                                     Integer.parseInt(eElement.getAttribute("green2")),
                                     Integer.parseInt(eElement.getAttribute("blue2"))));
                //nd.setWidth(Integer.parseInt(eElement.getAttribute("width")));
                //nd.setHeight(Integer.parseInt(eElement.getAttribute("height")));
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

            Attr red1 = doc.createAttribute("red1");
            red1.setValue(p.getColor().getRed()+"");
            node.setAttributeNode(red1);
            
            Attr green1 = doc.createAttribute("green1");
            green1.setValue(p.getColor().getGreen()+"");
            node.setAttributeNode(green1);
            
            Attr blue1 = doc.createAttribute("blue1");
            blue1.setValue(p.getColor().getBlue()+"");
            node.setAttributeNode(blue1);
            
            Attr green2 = doc.createAttribute("green2");
            green2.setValue(p.getColor2().getGreen()+"");
            node.setAttributeNode(green2);
            
            Attr red2 = doc.createAttribute("red2");
            red2.setValue(p.getColor2().getRed()+"");
            node.setAttributeNode(red2);
            
            Attr blue2 = doc.createAttribute("blue2");
            blue2.setValue(p.getColor2().getBlue()+"");
            node.setAttributeNode(blue2);
            
            Attr fontSize = doc.createAttribute("fontSize");
            fontSize.setValue(p.getFontSize()+"");
            node.setAttributeNode(fontSize);
            
            Attr width = doc.createAttribute("width");
            width.setValue(p.getWidth()+"");
            node.setAttributeNode(width);
            
            Attr height = doc.createAttribute("height");
            width.setValue(p.getHeight()+"");
            node.setAttributeNode(height);

            nodes.appendChild(node);
        }
        return nodes;
    }
}