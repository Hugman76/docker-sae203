#!/bin/bash
service mariadb start
mysql < /data/create_db.sql
java Sortie


