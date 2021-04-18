package chapter12.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static java.lang.System.out;

import static org.junit.jupiter.api.Assertions.*;

class EnumExTest {

    @DisplayName("열거형 예제1")
    @Test
    void enumEx1(){
        Direction direction1 = Direction.EAST;
        Direction direction2 = Direction.valueOf("WEST"); // 컴파일러가 모든 열거형에 추가해주는 valueOf 메서드
        Direction direction3 = Enum.valueOf(Direction.class, "EAST"); // Enum 클래스에 존재하는 static 메서드 valueOf

        out.println("direction1 : " + direction1);
        out.println("direction2 : " + direction2);
        out.println("direction3 : " + direction3);

        out.println("direction1==direction2 : " + (direction1==direction2));
        out.println("direction1==direction3 : " + (direction1==direction3));
        out.println("direction1.equals(direction3) : " + (direction1.equals(direction3)));
//        out.println("direction2 > direction3 : " + (direction1 > direction3)); // 컴파일 에러. '>' 의 사용이 허용되지 않음.
        out.println("direction1.compareTo(direction2) : " + (direction1.compareTo(direction2)));
        out.println("direction1.compareTo(direction3) : " + (direction1.compareTo(direction3)));

        switch (direction1){
            case WEST: out.println("This is a WEST"); break;
            case SOUTH: out.println("This is a SOUTH"); break;
            case EAST: out.println("This is a EAST"); break;
            case NORTH: out.println("This is a NORTH"); break;
            default: out.println("Invalid direction."); break;
        }

        Direction[] directions = Direction.values();
        for(Direction dir : directions){
            out.printf("%s = %d%n", dir.name(), dir.ordinal());
        }
    }

    @DisplayName("여러 값을 저장하는 열거형 + 열거형의 객체지향적인 설계")
    @Test
    void enumEx2(){
        DirectionWithSymbol[] directions = DirectionWithSymbol.values();

        for(DirectionWithSymbol dir : directions){
            out.println(dir);
        }
        DirectionWithSymbol direction1 = DirectionWithSymbol.EAST; // 외부에서 직접적으로 생성자를 사용하는 것이 아니라, 내부적으로 클래스의 인스턴스 변수들과 상수가 매핑되는 형식인듯!
        DirectionWithSymbol direction2 = DirectionWithSymbol.of(1);

        out.printf("direction1 : %s%n", direction1);
        out.printf("direction2 : %s%n", direction2);

        out.println(DirectionWithSymbol.EAST.rotate(1));
        out.println(DirectionWithSymbol.EAST.rotate(2));
        out.println(DirectionWithSymbol.EAST.rotate(-1));
        out.println(DirectionWithSymbol.EAST.rotate(3));

    }
}