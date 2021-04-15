package chapter12;

public class Juicer {

    public static Juice makeJuice(FruitBox<? extends Fruit> box){

        String tmp="";

        for(Fruit f : box.getList())
            tmp+=(f.toString()+" ");
        return new Juice(tmp);
    }
}
