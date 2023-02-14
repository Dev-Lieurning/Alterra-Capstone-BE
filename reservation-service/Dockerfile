FROM --platform=linux/amd64 openjdk:17
WORKDIR /app
COPY build/libs/reservation-0.0.1-SNAPSHOT.jar /app
ENTRYPOINT ["java", "-jar","reservation-0.0.1-SNAPSHOT.jar"]