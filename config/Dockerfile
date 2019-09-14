FROM openjdk:8-jdk-alpine
ENV TZ=Europe/Riga
ADD target/config.jar /home/config.jar
EXPOSE 8888:8888
ENV JAVA_OPTIONS=""
CMD java $JAVA_OPTIONS -jar /home/config.jar
