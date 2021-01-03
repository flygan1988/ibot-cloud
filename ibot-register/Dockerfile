FROM openjdk:8u212-jre
MAINTAINER FeiGan ganfei@tpl.taiping.com

COPY target/ibot-register-1.0-SNAPSHOT.jar /ibot/ibot-register-1.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-Xmx256m", "-jar", "/ibot/ibot-register-1.0-SNAPSHOT.jar"]