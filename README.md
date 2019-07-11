# StarWarsGame
Solução para desafio AME Digital. API com dados de planetas da franquia Star Wars.

- [Pré-requisitos](#pré-requisitos)
- [Instruções](#instruções)
- [Integração](#integração)
- [Endpoints](#endpoints)


## Pré-requisitos
- Java 1.8.0_162
- Maven 3.3.9
- Intellij IDEA
- Docker 17.05.0-ce

## Instruções

Todos os comandos deverão ser executado via terminal.
Fazer o checkout do projeto via GitHub e na raiz do projeto executar o comando abaixo:

``` mvn clean install ```

Após a execução gerar a imagem docker da aplicação, exemplo comando abaixo:
 
``` docker build -t jeffersonaraujop/ubuntu_java8 . ```

Com o build realizado com sucesso, a aplicação pode ser executada através da linha de comando abaixo via terminal:

``` docker run -it -p 8080:8080 jeffersonaraujo/ubuntu-java8:latest ```

A partir de agora você poderá acessar os serviços pelos [endpoints](#endpoints).

## Endpoints
Existe uma documentação dos servicos montada usando swagger que pode ser acessada pelo link, após executar os passos acima, segue abaixo:
> http://localhost:8080/swagger-ui.html

Endpoints necessário para consumir os serviços da aplicação:
- Adiciona Planetas
	> **POST** http://localhost:8080/planet/add
    
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
	> **GET** http://localhost:8080/planet/list

    - **REQUEST**
    ```	
        header  {
                    Content-Type:application/json
		            User-Agent: SWAPI/StarWarsGame
                }
    ```
    - **RESPONSE**
        - 200 - Lista retornada com sucesso
             
- Encontrar Planeta por Id
    > **GET** http://localhost:8080/planet/find/{ID}
    
    - **REQUEST** 
    ```	
        header  {
                    Content-Type:application/json
		            User-Agent: SWAPI/StarWarsGame
                }
    ```
    - **RESPONSE**
        - 200 - Lista retornada com sucesso
        - 204 - Planeta não encontrado pelo id
    
- Encontrar Planeta por Nome
    > **GET** http://localhost:8080/planet/find/{NOME}
    
    - **REQUEST** 
    ```	
        header  {
                    Content-Type:application/json
		            User-Agent: SWAPI/StarWarsGame
                }
    ```
    - **RESPONSE**
        - 200 - Lista retornada com sucesso
        - 204 - Planeta não encontrado pelo nome
    
- Apagar Planeta por Nome
    > **DELETE** http://localhost:8080/planet/delete/{ID}
    
    - **REQUEST** 
    ```	
        header  {
                    Content-Type:application/json
		            User-Agent: SWAPI/StarWarsGame
                }
    ```
    - **RESPONSE**
        - 410 - Planeta apagado com sucesso
        - 204 - Planeta não encontrado pelo id
