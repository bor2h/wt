# mysql user
use mysql;

# database 생성
create database trip;

# user 생성
create user 'trip'@'%' identified by 'trip1234';

# user 권한 부여
grant all privileges on trip.* to 'trip'@'%';

# 권한 적용
flush privileges;

# database 조회
show databases;

# 계정 조회
SELECT * FROM mysql.user;