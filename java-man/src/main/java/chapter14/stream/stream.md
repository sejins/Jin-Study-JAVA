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

**변환 - map**  

스트림의 요소로 저장된 값 중에서 원하는 필드만 뽑아내거나, 특정 형태로 변환해야 할 때 사용할 수 있다.
```java
Stream<R> map(Function<? super T, ? extends R> mapper
```  

```java
Stream<File> fileStream = Stream.of(new File("Ex1.java"), new File("Ex1"),new File("Ex1.bak"),
        new File("Ex2.java"),new File("Ex1.txt"));

fileStream.map(File::getName) // Stream<File> -> Stream<String>
    .filter(s -> s.indexOf('.')!=-1) // 확장자가 없는것은 제외
    .map(s -> s.subString(s.indexOf('.')+1)) // Stream<String> -> Stream<String>
    .map(String::toUpperCase)
    .distinct()
    .forEach(System.out::Println);
```  
자세한 건 API 문서를 참조하자.  

**갑자기 궁금해진 지네릭 메서드의 지네릭 타입 결정!**

map은 알다시피 Stream의 메서드 중에서도 새로운 타입 R에 대한 지네릭 메서드이다.  
그렇다면 지네릭 메서드의 타입을 컴파일러가 알아서 유추를 하는데 map 처럼 지네릭 타입이 R인데
매개변수로 T extends R의 타입이 올 수 있으면, 이럴때 R의 타입을 어떻게 유추하는 것인지 궁금해졌다.

StreamEx 예제  

**조회 - peek**  

연산 사이에 올바르게 처리가 되고 있는지 확인을 할 수 있다.  
최종 연산이 아니기 떄문에 스트림을 소모하지 않는다.  

StreamEx 예제 

**기본형 스트림으로읜 변환**  

```java
DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper)
IntStream mapToInt(ToIntFunction<? super T> mapper)
LongStream mapToLong(ToLongFunction<? super T> mapper)
```  
이러한 메서드를 통해서 스츠림을 기본형 스트림으로 변환할 수 있다.  

```java
Stream<U> mapToObj(IntStream<? super U> mapper) // IntStream -> Stream<U>
Stream<Integer> boxed() // IntStream -> Stream<Integer>
```  
반대로 이런 메서드를 통해서 기본형 스트림에서 제네릭 스트림으로 변환도 가능하다.  

```java
Stream<String> strStream = Stream.of("12","34","56");
strStream.mapToInt(Integer::parseInt); // ToIntFunction<? super T>
```  

**flatMap**  

```java
<R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper)
```  
flatMap을 통해서 결과가 `Stream<Stream<R>>` 인 람다식을 가지고 `Stream<R>` 의 스트림을 생성할 수 있다.  

StreamEx 예제  

## **Optional 클래스**  

`Optional<T>` 클래스는 지네릭 클래스로 T 타입의 객체를 감싸는 래퍼 클래스이다.  

```java
public final class Optional<T>{
    private final T value;
    ....
}
```  
그렇다면 이렇게 특정 타입의 객체를 Optinal 클래스에 담아서 사용하면 어떤 장점이 있는 것일까  

바로 `null checking`을 간결하게 할 수 있기 때문이다.  

NPE가 발생하지 않는 안전한 코드를 위해서는 지금까지는 if문을 통해서 null checking을 했었다.  

하지만 Optional 클래스의 각종 메서드를 통해서 간결하고 직관적으로 `null safe` 한 코드를 작성할 수 있게 되었다.  

**Otional 클래스의 생성** 

`of( )` 와 `ofNullable( )` 메서드를 통해서 Optinal 클래스를 생성할 수 있다.

단, of 의 매개변수는 null 이 아닌 값이어야한다. 그렇지 않으면 NPE를 발생시킨다.  

즉 참조변수가 null일 가능성이 있는 경우에는 ofNullable을 사용한다.  

