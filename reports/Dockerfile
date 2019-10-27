FROM openjdk:11-jdk
ENV TZ=Europe/Riga
ADD target/reports.jar /home/reports.jar
EXPOSE 8080:8080
ENV JAVA_OPTIONS="-Dspring.profiles.active=prod"
CMD java $JAVA_OPTIONS -jar /home/reports.jar