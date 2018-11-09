FROM java:8

ADD ./target/eureka-service-0.0.1-SNAPSHOT.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/eureka-service-0.0.1-SNAPSHOT.jar"]

EXPOSE 8762