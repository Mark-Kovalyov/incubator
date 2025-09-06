CREATE TABLE entities (
    ent_id NUMBER GENERATED ALWAYS AS IDENTITY,
    name   VARCHAR2(255) NOT NULL
);

CREATE TABLE attributes (
    attr_id NUMBER GENERATED ALWAYS AS IDENTITY,
    name    VARCHAR2(255) NOT NULL
);

CREATE TABLE eav (
    id      NUMBER GENERATED ALWAYS AS IDENTITY,
    ent_id  NUMBER NOT NULL,
    attr_id NUMBER NOT NULL,
    value   VARCHAR2(4000)
);

ALTER TABLE eav ADD CONSTRAINT eav_ent_id_fk FOREIGN KEY (ent_id) REFERENCES entitied(ent_id);
ALTER TABLE eav ADD CONSTRAINT eav_attr_id_fk FOREIGN KEY (attr_id) REFERENCES entitied(attr_id);
