#!/bin/bash

# install git and clone the sources and scripts
apt-get install git
mkdir /workspace
chmod a+rwx /workspace
cd /workspace
git clone https://github.com/robbertvdzon/contactdb.v1.git


#update apt
echo "deb http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" | tee /etc/apt/sources.list.d/webupd8team-java.list
echo "deb-src http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" | tee -a /etc/apt/sources.list.d/webupd8team-java.list
apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys EEA14886
apt-get update

#install tools for silent installations
apt-get install debconf-utils

echo "mysql-server-5.5 mysql-server/root_password_again password admin" | debconf-set-selections
echo "mysql-server-5.5 mysql-server/root_password password admin" | debconf-set-selections
apt-get -y install mysql-server 

echo 'phpmyadmin phpmyadmin/dbconfig-install boolean true' | debconf-set-selections
echo 'phpmyadmin phpmyadmin/app-password-confirm password admin' | debconf-set-selections
echo 'phpmyadmin phpmyadmin/mysql/admin-pass password admin' | debconf-set-selections
echo 'phpmyadmin phpmyadmin/mysql/app-pass password admin' | debconf-set-selections
echo 'phpmyadmin phpmyadmin/reconfigure-webserver multiselect apache2' | debconf-set-selections

apt-get -y install phpmyadmin

# install oracle with interactive settings already filled in
echo 'oracle-java8-installer shared/present-oracle-license-v1-1 note' | debconf-set-selections
echo 'oracle-java8-installer shared/accepted-oracle-license-v1-1 boolean true' | debconf-set-selections
apt-get -y install oracle-java8-installer

apt-get -y install maven apache2 libapache2-mod-proxy-html libmysql-java python g++ make checkinstall ufw

useradd devel
echo -e "devel\ndevel" | (passwd devel)
adduser devel sudo

cd /workspace/contactdb.v1
git config credential.helper store

rm -rf /var/www
ln -s /workspace/contactdb.v1/useless-frontend/app /var/www

a2enmod proxy proxy_http

rm -f /tmp/add.txt
echo 'ProxyPass /api http://localhost:28080/mswbackend-1.0-SNAPSHOT' >> /tmp/add.txt
echo 'ProxyPassReverse /api http://localhost:28080/mswbackend-1.0-SNAPSHOT' >> /tmp/add.txt
sed  -i '/<VirtualHost/r /tmp/add.txt' /etc/apache2/sites-available/default
service apache2 restart


# remove old configuration
rm /etc/apache2/conf.d/phpmyadmin.conf
# add post 888
rm -f /tmp/add.txt
echo 'Listen 888' >> /tmp/add.txt
sed  -i '/Listen 80/r /tmp/add.txt' /etc/apache2/ports.conf
# config apache
echo '<VirtualHost *:888>' >> /etc/apache2/sites-available/phpmyadmin
echo '  ServerName phpmyadmin' >> /etc/apache2/sites-available/phpmyadmin
echo '  DocumentRoot /usr/share/phpmyadmin' >> /etc/apache2/sites-available/phpmyadmin
echo '</VirtualHost>' >> /etc/apache2/sites-available/phpmyadmin
# add phpmyadmin
a2ensite phpmyadmin
# restart apache
service apache2 restart

mysql -u root --password=admin < /workspace/contactdb.v1/resources/contact.sql

/workspace/contactdb.v1/resources/wildfly-install.sh
# update rc.d (script doesn’t do that which makes wildfly not being started at boot time
update-rc.d wildfly defaults

cp /usr/share/maven-repo/mysql/mysql-connector-java/5.1.16/*.jar /opt/wildfly/standalone/deployments


/opt/wildfly/bin/add-user.sh -m -u admin -p admin

sed -i 's/127.0.0.1/0.0.0.0/g' /opt/wildfly/standalone/configuration/standalone.xml
service wildfly restart


# stop wildfly
service wildfly stop

# add the mySQL jdbc configuration
rm -f /tmp/add.txt
echo ' <datasource jta="false" jndi-name="java:jboss/datasources/MySQLDS" pool-name="MySQLDS" enabled="true" use-ccm="false">' >> /tmp/add.txt
echo '                    <connection-url>jdbc:mysql://localhost:3306/msw</connection-url>' >> /tmp/add.txt
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
# set the MySQLDS datasource as default
sed -i 's/datasource="java:jboss\/datasources\/ExampleDS"/datasource="java:jboss\/datasources\/MySQLDS"/g' /opt/wildfly/standalone/configuration/standalone.xml

# stop wildfly
service wildfly start

export JAVA_HOME=/usr/lib/jvm/java-8-oracle/jre
cd /workspace/contactdb.v1/useless-backend
mvn install
cp /workspace/contactdb.v1/useless-backend/target/*.war /opt/wildfly/standalone/deployments
# tail -f /var/log/wildfly/console.log


#install firewall
ufw disable
ufw allow 80
ufw allow 22
ufw allow from 127.0.0.1
ufw enable
ufw logging on
ufw status
