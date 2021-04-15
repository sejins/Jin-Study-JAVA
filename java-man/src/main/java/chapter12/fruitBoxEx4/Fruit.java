package chapter12.fruitBoxEx4;

public class Fruit {

    private String name;

    private int weight;

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public Fruit(String name, int weight){
        this.name = name;
        this.weight = weight;
    }

    @Override
    public String toString(){ return name + "("+weight+")";}
}
