FROM wnameless/mysql-phpmyadmin
COPY contact.sql /data/contact.sql
RUN mysql -u root --password=admin < /data/contact.sql

EXPOSE 3306

CMD mysqld

