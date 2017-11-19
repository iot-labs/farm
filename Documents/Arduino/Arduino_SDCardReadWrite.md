Arduino SD Card Read/Write 개발
==============================

## Arduino SD Card Read/Write 개발  (windows 용)


* Arduino ESP 8266 에 SD Card 모듈을 장착하고 read/write를 가능하게 한다.

먼저 ESP 8266은 기존의 arduino와는 다른 pin map을 가지고 있으므로 
```
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
```
* 각 pin들을 기존의 arduino와 mapping을 하여 사용합니다. SD Card 모듈은 ESP 8266과의 연결을 위해 기본적인 GND와 3V3, 그리고 직렬 클럭인 SCK, 마스터로부터 입력받는 MOSI, 마스터에게 출력하는 MISO, 칩 셀렉트를 해주는 CS 총 6개의 연결이 필요합니다.

 위의 define한 코드의 주석을 보면 연결을 위해 사용할 핀을 알 수 있습니다. D5는 SCK, D6은 MISO, D7은 MOSI, D8은 CS와 각각 연결합니다. GND와 3V3은 일반적인 연결을 합니다.
 
 ![kakaotalk_20171119_123714107](https://user-images.githubusercontent.com/15361210/32987190-84c2696c-cd27-11e7-9365-7abb0119fe59.jpg)

![kakaotalk_20171119_123706948](https://user-images.githubusercontent.com/15361210/32987191-84eedf10-cd27-11e7-9b4d-c76ae3884670.jpg)

* 전체 회로도

![default](https://user-images.githubusercontent.com/15361210/32987201-e2354254-cd27-11e7-94af-d077178c8adc.png)



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
  Serial.begin(9600);
  while (!Serial) {
    ; // wait for serial port to connect. Needed for native USB port only
  }


  Serial.print("Initializing SD card...");

  if (!SD.begin(D8)) {
    Serial.println("initialization failed!");
    return;
  }
  Serial.println("initialization done.");

  // open the file. note that only one file can be open at a time,
  // so you have to close this one before opening another.
  myFile = SD.open("test.txt", FILE_WRITE);

  // if the file opened okay, write to it:
  if (myFile) {
    Serial.print("Writing to test.txt...");
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
전체 코드입니다. 먼저 통신의 확인을 위해 Serial.begin()을 통해 연결합니다. 그 후, SD library를 통해 SD.begin으로 SD library와 sd card를 초기화합니다. SD library의 자세한 내용은 <https://www.arduino.cc/en/Reference/SD> 에서 확인할 수 있습니다.
* * *
중간 중간 Serial.println을 통하여 SD card와의 연결이 잘 이루어졌는 지 확인합니다. 파일 시스템은 SD.open()으로 파일을 열어 줍니다. 쓰기 용으로 파일을 열었을 때, 파일이 없다면 만들어 줍니다. file.println() 함수는 SD card에 string을 저장해 줍니다. 
Arduino에서 파일 시스템은 한 번에 한 개의 파일 연결을 다룹니다. 따라서 하나의 파일과의 작업을 한 후, 다른 파일을 작업하기 위해서는 먼저 사용한 파일을 file.close()를 통해서 닫아줄 필요가 있습니다.
위의 코드를 확인하시면 쓰기용으로 SD.open을 한 후, 작성이 끝나고 다시 file.close() 후, 다시 SD.open을 통해 읽기용으로 열어 준 것을 확인할 수 있습니다.

### 해결해야 할 문제
현재 위의 회로도와 코드는 문서와 다른 사람들의 프로젝트를 참고하여 작성한 것으로 문제가 없을 것으로 보이나 제대로 된 동작을 수행하는 것을 확인하지 못했습니다. 현재 sync fail 문제가 반복되고 있는데 해당 오류를 구글링한 결과 노트북 USB의 전류가 부족하여 외부 전원 공급 장치를 사용했더니 해결됐다고 하는 케이스가 있었습니다. 외부 전원 공급 장치가 없어 테스트 해보지는 못했지만, 한 번 시도해봐야 할 것 같습니다. 
