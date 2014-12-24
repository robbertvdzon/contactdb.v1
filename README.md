#Workshop: Building a contact database 
### Using: JEE7, Java8, Wildfly, MySQL, AngularJS
-----------
Complete workshop for setting up a develop syste, developing a contact database and deployment to a linux server in the cloud...  
  
  
**Installatie op debian server:**  
`cd /tmp`  
`wget https://raw.githubusercontent.com/robbertvdzon/contactdb.v1/master/resources/linux-full-install.sh`  
`sh /tmp/linux-full-install.sh > /tmp/install.log 2>&1 &`   
  
**easy server commands and urls:**  
`tail -f /var/log/wildfly/console.log`  
`service wildfly restart`  
phpmyadmin: http://[ip]:888  
wildfly console: http://[ip]:9990  (user=admin/passwd=admin /  lokaal is het passwd: admin)  
  
**Install bower en angular:**  
`su - `   
`npm install --global yo bower grunt-cli`   
`npm install --global generator-angular@0.9.2`  
`su robbert`  
`yo --version && bower --version && grunt --version`  
   
**Ontwikkel machine configuratie**  
Intellij install:  
Download cummunity edition  
Download plugin:  
open settings  
ga naar plugins  
  
  
**Wildfly:**  
download wildfly zip file en unzip in c:\apps  

