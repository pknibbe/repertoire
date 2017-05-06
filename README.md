# repertoire
Enterprise Java project: music manager

This project is intended to provide a single, convenient place on the cloud
to access all of the music to which I have the rights. This site will
not give me a hard time about permissions or credit cards. It will allow me 
to upload music and share my music with family members to whom I give accounts.


### Project Technologies/Techniques 

* Security/Authentication
  * Admin role: create/read/update/delete (crud) of all data
  * User role: create/play/edit own information and playlists
* Database (MySQL and Hibernate) Tables for:
  * users
  * songs (links to locations)
  * song lists
  * messages 
  * song list sharing
* Web Services or APIs
  * MP3 player
* Logging
  * Configurable logging using Log4J. In production, only errors will 
  normally be logged, but logging at a debug level can be turned on to 
  facilitate trouble-shooting. 
* Site and database hosted on AWS
* Unit Testing
  * JUnit tests to achieve 80% code coverage of persistence classes
* Independent Research Topic
  * Pure CSS for web pages

### Design

* [Screen Design](https://github.com/pknibbe/repertoire/tree/master/Images)
* [Application Flow](https://github.com/pknibbe/repertoire/tree/master/DesignDocuments/applicationFlow.md)
* [util.Database Design](https://github.com/pknibbe/repertoire/tree/master/Images/Database.png)

### [Project Plan](https://github.com/pknibbe/repertoire/tree/master/Planning/ProjectPlan.md)

### [Time Log](https://github.com/pknibbe/repertoire/tree/master/TimeLog.md)
