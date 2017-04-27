

#### Instituto Federal de Educação, Ciência e Tecnologia da Paraíba
#### Campus Cajazeiras
#### Curso Superior de Tecnologia em Análise e Desenvolvimento de Sistemas
#### Programação Orientada a Serviços
#### Professor: Ricardo de Sousa Job



**Atividade Avaliativa - Autenticação RESTful**

Implemente uma aplicação que avalia o quão um usuário é popular no Twitter. A avaliação vai mensurar dois fatores:  a **influência** e **popularidade** do usuário.

<img src="https://github.com/natarajanrodrigues/ativ2-auth-rest/blob/master/formul1.png" width="650">


A influência visa avaliar o quão participativa é a rede de seguidores do usuário.
Deve ser fornecido um ranking dos **dez usuários** mais bem classificados por esta métrica influence(u,w).

A popularidade visa avaliar se o usuário possui uma rede de seguidores ativos.

<img src="https://github.com/natarajanrodrigues/ativ2-auth-rest/blob/master/formula2.png" width="650">


**Observações:**

Forma de entrega: um repositório na __organization__.

O projeto deve ser **implantado com o Docker** e deve conter um roteiro de como realizar a implantação da aplicação.

A aplicação deve estar disponível via internet **(Heroku, por exemplo)**.

Após a implementação, **encaminhar** um email com o link do repositório para o email sousajob@gmail.com.

Prazo para entrega: **2 de maio de 2017, às 23h**.

<hr/>

**SOBRE O PROJETO**

1. Redis

  Os resultados obtidos pela aplicação são armazedos na persistência de um container `redis`. Estes resultados armazenados automaticamente se expiram depois de decorrido um prazo de tempo especificado (atualmente 10 horas).

  Isso permite retornar ao usuário resultados já computados nas últimas horas, funcionando como um cachê para aplicação.


**PASSO A PASSO**

Para executar o aplicativo através do Docker:

1. No seu terminal de comando, navegue até a pasta raiz do projeto, `ativ2-authagain`.

2. Certifique-se que o seu serviço Docker esteja iniciado.

3. Para sistemas unix-like, os serviços podem ser iniciados através do script `run.sh` e terminados através do `stop.sh`. Para iniciar, basta executar no seu terminal de linha de comando (estando ainda na pasta raiz do projeto):

    ```sh run.sh```

    Obs.: o scrip `stop.sh` pode apagar o volume de persistência criado para o uso do `redis`, mas para isso deve ser retirado o comentário da última linha `docker volume remove ativ2authrest_redis-volume`. Deixamos o comando comentado para fins de uso do dados salvos no redis durante o desenvolvimento.

4. Depois de alguns segundos nos quais a aplicação já tiver sido inicilizada, visitar a URL em seu navegador web:

    [http://localhost:8080/ativ2-authagain](http://localhost:8080/ativ2-authagain)
