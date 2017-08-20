
drop table users;
drop table cookies;
drop table articles;
drop table comments;

create table users(
  uid serial,
  user_id varchar(20) unique,
  name varchar(20),
  mail varchar(80) unique,
  password varchar(30),
  primary key(uid)
);

create table cookies(
  user_id int,
  cookie varchar(100) unique,
  primary key(user_id)
);

create table articles(
  uid serial,
  owner_id int,
  article text,
  title varchar(200),
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

insert into users (user_id, name, mail, password)
       values ('test', 'テストちゃん', 'test@test.com', 'testpass');
insert into users (user_id, name, mail, password)
       values ('tanaka', 'なかくん', 'tanaka@gmail.com', 'tanakapass');
