Este projeto foi desenvolvido para o teste prático da empresa Tools Software, e tem como objetivo criar APIS robustas, escaláveis e de fácil integração, utilizando as melhores práticas e tecnologias modernas.


**Tecnologias Utilizadas:**
- Spring Initializr: Utilizado para gerar a estrutura inicial do projeto de forma ágil e padronizada.
- Arquitetura MVC: Modelo de design de software para separar as responsabilidades da aplicação em três camadas: Modelo (dados), Visão (interface com o usuário) e Controle (lógica de negócios), promovendo uma estrutura modular e de fácil manutenção.
- Java 17 com Lombok.
- Spring Boot: Framework principal para construção da API, garantindo simplicidade e configuração mínima para criação de aplicações Java.
- Spring Web: Utilizado para construir a camada de comunicação da API, seguindo os princípios REST e tornando as rotas da API simples e eficazes.
- JPA (Java Persistence API): Para a persistência de dados, facilitando a interação com o banco de dados de forma orientada a objetos e sem a necessidade de escrever SQL manual.
- Banco H2: Banco de dados em memória, utilizado durante a execução do aplicativo para testes e desenvolvimento rápidos. O H2 oferece um ambiente leve e eficiente para armazenar e consultar dados temporários durante o ciclo de vida da aplicação.
- Swagger: Utilizado para gerar a documentação da API de forma automática e interativa, permitindo que os desenvolvedores consumam e testem a API diretamente pela interface Swagger UI.
- JUnit & Mockito: Ferramentas de teste utilizadas para garantir a qualidade e confiabilidade do código. O JUnit é usado para testes unitários, enquanto o Mockito facilita a criação de mocks para simular comportamentos de dependências e isolar o teste de unidades específicas.


```
Como executar:
- Faça o download do projeto.
- Importe o projeto na IDE de sua preferência.
- Em sua IDE, defina a linguagem como Java 17.
- Instale o plugin do Lombok.
- Dê comandos para o Maven limpar e atualizar a peça/projeto.
- Dê "start" na classe "PaymentResourcesApplication".
```


```
Como visualizar as apis/endpoints:
- Builde o projeto em "Debug".
- Após o projeto ter startado, acesse: http://localhost:8080/swagger-ui/index.html
- Faça os testes que forem necessários.
```
![image](https://github.com/user-attachments/assets/6173bcf7-7a40-4b1e-b2e3-b625dac5b186)



```
Como conferir se os dados das apis estão sendo salvos, modificados?
- Builde o projeto em "Debug".
- Chame no Swagger pelo menos o endpoint api/payment para cadastrar algo no bd H2.
  Recomendo utilizar o json do arquivo EVIDENCIAS.xlsx que encontra-se abaixo.
- Após o projeto ter startado, acesse: http://localhost:8080/h2-console
- Faça os testes que forem necessários.
```
![image](https://github.com/user-attachments/assets/610c6c37-67c7-4824-ad6d-9189ffce9a5a)
![image](https://github.com/user-attachments/assets/6416e398-8056-4c26-bc63-8555e333be37)


**Baixe o arquivo "Evidências.xlsx" para consultar os resultados obtidos (JSON ESPERADO X JSON OBTIDO):**
[EVIDÊNCIAS.xlsx](https://github.com/user-attachments/files/17921619/EVIDENCIAS.xlsx)

**OBS1: TOMEI A LIBERDADE DE CODAR  O PROJETO INTEGRALMENTE EM INGLÊS E ALTEREI A TIPAGEM DE ALGUNS ATRIBUTOS, PENSANDO NAS MELHORES PRÁTICAS.**

**OBS2: AS APIS SOMENTE SUPORTAM O FORMATO DE DATA "dd/MM/yyyy HH:mm:ss".**

Em caso de dúvida, me mande um oi! 31-99945-5969
