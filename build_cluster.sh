#!/bin/bash

export CLUSTERNR="1"

export $CLUSTERNR+MYPHPADMIN_PORT="081"
export $CLUSTERNR+APACHE_PORT="080"
export $CLUSTERNR+SSH_PORT="022"
export $CLUSTERNR+WILDFLY_ADMIN_PORT="090"
export $CLUSTERNR+WILDFLY_DEBUG_PORT="087"
export $CLUSTERNR+WILDFLY_APP_PORT="088"

export DOCKERNAME_SSH="ssh"$CLUSTERNR
export DOCKERNAME_APACHE="apache"$CLUSTERNR
export DOCKERNAME_WILDFLY="wildfly"$CLUSTERNR
export DOCKERNAME_MYSQLDB="mysqldb"$CLUSTERNR
export DOCKERNAME_WILDFLYDATA="wildflydata"$CLUSTERNR
export DOCKERNAME_APACHEDATA="apachedata"$CLUSTERNR

docker rm -f $DOCKERNAME_SSH
docker rm -f $DOCKERNAME_APACHE
docker rm -f $DOCKERNAME_WILDFLY
docker rm -f $DOCKERNAME_MYSQLDB
docker rm -f $DOCKERNAME_WILDFLYDATA
docker rm -f $DOCKERNAME_APACHEDATA

# mysql container
docker build -t robbertvdzon/$DOCKERNAME_MYSQLDB ./docker/mysql
docker run -d -p $MYPHPADMIN_PORT:80 --name $DOCKERNAME_MYSQLDB robbertvdzon/$DOCKERNAME_MYSQLDB

# data container for wildfly
docker build -t robbertvdzon/$DOCKERNAME_WILDFLYDATA ./docker/wildflydata
docker run -d --name $DOCKERNAME_WILDFLYDATA robbertvdzon/$DOCKERNAME_WILDFLYDATA

# data container for apache
docker build -t robbertvdzon/$DOCKERNAME_APACHEDATA ./docker/apachedata
docker run -d --name $DOCKERNAME_APACHEDATA robbertvdzon/$DOCKERNAME_APACHEDATA

# ssh container for accessing the wildfly and apache data
docker build -t robbertvdzon/$DOCKERNAME_SSH ./docker/ssh
docker run -d -p $SSH_PORT:22 --volumes-from $DOCKERNAME_APACHEDATA --volumes-from $DOCKERNAME_WILDFLYDATA --name $DOCKERNAME_SSH robbertvdzon/$DOCKERNAME_SSH

# wildfly container
docker build -t robbertvdzon/$DOCKERNAME_WILDFLY ./docker/wildfly
docker run -d -it -p $WILDFLY_DEBUG_PORT:8787 -p $WILDFLY_ADMIN_PORT:9990 -p $WILDFLY_APP_PORT:8080 --name $DOCKERNAME_WILDFLY --link $DOCKERNAME_MYSQLDB:mysqldb robbertvdzon/$DOCKERNAME_WILDFLY

# apache container
docker build -t robbertvdzon/$DOCKERNAME_APACHE ./docker/apache
docker run -d -it -p $APACHE_PORT:80 --name $DOCKERNAME_APACHE --volumes-from $DOCKERNAME_APACHEDATA --link $DOCKERNAME_WILDFLY:wildfly robbertvdzon/$DOCKERNAME_APACHE
