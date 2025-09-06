CREATE TABLE quintet(
    id       NUMBER GENERATED ALWAYS AS IDENTITY,
    datatype NUMBER NOT NULL,
    parent   NUMBER NOT NULL,
    order    NUMBER NOT NULL,
    value    VARCHAR2(4000)
);

CREATE INDEX datatype_val_idx ON quintet(datatype, value);

CREATE INDEX parent_datatype_idx ON quintet(parent, datatype);

INSERT INTO quintet VALUES (1,1,1,0,'ROOT');
INSERT INTO quintet VALUES (3,3,0,0,'SHORT');
INSERT INTO quintet VALUES (4,4,0,0,'DATETIME');
INSERT INTO quintet VALUES (8,8,0,0,'CHARS');
INSERT INTO quintet VALUES (9,9,0,0,'DATE');
INSERT INTO quintet VALUES (13,13,0,0,'NUMBER');
INSERT INTO quintet VALUES (14,14,0,0,'SIGNED');

INSERT INTO quintet VALUES (348,3,0,0,'Гость');
INSERT INTO quintet VALUES (349,3,0,0,'Номер');
INSERT INTO quintet VALUES (350,13,0,0,'Бронирование');
INSERT INTO quintet VALUES (351,348,0,0,null);
INSERT INTO quintet VALUES (352,349,0,0,null);
INSERT INTO quintet VALUES (353,351,350,1,null);
INSERT INTO quintet VALUES (355,352,350,2,null);
INSERT INTO quintet VALUES (356,4,0,0,'Дата бронирования');
INSERT INTO quintet VALUES (357,9,0,0,'Заезд');
INSERT INTO quintet VALUES (358,9,0,0,'Выезд');
INSERT INTO quintet VALUES (359,356,350,3,null);
INSERT INTO quintet VALUES (360,357,350,4,null);
INSERT INTO quintet VALUES (361,358,350,5,null);
COMMIT;

-- Hash partition by datatype

CREATE TABLE quintet_hash(
    id       NUMBER GENERATED ALWAYS AS IDENTITY,
    datatype NUMBER NOT NULL,
    parent   NUMBER NOT NULL,
    order    NUMBER NOT NULL,
    value    VARCHAR2(4000)
)
PARTITION BY HASH (datatype)
PARTITIONS 16;

-- Clustered table

CREATE CLUSTER quintet_cluster(datatype_id NUMBER) SIZE 512;

CREATE INDEX qunitet_cluster_index ON CLUSTER quintet_cluster;

CREATE TABLE quintet_cluster (
    id       NUMBER GENERATED ALWAYS AS IDENTITY,
    datatype NUMBER NOT NULL,
    parent   NUMBER NOT NULL,
    order    NUMBER NOT NULL,
    value    VARCHAR2(4000)
)
CLUSTER quintet_cluster(datatype);

--CREATE CLUSTER employees_departments_cluster(department_id NUMBER(4)) SIZE 8192 HASHKEYS 100;