그리고 null을 내부 value로 가지는 Optional 클래스를 생성하기 위해서는, `empty( )` 메서드를 사용.  

```java
Optional<String> optStr = Optional.empty();
Optional<String> optStr2 = null;
```  
이 두가지의 차이점에 대해서는 예제에서 설명을 해놓았다. Optional 클래스의 기본형으로는 전자를 선택하는 것이 좋다.  

**Optional 클래스의 값 사용하기**  

여기부터가 Optional 클래스를 왜 사용하는지 알 수 있는 부분일것이다. Optional 클래스를 사용하지 않으면 이부분을 
모두 if 문으로 처리해줘야한다.  

`get( )` 메소드를 통해서 value값을 가져올 수 있지만, 만약 value 값이 null 이라면, NoSuchElementException 이 발생한다.  

`orElse( )` 메소드를 통해서 value 값이 만약 null이라면 대체해서 사용할 값을 지정할 수 있다.  

그리고 orElse의 변형으로 `orElseGet( )` 메소드를 통해 null 대신 사용할 값을 반환하는 람다식을 지정 가능하고, 

orElseThrow( ) 를 통해서 null의 경우에 지정된 예외를 발생시킬수도 있다.
```java
String str3 = optStr3.ofElseGet(String::new);
String str4 = optStr4.orElseThrow(NullPointException::new);
```
이런식으로 사용이 가능하다.  

자세한건 API문서를 참고하도록하고, 이런 느낌으로 Optional클래스가 사용이 된다고 보면 된다.  

Optional 클래스는 스트림처럼 `map`, `flatMap`, `filter` 메소드를 사용할 수 있다.  
    
여기에서도 마찬가지로 flatMap은 `Optional<Optional<T>>` 를 반환하는 람다식을 통해 `Optional<T>` 를 반환하게 한다.  

자세한 사용은 API문서 참고!  

`isPresent( )` 메서드는 Optional 클래스의 value가 null이면 false, 아니면 true 를 반환한다. 

`ifPresent(Consumer<T> block)` 메서드는 value값이 null이면 아무것도 수행하지 않고, 그렇지 않으면 람다식의 내용
을 수행한다.  

```java
Optional.ofNullable(str).ifPresent(System.out::Println);
```  
if문 없이 이렇게 한줄로 null checking 과 그다음 동작을 수행할 수도 있다.  

isPresent 메소드는 Optinal 클래스를 반환하는 스트림의 최종연산과 자주 사용이 된다.  

이 메서드들은 필요할 때 API문서를 참고하자.  

**기본형을 value로 가지는 Optional**  

Optional 클래스도 기본형을 값으로 가지는 클래스들이있다.  
`OptionalInt`, `OptionalDouble`, `OptionalLong` 클래스는 제네릭 클래스가 아닌 각 자료형의 value 값을 가진다.  

제네릭 클래스가 아니라는 점을 제외하면 기존의 Optional 클래스와 동일하다.  

이처럼 기본형을 값으로 하는 Optional 클래스는 주의해야할 점이 있다.  

```java
OptionalInt optInt = OptionalInt.of(0);
OptionalInt optInt2 = OptionalInt.empty();
```  
이 두가지가 구분이 되냐에 관한 것이다.  
기본형 타입은 초깃값이 0이다. (타입에 따라서 0의 형태는 다를 수있다.)  

그렇다면 empty로 생성을 했을때도 value의 값을 0이 될텐데 이러면 두가지를 구분을 할 수 있냐는 것이다.  
이는 isPresent 메서드로 구분이 가능하다.  

```java
optInt.isPresent(); // true
optInt2.isPresent(); // false
optInt.equals(optInt2); // false
``` 

하지만 일반적인 Optional클래스에서는 null 과 그렇지 않은 참조변수의 값이 명확하기 때문에 이런 문제가 없다.  
```java
Optional<String> opt = Optional.ofNullable(null);
Optional<String> opt2 = Optional.empty();
opt.equals(opt2); // true
```