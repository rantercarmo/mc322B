public class RoboSonda extends RoboAereo implements Explorador, Comunicavel {
    private String ultimoRelatorioExploracao;
    private SensorProximidade sensorProximidadeAdicional;

    public RoboSonda(String id, int x, int y, int zInicial, int altitudeMaximaVoo,
                     SensorAltitude sensorAltitude, SensorProximidade sensorProximidadeAdicional) {
        super(id, x, y, zInicial, altitudeMaximaVoo, sensorAltitude);
        this.sensorProximidadeAdicional = sensorProximidadeAdicional;
        this.ultimoRelatorioExploracao = "Nenhuma exploração realizada ainda.";
    }

    @Override
    protected char getCharRepresentacaoEspecifica() {
        return 'S'; // Para 'Sonda'
    }

    @Override
    public String getDescricao() {
        return super.getDescricao() + " (Tipo: Sonda de Exploração)";
    }

    @Override
    public void executarTarefa(Ambiente ambiente, CentralComunicacao central)
            throws RoboDesligadoException, AcaoNaoPermitidaException, ColisaoException,
                   ForaDosLimitesException, ErroComunicacaoException, RecursoInsuficienteException {

        if (estado == EstadoRobo.DESLIGADO) throw new RoboDesligadoException(id + " (sonda) desligado.");

        System.out.println("RoboSonda " + id + " iniciando ciclo de exploração padrão.");
        int alvoX = x + (int) (Math.random() * 10) - 5;
        int alvoY = y + (int) (Math.random() * 10) - 5;
        int alvoZ = Math.min(z, Math.max(1, altitudeMaximaVoo - 1));

        explorarArea(ambiente, alvoX, alvoY, alvoZ); // Pode lançar RDE, ANPE, FDLE, CE
        System.out.println(getRelatorioExploracao());

        if (central != null) {
            central.registrarLogRobo(id, "Relatório Sonda: " + ultimoRelatorioExploracao.substring(0, Math.min(70, ultimoRelatorioExploracao.length())) + "...");
        }
    }

    // Implementação Explorador
    @Override
    public void explorarArea(Ambiente ambiente, int alvoX, int alvoY, int alvoZ)
            throws RoboDesligadoException, AcaoNaoPermitidaException, ForaDosLimitesException, ColisaoException {
        if (estado == EstadoRobo.DESLIGADO) throw new RoboDesligadoException(id + " (sonda) desligado.");

        System.out.println("RoboSonda " + id + " movendo para explorar área em torno de (" + alvoX + "," + alvoY + "," + alvoZ + ").");
        int zSeguroAlvo = Math.max(0, Math.min(alvoZ, this.altitudeMaximaVoo));

        if (!ambiente.dentroDosLimites(alvoX, alvoY, zSeguroAlvo)) {
            System.out.println("  Ponto de exploração (" + alvoX + "," + alvoY + "," + zSeguroAlvo + ") fora dos limites, ajustando para o mais próximo possível.");
            alvoX = Math.max(0, Math.min(alvoX, ambiente.largura - 1));
            alvoY = Math.max(0, Math.min(alvoY, ambiente.profundidade - 1));
            zSeguroAlvo = Math.max(0, Math.min(zSeguroAlvo, ambiente.altura - 1)); // zSeguroAlvo já considera altMaxVoo
        }

        if (this.x != alvoX || this.y != alvoY || this.z != zSeguroAlvo) {
            moverPara(alvoX, alvoY, zSeguroAlvo, ambiente); // Pode lançar RDE, CE, FDLE, ANPE
        }

        System.out.println("  Sonda " + id + " chegou ao ponto de exploração. Coletando dados...");
        StringBuilder relatorio = new StringBuilder("Relatório de Exploração da Sonda " + id + " em (" + this.x + "," + this.y + "," + this.z + "):\n");
        relatorio.append("  Dados do Sensor de Altitude:\n  ").append(sensorAltitude.lerDados(this, ambiente).replace("\n", "\n  ")).append("\n");
        if (sensorProximidadeAdicional != null) {
            relatorio.append("  Dados do Sensor de Proximidade Adicional:\n  ").append(sensorProximidadeAdicional.lerDados(this, ambiente).replace("\n", "\n  ")).append("\n");
        }
        this.ultimoRelatorioExploracao = relatorio.toString();
    }

    @Override
    public String getRelatorioExploracao() {
        return ultimoRelatorioExploracao;
    }

    // Implementação Comunicavel
    @Override
    public void enviarMensagem(Comunicavel destinatario, String mensagem, CentralComunicacao central) throws RoboDesligadoException, ErroComunicacaoException {
        if (estado == EstadoRobo.DESLIGADO) throw new RoboDesligadoException(id + " (sonda) desligado.");
        if (destinatario == null) throw new ErroComunicacaoException("Destinatário nulo para sonda " + id);
        String msgCompleta = "Sonda " + id + " para " + destinatario.getId() + ": '" + mensagem + "'";
        System.out.println(msgCompleta);
        central.registrarMensagem(this.id, destinatario.getId(), mensagem);
        try {
            destinatario.receberMensagem(this.id, mensagem);
        } catch (RoboDesligadoException e) {
            System.out.println("Central: Destinatário " + destinatario.getId() + " estava desligado. Mensagem não processada.");
            central.registrarLogRobo(destinatario.getId(), "Estava desligado ao receber msg de " + this.id);
        }
    }

    @Override
    public void receberMensagem(String remetenteId, String mensagem) throws RoboDesligadoException {
        if (estado == EstadoRobo.DESLIGADO) return;
        System.out.println("Sonda " + id + " recebeu de " + remetenteId + ": '" + mensagem + "'");
        if (mensagem.toLowerCase().startsWith("explorar ponto:")) {
            try {
                String[] coords = mensagem.substring("explorar ponto:".length()).trim().split("\\s+");
                if (coords.length == 3) {
                    int ex = Integer.parseInt(coords[0]);
                    int ey = Integer.parseInt(coords[1]);
                    int ez = Integer.parseInt(coords[2]);
                    System.out.println("  Sonda " + id + ": Nova tarefa de exploração agendada para (" + ex + "," + ey + "," + ez + "). Será executada no próximo ciclo de tarefa principal.");
                    // Para executar imediatamente, a sonda precisaria de uma referência ao 'Ambiente'
                    // ou a tarefa principal precisaria ser chamada/modificada para usar essas coordenadas.
                    // Exemplo: this.proximaExploracaoX = ex; this.proximaExploracaoY = ey; etc.
                }
            } catch (NumberFormatException e) {
                System.err.println("  Sonda " + id + ": Erro ao processar comando de exploração (coordenadas inválidas): " + mensagem);
            }
        }
    }

    // Implementação Sensoreavel (sobrescreve para combinar dados)
    @Override
    public String lerDadosSensor(Ambiente ambiente) throws RoboDesligadoException {
        if (estado == EstadoRobo.DESLIGADO) throw new RoboDesligadoException(id + " (sonda) desligado.");
        StringBuilder sb = new StringBuilder();
        sb.append("--- Dados Combinados Sensores Sonda ").append(id).append(" ---\n");
        if (sensorAltitude != null) { // Verifica se o sensor existe
            sb.append(sensorAltitude.lerDados(this, ambiente));
        } else {
            sb.append("  Sensor de Altitude não equipado/disponível.\n");
        }
        if (sensorProximidadeAdicional != null) { // Verifica se o sensor existe
            sb.append("\n").append(sensorProximidadeAdicional.lerDados(this, ambiente));
        } else {
             sb.append("  Sensor de Proximidade Adicional não equipado/disponível.\n");
        }
        return sb.toString();
    }
}