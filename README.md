Projeto: API de Pagamentos - Teste Prático

Este projeto foi desenvolvido para o teste prático da empresa Tools Software.

Contexto: Você trabalha em um Banco, no setor de cartões de crédito, e faz parte do Time Elite. A missão do time é implementar uma API de Pagamentos com os seguintes requisitos:

Operações da API:
Pagamento

Requisição: JSON conforme especificado
Resposta: JSON conforme especificado
Estorno

Consulta: Por ID
Resposta: JSON de retorno de estorno
Consulta

Consulta: Todos os pagamentos ou por ID
Resposta: JSON de retorno do pagamento
Requisitos:
A API deve seguir os princípios REST, utilizar JSON e o protocolo HTTP padrão de respostas.
O servidor pode ser Tomcat, JBoss ou Spring Boot.
Transações:
id: Deve ser único
status: "AUTORIZADO", "NEGADO"
formaPagamento: "AVISTA", "PARCELADO LOJA", "PARCELADO EMISSOR"
Tecnologias Utilizadas:
Spring Initializr para criação da estrutura
Spring Boot, Spring Web, JPA, Banco H2, Swagger, Arquitetura MVC
Este projeto busca implementar uma API robusta, escalável e de fácil integração, utilizando tecnologias modernas e boas práticas de desenvolvimento.
