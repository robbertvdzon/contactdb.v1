#!/bin/bash
docker rm -f apache
docker rm -f wildfly
docker rm -f mysqldb
docker rm -f wildflydata
docker rm -f mysqldata
docker rm -f apachedata
docker rm -f ssh

docker build -t robbertvdzon/wildflydata /workspace/contactdb.v1/docker/wildflydata
docker run -d --name wildflydata robbertvdzon/wildflydata

docker build -t robbertvdzon/mysqldata /workspace/contactdb.v1/docker/mysqldata
docker run -d --name mysqldata robbertvdzon/mysqldata

docker build -t robbertvdzon/apachedata /workspace/contactdb.v1/docker/apachedata
docker run -d --name apachedata robbertvdzon/apachedata

docker build -t robbertvdzon/mysql /workspace/contactdb.v1/docker/mysql
docker run -d -p 888:80 --name mysqldb --volumes-from mysqldata robbertvdzon/mysql

docker build -t robbertvdzon/wildfly /workspace/contactdb.v1/docker/wildfly
docker run -d -it -p 8787:8787 -p 9990:9990 -p 8080:8080 --name wildfly --volumes-from wildflydata --link mysqldb:mysqldb robbertvdzon/wildfly

docker build -t robbertvdzon/apache /workspace/contactdb.v1/docker/apache
docker run -d -it -p 80:80 --name apache --volumes-from apachedata --link wildfly:wildfly robbertvdzon/apache

docker build -t robbertvdzon/ssh /workspace/contactdb.v1/docker/ssh
docker run -d -p 4022:22 --name ssh robbertvdzon/ssh
