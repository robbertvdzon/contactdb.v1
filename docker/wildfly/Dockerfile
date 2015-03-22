FROM sillenttroll/wildfly-java-8
MAINTAINER Robbert vd Zon <robbert@vdzon.com>

RUN /opt/wildfly/bin/add-user.sh admin admin --silent
USER root

# for mysql client
RUN apt-get update && \
    apt-get -y install \
    mysql-client-core-5.5 \
    openssh-server


RUN mkdir /data
COPY *.war /data/
COPY config.sh /data/config.sh
COPY mysql-connector-java-5.1.33-bin.jar /data/mysql-connector-java-5.1.33-bin.jar
RUN chmod a+x /data/config.sh

EXPOSE 8787
EXPOSE 8080
EXPOSE 9990

# SSHD, login as root with passwd: admin
RUN mkdir /var/run/sshd
RUN echo 'root:admin' | chpasswd
RUN sed -i 's/PermitRootLogin without-password/PermitRootLogin yes/' /etc/ssh/sshd_config

# SSH login fix. Otherwise user is kicked off after login
RUN sed 's@session\s*required\s*pam_loginuid.so@session optional pam_loginuid.so@g' -i /etc/pam.d/sshd

ENV NOTVISIBLE "in users profile"
RUN echo "export VISIBLE=now" >> /etc/profile

EXPOSE 22


CMD ["/data/config.sh"]