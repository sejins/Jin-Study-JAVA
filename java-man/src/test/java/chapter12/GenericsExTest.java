package chapter12;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenericsExTest {

    @DisplayName("지네릭 클래스의 사용")
    @Test
    void genericsEx(){
        Box<Fruit> fruitBox= new Box<>();
        Box<Apple> appleBox= new Box<>();
        Box<Toy> toyBox= new Box<>();
//        Box<Grape> grapeBox= new Box<Apple>(); //에러.

        fruitBox.add(new Fruit());
        fruitBox.add(new Apple());
        fruitBox.add(new Grape());
//        fruitBox.add(new Toy()); // 에러.

        appleBox.add(new Apple());
//        appleBox.add(new Fruit()); // 에러.
//        appleBox.add(new Toy()); // 에러.

        toyBox.add(new Toy());
//        toyBox.add(new Fruit()); //에러.
//        toyBox.add(new Apple()); //에러.

        System.out.println(fruitBox);
        System.out.println(appleBox);
        System.out.println(toyBox);
    }

    @DisplayName("제한된 지네릭 클래스 사용")
    @Test
    void generics_restrictedEx(){
        FruitBox<Fruit> fruitBox = new FruitBox<>();
        FruitBox<Apple> appleBox = new FruitBox<>();
        FruitBox<Grape> grapeBox = new FruitBox<>();
//        FruitBox<Toy> toyBox = new FruitBox<>(); // 에러.
//        FruitBox<Grape> grapeBox2 = new FruitBox<Apple>(); // 에러.

        fruitBox.add(new Fruit());
        fruitBox.add(new Apple());
        fruitBox.add(new Grape());
        appleBox.add(new Apple());
//        appleBox.add(new Grape()); // 에러.
        grapeBox.add(new Grape());

        System.out.println(fruitBox);
        System.out.println(appleBox);
        System.out.println(grapeBox);
    }

    @DisplayName("와일드 카드 예제_<? extends T>")
    @Test
    void wildCardEx_extends(){

        FruitBox<Fruit> fruitBox = new FruitBox<>();
        FruitBox<Apple> appleBox = new FruitBox<>();

        fruitBox.add(new Apple());
        fruitBox.add(new Grape());

        appleBox.add(new Apple());
        appleBox.add(new Apple());

        Juice fruitJuice = Juicer.makeJuice(fruitBox);
        Juice appleJuice = Juicer.makeJuice(appleBox); // 와일드카드를 통해서 오버로딩 했기 때문에 가능함

        assertNotNull(fruitJuice);
        assertNotNull(appleJuice);

        System.out.println(fruitJuice);
        System.out.println(appleJuice);
    }
}