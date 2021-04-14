# 컬렉션 프레임워크

컬렉션 프레임 워크란 다수의 데이터(데이터 군)을 표준화되 프로그래밍 방식으로 다룰 수 있게 한 것이다.

JDK1.2부터 등장했다고 한다.

### _컬렉션 프레임 워크의 핵심 인터페이스_

<img src="https://user-images.githubusercontent.com/71161576/114208224-377dc480-9998-11eb-8898-24716e2857c3.jpg" height="300" width="500"/>

- List
  - 순서가 있는 데이터의 집합. 데이터의 중복을 허용한다.
- Set
  - 순서를 유지하지 않는 데이터의 집합. 중복을 허용하지 않는다.
- Map
  - Key / Value 쌍으로 이루어진 데이터 집합. 나머지 두 인터페이스와 달리 Collection 인터페이스를 상속하지 않는다.

### _List 인터페이스_

<img src="https://user-images.githubusercontent.com/71161576/114257430-fff43400-99fa-11eb-98ea-11cf64aa3656.jpg" height="300" width="500"/>

List 인터페이스는 중복을 허용하고, 저장순서가 유지되는 컬렉션을 구현하는데 사용.
인터페이스의 구현체로는 대표적으로 ArrayList 와 LinkedList가 있다.

### _Set 인터페이스_

<img src="https://user-images.githubusercontent.com/71161576/114257436-04205180-99fb-11eb-9223-3241ced700ee.jpg" height="300" width="500"/>

Set 인터페이스는 중복을 허용하지 않고, 저장 순서가 유지되지 않는 컬렉션 클래스를 구현하는데 사용.  
인터페이스의 구현체로는 대표적으로 HashSet 와 TreeSet이 있다.

### _Map 인터페이스_

<img src="https://user-images.githubusercontent.com/71161576/114257435-02ef2480-99fb-11eb-8826-8821c6615562.jpg" height="300" width="500"/>

Map 인터페이스는 key값과 value값을 하나의 쌍으로 묶어서 저장하는 컬렉션 클래스를 구현하는데 사용한다. key는 중복이 불가능하지만, value는 중복이 가능하다. key값으로 데이터를 구분하게 된다.
인터페이스의 구현체로는 대표적으로 HashTable, HashMap, LinkedHashMap, SortedMap, TreeMap 등이 있다.

### _ArrayList_

ArrayList는 기존의 Vector 클래스를 개선한 것으로, 클래스 내부적으로 배열을 이용해서 데이터를 저장한다.  
Vector 클래스와 동일하게 배열에 더 이상 저장할 공간이 없으면 더 큰 새로운 배열을 생성해서 기존의 배열에 저장된 내용을 새로운 배열로 복사한 다음에 저장한다. 당연 이때 오버헤드가 발생한다.

### _LinkedList_

LinkedList 클래스는 내부적으로 이중 연결 리스트 자료구조로 구현이 되어있다.

#### _장단점 존재_

- 순차적으로 추가/삭제하는 경우에는 ArrayList가 LinkedList보다 빠르다.  
  단 ArrayList의 배열의 크키가 넉넉하다는 가정에서
- 중간 인덱스에 데이터를 추가/삭제하는 경우에는 LinkedList가 더 빠르다.
- LinkdedList는 각 원소들이 메모리의 연속된 공간에 존재하지 않기 때문에, 원소의 수가 많아질수록 인덱싱 성능이 느려진다.
- 데이터의 개수가 변하지 않는 경우라면 ArrayList가 좋겠지만, 자주 변한다면 LinkedList를 고려할 수 있다.

ArrayListLinkedListTest 예제

### _Stack 과 Queue_

자바는 Stack 클래스를 별도로 제공하는 반면, Queue에 대해서는 인터페이스만 제공하고, 그 구현체를 알맞게 사용하면 된다. LinkedList도 Queue의 구현체이다.  
추가로 Queue 인터페이스를 구현한 PriortyQueue클래스도 제공을 한다. --> 최소힙  
한번더 추가로 Queue를 상속받는 Deque 인터페이스도 존재하는데, 이의 구현체 클래스는 Deque의 기능을 사용할 수 있겠다.

### _Iterator 인터페이스_

