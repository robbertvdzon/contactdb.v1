Workshop
=======
 
Building a contact databse
-----------
 
### Using:
### - JEE7
### - Java8
### - Wildfly
### - MySQL
### - AngularJS

 
 Paragraphs are separated
 by a blank line.
 
 Let 2 spaces at the end of a line to do a  
 line break
 
 Text attributes *italic*,
 **bold**, `monospace`,~~monospace~~, `monospace` .
 


*server commands:*
tail -f /var/log/wildfly/console.log
  
/opt/wildfly/bin/jboss-cli.sh --connect --controller=localhost:9990
deploy --force /workspace/msw-backend/target/mswbackend-1.0-SNAPSHOT.war  
  
service wildfly restart
  
*server adresses:*
  
phpmyadmin: http://[ip]:888
wildfly console: http://[ip]:9990  (user=admin/passwd=admin /  lokaal is het passwd: admin)
  
  
*Installatie op server:*
  
`cd /tmp
wget https://raw.githubusercontent.com/robbertvdzon/contactdb.v1/master/resources/linux-full-install.sh 
sh /tmp/linux-full-install.sh > /tmp/install.log 2>&1 & `
of:
  
*Install bower en angular:*
`su -
npm install --global yo bower grunt-cli
npm install --global generator-angular@0.9.2
su robbert
yo --version && bower --version && grunt --version`
  
  
*Ontwikkel machine configuratie*
Intellij install:
Download cummunity edition
Download plugin:
open settings
ga naar plugins
  
  
*Wildfly:*
download wildfly zip file en unzip in c:\apps

