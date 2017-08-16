drop table users;
drop table cookies;
drop table articles;
drop table comments;

create table users(
  uid serial,
  name varchar(20),
  mail varchar(80) unique,
  password varchar(30),
  primary key(uid)
);

create table cookies(
  uid serial,
  cookie varchar(100) unique,
  primary key(uid)
);

create table articles(
  uid serial,
  owner_id int,
  article text,
  date timestamp,
  primary key(uid)
);

create table comments(
  article_id int,
  id int,
  title varchar(30),
  name varchar(30),
  content text,
  date timestamp
);

insert into users (name, mail, password)
       values ('テストちゃん', 'test@test.com', 'testpass');
