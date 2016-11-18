package builder;

import builder.AbstructSweetBuilder;
import generated.Candy;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.util.List;
import java.util.Set;


/**
 * Created by anastasia on 01.11.16.
 */
public class SweetSAXBuilder extends AbstructSweetBuilder {
    //private Set<Candy> candySet;
    @Override
    public void buildSetCandies(String filename) {
        SweetHandler sh = new SweetHandler();
        try {
            XMLReader reader = XMLReaderFactory.createXMLReader();
            reader.setContentHandler(sh);
            reader.parse(filename);

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        candies = sh.getcandyList();
    }
    public List<Candy> getCandies(){
        return candies;
    }

}
