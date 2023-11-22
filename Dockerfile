FROM tomcat:10.1.10-jdk17-temurin

COPY target/todo-list.war /usr/local/tomcat/webapps/