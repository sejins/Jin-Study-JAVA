package chapter11;

import java.util.Objects;

public class Person2 {
    private final String name;
    private final int age;

    public Person2(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString(){
        return name+" : "+age;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Person2){
            Person2 tmp = (Person2)obj;
            return name.equals(((Person2) obj).getName())&&(age==tmp.getAge());
        }
        return false;
    }
    @Override
    public int hashCode(){
        return Objects.hash(name,age);
    }

    // equals 메서드와 hashCode 메서드를 통해서 같은 객체를 같다고 판별할 수 있는 기준을 만들어주자!
}
