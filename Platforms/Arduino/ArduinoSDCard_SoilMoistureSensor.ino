
/*
  # Example code for the moisture sensor
  # Editor     : Lauren
  # Date       : 18.11.2017
  # Version    : 1.0
  # Connect the sensor to the A0(Analog 0) pin on the Arduino board
   
  # the sensor value description
  # 0  ~300     dry soil
  # 300~700     humid soil
  # 700~950     in water
*/
static int soil=0;
void setup(){
   delay(100);
  Serial.begin(57600);
  delay(100);
}
 void dy(){
    soil =analogRead(A0);
  if(soil<300){
    Serial.println("Moisture Sensor Value:"+ String(soil)+" "+"dry soil");
   }
   else if(soil<700){
    Serial.println("Moisture Sensor Value: "+ String(soil)+" "+"humid soil");

    }
    else {
      Serial.println("Moisture Sensor Value:"+ String(soil)+" "+"in water");
    }
  delay(2000);
  }
void loop(){
    dy();

}
