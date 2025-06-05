# Projeto - Lista de Aquecimento (POO)

Este repositório contém a implementação da lista de aquecimento para o projeto da disciplina de **Programação Orientada a Objetos (POO)** do curso de Análise e Desenvolvimento de Sistemas - IFPB, Campus Santa Rita.

## 👥 Equipe
- Gabriel Souza
- Thalys 
- Maylson Silva

---

## 📌 Descrição do Projeto

O projeto é um sistema simples de cadastro de tarefas, que simula uma central de informações com as seguintes funcionalidades:

- Cadastro de tarefas com título, descrição e data limite (deadline)
- Armazenamento das tarefas em uma lista (`ArrayList`)
- Persistência dos dados em arquivo XML usando a biblioteca XStream
- Geração de relatórios em PDF para tarefas de um dia específico (usando iText)
- Envio de e-mails com as tarefas do dia atual em anexo (usando JavaMail)

A aplicação roda no terminal (modo texto), com um menu interativo.

---
## 📁 Estrutura do Projeto

projeto-poo/
│
├── src/
│ ├── modelo/ # Classes Tarefa e CentralDeInformacoes
│ ├── persistencia/ # Classe Persistencia (XStream)
│ ├── relatorios/ # Classe GeradorDeRelatorios (iText)
│ ├── email/ # Classe Mensageiro (JavaMail)
│ └── Main.java # Menu principal e interação com o usuário
│
├── .gitignore
├── README.md
└── central.xml # Arquivo XML com as tarefas (gerado pelo sistema)

---

## 🧪 Requisitos

- Java 8 ou superior
- XStream (persistência em XML)
- iText (geração de PDF)
- JavaMail (envio de e-mails)
- Biblioteca JARs devem ser adicionadas ao classpath do projeto
