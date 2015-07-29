# dynamodb-cookbook
This repository contains the source code for applications discussed in book DynamoDB Cookbook

To get started, please download the folders addressbook-local-tomcat and addressbook-elasticbeanstalk. 
Each contains code to be built and deployed on local tomcat and AWS ElasticBeanstalk.

To get this examples working, you would need create two tables in DynamoDB 
1. user ( Hash Key - email)
2. contact ( Hash Key - id and Range Key - parentId)

You woudl also need to updated AwsCredentials.properties file in addressbook-services src/main/resources folder. 

To build either of the project, please execute following command

cd addressbook
mvn clean install 

This will build a war file called addressbook.war in target folder of addressbook-controller project. Deploy it on Tomcat or AWS 
ElasticBeanstalk and you shoudl be able to see the application working. 

For tomcat hit URL - http://localhost:8080/addressbook
For ElasticBeanstalk  http://<elasticbeanstalk-app-id>.elasticbeanstalk.com

