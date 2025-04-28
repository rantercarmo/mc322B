public class Main {

    public static void main(String[] args){
        SensorAltitude sensorAereo = new SensorAltitude(400, 50);
        SensorProximidade sensorTerrestre = new SensorProximidade(120);

        Ambiente a1 = new Ambiente(2000, 2000,1000);
        Robo r1 = new Robo("teste",0,0);
        RoboTerrestre r2 = new RoboTerrestre("Carlinhos", 0, 0, 50, sensorTerrestre);
        RoboAereo r3 = new RoboAereo("Pedrinho", 0, 0, 0, 10, sensorAereo);
        RoboTransportador r4 = new RoboTransportador("Pedrito", 0, 0, 0, 20, sensorTerrestre);
        RoboVulcanico r5 = new RoboVulcanico("Robozito", 0, 0, 300, 300, sensorTerrestre);
        DroneDeEntrega r6 = new DroneDeEntrega("dronizito", 0, 0, 0, 400, "-22.815223, -47.079451", sensorAereo);
        DroneDeGuerra r7 = new DroneDeGuerra("Mala", 0, 0, 0, 300,  "-22.815223, -47.079451", "Ak-47", sensorAereo);

        a1.adicionarRobo(r1);
        a1.adicionarRobo(r2);
        a1.adicionarRobo(r3);
        a1.adicionarRobo(r4);
        a1.adicionarRobo(r5);
        a1.adicionarRobo(r6);
        a1.adicionarRobo(r7);

        a1.adicionarObstaculo("buraco", 45, 28);
        a1.adicionarObstaculo("torre", 10, 10);
        a1.adicionarObstaculo("pedra", 450, 120);
        a1.adicionarObstaculo("morrinho", 34, 567);
        a1.adicionarObstaculo("buraco", 68, 150);

        r1.mover(4,4);
        a1.dentroDosLimites(r1.getPosicaoX(), r1.getPosicaoY(),0);
        r2.mover(100, 100, 200000);
        r3.subir(40000);
        r4.carregar(4000000);
        r5.mover(100,200,30,40000000);
        r6.getDestino();
        r7.getAlvo();
        r2.mover(2, 1, 2);
        r3.subir(4);
        r6.subir(400);
        r7.subir(10);
        r3.subir(6);

        MenuInterativo NovoMenu = new MenuInterativo();

        while (true) {
            NovoMenu.Iniciar();
            NovoMenu.In = NovoMenu.UserIn.nextLine();

            if (NovoMenu.In.equals("/s")) {
                NovoMenu.Sensores(a1.robos);
            } else if (NovoMenu.In.equals("/st")) {
                NovoMenu.Status(a1.robos, a1.obstaculos);
            } else if (NovoMenu.In.equals("/m")) {
                NovoMenu.Movimento(a1.robos);
            } else if (NovoMenu.In.equals("/e")) {
                break;
            }
        }
    }
}
