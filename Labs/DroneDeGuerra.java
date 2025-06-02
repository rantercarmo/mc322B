public class DroneDeGuerra extends RoboAereo implements Comunicavel, Combatente {
    private String tipoArmamento;
    private int municacaoAtual;
    private final int municacaoMaxima;
    private Entidade alvoAtual;
    private Ambiente ambienteAtual; // Adicionado para referência em receberMensagem

    public DroneDeGuerra(String id, int x, int y, int zInicial, int altitudeMaximaVoo, SensorAltitude sensor,
                         String tipoArmamento, int municacaoMaxima) {
        super(id, x, y, zInicial, altitudeMaximaVoo, sensor);
        this.tipoArmamento = tipoArmamento;
        this.municacaoMaxima = municacaoMaxima;
        this.municacaoAtual = municacaoMaxima;
        this.alvoAtual = null;
        this.ambienteAtual = null; // Será definido quando a tarefa for executada
    }

    @Override
    protected char getCharRepresentacaoEspecifica() {
        return 'G'; // Para 'Guerra'
    }

    @Override
    public String getDescricao() {
        String alvoDesc = (alvoAtual != null) ? (alvoAtual instanceof Robo ? ((Robo)alvoAtual).getId() : alvoAtual.getClass().getSimpleName()) + "@(" + alvoAtual.getX() + "," + alvoAtual.getY() + "," + alvoAtual.getZ() + ")" : "Nenhum";
        return super.getDescricao() + " (Arma: " + tipoArmamento + " - Munição: " + municacaoAtual + "/" + municacaoMaxima + " - Alvo: " + alvoDesc + ")";
    }

    @Override
    public void executarTarefa(Ambiente ambiente, CentralComunicacao central)
            throws RoboDesligadoException, AcaoNaoPermitidaException, RecursoInsuficienteException,
                   ColisaoException, ForaDosLimitesException, ErroComunicacaoException {
        this.ambienteAtual = ambiente; // Guarda a referência do ambiente para uso em receberMensagem

        if (estado == EstadoRobo.DESLIGADO) throw new RoboDesligadoException(id + " (drone guerra) desligado.");

        System.out.println("Drone de Guerra " + id + " em modo de patrulha/combate.");
        System.out.println(getStatusCombate());
        System.out.println(lerDadosSensor(ambiente));

        if (alvoAtual != null) {
            if (!ambiente.getEntidades().contains(alvoAtual)) {
                System.out.println("  Alvo " + (alvoAtual instanceof Robo ? ((Robo)alvoAtual).getId() : alvoAtual.getDescricao()) + " não está mais presente. Removendo alvo.");
                if(central != null) central.registrarLogRobo(id, "Alvo " + (alvoAtual instanceof Robo ? ((Robo)alvoAtual).getId() : alvoAtual.getDescricao()) + " perdido.");
                alvoAtual = null;
            } else {
                double distAtual = Math.sqrt(Math.pow(x - alvoAtual.getX(), 2) + Math.pow(y - alvoAtual.getY(), 2) + Math.pow(z - alvoAtual.getZ(), 2));
                int distIdeal = 10;

                if (distAtual > distIdeal + 2) {
                    System.out.println("  Tentando aproximar do alvo " + (alvoAtual instanceof Robo ? ((Robo)alvoAtual).getId() : alvoAtual.getDescricao()) );
                    int nX = x + (int) Math.signum(alvoAtual.getX() - x);
                    int nY = y + (int) Math.signum(alvoAtual.getY() - y);
                    int nZ = z + (int) Math.signum(alvoAtual.getZ() - z);
                    try {
                        // moverPara pode lançar RDE, CE, FDLE, ANPE
                        if (!this.equals(ambiente.getEntidadeEm(nX, nY, nZ).orElse(null)))
                            moverPara(nX, nY, nZ, ambiente);
                    } catch (RoboDesligadoException | ColisaoException | ForaDosLimitesException | AcaoNaoPermitidaException e) {
                        System.out.println("  Falha ao tentar aproximar do alvo: " + e.getMessage());
                    }
                }
                System.out.println("  Engajando alvo " + (alvoAtual instanceof Robo ? ((Robo)alvoAtual).getId() : alvoAtual.getDescricao()) + "...");
                atacar(ambiente); // Pode lançar RDE, ANPE, RIE
            }
        } else {
            System.out.println("  Nenhum alvo definido. Patrulhando área.");
            if(central != null) central.registrarLogRobo(id, "Patrulhando sem alvo definido.");
        }
    }

    // Implementação Comunicavel
    @Override
    public void enviarMensagem(Comunicavel destinatario, String mensagem, CentralComunicacao central) throws RoboDesligadoException, ErroComunicacaoException {
        if (estado == EstadoRobo.DESLIGADO) throw new RoboDesligadoException(id + " (drone guerra) desligado.");
        if (destinatario == null) throw new ErroComunicacaoException("Destinatário nulo para drone guerra " + id);
        String msgCompleta = "DroneGuerra " + id + " para " + destinatario.getId() + ": '" + mensagem + "'";
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
        System.out.println("DroneGuerra " + id + " recebeu de " + remetenteId + ": '" + mensagem + "'");
        if (mensagem.toLowerCase().startsWith("definir alvo id:")) {
            String idAlvo = mensagem.substring("definir alvo id:".length()).trim();
            if (this.ambienteAtual == null) {
                 System.err.println("  DroneGuerra " + id + ": Ambiente não definido para procurar alvo. Tarefa precisa ser executada primeiro.");
                 return;
            }
            Robo roboAlvo = this.ambienteAtual.getRoboPeloId(idAlvo);
            if (roboAlvo != null) {
                try {
                    definirAlvo(roboAlvo);
                } catch (AcaoNaoPermitidaException e) {
                    System.err.println("  DroneGuerra " + id + ": Erro ao definir alvo - " + e.getMessage());
                }
            } else {
                 System.err.println("  DroneGuerra " + id + ": Alvo com ID '" + idAlvo + "' não encontrado no ambiente.");
            }
        }
    }

    // Implementação Combatente
    @Override
    public void definirAlvo(Entidade alvo) throws AcaoNaoPermitidaException {
        if (alvo == null) {
            this.alvoAtual = null;
            System.out.println("DroneGuerra " + id + ": Alvo removido.");
            return;
        }
        if (alvo.equals(this)) {
            throw new AcaoNaoPermitidaException("DroneGuerra " + id + " não pode definir a si mesmo como alvo.");
        }
        this.alvoAtual = alvo;
        String alvoId = (alvo instanceof Robo) ? ((Robo)alvo).getId() : alvo.getClass().getSimpleName();
        System.out.println("DroneGuerra " + id + ": Alvo definido -> " + alvoId);
    }

    @Override
    public void atacar(Ambiente ambiente) throws RoboDesligadoException, AcaoNaoPermitidaException, RecursoInsuficienteException {
        if (estado == EstadoRobo.DESLIGADO) throw new RoboDesligadoException(id + " (drone guerra) desligado.");
        if (alvoAtual == null) throw new AcaoNaoPermitidaException("DroneGuerra " + id + ": Nenhum alvo definido para atacar.");
        if (municacaoAtual <= 0) throw new RecursoInsuficienteException("DroneGuerra " + id + ": Sem munição (" + tipoArmamento + ").");

        String alvoId = (alvoAtual instanceof Robo) ? ((Robo)alvoAtual).getId() : alvoAtual.getDescricao();
        System.out.println("DroneGuerra " + id + " disparando " + tipoArmamento + " contra " + alvoId + "!");
        municacaoAtual--;

        if (alvoAtual instanceof Robo) {
            Robo alvoRobo = (Robo) alvoAtual;
            System.out.println("  -> " + alvoRobo.getId() + " foi atingido! (Dano simulado)");
            
        }
        if (municacaoAtual == 0) {
            System.out.println("DroneGuerra " + id + ": Munição (" + tipoArmamento + ") esgotada!");
        }
    }

    @Override
    public String getStatusCombate() {
        String alvoStr = (alvoAtual != null) ? ((alvoAtual instanceof Robo) ? ((Robo)alvoAtual).getId() : alvoAtual.getDescricao()) : "Nenhum";
        return "DroneGuerra " + id + " | Arma: " + tipoArmamento + " | Munição: " + municacaoAtual + "/" + municacaoMaxima + " | Alvo: " + alvoStr;
    }

    public void recarregar(int quantidade) {
        if (quantidade < 0) { System.out.println("Quantidade para recarregar deve ser positiva."); return;}
        this.municacaoAtual = Math.min(this.municacaoAtual + quantidade, this.municacaoMaxima);
        System.out.println("DroneGuerra " + id + " recarregou " + tipoArmamento + ". Munição atual: " + this.municacaoAtual);
    }
}