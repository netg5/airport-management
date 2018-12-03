FROM java:8

ADD ./target/service-resource-1.0.0.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/service-resource-1.0.0.jar"]

EXPOSE 8084