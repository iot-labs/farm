# 토양수분센서 연동 작업


## 회로도
![sen0114_dia](https://user-images.githubusercontent.com/22044186/32987498-f36cf9e6-cd30-11e7-8b9a-8b7a11ca81e5.png)
<br>

---
## 실제 구현모습
![default](https://user-images.githubusercontent.com/22044186/32987515-39d743d2-cd31-11e7-9be1-75abd35f2c90.jpg)
<br>

---
## 코드

```c
  # Example code for the moisture sensor
  # Date       : 18.11.2017
  # Version    : 1.0
  # Connect the sensor to the A0(Analog 0) pin on the Arduino board
  # the sensor value description
  # 0   ~  300     dry soil
  # 300 ~  700     humid soil
  # 700 ~  950     in water

  static int soil = 0;
  void setup() {
    delay(100);
    Serial.begin(57600);
    delay(100);
  }

  void dy() {
    soil = analogRead(A0);

    if(soil<300) {
      Serial.println("Moisture Sensor Value:"+ String(soil)+" "+"dry soil");
    }
    else if(soil<700) {
      Serial.println("Moisture Sensor Value: "+ String(soil)+" "+"humid soil");
    }
    else {
      Serial.println("Moisture Sensor Value:"+ String(soil)+" "+"in water");
    }

    delay(2000);
  }


  void loop() {
    dy();
  }

```
