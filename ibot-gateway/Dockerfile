FROM openjdk:8u212-jre
MAINTAINER FeiGan ganfei@tpl.taiping.com

COPY target/ibot-gateway-0.0.1-SNAPSHOT.jar /ibot/ibot-gateway-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-Xmx256m", "-jar", "/ibot/ibot-gateway-0.0.1-SNAPSHOT.jar"]