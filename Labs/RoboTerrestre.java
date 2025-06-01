public class RoboTerrestre extends Robo implements Entidade{
    public int velocidadeMaxima;
    public SensorProximidade sensor;

    public RoboTerrestre(String nome, int posicaoX, int posicaoY, int velocidadeMaxima, SensorProximidade sensor){
        super(nome, posicaoX, posicaoY);
        this.velocidadeMaxima = velocidadeMaxima;
        this.sensor = sensor;
    }


        //implementando funcs de Entidade
    @Override
    public TipoEntidade getTipo(){
        return TipoEntidade.ROBO;
    }

    @Override
    public String getDescricao(){
        return "Robo Terrestre " + nome + " na posição (" + posicaoX + "," + posicaoY + ",)";
    }

    @Override
    public char getRepresentacao(){
        return 'T';
    }

    public boolean mover(int deltaX, int deltaY, int velocidade){

        if(velocidade <= this.velocidadeMaxima){
            this.posicaoX += deltaX;
            this.posicaoY += deltaY;
            System.out.println("Posição alterada");
            return true;
        }
        else{
            System.out.println("Limite de velocidade atingido.");
            return false;
        }
    }

}
