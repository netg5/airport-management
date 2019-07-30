FROM openjdk:8-jdk-alpine
ENV TZ=Europe/Riga
ADD target/manager.jar /home/manager.jar
EXPOSE 8088:8088
ENV JAVA_OPTIONS="-Dspring.profiles.active=dev"
CMD java $JAVA_OPTIONS -jar /home/manager.jar