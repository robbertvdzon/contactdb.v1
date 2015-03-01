Workshop: Building a contact database (note: project is in progress!!)
======================================================================

### Using: JEE7, Java8, Wildfly, MySQL, AngularJS

 

**Creation date:** 26-dec-2014  
**Last modification date:** 26-dec-2014  
**Keywords:** JEE7, Java8, JPA, .....  
**Description:** Complete workshop for setting up a develop systes, developing a
contact database and deployment to a linux server in the cloud...
 


Step 1: Setting up devel system and server
------------------------------------------

Setting up a developer machine Configure a developer system

-   Install JDK1.8 (insall/java\_home/set path): see xxxx  
    

-   Install and configure WAMP: see xxxx  
    

-   Install Intellij: see xxxxx  
    

-   Install and configure Wildfly: see
    <http://robbertvdzon.blogspot.nl/2014/11/wildfly-configureren.html>

-   Install and configure WAMP: see xxxx

-   Install Bower and Angular: see xxx  
    

-   Get the source (clone github so you can update the code or use my sample
    code)

**Installatie op debian server:**  
To run the code on a real server on the web, you can get a debian server in the
cloud (e.g. at xxx for xx ct a day, see xxx)  
On a clean debian installation, perform the following steps to install and
configure the system. After installation the server will contain apache to host
the static pages, wildfly to host the backend and mySQL as the database.

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
cd /tmp
wget https://raw.githubusercontent.com/robbertvdzon/contactdb.v1/master/resources/linux-full-install.sh
sh /tmp/linux-full-install.sh
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
todo: how update git when changes are pushed
todo: how to deploy these changes
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

 

**Easy server commands and urls:**  


~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
tail -f /var/log/wildfly/console.log
service wildfly restart
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

 

phpmyadmin: http://[ip]:888  
wildfly console: http://[ip]:9990 (user=admin/passwd=admin / lokaal is het
passwd: admin)

Step 2: Developing the backend
------------------------------

**Add plugin in pom.xml to deploy to wildfly:**  
add to pom:  


~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
org.wildfly.plugins
wildfly-maven-plugin
1.0.2.Final 
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

om te deployen naar wildfly: mvn wildfly:deploy

**Authenticatie:**  
http://www.aschua.de/blog/pairing-angularjs-and-javaee-for-authentication/  
Let op!  
Nu bewaar in de token in een hashtable. Dit moet per sessie. En meer secure.  
Ook kan iedereen nu gewoon de header aanpassen en de authID (=userID) aanpassen.  
Ook controleer ik helemaal niet of de token wel klopt en of hij wel van de
originele browser afkomt.

**frontend en backend:**  
De frontend draait op poort 80 en de backend op poort 8080!  
De frontend kan dus op een apache of andere web server draaien terwijl de
backend op een application server draait!  
Ook kan de frontend een apart project zijn!

**AngularJS project maken:**  
(deze tutoriam lis goed: https://www.youtube.com/watch?v=gKiaLSJW5xI)  
Installatie:  
install NodeJS

 

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
# npm install --global yo bower grunt-cli
# yo --version && bower --version && grunt --version
# npm install --global generator-angular@0.9.2
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

 

**Maak nieuw project:**  


~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
# yo angular  (maak project zonder Compass en Bootstrap,geen idee wat dat is)
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

 

Pas bower aan zodat deze in de apps folder staan (anders kan Apache ze niet
vinden) edit .bowerrc :  


~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
{
"directory": "app\bower_components"
}
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

 

run daarna:

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
bower install
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

 

(nu wordt de bower folder in de apps gezet)

**Maak een nieuwe modules aan:**

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
yo angular:route game
yo angular:route team
yo angular:route competition
In dex index.html: maak links aan naar die pagina’s
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

 

**Voeg eerste angular testcode toe:**

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
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
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

 

**VOORBEELD:Voeg library toe aan project (hoeft niet want worden niet
gebruikt):**

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
bower search backbone
bower install backbone --save
bower list
bower update
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

 

Step 3: Developing the frontend
-------------------------------

 

 
