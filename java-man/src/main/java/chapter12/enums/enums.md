# 열거형(enums)
### **열거형이란..**
열거형은 서로 관련된 상수를 편리하게 선언하기 위한 것으로, 여러 상수를 정의할 때 사용하면 유용하다.  
`JDK 1.5` 부터 등장했다.  
C언어에도 열거형이 존재하는데, 결론적으로 말하자면 그보다 더 향상된 것이라 할 수 있다.  
C 언어의 열거형은 타입에 상관없이 그 값만 같다면 조건식의 결과가 true이다.  
하지만 자바의 열거형은 값뿐만 아니라 타입 또한 관리하기 때문에 논리적인 오류 또한 줄일 수 있다.  
```java
class Card{
    static final int CLOVER = 0;
    static final int HEART = 1;
    static final int DIAMOND = 2;
    static final int SPADE = 3;
    
    static final int TWO = 0;
    static final int THREE = 1;
    static final int FOUR = 2;
    
    final int kind;
    final int num;
}
```  
이러한 상수들을 편리하게 열거형을 사용해서 나타낼 수 있다.  
```java
class Card{
    enum Kind { CLOVER, HEART, DIAMONE, SPADE }
    enum Value { TWO, THREE, FOUR }
    final Kind kind;
    final Value value;
}
```  
여기서 알 수 있듯이, `상수들을 클래스의(객체 지향적) 형태`로 선언하고 관리하는 기능이라고 이해하면 되겠다.  

열거형을 사용하지 않은 클래스에서는 다음이 성립한다.  
```java
if(Card.CLOVER == Card.TWO) // true. 하지만 논리적으로 둘은 다르다.
```  
열거형을 사용하면 다음과 같다.  
```java
if(Card.Kind.CLOVER == Card.Value.TWO) // 컴파일 에러가 발생한다. 값은 같더라도 타입이 다르다.  
```  
그리고 성능적인 측면에서도 차이점이 존재하는데, 원래 상수의 값이 변경되면 상수를 참조하는 모든 소스를 다시 컴파일 해야한다.  
하지만 열거형 상수를 사용하면 기존의 소스를 다시 컴파일 하지 않아도 된다.  

### **열거형의 정의와 사용**  

열거형은 중괄호 안에 상수의 이름을 나열하기만 하면 된다.  
```java
enum Direction { EAST, SOUTH, WEST, NORTH } // 열거형 Direction 선언
```  
이렇게 열거형에 정의된 상수를 사용하는 방법은 `열거형이름.상수명` 으로 사용한다.  
클래스의 static 멤버를 참조하는 것과 유사하다.  
```java
class Point{
    int x, y;
    Direction direction;
    
    void init(){
        direction = Direction.EAST;
    }
}
```  
클래스에서 Direction 열거형을 사용하는 예시이다.  

열거형 상수간에는 `==` 을 통해서 비교를 할 수 있다. equals 메서드를 사용하지 않고 == 을 통해서
비교할 수 있다는 것은 빠른 성능을 제공한다는 얘기다.  
하지만 `<` `>` 같은 비교 연산자는 사용이 불가능 하기 때문에, `compareTo` 메서드를 사용한다.  
```java
if(dir == Direction.WEST){...} // 가능
if(dir > Direction.WEST){...} // 에러. '>' , '<' 비교연산자 사용 불가능
if(dir.compareTo(Direction.WEST)){...} // compareTo 메서드는 가능
```  
그리고 이처럼 switch 문에도 열거형 상수를 사용할 수 있다.  
```java
switch(dir){
    case WEST: ....; break;
    case EAST: ....; break;
    case SOUTH: ....; break;
    case NORTH: ....; break;
}
```  
switch 문에서 주의할 점은 case 문에서 `열거형의 이름은 적지 않는다`는 것이다.  
모든 열거형의 조상은 `java.lang.Enum<E>` 클래스이다.  
`values` 메서드는 열거형의 모든 상수를 배열에 담아 반환해준다.  이 메서드는 모든 열거형이 가지고 있는 것으로, 
컴파일러가 자동으로 추가해준다.  
`ordinal` 메서드는 `Enum` 클래스에 정의 된 것으로, 열거형 상수가 정의된 순서를 정수로 반환한다.  
추가로, `valueOf` 메서드도 values 메서드처럼 컴파일러가 자동으로 추가해주는 메서드이다.  
이 메서드는 열거형 상수의 이름으로 문자열 상수에 대한 참조를 얻을 수 있다.  
`values()` `valueOf(String name)` 메서드는 Enum 클래스에 존재하는게 아닌 컴파일러가 자동으로 모든 열거형에 
추가해주는 메서드이다.  
EnumEx 예제  

### **열거형에 멤버 추가하기**  
열거형 상수의 값이 불연속적인 경우에는 다음과 같이 열거형 상수의 이름옆에 원하는 값을 괄호와 함꼐 적어준다.  
```java
enum Direction{ EAST(1), SOUTH(5), WEST(-1), NORTH(10) }
```  
그리고 지정된 값을 저장할 수 있는 인스턴스 변수와 샌성자를 새로 추가해 주어야 한다.  
주의할 점은 먼저 열거형 상수를 모두 정의한 다음에 다른 멤버들을 추가해야한다.

```java
enum Direction {
    EAST(1), SOUTH(5), WEST(-1), NORTH(10);
    
    private final int value;
    
    Direction(int value){this.value = value;}// 접근제어자 private 가 생략됨.
    
    public int getValue(){return value;}
}
```  
열거형의 인스턴스 변수는 반드시 final이라는 제약은 없지만, value는 열거형 상수의 값을 저장하기 위한
것이라서 final을 붙힌 것이다.  
그리고 열거형의 생성자를 추가했지만, 열거형의 생성자의 접근 제어자가 묵시적으로 `private` 이다.  
즉, 외부에서 열거형의 생성자를 호출할 수 없다.  

필요에 따라서 다음과 같이 하나의 열거형 상수에 여러 값을 지정할 수도 있다.  
이때는 값의 개수에 맞게 인스턴스 변수와 생성자등을 새로 만들어 줘야한다.  
```java
enum Direction{
    WEST(1,"<"), SOUTH(2,"V"), EAST(3,">"), NORTH(4,"^");
    
    private final int value;
    private final String symbol;
    
    Direction(int value, String symbol){ // 접근제어자 private 가 생략됨.
        this.value = value;
        this.symbol = symbol;
    }
    
    int getValue(){return value;}
    String getSymbol(){return symbol;}
}
```  
EnumsEx 예제  
예제를 통해서 열거형이 어떻게 상수를 `객체 지향적`으로 관리하고 사용하는지 알 수 있을 것이다.  
[참고](https://www.nextree.co.kr/p11686/)  <<<< 열거형의 등장배경부터 현재까지에 대해서 정리가 잘 돼있음.