package chapter11;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class ListExTest {

    @DisplayName("arrayList와 LinkedList의 성능차이 테스트")
    @Test
    void arrayListLinkedListTest(){
        ListEx listEx = new ListEx();
        ArrayList arrayList = new ArrayList(2000000);
        LinkedList linkedList = new LinkedList();

        System.out.println("=순차적으로 추가하기=");
        System.out.print("ArrayList : ");
        listEx.addInOrder(arrayList);
        System.out.print("LinkedList : ");
        listEx.addInOrder(linkedList);

        System.out.println("=중간에 추가하기=");
        System.out.print("ArrayList : ");
        listEx.addInMiddle(arrayList);
        System.out.print("LinkedList : ");
        listEx.addInMiddle(linkedList);

        System.out.println("=중간에서 삭제하기=");
        System.out.print("ArrayList : ");
        listEx.removeInmiddle(arrayList);
        System.out.print("LinkedList : ");
        listEx.removeInmiddle(linkedList);

        System.out.println("=순차적으로 삭제하기=");
        System.out.print("ArrayList : ");
        listEx.removeInOrder(arrayList);
        System.out.print("LinkedList : ");
        listEx.removeInOrder(linkedList);
    }
}