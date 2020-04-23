
create table if not exists user(
	user_id int not null auto_increment,
    username varchar(32) unique not null,
    password varchar(32) not null,
    score int not null,
    type varchar(32) not null,
    banned boolean not null,

    primary key(user_id)
);

create table if not exists question(
	question_id int not null auto_increment,
    user_id int not null,
    title varchar(128) not null,
    text varchar(1024) not null,
    date_time datetime not null,

    primary key(question_id),

    constraint question_user foreign key(user_id) references user(user_id)
    on update cascade
    on delete cascade
);

create table if not exists tag(
	tag_id int not null auto_increment,
    name varchar(32) not null unique,

    primary key(tag_id)
);

create table if not exists question_tag(
	question_tag_id int not null auto_increment,
    question_id int not null,
    tag_id int not null,

    primary key(question_tag_id),

    constraint question_tag_question
    foreign key(question_id) references question(question_id)
    on update cascade
    on delete cascade,
    constraint question_tag_tag
    foreign key(tag_id) references tag(tag_id)
    on update cascade
    on delete cascade
);

create table if not exists answer(
	answer_id int not null auto_increment,
    question_id int not null,
    user_id int not null,
    text varchar(1024) not null,
    date_time datetime not null,

    primary key(answer_id),

    constraint answer_question
    foreign key(question_id) references question(question_id)
    on update cascade
    on delete cascade,
    constraint answer_user
    foreign key(user_id) references user(user_id)
    on update cascade
    on delete cascade
);

create table if not exists question_vote(
	vote_id int not null auto_increment,
    question_id int not null,
    user_id int not null,
    type boolean not null,

    primary key(vote_id),

    constraint question_vote_question
    foreign key(question_id) references question(question_id)
    on update cascade
    on delete cascade,
    constraint question_vote_user
    foreign key(user_id) references user(user_id)
    on update cascade
    on delete cascade
);

create table if not exists answer_vote(
	vote_id int not null auto_increment,
    answer_id int not null,
    user_id int not null,
    type boolean not null,

    primary key(vote_id),

    constraint answer_vote_answer
    foreign key(answer_id) references answer(answer_id)
    on update cascade
    on delete cascade,
    constraint answer_vote_user
    foreign key(user_id) references user(user_id)
    on update cascade
    on delete cascade
);
