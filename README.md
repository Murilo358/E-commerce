# E-Commerce Microservices (Em Desenvolvimento)

Este é um projeto de **e-commerce** em desenvolvimento, implementado utilizando **microserviços** com **Java Spring Boot**. A arquitetura segue os princípios de **Event-Driven Architecture** (Arquitetura Orientada a Eventos), permitindo que os microserviços se comuniquem de forma assíncrona. A comunicação entre os serviços é gerenciada por **Apache Kafka**, e a consistência dos dados é assegurada pelo **Confluent Schema Registry**. A implementação de **CQRS** (Command Query Responsibility Segregation) utilizando o **Axon Framework** garante a escalabilidade e flexibilidade do sistema.

## Tecnologias Utilizadas

- **Java Spring Boot**: Framework para construção de microserviços.
- **Apache Kafka**: Sistema de mensageria utilizado para comunicação assíncrona entre os microserviços.
- **Confluent Schema Registry**: Ferramenta para garantir a consistência e compatibilidade dos dados entre os microserviços.
- **Axon Framework**: Implementação do padrão CQRS, permitindo a separação de leitura e escrita para maior desempenho e escalabilidade.
- **Axon Server**: Servidor utilizado para facilitar a implementação de CQRS e Event Sourcing.

## Estrutura do Projeto

A arquitetura do sistema é baseada em microserviços, cada um responsável por uma parte específica do e-commerce. A comunicação entre esses serviços é feita através de eventos publicados no **Apache Kafka**, e o sistema segue a filosofia de **Event-Driven Architecture** para garantir a escalabilidade e robustez.

### Padrão CQRS
- O padrão **CQRS** é utilizado para separar as responsabilidades de leitura e escrita, garantindo que os microserviços possam ser otimizados para cada operação.

### Comunicação Assíncrona
- A comunicação entre os microserviços é realizada via Kafka, onde eventos são gerados e consumidos conforme as operações acontecem nos serviços.

### Confluent Schema Registry
- O **Confluent Schema Registry** é utilizado para garantir que as mensagens enviadas entre os microserviços estejam de acordo com um contrato comum, garantindo a compatibilidade dos dados.

## Como Rodar o Projeto (Em Desenvolvimento)

Este projeto ainda está em desenvolvimento, e a documentação para execução local será atualizada em breve.

## Como Contribuir

1. **Clone o repositório**:
    ```bash
    git clone https://github.com/Murilo358/E-commerce.git
    ```
2. **Crie uma branch** para suas modificações:
    ```bash
    git checkout -b feature/nome-da-feature
    ```
3. **Faça suas alterações** e envie um pull request.


Este repositório está em constante desenvolvimento, e contribuições são bem-vindas! Se você tiver dúvidas ou sugestões, sinta-se à vontade para abrir uma **issue**.
