FROM java:8

ADD ./target/auth-service-1.3.1.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/auth-service-1.3.1.jar"]

EXPOSE 8082