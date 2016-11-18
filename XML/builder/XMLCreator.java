package builder;

import generated.Candy;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anastasia on 01.11.16.
 */
public class XMLCreator {
    public static void writeInroXMLFile(String fileName, List<Candy> candies){

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = documentBuilder.newDocument();
        String root = "candies";
        Element rootElement = document.createElement(root);
        document.appendChild(rootElement);
        for (Candy c: candies) {
            //name
            Element elementName = document.createElement("name");
            elementName.appendChild(document.createTextNode(c.getName()));

            //energy
            Element elementEnergy = document.createElement("energy");
            elementEnergy.appendChild(document.createTextNode(String.valueOf(c.getEnergy())));

            //type
            Element elementType[] = new Element[c.getType().size()];
            for(int j = 0; j < c.getType().size(); j++){
                elementType[j] = document.createElement("type");
                elementType[j].appendChild(document.createTextNode(c.getType().get(j).getValue()));
            }

            //ingredients
            Element elementIngredient[] = new Element[c.getIngredient().size()];
            for(int j = 0; j < c.getIngredient().size(); j++){
                elementIngredient[j] = document.createElement("ingredient");
                elementIngredient[j].appendChild(document.createTextNode(c.getIngredient().get(j).getValue()));
            }

            //value
            Element elementValue = document.createElement("value");
            Element elementProtein = document.createElement("protein");
            elementProtein.appendChild(document.createTextNode(String.valueOf(c.getValue().getProtein())));
            Element elementCarbo = document.createElement("carbonates");
            elementCarbo.appendChild(document.createTextNode(String.valueOf(c.getValue().getCarbonates())));
            Element elementFats = document.createElement("fats");
            elementFats.appendChild(document.createTextNode(String.valueOf(c.getValue().getFats())));
            elementValue.appendChild(elementProtein);
            elementValue.appendChild(elementCarbo);
            elementValue.appendChild(elementFats);

            //production
            Element elementProduction = document.createElement("production");
            elementProduction.appendChild(document.createTextNode(c.getProduction()));

            //append children to document
            rootElement.appendChild(elementName);
            rootElement.appendChild(elementEnergy);
            for(int j = 0; j < elementType.length; j++){
                rootElement.appendChild(elementType[j]);
            }
            for(int j = 0; j < elementIngredient.length; j++){
                rootElement.appendChild(elementIngredient[j]);
            }
            rootElement.appendChild(elementValue);
            rootElement.appendChild(elementProduction);
        }
        for(int i = 0; i < 1; i++){

        }
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new FileWriter(fileName));
            transformer.transform(source, result);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
