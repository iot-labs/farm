# IoT Labs - Farm : 농장 데이터 수집 및 분석 서비스

IoT Labs : Farm 프로젝트는 농장의 데이터를 수집하여 분석하는 서비스입니다.

#### 농장 데이터의 수집
- Raspberry Pi 등을 이용하여 온도/습도/광량 등을 수집하며 기록 합니다.

#### 수집된 데이터의 분석
- Raspberry Pi 와 센서들로 수집된 데이터는 중앙 서버에서 시각화(Visualization) 합니다.
- 다른 농장의 데이터와 비교를 할 수 있어, 현재 농장의 상황을 객관적으로 판단 할 수 있도록 합니다.


## IoT Labs의 다른 프로젝트와의 연계
IoT Labs : Farm 프로젝트에는 하드웨어/통신/Database/대시보드(Dashboard) 등의 기술이 사용된다.
이 기술들을 모듈화 & 일반화 하여 각각의 프로젝트로 구성한다.

- **IoT Labs : Dashboard**
  - Database, Web 그리고 Dashboard 등의 기능을 한다
  - IoT Labs : Farm 과의 차이점은, 농장 중심으로 이루어져 있지 않고, 어디에서나 사용 할 수 있도록 범용적으로 모듈화 되어 있다
- **IoT Labs : Communication**
  - 데이터 통신 부분(MQTT)을 담고 있다
- **IoT Labs : Device**
  - 하드웨어 관련된 부분을 담고 있다.
- **IoT Labs : Farm**
  - IoT Labs 의 다른 프로젝트 기술을 이용하여, 농장 데이터 수집/분석을 한다.

## 프로젝트 목표
정확한 농업 데이터를 수집하여 시각화 함으로써 정확한 판단을 할 수 있도록 한다.
농업은 경험주의적 이거나 정밀하지 않은 데이터로 문서화 되어 있다.

다음은 현재 딸기 농사의 적용되고 있는 적정 온도 가이드 이다
- 촉성재배
  - 적정 온도 : 20~25℃
  - 최고 온도 : 30℃를 넘으면 안된다
  - 최저 온도 : 5℃ 이하로 떨어지면 안된다
  - 밤 온도 : 10~12℃
- 출뢰기
  - 낮 온도 : 25~27℃
  - 밤 온도 : 8~10℃
- 과실비대기
  - 낮 온도 : 23~25℃
  - 밤 온도 : 5~6℃

이정도 가이드를 가지고는 현재 농장이 잘 지켜지고 있는지 판단하기가 어렵다.
또한, 24시간 온도를 측정하는 것도 불가능하다.

이러한 24시간 데이터 수집을 가장 잘하는 것이 IoT 센서들이고
수집된 데이터를 시각화 하는 것은 Web 기술에 많이 존재 한다.

IoT Labs - Farm은 이러한 것들을 수행하고
나아가, 우리나라 모든 작물의 최적 환경을 데이터화 하는 것을 목표로 한다.

## 프로젝트 진행

#### 커뮤니케이션
- Github : https://github.com/iot-labs/farm
- Facebook : https://www.facebook.com/groups/IoTLabs
- Slack : iotlabs-team.slack.com


## 참여

## Issue
사용 문의 또는 질문 사항들은 [Github 이슈](https://github.com/jongkwang/IoTLabs/issues)에 올려 주시면 바로 처리 해 드리겠습니다.
많은 의견 부탁드립니다.

## Contribution
IoT Labs를 개선해주세요. Contribution은 언제나 환영합니다.

## Support

* 이 프로젝트는 [공개SW 개발자 Lab](http://devlab.oss.kr/)의 지원을 받고 있습니다.


## License
* [IoT Labs](https://github.com/jongkwang/IoTLabs) 는 [MIT](https://opensource.org/licenses/MIT) 라이센스를 따르며,
* 함께 사용된 SW와 폰트의 라이센스도 준수해야 합니다.
* 함께 사용된 SW와 폰트
	* Font
		* [스케치명조](http://www.asiasoft.co.kr/) 폰트가 사용 되었습니다.
	* Open Source
		* [Paho](http://www.eclipse.org/paho/) - [Eclipse Public License 1.0](http://projects.eclipse.org/content/eclipse-public-license-1.0)
		* [RabbitMQ](https://www.rabbitmq.com/) - [Mozilla Public License](https://www.rabbitmq.com/mpl.html)
