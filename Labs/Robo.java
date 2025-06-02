import java.util.Objects;

public abstract class Robo implements Entidade {
    protected final String id; // ID único do robô
    protected int x, y, z;
    protected EstadoRobo estado;
    protected final TipoEntidade tipoEntidade = TipoEntidade.ROBO; // Fixo para todos os robôs

    public Robo(String id, int x, int y, int z) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID do robô não pode ser nulo ou vazio.");
        }
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
        this.estado = EstadoRobo.DESLIGADO; // Robôs começam desligados por padrão
    }

    // Métodos da interface Entidade
    @Override
    public int getX() { return x; }
    @Override
    public int getY() { return y; }
    @Override
    public int getZ() { return z; }

    @Override
    public TipoEntidade getTipo() {
        return tipoEntidade;
    }

    // getDescricao() será mais específica nas subclasses
    @Override
    public String getDescricao() {
        return "Robo ID: " + id + " [" + getClass().getSimpleName() + "] em (" + x + "," + y + "," + z + ") - Estado: " + estado;
    }

    // getRepresentacao() será mais específica nas subclasses
    @Override
    public char getRepresentacao() {
        return (estado == EstadoRobo.LIGADO) ? Character.toUpperCase(getCharRepresentacaoEspecifica()) : Character.toLowerCase(getCharRepresentacaoEspecifica());
    }

    /**
     * @return O caractere base que representa o tipo específico de robô (ex: 'T' para Terrestre).
     *         A representação final levará em conta o estado (maiúscula para ligado).
     */
    protected abstract char getCharRepresentacaoEspecifica();


    // Métodos específicos de Robo
    public void moverPara(int novoX, int novoY, int novoZ, Ambiente ambiente)
            throws RoboDesligadoException, ColisaoException, ForaDosLimitesException, AcaoNaoPermitidaException {
        if (estado == EstadoRobo.DESLIGADO) {
            throw new RoboDesligadoException("Robo " + id + " está desligado. Não pode se mover.");
        }
        if (!ambiente.dentroDosLimites(novoX, novoY, novoZ)) {
            throw new ForaDosLimitesException("Movimento para (" + novoX + "," + novoY + "," + novoZ + ") fora dos limites do ambiente para robô " + id + ".");
        }
        if (ambiente.estaOcupadoPorOutraEntidade(novoX, novoY, novoZ, this)) {
            throw new ColisaoException("Posição (" + novoX + "," + novoY + "," + novoZ + ") já está ocupada por outra entidade. Robô " + id + " não pode mover.");
        }

        // Se o robô for aéreo, geralmente não pode ocupar Z=0 se já houver um terrestre lá (e vice-versa, a menos que seja um "estacionamento")
        //  `estaOcupadoPorOutraEntidade` faz a verificação.

        ambiente.atualizarPosicaoEntidadeNoMapa(this, novoX, novoY, novoZ);
        this.x = novoX;
        this.y = novoY;
        this.z = novoZ;
    }

    public void ligar() {
        if (this.estado == EstadoRobo.LIGADO) {
            System.out.println("Robo " + id + " já está ligado.");
        } else {
            this.estado = EstadoRobo.LIGADO;
            System.out.println("Robo " + id + " ligado.");
        }
    }

    public void desligar() {
        if (this.estado == EstadoRobo.DESLIGADO) {
            System.out.println("Robo " + id + " já está desligado.");
        } else {
            this.estado = EstadoRobo.DESLIGADO;
            System.out.println("Robo " + id + " desligado.");
        }
    }

    public EstadoRobo getEstado() {
        return estado;
    }

    public String getId() {
        return id;
    }

    /**
     * Método abstrato para a tarefa principal do robô.
     * As exceções que pode lançar dependerão da complexidade da tarefa.
     */
    public abstract void executarTarefa(Ambiente ambiente, CentralComunicacao central)
            throws RoboDesligadoException, AcaoNaoPermitidaException, ColisaoException,
                   ForaDosLimitesException, ErroComunicacaoException, RecursoInsuficienteException;

    // Necessário para a lista de entidades no Ambiente poder usar contains() e remove() corretamente,
    // e para a CentralComunicacao identificar remetentes/destinatários.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Robo)) return false; // Verifica se é uma instância de Robo ou subclasse
        Robo robo = (Robo) o;
        return id.equals(robo.id); // Comparação baseada apenas no ID
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return getDescricao(); 
    }
}