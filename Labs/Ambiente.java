import java.util.ArrayList;

public class Ambiente {
    public int largura;
    public int altura;
    public int altitude;
    public ArrayList<Robo> robos;
    public ArrayList<Obstaculo> obstaculos;


    public Ambiente (int largura, int altura, int altitude){
        this.largura  = largura;
        this.altura = altura;
        this.altitude = altitude;
    }

    public boolean dentroDosLimites (int x, int y, int z){
        if (x<= this.largura && y<= this.altura && z<= this.altitude){
            System.out.println("Verdadeiro");
            return true;
        }
        System.out.println("Falso");
        return false;

    }

    public void adicionarRobo (Robo robo){
        robos.add(robo);
        System.out.println("Robo " + robo.nome + " adicionado.");
    }

    public void removerRobo (Robo robo) {
        robos.remove(robo);
        System.out.println("Robo " + robo.nome + " removido.");
    }

}
