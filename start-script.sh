#!/bin/bash
service mariadb start
echo "<?php phpinfo(); ?>" > /var/www/html/info.php
mysql < /data/create_db.sql
/usr/sbin/apache2ctl -DFOREGROUND