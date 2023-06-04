-- With Cube

SELECT
  count(*) as cnt,
  extract(year from published) as ye
FROM torrent_info
GROUP BY CUBE(ye)
ORDER BY ye;

FROM torrent_info_consolidated


SELECT
  count(*) as cnt,
  extract(year  from published) as ye,
  extract(month from published) as mo
FROM torrent_info
GROUP BY ye,mo
ORDER BY ye,mo


