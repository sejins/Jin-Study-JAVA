package chapter15;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class IOExTest {

    @DisplayName("FileInputStream")
    @Test
    void fileInput() throws IOException {
        FileInputStream fis = new FileInputStream("copy.txt");
        int data =0;
        while((data=fis.read())!=-1){ // 한 바이트씩 읽어서 출력 -> 바이트씩 읽어오지만 read 메서드의 리턴타입이 int이다.
            System.out.println((char)data);
            System.out.println(data);
        }
        fis.close();
    }

    @DisplayName("fileCopy 예제")
    @Test
    void fileCopy() throws IOException {
        FileInputStream fis = new FileInputStream("test.txt");
        FileOutputStream fos = new FileOutputStream("copy.txt");
        int temp = 0;
        while((temp=fis.read())!=-1){
            fos.write(temp); // 읽어서 바로 쓰기
        }
        fos.write(450); // 256 초과 -> 앞부분 잘린다. 194의 값으로 넘어감! fileInput 테스트로 확인해보면 알 수 있음.
        fis.close();
        fos.close();
    }
}