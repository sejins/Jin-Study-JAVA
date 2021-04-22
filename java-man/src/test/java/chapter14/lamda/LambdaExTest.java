package chapter14.lamda;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.lang.System.out;

class LambdaExTest {

    @DisplayName("람다식 예제 1")
    @Test
    void lambdaTest1(){

        // 람다식으로 Myfunction의 myMethod를 구현
        Myfunction f1 = () -> System.out.println("This is a f1 ");

        // 익명 클래스로 Myfunction의 myMethod를 구현
        Myfunction f2 = new Myfunction() {
            @Override
            public void myMethod() { // 인터페이스의 추상 메서드를 구현하는 것이기 때문에 public을 반드시 지정해줘야한다.
                System.out.println("This is a f2");
            }
        };

        Myfunction f3 = LambdaEx.getMyFunction();

        f1.myMethod();
        f2.myMethod();
        f3.myMethod();

        LambdaEx.execute(f1);
        LambdaEx.execute(f2);
        LambdaEx.execute(f3);
        LambdaEx.execute(() -> System.out.println("This is a lmada expression!!"));

    }

    @DisplayName("람다식 예제 2")
    @Test
    void lamdaTest2(){

        Myfunction f = () -> {}; // Myfunction f = (Myfunction)(() -> {});  형변환의 생략이 가능하다.
        Object obj = (Myfunction)(() -> {}); // Object 타입으로의 형변환이 생략됨.
        String str = ((Object)(Myfunction)(() -> {})).toString();

        out.println(f);
        out.println(obj);
        out.println(str);
//        out.println(() -> {}); // 람다식 자체는 Object 타입으로 형변환이 안된다. 먼저 함수형 인터페이스로 형변환을 해줘야 한다.
        out.print((Myfunction)(() -> {}));

//        chapter14.lamda.LambdaExTest$$Lambda$307/0x0000000800c0f778@3bd94634
//        chapter14.lamda.LambdaExTest$$Lambda$308/0x0000000800c0f990@58a90037
//        chapter14.lamda.LambdaExTest$$Lambda$309/0x0000000800c0fba8@f4168b8
//        chapter14.lamda.LambdaExTest$$Lambda$310/0x0000000800c0fdc0@70a9f84e
        // 외부 클래스이름$$Lambda$번호 의 형식의 타입으로 객체가 생성이 된다.
    }

    @DisplayName("람다식 예제 3")
    @Test
    void lambdaEx3(){
        Outer outer = new Outer();
        Outer.Inner inner = outer.new Inner(); // Outer클래스 내부에서 Inner 클래스의 객체를 생성을 하지 않았는데, 이를 Inner 클래스로 사용해야할 이유가 없을듯!! 예제니까 그냥 보여준 것.
        inner.method(100);
    }

    @DisplayName("람다식 예제4 - java.util.function 패키지의 함수형 인터페이스 사용")
    @Test
    void lambdaEx4(){
        ArrayList<Integer> lst = new ArrayList<>();
        for(int i=0; i<10; i++)
            lst.add(i);

        lst.forEach(i -> out.print(i));
        out.println();
        lst.forEach(i -> {
                i = i*i;
                out.print(i);
                });
        out.println();

        lst.removeIf(i -> i%2==0 || i%3 ==0);
        out.print(lst);
        out.println();

        lst.replaceAll(i -> i*10);
        out.print(lst);
        out.println();

        Map<String, String> map = new HashMap<>();
        map.put("1","one");
        map.put("2","two");
        map.put("3","three");
        map.put("4","four");

        map.forEach((k,v) -> out.printf("key : %s , value : %s %n",k,v));
    }

    @DisplayName("람다식 예제5 - java.util.function 패키지의 함수형 인터페이스 사용")
    @Test
    void lambdaEx5(){

        Supplier<Integer> supplier = () -> (int)(Math.random()*100) + 1 ;
        Supplier<Integer> supplier1 = () -> 1997 ;
        Function<String, String> function = s -> s + "!@#" ;
        Function<String, String> function1 = s -> s + " hello~" ;
        Predicate<Integer> predicate = i -> i%2==0 ;
        Consumer<Integer> consumer = i -> out.printf("wow~ %d%n",i);

        List<Integer> list = LambdaEx.makeRandomList(supplier);
        out.println(list);
        list = LambdaEx.makeRandomList(supplier1);
        out.println(list);

        List<String> strList = new ArrayList<>();
        strList.add("sejins");
        strList.add("youngs");
        strList.add("eojins");
        List<String> newList = LambdaEx.updateListYouWant(function, strList);
        out.println(newList);
        newList = LambdaEx.updateListYouWant(function1, strList);
        out.println(newList);

        list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        LambdaEx.doSomethingIf(predicate, consumer, list);
    }

    @DisplayName("람다식 예제 - 함수형 인터페이스의 결합")
    @Test
    void lambdaEx7(){

        Function<String, Integer> f = s -> Integer.parseInt(s, 16);
        Function<Integer, String> g = i -> Integer.toBinaryString(i);

        Function<String, String> h = f.andThen(g);
        Function<Integer, Integer> h2 = f.compose(g);
        Function<String, String> h3 = f.andThen(i -> Integer.toBinaryString(i));
        Function<String, String> h4 = ((Function<String, Integer>)(s -> Integer.parseInt(s, 16))).andThen(i -> Integer.toBinaryString(i));
        // h, h3, h4는 모두 같은 내용임.

        System.out.println(h.apply("FF")); // "11111111"
        System.out.println(h2.apply(2)); // 16

        Predicate<Integer> p = i -> i < 100;
        Predicate<Integer> q = i -> i < 200;
        Predicate<Integer> r = i -> i%2==0 ;
        Predicate<Integer> notP = p.negate();
        Predicate<Integer> all = notP.and(q.or(r));
        System.out.println(all.test(150)); //true

        String str1 = "ABC";
        String str2 = "ABC";

        Predicate<String> p2 =  Predicate.isEqual(str1);
        System.out.println(p2.test(str2));
    }
}