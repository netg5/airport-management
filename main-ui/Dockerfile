FROM openjdk:8-jdk-alpine
ENV TZ=Europe/Riga
ADD target/main-ui.jar /home/main-ui.jar
EXPOSE 9097:9097
ENV JAVA_OPTIONS="-Dspring.profiles.active=dev"
CMD java $JAVA_OPTIONS -jar /home/main-ui.jar
