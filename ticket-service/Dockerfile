FROM java:8

ADD ./target/ticket-service-0.0.1-SNAPSHOT.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/ticket-service-0.0.1-SNAPSHOT.jar"]

EXPOSE 8083