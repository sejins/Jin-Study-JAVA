package chapter14.lamda;

public class LambdaEx {
    static void execute(Myfunction myfunction){
        myfunction.myMethod();
    }

    static Myfunction getMyFunction(){
        Myfunction f = () -> System.out.println("This is a f3");
        return f;
    }
}
