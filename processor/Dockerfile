FROM openjdk:8-jdk-alpine
ENV TZ=Europe/Riga
ADD target/template.jar /home/processor.jar
EXPOSE 8086:8086
ENV JAVA_OPTIONS=""
CMD java $JAVA_OPTIONS -jar /home/processor.jar