FROM openjdk:8-jdk-alpine
ENV TZ=Europe/Riga
ADD target/reports.jar /home/reports.jar
EXPOSE 9091:9091
ENV JAVA_OPTIONS="-Dspring.profiles.active=dev"
CMD java $JAVA_OPTIONS -jar /home/reports.jar
