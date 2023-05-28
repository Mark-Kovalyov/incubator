%sql
create table analytics.emp (
  empno INTEGER,
  ename STRING,
  job STRING,
  mgr INTEGER,
  hiredate DATE,
  sal DECIMAL,
  comm DECIMAL,
  depno INTEGER

) using delta;

%sql
create table analytics.emp_history (
  empno INTEGER,
  ename STRING,
  job STRING,
  mgr INTEGER,
  hiredate DATE,
  sal DECIMAL,
  comm DECIMAL,
  depno INTEGER,
  start_date TIMESTAMP,
  end_date TIMESTAMP,
  is_active INTEGER
) using delta;

%sql
select empno,ename,job,mgr,hiredate,sal,comm,depno from analytics.emp;
%sql
select empno,ename,job,mgr,hiredate,sal,comm,depno from analytics.emp_history where is_active = 1;
