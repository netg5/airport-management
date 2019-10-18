FROM openjdk:11-jdk
ENV TZ=Europe/Riga
ADD target/tickets.jar /home/tickets.jar
EXPOSE 9093:9093
ENV JAVA_OPTIONS="-Dspring.profiles.active=dev"
CMD java $JAVA_OPTIONS -jar /home/tickets.jar