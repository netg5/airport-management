FROM openjdk:8-jdk-alpine
ENV TZ=Europe/Riga
ADD target/booking.jar /home/booking.jar
EXPOSE 9092:9092
ENV JAVA_OPTIONS=""
CMD java $JAVA_OPTIONS -jar /home/booking.jar