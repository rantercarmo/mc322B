//esse robo faz tarefas em ambientes vulcanicos, onde as temperaturas são altas
public class RoboVulcanico extends RoboTerrestre {
    public int temperaturaMaxima;

    public RoboVulcanico(String nome, int posicaoX, int posicaoY, int velocidadeMaxima, int temperaturaMaxima){
        super(nome, posicaoX, posicaoY, velocidadeMaxima);
        this.temperaturaMaxima = temperaturaMaxima;
    }

    public boolean mover(int deltaX, int deltaY, int velocidade, int temperatura){

        if(velocidade <= this.velocidadeMaxima && temperatura < temperaturaMaxima ){
            this.posicaoX += deltaX;
            this.posicaoY += deltaY;
            return true;
        }
        else{
            return false;
        }
    }
}
