create sequence snap_sequence;

create table torrent_info_consolidated(
    snap_id int,
    year int not null,
    cnt int
) tablespace dht_space;

