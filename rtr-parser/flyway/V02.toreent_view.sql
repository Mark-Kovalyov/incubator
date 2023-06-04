drop view torrent_view;

create or replace view torrent_view as select
    'http://d.rutor.info/download/' || id as rutor_desc,
    'magnet:?xt=urn:btih:' || btih as magnet_link,
    name,
    published
from torrent_info;