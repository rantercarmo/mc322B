import java.util.ArrayList;

public class Ambiente {
    // dimensões do ambiente
    public final int largura;
    public final int altura;
    public int profundidade;

    public ArrayList<Entidade> entidades;
    public TipoEntidade[][][] mapa;

    public Ambiente (int largura, int altura, int profundidade){
        this.largura  = largura;
        this.altura = altura;
        this.profundidade = profundidade;
        this.entidades = new ArrayList<>();
        this.mapa = new TipoEntidade[largura][altura][profundidade];
        inicializarMapa();
    }

    private void inicializarMapa(){
        for (int x = 0; x< largura; x++){
            for (int y = 0; y< altura; y++){
                for (int z = 0; z< profundidade; z++){
                    mapa[x][y][z] = TipoEntidade.VAZIO;
                }
            }
        }
    }

    public boolean dentroDosLimites (int x, int y, int z){
        if (x<= this.largura && y<= this.altura && z<= this.profundidade){
            return true;
        }
        return false;

    }

    public boolean estaOcupado(int x, int y, int z) {
        return mapa[x][y][z] != TipoEntidade.VAZIO;
    }

    public void adicionarEntidade(Entidade e) throws ColisaoException {
        int x = e.getX(), y = e.getY(), z = e.getZ();

        if (!dentroDosLimites(x, y, z)){
            throw new ColisaoException ("Posição ("+x+","+y+","+z+") fora dos limites");
        }

         else if (estaOcupado(x, y, z)) {
            throw new ColisaoException("Posição ("+x+","+y+","+z+") já ocupada");
        }
        else{
            entidades.add(e);
            mapa[x][y][z] = e.getTipo();
        }
    }

    public void verificarColisoes() throws ColisaoException {
        // Implementar lógica para verificar colisões entre entidades
    }

    public void visualizarAmbiente(int planoZ) {
        System.out.println("Visualização do Ambiente (Plano XY na altitude Z=" + planoZ + ")");
        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < largura; x++) {
                System.out.print(mapa[x][y][planoZ].getRepresentacao() + " ");
            }
            System.out.println();
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
            }
            mapa.add(linha);
        }
        return mapa;
    }
}

