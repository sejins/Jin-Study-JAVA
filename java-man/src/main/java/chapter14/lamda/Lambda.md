# 람다식    

`JDK 1.8`부터 추가된 람다식으로 인해서, JDK 1.5의 제네릭 이후로 자바는 또 엄청난 변화를 맞이했다.  

람다식의 도입으로 인해서 자바는 `객체지향 언어`인 동시에 `함수형 언어`의 성격까지 띄게 된다.  

처음 람다식을 공부하면서, 내가 지금까지 공부하면서 느낀 자바는 정말 철저하게 객체 지향적인 성향을 띄는 언어였는데, 함수형 언어라는 
패러다임을 어떻게 자바에 스며들게 했는지 궁금했다.  

### **람다식이란?**  

간단히 말해서 람다식이란 메서드를 하나의 `식(expression)`으로 표현한 것이다.  

여기서 간단하게 `statement` 와 `expression` 의 차이에 대해서 다시 환기하고 가도록 하자.  

[statement 와 expression 의 차이](https://shoark7.github.io/programming/knowledge/expression-vs-statement)  

대충..  식이란 계산 후 하나의 값(value)으로 환원되는 것을 말한다.  

```java
Arrays.setAll(arr, () -> (int)(Math.random*()*5)+1);
```  
이렇게 메서드를 람다식으로 표현하게 되면 메서드의 이름과 반환값이 없어지게 된다.
그래서 람다식을 `익명함수` 라고도 한다.  

위의 람다식이 메서드로 구현된 것 보다 간결하면서 직관적으로 이해하기가 쉽다는 것에 이견이 없을 것이다.  
라고 책에 설명이 되어있는데,, 물론 적응이 되고 익숙해지면 그럴 것 같다👍  

일반적으로 자바에서 메서드를 생성하기 위해서는 클래스도 생성해야하고, 그리고 그 메서드를 사용하기 위해서는
객체를 생성하고 메서드를 수행해야하는데,  

람다식을 사용하면 이러한 모든 과정 없이 가능하다.  

그리고 위에서 식(expression)이란 계산 후 하나의 값으로 환원되는 것이라고 했는데,  
그렇다. 메서드를 하나의 값으로 간주하게 되면 이를 변수에 저장할 수 있는 것이고, 매개변수로 전달할 수있고,
그리고 반환 값으로도 사용할 수 있게 되는 것이다.  

람다식을 통해서 `메서드`를 하나의 `값`으로 사용할 수 있는 것이다.  

### **람다식 작성하기**  

람다식을 작성하는 방법은 앞의 예제를 통해서 유추해볼 수 있었을 것이다.  

익명함수 답게 먼저 메서드의 이름부터 날린다.  그리고 반환타입도 제거한다.  
그리고 남아있는 매개변수 선언부와 body 부분 사이에 화살표를 추가한다.  

그냥 예를 몇번 만들어보고 이해하자.  

```java
int max(int a, int b){
    return a>b ? a : b ;
        }
```  
이것을 람다식으로 표현하면 다음과 같다. 하나의 식 이라는 것에 집중을 해보자.

```java
(int a, int b) -> {return a>b ? a : b ;}
```  
이는 식이고 `하나의 값`으로 간주할 수 있기 때문에 변수에 저장하거나 매개변수로 넘겨주거나 반환값으로 
리턴할 수 있다.  

그리고 `return` 문은 또 다시 식으로 표현 할 수 있다. return 은 값을 반환하니까 값을 
반대로 식으로 나타낼 수 있는 것이라고 생각하자.  
이는 식이라서 `;` 을 붙히지 않는다는 점을 주의하자.  
```java
(int a, int b) -> a > b ? a : b
```  

또 람다식에 선언된 매개변수의 타입이 추론이 가능한 경우에는 생략이 가능하다.  
대부분의 경우에 생략이 가능한데, 이는 차차 알게 될 것이다.  
```java
(a,b) -> a > b ? a : b
```  
이렇게 표현하니 확실히 메서드를 선언하고 사용하는 것 보다 코드상으로도 간결하다.  

매개변수가 하나뿐인 경우에는 괄호를 생략할 수 있다.  
단, 매개변수의 타입이 있을 경우에는 괄호를 생략할 수 없다.  
```java
a -> a + a // 가능
int a -> a + a // 안됨
```  

마지막으로 중괄호 `{}` 안의 문장이 하나만 존재할 경우에는 중괄호를 생략할 수 있다.  
이 때도 `;` 를 붙히지 않아야 하는 것에 주의하자.  
```java
(String name, int age) -> System.out.println(name + " : " + age)
```  

하지만 중괄호 안의 문장이 return 문일 경우에는 중괄호의 생략이 불가능하다.  
```java
(a,b) -> return a > b ? a : b  // 불가능!
(a,b) -> {return a > b ? a : b ;} // 가능
```  

### **함수형 인터페이스** 

그렇다면 람다식이 값이라고 했고, 람다식의 타입은 어떻게 되는 것일까?  

처음 람다식을 접했을 때, 자바의 객체 지향을 그대로 유지하면서 함수형 패러다임을 적용했던 것을 보고
감탄을  하기도 했었다.  

사실 람다식은 `익명 클래스`의 `구현체(객체)`와 동등하다.  

```java
(int a, int b) -> a > b ? a : b 
```  
```java
new Object(){
    int max(int a, int b){
        return a>b ? a : b ;
        }
}
```  
두 개는 같다고 볼 수있다.  
여기서 메서드의 이름 max는 임의로 붙인 것일 뿐 의미는 없다.  

그렇다면 여기서 람다식의 타입을 Myfunction 타입이 될 것이고, 이는 클래스 또는 인터페이스 일 것이다.  

다음과 같은 인터페이스가 정의되어 있다고 가정해보자.  

```java
Interface Myfunction{
    public abstract int max(int a, int b);
        }
```  
그리고 이 인터페이스를 구현한 익명 클래스의 객체는 이렇게 생성할 수 있을 것이다.  
```java
Myfunction f = new Myfunction(){
        public int max(int a, int b){
            return a > b ? a : b;
            }
    };
int big = f.max(3,5);
```
여기서 Myfunction 인터페이스에 정의된 max 메서드와 람다식의 선언부가 일치하게 된다.  

그래서 익명 객체를 `람다식`으로 대체를 할 수 있다.  
```java
Myfunction f = (a,b) -> a > b ? a : b ; // 익명 객체를 람다식으로 대체.
int big = f.max(3,5); // 익명 객체의 메서드를 호출
```  

이렇게 익명 객체를 람다식으로 다룰 수 있는 이유는 람다식도 실제로는 익명 객체이고, 
Myfunction 인터페이스를 구현한 익명 객체의 메서드 max() 와 람다식이 매개변수 `타입`, `개수`, `반환타입`이
일치하기 때문이다.  

이렇게 람다식을 사용하지만, 기존의 객체 지향적 규칙들을 위배하지도 않고 자연스럽게 녹아든다.  

그렇기 때문에 자바에서 이렇게 인터페이스를 통해 람다식을 다루기로 결정했고 이를 `함수형 인터페이스` 라고 한다.  

```java
@FunctionalInterface
interface Myfunction{
    public abstract int max(int a, int b);
}
```  
람다식과 함수형 인터페이스가 1 대 1 로 매핑 하기 위해서 함수형 인터페이스에는 `오직 하나의 추상메서드`만 
정의 되어 있어야한다.  

하지만 `static 메서드`와 `default 메서드`의 개수에는 제약이 없다.  

그리고 표준 애너테이션인 `@FunctionalInterface` 을 사용하면 컴파일러가 함수형 인터페이스를 올바르게 
선언했는지 확인해주니까 반드시 쓰도록 하자.  

**함수형 인터페이스 타입의 매개변수와 반환 타입**  

만일 어떤 메서드의 매개변수 타입이 함수형 인터페이스 타입이면, 메서드의 매개변수로 람다식을 참조하는 
참조변수를 넘겨줘야 한다는 말이다. 
```java
...
void aMethod(Myfunction f){
    f.myMethod();
        }
...

aMethod(() -> System.out.println("myMethod"));
```  
이런식으로 직접 람다식을 매개변수로 넘겨주는 것이 가능하다.  

마찬가지로 어떤 메서드의 반환 타입이 함수형 인터페이스일 경우에 직접 람다식을 리턴할 수 있다.  

이렇게 람다식을 값처럼 사용할 수 있고, 함수형 인터페이스 타입을 통해서 람다식을 참조하는 것이다.  

**정리하자면** 람다식은 사실 익명 클래스의 구현 객체이다.  
객체 지향을 유지하면서 람다식을 사용하기 위해서 이렇게 함수형 인터페이스를 사용하고, 
사실상 함수형 인터페이스 타입으로 람다식을 찹조한다고 생각하면 된다. 🙏  

아 확실히 한번 더 보니까 예전 보다는 빠르고 더 정확하게 이해되는 것 같다~ 무야호🙀  

LambdaEx 예제  

**람다식의 타입과 형변환**  

위에서 말했듯이 람다식은 익명 객체이다.  
그래서 엄밀히 말하면 함수형 인터페이스 타입으로 람다식을 참조하는 것이지, 함수형 인터페이스가 
람다식의 타입이라고는 할 수 없다.  

내부적으로는 객체를 생성할 때 타입이 있겠지만, 이는 컴파일러가 임의로 이름을 정하는 것이기 때문에
우리는 알 수 없다.  

람다식은 **함수형 인터페이스를 직접 구현하지는 않았지만**, **이 인터페이스를 구현한 객체와 완전히 
동일**하기 때문에 이러한 `형변환`이 가능한 것이다.  
```java
Myfunction f = (Myfunction)(() -> {});
```  
그리고 이 형변환은 `생략`이 가능하기 때문에 보통 생략을 하고 사용한다.  

하지만 이러한 람다식은 익명일 뿐 분명 객체인데도, Object타입으로 형변환이 불가능하고, 오직 
`함수형 인터페이스`로만 형변환이 가능하다.  
```java
Object obj = (Object)(() -> {});  // Object 타입으로 형변환이 불가능하다.
```
굳이 Object 타입으로 변환을 하고자 한다면, 먼저 함수형 인터페이스로 변환을 하고나서 형변환이 가능하다. 

LambdaEx 예제  

**외부 변수를 참조하는 람다식**  

람다식도 익명 객체, 즉 익명 클래스의 인스턴스이기 때문에 동일한 방식으로 
외부에 선언된 변수에 접근할 수 있다.  

람다식 내부에서 참조하는 지역변수는 final이 붙지 않았어도 상수로 간주되기 때문에 이를 
변경하는 일은 허용되지 않는다.  

LambdaEx 예제  

### **java.util.function 패키지**  

대부분의 메서드의 타입은 비슷하다, 매개변수의 개수가 없거나, 한개 또는 두개 등등, 반환 값도 없거나 한개.  

이러한 함수형 인터페이스를 조금이라도 일반화 하기 위해서 `java.util.function` 패키지에 미리 몇개의 
함수형 인터페이스가 정의 되어있다.  

이렇게 하면 함수형 인터페이스와 메서드의 이름도 일반화가 가능하고 재사용과 유지보수 측면에서 유리하다.  

**매개변수가 없거나 한 개인 함수형 인터페이스**

함수형 인터페이스 | 메서드 | 설명
----------------|-------|------
java.lang.Runnable | void run( ) | 매개변수, 반환값 없음
Supplier< T > | T get( ) | 매개변수는 없고, 반환값만 있음
Consumer< T > | void accept( ) | 매개변수만 있고, 반환값이 없음
Function< T, R > | R apply( T t ) | 일반적인 함수
Predicate< T > | boolean test( T t ) | 조건식을 표현하는데 사용  

이해를 하기 위해서 Prediate로 조건식을 표현해보자  
```java

Predicate<String> isEmptyStr = s -> s.length() == 0 ;

String s = "";

if(isEmptyStr.test(s)) System.out.println(" Empty String.");
```  

**매개변수가 두 개인 함수형 인터페이스**  

매개변수의 개수가 2개인 함수형 인터페이스는 접두사 Bi를 붙히면 된다.  

함수형 인터페이스 | 메서드 | 설명
----------------|-------|-------
BiConsumer< T, U > | void accept( T t, U u ) | 두개의 매개변수만 있고, 반환값은 없음 
BiFunction< T, U, R > | R apply( T t, U u ) | 두개의 매개변수와 한개의 반환값을 가짐
BiPredicate< T, U > | boolean test( T t, U u ) | 두개의 매개변수로 조건식을 표현함  

이외의 세 개 이상의 매개변수를 갖는 함수형 인터페이스는 직접 만들어서 사용해야 한다.  

```java
@FunctionalInterface
interface TriFunction<T, U, V, R>{
    R apply(T t, U u, V v);
}
```  
이런식으로 만들어서 사용하면 될 것이다.  

**UnaryOperator 와 BinaryOperator**  

이는 Function의 변형이라고 보면 된다.  

함수형 인터페이스 | 메서드 | 설명
----------------|-------|-------
UnaryOperator< T > | T apply( T t ) | Function의 자손. Function과 다르게 리턴 타입과 매개변수의 타입이 같음
BinaryOperator< T > | T apply( T t, T t ) | BiFunction의 자손. BiFunction과 다르게 리턴 타입과 매개변수의 타입이 같음  

람다식이 등장하고나서 컬렉션 프레임 워크에도 함수형 인터페이스를 사용하는 메서드가 많이 추가 되었다.  

```java
void forEach(Consumer<T> action)  
```  
이는 `Iterable` 인터페이스의 `forEach` 메서드이다.

LambdaEx 예제  : 이해하는 데 많은 도움이 될 것이다. API 문서 참고  
[https://docs.oracle.com/javase/8/docs/api/index.html](https://docs.oracle.com/javase/8/docs/api/index.html)  

예제를 수행하면서 느낀 것인데, 함수형 프로그래밍으로 인해서 메서드의 매개변수를 함수의 형태로 받는 것이
가능하니까 이는 즉, 메서드의 내부 로직을 매개변수로 받는 것이다.  

하나의 메서드로, 다양한 로직을 만들 수 있게 됐다고 볼 수 있다.  

이 덕분에 특정 상황에서는 작성해야할 코드가 정말 많이 줄어들게 될 것 같다.  아주 굳👍  

**기본형을 사용하는 함수형 인터페이스**  

앞의 함수형 인터페이스는 입력 매개변수 타입과, 반환 타입이 모두 제네릭 타입이었다.  

그래서 기본형을 사용하더라도, 래퍼클래스를 통해서 사용을 하게 되므로, 이는 다소 비효율 적이다.  

그래서 기본형을 사용하는 함수형 인터페이스도 제공된다.  

함수형 인터페이스 | 메서드 | 설명
----------------|-------|------- 
OoubleToIntFunction | int applyAsInt( double d ) | **A**to**B**Function은 입력은 `A`타입 출력은 `B`타입
ToIntFunction< T > | int applyAsInt( T t ) | To**B**Function은 입력은 `지네릭`타입, 출력은 `B`타입
IntFunction < R > | R apply( int i ) | AFunction은 입력은 `A`타입, 출력은 `지네릭` 타입 
ObjIntConsumer< T > | void accept( T t , int i ) | ObjAConsumer은 입력은 `지네릭타입`과 `A` 타입, 출력은 없음  

API 문서에서 검색을 해보면 직관적으로 이해할 수 있을 것이다.  

### **Function의 합성과 Predicate의 결합**  

java.util.function 패키지의 함수형 인터페이스에는 추상메서드 이외에도 default 메서드와 static 메서드가 정의 되어 있다.  

Function 인터페이스와 Predicate 인터페이스에 정의되어있는 메서드를 예로 들어보자.  
나머지 인터페이스의 메서드들도 그 쓰임이 비슷비슷하다.  
자세한건 역시가 API문서 ㄱㄱ  

```java
// Function<T,R>
default <V> Function<T,V> andThen(Function<? super R, ? extends V> after)
default <V> Function<V,R> compose(Function<? super V, ? extends R> before)
static <T> Function<T,T> identity()
```  

함수형 인터페이스에 대한 내용도, 지네릭데 대한 내용도 학습하기 좋은 내용인 것 같다.  

<img src="https://user-images.githubusercontent.com/71161576/115664337-a980e100-a37c-11eb-834d-884866700bcd.png" width="900" height="500">  

함수형 인터페이스 내에서, 그 함수형 인터페이스의 타입을 다른 함수형 인터페이스 타입과 결합하는 것이다.  

수학으로 따지면 합성 함수의 역할을 하는 것이다.  

```java
Function<String, Integer> f = s -> Integer.parseInt(s, 16) ;
Function<Integer, String> g = i -> Integer.toBinaryString(i) ;
Function<String, String> h = f.andThen(g);
```  
예를 들어, 문자열을 숫자로 변환하는 함수(함수형 인터페이스) 와 숫자를 이진 문자열로 변환하는 함수(함수형 인터페이스)를
합성하여 새로운 함수(함수형 인터페이스) h를 만들어 낼 수 있게 된다.  

```java
Function<String, Integer> f = s -> Integer.parseInt(s, 16) ;
Function<Integer, String> g = i -> Integer.toBinaryString(i) ;
Function<Integer, Integer> h = f.compose(g);
```  
compose를 사용하면 반대의 경우로, Function< Integer, Integer > 타입이 된다.  

static 메서드인 identity는 항등함수를 의미한다. 

```java
// Predicate<T>
default Predicate<T> and(Predicate<? super T> other)
default Predicate<T> or(Predicate<? super T> other)
default Predicate<T> negate()
static <T> Predocate<T> isEqual(Object targetRef)
```  

### **메서드 참조**  

람다식이 `하나의 메서드`만을 호출하는 경우에는 `메서드 참조`라는 방법을 통해서 람다식을 더 간단하게 표현할 수 있다.  

```java
Function<String,Integer> f = s -> Integer.parseInt(s);
```  
문자열을 정수로 변환하는 이 람다식은 하나의 메서드만을 사용하기 때문에 메서드 참조를 사용할 수 있다.  

```java
Function<String, Integer> f = Integer::parseInt; // 메서드 참조
```  

메서드 참조를 하면서 람다식의 일부분이 생략이 되었지만, 컴파일러는 메서드의 선언부와 함수형 인터페에스의
지네릭 타입을 통해서 이를 알아낼 수 있다.  

```java
BiFunction<String, String, Boolean> f = (s1,s2) -> s1.equals(s2);
```  
이를 다시 메서드 참조를 사용해서 나타내면 다음과 같다.  

```java
BiFunction<String, String, Integer> f = String::equals; // 메서드 참조
```  

말도안되게 코드가 간결해진다~!  
컴파일러는 함수형 인터페이스를 통해서 입력으로 String 타입의 변수 두개가 입력으로 들어간다는 사실을 알고 있기 때문에
이렇게 매개변수를 생략할 수 있게 되는 것이다.  

그리고 이미 생성된 객체의 메서드를 람다식에서 사용한 경우에는 클래스 이름 대신 그 객체의 `참조변수`를 적어줘야 한다.  
```java
MyClass obj = new MyClass();
Function<String, Boolean> f = x -> obj.equals(x);
Function<String, Boolean> f2 = obj::equals(x); // 메서드 참조
```  

위에서 클래스 이름으로 메서드를 참조하는 방법과 참조변수의 이름으로 메서드 참조를 하는 방법의 `차이점`을 봐놓자!  

종류 | 람다식 | 메서드 참조
----|--------|-----------
static 메서드 참조 | x -> ClassName.method(x) | ClassName::method
인스턴스 메서드 참조 | (x,y) -> x.method(y) | ClassName::method
특정 객체 인스턴스 메서드 참조 | x -> obj.method(x) | obj::method  

**생성자의 메서드 참조**  

생성자를 호출하는 람다식도 메서드 참조로 변환할 수 있다.  

```java
Supplier<MyClass> s = () -> new MyClass();
Supplier<MyCLass> s2 = MyClass::new; // 메서드 참조
```  
매개변수가 있는 생성자라면, 매개변수의 개수에 따라서 함수형 인터페이스를 알맞게 사용하면 된다.  
필요하면 새로운 함수형 인터페이스를 만들어야한다.  

```java
Function<Integer, MyClass> f = i -> new MyClass(i); // 람다식
Function<Integer, MyClass> f2 = MyClass::new; // 메서드 참조

BiFunction<Integer, String, MyClass> hf = (i,s) -> new MyClass(i,s); // 람다식
BiFunction<Integer, String, MyClass> hf2 = MyClass::new; // 메서드 참조
```
```java
// 배열의 생성
Function<Integer, int[]> f = i -> new int[i];
Function<Integer, int[]> f2 = int[]::new;
```  

이처럼 메서드 참조는 코드를 간략히 하는데 유용해서 많이 쓴다.  

람다식을 메서드 참조로 변환하는 연습을 많이 하면 좋을 듯하다.  

LambdaEx 예제  

