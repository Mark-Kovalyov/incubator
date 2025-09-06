create sequence POSITION_9_SEQ;

create table POSITION_9(
  id number primary key,
  position_code varchar2(9) not null,
  constraint pos_code_9_unq unique(position_code)
);

create sequence POSITION_1000_SEQ;

create table POSITION_1000(
  id number primary key,
  position_code varchar2(3000) not null,
  constraint pos_code_1000_unq unique(position_code)
);



