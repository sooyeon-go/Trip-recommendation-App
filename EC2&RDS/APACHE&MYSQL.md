## EC2에서 APACHE,PHP 연동

1. MySQL 환경설정  

>cd /etc/mysql/mysql.conf.d;  
>sudo vi mysqld.cnf;

--> bind-address = 0.0.0.0

>sudo service mysql restart

2. APACHE 설치 및 설정  

>sudo apt-get install apache2

설치 후  
>sudo vi /etc/apache2/apache2.conf  
>sudo vi /etc/apache2/sites-available/000-default.conf

-->루트 디렉토리를 /home/ubuntu/phps로 변경  
![1](/apache2conf.png)  
![2](/000-default.png)

3. PHP 설치 및 설정  

>sudo apt install php php-mysql  
>sudo vi /home/ubuntu/phps/index.php


4. 위 설정을 마친 후 EC2 IP 주소를 입력하면

![3](/complete.png)