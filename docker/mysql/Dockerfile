FROM wnameless/mysql-phpmyadmin
RUN mkdir /data
COPY config.sh /data/config.sh
COPY contact.sql /data/contact.sql
RUN chmod a+x /data/config.sh
RUN /data/config.sh




#FROM phusion/baseimage:0.9.13
#RUN apt-get update
#RUN apt-get install debconf-utils
#RUN echo "mysql-server-5.5 mysql-server/root_password_again password admin" | debconf-set-selections
#RUN echo "mysql-server-5.5 mysql-server/root_password password admin" | debconf-set-selections
#RUN apt-get -y install mysql-server
#RUN mkdir /data
#RUN mysqld &
#RUN sleep 5
#COPY contact.sql /data/contact.sql
#RUN mysql -u root --password=admin < /data/contact.sql
#EXPOSE 3306
#CMD mysqld

