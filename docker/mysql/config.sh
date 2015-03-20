#!/bin/bash
echo "mysqld: ALL" >> /etc/hosts.allow
chown mysql:mysql /var/lib/mysql
mysqld_safe &
mysqladmin --silent --wait=30 ping 
mysql -e "GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION;"
mysql -e "GRANT ALL PRIVILEGES ON *.* TO 'admin'@'%' WITH GRANT OPTION;"
#mysql -u root --password=admin < /data/contact.sql

mysql -u root < /data/contact.sql

sed -i -e"s/^bind-address\s*=\s*127.0.0.1/bind-address = 0.0.0.0/" /etc/mysql/my.cnf
