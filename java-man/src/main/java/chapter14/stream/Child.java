package chapter14.stream;

public class Child extends Parent{

    private String name;

    public Child(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    void childMethod(){
        System.out.println("I'm child!!");
    }
}
