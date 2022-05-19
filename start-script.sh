#!/bin/bash
service mariadb start
mysql < /data/create_db.sql
cd java/src
java juegos/server/JuegosServer


