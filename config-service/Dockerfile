FROM java:8

ADD ./target/config-service-1.0.0.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/config-service-1.0.0.jar"]

EXPOSE 8079