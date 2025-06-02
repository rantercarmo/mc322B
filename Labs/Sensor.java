public abstract class Sensor {
    protected int raioDeteccao;

    public Sensor(int raioDeteccao) {
        this.raioDeteccao = raioDeteccao;
    }

    public int getRaioDeteccao() {
        return raioDeteccao;
    }

    /**
     * Lê os dados do sensor com base na posição do robô e no ambiente.
     * @param robo O robô que possui o sensor.
     * @param ambiente O ambiente para obter informações.
     * @return Uma string com os dados lidos pelo sensor.
     * @throws RoboDesligadoException Se o robô estiver desligado.
     */
    public abstract String lerDados(Robo robo, Ambiente ambiente) throws RoboDesligadoException;
}