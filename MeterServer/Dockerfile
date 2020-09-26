FROM java:8-jdk-alpine

ARG JAR_FILE=target/ZodicomMeterServer.jar
ARG JAR_LIB_FILE=target/lib/
ARG RESOURCES_FILE=/src/main/resources/serversocket.properties

WORKDIR /usr/local/runme

# copy target/*.jar /usr/local/runme/app.jar
COPY ${JAR_FILE} app.jar
COPY ${RESOURCES_FILE} serversocket.properties

# copy project dependencies
# cp -rf target/lib/  /usr/local/runme/lib
ADD ${JAR_LIB_FILE} lib/

# Expose server socket port
EXPOSE 1234

# java -jar /usr/local/runme/app.jar
#ENTRYPOINT ["java","-jar","app.jar"]