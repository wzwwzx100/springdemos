create database  IF not exists test;

use test;

drop table if exists student;
create table student (
name varchar(100),
age int
);

drop table if exists city;
create table city (
id int(11) NOT NULL AUTO_INCREMENT,
code varchar(20),
name varchar(100),
primary key (id)
);
insert into city(code, name) values('1','北京'),('2','上海'),('3','武汉'),('4','杭州');
