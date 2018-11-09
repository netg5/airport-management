FROM java:8

ADD ./target/zuul-gateway-0.0.1-SNAPSHOT.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/zuul-gateway-0.0.1-SNAPSHOT.jar"]

EXPOSE 8762