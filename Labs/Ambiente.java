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
        this.robos = new ArrayList<Robo>();
        this.obstaculos = new ArrayList<Obstaculo>();
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

    // Adiciona obstaculo ao ambiente
    public void adicionarObstaculo (String tipo, int x, int y) { // uso: tipo de obstaculo digitado, sem acentos, com coordenadas nos eixos x e y
        if (tipo == "torre") {
            Obstaculo novo = Obstaculo.TORRE;
            novo.posX = x;
            novo.posY = y;
            obstaculos.add(novo);
        } else if (tipo == "buraco") {
            Obstaculo novo = Obstaculo.BURACO;
            novo.posX = x;
            novo.posY = y;
            obstaculos.add(novo);
        } else if (tipo == "pedra") {
            Obstaculo novo = Obstaculo.PEDRA;
            novo.posX = x;
            novo.posY = y;
            obstaculos.add(novo);
        } else {
            Obstaculo novo = Obstaculo.MORRINHO;
            novo.posX = x;
            novo.posY = y;
            obstaculos.add(novo);
        }
    }
}
