package chapter14.stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import static java.lang.System.out;

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
    void streamEx4(){
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

    @DisplayName("최종연산 예제 - reduce")
    @Test
    void streamEx5(){
        String[] strArr = {"Inheritance","Java","Lambda","stream","Spring","OptionalDouble","count","sum","JPA"};

        Stream.of(strArr).forEach(System.out::println); // 단순히 출력

        boolean noEmptyStr = Stream.of(strArr).noneMatch(s -> s.length()==0);
        Optional<String> strOpt = Stream.of(strArr).filter(s -> s.charAt(0)=='s').findFirst();

        System.out.println(noEmptyStr);
        System.out.println(strOpt.orElse("No Element"));

        IntStream intStream1 = Stream.of(strArr).mapToInt(String::length);
        IntStream intStream2 = Stream.of(strArr).mapToInt(String::length);
        IntStream intStream3 = Stream.of(strArr).mapToInt(String::length);
        IntStream intStream4 = Stream.of(strArr).mapToInt(String::length);
        // 제공되는 메서드가 있지만, reduce의 사용을 위해서 굳이 구현을 해봤다.

        int count = intStream1.reduce(0, (a,b) -> a + 1);
        int sum = intStream2.reduce(0, (a,b) -> a + b);
        OptionalInt max = intStream3.reduce(Integer::max);
        OptionalInt min = intStream4.reduce(Integer::min);

        System.out.println(count + " " + sum + " " + max.orElse(-99999) + " " + min.orElse(99999));
    }

    @DisplayName("최종연산 예제 - collect")
    @Test
    void streamEx6(){
        Car[] cars = new Car[]{new Car("bmw","520d",6300),
                new Car("benz","e300",8200),
                new Car("genesis","g90",9700),
                new Car("bmw","m5",13700),
                new Car("genesis","g80",7700),
                new Car("benz","c63",10200),
                new Car("bmw","m340",7800)};

        // 이름만 출력해서 이를 다시 List에 저장.
        List<String> carNames = Stream.of(cars).map(Car::getName).collect(Collectors.toList());
        out.println(carNames);

        // 스트림을 배열로 변환
        Car[] cars1 = Stream.of(cars).toArray(Car[]::new);

        // 스트림을 Map으로 변환. 자동차의 이름이 key역할.
        Map<String, Car> map = Stream.of(cars).collect(Collectors.toMap(Car::getName, v -> v));
        for(String key : map.keySet()){
            out.println(key + " : " + map.get(key));
        }

        //통계기능
        long count = Stream.of(cars).collect(Collectors.counting());
        long totalPrice = Stream.of(cars).collect(Collectors.summingLong(Car::getPrice));
        out.printf("count : %d  total price : %d%n",count,totalPrice);

        Optional<Car> optCar = Stream.of(cars).collect(Collectors.maxBy(Comparator.comparingInt(Car::getPrice)));
        out.println(optCar);

        IntSummaryStatistics stat = Stream.of(cars).collect(Collectors.summarizingInt(Car::getPrice));
        out.println(stat);

        // 문자열의 결합 기능 -> 스트림의 요소가 String 또는 StringBuffer 처럼 CharSequence의 자손인 경우에만 결합이 가능!
        // 요소의 타입이 위와같지 않은 경우, toString을 수행한 결과를 결합.
        String names = Stream.of(cars).map(Car :: getName).collect(Collectors.joining(",","{","}"));
        out.println(names);
    }

    @DisplayName("스트림 연산 - partitioningBy에 의한 분류")
    @Test
    void streamEx7(){
        Student[] stuArr = new Student[]{
                new Student("Habertz",true, 1, 1, 300),
                new Student("Werner",false, 1, 1, 250),
                new Student("Mount",true, 1, 1, 200),
                new Student("Pulisic",false, 1, 2, 150),
                new Student("Hazard",true, 1, 2, 100),
                new Student("Kante",false, 1, 2, 50),
                new Student("Rodrigo",false, 1, 3, 100),
                new Student("Lionel",false, 1, 3, 150),
                new Student("Giroud",true, 1, 3, 200),

                new Student("Habertz",true, 2, 1, 300),
                new Student("Werner",false, 2, 1, 250),
                new Student("Mount",true, 2, 1, 200),
                new Student("Pulisic",false, 2, 2, 150),
                new Student("Hazard",true, 2, 2, 100),
                new Student("Kante",false, 2, 2, 50),
                new Student("Rodrigo",false, 2, 3, 100),
                new Student("Lionel",false, 2, 3, 150),
                new Student("Giroud",true, 2, 3, 200)
        };

        // 단순 분할 -> 성별로 분할
        out.println("1. 단순 분할 : 성별");
        //Map<Boolean,Integer> map = Stream.of(stuArr).collect(Collectors.partitioningBy(Person::isTrue)); // -> 타입 추론 연습
        Map<Boolean,List<Student>> stuBySex =  Stream.of(stuArr).collect(Collectors.partitioningBy(Student::isMale));
        List<Student> stuMale = stuBySex.get(true);
        List<Student> stuFemale = stuBySex.get(false);
        for(Student s : stuMale){out.println(s);}
        for(Student s : stuFemale){out.println(s);}
        out.println();


        //단순 분할 + 통계 -> 성별로 분할 하고, 학생의 수
        out.println("2. 단순 분할 + 통계 : 성별 - 학생 수");
        Map<Boolean,Long> stuNumBySex = Stream.of(stuArr).collect(Collectors.partitioningBy(Student::isMale, Collectors.counting()));
        out.println("남학생 수 : "+stuNumBySex.get(true));
        out.println("여학생 수 : "+stuNumBySex.get(false));

        //단순 분할 + 통계 -> 성별로 분할 후, 최대 성적학생
        out.println("3. 단순 분할 + 통계 : 성별 - 최고점 학생");
        Map<Boolean, Optional<Student>> stuTopScoreBySex =  Stream.of(stuArr).collect(Collectors.partitioningBy(Student::isMale, Collectors.maxBy(Comparator.comparingInt(Student::getScore))));
        out.println("남학생 최고점 학생 : "+stuTopScoreBySex.get(true));
        out.println("남학생 최고점 학생 : "+stuTopScoreBySex.get(false));

        //다중 분할 + 통계 -> 성별로 분할 후, 최대 성적학생
        out.println("4. 다중 분할 + 통계 : 성별 - 100점 이하");
        Stream.of(stuArr).collect(Collectors.partitioningBy(Student::isMale, Collectors.partitioningBy(s -> s.getScore() >=100))); // 이것도 지네릭 메서드랑 지네릭 타입이 어떻게 변하고, 타입 안정성을 유지하는지 이해가 안되노..
        // 주말에 전부다 분석!
    }
}