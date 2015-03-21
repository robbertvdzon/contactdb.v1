#!/bin/bash

export DOCKERNAME_SSH="ssh"
export DOCKERNAME_APACHE="apache"
export DOCKERNAME_WILDFLY="wildfly"
export DOCKERNAME_MYSQLDB="mysqldb"
export DOCKERNAME_WILDFLYDATA="wildflydata"
export DOCKERNAME_APACHEDATA="apachedata"

docker rm -f $DOCKERNAME_SSH
docker rm -f $DOCKERNAME_APACHE
docker rm -f $DOCKERNAME_WILDFLY
docker rm -f $DOCKERNAME_MYSQLDB
docker rm -f $DOCKERNAME_WILDFLYDATA
docker rm -f $DOCKERNAME_APACHEDATA

# mysql container
docker build -t robbertvdzon/$DOCKERNAME_MYSQLDB ./docker/mysql
docker run -d -p 888:80 --name $DOCKERNAME_MYSQLDB robbertvdzon/$DOCKERNAME_MYSQLDB

# data container for wildfly
docker build -t robbertvdzon/$DOCKERNAME_WILDFLYDATA ./docker/wildflydata
docker run -d --name $DOCKERNAME_WILDFLYDATA robbertvdzon/$DOCKERNAME_WILDFLYDATA

# data container for apache
docker build -t robbertvdzon/$DOCKERNAME_APACHEDATA ./docker/apachedata
docker run -d --name $DOCKERNAME_APACHEDATA robbertvdzon/$DOCKERNAME_APACHEDATA

# ssh container for accessing the wildfly and apache data
docker build -t robbertvdzon/$DOCKERNAME_SSH ./docker/ssh
docker run -d -p 4022:22 --volumes-from $DOCKERNAME_APACHEDATA --volumes-from $DOCKERNAME_WILDFLYDATA --name $DOCKERNAME_SSH robbertvdzon/$DOCKERNAME_SSH

# wildfly container
docker build -t robbertvdzon/$DOCKERNAME_WILDFLY ./docker/wildfly
docker run -d -it -p 8787:8787 -p 9990:9990 -p 8080:8080 --name $DOCKERNAME_WILDFLY --link $DOCKERNAME_MYSQLDB:mysqldb robbertvdzon/$DOCKERNAME_WILDFLY

# apache container
docker build -t robbertvdzon/apache ./docker/apache
docker run -d -it -p 80:80 --name $DOCKERNAME_APACHE --volumes-from $DOCKERNAME_APACHEDATA --link $DOCKERNAME_WILDFLY:wildfly robbertvdzon/$DOCKERNAME_APACHE
