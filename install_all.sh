#!/bin/bash
docker rm -f apache
docker rm -f wildfly
docker rm -f mysqldb

# mysql container
docker build -t robbertvdzon/mysql /workspace/contactdb.v1/docker/mysql
docker run -d -p 888:80 -p 4122:22 --name mysqldb robbertvdzon/mysql

# wildfly container
docker build -t robbertvdzon/wildfly /workspace/contactdb.v1/docker/wildfly
docker run -d -it -p 8787:8787 -p 9990:9990 -p 8080:8080 -p 4222:22 --name wildfly --link mysqldb:mysqldb robbertvdzon/wildfly

# apache container
docker build -t robbertvdzon/apache /workspace/contactdb.v1/docker/apache
docker run -d -it -p 80:80 -p 4322:22 --name apache --link wildfly:wildfly robbertvdzon/apache
