FROM openjdk:11-jdk
ENV TZ=Europe/Riga
ADD target/orchestration.jar /home/orchestration.jar
EXPOSE 9093:9093
ENV JAVA_OPTIONS=""
CMD java $JAVA_OPTIONS -jar /home/orchestration.jar
