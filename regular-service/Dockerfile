FROM openjdk:8-jdk-alpine
ENV TZ=Europe/Riga
ADD target/regular-service.jar /home/regular-service.jar
EXPOSE 8086:8086
ENV JAVA_OPTIONS="-Dspring.profiles.active=dev"
CMD java $JAVA_OPTIONS -jar /home/regular-service.jar