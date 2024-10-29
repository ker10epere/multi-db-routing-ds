FROM maven:3-amazoncorretto-21

WORKDIR /app/

COPY pom.xml /app/
COPY src/ /app/src/

RUN mvn clean install -DskipTests

CMD java -jar target/*.jar
