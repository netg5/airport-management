FROM java:8

ADD ./target/doc-service-1.0.0.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/doc-service-1.0.0.jar"]

EXPOSE 8085