#!/bin/bash

export CLUSTERNR=$1
if [ -z "$1" ]
	then
		export CLUSTERNR="1"
fi


# public port numbers
export APACHE_PORT=${CLUSTERNR}"080"
export APACHE_SSH_PORT=${CLUSTERNR}"122"
export WILDFLY_SSH_PORT=${CLUSTERNR}"222"
export MYSQL_SSH_PORT=${CLUSTERNR}"322"

export MYPHPADMIN_PORT=${CLUSTERNR}"081"
export WILDFLY_ADMIN_PORT=${CLUSTERNR}"090"
export WILDFLY_DEBUG_PORT=${CLUSTERNR}"087"
export WILDFLY_APP_PORT=${CLUSTERNR}"088"

# docker names
export DOCKERNAME_APACHE="apache"$CLUSTERNR
export DOCKERNAME_WILDFLY="wildfly"$CLUSTERNR
export DOCKERNAME_MYSQLDB="mysqldb"$CLUSTERNR

docker rm -f $DOCKERNAME_APACHE
docker rm -f $DOCKERNAME_WILDFLY
docker rm -f $DOCKERNAME_MYSQLDB

# build and run mysql container
docker build -t robbertvdzon/$DOCKERNAME_MYSQLDB ./mysql
docker run -d -p $MYSQL_SSH_PORT:22 -p $MYPHPADMIN_PORT:80 --name $DOCKERNAME_MYSQLDB robbertvdzon/$DOCKERNAME_MYSQLDB

# build and run wildfly container
docker build -t robbertvdzon/$DOCKERNAME_WILDFLY ./wildfly
docker run -d -it -p $WILDFLY_SSH_PORT:22 -p $WILDFLY_DEBUG_PORT:8787 -p $WILDFLY_ADMIN_PORT:9990 -p $WILDFLY_APP_PORT:8080 --name $DOCKERNAME_WILDFLY --link $DOCKERNAME_MYSQLDB:mysqldb robbertvdzon/$DOCKERNAME_WILDFLY

# build and run apache container
docker build -t robbertvdzon/$DOCKERNAME_APACHE ./apache
docker run -d -it -p $APACHE_SSH_PORT:22 -p $APACHE_PORT:80 --name $DOCKERNAME_APACHE --link $DOCKERNAME_WILDFLY:wildfly robbertvdzon/$DOCKERNAME_APACHE
