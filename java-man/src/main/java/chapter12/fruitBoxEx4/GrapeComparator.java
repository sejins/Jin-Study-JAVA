package chapter12.fruitBoxEx4;

import java.util.Comparator;

public class GrapeComparator implements Comparator<Grape> {

    @Override
    public int compare(Grape o1, Grape o2) {
        return o2.getWeight() - o1.getWeight();
    }
}
