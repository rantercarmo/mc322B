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

        r2.sensor.monitorar();
        r3.sensorIntegrado.monitorar(r3.altitude);
        r4.sensor.monitorar();
        r5.sensor.monitorar();
        r6.sensorIntegrado.monitorar(r6.altitude);
        r7.sensorIntegrado.monitorar(r7.altitude);

        r1.exibirPosicao();
        r2.exibirPosicao();
        r3.exibirPosicao();
        r4.exibirPosicao();
        r5.exibirPosicao();
        r6.exibirPosicao();
        r7.exibirPosicao();
        
    }
}
