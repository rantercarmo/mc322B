import java.util.*;

public class MenuInterativo {
    Scanner UserIn = new Scanner(System.in);
    String In = new String();
    
    public void Iniciar () {
        System.out.println("SEJA BEM VINDO AO CONSOLE DE CONTROLE DE AUTÔMATOS MEGALOTRON v2.0 \nLISTA DE COMANDOS:\n-visualizar status de robos e ambiente: /st\n-controle de movimento dos robos: /m\n-uso dos sensores: /s\n-sair: /e\n\nPor favor, entre com um comando: ");
    }

    public void Status (ArrayList<Robo> robos, ArrayList<Obstaculo> obstaculos) {
        System.out.println("\nRobôs atualmente ativos: " + robos);
        System.out.println("\nObstáculos presentes no ambiente: " + obstaculos);
        System.out.println("\nCaso queira visualizar detalhes de algum robô, digite '/r'\nCaso queira viualizar detalhes sobre algum obstáculo, digite '/o'\nCaso deseje voltar à tela inicial, digite qualquer outro caractere.");

        In = UserIn.nextLine();

        if (In.equals("/r")) {
            System.out.println("Digite o indexador do robo que deseja visualizar na lista dada (o primeiro robo da lista possui indexador 0): ");
            int index = UserIn.nextInt();
            Robo robo = robos.get(index);
            System.out.println("Nome do robo: " + robo.nome + "\nCoordenadas do robo no mapa: (" + robo.posicaoX + "," + robo.posicaoY + ")");

        } else if (In.equals("/o")) {
            System.out.println("Digite o indexador do obstaculo que deseja visualizar na lista dada (o primeiro obstaculo da lista possui indexador 0): ");
            int index = UserIn.nextInt();
            Obstaculo obstaculo = obstaculos.get(index);
            System.out.println("\nTipo de obstaculo: " + obstaculo.getClass() + "\nRaio do obstaculo: " + obstaculo.tamanhoX + "\nAltura do obstaculo: " + obstaculo.tamanhoZ + "\nCoordenadas do obstaculo no ambiente: (" + obstaculo.posX + "," + obstaculo.posY + ")");
            System.out.println("\nPressione espaço para voltar ao inicio");
            In = UserIn.nextLine();
        }
    }

    public void Movimento (ArrayList<Robo> robos) {
        System.out.println("\nRobôs atualmente ativos: " + robos);
        System.out.println("\nDigite o indexador do obstaculo que deseja movimentar na lista dada (o primeiro obstaculo da lista possui indexador 0): ");
        int index = UserIn.nextInt();
        Robo robo = robos.get(index);
        int deslocamentoX, deslocamentoY;

        if (robo instanceof RoboAereo) {
            System.out.println("\nQuanto o robo deve andar no eixo x? ");
            deslocamentoX = UserIn.nextInt();
            System.out.println("\n" + "Quanto o robo deve andar no eixo y? ");
            deslocamentoY = UserIn.nextInt();
            System.out.println("\n" + "Quanto o robo deve andar no eixo z? ");
            int deslocamentoZ = UserIn.nextInt();
            
            robo.mover(deslocamentoX, deslocamentoY);
            ((RoboAereo)robo).subir(deslocamentoZ);
            System.out.println("\nMovimento realizado com sucesso!");
        } else {
            System.out.println("\nQuanto o robo deve andar no eixo x? ");
            deslocamentoX = UserIn.nextInt();
            System.out.println("\n" + "Quanto o robo deve andar no eixo y? ");
            deslocamentoY = UserIn.nextInt();
            
            robo.mover(deslocamentoX, deslocamentoY);
            System.out.println("\nMovimento realizado com sucesso!");
        }
        System.out.println("\nPressione espaço para voltar ao inicio");
        In = UserIn.nextLine();
    }

    public void Sensores(ArrayList<Robo> robos) {
        System.out.println("\nRobôs atualmente ativos: " + robos);
        System.out.println("\nDigite o indexador do robô que você deseja utilizar o sensor na lista dada (o primeiro obstaculo da lista possui indexador 0): ");
        int index = UserIn.nextInt();
        Robo robo = robos.get(index);

        if (robo instanceof RoboAereo) {
            ((RoboAereo)robo).sensorIntegrado.monitorar();
        } else {
            ((RoboTerrestre)robo).sensor.monitorar();
        }
        System.out.println("\nPressione espaço para voltar ao inicio");
        In = UserIn.nextLine();
    }
}
