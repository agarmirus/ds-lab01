FROM openjdk:17-oracle

USER root

COPY ./config.json /config.json
COPY ./target/ds-lab01-1.0-SNAPSHOT-jar-with-dependencies.jar /httpserv/ds-lab01-1.0-SNAPSHOT-jar-with-dependencies.jar

ENTRYPOINT ["java", "-jar", "./httpserv/ds-lab01-1.0-SNAPSHOT-jar-with-dependencies.jar"]
