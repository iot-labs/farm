
Arduino 온/습도 센서 개발
==============================

# Arduino 온/습도 센서 개발


* Arduino ESP 8266 에 DHT11 센서를 장착하여 온/습도 측정을 가능하게 한다.
## 사용한 DHT11의 경우 아래와 같은 모양을 하고 있다.

![dht11](https://user-images.githubusercontent.com/22091610/32996536-2d4bad9c-cdc7-11e7-8a5a-83501800f816.png)

* 이 경우 왼쪽부터 전원, 데이터 OUT, 사용하지 않음, 그라운드의 순서이다.




## 회로도의 경우 위와 같다.

![curcit](https://user-images.githubusercontent.com/22091610/32996882-12c5372c-cdcc-11e7-9dd7-b3ddbf273ba1.png)

* 디지털 2번포트와 데이터 포트가 연결되어 있고, 전원은 3.3V 그리고 GND에 연결되어 있다.

* 이런식으로 연결할 경우 출력으로 습도, 온도(섭씨, 화시), 습도를 고려한 체감온도가 출력되게 된다.

## 코드의 경우 아래와 같다.

// Example testing sketch for various DHT humidity/temperature sensors
// Written by ladyada, public domain

#include "DHT.h"

#define DHTPIN 2     // what digital pin we're connected to

// Uncomment whatever type you're using!
#define DHTTYPE DHT11   // DHT 11
//#define DHTTYPE DHT22   // DHT 22  (AM2302), AM2321
//#define DHTTYPE DHT21   // DHT 21 (AM2301)

// Connect pin 1 (on the left) of the sensor to +5V
// NOTE: If using a board with 3.3V logic like an Arduino Due connect pin 1
// to 3.3V instead of 5V!
// Connect pin 2 of the sensor to whatever your DHTPIN is
// Connect pin 4 (on the right) of the sensor to GROUND
// Connect a 10K resistor from pin 2 (data) to pin 1 (power) of the sensor

// Initialize DHT sensor.
// Note that older versions of this library took an optional third parameter to
// tweak the timings for faster processors.  This parameter is no longer needed
// as the current DHT reading algorithm adjusts itself to work on faster procs.
DHT dht(DHTPIN, DHTTYPE);

void setup() {
  Serial.begin(9600);
  Serial.println("DHTxx test!");

  dht.begin();
}

void loop() {
  // Wait a few seconds between measurements.
  delay(2000);

  // Reading temperature or humidity takes about 250 milliseconds!
  // Sensor readings may also be up to 2 seconds 'old' (its a very slow sensor)
  float h = dht.readHumidity();
  // Read temperature as Celsius (the default)
  float t = dht.readTemperature();
  // Read temperature as Fahrenheit (isFahrenheit = true)
  float f = dht.readTemperature(true);

  // Check if any reads failed and exit early (to try again).
  if (isnan(h) || isnan(t) || isnan(f)) {
    Serial.println("Failed to read from DHT sensor!");
    return;
  }

  // Compute heat index in Fahrenheit (the default)
  float hif = dht.computeHeatIndex(f, h);
  // Compute heat index in Celsius (isFahreheit = false)
  float hic = dht.computeHeatIndex(t, h, false);

  Serial.print("Humidity: ");
  Serial.print(h);
  Serial.print(" %\t");
  Serial.print("Temperature: ");
  Serial.print(t);
  Serial.print(" *C ");
  Serial.print(f);
  Serial.print(" *F\t");
  Serial.print("Heat index: ");
  Serial.print(hic);
  Serial.print(" *C ");
  Serial.print(hif);
  Serial.println(" *F");
}

* 이 코드의 경우 DHT11, DHT21, DHT22 겸용이며 위의 코드에서 바꿔주게 될경우 각각의 기기의 대하여 사용할 수 있다.
