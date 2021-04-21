package chapter14.lamda;

public class Outer {
    int val = 10;

    public class Inner{
        int val = 20;

        void method(int i){ // 람다식에서 참조하는 지역변수는 final로 간주되어 람다식 내/외부에서 수정이 불가능하다.
            int val = 30;
//            i = 10; // 에러.

            Myfunction f = () ->{
                System.out.println(i);
                System.out.println(val);
//                System.out.println(val)++; // 에러.
                System.out.println(++this.val);
                System.out.println(++Outer.this.val);
            };

            f.myMethod();
        }
    }
}
