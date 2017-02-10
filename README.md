# Web-Service-API-Java
There are two application included in the package one fetches live data stream periodically after every 5 seconds, pre processes it and stores it in SQLServer Database, the other one is Rest Api which is used for serving this data to the client Application. Both of these applications are written in java using eclipse and jersey framework libraries for Rest Api. The client used for consuming the Rest api was Postman-Chrome Extention. Rest Api was hosted on tomcat-7.0.75-windows-x64. 

Instructions to run:

Step 1: In the folder "DB Backup-SQLServer2008" there is zip file, extract it. You will get a "NYC.bak" file. Restore this backup file in SQLServer2008 R2 or higher. 

Step 2: Place the "config" folder in C Drive. Inside this folder is a text file namely "Config.txt", it contains the connection string for the SQLServer Database, configure this connection string according to your SQL settings.

Step 3: Inside "Data fetcher" you can find source code and an executable jar file. Running the jar file will start fetching data in background, or you can run through source code inside eclipse.

Step 4: Inside "Web Service" you can find source code and web application archive file "Restapi.war". Host this "war" file on tomcat server. 

Step 5: Refer to screen shots inside the folder named "Screen Shots" for the end point URLs for the rest api. 
