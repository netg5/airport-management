FROM openjdk:11-jdk
ENV TZ=Europe/Riga
ADD target/timetable.jar /home/timetable.jar
EXPOSE 8080:8080
ENV JAVA_OPTIONS=""
CMD java $JAVA_OPTIONS -jar /home/timetable.jar