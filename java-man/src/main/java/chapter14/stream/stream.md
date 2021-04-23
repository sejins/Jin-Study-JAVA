# 스트림  

## **스트림이란**  

많은 수의 데이터를 다루기 위해서 배열과 컬렉션을 사용하여, for 문과 Iterator를 사용해서 이를 다루었다.  

하지만 이러한 방식에는 문제점이 존재하는데, 바로 데이터 소스마다 이를 다루는 방식이 다르다는 것이다.  

컬렉션 프레임 워크를 통해서 표준화를 하긴 했지만, 모두 표준화를 하진 못했다.  

스트림을 통해서 이러한 문제점을 해결할 수 있다.  

스트림은 `데이터 소스`를 `추상화`하고, 데이터들을 다루는데 자주 사용되는 메서드들을 정의해놓았다.  

데이터 소스를 추상화 했다는 말은 데이터 소스가 어떤 것이던 간에 `같은 방식`으로 다룰 수 있다는 것이다.  

```java
String[] strArr = {"aaa","bbb","ccc"};
List<String> strList = Arrays.asList(strArr);

Stream<String> strStream1 = strList.stream(); // List로부터 스트림 생성하기
Stream<String> strStream2 = Arrays.stream(strArr); // 배열로부터 스트림 생성하기 

strStream1.sorted().forEach(System.out::println);
strStream2.sorted().forEach(System.out::println);
```  

이처럼 두 스트림의 데이터 소스는 다르지만, 정렬하고 출력하는 방법은 완전하게 동일하다.  

비교를 위해서 스트림을 사용하지 않았을 경우를 보자  
```java
Arrays.sort(strArr);
Collection.sort(strList);

for(String str : strArr)
    System.out.println(str);

for(String str : strList)
    System.out.println(str);
```  

스트림을 사용하게 되면 표준화가 된다는 장점도 있지만, 이처럼 코드도 더 간결해진다.  

**스트림은 데이터 소스를 변경하지 않는다**  

스트림은 데이터 소스로부터 데이터를 읽기만 할 뿐, 데이터 소스를 변경하지 않는다.  

필요하면 처리한 결과를 컬렉션이나 배열에 담아서 반환할 수는 있다.  

**스트림은 일회용이다**  

스트림은 Iterator 처럼 일회용이다.  
스트림 또한 한번  사용하고 나면 닫혀서 다시 사용할 수 없고, 새로운 스트림을 생성해야한다.  

**스트림은 작업을 내부 반복으로 처리한다**  

스트림을 사용한 코드가 간결해지는 이유라고 할 수 있다.  

스트림 메서드들은 내부적으로 반복문을 통해서 데이터를 처리하기 때문에, 기존의 반복문을 사용해서 처리하던
내용을 메서드로 해결 할 수 있어 코드가 간결해진다.  

**스트림의 연산**  

스트림의 연산은 `중간 연산` 과 `최종 연산` 으로 분류할 수 있다.
* `중간 연산` 연산의 결과가 스트림인 연산. 그렇기 때문에 체이닝이 가능.
* `최종 연산` 연산의 결과가 스트림이 아닌 연산. 스트림의 요소를 소모하기 때문에 단 한번만 가능.  

**지연된 연산**  

지연된 연산이란, 스트림의 전체 연산에서 최종 연산이 수행되기 전까지는 중간연산이 수행되지 않는다는 것이다.  

중간연산을 호출하는 것은 단지 어떤 연산이 수행되어야하는지를 지정해주는 역할을 할 뿐이다.  

최종 연산이 수행되어야, 중간연산들이 순차적으로 실행이 되고 최종연산에서 스트림이 소모된다.  

**Stream< Integer > 와 IntStream**  

이는 앞의 함수형 인터페이스에서도 알아봤듯이, 오토박싱 & 언박싱의 오버헤드를 줄이기 위해서 기본형의 데이터
소스를 다루는 스트림들이 미리 정의 돼있다.  

**병렬 스트림**  

스트림으로 데이터를 다룰 때의 장점 중 하나가 병렬 처리가 쉽다는 것이다.  

병렬 스트림은 내부적으로  fork&join 프레임워크를 사용하여 자동적으로 연산을 병렬로 수행한다.  

병렬 처리를 위해 parallel() 메서드로  스트림의 속성을 변경해주면 된다.  

## **스트림 생성**  

**컬렉션** 

`Collection` 인터페이스에 `stream()` 메서드가 정의 되어 있다.  
```java
List<Integer> list = Arrays.asList(1,2,3,4,5);
Stream<Integer> intStream =  list.stream(); // 스트림 생성
```  

**배열**  

베열로 부터 스트림을 생성하는 방법에는 Stream 인터페이스를 사용하거나, Arrays 클래스를 사용하는 방법이 있다.  
```java
//  Stream 인터페이스의 static 메서드 of 사용
Stream<String> strStream = Stream.of("a","b","c");
Stream<String> strStream2 = Stream.of(new String[]{"a","b","c"});

// Arrays 클래스의 static 메서드 stream 사용
Stream<String> strStream3 = Arrays.stream(new String[]{"a","b","c"});
```  
추가적으로 기본형 타입을 데이터 소스로 하는 스트림을 생성하는 메서드들도 있다.  

**특정 범위의 정수**  

IntStream 과 LongStream 은 지정된 범위의 연속된 정수를 스트림으로 생성해서 반환하는 메서드가 있다.  
```java
IntStream intStream = IntStream.range(1,5); // 1,2,3,4
IntStream intStream2 = IntStream.rangeClosed(1,5); // 1,2,3,4,5
```  

**임의의 수**  

