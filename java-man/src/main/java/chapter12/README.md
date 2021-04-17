# 지네릭스  
`JDK 1.5` 부터 도입된 지네릭스는 JDK 1.8부터 도입된 람다식 만큼 획기적인 변화였다.  

지네릭스는 다양한 타입의 객체를 다루는 메서드나 컬렉션 클래스에 컴파일시에 터입체크를 해주는 기능이다.  
이를 통해서 타입 안정성을 보장할 수 있고, 불필요한 형변환을 줄일 수 있다.  
지네릭스를 왜 사용하는지 먼저 이해하면, 어떻게 사용하는지 쉽게 이해할 수 있을 듯하다.  
### **백문불여일견**
```java
class Box<T>{
    T item;
    void setItem(T item){this.item = item;}
    T getItem(){return this.item;}
}
```
여기서 T는 타입 매개변수로, 저렇게 선언된 클래스의 객체를 생성할 때 다양한 타입의 객체를 생성할 수 있기 때문에 
타입이 매개변수같은 역할을 한다고 해서 붙혀진 이름이다.   
지네릭 클래스의 객체를 생성할 때는 타입 매개변수의 값으로 실제 타입을 지정해주면된다.  

```java
Box<String> box = new Box<>();
box.setItem("ABC");
// box.setItem(new Object());  // 에러가 발생한다. String 이외의 타입은 불가능.
String it = box.getItem(); // 별도의 형변환이 필요하지 않다.
```  

지네릭 클래스의 객체를 생성할 때 타입의 값으로 String을 줬기 때문에 사실상 box 객체의 클래스는 이렇다고 볼 수 있다.  
```java
class Box{
    String item;
    void setItem(String item){this.item = item;}
    String getItem(){return this.item;}
}
```  

그리고 지네릭 클래스의 객체를 생성할 때 타입매개변수의 값을 지정해주지 않아도 되지만(이전 버전과의 호환성을 위해서)
raw 한 타입으로 객체를 생성했다고 경고가 나타나게 된다.  

### **지네릭스의 용어**
### `class Box<T> {...}`  
* `Box<T>` : 지네릭 클래스
* `T` : 타입 매개변수
* `Box` : 원시 타입  

예를 들어, `Box<String>` 과 `Box<Integer>`는 다른 타입 매개변수를 사용해서 객체를 생성한 것 뿐이지,
이 둘이 별개의 클래스를 의미하는 것은 아니다.  
이는 하나의 매서드에 매개변수를 다르게 줘서 호풀하는 것과 같은 맥락이라고 생각하면 될 듯하다.  
컴파일 후에 `Box<String>` 과 `Box<Integer>`는 이들의 원시타입입 Box로 변경된다. 즉 지네릭 타입이 제거가 된다.  

### **지네릭스의 제한사항**  
지네릭 클래스의 객체를 생성할 때 객체 별로 다른 타입을 지정하는 것은 적절하다. 원래 인스턴스별로
다른 타입으로 동작하도록 만든 기능이기 때문이다.  
그래서 모든 인스턴스가 동일하게 동작해야하는 `static 맴버`에는 **당연히** 지네릭 타입을 사용할 수 없다.  
```java
class Box<T>{
    static T item; // 안된다.
    static void method(T t1, T t2){ ... } // 안된다.
}
``` 
그리고 지네릭 타입의 `배열을 생성하는 것`도 허용되지 않는다.  지네릭 배열 참조 변수를 선언하는 것은 가능하지만,
new 연산자를 통해서 배열을 생성하는 것이 되지 않는다.  
이는 new 연산자가 컴파일 시점에 타입 T가 무엇인지 알아야하는데, 타입 T는 이를 호출해서 객체를 생성
하는 부분에서 결정이 되기 때문이다. 
같은 이유로 instance of 연산자도 T를 피연산자로 사용할 수 없다.  
꼭 지네릭 배열을 생성해야 할 필요가 있으면 Reflection API를 통해서 동적으로 객체를 생성하는 메소들르 사용해서
배열을 생성하거나, Object 배열을 생성해서  복사한 다음에 T[]로 형변환하는 방법 등을 사용한다.  

GenericsEx 예제  

