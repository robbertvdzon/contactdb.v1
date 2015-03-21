FROM wnameless/mysql-phpmyadmin
MAINTAINER Robbert vd Zon <robbert@vdzon.com>

RUN mkdir /data
COPY config.sh /data/config.sh
COPY contact.sql /data/contact.sql
RUN chmod a+x /data/config.sh
RUN /data/config.sh
