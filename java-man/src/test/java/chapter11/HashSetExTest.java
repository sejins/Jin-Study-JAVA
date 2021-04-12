package chapter11;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class HashSetExTest {

    @DisplayName("HashSet 중복값 저장 테스트 equals,hashCode 메서드 오버라이딩 하지 않았을 때")
    @Test
    void hashSetTest_no_equals_hashCode(){
        Person p1 = new Person("sejin",25);
        Person p2 = new Person("Eojin",23);
        Person p3 = new Person("sejin",25);

        Set<Person> set = new HashSet<>();
        System.out.println(set.add(p1));
        System.out.println(set.add(p2));
        System.out.println(set.add(p3));

        for(Person p : set){
            System.out.println(p);
        }
    }

    @DisplayName("HashSet 중복값 저장 테스트 equals,hashCode 메서드 오버라이딩 했을 때")
    @Test
    void hashSetTest_with_equals_hashCode(){
        Person2 p1 = new Person2("sejin",25);
        Person2 p2 = new Person2("Eojin",23);
        Person2 p3 = new Person2("sejin",25);

        Set<Person2> set = new HashSet<>();
        System.out.println(set.add(p1));
        System.out.println(set.add(p2));
        System.out.println(set.add(p3));

        for(Person2 p : set){
            System.out.println(p);
        }
    }
}