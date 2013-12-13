/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FileManager;

import Core.AbsPlace;
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
    private ArrayList<AdditionalElementInfo> elementInfo;
    private ArrayList<AdditionalArcInfo> arcsInfo;
    private ArrayList<Integer> tranIDs;
    private int minX;
    private int minY;
    private int maxX;
    private int maxY;
    private int id = 1023439816;

    public XMLCPNManager() {

        minX = Integer.MAX_VALUE;
        minY = Integer.MAX_VALUE;
        maxX = Integer.MIN_VALUE;
        maxY = Integer.MIN_VALUE;
        elementInfo = new ArrayList<AdditionalElementInfo>();

        arcsInfo = new ArrayList<AdditionalArcInfo>();
        tranIDs = new ArrayList<Integer>();
        try {
            docFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docFactory.newDocumentBuilder();

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLPetriManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Element findPageElement(Document doc) {
        Element page = null;
        NodeList titleList = doc.getElementsByTagName("page");
        Node nNode = titleList.item(0);

        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            page = (Element) nNode;
        }

        return page;
    }

    public void setMinMaxValues(PetriNet pn) {
        minX = Integer.MAX_VALUE;
        minY = Integer.MAX_VALUE;
        maxX = Integer.MIN_VALUE;
        maxY = Integer.MIN_VALUE;

        for (Place p : pn.getListOfPlaces()) {
            if(p.getX()< minX) minX=p.getX();
            if(p.getY()< minY) minY=p.getY();
            if(p.getX()> maxX) maxX=p.getX();
            if(p.getY()> maxY) maxY=p.getY();
        }
        for (Resource r : pn.getListOfResources()) {
            if(r.getX()< minX) minX=r.getX();
            if(r.getY()< minY) minY=r.getY();
            if(r.getX()> maxX) maxX=r.getX();
            if(r.getY()> maxY) maxY=r.getY();
        }
        for (Transition t : pn.getListOfTransitions()) {
            if(t.getX()< minX) minX=t.getX();
            if(t.getY()< minY) minY=t.getY();
            if(t.getX()> maxX) maxX=t.getX();
            if(t.getY()> maxY) maxY=t.getY();
        }
        for(Arc a: pn.getListOfArcs()){
            for(Point p: a.getBendPoints()){
                if(p.getX()< minX) minX=(int)p.getX();
                if(p.getY()< minY) minY=(int)p.getY();
                if(p.getX()> maxX) maxX=(int)p.getX();
                if(p.getY()> maxY) maxY=(int)p.getY();  
            }
            
        }


    }
    
    public int transformX(int x){        
        return x-maxX; 
    }
    
    public int transformY(int y){        
        return maxY-y; 
    }

    public boolean createPetriXML(Core.Graph g, File outputFile) {
        try {
            elementInfo.clear();
            PetriNet pn = (PetriNet) g;
            setMinMaxValues(pn);
            
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            dBuilder.setEntityResolver(new EntityResolver() {
                @Override
                public InputSource resolveEntity(String publicId,
                        String systemId) throws SAXException, IOException {
                    return new InputSource(new StringReader(""));
                }
            });

            doc = dBuilder.parse(outputFile);
            doc.getDocumentElement().normalize();
            Element page = findPageElement(doc);

            this.getPlacesCPNElement(pn.getListOfPlaces(), pn.getListOfResources(), doc, page);
            this.getTransCPNElement(pn.getListOfTransitions(), doc, page);
            this.getArcCPNElement(pn.getListOfArcs(), doc, page);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("encoding", "WINDOWS-1256");
            DOMSource source = new DOMSource(doc);
            System.out.println("Cesta" + outputFile.getAbsolutePath());
            StreamResult result = new StreamResult(outputFile);
            transformer.transform(source, result);

            return true;
        } catch (Exception ex) {
            System.out.println("Error parsing");
            Logger.getLogger(XMLPetriManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void getArcCPNElement(ArrayList<Arc> listOfArcs, Document doc, Element appendElement) {
        for (Arc a : listOfArcs) {
            Element arc = doc.createElement("arc");
            arc.setAttribute("id", "ID" + id);
            id++;
            String orientation = "";
            if (a.getOutElement() instanceof Transition) {
                orientation = "TtoP";
                arc.setAttribute("orientation", orientation);
            } else {
                orientation = "PtoT";
                arc.setAttribute("orientation", orientation);
            }
            arc.setAttribute("order", "1");

            Element posattr = doc.createElement("posattr");
            Attr posattrX = doc.createAttribute("x");
            posattrX.setValue("0.000000");
            Attr posattrY = doc.createAttribute("y");
            posattrY.setValue("0.000000");
            posattr.setAttributeNode(posattrX);
            posattr.setAttributeNode(posattrY);
            arc.appendChild(posattr);

            Element fillattr = doc.createElement("fillattr");
            fillattr.setAttribute("colour", "White");
            fillattr.setAttribute("pattern", "");
            fillattr.setAttribute("filled", "false");
            arc.appendChild(fillattr);

            Element lineattr = doc.createElement("lineattr");
            lineattr.setAttribute("colour", "Black");
            lineattr.setAttribute("thick", "1");
            lineattr.setAttribute("type", "Solid");
            arc.appendChild(lineattr);

            Element textattr = doc.createElement("textattr");
            textattr.setAttribute("colour", "Black");
            textattr.setAttribute("bold", "false");
            arc.appendChild(textattr);

            Element arrowattr = doc.createElement("arrowattr");
            textattr.setAttribute("headsize", "1.200000");
            textattr.setAttribute("currentcyckle", "2");
            arc.appendChild(textattr);

            int idOut = -1;
            int idIn = -1;
            for (AdditionalElementInfo aei : elementInfo) {
                if (a.getOutElement() == aei.getElement()) {
                    idOut = aei.getID();
                } else if (a.getInElement() == aei.getElement()) {
                    idIn = aei.getID();
                }
            }
            if (idOut == -1) {
                System.out.println("CHYBA idOut");
            }
            if (idIn == -1) {
                System.out.println("CHYBA idIN");
            }

            Element transend = doc.createElement("transend");
            Element placeend = doc.createElement("placeend");
            if (a.getOutElement() instanceof AbsPlace) {
                transend.setAttribute("idref", "ID" + idIn);
                placeend.setAttribute("idref", "ID" + idOut);
            } else {
                transend.setAttribute("idref", "ID" + idOut);
                placeend.setAttribute("idref", "ID" + idIn);
            }
            arc.appendChild(transend);
            arc.appendChild(placeend);
            if (a.getBendPoints().size() > 0) {
                 if ("TtoP".equals(orientation)) {
                    for (Point p : a.getBendPoints()) {
                        Element bendPoint = createbendElement(doc, p);
                        arc.appendChild(bendPoint);
                    }
                 } else {
                    for (int i = a.getBendPoints().size()-1; i >= 0; i--) {
                        Point p = a.getBendPoints().get(i);
                        Element bendPoint = createbendElement(doc, p);
                        arc.appendChild(bendPoint);
                    }
                 }
            }

            Element annot = doc.createElement("annot");
            annot.setAttribute("id", "ID" + id);
            id++;

            int x = a.getOutElement().getX() - ((a.getOutElement().getX() - a.getInElement().getX()) / 2);
            int y = a.getOutElement().getY() - ((a.getOutElement().getY() - a.getInElement().getY()) / 2);

            Element typePosAttr = doc.createElement("posattr");
            typePosAttr.setAttribute("x", transformX(x) + "");
            typePosAttr.setAttribute("y", transformY(y) + "");
            annot.appendChild(typePosAttr);

            //nejake chybaju
            Element annotText = doc.createElement("text");
            annotText.setAttribute("tool", "CPN Tools");
            annotText.setAttribute("version", "4.0.0");
            if (a.getOutElement() instanceof Place || a.getInElement() instanceof Place) {
                annotText.appendChild(doc.createTextNode("p"));
            } else {
                annotText.appendChild(doc.createTextNode(a.getCapacity() + ""));
            }
            annot.appendChild(annotText);
            arc.appendChild(annot);

            appendElement.appendChild(arc);
        }
    }

    public Element createbendElement(Document doc, Point p) {
        Element bendPoint = doc.createElement("bendpoint");
        bendPoint.setAttribute("id", "ID" + id);
        id++;
        bendPoint.setAttribute("serial", "1");

        Element bendposattr = doc.createElement("posattr");
        bendposattr.setAttribute("x", transformX(p.x) + ".000000");
        bendposattr.setAttribute("y", transformY(p.y) + ".000000");
        bendPoint.appendChild(bendposattr);

        Element fillattr = doc.createElement("fillattr");
        fillattr.setAttribute("colour", "White");
        fillattr.setAttribute("pattern", "Solid");
        fillattr.setAttribute("filled", "false");
        bendPoint.appendChild(fillattr);

        Element lineattr = doc.createElement("lineattr");
        lineattr.setAttribute("colour", "Black");
        lineattr.setAttribute("thick", "0");
        lineattr.setAttribute("type", "solid");
        bendPoint.appendChild(lineattr);

        Element textattr = doc.createElement("textattr");
        textattr.setAttribute("colour", "Black");
        textattr.setAttribute("bold", "false");
        bendPoint.appendChild(textattr);

        return bendPoint;
    }

    public void getTransCPNElement(ArrayList<Transition> listOfTransitions, Document doc, Element appendElement) {
        for (Transition t : listOfTransitions) {
            Element transition = doc.createElement("trans");
            transition.setAttribute("id", "ID" + id);
            elementInfo.add(new AdditionalElementInfo(id, t));
            id++;

            Element posattr = doc.createElement("posattr");
            Attr posattrX = doc.createAttribute("x");
            posattrX.setValue(transformX(t.getX()) + ".000000");
            Attr posattrY = doc.createAttribute("y");
            posattrY.setValue(transformY(t.getY()) + ".000000");
            posattr.setAttributeNode(posattrX);
            posattr.setAttributeNode(posattrY);
            transition.appendChild(posattr);

            Element fillattr = doc.createElement("fillattr");
            fillattr.setAttribute("colour", "White");
            fillattr.setAttribute("pattern", "");
            fillattr.setAttribute("filled", "false");
            transition.appendChild(fillattr);

            Element lineattr = doc.createElement("lineattr");
            lineattr.setAttribute("colour", "Black");
            lineattr.setAttribute("thick", "1");
            lineattr.setAttribute("type", "Solid");
            transition.appendChild(lineattr);

            Element textattr = doc.createElement("textattr");
            textattr.setAttribute("colour", "Black");
            textattr.setAttribute("bold", "false");
            transition.appendChild(textattr);

            Element text = doc.createElement("text");
            text.appendChild(doc.createTextNode(t.getName()));
            transition.appendChild(text);

            Element box = doc.createElement("box");
            box.setAttribute("w", t.getWidth() + "");
            box.setAttribute("h", t.getHeight() + "");
            transition.appendChild(box);

            appendElement.appendChild(transition);
        }
    }

    public void getPlacesCPNElement(ArrayList<Place> listOfPlaces, ArrayList<Resource> listOfResources, Document doc, Element appendElement) {
        for (Place p : listOfPlaces) {
            Element place = doc.createElement("place");
            place.setAttribute("id", "ID" + id);
            elementInfo.add(new AdditionalElementInfo(id, p));
            id++;

            Element posattr = doc.createElement("posattr");
            Attr posattrX = doc.createAttribute("x");
            posattrX.setValue(transformX(p.getX()) + ".000000");
            Attr posattrY = doc.createAttribute("y");
            posattrY.setValue(transformY(p.getY()) + ".000000");
            posattr.setAttributeNode(posattrX);
            posattr.setAttributeNode(posattrY);
            place.appendChild(posattr);

            Element fillattr = doc.createElement("fillattr");
            fillattr.setAttribute("colour", "White");
            fillattr.setAttribute("pattern", "");
            fillattr.setAttribute("filled", "false");
            place.appendChild(fillattr);

            Element lineattr = doc.createElement("lineattr");
            lineattr.setAttribute("colour", "Black");
            lineattr.setAttribute("thick", "1");
            lineattr.setAttribute("type", "Solid");
            place.appendChild(lineattr);

            Element textattr = doc.createElement("textattr");
            textattr.setAttribute("colour", "Black");
            textattr.setAttribute("bold", "false");
            place.appendChild(textattr);

            Element text = doc.createElement("text");
            text.appendChild(doc.createTextNode(p.getName()));
            place.appendChild(text);

            Element ellipse = doc.createElement("ellipse");
            ellipse.setAttribute("w", p.getWidth() + "");
            ellipse.setAttribute("h", p.getHeight() + "");
            place.appendChild(ellipse);

            Element type = doc.createElement("type");
            type.setAttribute("id", "ID" + id);
            id++;

            Element typePosAttr = doc.createElement("posattr");
            typePosAttr.setAttribute("x", (transformX(p.getX()) + 30) + "");
            typePosAttr.setAttribute("y", (transformY(p.getY()) - 30) + "");
            type.appendChild(typePosAttr);

            Element typeText = doc.createElement("text");
            typeText.setAttribute("tool", "CPN Tools");
            typeText.setAttribute("version", "4.0.0");
            typeText.appendChild(doc.createTextNode("cPI"));
            type.appendChild(typeText);
            place.appendChild(type);

            Element initmark = doc.createElement("initmark");
            initmark.setAttribute("id", "ID" + id);
            id++;
            Element initPosAttr = doc.createElement("posattr");
            initPosAttr.setAttribute("x", (transformX(p.getX()) + 50) + "");
            initPosAttr.setAttribute("y", (transformY(p.getY()) + 30) + "");
            initmark.appendChild(initPosAttr);

            Element initText = doc.createElement("text");
            initText.setAttribute("tool", "CPN Tools");
            initText.setAttribute("version", "4.0.0");
            initText.appendChild(doc.createTextNode(p.getMarkings().getCPNMarking()));
            initmark.appendChild(initText);
            place.appendChild(initmark);

            appendElement.appendChild(place);
        }

        for (Resource r : listOfResources) {
            Element resource = doc.createElement("place");
            resource.setAttribute("id", "ID" + id);
            elementInfo.add(new AdditionalElementInfo(id, r));
            id++;

            Element posattr = doc.createElement("posattr");
            Attr posattrX = doc.createAttribute("x");
            posattrX.setValue(transformX(r.getX()) + ".000000");
            Attr posattrY = doc.createAttribute("y");
            posattrY.setValue(transformY(r.getY()) + ".000000");
            posattr.setAttributeNode(posattrX);
            posattr.setAttributeNode(posattrY);
            resource.appendChild(posattr);

            Element text = doc.createElement("text");
            text.appendChild(doc.createTextNode(r.getName()));
            resource.appendChild(text);

            Element ellipse = doc.createElement("ellipse");
            ellipse.setAttribute("w", r.getWidth() + "");
            ellipse.setAttribute("h", r.getHeight() + "");
            resource.appendChild(ellipse);

            Element type = doc.createElement("type");
            type.setAttribute("id", "ID" + id);
            id++;
            Element typePosAttr = doc.createElement("posattr");
            typePosAttr.setAttribute("x", (transformX(r.getX()) + 50) + "");
            typePosAttr.setAttribute("y", (transformY(r.getY()) + 30) + "");
            type.appendChild(typePosAttr);

            Element typeText = doc.createElement("text");
            typeText.setAttribute("tool", "CPN Tools");
            typeText.setAttribute("version", "4.0.0");
            typeText.appendChild(doc.createTextNode("UNIT"));
            type.appendChild(typeText);
            resource.appendChild(type);

            Element initmark = doc.createElement("initmark");
            initmark.setAttribute("id", "ID" + id);
            id++;

            Element initPosAttr = doc.createElement("posattr");
            initPosAttr.setAttribute("x", (transformX(r.getX()) + 50) + "");
            initPosAttr.setAttribute("y", (transformY(r.getY()) + 30) + "");
            initmark.appendChild(initPosAttr);

            Element initText = doc.createElement("text");
            initText.setAttribute("tool", "CPN Tools");
            initText.setAttribute("version", "4.0.0");
            initText.appendChild(doc.createTextNode(r.getMarking() + ""));
            initmark.appendChild(initText);
            resource.appendChild(initmark);

            appendElement.appendChild(resource);
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

            this.getPlacesFromCPN(doc, pn);
            this.getTransitionsFromCPN(doc, pn);
            this.getArcsFromCPN(doc, pn);
            findResource(pn);

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

        for (int i = 0; i < elementInfo.size(); i++) {
            AdditionalElementInfo nupi = elementInfo.get(i);
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

        for (int index : listOfPlaceTypes) {
            setPlaceMarking(elementInfo.get(index));
        }
        if (type2 != null && "UNIT".equals(type2)) {
            for (int i = 0; i < listOfResourceTypes.size(); i++) {
                Resource r = new Resource(elementInfo.get(listOfResourceTypes.get(i)).getElement().getName());
                r.setX(elementInfo.get(listOfResourceTypes.get(i)).getElement().getX());
                r.setY(elementInfo.get(listOfResourceTypes.get(i)).getElement().getY());
                r.setWidth(((Place) elementInfo.get(listOfResourceTypes.get(i)).getElement()).getWidth());
                r.setHeight(((Place) elementInfo.get(listOfResourceTypes.get(i)).getElement()).getHeight());
                r.setColor(elementInfo.get(listOfResourceTypes.get(i)).getElement().getColor());
                r.setColor2(elementInfo.get(listOfResourceTypes.get(i)).getElement().getColor2());
                r.setFontSize(16);
                try {
                    int count = ((Number) NumberFormat.getInstance().parse((elementInfo).get(listOfResourceTypes.get(i)).getInitmarking())).intValue();
                    r.setMarking(count);
                } catch (ParseException ex) {
                }
                for (Arc a : ((Place) elementInfo.get(listOfResourceTypes.get(i)).getElement()).getListOfOutArcs()) {
                    a.setOutElement(r);
                    r.getListOfOutArcs().add(a);
                }
                for (Arc a : ((Place) elementInfo.get(listOfResourceTypes.get(i)).getElement()).getListOfInArcs()) {
                    a.setInElement(r);
                    r.getListOfInArcs().add(a);
                }
                pn.getListOfPlaces().remove(((Place) elementInfo.get(listOfResourceTypes.get(i)).getElement()));
                pn.addResource(r);
            }
        } else if (type2 != null) {
            ArrayList<Resource> listOfAddedResources = new ArrayList<Resource>();
            AdditionalElementInfo nupi = elementInfo.get(listOfResourceTypes.get(0));
            String[] tokens = nupi.getInitmarking().split("\\+\\+");
            for (int i = 0; i < tokens.length; i++) {
                int count = 1;
                try {
                    count = ((Number) NumberFormat.getInstance().parse(tokens[i])).intValue();
                } catch (ParseException ex) {
                }
                String[] markingPart = tokens[i].split("`");
                String name = markingPart[1];
                Resource r = new Resource(nupi.getElement().getName() + "_" + name);
                r.setX(nupi.getElement().getX());
                r.setY(nupi.getElement().getY() - i * 70);
                r.setWidth(((Place) nupi.getElement()).getWidth());
                r.setHeight(((Place) nupi.getElement()).getHeight());
                r.setColor(nupi.getElement().getColor());
                r.setColor2(nupi.getElement().getColor2());
                r.setMarking(count);

                for (Arc a : ((Place) elementInfo.get(listOfResourceTypes.get(0)).getElement()).getListOfOutArcs()) {
                    for (AdditionalArcInfo aai : arcsInfo) {
                        if (aai.getArc() == a && aai.getText().equals(name)) {
                            a.setOutElement(r);
                            r.getListOfOutArcs().add(a);
                        }
                    }
                }
                for (Arc a : ((Place) elementInfo.get(listOfResourceTypes.get(0)).getElement()).getListOfInArcs()) {
                    for (AdditionalArcInfo aai : arcsInfo) {
                        if (aai.getArc() == a && aai.getText().equals(name)) {
                            a.setInElement(r);
                            r.getListOfInArcs().add(a);
                        }
                    }
                }
                listOfAddedResources.add(r);

            }
            pn.getListOfPlaces().remove(((Place) nupi.getElement()));
            for (int i = 0; i < listOfAddedResources.size(); i++) {
                pn.addResource(listOfAddedResources.get(i));
            }
        }
    }

    public void setPlaceMarking(AdditionalElementInfo nupi) {

        Place p = (Place) nupi.getElement();
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
                String sign = ((Element) eElement.getElementsByTagName("initmark").item(0)).getElementsByTagName("text").item(0).getTextContent();

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
                elementInfo.add(new AdditionalElementInfo(id, text, sign, p));
                pn.addPlace(p);
            }
        }

    }

    public void getTransitionsFromCPN(Document doc, PetriNet pn) {
        NodeList transList = doc.getElementsByTagName("trans");
        for (int i = 0; i < transList.getLength(); i++) {
            Node nNode = transList.item(i);

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
                            a.getBendPoints().add(0, new Point(bendX, bendY));

                        } else {
                            a.getBendPoints().add(new Point(bendX, bendY));
                        }
                    }
                }
                pn.addArc(a);
            }
        }
    }

    private Place getPlace(int id, PetriNet pn) {
        int index = -1;
        for (int i = 0; i < elementInfo.size(); i++) {
            if (elementInfo.get(i).getID() == id) {
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
