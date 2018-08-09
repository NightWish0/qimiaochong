/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2018/8/8 10:47:36                            */
/*==============================================================*/


drop table if exists report_type;

drop table if exists topic;

drop table if exists topic_comment;

drop table if exists topic_comment_report;

drop table if exists topic_label;

drop table if exists topic_like_counts;

drop table if exists topic_report;

drop table if exists user;

drop table if exists user_collections;

/*==============================================================*/
/* Table: report_type                                           */
/*==============================================================*/
create table report_type
(
   id                   int not null auto_increment,
   type                 varchar(255),
   created_at           datetime,
   primary key (id)
);

/*==============================================================*/
/* Table: topic                                                 */
/*==============================================================*/
create table topic
(
   id                   int not null auto_increment,
   user_id              int,
   title                varchar(255),
   content              text,
   like_count           int,
   label_id             int,
   created_at           datetime,
   ban_at               datetime,
   deleted_at           datetime,
   status               int comment '-1--文章已删除；
            0--文章违禁等不可看；
            1--文章正常',
   primary key (id)
);

alter table topic comment '分享的文章';

/*==============================================================*/
/* Table: topic_comment                                         */
/*==============================================================*/
create table topic_comment
(
   id                   int not null auto_increment,
   parent_id            int,
   user_id              int,
   topic_id             int,
   content              varchar(1000),
   like_count           int,
   created_at           datetime,
   deleted_at           datetime,
   primary key (id)
);

alter table topic_comment comment '文章评论';

/*==============================================================*/
/* Table: topic_comment_report                                  */
/*==============================================================*/
create table topic_comment_report
(
   id                   int not null auto_increment,
   user_id              int,
   topic_comment_id     int,
   report_type_id       int,
   content              varchar(500),
   created_at           datetime,
   status               boolean,
   result               boolean,
   primary key (id)
);

/*==============================================================*/
/* Table: topic_label                                           */
/*==============================================================*/
create table topic_label
(
   id                   int not null auto_increment,
   label                varchar(100) binary,
   created_at           datetime,
   deleted_at           datetime,
   primary key (id)
);

/*==============================================================*/
/* Table: topic_like_counts                                     */
/*==============================================================*/
create table topic_like_counts
(
   id                   int not null auto_increment,
   user_id              int,
   topic_id             int,
   created_at           datetime,
   primary key (id)
);

/*==============================================================*/
/* Table: topic_report                                          */
/*==============================================================*/
create table topic_report
(
   id                   int not null auto_increment,
   user_id              int,
   topic_id             int,
   report_type_id       int,
   content              varchar(500),
   created_at           datetime,
   status               boolean comment '0--未处理；
            1--已处理；',
   result               boolean,
   primary key (id)
);

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   id                   int not null auto_increment,
   name                 varchar(255),
   password             varchar(255),
   avatar               varchar(255),
   gendel               varchar(10),
   birthday             date,
   address              varchar(255),
   profile              varchar(255),
   created_at           datetime,
   deleted_at           datetime,
   status               int comment '-1--用户删除；
            0--用户冻结；
            1--用户活跃',
   primary key (id)
);

alter table user comment '用户表';

/*==============================================================*/
/* Table: user_collections                                      */
/*==============================================================*/
create table user_collections
(
   id                   int not null auto_increment,
   user_id              int,
   topic_id             int,
   created_at           datetime,
   deleted_at           datetime,
   primary key (id)
);

alter table topic add constraint FK_Reference_6 foreign key (label_id)
      references topic_label (id) on delete restrict on update restrict;

alter table topic add constraint FK_author_id foreign key (user_id)
      references user (id) on delete restrict on update restrict;

alter table topic_comment add constraint FK_Reference_2 foreign key (topic_id)
      references topic (id) on delete restrict on update restrict;

alter table topic_comment add constraint FK_Reference_3 foreign key (user_id)
      references user (id) on delete restrict on update restrict;

alter table topic_comment_report add constraint FK_Reference_11 foreign key (report_type_id)
      references report_type (id) on delete restrict on update restrict;

alter table topic_comment_report add constraint FK_Reference_7 foreign key (topic_comment_id)
      references topic_comment (id) on delete restrict on update restrict;

alter table topic_comment_report add constraint FK_Reference_8 foreign key (user_id)
      references user (id) on delete restrict on update restrict;

alter table topic_like_counts add constraint FK_Reference_4 foreign key (user_id)
      references user (id) on delete restrict on update restrict;

alter table topic_like_counts add constraint FK_Reference_5 foreign key (topic_id)
      references topic (id) on delete restrict on update restrict;

alter table topic_report add constraint FK_Reference_10 foreign key (user_id)
      references user (id) on delete restrict on update restrict;

alter table topic_report add constraint FK_Reference_12 foreign key (report_type_id)
      references report_type (id) on delete restrict on update restrict;

alter table topic_report add constraint FK_Reference_9 foreign key (topic_id)
      references topic (id) on delete restrict on update restrict;

alter table user_collections add constraint FK_Reference_13 foreign key (user_id)
      references user (id) on delete restrict on update restrict;

alter table user_collections add constraint FK_Reference_14 foreign key (topic_id)
      references topic (id) on delete restrict on update restrict;

