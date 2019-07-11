# StarWarsGame
Solução para desafio B2W Digital. API com dados de planetas da franquia Star Wars.

- [Pré-requisitos](#pré-requisitos)
- [Instruções](#instruções)
- [Integração](#integração)
- [Endpoints](#endpoints)


## Pré-requisitos
- Java 1.8.0_162
- Maven 3.3.9
- Intellij IDEA

## Instruções

Todos os comandos deverão ser executado via terminal.
Fazer o checkout do projeto via GitHub e na raiz do projeto executar o comando abaixo:

``` mvn clean install ```

Com o build realizado com sucesso, a aplicação pode ser executada através da linha de comando abaixo via terminal:

```java -jar target/starwarsgame-1.0-SNAPSHOT.jar ```

A partir de agora você poderá acessar os serviços pelos [endpoints](#endpoints).

## Endpoints
Existe uma documentação dos servicos montada usando swagger que pode ser acessada pelo link, após executar os passos acima, segue abaixo:
> http://localhost:8080/swagger-ui.html

Endpoints necessário para consumir os serviços da aplicação:
- Adiciona Planetas
	> **POST** http://localhost:8080/
    
    - **REQUEST**
    ```	
        header  {
                    Content-Type:application/json
		    User-Agent: SWAPI/StarWarsGame
                }
    ```
    ```
        body    {
                    "climate": "quente",
                    "name": "Tatooine",
                    "terrain": "Tatooine"
                }
    
    ```
    - **RESPONSE**
         - 201 - Criado com sucesso na base de dados
         - 304 - Planeta já existe, não será adicionado 
         - 500 - Erro ao tentar acessar o SWAPI
    
- Listar Planetas
	> **GET** http://localhost:8080/list

    - **REQUEST**
    ```	
        header  {
                    Content-Type:application/json
                }
    ```
    - **RESPONSE**
        - 200 - Lista retornada com sucesso
             
- Encontrar Planeta por Id
    > **GET** http://localhost:8080/find/id/{ID}
    
    - **REQUEST** 
    ```	
        header  {
                    Content-Type:application/json
                }
    ```
    - **RESPONSE**
        - 200 - Lista retornada com sucesso
        - 204 - Planeta não encontrado pelo id
    
- Encontrar Planeta por Nome
    > **GET** http://localhost:8080/find/name/{NOME}
    
    - **REQUEST** 
    ```	
        header  {
                    Content-Type:application/json
                }
    ```
    - **RESPONSE**
        - 200 - Lista retornada com sucesso
        - 204 - Planeta não encontrado pelo nome
    
- Apagar Planeta por Nome
    > **DELETE** http://localhost:8080/delete/id/{ID}
    
    - **REQUEST** 
    ```	
        header  {
                    Content-Type:application/json
                }
    ```
    - **RESPONSE**
        - 410 - Planeta apagado com sucesso
        - 204 - Planeta não encontrado pelo id