<img src="https://user-images.githubusercontent.com/71161576/114257437-05517e80-99fb-11eb-8be4-39a54d3d1a45.PNG" height="300" width="500"/>

컬렉션에 저장된 요소를 접근하는데 사용하는 인터페이스이다.  
이를 통해 컬렉션에 저장된 요소들을 읽어오는 방법이 표준화 되었다.  
Collection 인터페이스의 구현체는 iterator() 메서드를 통해 Ierator의 구현체를 리턴한다.  
Map 인터페이스의 구현체들은 Collection 인터페이스를 구현하지 않기 때문에, key값이나 value값으로 별도의 컬렉션(Set)을 얻은 뒤 사용가능하다.

```Java
Collection c = new ArrayList();
Iterator it = c.iterator();
while(it.hasNext()){
  System.out.println(it.next());
}
```

위 처럼 Collection 인터페이스에 존재하는 메서드를 사용할 때에는 Collection 타입으로 참조변수를 선언하여 사용하자. --> 코드의 확실성 측면에서

Enumeration은 Iterator의 구버젼이고, ListIterator는 Iterator를 상속받은 인터페이스로, 원소에 대해서 양방향 접근이 가능하다.

### _Arrays 클래스_

배열을 다루는데 유용한 메서드가 정의된 클래스.
배열의 복사, 채우기, 정렬 및 검색, equals, toString, 리스트로 변환등의 기능이 있다.ㄴ

### _Comparator & Comparable 인터페이스_

Comparator 과 Comparable 는 둘 다 컬렉션을 정렬하는데 필요한 인터페이스로, 정렬을 하기 위한 메서드를 정의하고 있다.  
Comparable는 정렬 수행 시 기본적으로 적용이 되는 메서드를 정의하는 인터페이스이다. 래퍼 클래스나 String 클래스 같이 자바에서 정렬이 가능한 것들이 Comparable 인터페이스를 구현했다.

equals 메서드는 이미 모든 클래스가 가지고 있기 때문에, 따로 오버라이딩을 할 필요는 없다.

```java
public interface Comparator {
  int compare(Object o1, Object o2);
  boolean equals(Object obj);
}

public interface Comparable{
  public int compareTo(Object o);
}
```

