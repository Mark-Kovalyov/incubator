drop table torrent_info cascade;

create table torrent_info(
    id integer,
    btih text not null,
    name text,
    published date,
    last_update timestamp,
    page integer
) tablespace dht_space;

ALTER TABLE torrent_info ADD CONSTRAINT torrent_info_id PRIMARY KEY(id);

CREATE INDEX torrent_info_btih ON torrent_info(btih) TABLESPACE dht_space;
CREATE INDEX torrent_info_name ON torrent_info USING GIN (name gin_trgm_ops);