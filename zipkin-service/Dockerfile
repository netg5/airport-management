FROM java:8

ADD ./target/zipkin-service-1.0.0.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/zipkin-service-1.0.0.jar"]

EXPOSE 9411