#!/bin/bash
docker rm -f ssh
docker rm -f apache
docker rm -f wildfly
docker rm -f mysqldb
docker rm -f wildflydata
docker rm -f apachedata

docker build -t robbertvdzon/mysql /workspace/contactdb.v1/docker/mysql
docker run -d -p 888:80 --name mysqldb robbertvdzon/mysql

docker build -t robbertvdzon/wildflydata /workspace/contactdb.v1/docker/wildflydata
docker run -d --name wildflydata robbertvdzon/wildflydata

docker build -t robbertvdzon/apachedata /workspace/contactdb.v1/docker/apachedata
docker run -d --name apachedata robbertvdzon/apachedata

docker build -t robbertvdzon/ssh /workspace/contactdb.v1/docker/ssh
docker run -d -p 4022:22 --volumes-from apachedata --volumes-from wildflydata  --name ssh robbertvdzon/ssh

docker build -t robbertvdzon/wildfly /workspace/contactdb.v1/docker/wildfly
docker run -d -it -p 8787:8787 -p 9990:9990 -p 8080:8080 --name wildfly --link mysqldb:mysqldb robbertvdzon/wildfly

docker build -t robbertvdzon/apache /workspace/contactdb.v1/docker/apache
docker run -d -it -p 80:80 --name apache --volumes-from apachedata --link wildfly:wildfly robbertvdzon/apache
