SELECT
  extract(year from published) as ye,
  count(*) as cnt
FROM torrent_info
GROUP BY CUBE(ye)
ORDER BY ye;

