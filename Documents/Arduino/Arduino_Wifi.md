
## Arduino Wi-fi
- Wifi 연결
- IoT Labs  HTTPS Request 보낸다


### 개요
 - ESP8266MOD Wi-fi 모듈을 이용하여 웹서버에 센서들로부터 수집된 데이터를 주기적으로 전송하고, SD카드에 전송한 데이터를 기록한다.

 - URL Test 주소 : https://www.socialq.kr/servlet/util/PrintHeader
 - HTTPs Request 가 성공하면, kim@jongkwang.com 에게 이메일이 실시간 전송됩니다.

### 절차
 1. Wifi 공유기가 필요하며, ESP8266MOD 를 준비한다.
 2. 아래 Source code 에 공유기 연결정보를 작성하여 연결을 시도한다.
 3. 호출할 서버 URL 정보를 host, URL 변수에 작성한다.
 4. Loop 에는 1분주기마다 다음을 실행한다.

  1. 습도, Ground, 광량, 온도 센서로부터 데이터를 받아들인다.
  2. 수집된 데이터를 서버 URL 로 전송한다.
   : Seq 는 계속 증가하며, 전송받은 서버측에서 전송받은 데이터와 전송 받은 시간을 기록한다.
   3. SD카드에 데이터를 기록한다.
   4. 다시 Loop 1번부터 수행한다.

#### Source

```Arduino

//referenced by
//https://github.com/esp8266/Arduino/blob/master/libraries/ESP8266WiFi/examples/HTTPSRequest/HTTPSRequest.ino
#include <ESP8266WiFi.h>
#include <WiFiClientSecure.h>

const char *ssid = "ABCD"; // 공유기 연결정보
const char *password = "1234"; //공유기 연결 암호
const char* host = "socialq.kr"; // URL 호스트
const int httpsPort = 443;

// Use web browser to view and copy
// SHA1 fingerprint of the certificate
const char* fingerprint = "CF 05 98 89 CA FF 8E D8 5E 5C E0 C2 E4 F7 E6 C3 C7 50 DD 5C";

void setup() {
  Serial.begin(115200);
  Serial.println();
  Serial.print("connecting to ");
  Serial.println(ssid);
  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  //test 데이터
  root["seq"] = "1";
  root["device"] = "dev1";
  root["humidity"] = "43";
  root["ground"] = "17";
  root["light"] = "33";
  root["temperature"] = "2";

 // {"seq": "1", "device":"dev1", "humidity":"43", "ground":"17", "light":"33", "temperature":"2" }
  // seq, device, 온도, 습도, 그라운드, 광량, 온도

  root.printTo(Serial);
  Serial.println();
  root.prettyPrintTo(Serial);

  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());

  // Use WiFiClientSecure class to create TLS connection
  WiFiClientSecure client;
  Serial.print("connecting to ");
  Serial.println(host);
  if (!client.connect(host, httpsPort)) {
    Serial.println("connection failed");
    return;
  }

  if (client.verify(fingerprint, host)) {
    Serial.println("certificate matches");
  } else {
    Serial.println("certificate doesn't match");
  }

  // 연결 URL
  String url = "/servlet/util/PrintHeader";
  Serial.print("requesting URL: ");
  Serial.println(url);

  client.print(String("GET ") + url + " HTTP/1.1\r\n" +
               "Host: " + host + "\r\n" +
               "User-Agent: BuildFailureDetectorESP8266\r\n" +
               "Connection: close\r\n\r\n");

  Serial.println("request sent");
  while (client.connected()) {
    String line = client.readStringUntil('\n');
    if (line == "\r") {
      Serial.println("headers received");
      break;
    }
  }
  String line = client.readStringUntil('\n');
  if (line.startsWith("{\"state\":\"success\"")) {
    Serial.println("esp8266/Arduino CI successfull!");
  } else {
    Serial.println("esp8266/Arduino CI has failed");
  }
  Serial.println("reply was:");
  Serial.println("==========");
  Serial.println(line);
  Serial.println("==========");
  Serial.println("closing connection");
}

void loop() {
  // # 1분주기마다 다음을 실행한다.
  // 1. 습도, Ground, 광량, 온도 센서로부터 데이터를 받아들인다.
  // 2. 수집된 데이터를 서버 URL 로 전송한다.
  //   : Seq 는 계속 증가하며, 전송받은 서버측에서 전송받은 데이터와 전송 받은 시간을 기록한다.
  // 3. SD카드에 데이터를 기록한다.
}
```

#### 참고
https://github.com/esp8266/Arduino/blob/master/libraries/ESP8266WiFi/examples/HTTPSRequest/HTTPSRequest.ino
