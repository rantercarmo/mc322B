public class RoboVulcanico extends RoboTerrestre {
    private int temperaturaMaximaSuportada; // em Celsius

    public RoboVulcanico(String id, int x, int y, int velocidadeMaxima, SensorProximidade sensor, int temperaturaMaximaSuportada) {
        super(id, x, y, velocidadeMaxima, sensor);
        this.temperaturaMaximaSuportada = temperaturaMaximaSuportada;
    }

    @Override
    protected char getCharRepresentacaoEspecifica() {
        return 'V';
    }

    @Override
    public String getDescricao() {
        return super.getDescricao() + " (TempMaxSup: " + temperaturaMaximaSuportada + "°C)";
    }

    // Simula a temperatura ambiente em uma dada coordenada (para exemplo)
    private int getTemperaturaAmbiente(int posX, int posY, int posZ, Ambiente ambiente) {
        double distCentroX = Math.abs(posX - ambiente.largura / 2.0);
        double distCentroY = Math.abs(posY - ambiente.profundidade / 2.0);
        double fatorDist = (distCentroX + distCentroY) / (ambiente.largura / 2.0 + ambiente.profundidade / 2.0);
        int tempBase = 30;
        int tempMaxVulc = 800;
        return (int) (tempBase + (tempMaxVulc - tempBase) * (1 - Math.min(1, fatorDist * 2)));
    }


    @Override
    public void moverPara(int novoX, int novoY, int novoZ, Ambiente ambiente)
            throws RoboDesligadoException, ColisaoException, ForaDosLimitesException, AcaoNaoPermitidaException {
        super.moverPara(novoX, novoY, novoZ, ambiente); 

        int tempLocal = getTemperaturaAmbiente(this.x, this.y, this.z, ambiente);
        if (tempLocal > this.temperaturaMaximaSuportada) {
            System.err.println("ALERTA! RoboVulcanico " + id + " moveu para local com temperatura (" + tempLocal + "°C) ACIMA do suportado (" + temperaturaMaximaSuportada + "°C)!");
        }
    }

    @Override
    public void executarTarefa(Ambiente ambiente, CentralComunicacao central)
            throws RoboDesligadoException, AcaoNaoPermitidaException, ColisaoException,
                   ForaDosLimitesException, ErroComunicacaoException, RecursoInsuficienteException {

        if (this.estado == EstadoRobo.DESLIGADO) {
            throw new RoboDesligadoException("Robo Vulcânico " + id + " está desligado.");
        }
        System.out.println("Robo Vulcânico " + id + " executando coleta de dados em ambiente hostil.");
        int tempAtual = getTemperaturaAmbiente(x, y, z, ambiente);
        System.out.println("  Temperatura local: " + tempAtual + "°C. Suportada: " + temperaturaMaximaSuportada + "°C.");

        if (tempAtual > temperaturaMaximaSuportada) {
            System.err.println("  CRÍTICO: Temperatura excedida! Tentando recuar...");
            try {
                // Tenta mover para uma posição adjacente menos perigosa (simplificado)
                if (ambiente.dentroDosLimites(x - 1, y, 0) && !ambiente.estaOcupadoPorOutraEntidade(x - 1, y, 0, this)) {
                    moverPara(x - 1, y, 0, ambiente);
                } else if (ambiente.dentroDosLimites(x, y - 1, 0) && !ambiente.estaOcupadoPorOutraEntidade(x, y - 1, 0, this)) {
                    moverPara(x, y - 1, 0, ambiente);
                } else {
                    System.err.println("  Não foi possível recuar automaticamente.");
                    throw new AcaoNaoPermitidaException("Preso em local superaquecido e sem rota de fuga imediata.");
                }
                System.out.println("  Recuo para " + this.x + "," + this.y);
            } catch (RoboDesligadoException | ColisaoException | ForaDosLimitesException | AcaoNaoPermitidaException e) {
                System.err.println("  Falha crítica ao tentar recuar: " + e.getMessage());
                
                throw new AcaoNaoPermitidaException("Falha crítica ao tentar recuar de local superaquecido.", e);
            }
        } else {
            System.out.println("  Coletando amostras (simulado).");
        }
        // acionarSensores(); // Herda
        System.out.println(lerDadosSensor(ambiente)); // Herda
    }
}