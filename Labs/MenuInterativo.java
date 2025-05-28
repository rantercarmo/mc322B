import java.util.*;

public class MenuInterativo {
    // Scanner e string criados para receber inputs variados do usuário
    Scanner UserIn = new Scanner(System.in);
    String In = new String();
    
    // Texto inicial do menu interativo com instruções
    public void Iniciar () {
        System.out.println("\n\nSEJA BEM VINDO AO CONSOLE DE CONTROLE DE AUTÔMATOS MEGALOTRON v2.0 \nLISTA DE COMANDOS:\n-adicionar um obstaculo ao mapa: /obs\n-adicionar um robô ao mapa: /add\n-visualizar status de robos e ambiente: /st\n-controle de movimento dos robos: /m\n-uso dos sensores: /s\n-para imprimir o mapa: /pr\n-sair: /e\n\nPor favor, entre com um comando: ");
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
    
        Espera();
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
        Espera();
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

        Espera();
    }

    public void Imprimir (Ambiente mapa) {
        mapa.imprimirMapa();
        Espera(); // chama a funcao de espera para que o usuario possa voltar ao menu principal
    }

    public void AddRobo (Ambiente mapa) {
        System.out.println("\nDigite o nome do robô: ");
        String nome = UserIn.nextLine();
        System.out.println("\nDigite a posição X do robô: ");
        int posX = UserIn.nextInt();
        System.out.println("\nDigite a posição Y do robô: ");
        int posY = UserIn.nextInt();

        System.out.println("\nEscolha o tipo de robô que deseja adicionar:\n1. Robo Terrestre\n2. Robo Aéreo\n3. Robo Transportador\n4. Robo Vulcânico\n5. Drone de Entrega\n6. Drone de Guerra");
        int tipoRobo = UserIn.nextInt(); // recebe o tipo de robo que o usuario deseja adicionar ao ambiente
        UserIn.nextLine(); // limpa o buffer do scanner
        switch (tipoRobo) {
            case 1: // Caso seja um robo terrestre
                System.out.println("\nDigite o raio do sensor de proximidade: ");
                int raioSensorTerrestre = UserIn.nextInt();
                System.out.println("\nDigite a velocidade maxima do robo: ");
                int velMax = UserIn.nextInt();
                SensorProximidade sensorTerrestre = new SensorProximidade(raioSensorTerrestre);
                RoboTerrestre RoboTerrestre = new RoboTerrestre(nome, posX, posY, velMax, sensorTerrestre);
                mapa.adicionarRobo(RoboTerrestre); // adiciona o robo terrestre a listagem do ambiente
                break;
            case 2: // Caso seja um robo aereo
                System.out.println("\nDigite a altura maxima do sensor de altitude: ");
                int alturaMaxSensor = UserIn.nextInt(); // altura maxima do sensor de altitude do robo aereo
                UserIn.nextLine(); // limpa o buffer do scanner. Apos todas as entradas o buffer sera limpo 

                System.out.println("\nDigite o raio do sensor de altitude do robo: ");
                int raioSensor = UserIn.nextInt(); // raio do sensor de altitude do robo aereo
                UserIn.nextLine();

                System.out.println("\nDigite a altitude do robo: ");
                int altitudeRobo = UserIn.nextInt(); // altitude inicial do robo aereo
                UserIn.nextLine(); 

                System.out.println("\nDigite a altura maxima que o robo pode alcancar: ");
                int alturaMaxRobo = UserIn.nextInt(); // altura maxima que o robo aereo pode alcancar
                UserIn.nextLine(); 

                SensorAltitude sensorAereo = new SensorAltitude(raioSensor, alturaMaxSensor);
                RoboAereo RoboAereo = new RoboAereo(nome, posX, posY, altitudeRobo, alturaMaxRobo, sensorAereo);
                mapa.adicionarRobo(RoboAereo);
                break;
            case 3: // Caso seja um robo transportador
                System.out.println("\nDigite a velocidade maxima do robo: ");
                int VelMax = UserIn.nextInt();
                UserIn.nextLine(); // limpa o buffer do scanner

                System.out.println("\nDigite a carga maxima que o robo suporta: ");
                int cargaMax = UserIn.nextInt();
                UserIn.nextLine();

                System.out.println("\nDigite o raio do sensor do robo: ");
                int RaioSensor = UserIn.nextInt();
                UserIn.nextLine();

                SensorProximidade sensorTransportador = new SensorProximidade(RaioSensor);
                RoboTransportador RoboTransportador = new RoboTransportador(nome, posX, posY, VelMax, cargaMax, sensorTransportador);
                mapa.adicionarRobo(RoboTransportador); // adiciona o robo transportador a listagem do ambiente
                break;
            case 4: // Caso seja um robo vulcanico
                System.out.println("\nDigite a velocidade maxima do robo: ");
                int velMaxVulc = UserIn.nextInt();
                UserIn.nextLine();

                System.out.println("\nDigite a temperatura maxima que o robo aguenta (em kelvin): ");
                int TempMax = UserIn.nextInt();
                UserIn.nextLine();

                System.out.println("\nDigite o raio do sensor do robo: ");
                int RaioSensorVulc = UserIn.nextInt();
                UserIn.nextLine();

                SensorProximidade sensorVulcanico = new SensorProximidade(RaioSensorVulc); // cria o sensor de proximidade do robo vulcanico
                RoboVulcanico RoboVulcanico = new RoboVulcanico(nome, posX, posY, velMaxVulc, TempMax, sensorVulcanico); // cria o robo vulcanico com os dados fornecidos pelo usuario
                mapa.adicionarRobo(RoboVulcanico); // adiciona o robo vulcanico a listagem do ambiente

                break;
            case 5: // Caso seja um drone de entrega
                System.out.println("\nDigite a altura inicial do robo (em metros): ");
                int AltitudeEntrega = UserIn.nextInt(); // altura inicial do robo de entrega
                UserIn.nextLine();

                System.out.println("\nDigite a altura maxima do robo (em metros): ");
                int AltitudeMaxEntrega = UserIn.nextInt(); // altura maxima do robo de entrega
                UserIn.nextLine();

                System.out.println("\nDigite as coordenadas do local de entrega da carga do robo, as coordenadas X e Y, uma em cada linha: ");
                String CoordEntrega = UserIn.nextLine(); // coordenadas do local de entrega da carga do robo
                UserIn.nextLine();

                System.out.println("\nDigite o raio do sensor do robo: ");
                int RaioSensorEntrega = UserIn.nextInt(); // raio do sensor de altitude do robo de entrega
                UserIn.nextLine();

                System.out.println("\nDigite a altura maxima do sensor do robo: ");
                int AlturaMaxSensorEntrega = UserIn.nextInt(); // altura maxima do sensor de altitude do robo de entrega
                UserIn.nextLine();

                SensorAltitude sensorEntrega = new SensorAltitude(RaioSensorEntrega, AlturaMaxSensorEntrega); // cria o sensor de altitude do drone de entrega
                DroneDeEntrega DroneEntrega = new DroneDeEntrega(nome, posX, posY, AltitudeEntrega, AltitudeMaxEntrega, CoordEntrega, sensorEntrega); // cria o drone de entrega com os dados fornecidos pelo usuario
                mapa.adicionarRobo(DroneEntrega); // adiciona o drone de entrega a listagem do ambiente
                break;
            case 6: // Caso seja um drone de guerra
                System.out.println("\nDigite a altura inicial do robo (em metros): ");
                int AltitudeGuerra = UserIn.nextInt(); // altura inicial do robo de entrega
                UserIn.nextLine();

                System.out.println("\nDigite a altura maxima do robo (em metros): ");
                int AltitudeMaxGuerra = UserIn.nextInt(); // altura maxima do robo de entrega
                UserIn.nextLine();

                System.out.println("\nDigite as coordenadas do alvo do drone de guerra: ");
                String CoordAlvo = UserIn.nextLine(); // coordenadas do alvo do drone de guerra
                UserIn.nextLine();

                System.out.println("\nDigite a arma usada pelo drone de guerra: ");
                String tipoArma = UserIn.nextLine(); // arma usada pelo drone de guerra
                UserIn.nextLine();

                System.out.println("\nDigite o raio do sensor de altitude: ");
                int raioSensorGuerra = UserIn.nextInt();
                UserIn.nextLine(); // limpa o buffer do scanner

                System.out.println("\nDigite a altura maxima do sensor de altitude: ");
                int alturaMaxSensorGuerra = UserIn.nextInt(); // altura maxima do sensor de altitude do drone de guerra
                UserIn.nextLine(); // limpa o buffer do scanner

                SensorAltitude sensorGuerra = new SensorAltitude(raioSensorGuerra, alturaMaxSensorGuerra); // cria o sensor de altitude do drone de guerra
                DroneDeGuerra DroneGuerra = new DroneDeGuerra(nome, posX, posY, AltitudeGuerra, AltitudeMaxGuerra, CoordAlvo, tipoArma, sensorGuerra); // cria o drone de guerra com os dados fornecidos pelo usuario
                mapa.adicionarRobo(DroneGuerra); // adiciona o drone de guerra a listagem do ambiente
                break;
        }
        Espera();
    }

