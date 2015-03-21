#!/bin/bash

export CLUSTERNR=$1
if [ -z "$1" ]
	then
		export CLUSTERNR="1"
fi


export DOCKERNAME_APACHE="apache"$CLUSTERNR
export DOCKERNAME_WILDFLY="wildfly"$CLUSTERNR
export DOCKERNAME_MYSQLDB="mysqldb"$CLUSTERNR

docker stop $DOCKERNAME_APACHE
docker stop $DOCKERNAME_WILDFLY
docker stop $DOCKERNAME_MYSQLDB
