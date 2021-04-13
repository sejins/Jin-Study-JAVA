package chapter11;

import java.util.Comparator;
import java.util.Map;

public class ValueComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        if(o1 instanceof Map.Entry && o2 instanceof Map.Entry){
            Map.Entry e1 = (Map.Entry)o1;
            Map.Entry e2 = (Map.Entry)o2;

            return (int)(e2.getValue()) - (int)(e1.getValue());
        }
        return -1;
    }
}
