FROM openjdk:8-jdk-alpine
ENV TZ=Europe/Riga
ADD target/payment.jar /home/payment.jar
EXPOSE 8080:8080
ENV JAVA_OPTIONS="-Dspring.profiles.active=dev"
CMD java $JAVA_OPTIONS -jar /home/payment.jar