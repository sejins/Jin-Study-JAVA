package chapter12;

public class Juicer {

//    public static  Juice makeJuice(FruitBox<? extends Fruit> box){
//
//        String tmp="";
//
//        for(Fruit f : box.getList())
//            tmp+=(f.toString()+" ");
//        return new Juice(tmp);
//    }

    // 지네릭 메서드로 표현
    public static <T extends Fruit> Juice makeJuice(FruitBox<T> box){

        String tmp="";

        for(Fruit f : box.getList())
            tmp+=(f.toString()+" ");
        return new Juice(tmp);
    }
}
