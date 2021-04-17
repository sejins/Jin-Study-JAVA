package chapter12;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenericsAllAboutExTest {

    @DisplayName("지네릭에서 헷갈리는거 전부 해보기!")
    @Test
    void genericsAllAbout(){


        // 지네릭 메서드 makeJuice  -> 지네릭 타입의 다형성을 통해 오버로딩
        Juicer.makeJuice(new FruitBox<Fruit>()); // 가능
        Juicer.makeJuice(new FruitBox<Apple>()); // 가능
        Juicer.makeJuice(new FruitBox<Grape>()); // 가능

        // 지네릭 클래스를 raw 타입으로 생성하기
        FruitBox fruitFruitBox = new FruitBox();
        fruitFruitBox.add(15); // raw 타입으로 생성하니까 Fruit 하위 타입이 아닌것도 들어간다.  -> 컴파일시에 아무런 오류없이 동작한다.
        fruitFruitBox.add("hello"); // 절대 raw 타입을 사용하지 말라고한다.  -> 런타임에서 문제가 발생할 수 있다.
        // raw 타입은 지네릭 이전과의 호환성을 위해서 존재하는 것일 뿐, 지네릭 타입에 대해서 신경쓰고 싶지 않은경우 raw 타입이 아닌 비한정적 와일드 카드를 사용하자
        System.out.println(fruitFruitBox.getList());



        // 지네릭 클래스 다형성
        FruitBox<Apple> appleBox = new FruitBox<>(); // 가능
        Box<Apple> aBox = new FruitBox<Apple>(); // 가능

//        FruitBox<Fruit> fruitBox = new FruitBox<Apple>(); // 에러.  --> 타입 파라미터는 상속관계 적용 안됨.
//        Box<Fruit> fbox = new FruitBox<Apple>(); // 에러. --> 마찬가지로 에러


        FruitBox<? extends Fruit> fruitBox = new FruitBox<>(); // FruitBox<? extends Fruit> fruitBox = new FruitBox<Fruit>(); 와 동일
        fruitBox = new FruitBox<Apple>(); // 가능
        fruitBox = new FruitBox<Grape>(); // 가능
        fruitBox = new FruitBox<Fruit>(); // 가능
    }
}