FROM adoptopenjdk/openjdk15:alpine

ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY target/*.jar klasha-interview-assessment-solution.jar
EXPOSE 8080


CMD [ "java", "-jar", "klasha-interview-assessment-solution.jar"]