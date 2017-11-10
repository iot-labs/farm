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

## setuptool & 방화벽
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

또는 Nginx 서비스 등록은 이렇게 해도 된다
```sh
systemctl enable nginx # start nginx on boot
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
# 추가로 부팅시에도 firewalld 작동을 막아야 재부팅후에도 정상 작동.
systemctl disable nginx # 테스트해보지 않음

# 다시 setup 실행하면 정상작동
setup
```

### 방화벽 열린 것 확인
```sh
# CentOS 7.x 의 경우.(아래 2개중 1개 사용)
iptables -L -n
iptables -L -vn

# CentOS 6.x 의 경우, 아래 3가기 중 1가지 사용
netstat -tnlp
lsof -i -nP | grep LISTEN | awk '{print $(NF-1)" "$1}' | sort -u
nmap localhost
```

### 웹서비스 작동되는 것 확인

- 브라우저에서 해당 서버 접속
 - <code>Welcome to nginx</code> 표시되면 성공

## Nginx 설정

### Nginx에 프로젝트 셋팅

- 웹 Root 위치 : /var/www/www.iotlabs.net

```sh
# Nginx 설정 파일 생성
vi /etc/nginx/conf.d/www.iotlabs.net.conf

# www.iotlabs.net.conf 파일 내용
server {
    listen       80;
    server_name  iotlabs.net www.iotlabs.net farm.iotlabs.net dashboard.iotlabs.net;
    client_max_body_size 2000M;
    fastcgi_read_timeout 600s;

    charset utf-8;
    access_log  /var/log/nginx/access.www.iotlabs.net.log  main;
    error_log   /var/log/nginx/error.www.iotlabs.net.log  error;

    location / {
        root   /var/www/www.iotlabs.net;
        index  index.html index.jsp;
    }

    #error_page  404              /404.html;

    # redirect server error pages to the static page /50x.html
    #
    error_page   500 502 503 504  /50x.html;
    location = /50x.html {
        root   /usr/share/nginx/html;
    }
}
```

### Nginx reload

```sh
service nginx reload
# restart 하지 않아도 반영된다. (확인함)
```

yum install certbot
/etc/nginx/default.d/well-known.conf
location ~ /.well-known {
	allow all;
}
service nginx restart
certbot certonly -a webroot --webroot-path=/var/www/www.iotlabs.net -d www.iotlabs.net -d iotlabs.net -d farm.iotlabs.net -d dashboard.iotlabs.net -d jenkins.iotlabs.net -d test.iotlabs.net

```sh
IMPORTANT NOTES:
 - Congratulations! Your certificate and chain have been saved at:
   /etc/letsencrypt/live/www.iotlabs.net/fullchain.pem
   Your key file has been saved at:
   /etc/letsencrypt/live/www.iotlabs.net/privkey.pem
   Your cert will expire on 2018-02-08. To obtain a new or tweaked
   version of this certificate in the future, simply run certbot
   again. To non-interactively renew *all* of your certificates, run
   "certbot renew"
 - If you like Certbot, please consider supporting our work by:

   Donating to ISRG / Let's Encrypt:   https://letsencrypt.org/donate
   Donating to EFF:                    https://eff.org/donate-le
```
openssl dhparam -out /etc/ssl/certs/dhparam.pem 2048
