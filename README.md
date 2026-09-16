# 🐾 Sistema de Adoção de Animais

> API REST para cadastro e gerenciamento de animais disponíveis para adoção, desenvolvida com Java e Spring Boot.

![Status](https://img.shields.io/badge/status-em%20desenvolvimento-yellow)
![Java](https://img.shields.io/badge/Java-21+-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-framework-6DB33F?logo=springboot)
![IntelliJ IDEA](https://img.shields.io/badge/IDE-IntelliJ%20IDEA-000000?logo=intellijidea)

---

## 📖 Sobre o projeto

Este projeto foi desenvolvido como **prova prática** da disciplina de `<!-- NOME DA DISCIPLINA -->` na **Univassouras**.

O objetivo é construir um sistema para **cadastro e gerenciamento de animais disponíveis para adoção**, permitindo cadastrar, consultar, atualizar e excluir animais por meio de uma **API REST**.

A atividade coloca em prática conhecimentos de:

- Linguagem **Java**
- **Programação Orientada a Objetos (POO)**
- Framework **Spring Boot**
- Desenvolvimento de **APIs REST**

---

## ✨ Funcionalidades

- [ ] Cadastrar um animal
- [ ] Listar todos os animais
- [ ] Consultar um animal pelo ID
- [ ] Atualizar os dados de um animal
- [ ] Excluir um animal

---

## 🛠️ Tecnologias utilizadas

| Tecnologia | Finalidade |
|---|---|
| **Java 21+** | Linguagem de programação |
| **Spring Boot** | Framework para criação da aplicação |
| **Spring Web** | Criação dos endpoints REST |
| **Spring Data JPA** | Persistência de dados |
| **H2 Database** | Banco de dados em memória <!-- ou MySQL/PostgreSQL --> |
| **Bean Validation** | Validação dos dados de entrada |
| **Maven** | Gerenciamento de dependências |
| **IntelliJ IDEA** | Ambiente de desenvolvimento |
| **Postman / Insomnia** | Testes dos endpoints |

> 💡 O projeto pode ser gerado em [start.spring.io](https://start.spring.io) com as dependências: *Spring Web*, *Spring Data JPA*, *H2 Database* e *Validation*.

---

**Fluxo de uma requisição:**

```
Cliente (Postman) → Controller → Service → Repository → Banco de dados
```

---

## ▶️ Como executar

### Pré-requisitos

- [JDK 21+](https://adoptium.net/) instalado
- [IntelliJ IDEA](https://www.jetbrains.com/idea/)
- [Postman](https://www.postman.com/) ou [Insomnia](https://insomnia.rest/) para testar os endpoints

### Passo a passo

1. **Clone o repositório**

   ```bash
   git clone <!-- URL DO REPOSITÓRIO -->
   cd adocao-animais
   ```

2. **Abra no IntelliJ IDEA**

   `File` → `Open` → selecione a pasta do projeto e aguarde o Maven baixar as dependências.

3. **Execute a aplicação**

   Rode a classe `AdocaoAnimaisApplication` pelo IntelliJ **ou** pelo terminal:

   ```bash
   ./mvnw spring-boot:run        # Linux/macOS
   mvnw.cmd spring-boot:run      # Windows
   ```

4. **Acesse a API** em `http://localhost:8080/animais`

### Configuração do banco H2 (opcional)

No arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:h2:mem:adocaodb
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
spring.jpa.show-sql=true
```

Console do H2: `http://localhost:8080/h2-console`

---

## 👥 Integrantes

| Nome | Matrícula | GitHub |
|---|---|---|
| Luis Filipe Soares Lima | 202313575 | lfslDEV |
| Maria Eduarda Franklin Barbosa | 202310532 | Tenko404 |

---

## 📄 Licença

Projeto desenvolvido para fins **acadêmicos** — Univassouras.
