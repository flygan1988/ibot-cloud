FROM openjdk:8u212-jre
MAINTAINER FeiGan ganfei@tpl.taiping.com

COPY target/ibot-server-system-1.0-SNAPSHOT.jar /ibot/ibot-server-system-1.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-Xmx256m", "-jar", "/ibot/ibot-server-system-1.0-SNAPSHOT.jar"]
