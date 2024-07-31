# MS Payment

### 🚀 **Objetivo**

> Plataforma do produto **Bank**, que dá suporte as demandas internas do time financeiro da Cayena, que gerencia as transações via CayenaPay.
Para mais informações clique [aqui](https://www.notion.so/cayena-app/Documenta-o-Cayena-Bank-1737ecd6131e498cb0b0c85517d1c479)

&nbsp;

### 💻 Arquitetura e Design system
Este serviço faz parte de uma arquitetura de microserviços 
que oferece interface no padrão REST API [documentada](https://api-dev.cayena.io/payment-rest-api/swagger-ui.html) com a especificação Open API e orientado a eventos, integrado aos serviços da AWS EventBrigde e SQS. 
[Desenho da arquitetura da solução](https://app.diagrams.net/#G1l1I4q4KTwncwwWr_kcwNljogKLvFKnjl#%7B%22pageId%22%3A%22QA03ZjVP1ARPEGaEjEtX%22%7D).

Está organizado em módulos conforme a [documentação](https://www.notion.so/cayena-app/M-dulos-3885cf390a104101b96ddae066ccb72f)


##### Stack de Tecnologias:
Java 11, Spring Boot, MySQL, etc
&nbsp;


### ⚙️ Alterações, teste e validações


- Ajuste seu ambiente conforme os [pré-requisitos](https://www.notion.so/cayena-app/Ferramentas-e-Vers-es-41015ed2df2647a29c2d7561f8680485)


- Para rodar a aplicação localmente siga este [passo a passo](https://www.notion.so/cayena-app/Payment-f61c5e932a6b4446af2f3a7e4b69a020)


- Para entender nosso padrão de [ciclo de desenvolvimento](https://www.notion.so/cayena-app/Development-Lifecycle-e12be7a1f40743938a9bb5bed94b8415)


- Usamos o swagger para [documentacao da API](https://api-dev.cayena.io/payment-rest-api/swagger-ui.html),  caso queira entender melhor como usar, clique [aqui](https://www.notion.so/cayena-app/REST-API-Documentation-Swagger-08b794dbc10546aba1d798b2b0064089).


- Esteja atento as métricas de qualidade no [sonar](https://www.notion.so/cayena-app/SonarQube-fe620139b1dd49e9bb6f5bba99d16b9f)


- Consulte o [style guide](https://www.notion.so/cayena-app/Project-Architecture-Style-Guide-c171de9f558747b993bda7fbd2ca3231) para adotar nossos padrões e boas práticas.
&nbsp;


### 📋 Key Features


<details>


<summary>ver mais</summary>


- [Desconto](https://github.com/)
- [Analise de credito](https://github.com/)
- Consumo de limite
- Listagem de entregas
- Adicional financeiro (adf)
- xxx


</details>


&nbsp;




### 💻 Ambientes


- [Desenvolvimento](https://bank-dev.cayena.com/)
- [Homologação ](https://bank-test.cayena.com/)
- [Producao](https://bank.cayena.com/)


###  📈 Monitoramento


Para monitoramento dos logs e métricas usamos as ferramentas


- [Grafana](https://grafana.cayena.io)
- [Elastic](https://elastic.cayena.io/)


>[!TIP]
>
>Caso não tenha acesso, entre em contato com o SRE.


&nbsp;


### 🤝 Responsáveis


Aqui pode ser o nome da squad ou aqueles que respondem pela manutenção.


&nbsp;

