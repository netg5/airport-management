FROM java:8

ADD ./target/eureka-service-1.0.0.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/eureka-service-1.0.0.jar"]

EXPOSE 8761