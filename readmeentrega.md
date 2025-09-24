# Refactor Código Clinica-Api-Starter 

API REST para **Paciente**, **Médico** e **Consulta** com Spring Boot 3, JPA/Hibernate, **Flyway** e doc **OpenAPI/Swagger**.

## Stack
- Java 21 • Spring Boot 3.3 • Spring Web, Data JPA, Validation
- **PostgreSQL** (via Docker)
- Flyway (migrations)
- springdoc-openapi
- Lombok

## Requisitos
- JDK 21
- Docker Desktop
- IntelliJ IDEA (ou Maven)

## Subindo o banco (Docker)
```powershell
docker run --name pg-clinica -e POSTGRES_DB=clinica -e POSTGRES_USER=clinica -e POSTGRES_PASSWORD=secret -p 5432:5432 -d postgres:15
```

## Rodando a API
IntelliJ → Maven (Tool Window) → Plugins → spring-boot → spring-boot:run

ou, no terminal:

```
mvn spring-boot:run
```

## Swagger
**UI**: http://localhost:8080/swagger-ui.html

**OpenAPI**: http://localhost:8080/v3/api-docs

## Endpoints
### Pacientes
**POST /pacientes — cria**
```
{"nome":"Ana","cpf":"12345678901","email":"ana@example.com"}
```
201 Created + Location: /pacientes/{id}

**GET /pacientes — lista paginada (page, size, sort)**

## Médicos
**POST /medicos — cria**
```
{"nome":"Dr. João","crm":"SP-12345"}
```
201 Created + Location: /medicos/{id}

**GET /medicos — lista paginada**

## Consultas
**POST /consultas — agenda**
```
{"pacienteId":1,"medicoId":1,"dataHora":"2025-10-01T14:00:00"}
```
200 OK retorna id da consulta (segunda tentativa no mesmo horário → erro 422 com mensagem).

Melhoria aplicada: bloqueio de overbooking (mesmo médico, mesmo horário).

**GET /consultas — lista paginada**

?medicoId={id} opcional para filtrar por médico

## Evoluções sugeridas:

 - Cancelamento de consulta com motivo + trilha de auditoria.

 - Filtros de busca nos endpoints (/pacientes?nome=..., /medicos?crm=...).

 - Bloqueio de overbooking (mesmo médico, mesmo horário).