public class DroneDeGuerra extends RoboAereo{
    public String arma;
    public String alvo;

    public DroneDeGuerra (String nome, int posicaoX, int posicaoY, int altitude, int altitudeMaxima, String alvo, String arma, SensorAltitude sensor){
        super(nome, posicaoX, posicaoY, altitude,altitudeMaxima, sensor);
        this.arma = arma;
        this.alvo = alvo;
    }

    public void setAlvo (String alvo){
        System.out.println("Alvo atualizado.");
        this.alvo = alvo;
    }

    public String getAlvo (){
        System.out.println("O alvo atual é:" + this.alvo);
        return alvo;
    }

}
