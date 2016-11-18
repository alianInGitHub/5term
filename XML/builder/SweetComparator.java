package builder;

import generated.Candy;
import generated.CandyEnum;
import generated.ValueEnum;

import java.util.ArrayList;
import java.util.Comparator;


/**
 * Created by anastasia on 13.11.16.
 */
public class SweetComparator implements Comparator<Candy>{
    private String fieldToCompare;
    private boolean isValue = false;
    public void setFieldToCompare(String fieldToCompare){
        this.fieldToCompare = fieldToCompare;
        for(int i = 0; i < ValueEnum.values().length; i++){
            if(ValueEnum.values()[i].toString() == fieldToCompare){
                isValue = true;
                break;
            }
        }
    }
    @Override
    public int compare(Candy c, Candy t1) {
        if(isValue){
            switch (ValueEnum.valueOf(fieldToCompare)) {
                case PROTEIN:
                    return Float.compare(c.getValue().getProtein(), t1.getValue().getProtein());
                case CARBONATES:
                    return Float.compare(c.getValue().getCarbonates(), t1.getValue().getCarbonates());
                case FATS:
                    return Float.compare(c.getValue().getFats(), t1.getValue().getFats());
                default:
                    break;
            }
        } else {
            switch (CandyEnum.valueOf(fieldToCompare)) {
                case NAME:
                    return c.getName().compareTo(t1.getName());
                case ENERGY:
                    return Integer.compare(c.getEnergy(), t1.getEnergy());
                case INGREDIENT:
                    return Integer.compare(c.getIngredient().size(), t1.getIngredient().size());
                case PRODUCTION:
                    return c.getProduction().compareTo(t1.getProduction());
                default:
                    break;
            }
        }
        return 0;
    }
}
