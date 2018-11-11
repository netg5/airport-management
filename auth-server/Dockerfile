FROM java:8

ADD ./target/auth-server-0.0.1-SNAPSHOT.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/auth-server-0.0.1-SNAPSHOT.jar"]

EXPOSE 8085