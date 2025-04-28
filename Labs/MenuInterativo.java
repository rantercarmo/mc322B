import java.util.*;

public class MenuInterativo {
    // Scanner e string criados para receber inputs variados do usuário
    Scanner UserIn = new Scanner(System.in);
    String In = new String();
    
    // Texto inicial do menu interativo com instruções
    public void Iniciar () {
        System.out.println("\n\nSEJA BEM VINDO AO CONSOLE DE CONTROLE DE AUTÔMATOS MEGALOTRON v2.0 \nLISTA DE COMANDOS:\n-visualizar status de robos e ambiente: /st\n-controle de movimento dos robos: /m\n-uso dos sensores: /s\n-sair: /e\n\nPor favor, entre com um comando: ");
    }

    // funcao para que o usuario veja os status dos robos presentes no ambiente
    public void Status (ArrayList<Robo> robos, ArrayList<Obstaculo> obstaculos) {
        System.out.println("\nRobôs atualmente ativos: " + robos);
        System.out.println("\nObstáculos presentes no ambiente: " + obstaculos);
        System.out.println("\nCaso queira visualizar detalhes de algum robô, digite '/r'\nCaso queira viualizar detalhes sobre algum obstáculo, digite '/o'\nCaso deseje voltar à tela inicial, digite qualquer outro caractere.");

        In = UserIn.nextLine();

        // Caso o usuario queira ver dados detalhados de algum robo em questao
        if (In.equals("/r")) {
            System.out.println("Digite o indexador do robo que deseja visualizar na lista dada (o primeiro robo da lista possui indexador 0): ");
            int index = UserIn.nextInt();
            Robo robo = robos.get(index);
            System.out.println("Nome do robo: " + robo.nome + "\nCoordenadas do robo no mapa: (" + robo.posicaoX + "," + robo.posicaoY + ")");

        // Caso o usuario queira ver detalhes de algum obstaculo no mapa
        } else if (In.equals("/o")) {
            System.out.println("Digite o indexador do obstaculo que deseja visualizar na lista dada (o primeiro obstaculo da lista possui indexador 0): ");
            int index = UserIn.nextInt(); // indexador do obstaculo na lista do ambiente
            Obstaculo obstaculo = obstaculos.get(index);
            // tipo de obstaculo no enum
            System.out.println("\nTipo de obstaculo: " + obstaculo.getClass() + "\nRaio do obstaculo: " + obstaculo.tamanhoX + "\nAltura do obstaculo: " + obstaculo.tamanhoZ + "\nCoordenadas do obstaculo no ambiente: (" + obstaculo.posX + "," + obstaculo.posY + ")");
        }
    }

    // Opcao para o usuario movimente os robos
    public void Movimento (ArrayList<Robo> robos) { // e usada a lista com os robos no ambiente para acessa-los
        System.out.println("\nRobôs atualmente ativos: " + robos);
        System.out.println("\nDigite o indexador do obstaculo que deseja movimentar na lista dada (o primeiro obstaculo da lista possui indexador 0): ");
        int index = UserIn.nextInt();
        Robo robo = robos.get(index);
        int deslocamentoX, deslocamentoY; // variaveis de deslocamento padrao dos robos

        // avalia a subclasse do robo a ser controlado, para que possam ser acessados controles especifico de cada subclasse
        if (robo instanceof RoboAereo) { // no caso de robos aereos, o movimento e realizado nos eixos x, y e z
            System.out.println("\nQuanto o robo deve andar no eixo x? ");
            deslocamentoX = UserIn.nextInt();
            System.out.println("\n" + "Quanto o robo deve andar no eixo y? ");
            deslocamentoY = UserIn.nextInt();
            System.out.println("\n" + "Quanto o robo deve andar no eixo z? ");
            int deslocamentoZ = UserIn.nextInt();
            
            robo.mover(deslocamentoX, deslocamentoY);
            ((RoboAereo)robo).subir(deslocamentoZ); // subida do robo no eixo z
            System.out.println("\nMovimento realizado com sucesso!");
        } else { // caso o robo nao seja aereo, o movimento e realizado apenas nos eixos x e y
            System.out.println("\nQuanto o robo deve andar no eixo x? ");
            deslocamentoX = UserIn.nextInt();
            System.out.println("\n" + "Quanto o robo deve andar no eixo y? ");
            deslocamentoY = UserIn.nextInt();
            
            robo.mover(deslocamentoX, deslocamentoY);
            System.out.println("\nMovimento realizado com sucesso!");
        }
    }
    
    // caso o usuario queira utilizar os sensores dos robos
    public void Sensores(ArrayList<Robo> robos) {
        System.out.println("\nRobôs atualmente ativos: " + robos);
        System.out.println("\nDigite o indexador do robô que você deseja utilizar o sensor na lista dada (o primeiro obstaculo da lista possui indexador 0): ");
        int index = UserIn.nextInt();
        Robo robo = robos.get(index); // o robo e acessado pela indexacao na lista de robos do ambiente

        if (robo instanceof RoboAereo) { // novamente, os casos sao divididos pela subclasse do robo, pois cada subclasse possui um tipo de sensor diferente
            ((RoboAereo)robo).sensorIntegrado.monitorar(); // funcionalidades do sensor aereo
        } else {
            ((RoboTerrestre)robo).sensor.monitorar(); // funcionalidades do sensor terrestre
        }
    }
}
