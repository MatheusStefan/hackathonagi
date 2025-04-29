
# Estrutura de Pastas – Projeto Hackathon (Programa de Devs do Agi)

Este projeto foi desenvolvido como parte de um desafio de hackathon do programa de Devs do Agi. Abaixo está a estrutura de diretórios utilizada, baseada no padrão MVC (Model-View-Controller), amplamente adotado em aplicações Java utilizando o framework Spring.

## 📁 Estrutura de Pastas

```
src/main/java/
└── com/agibank/hackathon/
    ├── controller/
    │   ├── request/
    │   └── response/
    ├── entities/
    ├── enums/
    └── service/
```

---

## 📂 controller/

Responsável por receber e tratar as requisições HTTP. Atua como a "porta de entrada" da aplicação, direcionando chamadas para os serviços apropriados.

### 📂 request/

Contém as classes que representam os dados recebidos no corpo das requisições (payload). São usadas principalmente em métodos `POST`, `PUT`, etc.

Exemplo: `ColaboradorRequest.java`, `ColaboradorStatusRequest.java`

### 📂 response/

Contém as classes responsáveis por definir a estrutura dos dados que serão retornados nas respostas HTTP.

Exemplo: `ColaboradorResponse.java`, `ColaboradorStatusResponse.java`

---

## 📂 entities/

Contém as classes que representam as entidades do domínio da aplicação. Essas classes geralmente estão associadas a tabelas no banco de dados.

Exemplo: `Colaborador.java`, `Equipamento.java`

---

## 📂 enums/

Define os tipos enumerados utilizados em diferentes partes do sistema, promovendo clareza e restrição de valores.

Exemplo: `statusColaborador.java`, `statusEquipamento.java`

---

## 📂 service/

Contém a lógica de negócio da aplicação. Os serviços recebem chamadas dos controllers, processam os dados (com validações, regras de negócio, etc.) e retornam os resultados.

Exemplo: `ColaboradorService.java`, `EquipamentoService.java`

---

Essa estrutura modular e organizada facilita a manutenção, escalabilidade e entendimento do projeto, especialmente em times ágeis e em contextos como hackathons, onde produtividade e clareza são fundamentais.
