public class RoboTerrestre extends Robo{
    public int velocidadeMaxima;
    public SensorProximidade sensor;

    public RoboTerrestre(String nome, int posicaoX, int posicaoY, int velocidadeMaxima, SensorProximidade sensor){
        super(nome, posicaoX, posicaoY);
        this.velocidadeMaxima = velocidadeMaxima;
        this.sensor = sensor;
    }

    public boolean mover(int deltaX, int deltaY, int velocidade){

        if(velocidade <= this.velocidadeMaxima){
            this.posicaoX += deltaX;
            this.posicaoY += deltaY;
            System.out.println("Posição alterada");
            return true;
        }
        else{
            System.out.println("Limite de velocidade atingido.");
            return false;
        }
    }

}
