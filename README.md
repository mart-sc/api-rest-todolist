## Todolist em Java com Spring Boot
Projeto de API Rest de um Gerenciador de tarefas, desenvolvido em Java utilizando Spring Boot

## Tecnologias
- Java 17 <br>
- SpringBoot 3.0.11 <br>
- Maven <br>
- h2database (banco em memória) <br>

## Configuração do ambiente de desenvolvimento
1. Clonagem do repositório: ```git clone <url_git>``` <br>
2. Instalação de dependências: ```mvn clean install``` <br>
3. Startar a aplicação: ```mvn spring-boot:run``` <br>

## Rest Client
Para testar a API é necessário um Cliente Rest, como o API Dog ou Postman. <br>
OBS: Algumas requisições é necessário passar uma Autenticação com o Cliente Rest (Auth)

[GET]  -  LISTAR USUÁRIOS: http://localhost:8080/users/ <br>
[GET]  -  BUSCAR USUÁRIO POR USERNAME: http://localhost:8080/users/{username} <br>
[POST] -  ADICIONAR USUÁRIO: http://localhost:8080/users/  (Requer Autenticação) <br><br>

[GET]  -  LISTAR TAREFAS DE UM USUÁRIO: http://localhost:8080/tasks/  (Requer Autenticação) <br>
[POST] -  ADICIONAR NOVA TAREFA: http://localhost:8080/tasks/  (Requer Autenticação) <br>
[PUT]  -  ALTERAR TAREFA: http://localhost:8080/tasks/{id}  (Requer Autenticação) <br>
