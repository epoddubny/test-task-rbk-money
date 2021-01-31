FROM adoptopenjdk/openjdk11:alpine-jre

ARG JAR_FILE=target/test-task-rbkmoney.jar

WORKDIR /opt/app

COPY ${JAR_FILE} app.jar

CMD exec java -jar app.jar $JAVA_OPTS