FROM java:8

ADD ./target/flight-service-1.3.1.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/flight-service-1.3.1.jar"]

EXPOSE 8083