public class RoboAereo extends Robo implements Sensoreavel {
    protected int altitudeMaximaVoo;
    protected SensorAltitude sensorAltitude;

    public RoboAereo(String id, int x, int y, int zInicial, int altitudeMaximaVoo, SensorAltitude sensor) {
        super(id, x, y, zInicial);
        if (zInicial < 0) throw new IllegalArgumentException("Altitude inicial de RoboAereo não pode ser negativa.");
        this.altitudeMaximaVoo = altitudeMaximaVoo;
        this.sensorAltitude = sensor;
    }

    @Override
    protected char getCharRepresentacaoEspecifica() {
        return 'A';
    }

    @Override
    public String getDescricao() {
        return super.getDescricao() + " (AltMax: " + altitudeMaximaVoo + "m)";
    }

    @Override
    public void moverPara(int novoX, int novoY, int novoZ, Ambiente ambiente)
            throws RoboDesligadoException, ColisaoException, ForaDosLimitesException, AcaoNaoPermitidaException {
        if (novoZ < 0) {
             throw new AcaoNaoPermitidaException("Movimento para Z=" + novoZ + " inválido (abaixo do solo) para robô aéreo " + id + ".");
        }
        if (novoZ > this.altitudeMaximaVoo) {
            throw new AcaoNaoPermitidaException("Movimento para Z=" + novoZ + " excede altitude máxima de voo (" + this.altitudeMaximaVoo + ") do robô aéreo " + id + ".");
        }
        super.moverPara(novoX, novoY, novoZ, ambiente);
    }

    public void subir(int incrementoZ, Ambiente ambiente) throws RoboDesligadoException, ColisaoException, ForaDosLimitesException, AcaoNaoPermitidaException {
        moverPara(this.x, this.y, this.z + incrementoZ, ambiente);
    }

    public void descer(int decrementoZ, Ambiente ambiente) throws RoboDesligadoException, ColisaoException, ForaDosLimitesException, AcaoNaoPermitidaException {
        moverPara(this.x, this.y, this.z - decrementoZ, ambiente);
    }

    @Override
    public void executarTarefa(Ambiente ambiente, CentralComunicacao central)
            throws RoboDesligadoException, AcaoNaoPermitidaException, ColisaoException,
                   ForaDosLimitesException, ErroComunicacaoException, RecursoInsuficienteException {
        

        if (this.estado == EstadoRobo.DESLIGADO) {
            throw new RoboDesligadoException("Robo Aéreo " + id + " está desligado.");
        }
        System.out.println("Robo Aéreo " + id + " executando voo de reconhecimento e verificando altitude.");

        if (this.z < altitudeMaximaVoo / 2 && this.z < ambiente.altura - 1) {
            try {
                // subir pode lançar RDE, ANPE, CE, FDLE
                subir(1, ambiente);
                System.out.println(id + " ajustou altitude para " + this.z);
            } catch (RoboDesligadoException | AcaoNaoPermitidaException | ColisaoException | ForaDosLimitesException e) {
                // Tratamento específico para a falha no ajuste de altitude.
                // Permite que a tarefa continue.
                System.out.println(id + " não pôde ajustar altitude automaticamente: " + e.getMessage());
            }
        }
        acionarSensores(); // Pode lançar RoboDesligadoException
        System.out.println(lerDadosSensor(ambiente)); // Pode lançar RoboDesligadoException

        // Este método, em sua implementação atual, não lança diretamente
        // ErroComunicacaoException ou RecursoInsuficienteException.
        // A assinatura completa é mantida para permitir que subclasses de RoboAereo possam fazer isso.
    }

    @Override
    public void acionarSensores() throws RoboDesligadoException {
        if (this.estado == EstadoRobo.DESLIGADO) {
            throw new RoboDesligadoException("Sensores (Robo Aéreo " + id + "): Robô desligado.");
        }
        System.out.println("Robo Aéreo " + id + ": Sensor de Altitude ativado.");
    }

    @Override
    public String lerDadosSensor(Ambiente ambiente) throws RoboDesligadoException {
        if (sensorAltitude == null) {
            return "Robo Aéreo " + id + " não possui sensor de altitude equipado.";
        }
        return sensorAltitude.lerDados(this, ambiente);
    }
}