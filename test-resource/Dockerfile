FROM java:8

ADD ./target/test-resources-0.0.1-SNAPSHOT.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/test-resources-0.0.1-SNAPSHOT.jar"]

EXPOSE 8084