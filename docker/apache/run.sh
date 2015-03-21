#!/bin/bash

# run sshd
/usr/sbin/sshd -D &

# run apache
chown www-data:www-data /var/www/html -R
source /etc/apache2/envvars
exec apache2 -D FOREGROUND