FROM openjdk:8-jdk-alpine
ENV TZ=Europe/Riga
ADD target/reservation.jar /home/reservation.jar
EXPOSE 9092:9092
ENV JAVA_OPTIONS="-Dspring.profiles.active=dev"
CMD java $JAVA_OPTIONS -jar /home/reservation.jar