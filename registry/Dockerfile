FROM openjdk:11-jdk
ENV TZ=Europe/Riga
ADD target/registry.jar /home/registry.jar
EXPOSE 8761:8761
ENV JAVA_OPTIONS="-Dspring.profiles.active=dev"
CMD java $JAVA_OPTIONS -jar /home/registry.jar
