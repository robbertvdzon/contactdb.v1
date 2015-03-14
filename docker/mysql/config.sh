#!/bin/bash
echo "mysqld: ALL" >> /etc/hosts.allow
mysqld_safe &
mysqladmin --silent --wait=30 ping 
mysql -e "GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION;"
mysql -e "GRANT ALL PRIVILEGES ON *.* TO 'admin'@'%' WITH GRANT OPTION;"
#mysql -u root --password=admin < /data/contact.sql
mysql -u root < /data/contact.sql