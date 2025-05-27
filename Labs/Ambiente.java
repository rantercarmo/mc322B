import java.util.ArrayList;

public class Ambiente {
    public final int largura;
    public final int altura;
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

    public void imprimirMapa() {
        ArrayList<ArrayList<Character>> mapa = criarMapa();
        
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                System.out.print(mapa.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }

    private ArrayList<ArrayList<Character>> criarMapa() {
        ArrayList<ArrayList<Character>> mapa = new ArrayList<>();
        
        for (int i = 0; i < altura; i++) {

            ArrayList<Character> linha = new ArrayList<>();
            for (int j = 0; j < largura; j++) {
                Boolean temRobo = false;
                Boolean temObstaculo = false;

                for (int z = 0; z < robos.size(); z++) {
                    Robo robo = robos.get(z);
                    if (robo.getPosicaoX() == j && robo.getPosicaoY() == i) {
                        linha.add('R'); // Representa um robô
                        temRobo = true;
                        break; // Se já encontrou um robô, não precisa verificar mais
                    }
                }
                if (!temRobo) {
                    for (int y = 0; y < obstaculos.size(); y++) {
                        Obstaculo obstaculo = obstaculos.get(y);
                        if (obstaculo.posX == j && obstaculo.posY == i) {
                            linha.add('O'); // Representa um obstáculo
                            temObstaculo = true;
                            break; // Se já encontrou um obstáculo, não precisa verificar mais
                        }
                    }
                    if (!temObstaculo) {
                        linha.add('_'); // Representa um espaço vazio
                    }
                }
                mapa.add(linha);
            }
        }
        return mapa;
    }
}
