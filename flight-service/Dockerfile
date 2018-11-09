FROM java:8-jre

ADD ./target/flight-service-0.0.1-SNAPSHOT.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/flight-service-0.0.1-SNAPSHOT.jar"]

RUN curl -L -o /mysql-connector-java-5.1.47.jar https://repo1.maven.org/maven2/mysql/mysql-connector-java/5.1.47/mysql-connector-java-5.1.47.jar
ENV CLASSPATH=/mysql-connector-java-5.1.47.jar:${CLASSPATH}

EXPOSE 8085