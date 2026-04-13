FROM tomcat:10.1

COPY auth-service/target/auth-service.war /usr/local/tomcat/webapps/

COPY auth-service/target/auth-service/WEB-INF/lib/mysql-connector-j-8.4.0.jar /usr/local/tomcat/lib/