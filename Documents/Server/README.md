# IoT Labs - Farm 프로젝트 서버 셋팅

## Server
- OS : CentOS 7.3

## Linux 기본정보 확인

### Linux 버전
```sh
# 버전확인
cat /etc/*release*
# OS 64bit 확인
getconf LONG_BIT
```

## 사전 작업
### 프롬프트에 절대경로 표시하기

> 개인적으로 Linux 프롬프트에 절대 경로(Full path)가 표시 되는 것을 선호 한다.

```sh
vi /etc/bashrc

PS1="[u@h ]..."
# 윗부분 찾아서 아래와 같이 변경한다.
[ "$PS1" = "s-v$ " ] && PS1="[u@h $PWD]$ "
```
### yum 속도 향상
> yum-fastestmirror 설치하여 ping 이 빠른 서버 자동으로 찾기

```sh
# 설치여부 확인
yum list installed | grep fastestmirror
# 설치 방법
yum install yum-plugin-fastestmirror yum-fastestmirror
```

### OS Update
```sh
yum update # OS 전체 업데이트
reboot # 시스템 재부팅. vultr 메뉴얼에는 최초 한번 reboot 하라고 권고하고 있다
```

## NGINX 설치

### NGINX 설치

> 버전 확인 : https://nginx.org/en/download.html
```sh
yum install nginx
```

### NGINX Start & Status
```sh
# Nginx start
systemctl start nginx.service

# Nginx ststus (상태확인)
systemctl status nginx.service
```

여기까지 진행하고, 잠시 setuptool 설정으로 넘어간다.
**Nginx 추가 설정은 하단에서 이어짐**

## setuptool
setuptool 에서는 아래 작업을 진행
- 서비스 자동시작 (Nginx)
- 방화벽 셋팅

### setuptool 및 방화벽 툴 설치
```sh
# setuptool 을 이용하여 방화벽을 설정할 것이다
# cmd 로 셋팅 할 수 있지만 개인적으로 이것을 선호
yum install setuptool system-config-securitylevel-tui authconfig system-config-network-tui ntsysv
```

### setuptool - Nginx 서비스 등록
서버 부팅 시 자동으로 Nginx 실행 되도록
```sh
# setuptool 실행
setup
# setup 화면에서
- System services 메뉴 선택
- nginx.service 체크
- 저장 후 종료
```

### setuptool - 방화벽 설정
Nginx 서비스를 위해 80 포트 오픈
```sh
# setuptool 실행
setup
# setup 화면에서
- Firewall congifuration 선택
- 만약, Firewall configuration 선택시 에러 발생하면, 하단 참조
- 셋팅 후 종료

# 변경사항 반영
systemctl restart iptables
```
### Error : setuptool 의 Firewall configuration 에서 에러 발생시

- Error Msg : <code>ERROR: FirewallD is active, please use firewall-cmd.</code>

```sh
# firewalld 서비스 Stop
service firewalld stop

# 다시 setup 실행하면 정상작동
```
