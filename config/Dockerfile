FROM openjdk:11-jdk
ENV TZ=Europe/Riga
ADD target/config.jar /home/config.jar
EXPOSE 8888:8888
ENV JAVA_OPTIONS="-Dspring.profiles.active=dev"
CMD java $JAVA_OPTIONS -jar /home/config.jar
