public class Robo {
    public String nome;
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
        System.out.println("A posição é\nx:" + this.posicaoX + "\ny:" +this.posicaoY );
    }

    public int getPosicaoX(){
        return this.posicaoX;
    }

    public int getPosicaoY(){
        return this.posicaoY;
    }

    public void identificarObstaculo(){
        
    }
    
}