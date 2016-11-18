package builder;

import builder.AbstructSweetBuilder;
import generated.Candy;
import generated.CandyEnum;
import generated.MyString;
import generated.Value;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by anastasia on 01.11.16.
 */
public class SweetDOMBuilder extends AbstructSweetBuilder {
    //private Set<Candy> candies;
    private DocumentBuilder builder;
    public SweetDOMBuilder() {
        candies = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void buildSetCandies(String filename) {
        Document doc = null;
        try {
            doc = builder.parse(filename);
            Element root = doc.getDocumentElement();
            NodeList list = root.getElementsByTagName("candy");
            for(int i = 0; i < list.getLength(); i++) {
                Node n = list.item(i);
                Candy c = buildCandy(list.item(i));
                candies.add(c);
            }
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Candy buildCandy(Node candyNode) {
        Candy candy = new Candy();
        candy.setProduction(candyNode.getAttributes().item(0).getTextContent());
        candy.setName(getElementTextContent((Element) candyNode, "name").get(0));
        candy.setEnergy(Integer.valueOf(getElementTextContent((Element)candyNode, "energy").get(0)));
        MyString ms;
        ArrayList<String> temp = getElementTextContent((Element) candyNode, "ingredient");
        for (String s:temp) {
            ms = new MyString();
            ms.setValue(s);
            candy.getIngredient().add(ms);
        }
        temp = getElementTextContent((Element) candyNode, "type");
        for (String s:temp) {
            ms = new MyString();
            ms.setValue(s);
            candy.getType().add(ms);
        }
        Value v = new Value();
        Node valueNode = ((Element) candyNode).getElementsByTagName("value").item(0);
        v.setProtein(Float.valueOf(valueNode.getAttributes().getNamedItem("protein").getTextContent()));
        v.setCarbonates(Float.valueOf(valueNode.getAttributes().getNamedItem("carbonates").getTextContent()));
        v.setFats(Float.valueOf(valueNode.getAttributes().getNamedItem("fats").getTextContent()));
        candy.setValue(v);
        return  candy;
    }
    public List<Candy> getCandies(){return candies;}

    private static ArrayList<String> getElementTextContent(Element elem, String tag){
        NodeList nodeList = elem.getElementsByTagName(tag);
        ArrayList<String> list = new ArrayList<>();
        switch (CandyEnum.valueOf(tag.toUpperCase())) {
            case TYPE:
                for(int i = 0; i < nodeList.getLength(); i++){
                Node n = nodeList.item(i).getAttributes().item(0);
                list.add(n.getTextContent());
            }
                break;
            case INGREDIENT:
                for(int i = 0; i < nodeList.getLength(); i++){
                    Node n = nodeList.item(i).getAttributes().item(0);
                    list.add(n.getTextContent());
                }
                break;
            default:
                for(int i = 0; i < nodeList.getLength(); i++){
                    list.add(nodeList.item(i).getTextContent());
                }
                break;
        }
        return list;
    }
}
