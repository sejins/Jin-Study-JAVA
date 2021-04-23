package chapter14.lamda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class LambdaEx {

    //LambdaExTest

    static void execute(Myfunction myfunction){
        myfunction.myMethod();
    }

    static Myfunction getMyFunction(){
        Myfunction f = () -> System.out.println("This is a f3");
        return f;
    }

    static <T> List<T> updateListYouWant(Function<T,T> f, List<T> list){
        List<T> newList = new ArrayList<>();

        for(T t : list){
            newList.add(f.apply(t));
        }

        return newList;
    }

    static <T> void doSomethingIf(Predicate<T> p, Consumer<T> c, List<T> list){

        for(T t : list){
            if(p.test(t)){
                c.accept(t);
            }
        }
    } // 여기서 알 수 있듯이, 하나의 메서드로 내부 로직 자체를 모듈화하여 다르게 만들 수 있다. --> 함수를 매개변수로 받으니까!
    // 함수가 즉, 로직이 매개변수화 되는 것이다. -> 내용이 매개변수에 따라서 달라질 수 있다.

    static <T> List<T> makeRandomList(Supplier<T> s){
        List<T> newList = new ArrayList<>();
        for(int i=0; i<10; i++)
            newList.add(s.get());
        return newList;
    }
}
