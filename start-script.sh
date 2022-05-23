#!/bin/bash
service mariadb start
mysql < /data/bases/create_db.sql
cd java/src
java juegos/server/JuegosServer


