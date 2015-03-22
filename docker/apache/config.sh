#!/bin/bash
rm -f /tmp/add.txt
echo 'ProxyPass /resources http://wildfly:8080/api/resources' >> /tmp/add.txt
echo 'ProxyPassReverse /resources http://wildfly:8080/api/resources' >> /tmp/add.txt
sed  -i '/<VirtualHost/r /tmp/add.txt' /etc/apache2/sites-available/000-default.conf
a2enmod proxy proxy_http
