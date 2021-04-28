package chapter14.stream;

import java.util.List;
import java.util.stream.Stream;

public class StreamEx {

    //StreamExTest

    static  <T> void thisIsTestMethod(Stream<? extends List<Parent>> wild){ // ? : List<Parent> , ArrayList<Parent> ....
        System.out.println("This is a test.");
    }

    static  <T> void thisIsAnotherTestMethod(Stream<? extends List<? extends Parent>> wild){ // ? : List<Parent> , ArrayList<Parent>, List<Child>, ArrayList<Child> ....
        // 이제 진짜로 제네릭 좀 치는듯??
        System.out.println("This is a test.");
    }
}

