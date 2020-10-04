use ssafydb;

create table user (
id varchar(20) not null,
pw varchar(20) not null,
email varchar(50) not null,
constraint primary key (id)
);


insert into user 
values ("test","test","pby_010@naver.com");

create table userinfo(
id varchar(20) not null,
baekjoon_id varchar(20) not null,
score int not null,
previous_problems varchar(50) not null,
foreign key (id) references user (id)
);

-- alter table userIO add column no int not null auto_increment primary key; 

insert into userinfo
values ("test","test_baekjoon",100,"");


create table notice(
no int not null auto_increment,
writer varchar(20) not null,
title varchar(20) not null,
contents varchar(200) not null,
time datetime not null,
constraint primary key (no)
);

create table board(
no int not null auto_increment,
id varchar(20) not null,
title varchar(20) not null,
contents varchar(200) not null,
time datetime not null,
constraint primary key (no),
foreign key (id) references user (id)
);

create table reply_board(
no int not null auto_increment,
board_no int not null,
id varchar(20) not null,
contents varchar(200) not null,
time datetime not null,
constraint primary key (no),
foreign key (board_no) references board (no),
foreign key (id) references user (id)
);


create table request_friend(
no int not null auto_increment,
rquest_id varchar(20) not null,
target_id varchar(20) not null,
time datetime not null,
constraint primary key (no),
foreign key (rquest_id) references user (id),
foreign key (target_id) references user (id)
);

create table friend(
no int not null auto_increment,
rquest_id varchar(20) not null,
target_id varchar(20) not null,
time datetime not null,
constraint primary key (no),
foreign key (rquest_id) references user (id),
foreign key (target_id) references user (id)
);
