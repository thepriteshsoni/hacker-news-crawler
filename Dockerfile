###############################################################
#                         BUILD STAGE                         #
###############################################################

FROM maven:3.6.3-jdk-8 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

###############################################################
#                         PACKAGE STAGE                       #
###############################################################

FROM openjdk:8-jdk-alpine
COPY --from=build /home/app/target/*.jar /usr/local/lib/App.jar
ENTRYPOINT ["java","-jar","/usr/local/lib/App.jar"]

###############################################################
#                              END                            #
###############################################################