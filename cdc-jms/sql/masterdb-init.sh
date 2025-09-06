#!/bin/bash

psql -h localhost -p 15432 -d postgres -U postgres -f eav_log.sql
