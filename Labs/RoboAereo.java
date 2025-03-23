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
            System.out.println("Posição alterada");
            return true;
        }
        else{
            System.out.println("Limite de altitude máxima atingido.");
            return false;
        }
    }

    public boolean descer (int metros){
        if(this.altitude - metros >= 0){
            this.altitude =+ metros;
            System.out.println("Posição alterada");
            return true;
        }
        else{
            System.out.println("Limite de altitude mínima atingido.");
            return false;
        }
    }
    @Override
    public void exibirPosicao(){
        System.out.println("A posição é\nx:" + this.posicaoX + "\ny:" + this.posicaoY + "\nz:" +this.altitude);
    }

}
