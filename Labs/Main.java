import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Configuração do Ambiente e Entidades Iniciais
        Ambiente ambiente = new Ambiente(20, 15, 10); // Largura (X), Profundidade (Y), Altura (Z)
        CentralComunicacao centralCom = new CentralComunicacao();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Inicializando Simulador de Robôs - Laboratório 4");

        try {
            // --- Criação de Sensores ---
            SensorProximidade sensorPComum = new SensorProximidade(3);
            SensorProximidade sensorPLongoAlcance = new SensorProximidade(5);
            SensorAltitude sensorAltitudePadrao = new SensorAltitude(2, 30); // raio detecção obst. aéreo, alt. max. operacional sensor
            SensorAltitude sensorAltitudeAvancado = new SensorAltitude(3, 50);

            // --- Criação e Adição de Robôs ---
            RoboTerrestre rt1 = new RoboTerrestre("RT-Charlie", 2, 2, 15, sensorPComum);
            ambiente.adicionarEntidade(rt1);

            RoboTransportador rtCargo = new RoboTransportador("CargoBot-01", 5, 3, 10, sensorPLongoAlcance, 100);
            ambiente.adicionarEntidade(rtCargo);

            RoboVulcanico rv1 = new RoboVulcanico("MagmaRover", 8, 8, 8, sensorPComum, 700);
            ambiente.adicionarEntidade(rv1);

            RoboAereo ra1 = new RoboAereo("SkySpy-1", 3, 5, 2, 25, sensorAltitudePadrao); // alt max voo robo
            ambiente.adicionarEntidade(ra1);

            DroneDeEntrega de1 = new DroneDeEntrega("ZipDeliver", 6, 6, 3, 20, sensorAltitudeAvancado, "QG Central", 50);
            ambiente.adicionarEntidade(de1);

            DroneDeGuerra dg1 = new DroneDeGuerra("WarHawk-7", 10, 10, 5, 40, sensorAltitudeAvancado, "Laser Pesado", 20);
            ambiente.adicionarEntidade(dg1);

            RoboSonda sonda1 = new RoboSonda("ExplorerProbe", 1, 8, 4, 35, sensorAltitudeAvancado, sensorPLongoAlcance);
            ambiente.adicionarEntidade(sonda1);


            // --- Criação e Adição de Obstáculos ---
            Obstaculo obs1 = new Obstaculo("Rocha Grande", 7, 2, 0, 'P');
            ambiente.adicionarEntidade(obs1);
            Obstaculo obs2 = new Obstaculo("Muro Baixo", 4, 6, 0, '#');
            ambiente.adicionarEntidade(obs2);
            Obstaculo obs3 = new Obstaculo("Muro Baixo Topo", 4, 6, 1, '#'); // Parte de cima do muro
            ambiente.adicionarEntidade(obs3);
            Obstaculo obsAereo = new Obstaculo("Balão à Deriva", 12, 7, 6, 'B');
            ambiente.adicionarEntidade(obsAereo);


            System.out.println("\nAmbiente e entidades iniciais configurados com sucesso.");
            ambiente.visualizarAmbiente(0); // Visualiza a camada do chão

        } catch (ColisaoException | ForaDosLimitesException | EntidadeJaExisteException | IllegalArgumentException e) {
            System.err.println("ERRO FATAL NA INICIALIZAÇÃO DO AMBIENTE: " + e.getMessage());
            e.printStackTrace();
            System.out.println("Simulador não pôde ser iniciado devido a erro na configuração.");
            scanner.close();
            return;
        }

        // Inicia o Menu Interativo
        MenuInterativo menu = new MenuInterativo(ambiente, centralCom, scanner);
        menu.iniciar();

        scanner.close();
        System.out.println("\nSimulador de Robôs encerrado. Até a próxima!");
    }
}