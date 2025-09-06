# Relation model to EAV

## Keywords : EAV, KV, Magneto, Quintet data model, CIM (Common Information Model), MQL, Neo4j, SPARQL

## Usefull links Quintet

Квинтет как базовая сущность для описания предметной области
https://habr.com/en/company/neoflex/blog/433058/

Запатентованная мечта программистов 80-90-х
https://habr.com/en/post/358934/

Альтернативная архитектура СУБД и подход к разработке приложений
https://habr.com/en/post/346816/

Запатентованная мечта программиста — часть II
https://habr.com/en/post/414255/

Business Intelligence по-русски — на квинтетах
https://habr.com/en/company/neoflex/blog/451218/

## Links Magneto

Magento 2 EAV: обзор структур данных
https://habr.com/en/post/441122/

## Postgresql

 $ sudo -i -u postgres
 $ psql
 postgres=# create user mayton with encrypted password '********';
 postgres=# create database maytondb with owner mayton;
 CREATE DATABASE
 $ exit
 $ psql -h localhost -p 5432 -d maytondb -U mayton 
  