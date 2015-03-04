FROM debian
RUN apt-get install debconf-utils
RUN echo "mysql-server-5.5 mysql-server/root_password_again password admin" | debconf-set-selections
RUN echo "mysql-server-5.5 mysql-server/root_password password admin" | debconf-set-selections
RUN apt-get -y install mysql-server 
RUN apt-get -y install libmysql-java 
RUN mkdir /data
COPY contact.sql /data/contact.sql
RUN mysql -u root --password=admin < /data/contact.sql

#RUN apachectl start
EXPOSE 80


CMD tail -f /data/contact.sql


