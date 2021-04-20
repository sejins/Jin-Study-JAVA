package chapter14.lamda;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LambdaExTest {

    @DisplayName("랃다식 예제")
    @Test
    void lambdaTest(){

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

}