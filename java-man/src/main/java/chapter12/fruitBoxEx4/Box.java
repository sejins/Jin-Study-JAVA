package chapter12.fruitBoxEx4;

import java.util.ArrayList;
import java.util.List;

public class Box<T> {

    ArrayList<T> arrayList = new ArrayList<>();

    public void add(T item){arrayList.add(item);}

    public T get(int i){return arrayList.get(i);}

    public List<T> getList(){return arrayList;}

    public int size(){return arrayList.size();}

    @Override
    public String toString(){return arrayList.toString();}
}
