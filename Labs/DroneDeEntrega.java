public class DroneDeEntrega extends RoboAereo implements Comunicavel, Coletor {
    private String destinoAtualDescricao;
    private int capacidadeCargaKg;
    private int cargaAtualKg;
    private String itemCarregado;

    public DroneDeEntrega(String id, int x, int y, int zInicial, int altitudeMaximaVoo, SensorAltitude sensor,
                          String destinoInicialDescricao, int capacidadeCargaKg) {
        super(id, x, y, zInicial, altitudeMaximaVoo, sensor);
        this.destinoAtualDescricao = destinoInicialDescricao;
        this.capacidadeCargaKg = capacidadeCargaKg;
        this.cargaAtualKg = 0;
        this.itemCarregado = "Nenhum";
    }

    @Override
    protected char getCharRepresentacaoEspecifica() {
        return 'E'; // Para 'Entrega'
    }

    @Override
    public String getDescricao() {
        return super.getDescricao() + " (Carga: " + cargaAtualKg + "/" + capacidadeCargaKg + "kg - Item: " + itemCarregado + " - Destino: " + destinoAtualDescricao + ")";
    }

    public void setDestino(String novaDescricao) {
        this.destinoAtualDescricao = novaDescricao;
        System.out.println("Drone " + id + ": Novo destino definido para " + novaDescricao);
    }

    @Override
    public void executarTarefa(Ambiente ambiente, CentralComunicacao central)
            throws RoboDesligadoException, AcaoNaoPermitidaException, ColisaoException,
                   ForaDosLimitesException, ErroComunicacaoException, RecursoInsuficienteException {
        // Assinatura agora compatível com Robo.java

        if (estado == EstadoRobo.DESLIGADO) throw new RoboDesligadoException(id + " (drone entrega) desligado.");

        System.out.println("Drone de Entrega " + id + " verificando status.");
        System.out.println("  Destino: " + destinoAtualDescricao + ". Carga: " + itemCarregado + " (" + cargaAtualKg + "kg).");

        if (cargaAtualKg > 0) {
            System.out.println("  Simulando voo para o destino " + destinoAtualDescricao + "...");
            // Lógica de voo e entrega aqui
            if (central != null) {
                central.registrarLogRobo(id, "Em rota para " + destinoAtualDescricao + " com " + itemCarregado);
                // Exemplo de comunicação ao chegar (simulado)
                // if (chegouAoDestino) {
                //    descarregarCarga();
                //    enviarMensagem(algumReceptor, "Entrega de " + itemCarregado + " concluída em " + destinoAtualDescricao, central);
                // }
            }
        } else {
            System.out.println("  Aguardando ordens de coleta ou novo destino.");
            if (central != null) {
                central.registrarLogRobo(id, "Ocioso, aguardando tarefa de entrega.");
            }
        }
        // acionarSensores(); // Herda
        System.out.println(lerDadosSensor(ambiente)); // Sensor de altitude
    }

    // Implementação Comunicavel
    @Override
    public void enviarMensagem(Comunicavel destinatario, String mensagem, CentralComunicacao central) throws RoboDesligadoException, ErroComunicacaoException {
        if (estado == EstadoRobo.DESLIGADO) throw new RoboDesligadoException(id + " (drone entrega) desligado. Não pode enviar msg.");
        if (destinatario == null) throw new ErroComunicacaoException("Destinatário nulo para drone " + id);

        String msgCompleta = "Drone " + id + " para " + destinatario.getId() + ": '" + mensagem + "'";
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
        if (estado == EstadoRobo.DESLIGADO && !mensagem.toLowerCase().contains("ativar drone " + id.toLowerCase())) {
            return;
        }
        System.out.println("Drone " + id + " recebeu de " + remetenteId + ": '" + mensagem + "'");
        if (mensagem.toLowerCase().startsWith("novo destino:")) {
            this.destinoAtualDescricao = mensagem.substring("novo destino:".length()).trim();
            System.out.println("  Drone " + id + ": Destino atualizado para " + this.destinoAtualDescricao);
        } else if (mensagem.toLowerCase().startsWith("coletar item:")) {
            try {
                String[] partes = mensagem.substring("coletar item:".length()).trim().split("\\s+");
                if (partes.length >= 2) {
                    String nomeItem = partes[0];
                    int peso = Integer.parseInt(partes[1]);
                    carregarItem(nomeItem, peso); // Pode lançar RDE, ANPE, RIE
                }
            } catch (NumberFormatException e ) {System.err.println("  Drone " + id + ": Erro ao processar comando de coleta (peso inválido): " + mensagem);
            } catch (RoboDesligadoException | AcaoNaoPermitidaException | RecursoInsuficienteException e) {
                System.err.println("  Drone " + id + ": Erro ao tentar carregar item: " + e.getMessage());
            }
        }
    }

    // Implementação Coletor
    @Override
    public boolean carregarItem(String nomeItem, int pesoItem) throws RoboDesligadoException, AcaoNaoPermitidaException, RecursoInsuficienteException {
        if (estado == EstadoRobo.DESLIGADO) throw new RoboDesligadoException(id + " (drone entrega) desligado.");
        if (pesoItem <= 0) throw new AcaoNaoPermitidaException("Peso do item deve ser positivo.");
        if (cargaAtualKg > 0) throw new AcaoNaoPermitidaException("Drone " + id + " já está carregando '" + itemCarregado + "'. Descarregue primeiro.");
        if (pesoItem > capacidadeCargaKg) {
            throw new RecursoInsuficienteException("Item '" + nomeItem + "' (" + pesoItem + "kg) excede capacidade de carga do drone " + id + " (" + capacidadeCargaKg + "kg).");
        }
        cargaAtualKg = pesoItem;
        itemCarregado = nomeItem;
        System.out.println("Drone " + id + ": Item '" + nomeItem + "' (" + pesoItem + "kg) carregado.");
        return true;
    }

    @Override
    public String descarregarCarga() throws RoboDesligadoException {
        if (estado == EstadoRobo.DESLIGADO) throw new RoboDesligadoException(id + " (drone entrega) desligado.");
        if (cargaAtualKg == 0) {
            return "Drone " + id + ": Sem carga para descarregar.";
        }
        String msg = "Drone " + id + ": Carga '" + itemCarregado + "' (" + cargaAtualKg + "kg) descarregada no destino " + destinoAtualDescricao + ".";
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