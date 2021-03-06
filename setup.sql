
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

insert into articles (owner_id, title, article)
       values (1, '打ち上げ花火 映画で見るか 小説読むか',
              '小説で読みました。<br>ちょっと設定が微妙というかちゃんと考えられてるか怪しいと思いました。');
