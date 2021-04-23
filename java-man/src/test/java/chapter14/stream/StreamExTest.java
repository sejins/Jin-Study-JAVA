package chapter14.stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
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
}