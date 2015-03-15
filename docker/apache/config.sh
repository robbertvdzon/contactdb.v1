#!/bin/bash
mv /etc/apache2/sites-available/000-default.conf /etc/apache2/sites-available/default.conf
mv /etc/apache2/sites-available/default.conf /etc/apache2/sites-available/000-default.conf
echo 'ProxyPass /api http://wildfly:28080/contactsbackend-1.0-SNAPSHOT' >> /tmp/add.txt
echo 'ProxyPassReverse /api http://wildfly:28080/contactsbackend-1.0-SNAPSHOT' >> /tmp/add.txt
sed  -i '/<VirtualHost/r /tmp/add.txt' /etc/apache2/sites-available/default
rm -f /tmp/add.txt

mkdir -p /data/app
rm -fr /var/www/html
ln -s /data/app /var/www/html
