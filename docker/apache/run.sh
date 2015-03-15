#!/bin/bash
chown www-data:www-data /data/app -R
source /etc/apache2/envvars
exec apache2 -D FOREGROUND