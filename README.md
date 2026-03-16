![image.png](src/main/resources/static/images/image.png)

# Projekt komunikatora

## 1. Opis

Projekt zawiera podstawową strukturę komunikatora webowego opartego na frameworku Spring Boot 3.

Docelowo aplikacja będzie wdrażana na klastrze Kubernetes z wykorzystaniem Apache Kafka jako szyny wiadomości.

## 2. Zadania do wykonania dla studentów

### 2.1. Implementacja konfiguracji Spring Security
Implementacja konfiguracji Spring Security w klasie `SecurityConfig`.

### 2.2. Implementacja logiki w kontrolerze
Implementacja logiki w klasie `MainController`.

### 2.3. Implementacja logiki obsługi wiadomości z Kafki
Implementacja logiki w klasie `ChatKafkaListener`.

### 2.4. Implementacja logiki serwisowej
Implementacja logiki w klasie `MessageService`.

## 3. Uruchomienie aplikacji

### 3.1. Wymagania
Do uruchomienia projektu potrzebne są:

- Java 17
- Docker Desktop
- Maven Wrapper

### 3.2. Uruchomienie Apache Kafka
Najpierw należy uruchomić Docker Desktop, a następnie w katalogu projektu uruchomić Kafkę:

```
docker compose up -d
```

### 3.3. Uruchomienie aplikacji Spring Boot

Po uruchomieniu Kafki należy uruchomić aplikację:

```
./mvnw spring-boot:run
```

### 3.4. Uruchomienie z pliku JAR

```
./mvnw clean package -DskipTests
java -jar target/pjait-sii-chatroom-0.0.1-SNAPSHOT.jar
```

### 3.5. Dostęp do aplikacji

Po uruchomieniu aplikacja będzie dostępna pod adresem:

```
http://localhost:8080
```
