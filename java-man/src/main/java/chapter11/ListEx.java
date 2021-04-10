package chapter11;

import java.util.List;

public class ListEx {
    public void addInOrder(List list){
        long start = System.currentTimeMillis();
        for(int i=0; i<1000000; i++){
            list.add(i+"");
        }
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }

    public void addInMiddle(List list){
        long start = System.currentTimeMillis();
        for(int i=0; i<10000; i++){
            list.add(500,"S");
        }
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }

    public void removeInmiddle(List list){
        long start = System.currentTimeMillis();
        for(int i=0; i<10000; i++){
            list.remove(i);
        }
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }

    public void removeInOrder(List list){
        long start = System.currentTimeMillis();
        for(int i=list.size()-1; i>=0; i--){
            list.remove(i);
        }
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }

    public void access(List list){
        long start = System.currentTimeMillis();
        for(int i=0; i<=10000; i++){
            list.get(i);
        }
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }
}
