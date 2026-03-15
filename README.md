![image.png](src/main/resources/static/images/image.png)

# Projekt komunikatora

## 1. Opis

Projekt zawiera podstawową strukturę komunikatora webowego opartego na frameworku Spring Boot 3.

Docelowo aplikacja będzie wdrażana na klastrze Kubernetes z wykorzystaniem Apache Kafka jako szyny wiadomości.

Projekt zawiera dwa podstawowe branche:
- `starting_branch` - branch startowy
- `final_branch` - branch z gotowym rozwiązaniem do weryfikacji zadań

## 2. Zadania do wykonania dla studentów

### 2.1. Implementacja konfiguracji Spring Security
Implementacja konfiguracji Spring Security w klasie `SecurityConfig`.

### 2.2. Implementacja logiki w kontrolerze
Implementacja logiki w klasie `MainController`.

### 2.3. Implementacja logiki obsługi wiadomości z Kafki
Implementacja logiki w klasie `ChatKafkaListener`.

### 2.4. Implementacja logiki serwisowej
Implementacja logiki w klasie `MessageService`.
