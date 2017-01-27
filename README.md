# repertoire
Enterprise Java project: music manager

Wanting a single, convenient place on the cloud to access all of the music to which I have rights
by way of purchase, service permissions, or because it is publicly available on the web. This site will
not give me a hard time about permissions or credit cards. It will allow me to upload music or music links
and share my music with family members to whom I will give accounts.


### Project Technologies/Techniques 

* Security/Authentication
  * Admin role: create/read/update/delete (crud) of all data
  * User role: create/play/edit own information and playlists
  * All: May create a list of publicly available music for unknown visitors
* Database (MySQL and Hibernate)
  * Store users and roles
  * Store playlist information
  * May store login credentials for other services
* Web Services or APIs
  * MyTunes
  * Amazon Prime
  * May include capability to send gmail from repertoire account
* Bootstrap?
* Logging
  * Configurable logging using Log4J. In production, only errors will normally be logged, but logging 
     at a debug level can be turned on to facilitate trouble-shooting. 
* Site and database hosted on AWS
* Jenkins for Continuous Integration
* Unit Testing
  * JUnit tests to achieve 80% code coverage
* Independent Research Topic
  * Automating access to places like Amazon Prime music streaming?

### Design - these are still copies from sample

* [Screen Design](DesignDocuments/Screens.md)
* [Application Flow](DesignDocuments/applicationFlow.md)
* [Database Design](DesignDocuments/databaseDiagram.png)

### [Project Plan](ProjectPlan.md)

### [Time Log](TimeLog.md)
