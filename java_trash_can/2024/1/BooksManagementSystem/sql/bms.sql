create table book
(
    id     int auto_increment
        primary key,
    name   varchar(50) not null comment '书名',
    author varchar(20) not null comment '作者',
    press  varchar(20) not null comment '出版社',
    number int         not null comment '库存总数量',
    state  int         not null comment '借阅状态(数字为当前被借数量)'
);

create table user
(
    id        int auto_increment
        primary key,
    name      varchar(20)                  not null comment '姓名',
    user_name varchar(20)                  not null comment '用户名',
    password  varchar(50) default '123456' not null comment '密码',
    gender    int         default 1        not null comment '性别(1为男2为女)',
    root      int         default 2        not null comment '权限(1为管理员2为一般用户)',
    constraint gender_check
        check ((`gender` = 1) or (`gender` = 2)),
    constraint root_check
        check ((`root` = 1) or (`root` = 2))
)
    comment '用户表';


