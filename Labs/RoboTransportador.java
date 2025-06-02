public class RoboTransportador extends RoboTerrestre implements Coletor {
    private int capacidadeCargaKg;
    private int cargaAtualKg;
    private String itemCarregado;

    public RoboTransportador(String id, int x, int y, int velocidadeMaxima, SensorProximidade sensor, int capacidadeCargaKg) {
        super(id, x, y, velocidadeMaxima, sensor);
        this.capacidadeCargaKg = capacidadeCargaKg;
        this.cargaAtualKg = 0;
        this.itemCarregado = "Nenhum";
    }

    @Override
    protected char getCharRepresentacaoEspecifica() {
        return 'C'; // Para 'Carrier' ou 'Coletor'
    }

    @Override
    public String getDescricao() {
        return super.getDescricao() + " (Carga: " + cargaAtualKg + "/" + capacidadeCargaKg + "kg - Item: " + itemCarregado + ")";
    }

    @Override
    public void executarTarefa(Ambiente ambiente, CentralComunicacao central)
            throws RoboDesligadoException, AcaoNaoPermitidaException, ColisaoException,
                   ForaDosLimitesException, ErroComunicacaoException, RecursoInsuficienteException {

        if (this.estado == EstadoRobo.DESLIGADO) {
            throw new RoboDesligadoException("Robo Transportador " + id + " está desligado.");
        }
        System.out.println("Robo Transportador " + id + " verificando status de carga.");
        if (cargaAtualKg > 0) {
            System.out.println("  Transportando " + itemCarregado + " (" + cargaAtualKg + "kg). Procurando ponto de entrega...");
        } else {
            System.out.println("  Vazio. Procurando por itens para coletar...");
        }
        System.out.println(lerDadosSensor(ambiente)); // Usa o sensor de proximidade herdado
    }

    // Implementação Coletor
    @Override
    public boolean carregarItem(String nomeItem, int pesoItem) throws RoboDesligadoException, AcaoNaoPermitidaException, RecursoInsuficienteException {
        if (estado == EstadoRobo.DESLIGADO) throw new RoboDesligadoException(id + " (transportador) desligado.");
        if (pesoItem <= 0) throw new AcaoNaoPermitidaException("Peso do item deve ser positivo.");
        if (cargaAtualKg > 0) throw new AcaoNaoPermitidaException("Já está carregando '" + itemCarregado + "'. Descarregue primeiro.");
        if (pesoItem > capacidadeCargaKg) {
            throw new RecursoInsuficienteException("Item '" + nomeItem + "' (" + pesoItem + "kg) excede capacidade de carga (" + capacidadeCargaKg + "kg).");
        }
        cargaAtualKg = pesoItem;
        itemCarregado = nomeItem;
        System.out.println("Robo Transportador " + id + ": Item '" + nomeItem + "' (" + pesoItem + "kg) carregado.");
        return true;
    }

    @Override
    public String descarregarCarga() throws RoboDesligadoException {
        if (estado == EstadoRobo.DESLIGADO) throw new RoboDesligadoException(id + " (transportador) desligado.");
        if (cargaAtualKg == 0) {
            return "Robo Transportador " + id + ": Sem carga para descarregar.";
        }
        String msg = "Robo Transportador " + id + ": Carga '" + itemCarregado + "' (" + cargaAtualKg + "kg) descarregada.";
        System.out.println(msg);
        cargaAtualKg = 0;
        itemCarregado = "Nenhum";
        return msg;
    }

    @Override
    public int getCargaAtual() { return cargaAtualKg; }
    @Override
    public int getCapacidadeMaximaCarga() { return capacidadeCargaKg; }
}