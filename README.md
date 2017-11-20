﻿# IoT Labs - Farm : 농장 데이터 수집 및 분석 서비스

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

다음은 현재 딸기 농사의 적용되고 있는 적정 온도 가이드이다
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

## 커뮤니케이션
- Github : https://github.com/iot-labs/farm
- Facebook : https://www.facebook.com/groups/IoTLabs
- Slack : iotlabs-team.slack.com

# 프로젝트 개발
전체 Task를 이곳에 정리 합니다.
* Device 파트와 Web 파트로 나뉘어 집니다
* 이곳에서 전체적인 Task를 볼 수 있고,
* 상세한 내용은 해당하는 Issue 에서 확인 하시기 바랍니다
  * Task 옆에 Issue 링크가 기록되어 있습니다

## Device 파트

### 사전 조사
- Device 선정 (Arduino or Raspberry Pi) - Arduino 로 결정됨 [![Issue Status](https://img.shields.io/badge/issue_9-closed-lightgrey.svg)](https://github.com/iot-labs/farm/issues/9)
- [아두이노 시뮬레이터](https://circuits.io/lab) 제작
  - 필요한 Device 를 아두이노 시뮬레이터를 이용해 구성해본다
    - Circuits를 이용하여 온도/습도 측정 개발 - [![Issue Status](https://img.shields.io/badge/issue_26-processing-brightgreen.svg)](https://github.com/iot-labs/farm/issues/26)
    - Circuits를 이용하여 조도(빛) 측정 개발 - [![Issue Status](https://img.shields.io/badge/issue_27-processing-brightgreen.svg)](https://github.com/iot-labs/farm/issues/27)
  - 장비 구매에 참고 한다
  - URL : https://www.tinkercad.com/circuits
  - 구성한 시뮬레이터 정보도 Github 에 공개하여 공유한다 [![Issue Status](https://img.shields.io/badge/issue_26-processing-brightgreen.svg)](https://github.com/iot-labs/farm/issues/26) [![Issue Status](https://img.shields.io/badge/issue_27-processing-brightgreen.svg)](https://github.com/iot-labs/farm/issues/27)
- 1개 Arduino 보드에 모든 센서를 붙일 수 있는가? 없다면 몇개 아두이노가 필요한가?

### 장비 구매

> 구매할 장비 URL 을 해당 Issue 에 남겨주세요
> 일괄 구매 하도록 하겠습니다

- Main Device : Arduino 어떤 모델? [![Issue Status](https://img.shields.io/badge/issue_10-closed-lightgrey.svg)](https://github.com/iot-labs/farm/issues/10)
- Arduino SDCard Read/Write [![Issue Status](https://img.shields.io/badge/issue_11-closed-lightgrey.svg)](https://github.com/iot-labs/farm/issues/11)
  1. 참고 : http://deneb21.tistory.com/266
- 온/습도 센서 [![Issue Status](https://img.shields.io/badge/issue_12-closed-lightgrey.svg)](https://github.com/iot-labs/farm/issues/12)
- 광 센서 [![Issue Status](https://img.shields.io/badge/issue_13-closed-lightgrey.svg)](https://github.com/iot-labs/farm/issues/13)
- 토양 수분 센서 [![Issue Status](https://img.shields.io/badge/issue_14-closed-lightgrey.svg)](https://github.com/iot-labs/farm/issues/14)
  1. 참고 : http://codingrun.com/109
  2. 참고 : https://www.kocoafab.cc/tutorial/view/369
- 기타 장비 (빵판, 전선, 기타) [![Issue Status](https://img.shields.io/badge/issue_15-closed-lightgrey.svg)](https://github.com/iot-labs/farm/issues/15)

### 개발
- Arduino IDE 에 ESP8266 추가 하기 + 문서작업 [![Issue Status](https://img.shields.io/badge/issue_36-closed-lightgrey.svg)](https://github.com/iot-labs/farm/issues/36) <code>조대영</code>
- Arduino SDCard Read/Write 개발 [![Issue Status](https://img.shields.io/badge/issue_37-closed-lightgrey.svg)](https://github.com/iot-labs/farm/issues/37) <code>윤재호</code>
- Arduino Wi-Fi 통신 [![Issue Status](https://img.shields.io/badge/issue_41-closed-lightgrey.svg)](https://github.com/iot-labs/farm/issues/41) <code>Brad.Choi</code>
- LCD Display 출력 개발 [![Issue Status](https://img.shields.io/badge/issue_28-closed-lightgrey.svg)](https://github.com/iot-labs/farm/issues/28) <code>정동훈</code>
- 온/습도 센서 개발 [![Issue Status](https://img.shields.io/badge/issue_29-closed-lightgrey.svg)](https://github.com/iot-labs/farm/issues/29) <code>전태경</code>
- 광 센서 개발 [![Issue Status](https://img.shields.io/badge/issue_38-closed-lightgrey.svg)](https://github.com/iot-labs/farm/issues/38) <code>선해정</code>
- 토양 수분 센서 개발 [![Issue Status](https://img.shields.io/badge/issue_39-closed-lightgrey.svg)](https://github.com/iot-labs/farm/issues/39)
- 센서 통합 버전 개발 [![Issue Status](https://img.shields.io/badge/issue_40-open-brightgreen.svg)](https://github.com/iot-labs/farm/issues/40)

### 문서화
- Device 개발 문서화 [![Issue Status](https://img.shields.io/badge/issue_45-closed-lightgrey.svg)](https://github.com/iot-labs/farm/issues/45) <code>한홍근</code>
- 회로도 작성
- 설명서를 Github 에 메뉴얼 식으로 작성

## Web 파트

### 기술 선정
- Chart 는 어떤 것으로?

### 서버 세팅
- Web Server 셋팅
  - Tomcat + Nginx 설치
  - Jenkins 설치
- DB Server 셋팅
  - MySQL 설치 + 셋팅

### 화면 설계 + Mockup 개발
- Bootstrap 으로 Mockup 개발

### 화면 개발
- 기본 Frame 개발
- 챠트 개발

## DB 파트

### Import
- SD Card 의 데이터를 MySQL 으로 Import 할 수 있는 기능 개발


# 프로젝트 참여/기여

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
