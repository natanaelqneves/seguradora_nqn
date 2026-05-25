# 🛡️ Seguradora-NQN

[![Java](https://img.shields.io/badge/Java-17%2B-orange?style=for-the-badge&logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=for-the-badge&logo=spring-boot)](https://spring.io/projects/spring-boot)
[![Docker](https://img.shields.io/badge/Docker-24%2B-blue?style=for-the-badge&logo=docker)](https://www.docker.com/)
[![JUnit 5](https://img.shields.io/badge/JUnit-5-red?style=for-the-badge&logo=junit5)](https://junit.org/junit5/)
[![SQL Server](https://img.shields.io/badge/SQL%20Server-2022-red?style=for-the-badge&logo=microsoft-sql-server)](https://www.microsoft.com/sql-server)

Uma API RESTful robusta e de alta performance para o mercado de seguros, desenvolvida com o ecossistema **Java** e **Spring Boot**. O sistema foi projetado sob os princípios de **Clean Code**, **Arquitetura Defensiva** e isolamento completo entre as camadas de apresentação, negócio e persistência.

A aplicação gerencia o ciclo de vida de clientes (Segurados) e a emissão de contratos (Apólices), integrando validações matemáticas complexas, tratamento rigoroso de linhas temporais de vigência e infraestrutura conteinerizada para implantação simplificada.

---

## 🏗️ Arquitetura do Sistema e Infraestrutura

O projeto adota uma infraestrutura moderna onde a persistência de dados foi isolada em um contêiner Docker rodando a versão estável do Microsoft SQL Server 2022.

[ Camada Web: Controllers ]
          │ (Transfere dados via DTOs Imutáveis)
          ▼
[ Camada de Negócio: Services ] <─── Validações Defensivas (Regras de Negócio)
          │ (Manipula Entidades JPA)
          ▼
[ Camada de Persistência: Repositories ]
          │ (Mapeamento ORM / Queries)
          ▼
[ Banco de Dados: SQL Server ]

### 1. Camada de Apresentação (`Controller`)
* Expõe os endpoints REST da API (`/api/v1/segurados` e `/api/v1/apolices`).
* Integra o mapeamento de formatos customizados de data (ex: `dd/MM/yyyy`) via Jackson para comunicação JSON padronizada.

### 2. Camada de Negócio e Arquitetura Defensiva (`Service`)
* **Isolamento de Entidades:** As classes JPA (`Segurado` e `Apolice`) nunca são expostas diretamente na Web.
* **Uso de Java Records:** Todo o tráfego de entrada (`RequestDTO`) e saída (`ResponseDTO`) é controlado por estruturas de dados imutáveis nativas do Java 17.
* **Ocultação de IDs:** As chaves primárias autoincrementais do banco de dados são omitidas dos payloads públicos de resposta, mitigando riscos de segurança como IDOR.

### 3. Infraestrutura Conteinerizada (`Docker`)
O banco de dados é inicializado de forma isolada e resiliente. O arquivo `docker-compose.yml` gerencia o ciclo de vida do serviço, garantindo a persistência das tabelas através de volumes mapeados.

---

## 🎯 Regras de Negócio e Validações Críticas

### 👥 Domínio de Segurados
* **Unicidade de Registro:** Validação estrita na base de dados antes de persistir um novo segurado, impedindo o cadastro de CPFs duplicados.

### 📄 Domínio de Apólices
* **Vínculo Obrigatório:** Uma apólice só pode ser emitida se estiver associada a um segurado válido e previamente cadastrado.
* **Consistência Cronológica:** Motor de regras temporal que impede a gravação de contratos cujo início de vigência seja posterior à data de término.
* **Precisão Financeira (`BigDecimal`):** Todos os cálculos de Prêmio e Cobertura utilizam `BigDecimal` e validações estritas via `.compareTo()`. O sistema barra contratos onde o valor da cobertura seja menor ou igual ao prêmio cobrado.

---

## 🧪 Estratégia de Testes Automatizados

A estabilidade do core do sistema é garantida por uma suíte de **Testes Unitários de Alta Velocidade** implementada com **JUnit 5** e **Mockito**. O foco foi o isolamento completo da lógica de negócio, eliminando a dependência de infraestrutura externa (Mocks).

[100% VERDE - 5/5 testes executados com sucesso]
│
├── 🎯 ApoliceServiceTest
│   ├── ✅ Deve barrar a emissão se a cobertura for menor ou igual ao prêmio cobrado. (Precisão Financeira)
│   ├── ✅ Deve emitir uma apólice com sucesso sob condições válidas. (Fluxo Principal)
│   └── ✅ Deve barrar a emissão se a data de início for posterior ao fim de vigência. (Consistência Temporal)
│
└── 🎯 SeguradoServiceTest
├── ✅ Deve cadastrar um segurado com sucesso quando o CPF for inédito.
└── ✅ Deve lançar exceção ao tentar cadastrar segurado com CPF duplicado. (Unicidade)


---

## 🛠️ Configuração do Ambiente e Arquivos Base

O projeto utiliza injeção dinâmica de propriedades. A senha e as credenciais do banco de dados podem ser definidas através de variáveis de ambiente do sistema operacional para manter a segurança das credenciais, possuindo valores padrão para o ambiente de desenvolvimento local.

### 🐳 1. Docker Compose (`docker-compose.yml`)
```yaml
version: '3.8'

services:
  db:
    image: [mcr.microsoft.com/mssql/server:2022-latest](https://mcr.microsoft.com/mssql/server:2022-latest)
    container_name: mssql_prod
    restart: always
    environment:
      ACCEPT_EULA: "Y"
      MSSQL_SA_PASSWORD: "${DB_PASSWORD:123456}"
    ports:
      - "1433:1433"
    volumes:
      - mssql_seguros_data:/var/opt/mssql

volumes:
  mssql_seguros_data:
📝 2. Propriedades do Spring (application.properties)
Properties
spring.application.name=Seguradora-NQN

# URL de Conexão com Fallback Dinâmico
spring.datasource.url=${DB_URL:jdbc:sqlserver://localhost:1433;databaseName=db_seguradora_nqn;encrypt=false;trustServerCertificate=true}

# Credenciais gerenciadas por variáveis de ambiente
spring.datasource.username=${DB_USER:sa}
spring.datasource.password=${DB_PASSWORD:123456}
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver

# Configurações do Hibernate JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.SQLServerDialect

# Inicialização automática do catálogo/schema caso ele não exista no SQL Server
spring.jpa.properties.hibernate.javax.persistence.schema-generation.create-database-schemas=true
🚀 Como Executar o Projeto
Clonar o Repositório:

Bash
git clone [https://github.com/seu-usuario/Seguradora-NQN.git](https://github.com/seu-usuario/Seguradora-NQN.git)
cd Seguradora-NQN
Subir a Infraestrutura de Banco de Dados (Docker):
Execute o comando para provisionar o container do SQL Server 2022 em segundo plano:

Bash
docker-compose up -d
Compilar e Executar a API Spring Boot:

Bash
./mvnw spring-boot:run
Executar a Suíte de Testes Unitários:

Bash
./mvnw test
