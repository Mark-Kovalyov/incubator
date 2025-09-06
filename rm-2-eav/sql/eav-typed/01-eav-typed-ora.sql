-- .........

CREATE TABLE eav_varchar (
    id      NUMBER,
    ent_id  NUMBER NOT NULL,
    attr_id NUMBER NOT NULL,
    value   VARCHAR2(4000)
);

CREATE TABLE eav_number (
    id      NUMBER,
    ent_id  NUMBER NOT NULL,
    attr_id NUMBER NOT NULL,
    value   NUMBER
);

CREATE TABLE eav_date (
    id      NUMBER,
    ent_id  NUMBER NOT NULL,
    attr_id NUMBER NOT NULL,
    value   DATE
);

CREATE TABLE metadata (
    metadata_id NUMBER GENERATED ALWAYS AS IDENTITY,
    attr_id     NUMBER,
    datatype    VARCHAR2(30),
    id_req      CHAR(1), -- {Y|N}
    format      VARCHAR2(30),
    is_searchable CHAR(1) -- {Y|N}
);
