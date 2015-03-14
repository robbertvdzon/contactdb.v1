FROM wnameless/mysql-phpmyadmin
RUN mkdir /data
COPY config.sh /data/config.sh
COPY contact.sql /data/contact.sql
RUN chmod a+x /data/config.sh
RUN /data/config.sh
