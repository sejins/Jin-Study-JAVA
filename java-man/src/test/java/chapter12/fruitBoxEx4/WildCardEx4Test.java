package chapter12.fruitBoxEx4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class WildCardEx4Test {

    @DisplayName(" 와일드 카드 예제_<? super T> ")
    @Test
    void wilecardEx_super(){

        FruitBox<Apple> appleBox = new FruitBox<>();
        FruitBox<Grape> grapeBox = new FruitBox<>();

        appleBox.add(new Apple("GreenApple",300));
        appleBox.add(new Apple("GreenApple",100));
        appleBox.add(new Apple("GreenApple",200));

        grapeBox.add(new Grape("GreenGrape",400));
        grapeBox.add(new Grape("GreenGrape",300));
        grapeBox.add(new Grape("GreenGrape",200));

//        static <T> void sort(List<T> list, Comparator<? super T>)  -> 와일드 카드를 사용해서 재사용이 용이하도록 API가 설계가 돼있음.
        Collections.sort(appleBox.getList(), new AppleComparator());
        Collections.sort(grapeBox.getList(), new GrapeComparator());
        System.out.println(appleBox);
        System.out.println(grapeBox);
        System.out.println();
        Collections.sort(appleBox.getList(), new FruitComparator());
        Collections.sort(grapeBox.getList(), new FruitComparator());
        System.out.println(appleBox);
        System.out.println(grapeBox);


    }
}