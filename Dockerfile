FROM tomcat:10.1

COPY auth-service/target/auth-service.war /usr/local/tomcat/webapps/