FROM openjdk:11-jdk
ENV TZ=Europe/Riga
ADD target/booking.jar /home/booking.jar
EXPOSE 9092:9092
ENV JAVA_OPTIONS="-Dspring.profiles.active=dev"
CMD java $JAVA_OPTIONS -jar /home/booking.jar