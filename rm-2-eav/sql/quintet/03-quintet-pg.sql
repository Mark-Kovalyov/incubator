CREATE SEQUENCE quintet_seq;

CREATE SEQUENCE quintet_datatype_seq;

CREATE TABLE quintet(
    id       INT    PRIMARY KEY,
    datatype INT    NOT NULL,
    parent   BIGINT NOT NULL,
    ord      INT    NOT NULL,
    value    TEXT
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

CREATE SEQUENCE quintet_seq;

CREATE TABLE quintet_hash(
    id       BIGINT NOT NULL,
    datatype INT    NOT NULL,
    parent   BIGINT NOT NULL,
    ord      INT    NOT NULL,
    value    TEXT
) PARTITION BY HASH (datatype);
-- psql (10.10 (Ubuntu 10.10-0ubuntu0.18.04.1))
-- ERROR:  unrecognized partitioning strategy "hash"

CREATE TABLE quintet_hash_part01 PARTITION OF quintet_hash FOR VALUES WITH (MODULUS 4, REMAINDER 0);
CREATE TABLE quintet_hash_part02 PARTITION OF quintet_hash FOR VALUES WITH (MODULUS 4, REMAINDER 1);
CREATE TABLE quintet_hash_part03 PARTITION OF quintet_hash FOR VALUES WITH (MODULUS 4, REMAINDER 2);
CREATE TABLE quintet_hash_part04 PARTITION OF quintet_hash FOR VALUES WITH (MODULUS 4, REMAINDER 3);

-- List
CREATE TABLE quintet_list(
    id       BIGINT NOT NULL,
    datatype INT    CHECK ((datatype) in (351, 352)),
    parent   BIGINT NOT NULL,
    ord      INT    NOT NULL,
    value    TEXT
)
PARTITION BY LIST (datatype);

CREATE TABLE quintet_list_part01 PARTITION OF quintet_list FOR VALUES IN (351);
CREATE TABLE quintet_list_part02 PARTITION OF quintet_list FOR VALUES IN (352);


