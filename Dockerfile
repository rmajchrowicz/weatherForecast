# Wybieramy obraz z Javy (OpenJDK) jako podstawę
FROM openjdk:17-jdk-slim

# Ustawiamy katalog roboczy w kontenerze
WORKDIR /app

# Kopiujemy plik JAR aplikacji do kontenera
COPY target/weatherForecast-0.0.1-SNAPSHOT.jar app.jar

# Ustawiamy port, na którym aplikacja będzie działać
EXPOSE 8080

# Uruchamiamy aplikację Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
