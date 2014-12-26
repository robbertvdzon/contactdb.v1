#Workshop: Building a contact database 
### Using: JEE7, Java8, Wildfly, MySQL, AngularJS
### note: this project is work-in-progress !!! 
-----------
**Description:**  
Complete workshop for setting up a develop systes, developing a contact database and deployment to a linux server in the cloud...  
  
# Still Todo: #

- async code
- login beter
- add/edit/remove contact
- document backend
- document angular
- blogs for:  
-- install Intellij  
-- install WAMP  
-- install Wildfly on devel system  
-- install Bower/Angular on devel system  
-- source: REST  
-- source: Asyc  
-- source: Authentication  
-- source: Angular  


# Step 1: Setting up devel system and server#
  
**Setting up a developer machine**  
Configure a developer system

- Install and configure WAMP: see xxxx  
- Install Intellij: see xxxxx    
-- Download cummunity edition  
-- Download plugin:  
-- open settings  
-- ga naar plugins...  
- Install and configure Wildfly: see [http://robbertvdzon.blogspot.nl/2014/11/wildfly-configureren.html](http://robbertvdzon.blogspot.nl/2014/11/wildfly-configureren.html "install Intellij")
- Install and configure WAMP: see xxxx
- Install Bower and Angular: see xxx  
`su - `   
`npm install --global yo bower grunt-cli`   
`npm install --global generator-angular@0.9.2`  
`su robbert`  
`yo --version && bower --version && grunt --version`  
- Get the source (clone github so you can update the code or use my sample code)  

  
**Installatie op debian server:**  
To run the code on a real server on the web, you can get a debian server in the cloud (e.g. at xxx for xx ct a day, see xxx)  
On a clean debian installation, perform the following steps to install and configure the system. After installation the server will contain apache to host the static pages, wildfly to host the backend and mySQL as the database.  

`cd /tmp`  
`wget https://raw.githubusercontent.com/robbertvdzon/contactdb.v1/master/resources/linux-full-install.sh`  
`sh /tmp/linux-full-install.sh`   
  
todo: how update git when changes are pushed  
todo: how to deploy these changes  

*easy server commands and urls:*   
`tail -f /var/log/wildfly/console.log`  
`service wildfly restart`  
phpmyadmin: http://[ip]:888  
wildfly console: http://[ip]:9990  (user=admin/passwd=admin /  lokaal is het passwd: admin)  
  
  
# Step 2: Developing the backend


# Step 3: Developing the frontend


