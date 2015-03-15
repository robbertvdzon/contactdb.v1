FROM ubuntu:trusty

# Install base packages
RUN apt-get update
RUN apt-get -yq install apache2
RUN echo "ServerName localhost" >> /etc/apache2/apache2.conf
RUN a2enmod proxy proxy_http

RUN mkdir /data
ADD app/ /data/app
ADD config.sh /data/config.sh
ADD run.sh /data/run.sh
RUN chmod a+x /data/run.sh
RUN chmod a+x /data/config.sh
RUN /data/config.sh

EXPOSE 80
WORKDIR /app

CMD ["/run.sh"]