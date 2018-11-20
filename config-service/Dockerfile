FROM java:8

ADD ./target/config-service-0.0.1-SNAPSHOT.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/config-service-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080