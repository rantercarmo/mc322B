public class RoboTerrestre extends Robo implements Sensoreavel {
    protected int velocidadeMaxima;
    protected SensorProximidade sensorProximidade;

    public RoboTerrestre(String id, int x, int y, int velocidadeMaxima, SensorProximidade sensor) {
        super(id, x, y, 0); // Robôs terrestres sempre no Z=0 (ou nível do chão)
        this.velocidadeMaxima = velocidadeMaxima;
        this.sensorProximidade = sensor;
    }

    @Override
    protected char getCharRepresentacaoEspecifica() {
        return 'T';
    }

    @Override
    public String getDescricao() {
        return super.getDescricao() + " (VelMax: " + velocidadeMaxima + "km/h)";
    }

    @Override
    public void moverPara(int novoX, int novoY, int novoZ, Ambiente ambiente)
            throws RoboDesligadoException, ColisaoException, ForaDosLimitesException, AcaoNaoPermitidaException {
        if (novoZ != 0) {
            throw new AcaoNaoPermitidaException("Robô terrestre " + id + " só pode se mover no nível Z=0.");
        }
        super.moverPara(novoX, novoY, 0, ambiente);
    }

    @Override
    public void executarTarefa(Ambiente ambiente, CentralComunicacao central)
            throws RoboDesligadoException, AcaoNaoPermitidaException, ColisaoException,
                   ForaDosLimitesException, ErroComunicacaoException, RecursoInsuficienteException {
        // Assinatura agora compatível com Robo.java

        if (this.estado == EstadoRobo.DESLIGADO) {
            throw new RoboDesligadoException("Robo Terrestre " + id + " está desligado.");
        }
        System.out.println("Robo Terrestre " + id + " executando patrulha terrestre simples e verificando arredores.");

        try {
            int dir = (int) (Math.random() * 4);
            int nX = x, nY = y;
            if (dir == 0) nX++;
            else if (dir == 1) nX--;
            else if (dir == 2) nY++;
            else nY--;

            // MoverPara pode lançar RDE, CE, FDLE, ANPE (que estão na assinatura)
            if (ambiente.dentroDosLimites(nX, nY, 0) && !ambiente.estaOcupadoPorOutraEntidade(nX, nY, 0, this)) {
                moverPara(nX, nY, 0, ambiente);
                System.out.println(id + " patrulhou para (" + nX + "," + nY + ",0)");
            } else {
                System.out.println(id + " não pôde patrulhar para (" + nX + "," + nY + ",0) - local inválido ou ocupado.");
            }
        } catch (RoboDesligadoException | ColisaoException | ForaDosLimitesException | AcaoNaoPermitidaException e) {
            // Tratar ou registrar a exceção específica da tentativa de movimento de patrulha.
            // Se a tarefa principal pode continuar apesar disso, não precisa relançar aqui.
            // Se a falha na patrulha impede a tarefa, então relance ou lance uma nova exceção.
            System.out.println(id + " encontrou um problema durante a patrulha simples: " + e.getMessage());
            // Para este exemplo, vamos permitir que a tarefa continue com a leitura dos sensores.
        }

        acionarSensores(); // Pode lançar RoboDesligadoException
        System.out.println(lerDadosSensor(ambiente)); // Pode lançar RoboDesligadoException

    }

    @Override
    public void acionarSensores() throws RoboDesligadoException {
        if (this.estado == EstadoRobo.DESLIGADO) {
            throw new RoboDesligadoException("Sensores (Robo Terrestre " + id + "): Robô desligado.");
        }
        System.out.println("Robo Terrestre " + id + ": Sensor de Proximidade ativado.");
    }

    @Override
    public String lerDadosSensor(Ambiente ambiente) throws RoboDesligadoException {
        if (sensorProximidade == null) {
            return "Robo Terrestre " + id + " não possui sensor de proximidade equipado.";
        }
        return sensorProximidade.lerDados(this, ambiente);
    }
}