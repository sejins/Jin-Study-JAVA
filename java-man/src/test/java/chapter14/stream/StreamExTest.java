package chapter14.stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class StreamExTest {

    @DisplayName("스트림 예제1")
    @Test
    void streamEx1(){
        Stream<Car> cars = Stream.of(new Car("bmw","520d",6300),
                                    new Car("benz","e300",8200),
                                    new Car("genesis","g90",9700),
                                    new Car("bmw","m5",13700),
                                    new Car("genesis","g80",7700),
                                    new Car("benz","c63",10200),
                                    new Car("bmw","m340",7800));

//        cars.sorted().forEach(System.out::println); // 단순히 price에 대해서 내림차순 정렬을 함.

//        cars.sorted(Comparator.comparing(Car::getManufacturer)).forEach(System.out::println); // 정렬 기준을 manufacturer로 정렬  -> manufacturer는 String 타입으로 Comparable 인터페이스를 구현한 클래스이다.

        cars.sorted(Comparator.comparing(Car::getManufacturer).thenComparing(Comparator.naturalOrder())).forEach(System.out::println);
    }

    @DisplayName("와일드 카드 사용 시 지네릭 메서드의 타입 유추가 궁금해서 하는 예제")
    @Test
    void streamEx2(){
        Stream<String> nameStream = Stream.of("sejin","eojin","mansa","soland","kimberly");

        // <R> Stream<R> map(Function<? super T, ? extends R> mapper)

        nameStream.map(Child::new) // 타입 R 이 Child 타입으로 잡힌 것이다.
                .forEach(Child::childMethod);


        nameStream = Stream.of("sejin","eojin","mansa","soland","kimberly");
        Stream<Parent> parentStream = nameStream.map(Child::new); // 타입 R이 Parent로 잡혔다.
//        parentStream.forEach(Child::childMethod); // Parent 클래스에는 childMethod 메소드가 없어서 컴파일러가 메서드 참조를 보고 이를 Child 클래스의 static 메서드로 찾아버린다.
        // 타입 R 이 Parent로 잡히기 때문에 Child 클래스의 메소드가 사용이 안되는 것을 알 수 있음.
        parentStream.forEach(Parent::parentMethod);

        // 이런식으로 지네릭 메서드는 따로 타입을 명시해주지 않으면, 선언한 지네릭 타입이 사용되는 모든 곳( 메서드의 매개변수이나 리턴타입)을 컴파일러가 확인해서 유추를 하는 듯하다.
        // 맨 위의 경우에는 ? extends R 에서 Child 클래스를 넣었지만 R 타입에 대해서 어떠한 명시도 없었기 때문에 R 자체를 그냥 Child로 타입을 사용했다.
        // 직관적으로도 이게 맞는듯하다.
    }

    @DisplayName("스트림 예제2")
    @Test
    void streamEx3(){
        Stream<File> fileStream = Stream.of(new File("Ex1.java"), new File("Ex1"),new File("Ex1.bak"),
                new File("Ex2.java"),new File("Ex1.txt"));

        fileStream.map(File::getName) // Stream<File> -> Stream<String>
                .filter(s -> s.indexOf('.')!=-1) // 확장자가 없는것은 제외
                .peek(s -> System.out.printf("filename = %s%n",s))
                .map(s -> s.substring(s.indexOf('.')+1)) // Stream<String> -> Stream<String>
                .map(String::toUpperCase)
                .peek(s -> System.out.printf("extension = %s%n",s))
                .distinct()
                .forEach(System.out::println);

        // 실행 결과에서도 알 수 있듯이, 모든 중간연산은 지연연산이 된다는 것을 알 수 있다.

    }

    @DisplayName("갑자기 제네릭이 헷갈려서 하는 테스트!")
    @Test
    void justTest(){

//        StreamEx.thisIsTestMethod(Stream.of(new ArrayList<Child>())); // 안됨. List<Parent> 의 자손만 가능!
        StreamEx.thisIsTestMethod(Stream.of(new ArrayList<Parent>()));
        StreamEx.thisIsTestMethod(Stream.of(new ArrayList<>()));

        StreamEx.thisIsAnotherTestMethod(Stream.of(new ArrayList<Child>()));
        StreamEx.thisIsAnotherTestMethod(Stream.of(new ArrayList<Parent>()));
        StreamEx.thisIsAnotherTestMethod(Stream.of(new LinkedList<Child>()));
        StreamEx.thisIsAnotherTestMethod(Stream.of(new LinkedList<Parent>()));
    }

    @DisplayName("flatMap 에제")
    @Test
    void streamEx(){
        Stream<String[]> strArrStrm = Stream.of(new String[]{"abc","def","jkl"},
                new String[]{"ABC","GHI","JKL"}); // 배열의 스트림

        Stream<Stream<String>> strStrmStrm = strArrStrm.map(Stream::of);
        Stream<String> strStrm = strArrStrm.flatMap(Stream::of); // flatMap 을 통해서 이중 스트림을 평탄화

        strStrm.map(String::toLowerCase)
                .distinct()
                .sorted()
                .forEach(System.out::println);

        System.out.println();
    }

    @DisplayName("Optional클래스 예제")
    @Test
    void optionalEx(){

//        Optional<String> optVal = Optional.of(null); // Passing 'null' argument to parameter annotated as @NotNull  -> 컴파일 에러 발생
        Optional<String> optVal2 = Optional.ofNullable(null);

        Optional<String> optVal3 = Optional.empty();
        Optional<String> optVal4 = null;
        // 두개의 차이점. optVal4는 참조변수가 null이기 때문에 이는 어떠한 경우에도 NPE를 발생시킨다.
        // 하지만 optVal3는 엄연한 Optinal 클래스의 인스턴스이고, 감싸고있는 내부의 value값이 null이기 때문에 이를 Optional클래스의 기본값으로 사용하는 것이 알맞다.
        System.out.println(optVal3);
        System.out.println(optVal4);

//        optVal4.orElse("hello"); // 이경우 당연하게 NPE가 발생한다.
        System.out.println(optVal3.orElse("이건 아니지롱"));
        // 인텔리제이 진짜 똑똑하다.... 컴파일 에러뿐만아니라 논리적인 실수들도 알려주네,,,!!ㅋㅋㅋㅋ

        Optional<String> opt = Optional.ofNullable("");
        opt.ifPresent(s -> System.out.println("이거 null 아니다"));

        int i = Optional.of("123").filter(s -> s.length() > 0).map(Integer::parseInt).orElse(-1);
        System.out.println(i);

        int i2 = Optional.of("").filter(s -> s.length() > 0).map(Integer::parseInt).orElse(-1);
        System.out.println(i2);

        OptionalInt optInt = OptionalInt.of(0);
        OptionalInt optInt2 = OptionalInt.empty();
        System.out.println(optInt.orElse(99));
        System.out.println(optInt2.orElse(99));
    }
}