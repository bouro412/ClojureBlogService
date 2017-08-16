create table users(
  uid int unique,
  name varchar(20),
  mail varchar(80) unique,
  password varchar(30),
  primary key(uid)
);

create table cookies(
  uid int unique,
  cookie varchar(100) unique,
  primary key(uid)
);

create table article(
  uid int unique,
  owner_id int,
  article text,
  date timestamp,
  primary key(uid)
);

create table comment(
  article_id int,
  id int,
  title varchar(30),
  name varchar(30),
  content text,
  date timestamp
);

insert into users (name, mail, password)
       values ('テストちゃん', 'test@test.com', 'testpass');
