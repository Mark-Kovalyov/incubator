drop table dht_messages;

create table dht_messages(
    ts text,
    ip text,
    source_port int,
    tag text,
    packet_size int,
    packet_body text,
    ptr text,
    decoded_event text
);

drop table top_ips;

create table top_ips(
    ip text,
    ip_count int
);


