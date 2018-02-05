
# Arduino IDE에 ESP8266 추가 하기 for Windows


## 아두이노 ESP8266 설치하기

#### Step 1
![1](https://user-images.githubusercontent.com/22044186/32977730-82101b9e-cc76-11e7-863d-a4f567508c29.png)

#### Step 2
![2](https://user-images.githubusercontent.com/22044186/32977738-b419f042-cc76-11e7-8756-627af9be6e48.png)
http://arduino.esp8266.com/stable/package_esp8266com_index.json
이 주소를 입력해준다.

#### Step 3
https://www.silabs.com/products/development-tools/software/usb-to-uart-bridge-vcp-drivers
들어간다.

#### Step 4
![image](https://user-images.githubusercontent.com/22044186/32977951-22e826ca-cc7b-11e7-83ab-9c03b04a7f1e.png)


#### Step 5
- windows 비트에 맞게 설치한다.
![image](https://user-images.githubusercontent.com/22044186/32977955-4bab10d6-cc7b-11e7-8c27-fc6ed44ba747.png)


#### Step 6
- 보드 매니저를 클릭
![image](https://user-images.githubusercontent.com/22044186/32977957-71e20fac-cc7b-11e7-9fcd-e8085365749c.png)

#### Step 7
- `[Tools -> Board -> Boards manager]`
- Boards Manager 창이 뜨면 esp8266 항목에서 최신 버전을 선택하고 `[Install]` 버튼 클릭
![image](https://user-images.githubusercontent.com/22044186/32977964-9d2513f8-cc7b-11e7-85f1-83ba98faf0f7.png)


#### Step 8
- 보드에 NodeMCU 1.0 버전을 선택한다.
![image](https://user-images.githubusercontent.com/22044186/32978013-91e5146a-cc7c-11e7-979c-01745a71ba13.png)



#### Step 9
- `파일 -> 예제 -> ESP8266 Blink`를 실행시킨다.
아두이노에 불빛이 들어오면 성공!
![image](https://user-images.githubusercontent.com/22044186/32978022-d1becd42-cc7c-11e7-9a77-0560d491b5f3.png)
