# **MC322 - Turma B**

# **Alunos**
    Ranter Soares
    João Gabriel

# **Ra**
    186289
    281255
    
# **Informações iniciais do código**
- **IDE Usada**: _Virtual Studio Code_
- **Versão Java usada**: `java 23.0.2 2025-01-21`

# **Informações mais detalhadas do repositório**
## Conteúdo do Repositório
- Este repositório contém todos os arquivos referentes aos laboratórios de MC322, desde as primeiras versões do emulador de robôs em um ambiente virtual, passando por todas as suas evoluções, refatorações e adição de funcionalidades. Nos arquivos do repositório, há a classe dos robôs e suas subclasses associadas, divididas entre robôs aéreos (`RoboAereo`) e robôs terrestres (`RoboTerrestre`) e as subclasses associadas dessas subclasses. 
- Além das classes derivadas de robôs, há as classes `Sensor` e `Obstaculo`. A classe `Sensor`, com suas subclasses associadas, estão relacionadas aos sensores que compoem os robos. Cada subclasse do sensor compõe uma subclasse específica de robô. Por exemplo, a subclasse `SensorProximidade` está associada a `RoboTerrestre` e subclasses associadas, ao que a subclasse `SensorAltitude` está associada a `RoboAereo` e subclasses associadas. A classe `Obstaculo` representa os obstáculos presentes no mapa. Ela é um _enum_ com diferentes tipos de obstáculo no mapa.
- A classe `Ambiente` representa todo o ambiente virtual em que os robôs e obstáculos estarão presentes. O acompanhamento de robôs e obstáculos no ambiente é feito por duas `ArrayList`, uma para cada elemento.
- A classe `MenuInterativo` representa os métodos de interação do usuário com o ambiente e os robôs
- A classe `Main` coordena todas as classes e subclasses do repositório, inicia o programa, o mapa, os robôs, os obstáculos e a classe `MenuInterativo`.
### Diagrama UML das classes do programa
- O diagrama exemplificando as classes do repositório e suas respectivas relações pode ser encontrado [aqui](https://lucid.app/lucidchart/b50848b2-9e0f-471a-b9c5-a4230f7cc368/edit?viewport_loc=680%2C-845%2C1943%2C1124%2C0_0&invitationId=inv_ed91c67d-cec0-4f8d-a3f2-632e1b6c0af5).

## Como executar cada programa
- Uma vez acessada a pasta do repositório (`mc322B`), basta rodar os seguintes comandos:
```
cd Labs
java Main.java
```
- Como a classe Main coordena todas as outras, basta este comando para inicializar o programa contido no repositório como um todo.
