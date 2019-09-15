FROM openjdk:8-jdk-alpine
ENV TZ=Europe/Riga
ADD target/gateway.jar /home/gateway.jar
EXPOSE 8080:8080
ENV JAVA_OPTIONS=""
CMD java $JAVA_OPTIONS -jar /home/gateway.jar
