FROM java:8

ADD ./target/zipkin-service-0.0.1-SNAPSHOT.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/zipkin-service-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080