Random클래스에는 해당 타입의 난수들로 이루어진 스트림을 반환하는 메서드가 있다.  
```java
IntStream ints()
LongStream longs()
DoubleStream doubles()
```  

이 메서드들이 반환하는 스트림은 크기가 정해지지 않은 무한 스트림 이기 때문에 limit()를 사용해서  
스트림의 크기를 제한해주어야한다.  

아니면 애초에 Random 클래스의 스트림을 생성하는 메서드 중에서, 지정 개수의 스트림을 반환하는 메서드를 사용해줘야함.  

추가적으로 지정된 범위의 난수를 발생 시키는 스트림을 반환하는 메서드들도 있다.  

모든 메서드가 타입별로 오버로딩 되어있음!  

**람다식**  
Stream 인터페이스의 static 클래스 중 `iterate` 와 `generate` 메서드는  
람다식을 매개변수로 받아서, 이 람다식에 의해 계산되는 값들을 요소로 하는 무한 스트림을 생성할 수 있다.  

```java
static <T> Stream<T> iterate(T seed, UnaryOperator<T> f)
static <T> Stream<T> generate(Supplier<T> s)
```  

`iterate` 메서드는 seed 값부터 시작해서, 람다식 f에 의해 계산된 결과를 다시 seed 값으로 하면서 계산을 반복한다.  

```java
Stream<Integer> intStream = Stream.iterate(0, n -> n+2); // 0, 2, 4, 6 ....
``` 

`generate` 메서드는 seed 값이 없이 반복해서 계산을 한다.  

```java
Stream<Integer> intStream = Stream.generate(Math::random);
Stream<Integer> intStream2 = Stream.generate(() -> 1); // 1, 1, 1, 1 ....
```  

**파일**  

`java.nio.file.Files` 클래스에는 파일을 다루는데 유용한 메서드들을 제공하는데, list() 는 지정된 디렉토리에
있는 파일의 목록을 데이터 소스로 하는 스트림을 생성해서 반환한다.
```java
Stream<Path> Files.list(Path dir)
```  
이외에도 파일의 한 행을 요소로 하는 스트림이라던지 스트림을 생성하는 다양한  메서드가 있다.  
```java
Stream<String> Files.lines(Path path)
```  

**빈 스트림**  

요소가 하나도 없는 비어있는 스트림을 생성할 수 있다.  

스트림에 연산을 수행한 결과가 하나도 없을 때, null보다는 빈 스트림을 반환하는 것이 낫다.

```java
static <T> Stream<T> empty()
```  

**두 스트림의 연결**  

concat( ) 을 통해서 두개의 스트림을 하나로 연결할 수 있다.  
물론 두 스트림의 데이터 소스는 같은 타입이어야한다.  

이외에도 각종 스트림을 생성하는 다양한 메서드와 방법들이 있다.  

이는 나중에 필요한 상황이 되면, API 문서를 참조해서 사용하도록 하자.  

## **스트림의 중간연산**    

**스트림 자르기**  

`skip` 과 `limit` 메서드를 통해서 스트림의 일부를 잘라낼 수 있다.  
```java
IntStream intStream = IntStream.rangeClosed(1,10); // 1 ~ 10
intStream.skip(3).limit(5).forEach(System.out::println); // 4 5 6 7 8
```  

**스트림의 요소 걸러내기**  

`distinct` 는 스트림에서 중복된 요소들을 제거하고, `filter` 는 매개변수로 주어지는 Predicate에 맞지 않는 요소를 걸러낸다.  
```java
Stream<T> filter(Predicate<? super T> predicate)
Stream<T> distinct()
```  
```java
IntStream intStream = IntStream.of(1,2,2,3,3,3,4,5,5,6);
intStream.distinct().forEach(System.out::println); // 1 2 3 4 5 6 

intStream = IntStream.rangeClosed(1,10);
intStream.filter(i -> i%2==0).forEach(System.out::Println); // 2 4 6 8 10 
```  
이런식으로 사용하면 된다.  

그리고 스트림의 중간 연산은 다시 스트림을 반환하기 때문에 `체이닝`이 가능하다. 그렇기 때문에 이러한 표현을 사용할 수 있다.  

```java
intStream.filter(i -> i%2==0 && i%3==0).forEach(System.out::println);
intStream.filter(i -> i%2==0).filter(i%3==0).forEAch(System.out::println);;
```  
체이닝에 의해서 이 두 연산의 결과는 같다.  

**정렬**  

스트림을 정렬할 때 sorted 메서드를 사용한다.  

```java
Stream<T> sorted()
Stream<T> sorted(Comparator<? super T> comparator)
```  

매개변수를 지정하지 않으면 스트림 요소의 기본 정렬기준으로 정렬을 한다. 하지만 요소가 Comparable 인터페이스를 구현하지 않은 경우에는
에러를 발생시킨다.  

매개변수로 Comparator을 지정하면 해당 Comparator 에 의해서 정렬을 수행한다.  

`JDK 1.8` 이후로 Comparator 인터페이스에 다양한 static 메서드와 default 메서드가 생겨서 Comparator를 좀 더 편하게 정렬이 가능하다.  

가장 기본적인 메서드는 static 메서드의 comparing 메서드인데, 나머지 메서드는 필요하면 API문서 참조.  

```java
static <T, U extends Comparable<? super U>> Comparator<T> comparing(Function<T, ? extends U> keyExtractor)
```  
이 메서드의 선언부를 해석하면 어떻게 쓰이는지 이해할 수 있을 것! 지네릭을 이해하는데 큰 도움이 될 듯하다!

StreamEx 예제  

