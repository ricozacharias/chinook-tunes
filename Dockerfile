FROM openjdk:16
ADD target/ChinookTunes-0.0.1-SNAPSHOT.jar ChinookTunes.jar
ENTRYPOINT [ "java", "-jar", "ChinookTunes.jar" ]
