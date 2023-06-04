drop table torrent_info cascade;

create table torrent_info(
    id integer,
    btih text not null,
    name text,
    published date,
    last_update timestamp,
    page integer
) tablespace dht_space;



--partition by range (published)
--CREATE TABLE torrent_info_2010 PARTITION OF torrent_info FOR VALUES FROM ('2000-01-01') TO ('2010-01-01') TABLESPACE dht_space;
--CREATE TABLE torrent_info_2020 PARTITION OF torrent_info FOR VALUES FROM ('2010-01-01') TO ('2020-01-01') TABLESPACE dht_space;
--CREATE TABLE torrent_info_2030 PARTITION OF torrent_info FOR VALUES FROM ('2020-01-01') TO ('2030-01-01') TABLESPACE dht_space;

ALTER TABLE torrent_info ADD CONSTRAINT torrent_info_id PRIMARY KEY(id);

CREATE INDEX torrent_info_btih ON torrent_info(btih) TABLESPACE dht_space;
CREATE INDEX torrent_info_name ON torrent_info USING GIN (name gin_trgm_ops);