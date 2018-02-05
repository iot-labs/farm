# Arduino SD Card Read/Write 기능 개발 for Windows

-  Arduino ESP 8266 에 SD Card 모듈을 장착하고 read/write를 가능하게 합니다.
<br>

---
## Arduino, ESP8266 pin mapping(핀 연결)

먼저 ESP 8266과 Arduino를 pin map 참고하여 서로 포트에 mapping을 해줘야 합니다.

- 각 포트들을 기존의 arduino와 mapping을 하여 사용합니다. SD Card 모듈은 ESP 8266과의 연결을 위해 기본적인 GND와 3V 출력 포트, 그리고 직렬 클럭인 SCK, 마스터로부터 입력받는 MOSI, 마스터에게 출력하는 MISO, 칩 셀렉트를 해주는 CS 총 6개의 연결이 필요합니다.

Arduino와 ESP8266은 아래와 같이 핀을 mapping 해줍니다.
```
Arduino   <--->   ESP8266
  D5      <--->   SCK
  D6      <--->   MISO
  D7      <--->   MOSI
  D8      <--->   CS
  GND     <--->   GND
3V OUTPUT <--->   3V OUTPUT
```
<br>

---
## 실제 pin mapping 이미지

 ![kakaotalk_20171119_123714107](https://user-images.githubusercontent.com/15361210/32987190-84c2696c-cd27-11e7-9365-7abb0119fe59.jpg)

![kakaotalk_20171119_123706948](https://user-images.githubusercontent.com/15361210/32987191-84eedf10-cd27-11e7-9b4d-c76ae3884670.jpg)
<br>

---
## 전체 회로도

![default](https://user-images.githubusercontent.com/15361210/32987201-e2354254-cd27-11e7-94af-d077178c8adc.png)
<br>

---
## 코드

```
/*
  SD card read/write

 This example shows how to read and write data to and from an SD card file
 The circuit:
 * SD card attached to SPI bus as follows:
 ** MOSI - pin 11
 ** MISO - pin 12
 ** CLK - pin 13
 ** CS - pin 4 (for MKRZero SD: SDCARD_SS_PIN)

 created   Nov 2010
 by David A. Mellis
 modified 9 Apr 2012
 by Tom Igoe

 This example code is in the public domain.

 */

  #include <SPI.h>
  #include <SD.h>
  /*
  #define D0 16
  #define D1 5 // I2C Bus SCL (clock)
  #define D2 4 // I2C Bus SDA (data)
  #define D3 0
  #define D4 2 // Same as "LED_BUILTIN", but inverted logic
  #define D5 14 // SPI Bus SCK (clock)
  #define D6 12 // SPI Bus MISO
  #define D7 13 // SPI Bus MOSI
  #define D8 15 // SPI Bus SS (CS)
  #define D9 3 // RX0 (Serial console)
  #define D10 1 // TX0 (Serial console)
  */
  File myFile;

  void setup() {
    // Open serial communications and wait for port to open:
    // 통신 확인을 위해 Serial.begin()을 통해 연결
    Serial.begin(9600);
    while (!Serial) {
      ; // wait for serial port to connect. Needed for native USB port only
    }


    Serial.print("Initializing SD card...");

    //SD library를 통해 SD.begin으로 SD library와 sd card 초기화 시도
    if (!SD.begin(D8)) {
      Serial.println("initialization failed!");
      return;
    }
    Serial.println("initialization done.");

    // open the file. note that only one file can be open at a time,
    // so you have to close this one before opening another.
    // SD.open()으로 파일 열기 시도
    myFile = SD.open("test.txt", FILE_WRITE);

    // if the file opened okay, write to it:
    if (myFile) {
      Serial.print("Writing to test.txt...");
      // file.println()을 통해 SD card에 string을 저장
      myFile.println("testing 1, 2, 3.");
      // close the file:
      myFile.close();
      Serial.println("done.");
    } else {
      // if the file didn't open, print an error:
      Serial.println("error opening test.txt");
    }

    // re-open the file for reading:
    myFile = SD.open("test.txt");
    if (myFile) {
      Serial.println("test.txt:");

      // read from the file until there's nothing else in it:
      while (myFile.available()) {
        Serial.write(myFile.read());
      }
      // close the file:
      myFile.close();
    } else {
      // if the file didn't open, print an error:
      Serial.println("error opening test.txt");
    }
  }

  void loop() {
    // nothing happens after setup
  }
```
<br>

---
## 참고사항
Arduino에서 파일 시스템은 한 번에 한 개의 파일 연결을 다룹니다. <br>
따라서 하나의 파일과의 작업을 한 후, 다른 파일을 작업하기 위해서는 먼저 사용한 파일을 file.close()를 통해서 닫아줄 필요가 있습니다.

쓰기 작업용(Write)으로 SD.open 명령 수행<br>
`SD.open("test.txt", FILE_WRITE);`

코드 작성<br>
`myFile.println("testing 1, 2, 3.");`

작업중인 파일 닫기 처리<br>
`myFile.close();`

이후 다시 SD.open을 통해 읽기용으로 열어 준 것을 확인할 수 있습니다.
<br>

---
## 해결해야 할 문제
- **Tom Igoe**의 프로젝트를 참고하여 작성한 개발내용입니다. 따라서 iot-labs farm 프로젝트에 적합한 코드인지 추가 검증이 필요합니다.
- sync fail 문제 : 아두이노로 공급하는 USB포트의 전류가 부족할 때 발생한다는 케이스가 있었습니다. 외부 전원 공급 장치를 사용했을때 문제가 없다는 기록이 있으므로 외부전원 공급하에 테스트를 해봐야 합니다.
<br>

---
## 참고 라이브러리
link : https://www.arduino.cc/en/Reference/SD
