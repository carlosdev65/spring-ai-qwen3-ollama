# Spring AI + Ollama + Qwen3:4b

Projeto mínimo para executar um LLM local usando:

- Java 25
- Spring Boot 4.1.1
- Spring AI 2.0.1
- Ollama
- Qwen3 8B (`qwen3:4b`)
- Maven
- REST API

## 1. Verificar Java

```bash
java -version
```

Deve ser Java 25.

## 2. Verificar Ollama

```bash
ollama --version
```

Se o Ollama já estiver instalado:

```bash
ollama pull qwen3:4b
ollama list
```

Teste diretamente no Ollama:

```bash
ollama run qwen3:4b
```

Depois:

```text
Explique o que é Spring AI em duas frases.
```

O Ollama normalmente atende em:

```text
http://localhost:11434
```

## 3. Compilar

Na raiz do projeto:

```bash
mvn clean test
```

Depois:

```bash
mvn spring-boot:run
```

Ou:

```bash
mvn clean package
java -jar target/spring-ai-qwen3-ollama-0.0.1-SNAPSHOT.jar
```

## 4. Testar a API

Em outro terminal:

```bash
curl "http://localhost:8080/ai/generate?message=Explique%20o%20que%20%C3%A9%20Java%2025"
```

Resposta esperada:

```json
{
  "generation": "..."
}
```

Também pode testar:

```bash
curl "http://localhost:8080/ai/generate?message=Escreva%20um%20exemplo%20simples%20de%20REST%20com%20Spring%20Boot"
```

## 5. Configuração para 16 GB

O projeto começa com:

```properties
spring.ai.ollama.chat.num-ctx=4096
spring.ai.ollama.chat.num-batch=256
spring.ai.ollama.chat.temperature=0.2
spring.ai.ollama.chat.think=false
spring.ai.ollama.chat.keep-alive=10m
```

A ideia é evitar consumir memória desnecessariamente no notebook.

Se houver bastante RAM disponível e você quiser respostas mais longas, experimente:

```properties
spring.ai.ollama.chat.num-ctx=8192
```

Se o notebook ficar pressionado por memória, volte para:

```properties
spring.ai.ollama.chat.num-ctx=2048
```

Não é necessário fixar `num-thread`: por padrão, o Ollama pode detectar automaticamente uma configuração adequada.

## 6. Thinking do Qwen3

Para uso interativo rápido, o projeto deixa:

```properties
spring.ai.ollama.chat.think=false
```

O Qwen3 suporta thinking. Para experimentar raciocínio, altere para:

```properties
spring.ai.ollama.chat.think=true
```

Isso pode aumentar o tempo e o consumo de recursos.

## 7. Estrutura

```text
spring-ai-qwen3-ollama/
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   ├── java/br/com/example/qwen3/
    │   │   ├── Qwen3Application.java
    │   │   └── ChatController.java
    │   └── resources/
    │       └── application.properties
    └── test/
        └── java/br/com/example/qwen3/
            └── Qwen3ApplicationTests.java
```

## 8. Arquitetura

```text
Cliente HTTP
     |
     v
Spring Boot 4.1.1
     |
     v
ChatController
     |
     v
Spring AI 2.0.1
     |
     v
Ollama HTTP API
localhost:11434
     |
     v
Qwen3:4b
     |
     v
Resposta
```

## Observação

O projeto não baixa o modelo automaticamente durante o startup. O modelo deve ser instalado uma vez com:

```bash
ollama pull qwen3:4b
```

Isso evita que o Spring Boot fique esperando um download grande quando a aplicação for iniciada.
