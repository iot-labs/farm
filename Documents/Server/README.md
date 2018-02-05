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

> 개인적으로 Linux 프롬프트에 절대 경로(Full path)가 표시 되는 것을 선호한다.

```sh
vi /etc/bashrc

PS1="[u@h ]..."
# 윗부분 찾아서 아래와 같이 변경한다.
[ "$PS1" = "s-v$ " ] && PS1="[u@h $PWD]$ "
```
### yum 속도 향상
> yum-fastestmirror 설치하여 ping이 빠른 서버 자동으로 찾기

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

또는 Nginx 서비스 등록은 이렇게 해도 된다.
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
# restart 하지 않아도 반영된다. (확인함)
service nginx reload
```

### HTTP 테스트

테스트용 index.html 생성
```sh
vi /var/www/www.iotlabs.net/index.html
Hello World
```

브라우저에서 접속하여 "Hello World" 문구 확인
- http://www.iotalbs.net

## Let's Encrypt - HTTPS 적용

### Let's Encrypt 설치 및 Nginx 설정

```sh
# certbot 설치
yum install certbot

# 설정 추가
vi /etc/nginx/conf.d/www.iotalbs.net.conf
# 추가할 내용
location ~ /.well-known {
	allow all;
}

# 추가된 모습
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
    location ~ /.well-known {
    	allow all;
    }
    #error_page  404              /404.html;

    # redirect server error pages to the static page /50x.html
    #
    error_page   500 502 503 504  /50x.html;
    location = /50x.html {
        root   /usr/share/nginx/html;
    }
}

# Nginx Restart (reload 는 테스트 안해봤음)
service nginx restart
```

### 인증서 획득
```sh
# 도메인을 이와같이 여러개 입력한다. Let'sEncrypt 에서는 "*" 도메인을 지원하지 않는다.
certbot certonly -a webroot --webroot-path=/var/www/www.iotlabs.net -d www.iotlabs.net -d iotlabs.net -d farm.iotlabs.net -d dashboard.iotlabs.net

# 아래와 같은 성공 메세지 확인. 특히, fullchain.pem 과 privkey.pem 경로는 적어둔다
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

   Donating to ISRG / Lets Encrypt:   https://letsencrypt.org/donate
   Donating to EFF:                    https://eff.org/donate-le
```

### dhparam.pem 생성
Diffie–Hellman 파라미터라 부르며(줄여서 dhparam) SSL 통신시 암호화를 도와주는 것으로 암호의 복잡도를 높여준다.
SSL 등급을 측정하는 사이트에서 A등급 이상을 받으려면 필수로 해야 한다.

```sh
openssl dhparam -out /etc/ssl/certs/dhparam.pem 2048
# 4096 으로 해도 된다 (미확인)
# PC 사양에 따라 5분에서 10분까지 소요 될 수 있다
```

### Nginx 에 SSL 설정 적용

```sh
vi /etc/nginx/conf.d/www.iotlabs.net.conf

# 아래와 같이 변경
server {
    server_name iotlabs.net www.iotlabs.net farm.iotlabs.net dashboard.iotlabs.net;
    return 301 https://$host$request_uri;
}

server {
    listen       443 http2 ssl;
    server_name  iotlabs.net www.iotlabs.net farm.iotlabs.net dashboard.iotlabs.net;
    client_max_body_size 2000M;
    fastcgi_read_timeout 600s;

    charset utf-8;
    access_log  /var/log/nginx/access.www.iotlabs.net.log  main;
    error_log   /var/log/nginx/error.www.iotlabs.net.log  error;

    # SSL
    ssl_certificate /etc/letsencrypt/live/www.iotlabs.net/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/www.iotlabs.net/privkey.pem;

    # From https://cipherli.st/
    ssl_protocols TLSv1 TLSv1.1 TLSv1.2;
    ssl_prefer_server_ciphers on;
    ssl_ciphers "EECDH+AESGCM:EDH+AESGCM:AES256+EECDH:AES256+EDH";
    ssl_ecdh_curve secp384r1;
    ssl_session_cache shared:SSL:10m;
    ssl_session_tickets off;
    ssl_stapling on;
    ssl_stapling_verify on;
    resolver 8.8.8.8 8.8.4.4 valid=300s;
    resolver_timeout 5s;
    add_header Strict-Transport-Security "max-age=63072000; includeSubdomains";
    add_header X-Frame-Options DENY;
    add_header X-Content-Type-Options nosniff;

    ssl_dhparam /etc/ssl/certs/dhparam.pem;

    location ~ /.well-known {
            allow all;
    }
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

### Nginx 재부팅
```sh
# Nginx 설정 테스트
nginx -t

# Nginx 재시작
systemctl restart nginx
```

### https 확인
브라우저에서 https 로 접속 해본다.
- URL : https://www.iotlabs.net

### SSL 테스트 사이트
위와 같이 설정하면 "A+" 등급을 받을 수 있다.
- https://www.ssllabs.com/ssltest/

## Lets Encrypt 인증서 갱신

인증서가 90일 만료로 매우 짧다.
반드시 자동 갱신이 되어있어야 유용한다.

```sh
vi /etc/crontab

# 하단 내용 추가
20 4 * * 0 /usr/bin/certbot renew >> /var/log/certs-renew.log
23 4 * * 0 /usr/bin/systemctl reload nginx
```
