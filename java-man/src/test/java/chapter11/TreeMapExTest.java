package chapter11;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TreeMapExTest {

    @DisplayName("TreeMap 예제")
    @Test
    void treeMap() {
        String[] data = {"A", "K", "A", "K", "D", "K", "A", "K", "K", "K", "Z", "D"};

        TreeMap<String, Integer> map = new TreeMap<>();

        for (int i = 0; i < data.length; i++) {
            if (map.containsKey(data[i])) {
                Integer value = (Integer) (map.get(data[i]));
                map.put(data[i], value + 1);
            } else {
                map.put(data[i], 1);
            }
        }

        Iterator iterator = map.entrySet().iterator();
        System.out.println("=기본 정렬 (키에 의해서) =");
        while (iterator.hasNext()) {
            Map.Entry e = (Map.Entry) (iterator.next());
            int value = (Integer) e.getValue();
            System.out.println(e.getKey() + " : " + value);
        }
        System.out.println();

        //map을 Collection으로 변환한 후 정렬  -> Comparator를 사용하여 정렬기준을 생성
        Set set = map.entrySet();
        List list = new ArrayList<>(set);
        Collections.sort(list, new ValueComparator());

        iterator = list.iterator();
        System.out.println("=값에 의한 내림차순 정렬=");
        while (iterator.hasNext()) {
            Map.Entry e = (Map.Entry) (iterator.next());
            int value = (Integer) e.getValue();
            System.out.println(e.getKey() + " : " + value);
        }
        System.out.println();
    }
}