#!/bin/bash

#cp /data/*.jar /opt/wildfly/standalone/deployments
#cp /data/*.war /opt/wildfly/standalone/deployments
#sed -i 's/127.0.0.1/0.0.0.0/g' /opt/wildfly/standalone/configuration/standalone.xml
#rm -f /tmp/add.txt
#echo ' <datasource jta="false" jndi-name="java:jboss/datasources/MySQLDS" pool-name="MySQLDS" enabled="true" use-ccm="false">' >> /tmp/add.txt
#echo '                    <connection-url>jdbc:mysql://172.17.0.70:3306/contact</connection-url>' >> /tmp/add.txt
#echo '                    <driver-class>com.mysql.jdbc.Driver</driver-class>' >> /tmp/add.txt
#echo '                    <driver>mysql-connector-java-5.1.16.jar</driver>' >> /tmp/add.txt
#echo '                    <security>' >> /tmp/add.txt
#echo '                        <user-name>admin</user-name>' >> /tmp/add.txt
#echo '                        <password></password>' >> /tmp/add.txt
#echo '                    </security>' >> /tmp/add.txt
#echo '                    <validation>' >> /tmp/add.txt
#echo '                        <validate-on-match>false</validate-on-match>' >> /tmp/add.txt
#echo '                        <background-validation>false</background-validation>' >> /tmp/add.txt
#echo '                    </validation>' >> /tmp/add.txt
#echo '                    <statement>' >> /tmp/add.txt
#echo '                        <share-prepared-statements>false</share-prepared-statements>' >> /tmp/add.txt
#echo '                    </statement>' >> /tmp/add.txt
#echo '                </datasource>' >> /tmp/add.txt
#sed  -i '/<datasources>/r /tmp/add.txt' /opt/wildfly/standalone/configuration/standalone.xml
#sed -i 's/datasource="java:jboss\/datasources\/ExampleDS"/datasource="java:jboss\/datasources\/MySQLDS"/g' /opt/wildfly/standalone/configuration/standalone.xml
# tail -f /var/log/wildfly/console.log



JBOSS_HOME=/opt/wildfly
JBOSS_CLI=$JBOSS_HOME/bin/jboss-cli.sh
JBOSS_MODE=${1:-"standalone"}
JBOSS_CONFIG=${2:-"$JBOSS_MODE.xml"}

function wait_for_server() {
  until `$JBOSS_CLI -c "ls /deployment" &> /dev/null`; do
    sleep 1
  done
}

echo "=> Starting WildFly server"
$JBOSS_HOME/bin/$JBOSS_MODE.sh -b 0.0.0.0 -c $JBOSS_CONFIG &

echo "=> Waiting for the server to boot"
wait_for_server

echo "=> Executing the commands"
echo "=> MYSQL_HOST: mysqldb"
echo "=> MYSQL_PORT: 3306"
echo "=> MYSQL (docker host): mysqldb"
echo "=> MYSQL (docker port): 3306"
echo "=> MYSQL (kubernetes host): mysqldb"
echo "=> MYSQL (kubernetes port): 3306"
#$JBOSS_CLI -c --file=`dirname "$0"`/commands.cli
$JBOSS_CLI -c << EOF
batch

# Add MySQL module
module add --name=com.mysql --resources=/data/mysql-connector-java-5.1.33-bin.jar --dependencies=javax.api,javax.transaction.api

# Add MySQL driver
/subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql,driver-module-name=com.mysql,driver-xa-datasource-class-name=com.mysql.jdbc.jdbc2.optional.MysqlXADataSource)

# Add the datasource
data-source remove --name=ExampleDS
data-source add --name=ExampleDS --driver-name=mysql --jndi-name=java:jboss/datasources/ExampleDS --connection-url=jdbc:mysql://mysqldb:3306/contact --user-name=root --use-ccm=false --max-pool-size=25 --blocking-timeout-wait-millis=5000 --enabled=true
#data-source add --name=MySQLDS --driver-name=mysql --jndi-name=java:jboss/datasources/MySQLDS --connection-url=jdbc:mysql://mysqldb:3306/contact --user-name=root --use-ccm=false --max-pool-size=25 --blocking-timeout-wait-millis=5000 --enabled=true
# Execute the batch
run-batch
EOF

# Deploy the WAR
#cp /opt/jboss/wildfly/customization/employees.war $JBOSS_HOME/$JBOSS_MODE/deployments/employees.war
cp /data/*.war $JBOSS_HOME/standalone/deployments

echo "=> Shutting down WildFly"
if [ "$JBOSS_MODE" = "standalone" ]; then
  $JBOSS_CLI -c ":shutdown"
else
  $JBOSS_CLI -c "/host=*:shutdown"
fi

echo "=> Restarting WildFly"
$JBOSS_HOME/bin/$JBOSS_MODE.sh -b 0.0.0.0 -c $JBOSS_CONFIG -bmanagement 0.0.0.0 --debug

#CMD ["/opt/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]

