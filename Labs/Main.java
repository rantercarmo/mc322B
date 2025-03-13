public class Main {

    public static void main(String[] args){
        Ambiente a1 = new Ambiente(20, 20);
        Robo r1 = new Robo("teste",0,0);

        r1.mover(4,4);
        a1.dentroDosLimites(r1.getPosicaoX(), r1.getPosicaoY());
        r1.exibirPosicao();
    }

}
