FROM openjdk:11-jdk
ENV TZ=Europe/Riga
ADD target/auth.jar /home/auth.jar
EXPOSE 8082:8082
ENV JAVA_OPTIONS="-Dspring.profiles.active=prod"
CMD java $JAVA_OPTIONS -jar /home/auth.jar