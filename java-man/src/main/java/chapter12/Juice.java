package chapter12;

public class Juice {

    private String name;

    public Juice(String name){ this.name = name + " juice"; }

    @Override
    public String toString(){ return name;}

}