지네릭 클래스의 객체를 생성할 때는 역시나 타입에 주의를 해야한다. 
```java
Box<Apple> appleBox = new Box<Apple>();
Box<Apple> appleBox = new Box<Grape>(); // 에러.
Box<Fruit> appleBox = New Box<Apple>(); // 에러. 타입이 서로 상속관계에 있어도 안됨.
Box<Apple> appleBox = new FruitBox<Apple>(); // 이건 가능. 지네릭 클래스가 상속 관계인 경우.
```  

### **제한된 지네릭 클래스**  
타입매개변수로 타입을 명시하면 한 종류의 타입만 저장할 수 있도록 제한을 할 수 있다.  
하지만 여전히 모든 종류의 타입을 명시할 수 있다.  
그래서 지정할 수 있는 타입 매개변수를 제한하는 방법이 있다.
```java
import java.util.ArrayList;class FruitBox<T extends Fruit>{
    ArrayList<T> arrayList = new ArrayList<>();
}
```  
`extends`를 사용하면 특정 타입의 자손들만 타입 매개변수로 지정할 수 있게 제한이 가능하다.
```java
FruitBox<Apple> appleBox = new FruitBox<>();
FruitBox<Toy> toyBox = new FruitBox<>(); // 에러. Toy는 Fruit의 자손이 아님.
```  
만약 타입 매개변수로 받을 클래스가 클래스를 상속받는 것이 아닌 인터페이스를 구현해야할 경우에도  
implements가 아닌 `extends`를 사용한다.  
 만약 클래스 Fruit를 상속받는 동시에 Eatable 인터페이스를 구해야 하는 경우에는 `&`을 사용해서 구분한다.
 ```java
class FruitBox<T extends Fruit & Eatable>{ ... }
```  
헷갈렸던 부분  
```java
class FruitBox<T extends Fruit & Eatable> extends Box<T>{}
```  
FruitBox의 타입 매개변수 T는 extends를 통해서 타입을 제한한 것이고, Box<T>는 다형성.  
논리적으로 한번 생각해 볼 필요가 있을 듯하다.  
FruitBox 에는 과일들만 담을 수 있기 때문에, `타입`을 과일로 제한 했고, FruitBox는 
결국 Box로 일반화 할 수 있기 때문에, `다형성`에 의해서 Box를 상속 받는다.

### **와일드 카드**  

