create table emp (
  empno INTEGER,
  ename TEXT,
  job TEXT,
  mgr INTEGER,
  hiredate DATE,
  sal DECIMAL,
  comm DECIMAL,
  depno INTEGER,
)

create table emp_history (
  empno INTEGER,
  ename TEXT,
  job TEXT,
  mgr INTEGER,
  hiredate DATE,
  sal DECIMAL,
  comm DECIMAL,
  depno INTEGER,
  start_date TIMESTAMP,
  end_date TIMESTAMP,
  is_active INTEGER
)


CREATE PROCEDURE update_history(a integer, b integer)
LANGUAGE SQL
AS $$
  SELECT * FROM history WHERE 
  INSERT INTO tbl VALUES (b);
$$;

