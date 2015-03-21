#!/bin/bash

export CLUSTERNR=$1
if [ -z "$1" ]
	then
		export CLUSTERNR="1"
fi

export DOCKERNAME_APACHE="apache"$CLUSTERNR
export DOCKERNAME_WILDFLY="wildfly"$CLUSTERNR
export DOCKERNAME_MYSQLDB="mysqldb"$CLUSTERNR

docker rm -f $DOCKERNAME_APACHE
docker rm -f $DOCKERNAME_WILDFLY
docker rm -f $DOCKERNAME_MYSQLDB

