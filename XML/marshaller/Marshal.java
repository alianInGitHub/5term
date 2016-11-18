package marshaller;

import generated.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by anastasia on 03.11.16.
 */
public class Marshal {
    public static void main(String[] args) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Candies.class);
        Marshaller marshaller = context.createMarshaller();
        Candies candies = new Candies();
        //List<Candy> candies = new ArrayList<Candy>();
        Candy c = new Candy();
        c.setName("SouthNight");
        c.setEnergy(380);
        MyString s = new MyString();
        s.setValue("Sugar");
        c.getIngredient().add(s);
        s = new MyString();
        s.setValue("apple puree");
        c.getIngredient().add(s);
        s = new MyString();
        s.setValue("vegetable fats");
        c.getIngredient().add(s);
        s = new MyString();
        s.setValue("concentrated juice");
        c.getIngredient().add(s);
        s = new MyString();
        s.setValue("cacao");
        c.getIngredient().add(s);
        s = new MyString();
        s.setValue("chocolate");
        c.getType().add(s);
        s = new MyString();
        s.setValue("jelly");
        c.getType().add(s);
        Value v = new Value();
        v.setProtein(1.5f);
        v.setCarbonates(76.0f);
        v.setFats(3.6f);
        c.setValue(v);
        c.setProduction("Roshen");
        candies.getCandy().add(c);

        c = new Candy();
        c.setName("Korivka");
        c.setEnergy(370);
        s = new MyString();
        s.setValue("sugar");
        c.getIngredient().add(s);
        s = new MyString();
        s.setValue("milk");
        c.getIngredient().add(s);
        s = new MyString();
        s.setValue("vegetable fats");
        c.getIngredient().add(s);
        s = new MyString();
        s.setValue("concentrated juice");
        c.getIngredient().add(s);
        s = new MyString();
        s.setValue("caramel");
        c.getType().add(s);
        v = new Value();
        v.setProtein(2.5f);
        v.setCarbonates(79.0f);
        v.setFats(4.0f);
        c.setValue(v);
        c.setProduction("Roshen");
        candies.getCandy().add(c);

        c = new Candy();
        c.setName("A1");
        c.setEnergy(370);
        s = new MyString();
        s.setValue("sugar");
        c.getIngredient().add(s);
        s = new MyString();
        s.setValue("apple puree");
        c.getIngredient().add(s);
        s = new MyString();
        s.setValue("vegetable fats");
        c.getIngredient().add(s);
        s = new MyString();
        s.setValue("concentrated juice");
        c.getIngredient().add(s);
        s = new MyString();
        s.setValue("ahahaha");
        c.getIngredient().add(s);
        s = new MyString();
        s.setValue("caramel");
        c.getType().add(s);
        s = new MyString();
        s.setValue("opapapa");
        c.getType().add(s);
        v = new Value();
        v.setProtein(3.5f);
        v.setCarbonates(100.0f);
        v.setFats(6.0f);
        c.setValue(v);
        c.setProduction("AVK");
        candies.getCandy().add(c);

        c = new Candy();
        c.setName("BB");
        c.setEnergy(370);
        s = new MyString();
        s.setValue("sugar");
        c.getIngredient().add(s);
        s = new MyString();
        s.setValue("vegetable fats");
        c.getIngredient().add(s);
        s = new MyString();
        s.setValue("butter");
        c.getIngredient().add(s);;
        s = new MyString();
        s.setValue("duru");
        c.getType().add(s);
        s = new MyString();
        s.setValue("one more");
        c.getType().add(s);
        s = new MyString();
        s.setValue("smth crazy");
        c.getType().add(s);
        v = new Value();
        v.setProtein(0.5f);
        v.setCarbonates(79.0f);
        v.setFats(10.0f);
        c.setValue(v);
        c.setProduction("Svitoch");
        candies.getCandy().add(c);
        try {
            marshaller.marshal(candies, new FileOutputStream("src/Candy.xml"));
            marshaller.marshal(candies, System.out); // to console ^_^
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
