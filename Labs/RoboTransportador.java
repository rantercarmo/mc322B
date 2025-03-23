public class RoboTransportador extends RoboTerrestre {
    public int cargaMaxima; //carga maxima que o robo pode transportar
    public int cargaAtual = 0;

    public RoboTransportador(String nome, int posicaoX, int posicaoY, int velocidadeMaxima, int cargaMaxima){
        super(nome, posicaoX, posicaoY, velocidadeMaxima);
        this.cargaMaxima = cargaMaxima;
    }

    public boolean carregar (int pesoCarga){
        if (pesoCarga + cargaAtual <= cargaMaxima){
            cargaAtual = cargaAtual + pesoCarga;
            System.out.println("Carregamento realizado.");
            return true;
        }
        else{
            System.out.println("Falha no carregamento, o peso final é maior que o permitido");
            return false;
        }
    }

}
