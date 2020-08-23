FROM java:8-jdk-alpine

ARG JAR_FILE=target/threadPoolTest.jar
ARG JAR_LIB_FILE=target/lib/

WORKDIR /usr/local/runme

# copy target/*.jar /usr/local/runme/app.jar
COPY ${JAR_FILE} app.jar

# copy project dependencies
# cp -rf target/lib/  /usr/local/runme/lib
ADD ${JAR_LIB_FILE} lib/

# Expose server socket port
EXPOSE 1234

# java -jar /usr/local/runme/app.jar
# deay entry so we can start container with an interactive shell.
#ENTRYPOINT ["java","-jar","app.jar"]