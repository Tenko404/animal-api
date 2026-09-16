# Animais API

API REST para cadastro e gerenciamento de animais disponíveis para adoção, desenvolvida em **Java** com **Spring Boot**.

Prova prática de Engenharia de Software — Universidade de Vassouras.

## Tecnologias

Java 25 · Spring Boot 4 · Spring Web MVC · Spring Data JPA · Bean Validation · PostgreSQL · H2 · Docker Compose · Lombok · Maven

## Arquitetura

```
animais-api/src/main/java/com/lfsl/animais_api/
├── domain/              # Entidade Animal, regras de negócio e contrato do repositório
├── application/         # Casos de uso: criar, listar, buscar, atualizar e excluir
└── infrastructure/
    ├── http/            # Controller, DTOs e tratamento global de erros
    └── persistence/     # Entidade JPA e implementação do repositório
```

## Como executar

**Pré-requisitos:** JDK 25 e Docker (apenas para o PostgreSQL).

```bash
git clone https://github.com/Tenko404/animal-api.git
cd animal-api/animais-api
```

**Com PostgreSQL** (perfil padrão, banco em `localhost:5434`):

```bash
docker compose up -d
./mvnw spring-boot:run
```

**Com H2 em memória** (sem Docker):

```bash
./mvnw spring-boot:run "-Dspring-boot.run.profiles=h2"
```

Console do H2 em `http://localhost:8080/h2-console` (JDBC URL `jdbc:h2:mem:animaisdb`, usuário `sa`, sem senha).

No Windows, use `mvnw.cmd` no lugar de `./mvnw`. No IntelliJ, execute a classe `AnimaisApiApplication`; para usar o H2, defina a variável de ambiente `SPRING_PROFILES_ACTIVE=h2` na configuração de execução.

A API fica disponível em `http://localhost:8080/animais`.

## Endpoints

| Método | Rota | Descrição | Respostas |
|---|---|---|---|
| `POST` | `/animais` | Cadastrar animal | `201` · `400` |
| `GET` | `/animais` | Listar todos | `200` |
| `GET` | `/animais/{id}` | Buscar por ID | `200` · `400` · `404` |
| `PUT` | `/animais/{id}` | Atualizar animal | `200` · `400` · `404` |
| `DELETE` | `/animais/{id}` | Excluir animal | `204` · `404` |

O `id` é um **UUID** gerado pela API. O `POST` retorna o header `Location` com a URL do animal criado.

**Cadastro (`POST`):** todo animal começa com status `DISPONIVEL`.

```json
{ "nome": "Rex", "especie": "Cachorro", "raca": "SRD", "idade": 3 }
```

**Atualização (`PUT`):** todos os campos são obrigatórios, incluindo o `status`.

```json
{ "nome": "Rex", "especie": "Cachorro", "raca": "SRD", "idade": 4, "status": "ADOTADO" }
```

**Regras de validação:**

| Campo | Regra |
|---|---|
| `nome` | Obrigatório, 2 a 100 caracteres |
| `especie` | Obrigatório, 2 a 50 caracteres |
| `raca` | Obrigatório, 2 a 50 caracteres |
| `idade` | Obrigatório, de 0 a 30 |
| `status` | `DISPONIVEL` ou `ADOTADO` (apenas no `PUT`) |

**Formato de erro** (o campo `errors` aparece só em falhas de validação):

```json
{
  "timestamp": "2026-09-16T12:00:00Z",
  "status": 400,
  "error": "Requisição inválida",
  "message": "Existem campos inválidos na requisição",
  "path": "/animais",
  "errors": [{ "field": "nome", "message": "O nome é obrigatório" }]
}
```

## Testes

A collection do Postman está na raiz do repositório: [`animais-api.postman_collection.json`](animais-api.postman_collection.json). Importe no Postman e rode a pasta **01** primeiro, pois ela define o `{{animalId}}` usado nas demais.

Caso a collection não seja importada corretamente, os mesmos testes podem ser feitos via `curl` com a API rodando. Os comandos usam sintaxe **bash** (Linux, macOS ou Git Bash no Windows) e devem ser executados em sequência no mesmo terminal. O `-i` exibe os headers da resposta, incluindo o status code e o `Location`.

```bash
API=http://localhost:8080/animais
```

### 01 — Fluxo principal

```bash
# 1. Criar → 201 Created + header Location (o ID fica salvo em $ID)
RESP=$(curl -si -X POST $API \
  -H "Content-Type: application/json" \
  -d '{"nome": "Rex", "especie": "Cachorro", "raca": "SRD", "idade": 3}')
echo "$RESP"
ID=$(echo "$RESP" | grep -o '"id":"[^"]*"' | cut -d'"' -f4)

# 2. Listar todos → 200 OK + array JSON
curl -i $API

# 3. Buscar por ID → 200 OK
curl -i $API/$ID

# 4. Atualizar (idade 4 e status ADOTADO) → 200 OK
curl -i -X PUT $API/$ID \
  -H "Content-Type: application/json" \
  -d '{"nome": "Rex", "especie": "Cachorro", "raca": "SRD", "idade": 4, "status": "ADOTADO"}'

# 5. Excluir → 204 No Content (corpo vazio)
curl -i -X DELETE $API/$ID

# 6. Confirmar exclusão → 404 Not Found
curl -i $API/$ID
```

### 02 — Validação (400 Bad Request)

```bash
# Nome vazio → errors listando "nome"
curl -i -X POST $API -H "Content-Type: application/json" \
  -d '{"nome": "", "especie": "Cachorro", "raca": "SRD", "idade": 3}'

# Sem idade → errors listando "idade"
curl -i -X POST $API -H "Content-Type: application/json" \
  -d '{"nome": "Fido", "especie": "Cachorro", "raca": "SRD"}'

# Idade negativa → errors listando "idade"
curl -i -X POST $API -H "Content-Type: application/json" \
  -d '{"nome": "Fido", "especie": "Cachorro", "raca": "SRD", "idade": -5}'

# Status inexistente
curl -i -X PUT $API/$ID -H "Content-Type: application/json" \
  -d '{"nome": "Rex", "especie": "Cachorro", "raca": "SRD", "idade": 4, "status": "FICOU_TRISTE"}'

# UUID malformado na URL
curl -i $API/abc-nao-e-uuid
```

### 03 — Não encontrado (404 Not Found)

```bash
# Buscar inexistente (sem stack trace na resposta)
curl -i $API/00000000-0000-0000-0000-000000000000

# Atualizar inexistente
curl -i -X PUT $API/00000000-0000-0000-0000-000000000000 \
  -H "Content-Type: application/json" \
  -d '{"nome": "Fantasma", "especie": "Cachorro", "raca": "SRD", "idade": 1, "status": "DISPONIVEL"}'

# Excluir inexistente
curl -i -X DELETE $API/00000000-0000-0000-0000-000000000000
```

## Integrantes

| Nome | Matrícula | GitHub |
|---|---|---|
| Luis Filipe Soares Lima | 202313575 | [@lfslDEV](https://github.com/lfslDEV) |
| Maria Eduarda Franklin Barbosa | 202310532 | [@Tenko404](https://github.com/Tenko404) |

---

Projeto desenvolvido para fins acadêmicos — Universidade de Vassouras.