즉, Comparable을 구현한 클래스는 정렬이 가능하고, 이 정렬 기준 이외의 것으로 정렬을 하고자 할 때는 Comparator를 구현한 클래스를 사용한다.  
[참조](https://codevang.tistory.com/288)

ComparatorTest 예제  
음수 또는 0일때는 비교하는 객체들의 자리가 유지가 되고, 양수를 리턴할때 객체의 자리가 변경된다.  
이렇게 Comparator를 통해서 커스텀한 정렬 기준을 만들 수 있다.

### _HashSet_

HashSet은 컬렉션 데이터의 중복을 허용하지 않고, 순서를 보장하지 않고 자체적인 저장 방식에 따라 순서가 결정된다. 순서를 고려해야한다면 LinkedHashSet을 사용해야한다.  
Set에 객체를 추가할 때, 중복된 객체인지 판별하기 위해서 객체의 equals 메서드와 hashCode 메서드를 호출해서 같은 객체가 존재하는지 확인한다.  
그래서 객체를 같은 객체로 인식할 것인지 지정하기 위해선 equals 메서드와 hashCode 메서드를 오버라이딩해서 커스터마이징해야한다.  
HashSetEx 예제

### _TreeSet_

TreeSet은 이진 검색트리의 성능을 향상시킨 레드-블랙 트리로 구현이 된 Set이다.  
Set을 구현했기 때문에 마찬가지로 중복을 허용하지 않고, 순서를 반영하지 않는다.  
하지만 이진 검색트리 자료구조로 구현이 되었기 때문에 내부적으로 값을 정렬하게 된다.  
정렬은 마찬가지로 TreeSet에 저장되는 객체가 Comparable을 구현했던가, 아니면 Comparator를 제공해서 정렬 기준을 알려줘야한다.
이외의 다양한 메서드를 제공하는데 이는 필요할 때 API문서를 참고.

### _HashMap_

HashMap은 Map 인터페이스를 구현한 클래스이다. 비슷한 클래스로 HashTable 클래스가 있는데 이는 예전 버전이므로 HashMap의 사용을 권장한다.  
Map 인터페이스를 구현했기 때문에 key 와 value를 묶어서 하나의 데이터로 저장한다는 특징이 있다.  
객체 지향적인 방식에 의해서 key 와 value는 HashMap 클래스의 내부 static 클래스인 Entry에 의해서 관리가 된다.

```java
.
public class HashMap{
  Entity[] table;
  static class Entry{
    Object key;
    Object value;
  }
}
```

이런식으로  
알다시피 key는 저장된 값을 찾기 위한 값으로, 유일해야하고 같은 key를 사용하게 될 시에 기존의 key에 해당하는 값을 수젇하게 된다.  
HashMapEx 예제

### _해싱과 해싱함수_

컬렉션 중 HashSet 과 HashMap은 해싱을 구현한 컬렉션 클래스이다.  
해싱에서 사용하는 자료구조는 다음과 같이 배열과 링크드리스트의 조합으로 구성이 된다.

<img src="https://user-images.githubusercontent.com/71161576/114553731-eaa03380-9ca0-11eb-83fb-ddf349ab11af.png" height="300" width="500"/>

저장할 데이터의 키를 해시함수에 넣으면 배열의 한 요소의 주소를 얻게 되고, 다시 그 곳에 연결되어 있는 링크드 리스트에 값을 저장하게 된다.  
하지만 링크드 리스트의 인덱싱 성능이 효율적이지 못해서, 일반적인 해싱은 링크드 리스트의 원소가 하나인 즉, 하나의 해시코드(해시함수 리턴값)은 최대한 유일한 형태를 의미한다.
<img src="https://user-images.githubusercontent.com/71161576/114553713-e4aa5280-9ca0-11eb-9e59-4ea3a0a8f3e2.png" height="300" width="500"/>  
해시코드 값이 같다면, 이는 해시 충돌이 발생한 것이다.  
앞에서 살펴 본 것처럼 HashMap 같이 해싱을 구현한 클래스들은 hashCode() 메서드를 사용한다.

### _TreeMap_

TreeSet과 유사하게 이진 검색 트리의 형태로 키와 값의 쌍을 저장한다. 이진 검색트리 자료구조를 사용하기 때문에 값이 정렬되어 저장이 된다. 기본정렬은 키값에 대해서 정렬이 된다. 커스텀 정렬 기준을 생성하기 위해서는 Comparator 클래스를 구현하면 된다.  
검색에 관한 대부분의 경우에서 HashMap이 TreeMap 보다 더 뛰어나지만, 범위 검색이나 정렬이 필요한 경우에는 TreeMap을 사용하도록 하면 되겠다.  
TreeMapEx 예제

### _Properties_

HashMap 클래스의 구버전인 Hashtable을 상속받아서 구현한 것으로, 키와 값을 String , String 형식으로 저장하는 단순화 된 컬렉션 클래스이다.  
그래서 주로 애플리케이션의 환경 설정과 관련된 프로퍼티를 저장하는데 사용되며 입출력 스트림을 통해서 설정 파일로부터 읽고 쓰는 편리한 기능들을 제공한다.  
자세한 사용 방법은 API문서를 참고하면 되겠다.  
특징으로는 Map의 특성상 순서를 유지하지 않는다는 것이고, 설정파일 등의 외부 파일의 형식은 라인단위로 키와 값이 = 으로 연결된 형태이여야 하며, 주석 라인은 첫번째 문자가 # 이어야한다.

### _Collections_

Arrays가 배열에 관련해서 편리한 기능을 제공하는 클래스라면, Collections 클래스는 컬렉션 클래스에 대해서 편리한 static 메소드들을 제공을 한다.
기본적인 기능들은 API문서를 참고하면 좋을 듯 하다.  
기존의 Arrays 클래스와 차이가 있는 부분들은 다음과 같다.

- 컬렉션의 동기화
- 변경불가 컬렉션 생성
- 싱글톤 컬렉션 생성
- 한 종류의 객체만 저장하는 컬렉션 생성

한 종류의 객체만 저장하는 컬렉션은 JDK 1.5이후부터 등장한 제네릭스를 통해서 간단하게 처리할 수 있지만, 이전 버전과의 호환성을 위해서 남겨져 있다.
