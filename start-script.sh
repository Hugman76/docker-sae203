#!/bin/bash
service mariadb start
mysql < /data/create_db.sql
java java/src/juegos/server/JuegosServer


