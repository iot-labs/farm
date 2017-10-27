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

여기까지 진행하면, 방화벽을 셋팅하고 Ngiux 테스트를 해야 한다.
