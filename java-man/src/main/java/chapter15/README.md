# 입출력  

## 자바에서의 입출력과 스트림 

입출력은 컴퓨터 내부 또는 외부 장치와 프로그램간의 데이터를 주고받는 것를 말한다.  

이러한 입출력을 수행하기 위해서 데이터를 주고받는 양 쪽을 매개하는 것이 `스트림`이다.  

스트림은 데이터를 운반하는데 사용되는 연결통로라고 생각하면 된다.

람다식과 함께 사용하는 스트림과 이름은 같지만 다른 의미이다.  

스트림은 데이터의 흐름을 물의 흐름에 비유해서 지어진 이름이라고 한다..  

스트림은 단방향 통신만 되기 때문에, 하나의 스트림은 임력 또는 출력 하나만 수행할 수 있다.  

그래서 입력과 출력을 모두 하려면 `입력 스트림`과 `출력 스트림`이 모두 필요하다.  

스트림은 먼저 보낸 데이터를 먼저 받게 되어있으며, 중간에 건너뜀없이 연속적으로 데이터를 주고받는다.  큐와같은 FIFO 구조로 되어있다.  

## 바이트기반 스트림  InputStream, OutputStream

바이트 기반 스트림은 `바이트 단위`로 데이터를 전송한다.  한번에 한 바이트씩 데이터를 읽어온다.  

입력스트림 | 출력스트림 | 입출력 대상의 종류
---------|-----------|------------------  
FileInputStream|FileOutputStream|파일  
ByteArrayInputStream|ByteOutputStream|메모리(byte배열)
PipedInputStream|PipedOutputStream|프로세스
AudioInputStream|AudioOutputStream|오디오 장치  

입출력을 수행할 대상에 따라서 적절한 스트림을 사용하면 된다.  

이 스트림들은 모두 `InputStream` `OutputStream`의 자손이다.  

두 스트림에 데이터를 읽고 쓰는 메서드를 추상메서드를 구현해놓았고, 각각의 스트림에서 알맞게 구현해서 사용하게 된다.  

그렇기 때문에 대상이 무엇일지라도 읽고 쓰는 방법이 표준화가 되어있어서 동일한 방식으로 사용하면 된다.  

InputStream | OutputStream
------------|--------------  
abstract int read() | abstract void write(int b)
int read(byte[] b) | void write(byte[] b)
int read(byte[] b, int off, int len) | void write(byte[] b, int off, int len)  

위에서 오버로딩된 read 메서드와 write 메서드는 내부적으로 추상메서드인 read, write 메서드를 사용한다.  
메서드는 선언부만 알고있으면 사용할 수 있기 때문!  

암튼 각 스트림들이 대상에 알맞게 read, write 메서드를 구현해놓았기 때문에 이를 사용하면된다.  

`InputStream` `OutputStream`을 통해서 표준화를 했다는 것이 중요!  

바이트 기만 스트림의 입출력 메서드의 리턴 타입과 매개변수 타입이 int 인 이유는 더이상 읽을 값이 없을 
때를 -1로 표현하기 위해서이다.  

하지만 입출력 과정에서 스트림을 통해 `1 byte`만 읽고 써지는 것은 동일하다.  

IOEx 예제

## 보조 스트림  

스트림의 기능을 보완하기 위한 보조 스트림도 존재한다.  

보조 스트림은 실제로 데이터를 주고받는 스트림이 아니라 데이터를 입출력할 수 있는 기능은 없다.  

스트림의 기능을 향상시키서나, 새로운 기능을 추가할 수 있다.  

그렇기 때문에 보조스트림은 `기반 스트림`을 필요로하고, 이를 통해서 생성할 수 있다.  

```java
// 기반 스트림
FileInputStream fis = new FileInputStream("test.txt");

//보조 스트림
BufferedInputStream bis = new BufferedInputStream(fis);
```  

이렇게 사용하면  보조 스트림 bis가 입출력을 수행하는 것처럼 보이지만, 내부적으로 입출력 메서드는 기반 스트림의 입출력 메서드를 그대로 호출한다.  

단지 보조 스트림 bis는 기반 스트림에 버퍼를 제공하는 기능을 수행한다.  

버퍼를 사용하고 말고의 성능차이는 상당하기에 대부분의 경우 버퍼를 이용한 보조 스트림을 사용한다.  

모든 보조 스트림들은 `FilterInputStream` `FilterOutputStream` 의 자손이고 이 둘은 다시 InputStream OutputStream의 자손이기 때문에 
보조 스트림들도 입출력 방법이 기반 스트림과 동일하게 표준화가 되어있다.  

## 문자기반 스트림 Reader, Writer  

자바에서는 하나의 문자가 2 byte로 구성이 되기 때문에, 문자 데이터를 읽어오는데 있어서 바이트 기반 스트림을 사용하면 무리가 있다.  

이 점을 보완하기 위해서 문자 기반 스트림이 존재한다.  

문자 데이터를 입출력할 때는 문자 기반 스트림을 사용한다.  

InputStream - Reader  
OutputStream - Writer  

바이트 기반 스트림 | 문자 기반 스트림
-----------------|---------------
FileInputStream / FileOutputStream |   FileReader / FileWriter  
ByteArrayInputStream / ByteArrayOutputStream | CharArrayReader / CharArrayWriter  
PipedInputStream / PipedOutputStream | PipedReader / PipedWriter  
StringBufferInputStream / StringBufferOutputStream | StringReader / StringWriter

StringBufferInputStream / StringBufferOutputStream 는 deprecated 되었음.  

Reader와 Writer도 입출력 추상메서드를 선언하고 이를 각 스트림에서 적절하게 오버라이딩하였기 때문에 사용하기만 하면된다.  

보조스트림 역시 문자기반 보조스트림이 존재하고, 이 또한 바이트기반 보조스트림과 사용방법이 동일하다.  

FilterInputStream - FilterReader
FilterOutputStream - FilterWriter

BufferedInputStream / BufferedOutputStream - BufferedReader / BufferedWriter   