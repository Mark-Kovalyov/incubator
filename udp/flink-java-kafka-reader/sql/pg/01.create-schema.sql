drop table dht_messages;

create table dht_messages2(
    ts          timestamp,
    ip          text,
    source_port int,
    tag         text,
    packet_size int,
    packet_body text,
    ptr         text,
    decoded_event jsonb
);