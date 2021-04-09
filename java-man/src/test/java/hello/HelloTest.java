package hello;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HelloTest {

    @DisplayName("hello 테스트")
    @Test
    void hello(){
        Hello hello = new Hello();
        hello.method();
    }
}