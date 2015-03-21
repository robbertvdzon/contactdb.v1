#!/bin/bash

export CLUSTERNR=$1
if [ -z "$1" ]
	then
		export CLUSTERNR="1"
fi


export DOCKERNAME_SSH="ssh"$CLUSTERNR
export DOCKERNAME_APACHE="apache"$CLUSTERNR
export DOCKERNAME_WILDFLY="wildfly"$CLUSTERNR
export DOCKERNAME_MYSQLDB="mysqldb"$CLUSTERNR

docker start $DOCKERNAME_SSH
docker start $DOCKERNAME_APACHE
docker start $DOCKERNAME_WILDFLY
docker start $DOCKERNAME_MYSQLDB
