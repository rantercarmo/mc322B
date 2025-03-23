public class DroneDeEntrega extends RoboAereo {
    public String destino;

    public DroneDeEntrega (String nome, int posicaoX, int posicaoY, int altitude, int altitudeMaxima, String destino){
        super(nome, posicaoX, posicaoY, altitude,altitudeMaxima);
        this.destino = destino;
    }

    public void setDestino(String destino){
        this.destino = destino;
    }

    public String getDestino(){
        return this.destino;
    }
}
