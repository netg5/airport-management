FROM openjdk:8-jdk-alpine
ENV TZ=Europe/Riga
ADD target/registry.jar /home/registry.jar
EXPOSE 8761:8761
ENV JAVA_OPTIONS=""
CMD java $JAVA_OPTIONS -jar /home/registry.jar
