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
