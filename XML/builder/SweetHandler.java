package builder;

import generated.Candy;
import generated.CandyEnum;
import generated.MyString;
import generated.Value;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;

/**
 * Created by anastasia on 03.11.16.
 */
public class SweetHandler extends DefaultHandler {
    private List<Candy> candyList;
    private Candy current = null;
    private EnumSet<CandyEnum> candyEnumSet = null;
    private CandyEnum currentEnum = null;
    SweetHandler(){
        candyList = new ArrayList<>();
        candyEnumSet = EnumSet.range(CandyEnum.CANDY, CandyEnum.PRODUCTION);
    }
    public void startDocument(){
        System.out.println("Start parsing...");
    }

    public void endDocument(){
        System.out.println("\nFinish parsing!");
    }

    /**
     *  Will be called when analyser process the content of the opening tag
     * @param uri           unique name of namespace
     * @param localName     name of element without prefix ("ne=")
     * @param qName         full name of element with prefix
     * @param attributes    list of attributes
     */
    public void startElement(String uri, String localName, String qName, Attributes attributes){
        if(!localName.equals("candies")) {
            if (localName.equals("candy")) {
                current = new Candy();
                current.setProduction(attributes.getValue(0));
            } else {
                CandyEnum temp = CandyEnum.valueOf(localName.toUpperCase());
                    MyString ms = new MyString();
                    switch (temp) {
                        case TYPE:
                            ms.setValue(attributes.getValue(0));
                            current.getType().add(ms);
                            break;
                        case INGREDIENT:
                            ms.setValue(attributes.getValue(0));
                            current.getIngredient().add(ms);
                            break;
                        case VALUE:
                            if (attributes.getLength() == 3) {
                                Value v = new Value();
                                v.setProtein(Float.valueOf(attributes.getValue(0)));
                                v.setCarbonates(Float.valueOf(attributes.getValue(1)));
                                v.setFats(Float.valueOf(attributes.getValue(2)));
                                current.setValue(v);
                            }
                            break;
                        default:
                            currentEnum = temp;
                            break;
                    }
            }
        }
    }

    /**
     *  Signal the end of element
     * @param uri           unique name of namespace
     * @param localName     name of element without prefix ("ne=")
     * @param qName         full name of element with prefix
     */
    public void endElement(String uri, String localName, String qName){
        if(localName.equals("candy")){
            candyList.add(current);
        }
    }

    /**
     *  Called in case, if analyser met character information in the tag context
     * @param chars
     * @param start
     * @param length
     */
    public void characters(char[] chars, int start, int length){
        //System.out.print(new String(chars, start, length));
        String s = new String(chars, start, length);
        MyString ms = new MyString();
        if(currentEnum != null){
            switch (currentEnum){
                case NAME:
                    current.setName(s);
                    break;
                case ENERGY:
                    current.setEnergy(Integer.valueOf(s));
                    break;
                case TYPE:
                    ms.setValue(s);
                    current.getType().add(ms);
                    break;
                case INGREDIENT:
                    ms.setValue(s);
                    current.getType().add(ms);
                    break;
                case VALUE:
                    System.out.println("Error in 45");
                    break;
                case PRODUCTION:
                    current.setProduction(s);
                    break;
                default:
                    System.out.println("Constant is not present");
                    break;
            }
            currentEnum = null;
        }
    }

    public List<Candy> getcandyList() {
        return candyList;
    }

    /**
     * Created by anastasia on 04.11.16.
     */
    public static class Main {
        public static void main(String[] args){
            AbstructSweetBuilder builder = new SweetSAXBuilder();
            builder.buildSetCandies("caaandy.xml");
            System.out.print(builder.toString());
        }
    }
}
