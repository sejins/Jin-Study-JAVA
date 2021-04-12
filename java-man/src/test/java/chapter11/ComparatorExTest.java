package chapter11;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ComparatorExTest {

    @DisplayName("Comparator 테스트")
    @Test
    void ComparatorEx(){
        ComparatorEx p1 = new ComparatorEx(1,2);
        ComparatorEx p2 = new ComparatorEx(1,5);
        ComparatorEx p3 = new ComparatorEx(4,3);

        // 익명클래스 사용
        Comparator<ComparatorEx> comparator = new Comparator<ComparatorEx>() {
            @Override
            public int compare(ComparatorEx o1, ComparatorEx o2) {
                if (o1.getX() > o2.getX()) // x에 대해서 먼저 오름차순 정렬
                    return 1;
                else if (o1.getX() == o2.getX()) // y에 대해서 먼저 내림차순 정렬
                {if(o1.getY() < o2.getY())
                        return 1;
                else
                    return -1;
                }
                else
                    return -1;
            }
        };

        List<ComparatorEx> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        list.add(p3);
        Collections.sort(list,comparator);

        for(ComparatorEx c : list){
            System.out.println(c);
        }
    }
}