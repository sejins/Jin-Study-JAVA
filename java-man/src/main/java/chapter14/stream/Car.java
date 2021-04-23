package chapter14.stream;

public class Car implements Comparable<Car>{

    private String name;

    private String manufacturer;

    private int price;

    @Override
    public String toString() {
        return manufacturer+" "+name+" "+price;
    }

    public Car(String manufacturer, String name, int price) {
        this.manufacturer = manufacturer;
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public int getPrice() {
        return price;
    }


    @Override
    public int compareTo(Car o) { // 기본정렬 조건 : price에 대해서 내림차순 정렬
        return o.price - this.price;
    }
}
