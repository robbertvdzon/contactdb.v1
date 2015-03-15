#!/bin/bash
echo 'ProxyPass /api http://localhost:28080/contactsbackend-1.0-SNAPSHOT' >> /tmp/add.txt
echo 'ProxyPassReverse /api http://localhost:28080/contactsbackend-1.0-SNAPSHOT' >> /tmp/add.txt
sed  -i '/<VirtualHost/r /tmp/add.txt' /etc/apache2/sites-available/default
rm -f /tmp/add.txt

mkdir -p /data/app
rm -fr /var/www/html
ln -s /data/app /var/www/html
