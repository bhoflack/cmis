FROM java:8

ADD target/cmis-2.0.0-SNAPSHOT-standalone.jar /app/

ENTRYPOINT ["java", "-jar", "/app/cmis-2.0.0-SNAPSHOT-standalone.jar"]
