# FROM --platform=linux/amd64 openjdk:17
# ARG JAR_FILE=build/libs/*jar
# COPY ${JAR_FILE} auth-0.0.1-SNAPSHOT.jar
# ENTRYPOINT ["java", "-jar","/auth-0.0.1-SNAPSHOT.jar"]

FROM --platform=linux/amd64 openjdk:17
# ARG JAR_FILE=build/libs/*jar
WORKDIR /app
COPY build/libs/auth-0.0.1-SNAPSHOT.jar /app
ENTRYPOINT ["java", "-jar","auth-0.0.1-SNAPSHOT.jar"]