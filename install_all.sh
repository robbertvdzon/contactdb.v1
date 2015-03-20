#!/bin/bash
docker rm -f apache
docker rm -f wildfly
docker rm -f mysqldb
docker rm -f datavolume
docker build -t robbertvdzon/datavolume /workspace/contactdb.v1/docker/datavolume
docker run -d --name datavolume robbertvdzon/datavolume
docker build -t robbertvdzon/mysql /workspace/contactdb.v1/docker/mysql
docker run -d -p 4022:22 -p 888:80 --name mysqldb --volumes-from datavolume robbertvdzon/mysql
docker build -t robbertvdzon/wildfly /workspace/contactdb.v1/docker/wildfly
docker run -d -it -p 8787:8787 -p 9990:9990 -p 8080:8080 --name wildfly --volumes-from datavolume --link mysqldb:mysqldb robbertvdzon/wildfly
docker build -t robbertvdzon/apache /workspace/contactdb.v1/docker/apache
docker run -d -it -p 80:80 --name apache --volumes-from datavolume --link wildfly:wildfly robbertvdzon/apache
