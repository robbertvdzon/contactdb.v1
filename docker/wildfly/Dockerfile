FROM sillenttroll/wildfly-java-8
#RUN /opt/wildfly/bin/add-user.sh user password --silent

USER root

RUN mkdir /data
COPY contactsbackend-1.0-SNAPSHOT.war /data/contactsbackend-1.0-SNAPSHOT.war
COPY config.sh /data/config.sh
COPY mysql-connector-java-5.1.33-bin.jar /data/mysql-connector-java-5.1.33-bin.jar
RUN chmod a+x /data/config.sh
#RUN /data/config.sh

#USER wildfly
