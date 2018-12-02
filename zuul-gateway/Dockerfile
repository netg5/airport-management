FROM java:8

ADD ./target/zuul-gateway-1.0.0.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/zuul-gateway-1.0.0.jar"]

EXPOSE 8080