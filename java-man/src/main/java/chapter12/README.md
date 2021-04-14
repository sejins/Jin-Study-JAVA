# 지네릭스  
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
* Box<T> : 지네릭 클래스
* T : 타입 매개변수
* Box : 원시 타입  

예를 들어, Box<String> 과 Box<Integer>는 다른 타입 매개변수를 사용해서 객체를 생성한 것 뿐이지,
이 둘이 별개의 클래스를 의미하는 것은 아니다.  
이는 하나의 매서드에 매개변수를 다르게 줘서 호풀하는 것과 같은 맥락이라고 생각하면 될 듯하다.  
컴파일 후에 Box<String> 과 Box<Integer>는 이들의 원시타입입 Box로 변경된다. 즉 지네릭 타입이 제거가 된다.  

### **지네릭스의 제한**  
지네릭 클래스의 객체를 생성할 때 객체 별로 다른 타입을 지정하는 것은 적절하다. 원래 인스턴스별로
다른 타입으로 동작하도록 만든 기능이기 때문이다.  
그래서 모든 인스턴스가 동일하게 동작해야하는 static 맴버에는 **당연히** 지네릭 타입을 사용할 수 없다.  
```java
class Box<T>{
    static T item; // 안된다.
    static void method(T t1, T t2){ ... } // 안된다.
}
``` 
그리고 지네릭 타입의 배열을 생성하는 것도 허용되지 않는다.  지네릭 배열 참조 변수를 선언하는 것은 가능하지만,
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