public class RoboTerrestre extends Robo{
    public int velocidadeMaxima;

    public RoboTerrestre(String nome, int posicaoX, int posicaoY, int velocidadeMaxima){
        super(nome, posicaoX, posicaoY);
        this.velocidadeMaxima = velocidadeMaxima;
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
            System.out.println("operação cancelada");
            return false;
        }
    }

}
