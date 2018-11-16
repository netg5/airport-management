FROM java:8

ADD ./target/doc-service-0.0.1-SNAPSHOT.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/doc-service-0.0.1-SNAPSHOT.jar"]

EXPOSE 8085