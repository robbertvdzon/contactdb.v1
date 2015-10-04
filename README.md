### Building a contact database using AngularJS, REST services, JEE7, Java8, Apache, Wildfly, MySQL and Docker

----------

**Keywords:** JEE7, Java8, Wildfly, JPA, Angular, Apache, Mysql, Docker<br>
**Description:** Setting up 3 docker images to host a simple contact database using and apache, wildfly and mysql docker image. The contact database is created using java8, rest services and JPA on the backend and angularJS on the frontend.  
<br>

**Architecture picture:**  <br>
![alt tag](https://raw.githubusercontent.com/robbertvdzon/contactdb.v1/master/contactdatabase1-architecture.png)

Step 1: Setting up ubuntu server for running, deploying an debugging
------------------------------------------

Setting op an ubuntu server. On this server we will run mysql for running the database, wildfly for running the
backend and apache for hosting the static web files.


You can use the single line full install which will download and run all images:

    cd /tmp && wget https://raw.githubusercontent.com/robbertvdzon/contactdb.v1/master/full_install.sh && sh /tmp/full_install.sh

You can also manually install the packages, get the project and install/run the containers using the following steps

First: install Docker, git and clone the source:

    # install docker 
	apt-get update
	apt-get install docker.io

	# update to the latest version (needed to use the 'exec' command) 
    curl -sSL https://get.docker.com/ubuntu | sudo sh    

	# clone the sources
    mkdir /workspace    
    cd /workspace    
    git clone https://github.com/robbertvdzon/contactdb.v1.git
    


Next: build and start the three docker containers:

	cd /workspace/contactdb.v1/docker
	./cluster_build.sh

The three docker containers work together as a cluster.
To stop, start and remove the docker containers, use the following commands:

	./cluster_stop.sh 
	./cluster_start.sh 
	./cluster_remote.sh 


All containers have sshd running, so it is possible to ssh to any of the containers directly to copy files or to monitor the system. The ssh ports of the containers are available on the ubuntu host under differente ports: 

	apache container: port 1122 (login as root/admin)
	wildfly container: port 1222 (login as root/admin)
	mysql container: port 1322 (login as root/admin)

The following web en debug ports are created on the host:

	#apache, which is running the application
	http://ubuntuserver:1080

	#phpmyadmin: login as root with no passwd
	http://ubuntuserver:1081/phpmyadmin

	#the wildfly console, login as admin/admin
	http://ubuntuserver:1090/console/App.html

Besides these (web) ports, the followings are also created:

	the wildfly debug port: port 1087
	the wildfly application: port 1088

Step 3: Test the application
------------------------------------------
Open a browser and go to: `http://ubuntuserver:1080`


Step 4: Setting up a development system 
------------------------------------------

Setting up a developer machine Configure a developer system on windows.
We will use windows to develop on. We deploy, debug en test the application on the ubuntu server.

-   Install JDK1.8, git, maven and intellij

Clone the source

    mkdir /workspace    
    cd /workspace    
    git clone https://github.com/robbertvdzon/contactdb.v1.git

Update the local settings.xml file with the properties for the docker containers: 
	
	<?xml version="1.0" encoding="UTF-8"?>
	<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
	          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">
	    <profiles>
	        <profile>
	            <id>local</id>
	            <properties>
	                <!-- properties for deploy to wildfly -->
	                <wildfly-hostname>localhost</wildfly-hostname>
	                <wildfly-port>9990</wildfly-port>
	                <wildfly-username>admin</wildfly-username>
	                <wildfly-password>admin</wildfly-password>
	                <!-- skip uploading to apache -->
	                <skip-apache-scp>true</skip-apache-scp>
	            </properties>
	        </profile>
	        <profile>
	            <id>remote</id>
	            <properties>
	                <!-- properties for deploy to wildfly -->
	                <wildfly-hostname>192.168.178.26</wildfly-hostname>
	                <wildfly-port>1090</wildfly-port>
	                <wildfly-username>admin</wildfly-username>
	                <wildfly-password>admin</wildfly-password>
	                <!-- enabled uploading to apache -->
	                <skip-apache-scp>false</skip-apache-scp>
	                <!-- properties for uploading to apache -->
	                <apache-scp-user>root</apache-scp-user>
	                <apache-scp-passwd>admin</apache-scp-passwd>
	                <apache-scp-host>192.168.178.26</apache-scp-host>
	                <apache-scp-wwwdir>/var/www/html</apache-scp-wwwdir>
	                <apache-scp-port>1122</apache-scp-port>
	            </properties>
	        </profile>
	    </profiles>
	</settings>


Use the following command to compile, deploy and upload:

	# deploy the project to the wildfly docker container
	mvn install wildfly:deploy -P remote
	
	# scp the static files (html/javascript files) directly to apache docker container
	mvn install -P remote


Step 5: Developing the frontend
------------------------------

The frontend is build using AngularJS and uses controllers to manage the web page and services to connect to the backend.

Step 6: Developing the backend
-------------------------------

The backend runs on Wildfly on java8. <br>
Wildfly uses the RESTEasy implementation of JAX-RS which we use to implement a set of RESTfull web services to implement the API calls to list, update, add and remove contacts.<br>
The application uses JPA to persist the contacts.



Wishlist:
-------------------------------
This reference implementation is not finished yet, the following issues needs to be resolved yet:
<br><br>
**Authenticatie:**
We store the token in a hashtable, this must be done per session and more secure. Perhaps we also need to store the tokens in the database.
I think anyone can adjust the header and change the authID (=userID). I also do not check if the token belongs to the original browser.
<br><br>
**Datastore:**
Currently, we change the ExampleDS configuration in wildfly to use mysql. 
Perhaps we can configure a second datastore and use that in our backend.
<br><br>
**Data container:**
We need to use a data container for the mysql docker container.
<br><br>
**Passwords:**
The passwords are stored as plain text. These needs to be stores encrypted.

<br>
<br>
