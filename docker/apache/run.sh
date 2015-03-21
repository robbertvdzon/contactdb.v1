#!/bin/bash
chown www-data:www-data /var/www/html -R
source /etc/apache2/envvars
exec apache2 -D FOREGROUND