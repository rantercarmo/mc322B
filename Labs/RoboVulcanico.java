//esse robo faz tarefas em ambientes vulcanicos, onde as temperaturas são altas
public class RoboVulcanico extends RoboTerrestre {
    public int temperaturaMaxima;

    public RoboVulcanico(String nome, int posicaoX, int posicaoY, int velocidadeMaxima, int temperaturaMaxima, SensorProximidade sensor){
        super(nome, posicaoX, posicaoY, velocidadeMaxima, sensor);
        this.temperaturaMaxima = temperaturaMaxima;
    }

    public boolean mover(int deltaX, int deltaY, int velocidade, int temperatura){

        if(velocidade <= this.velocidadeMaxima && temperatura < temperaturaMaxima ){
            this.posicaoX += deltaX;
            this.posicaoY += deltaY;
            System.out.println("Movimentação realizada");
            return true;
        }
        else{
            System.out.println("Limite de temperatura não seguro para movimentação.");
            return false;
        }
    }
}
