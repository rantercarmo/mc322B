public class DroneDeEntrega extends RoboAereo {
    public String destino;

    public DroneDeEntrega (String nome, int posicaoX, int posicaoY, int altitude, int altitudeMaxima, String destino, SensorAltitude sensor){
        super(nome, posicaoX, posicaoY, altitude,altitudeMaxima, sensor);
        this.destino = destino;
    }

    public void setDestino(String destino){
        System.out.println("Destino alterado");
        this.destino = destino;
    }

    public String getDestino(){
        System.out.println("O destino atual é:" + this.destino);
        return this.destino;
    }
}
