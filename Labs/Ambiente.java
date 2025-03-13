public class Ambiente {
    public int largura;
    public int altura;


    public Ambiente (int largura, int altura){
        this.largura  = largura;
        this.altura = altura;
    }

    public boolean dentroDosLimites (int x, int y){
        if (x<= this.largura && y<= this.altura){
            System.out.println("Verdadeiro");
            return true;
        }
        System.out.println("Falso");
        return false;

    }

}