```java
class Juicer{
    static Juice makeJuice(FruitBox<Fruit> box){
        String tmp = "";
        for(Fruit f : box.getList()) tmp += f+" ";
        return new Juice(tmp);
    }   
}
```  
일단 지네릭 클래스가 아니고, 지네릭 클래스라고 하더라고 static 메서드이기 때문에 타입 파라미터를 사용하지 못한다.  
그래서 아래의 상황이 발생한다.  
```java
Juicer.makeJuice(new FruitBox<Fruit>()); // 가능
Juicer.makeJuice(new FruitBox<Apple>()); // 불가능
```  
왜냐하면 raw 타입에 대해서만 다형성이 존재하고, 타입 파라미터에 대해서는 다형성이 존재하지 않는다.  
즉, `FruitBox< Fruit >  <--  FruitBox< Apple >` 의 관계가 성립하지 않는다.  
[참고](https://thecodinglog.github.io/java/2020/12/15/java-generic-wildcard.html)  
다형성이 성립하지 않기 때문에, 메서드를 오버로딩 해야한다.  
```java
static Juice makeJuice(FruitBox<Fruit> box){...}
static Juice makeJuice(FruitBox<Apple> box){...}
```  
하지만 컴파일 에러가 발생한다.  
지네릭 타입이 다른 것만으로는 오버로딩이 성립하지 않기 때문이다.  
이때 사용해야하는 것이 `와일드카드` 이다.  
* `<? extends T>`   와일드 카드의 상한 제한. T와 그 자손들만 가능
* `<? super T>`     와일드 카드의 하한 제한. T와 그 조상들만 가능
* `<?>`             제한 없음. 모든 타입이 가능. <? extends Object>와 동일.

```java
static Juice makeJuice(FruitBox<? extends Fruit> box){...}
```  
이렇게 하면 메서드의 매개변수로 FruitBox< Fruit > 뿐만 아니라, FruitBox< Apple > 와 FruitBox< Grape > 도 가능함. 

여기서 또 FruitBox 클래스 자체적으로 타입을 제한을 해 준 것을 생각 해야한다.  
FruitBox 클래스가 아래와 같기 때문에  
 ```java
class FruitBox<T extends Fruit & Eatable>{ ... }
```    
만일 메서드의 매개변수를 `FruitBox<?>` 로 지정한다고 해도, T에는 Object의 하위 클래스 모두가 들어가는 것이 아니라  
Fruit 클래스를 상속받고, Eatable 인터페이스를 구현한 모든 클래스가 가능한 것이다.  
GenericsExTest 예제, WildCardEx4Test 예제  

Collections 클래스의 `sort` 메서드  
```java
static <T> void sort(List<T>, Comparator<? super T>)
```  
* `Comparator<? super Apple>` : Comaparator< Apple >, Comaparator< Fruit >, Comaparator< Object >  
* `Comparator<? super Grape>` : Comaparator< Grape >, Comaparator< Fruit >, Comaparator< Object >

### **지네릭 메서드**  
```java
static <T> void sort(List<T>, Comparator<? super T> c)
```  
메서드의 선언부에 지네릭 타입이 선언된 메서드를 지네릭 메서드라고 한다.  
지네릭 타입의 선언 위치는 반환 타입 바로 앞이다.  
주의할 점은 지네릭 클래스에 정의된 타입 매개변수와 지네릭 메서드에 정의된 타입 매개변수는 별개라는 것이다.  
설령 타입매개변수가 T 로 통일하더라도 이는 같은 것이 아니다.  
지네릭 메서드는 지네릭 클래스가 아닌 클래스에도 정의될 수 있다.  
앞에서 static 메서드에는 타입 매개변수를 사용할 수 없다고 했다, 하지만 지네릭 메서드는 static 메서드로 사용할 수 있다.  
지네릭 메서드의 타입 매개변수 T 는 메서드에서 지역적으로 사용되는 타입이기 때문에 상관이 없다.  

앞서 나왔던 메서드를 지네릭 메서드로 변경해보자.  
```java
static Juice makeJuice(FruitBox<? extends Fruit> box){...}
```  
```java
static <T extends Fruit> Juice makeJuice(FruitBox<T> box){...}
```  
이렇게 지네릭 메서드를 사용하면 위에서 와일드 카드를 사용한 것과 동일하게, 메서드의 오버로딩이 가능해진다.  

그리고 원래라면 메서드를 호출할 때 지네릭 타입을 명시해줘야하는데
```java
Juicer.<Fruit>makeJuice(fruitBox);
Juicer.<Apple>makeJuice(appleBox);
```  
이 정도는 컴파일러가 타입을 추정할 수 있다.
```java
Juicer.makeJuice(fruitBox);
Juicer.makeJuice(appleBox);
```  
이러한 형태로 와일드 카드의 사용과 지네릭 타입의 제한의 사용에 대해서 잘 이해할 수 있을 것 같다.  
그냥 딱 직관적으로. 보이는대로 해석하면 된다.  
```java
public static void printAll(ArrayList<? extends Product> list1, 
                        ArrayList<? extends Product> list2, ){...}
```               
```java
public static <T extends Product> void printAll(ArrayList<T> list1, 
                                            ArrayList<T> list2, ){...}
```  
마지막으로 이 정도를 이해할 수 있으면 될 듯하다.  

### **지네릭 타입의 형변환**  
우선 지네릭 타입과 넌 지네릭(non-generic) 타입간의 형변환은 가능하다. 
```java
Box box = null;
Box<Object> objBox = null;

box = (Box)objBox; // 가능하지만 경고발생
objBox = (Box<Object>)box; // 마찬가지로 가능하지만 경고 발생
```  
하지만 이 경우에 대해서는 생각을 해봐야한다.  
```java
Box<String> strBox = null;
Box<Object> objBox = null;

strBox = (Box<String>)objBox; // 에러
objBox = (Box<Object>)strBox; // 에러
```  
앞서 살펴본 것처럼, 타입 파라미터 간에는 다형성이 존재하지 않는다.  
그래서 앞에서 와일드 카드를 통해, 지네릭 타입간의 다형성을 나타낸 것을 생각해볼 수 있다.  
**쉽게 말해, 지네릭 타입의 다형성을 위해서는 와일드 카드를 사용한다고 생각하자.**  

```java
static Juice makeJuice(FruitBox<? extends Fruit> box){...}
```  
이 경우에 메서드의 인자로 `FruitBox<Fruit>` `FruitBox<Apple>` `FruitBox<Grape>` 타입이 모두 가능하다.  
즉, 지네릭 타입의 다형성을 와일드 카드를 통해서 표현할 수 있다. 
```java
FruitBox<? extends Fruit> fruitBox1 = new FruitBox<Fruit>();
FruitBox<? extends Fruit> fruitBox2 = new FruitBox<Apple>();
FruitBox<? extends Fruit> fruitBox3 = new FruitBox<Grape>();
```  
역으로 다음과 같은 경우도 가능하지만, 확인되지 않은 형변환이라는 경고가 발생한다.  
```java
FruitBox<? extends Fruit> fruitbox = null;
FruitBox<Apple> appleBox = (FruitBox<Apple>)box; // 경고.
```
`FruitBox<? extends Fruit>`  에 대입될 수 있는 타입은 여러개이지만, `FruitBox<Apple>`를
제외한 다른 타입은 `FruitBox<Apple>`로 형변환 할 수 없기 때문이다.  

실제 `Optional` 클래스의 일부를 보면서 이해해보자.  
```java
public final class Optional<T>{
    private static final Optional<?> EMPTY = new Optional<>(); // 다형성
    private final T value;
    ...
    public static <T> Optional<T> empty(){
        Optional<T> t = (Optional<T>)EMPTY;
        return t;
    }
    ...
}
```  
여기서 유심히 봐야할 곳은 이부분인데,
```java
private static final Optional<?> EMPTY = new Optional<>();
```   
이는 이것과 동일하다.  
```java
Optional<?> EMPTY = new Optional<>();
--> Optional<? extends Object> EMPTY = new Optional<>();
--> Optional<? extends Object> EMPTY = new Optional<Object>();
```  
마지막 줄에서 의문이 생기는데, 마지막 줄이 이것과 다른 점은 무엇일까?  
```java
Optional<Object> EMPTY = new Optional<Object>();
```  
객체를 생성하는 부분은 동일하지만, `Optional<?>` 인 이유는 다형성에 의해 형변환이 가능하기 때문이다.  
```java
Optional<?> wopt = new Optional<Object>();
Optional<Object> wopt = new Optional<Object>();

Optional<String> sopt1 = (Optional<String>)wopt; // 형변환 가능.
Optional<String> sopt2 = (Optional<String>)oopt; // 현변환 불가능.
```  

### **지네릭 타입의 제거**  
앞서 말한 것처럼 `Box<String>` 와 `Box<Integer>` 별개의 클래스가 아니라고 했다.  
컴파일러틑 먼저 지네릭 타입을 이용해서 소스코드를 체크하고(타입 체크), 필요한 곳에 형변환을 넣어주게 된다.  
그리고 지네릭 타입을 제거하게 된다. 즉, 컴파일 후 클래스파일(*.class)에는 지네릭 타입이 존재하지 않는다.  

**컴파일 시 지네릭 타입의 제거**
1. 지네릭 타입을 보고 소스의 타입을 체크한다.
2. 지네릭 타입의 경계를 제거한다.  
      * 지네릭 타입이 `<T extends Fruit>` 인 경우 `T`는 `Fruit`로 치환된다. 
      * 지네릭 타입이 `<T>`  인 경우 `T` 는 `Object` 로 치환된다.
 3. 지네릭 타입을 제거한 후에 타입을 일치시키기 위해서 형변환을 추가해준다.  

GenericAllAboutEx 예제에 헷갈리는거 다 해놓자

지네릭은 돌아서면 헷갈린다...  
반복해서 정리해놓은 것을 보자! 언젠가는 매끄럽게 이해하겠지~👍  

### **지네릭 타입과 Raw 타입**  
[참고](https://velog.io/@eversong/Effective-Java-26.-%EB%A1%9C-%ED%83%80%EC%9E%85%EC%9D%80-%EC%82%AC%EC%9A%A9%ED%95%98%EC%A7%80-%EB%A7%90%EB%9D%BC)  
Raw 타입에 대해서는 다시 한번 정리를 하던가 해야겠다.  
