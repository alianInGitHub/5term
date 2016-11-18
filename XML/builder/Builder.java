package builder;

import generated.Candy;
import generated.MyString;

import java.util.List;
import java.util.Set;

/**
 * Created by anastasia on 06.11.16.
 */
public class Builder {
    private static String toString(Candy c){
        String s = "";
        s += c.getName() + "\n" + c.getEnergy() + "\n";
        for ( MyString ms: c.getType()) {
            s += ms.getValue() + ", ";
        }
        s += "\n";
        for(MyString ms : c.getIngredient()){
            s += ms.getValue() + ", ";
        }
        s += "\n" + c.getValue().getProtein() + ", " + c.getValue().getCarbonates() + ", " + c.getValue().getFats() + "\n";
        s += c.getProduction() + "\n" + "\n";
        return s;
    }
    public static void main(String[] args){
        //AbstructSweetBuilder builder = new SweetSAXBuilder();
        AbstructSweetBuilder builder = new SweetDOMBuilder();
        //AbstructSweetBuilder builder = new SweetStAXBuilder();
        builder.buildSetCandies("src/Candy.xml");
        builder.sort();
        XMLCreator.writeInroXMLFile("sorted_candies.xml", builder.getCandies());
        List<Candy> list = builder.getCandies();
        for (Candy c: list) {
            // print Result
            System.out.print(toString(c));
        }
    }
}
