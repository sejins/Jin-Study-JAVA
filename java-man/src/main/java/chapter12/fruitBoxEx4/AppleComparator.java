package chapter12.fruitBoxEx4;

import java.util.Comparator;

public class AppleComparator implements Comparator<Apple> {

    @Override
    public int compare(Apple o1, Apple o2) {
        return o2.getWeight() - o1.getWeight();
    }
}
