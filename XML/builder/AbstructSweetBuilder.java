package builder;

import java.util.*;

import generated.*;

/**
 * Created by anastasia on 02.11.16.
 */
public abstract class AbstructSweetBuilder {
    protected List<Candy> candies;
    public AbstructSweetBuilder(){
        candies = new ArrayList<>();
    }
    public AbstructSweetBuilder(List<Candy> candies){
        this.candies = candies;
    }
    public List<Candy> getCandies(){
        return candies;
    }
    public String toString(){
        String s = "";
        for (Candy i:candies) {
            s += i.toString() + "\n";
        }
        return  s;
    }
    public abstract void buildSetCandies(String filename);
    public void sort(){
        if(!candies.isEmpty()){
            System.out.print("Choose field to sort : \n");
            System.out.println(Candy.class.toString());
            System.out.print("1\t" + "name" + "\n");
            System.out.print("2\t" + "type" + "\n");
            System.out.print("3\t" + "production" + "\n");
            System.out.print("4\t" + "proteins" + "\n");
            System.out.print("5\t" + "carbonates\n");
            System.out.print("6\t" + "fats\n");
            System.out.print("7\t" + "energy" + "\n");
            System.out.print("8\t" + "ingredients amount\n");
            System.out.print("variant: ");
            Scanner scanner = new Scanner(System.in);
            boolean scanning = true;
            int option;
            SweetComparator comparator = new SweetComparator();
            while (scanning){
                option = scanner.nextInt();
                switch (option){
                    case 1:
                        comparator.setFieldToCompare(CandyEnum.NAME.toString());
                        break;
                    case 2:
                        comparator.setFieldToCompare(CandyEnum.TYPE.toString());
                        break;
                    case 3:
                        comparator.setFieldToCompare(CandyEnum.PRODUCTION.toString());
                        break;
                    case 4:
                        comparator.setFieldToCompare(ValueEnum.PROTEIN.toString());
                        break;
                    case 5:
                        comparator.setFieldToCompare(ValueEnum.CARBONATES.toString());
                        break;
                    case 6:
                        comparator.setFieldToCompare(ValueEnum.FATS.toString());
                        break;
                    case 7:
                        comparator.setFieldToCompare(CandyEnum.ENERGY.toString());
                        break;
                    case 8:
                        comparator.setFieldToCompare(CandyEnum.INGREDIENT.toString());
                        break;
                    default:
                        System.out.print("Try again!");
                        continue;
                }
                scanning = false;
            }
            candies.sort(comparator);
        }

    }
}
