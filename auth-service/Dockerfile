FROM java:8

ADD ./target/auth-service-0.0.1-SNAPSHOT.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/auth-service-0.0.1-SNAPSHOT.jar"]

EXPOSE 8082