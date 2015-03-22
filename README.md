Workshop: Building a contact database (note: project is in progress!!)
======================================================================

### Using: JEE7, Java8, Wildfly, MySQL, AngularJS

 

**Creation date:** 26-dec-2014  
**Last modification date:** 01-maart-2015  
**Keywords:** JEE7, Java8, JPA, .....  
**Description:** Complete workshop for setting up a develop systes, developing a
contact database and deployment to a linux server in the cloud...
 

Step 1: Setting up ubuntu server for running, deploying an debugging
------------------------------------------

Setting op an ubuntu server. On this server we will run mysql for running the database, wildfly for running the
backend and apache for hosting the static web files.


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
	http://192.168.178.26:1080

	#phpmyadmin: login as root with no passwd
	http://192.168.178.26:1081/phpmyadmin

	#the wildfly console, login as admin/admin
	http://192.168.178.26:1090/console/App.html

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

-   Install JDK1.8, git, maven and intellij : see blog xxxx (todo)

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

First: install Bower and Angular: see xxx



**AngularJS project maken:**  
(use this tuturial: https://www.youtube.com/watch?v=gKiaLSJW5xI)  

	# npm install --global yo bower grunt-cli
	# yo --version && bower --version && grunt --version
	# npm install --global generator-angular@0.9.2

 

**Maak nieuw project:**  


	# yo angular  (maak project zonder Compass en Bootstrap,geen idee wat dat is)
 

Pas bower aan zodat deze in de apps folder staan (anders kan Apache ze niet
vinden) edit .bowerrc :  


	{
	"directory": "app\bower_components"
	}

 

run daarna:

	bower install

 
(the bower folder is added in the apps folder)

**Create new module:**

	yo angular:route game
	yo angular:route team
	yo angular:route competition
	In dex index.html: maak links aan naar die pagina’s

 

**Add new angular test code:**

	<script>
	function HelloController($scope) {
	    $scope.greeting = { text: 'Hello' };
	}
	
	function teams2Controller($scope,$http) {
	   $http.get("http://localhost/api/resources/teams/all").
	    success(function(response) {$scope.teams = response;});
	}
	</script>
	
	<div ng-controller='HelloController'><p>
	{{greeting.text}}, World</p>
	</div>
	
	<div ng-controller='HelloController'>
	<input ng-model='greeting.text'>
	<p>{{greeting.text}}, World</p>
	</div>
	
	<div ng-app="" ng-controller="teams2Controller">
	<table>
	<tr ng-repeat="x in teams">
	<td>
	    {{ x.id }}
	</td>
	<td>
	{{ x.teamname }}
	</td>
	</table>

 

**Example how to add a library to the project:**

	bower search backbone
	bower install backbone --save
	bower list
	bower update

 

Step 6: Developing the backend
-------------------------------


**Authenticatie:**
http://www.aschua.de/blog/pairing-angularjs-and-javaee-for-authentication/
Let op!
Nu bewaar in de token in een hashtable. Dit moet per sessie. En meer secure.
Ook kan iedereen nu gewoon de header aanpassen en de authID (=userID) aanpassen.
Ook controleer ik helemaal niet of de token wel klopt en of hij wel van de
originele browser afkomt.

Wishlist
-------------------------------


 
