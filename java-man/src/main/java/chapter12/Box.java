package chapter12;

import java.util.ArrayList;

public class Box<T> {
    ArrayList<T> arrayList = new ArrayList<>();
    public void add(T item){arrayList.add(item);}
    public T get(int i){return arrayList.get(i);}
    public int size(){return arrayList.size();}
    @Override
    public String toString(){return arrayList.toString();}
}
