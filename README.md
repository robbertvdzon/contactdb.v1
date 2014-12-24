 Heading
 =======
 
 Sub-heading
 -----------
 
 ### Another deeper heading
 
 Paragraphs are separated
 by a blank line.
 
 Let 2 spaces at the end of a line to do a  
 line break
 
 Text attributes *italic*,
 **bold**, `monospace`,~~monospace~~, `monospace` .
 



Shortcuts:

server commands:
tail -f /var/log/wildfly/console.log

/opt/wildfly/bin/jboss-cli.sh --connect --controller=localhost:9990
deploy --force /workspace/msw-backend/target/mswbackend-1.0-SNAPSHOT.war

service wildfly restart

handige links:
main server: 128.199.36.11
test server:128.199.33.12

phpmyadmin: http://128.199.36.11:888
mysql passwd: 12Iw  (zonder _msw)
wildfly: http://128.199.36.11:28080/
wildfly: http://128.199.36.11:9990  (user=admin/passwd=admin /  lokaal is het passwd: admin)
app base: http://128.199.36.11:28080/mswbackend-1.0-SNAPSHOT


Installatie op server:

cd /tmp
wget https://raw.githubusercontent.com/robbertvdzon/contactdb.v1/master/resources/linux-full-install.sh 
sh /tmp/linux-full-install.sh > /tmp/install.log 2>&1 & 
of:
apt-get install git
mkdir /workspace
chmod a+rwx /workspace
cd /workspace
git clone https://github.com/robbertvdzon/contactdb.v1.git

Run de rest automatisch met script:
/workspace/contactdb.v1/resources/linux-full-install.sh




Install bower en angular:
su -
npm install --global yo bower grunt-cli
npm install --global generator-angular@0.9.2
su robbert
yo --version && bower --version && grunt --version


Install nodejs:
mkdir ~/src && cd $_
wget -N http://nodejs.org/dist/node-latest.tar.gz
tar xzvf node-latest.tar.gz && cd node-v*
./configure
checkinstall   #remove the "v" in front of the version number in the dialog!
dpkg -i node_*

Build frontend:
su robbert
cd /workspace/msw-frontend
bower install



Een tweede test op poort 88 maken:
su -
vi /etc/apache2/ports.conf
add below Listen 80
Listen 88
vi /etc/apache2/sites-available/test

<VirtualHost *:88>
ServerName test
DocumentRoot /workspace/msw-frontend/app
ProxyPass /api/ http://localhost:28080/mswbackend-1.0-SNAPSHOT/resources/
ProxyPassReverse /api/ http://localhost:28080/mswbackend-1.0-SNAPSHOT/resources/
</VirtualHost>

a2ensite test



Ontwikkel machine configuratie

Intellij install:
Download cummunity edition
Download plugin:
open settings
ga naar plugins


Wildfly:
download wildfly zip file en unzip in c:\apps

