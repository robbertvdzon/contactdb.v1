#!/bin/bash

rm -f /tmp/add.txt
echo 'ProxyPass /api http://wildfly:28080/contactsbackend-1.0-SNAPSHOT' >> /tmp/add.txt
echo 'ProxyPassReverse /api http://wildfly:28080/contactsbackend-1.0-SNAPSHOT' >> /tmp/add.txt
sed  -i '/<VirtualHost/r /tmp/add.txt' /etc/apache2/sites-available/000-default.conf

a2enmod proxy proxy_http

chown www-data:www-data /app -R
source /etc/apache2/envvars
exec apache2 -D FOREGROUND