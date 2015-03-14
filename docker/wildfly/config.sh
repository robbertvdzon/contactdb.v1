#!/bin/bash




#sh /workspace/contactdb.v1/resources/wildfly-install.sh

#
# update rc.d (script doesn’t do that which makes wildfly not being started at boot time
#
#update-rc.d wildfly defaults

cp /data/*.jar /opt/wildfly/standalone/deployments
cp /data/*.war /opt/wildfly/standalone/deployments

/opt/wildfly/bin/add-user.sh -m -u admin -p admin

sed -i 's/127.0.0.1/0.0.0.0/g' /opt/wildfly/standalone/configuration/standalone.xml
#service wildfly restart


#
# stop wildfly
#
service wildfly stop

#
# add the mySQL jdbc configuration
#
rm -f /tmp/add.txt
echo ' <datasource jta="false" jndi-name="java:jboss/datasources/MySQLDS" pool-name="MySQLDS" enabled="true" use-ccm="false">' >> /tmp/add.txt
echo '                    <connection-url>jdbc:mysql://localhost:3306/contact</connection-url>' >> /tmp/add.txt
echo '                    <driver-class>com.mysql.jdbc.Driver</driver-class>' >> /tmp/add.txt
echo '                    <driver>mysql-connector-java-5.1.16.jar</driver>' >> /tmp/add.txt
echo '                    <security>' >> /tmp/add.txt
echo '                        <user-name>root</user-name>' >> /tmp/add.txt
echo '                        <password>admin</password>' >> /tmp/add.txt
echo '                    </security>' >> /tmp/add.txt
echo '                    <validation>' >> /tmp/add.txt
echo '                        <validate-on-match>false</validate-on-match>' >> /tmp/add.txt
echo '                        <background-validation>false</background-validation>' >> /tmp/add.txt
echo '                    </validation>' >> /tmp/add.txt
echo '                    <statement>' >> /tmp/add.txt
echo '                        <share-prepared-statements>false</share-prepared-statements>' >> /tmp/add.txt
echo '                    </statement>' >> /tmp/add.txt
echo '                </datasource>' >> /tmp/add.txt
sed  -i '/<datasources>/r /tmp/add.txt' /opt/wildfly/standalone/configuration/standalone.xml

#
# set the MySQLDS datasource as default
#
sed -i 's/datasource="java:jboss\/datasources\/ExampleDS"/datasource="java:jboss\/datasources\/MySQLDS"/g' /opt/wildfly/standalone/configuration/standalone.xml


#
# stop wildfly
#
#service wildfly start

#export JAVA_HOME=/usr/lib/jvm/java-8-oracle/jre
#cd /workspace/contactdb.v1/useless_backend
#mvn install
#cp /workspace/contactdb.v1/useless_backend/target/*.war /opt/wildfly/standalone/deployments

#
# tail -f /var/log/wildfly/console.log
#


