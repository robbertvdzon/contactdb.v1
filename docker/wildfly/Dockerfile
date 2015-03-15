FROM sillenttroll/wildfly-java-8
#RUN /opt/wildfly/bin/add-user.sh user password --silent
RUN /opt/wildfly/bin/add-user.sh admin admin --silent
#RUN /opt/wildfly/bin/add-user.sh -m -u admin -p admin
USER root

# for mysql client
RUN apt-get update
RUN apt-get install mysql-client-core-5.5
RUN apt-get install telnet

RUN mkdir /data
COPY contactsbackend-1.0-SNAPSHOT.war /data/contactsbackend-1.0-SNAPSHOT.war
COPY config.sh /data/config.sh
COPY mysql-connector-java-5.1.33-bin.jar /data/mysql-connector-java-5.1.33-bin.jar
RUN chmod a+x /data/config.sh
#RUN /data/config.sh
#USER wildfly

CMD ["/data/config.sh"]