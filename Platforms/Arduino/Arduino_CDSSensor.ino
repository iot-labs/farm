void setup() 
{
	Serial.begin(9600);  // 시리얼 통신을 시작. 속도는 9600 으로 설정
}

void loop() 
{
	int sensorValue = analogRead(A0);  // 아날로그값을 저장
	float voltage = sensorValue * (5.0 / 1023.0);  // 아날로그 값을 변환
	Serial.println(voltage);  // 저장된 값을 출력
        delay(1000);  // 1초 delay
}
