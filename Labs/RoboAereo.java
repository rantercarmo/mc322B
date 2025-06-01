public class RoboAereo extends Robo{
    public int altitudeMaxima;
    public SensorAltitude sensorIntegrado;

    public RoboAereo(String nome, int posicaoX, int posicaoY, int posicaoZ, int altitudeMaxima, SensorAltitude sensorIntegrado){
        super(nome, posicaoX, posicaoY, posicaoZ);
        this.altitudeMaxima = altitudeMaxima;
        this.sensorIntegrado = sensorIntegrado;
    }

    //implementando funcs de Entidade

    @Override
    public TipoEntidade getTipo(){
        return TipoEntidade.ROBO;
    }

    @Override
    public String getDescricao(){
        return "Robo Aéreo " + nome + " na posição (" + getX() + "," + getY() + "," + getZ() + ")";
    }

    @Override
    public char getRepresentacao(){
        return 'A';
    }


}
