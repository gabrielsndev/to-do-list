# 🚀 Advanced ToDo List - Java Multi-Database

Este projeto é uma aplicação de gerenciamento de tarefas desenvolvida originalmente no 2º período de ADS (IFPB) e profundamente refatorada no 3º período para implementar arquiteturas avançadas de persistência e padrões de projeto.

## 🛠️ Evolução e Disciplinas
O projeto passou por um ciclo de melhoria contínua integrando conhecimentos de:
- **Padrões de Projeto:** Refatoração de código para garantir escalabilidade e manutenção.
- **Banco de Dados II:** Implementação de persistência poliglota (SQL e NoSQL).

## 🏗️ Arquitetura de Dados
Para cada necessidade do sistema, escolhemos a tecnologia mais eficiente:
- **H2 Database (SQL):** Responsável pelo armazenamento central das tarefas.
- **MongoDB (NoSQL):** Utilizado para o armazenamento flexível de subtarefas e metadados dinâmicos.
- **Redis:** Gerenciamento de sessões de usuário, garantindo alta performance e expiração automática.

## 🌟 Funcionalidades Principais
- **Sistema de Autenticação:** Login seguro com controle de sessão via Redis.
- **Gestão de Tarefas:** CRUD completo de tarefas (H2) e subtarefas (MongoDB).
- **Relatórios Automatizados:** Envio de relatórios de produtividade via E-mail.
- **Design Patterns Aplicados:** - *Factory/Singleton* para conexões com bancos.
    - *Strategy* para diferentes formas de notificação ou relatórios.
    - *DAO/Repository* para abstração da camada de dados.

## 🚀 Como Executar
1. Certifique-se de ter o **Java 17+** instalado.
2. É necessário ter instâncias do **MongoDB** e **Redis** rodando (recomendado via Docker).
3. Clone o repositório: `git clone https://github.com/gabrielsndev/to-do-list`
4. Configure as credenciais de e-mail no arquivo de propriedades.

## 👥 Contribuidores
- [Gabriel Souza]
- [Thalys Eduardo]
- [Maylson Silva]
