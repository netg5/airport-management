FROM openjdk:11-jdk
ENV TZ=Europe/Riga
ADD target/gateway.jar /home/gateway.jar
EXPOSE 8080:8080
ENV JAVA_OPTIONS="-Dspring.profiles.active=dev"
CMD java $JAVA_OPTIONS -jar /home/gateway.jar
