#!/bin/bash -v
curl -X PUT http://192.168.1.2:5555/scott
curl -H 'Content-Type: application/json' -X PUT http://192.168.1.2:5555/scott/7369 -d '{ "empno" : 7369, "ename" : "SMITH", "job" : "CLERK", "mgr" : "7902", "sal" : 800 }'
curl -H 'Content-Type: application/json' -X PUT http://192.168.1.2:5555/scott/7499 -d '{ "empno" : 7499, "ename" : "ALLEN", "job" : "SALESMAN", "mgr" : "7698", "sal" : 1600 }'
curl -H 'Content-Type: application/json' -X PUT http://192.168.1.2:5555/scott/7521 -d '{ "empno" : 7521, "ename" : "WARD", "job" : "SALESMAN", "mgr" : "7698", "sal" : 1250 }'
curl -H 'Content-Type: application/json' -X PUT http://192.168.1.2:5555/scott/7566 -d '{ "empno" : 7566, "ename" : "JONES", "job" : "MANAGER", "mgr" : "7839", "sal" : 2975 }'
curl -H 'Content-Type: application/json' -X PUT http://192.168.1.2:5555/scott/7654 -d '{ "empno" : 7654, "ename" : "MARTIN", "job" : "SALESMAN", "mgr" : "7698", "sal" : 1250 }'
curl -H 'Content-Type: application/json' -X PUT http://192.168.1.2:5555/scott/7698 -d '{ "empno" : 7698, "ename" : "BLAKE", "job" : "MANAGER", "mgr" : "7839", "sal" : 2850 }'
curl -H 'Content-Type: application/json' -X PUT http://192.168.1.2:5555/scott/7782 -d '{ "empno" : 7782, "ename" : "CLARK", "job" : "MANAGER", "mgr" : "7839", "sal" : 2450 }'
curl -H 'Content-Type: application/json' -X PUT http://192.168.1.2:5555/scott/7788 -d '{ "empno" : 7788, "ename" : "SCOTT", "job" : "ANALYST", "mgr" : "7566", "sal" : 3000 }'
curl -H 'Content-Type: application/json' -X PUT http://192.168.1.2:5555/scott/7839 -d '{ "empno" : 7839, "ename" : "KING", "job" : "PRESIDENT", "mgr" : "", "sal" : 5000 }'
curl -H 'Content-Type: application/json' -X PUT http://192.168.1.2:5555/scott/7844 -d '{ "empno" : 7844, "ename" : "TURNER", "job" : "SALESMAN", "mgr" : "7698", "sal" : 1500 }'
curl -H 'Content-Type: application/json' -X PUT http://192.168.1.2:5555/scott/7876 -d '{ "empno" : 7876, "ename" : "ADAMS", "job" : "CLERK", "mgr" : "7788", "sal" : 1100 }'
curl -H 'Content-Type: application/json' -X PUT http://192.168.1.2:5555/scott/7900 -d '{ "empno" : 7900, "ename" : "JAMES", "job" : "CLERK", "mgr" : "7698", "sal" : 950 }'
curl -H 'Content-Type: application/json' -X PUT http://192.168.1.2:5555/scott/7902 -d '{ "empno" : 7902, "ename" : "FORD", "job" : "ANALYST", "mgr" : "7566", "sal" : 3000 }'
curl -H 'Content-Type: application/json' -X PUT http://192.168.1.2:5555/scott/7934 -d '{ "empno" : 7934, "ename" : "MILLER", "job" : "CLERK", "mgr" : "7782", "sal" : 1300 }'