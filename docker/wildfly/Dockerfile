FROM sillenttroll/wildfly-java-8
RUN /opt/wildfly/bin/add-user.sh user password --silent

USER root

RUN mkdir /data
COPY contactsbackend-1.0-SNAPSHOT.war /data/config.sh
COPY config.sh /data/config.sh
RUN chmod a+x /data/config.sh
RUN /data/config.sh

USER wildfly
