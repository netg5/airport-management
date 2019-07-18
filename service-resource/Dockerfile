FROM openjdk:8-jdk-alpine
ENV TZ=Europe/Riga
ADD target/service-resource.jar /home/service-resource.jar
EXPOSE 9096:9096
ENV JAVA_OPTIONS="-Dspring.profiles.active=dev"
CMD java $JAVA_OPTIONS -jar /home/service-resource.jar