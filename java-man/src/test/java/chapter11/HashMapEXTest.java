package chapter11;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class HashMapEXTest {

    @DisplayName("HashMap 예제 1")
    @Test
    void hashMap(){
        HashMap<String,Integer> map = new HashMap<>();

        map.put("sejin",25);
        map.put("eojin",23);
        map.put("hihi",30);

        Set set = map.entrySet(); // 엔트리 셋
        System.out.println(set);

        set = map.keySet(); // 키 셋
        System.out.println(set);

        Collection collection = map.values(); // value 값
        System.out.println(collection);
    }

    @DisplayName("HashMap 예제 2")
    @Test
    void hashMap2(){
        String[] data = {"A","K","A","K","D","K","A","K","K","K","Z","D"};

        HashMap<String,Integer> map = new HashMap<>();

        for(int i=0; i<data.length; i++){
            if(map.containsKey(data[i])){
                Integer value = map.get(data[i]);
                map.put(data[i],value+1);
            }
            else{
                map.put(data[i],1);
            }
        }

        Iterator iterator = map.entrySet().iterator();

        while(iterator.hasNext()){
            Map.Entry e = (Map.Entry)iterator.next();
            System.out.println(e.getKey()+" : "+e.getValue());
        }
    }
}