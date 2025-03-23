public class RoboAereo extends Robo{
    public int altitude;
    public int altitudeMaxima;

    public RoboAereo(String nome, int posicaoX, int posicaoY, int altitude, int altitudeMaxima){
        super(nome, posicaoX, posicaoY);
        this.altitude = altitude;
        this.altitudeMaxima = altitudeMaxima;
    }


    public boolean subir (int metros){
        if(this.altitude + metros <= altitudeMaxima){
            this.altitude =+ metros;
            return true;
        }
        else{
            return false;
        }
    }

    public boolean descer (int metros){
        if(this.altitude - metros >= 0){
            this.altitude =+ metros;
            return true;
        }
        else{
            return false;
        }
    }

}
