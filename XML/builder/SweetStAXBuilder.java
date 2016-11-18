package builder;

import builder.AbstructSweetBuilder;
import generated.*;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by anastasia on 01.11.16.
 */
public class SweetStAXBuilder extends AbstructSweetBuilder {

    //private HashSet<Candy> candies = new HashSet<>();
    private XMLInputFactory factory = null;
    public SweetStAXBuilder(){
        factory = XMLInputFactory.newFactory();
    }
    @Override
    public List<Candy> getCandies(){
        return candies;
    }
    @Override
    public void buildSetCandies(String filename) {
        FileInputStream inputStream = null;
        XMLStreamReader reader = null;
        String name;
        try {
            inputStream = new FileInputStream(new File(filename));
            reader = factory.createXMLStreamReader(inputStream);
            if(reader.hasNext())
                reader.next();
            while (reader.hasNext()){
                int type = reader.next();
                if(type == XMLStreamConstants.START_ELEMENT){
                    name = reader.getLocalName();
                    //we create only 1 object
                    if(CandyEnum.CANDY == CandyEnum.valueOf(name.toUpperCase())) {
                        Candy sweety = buildCandy(reader);
                        candies.add(sweety);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                //cannot close the file
                e.printStackTrace();
            }
        }
    }

    // private fields

    private Candy buildCandy(XMLStreamReader reader){
        Candy sweety = new Candy();
        sweety.setProduction(reader.getAttributeValue(0));
        String s;
        try {
            while (reader.hasNext()){
                int type = reader.next();
                s = reader.getLocalName();
                switch (type){
                    case XMLStreamConstants.START_ELEMENT:{
                        MyString ms = new MyString();
                        switch (CandyEnum.valueOf(s.toUpperCase())) {
                            case NAME:
                                sweety.setName(getXMLText(reader));
                                break;
                            case ENERGY:
                                sweety.setEnergy(Integer.parseInt(getXMLText(reader)));
                                break;
                            case TYPE:
                                ms.setValue(reader.getAttributeValue(0));
                                sweety.getType().add(ms);
                                break;
                            case INGREDIENT:
                                ms.setValue(reader.getAttributeValue(0));
                                sweety.getIngredient().add(ms);
                                break;
                            case VALUE:
                                sweety.setValue(getXMLValue(reader));
                                break;
                            case PRODUCTION:
                                sweety.setProduction(getXMLText(reader));
                                break;
                            case CANDY:
                                return sweety;
                            default:
                                System.out.println("Unknown symbol found while parsing xml file");
                                break;
                        }
                    }
                        break;
                    case XMLStreamConstants.END_ELEMENT:{
                        if(CandyEnum.CANDY == CandyEnum.valueOf(s.toUpperCase())){
                            return sweety;
                        }
                    }
                    break;
                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return sweety;
    }

    private Value getXMLValue(XMLStreamReader reader) {
        Value v = new Value();
        v.setProtein(Float.valueOf(reader.getAttributeValue(0)));
        v.setCarbonates(Float.valueOf(reader.getAttributeValue(1)));
        v.setFats(Float.valueOf(reader.getAttributeValue(2)));
        return v;
    }

    private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if(reader.hasNext()){
            reader.next();
            text = reader.getText();
        }
        return text;
    }
}
