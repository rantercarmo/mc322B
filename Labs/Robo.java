public class Robo {
    public final String nome;
    public int posicaoX;
    public int posicaoY;
    public String direcao;


    public Robo(String nome, int posicaoX, int posicaoY){
        this.nome = nome;
        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
    }


    public void mover(int deltaX, int deltaY){
        this.posicaoX += deltaX;
        this.posicaoY += deltaY;
    }

    public void exibirPosicao(){
        System.out.println("A posição é\nx:" + getPosicaoX() + "\ny:" + getPosicaoY() );
    }

    protected int getPosicaoX(){
        return this.posicaoX;
    }

    protected int getPosicaoY(){
        return this.posicaoY;
    }
}