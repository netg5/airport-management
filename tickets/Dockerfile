FROM openjdk:8-jdk-alpine
ENV TZ=Europe/Riga
ADD target/tickets.jar /home/tickets.jar
EXPOSE 9093:9093
ENV JAVA_OPTIONS="-"
CMD java $JAVA_OPTIONS -jar /home/tickets.jar