    public void addObstaculo (Ambiente mapa) {
        System.out.println("Digite as coordenadas, no mapa, que o obstaculo deve estar: \nX: ");
        int posX = UserIn.nextInt();
        UserIn.nextLine();

        System.out.println("Y: ");
        int posY = UserIn.nextInt();

        System.out.println("\nDigite o tipo de obstáculo que deseja adicionar: \n1. TORRE\n2. BURACO\n3. PEDRA\n4. MORRO PEQUENO\n"); 
        int tipoObstaculo = UserIn.nextInt(); // avalia o tipo de obstaculo que o usuario deseja adicionar 
        UserIn.nextLine(); // limpa o buffer do teclado

        switch (tipoObstaculo) {
            case 1:
                mapa.adicionarObstaculo("torre", posX, posY);
            case 2: 
                mapa.adicionarObstaculo("buraco", posX, posY);
            case 3: 
                mapa.adicionarObstaculo("pedra", posX, posY);
            case 4:
                mapa.adicionarObstaculo("morrinho", posX, posY);
        }
        System.out.println("\nObstaculo adicionado com sucesso!");
        Espera();
        return;
    }

    private void Espera() {
        System.out.println("\nPressione qualquer tecla para voltar ao menu principal.");
        UserIn.nextLine(); // aguarda o usuario pressionar qualquer tecla para voltar ao menu principal
        In = UserIn.nextLine(); // limpa o input do usuario
        if (In.equals("")) {
            In = " "; // garante que o input seja diferente de "/r" ou "/o"
        }
        return;
    }
}
