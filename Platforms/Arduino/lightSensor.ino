int pin = A0;

void setup() {
   Serial.begin(9600);
}

void loop() {
     int sensorValue  = analogRead(A0);
     Serial.print("sensorValue:");
     Serial.println(sensorValue);
     delay(200);
}
