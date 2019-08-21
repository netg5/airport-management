FROM openjdk:8-jdk-alpine
ENV TZ=Europe/Riga
ADD target/cargo.jar /home/cargo.jar
EXPOSE 8098:8098
ENV JAVA_OPTIONS=""
CMD java $JAVA_OPTIONS -jar /home/cargo.jar