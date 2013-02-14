/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FileCreator;

import Core.Arc;
import Core.PetriNet;
import Core.Place;
import Core.Transition;
import java.io.File;
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

/**
 *
 * @author Atarin
 */
public class XMLFileCreator {

    DocumentBuilderFactory docFactory;
    DocumentBuilder docBuilder;
    Document doc;

    public XMLFileCreator() {
        try {
            docFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docFactory.newDocumentBuilder();

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLFileCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean createPetriXML(PetriNet pn) {
        try {
            Document doc = (Document) docBuilder.newDocument();
            Element rootElement = doc.createElement("process");
            doc.appendChild(rootElement);

            Element title = doc.createElement("title");
            title.appendChild(doc.createTextNode(pn.getName()));
            rootElement.appendChild(title);

            Element resources = this.getResourcesElement(doc);
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
            
            StreamResult result = new StreamResult(new File("C:\\file.xml"));
            transformer.transform(source, result);

            return true;
        } catch (TransformerException ex) {
            Logger.getLogger(XMLFileCreator.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private Element getEdgesElement(ArrayList<Arc> listOfArcs, Document doc) {
        Element edges = doc.createElement("edges");

        for (Arc a : listOfArcs) {
            Element edge = doc.createElement("edge");

            String type = "";
            if (a.getOutElement() instanceof Transition) {
                type = "TP";
            } else {
                type = "PT";
            }

            Attr edgeType = doc.createAttribute("type");
            edgeType.setValue(type);
            edge.setAttributeNode(edgeType);

            Attr X1 = doc.createAttribute("x1");
            //X1.setValue(a.getOutElement().getDiagramElement().getX()+"");
            X1.setValue("");
            edge.setAttributeNode(X1);

            Attr Y1 = doc.createAttribute("y1");
            //Y1.setValue(a.getOutElement().getDiagramElement().getY()+"");
            Y1.setValue("");
            edge.setAttributeNode(Y1);

            Attr X2 = doc.createAttribute("x2");
            //X2.setValue(a.getInElement().getDiagramElement().getX()+"");
            X2.setValue("");
            edge.setAttributeNode(X2);

            Attr Y2 = doc.createAttribute("y2");
            //Y2.setValue(a.getInElement().getDiagramElement().getY()+"");
            Y2.setValue("");
            edge.setAttributeNode(Y2);

            Attr power = doc.createAttribute("power");
            //?????????????
            power.setValue("1");
            edge.setAttributeNode(power);

            Attr resourceProfession = doc.createAttribute("ResourceProfession");
            //?????????????
            resourceProfession.setValue("");
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
            //X.setValue(p.getDiagramElement().getX()+"");
            X.setValue("");
            place.setAttributeNode(X);

            Attr Y = doc.createAttribute("y");
            //Y.setValue(p.getDiagramElement().getY()+"");
            Y.setValue("");
            place.setAttributeNode(Y);

            Attr tokens = doc.createAttribute("tokens");
            tokens.setValue(" - ");
            place.setAttributeNode(tokens);

            Attr start = doc.createAttribute("start");
            //?????????????
            start.setValue("no");
            place.setAttributeNode(start);

            Attr end = doc.createAttribute("end");
            //?????????????
            end.setValue("no");
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
            //X.setValue(p.getDiagramElement().getX()+"");
            X.setValue("");
            transition.setAttributeNode(X);

            Attr Y = doc.createAttribute("y");
            //Y.setValue(p.getDiagramElement().getY()+"");
            Y.setValue("");
            transition.setAttributeNode(Y);

            transitions.appendChild(transition);
        }
        return transitions;
    }

    private Element getResourcesElement(Document doc) {
        Element resources = doc.createElement("resources");

        Element resource = doc.createElement("resource");

        Attr resName = doc.createAttribute("name");
        resName.setValue("1");
        resource.setAttributeNode(resName);

        Attr quantity = doc.createAttribute("quantity");
        quantity.setValue("1");
        resource.setAttributeNode(quantity);

        Attr resX = doc.createAttribute("x");
        resX.setValue("1");
        resource.setAttributeNode(resX);

        Attr resY = doc.createAttribute("y");
        resY.setValue("1");
        resource.setAttributeNode(resY);
        resources.appendChild(resource);
        
        return resources;
    }
}