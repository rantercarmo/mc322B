/**
 * Enumeração que define os diferentes tipos básicos de entidades
 * que podem ocupar uma célula no mapa do ambiente.
 *
 * Cada tipo de entidade também possui um caractere de representação visual
 * associado, utilizado para exibir o mapa no console.
 */
public enum TipoEntidade {
    /**
     * Representa uma célula vazia no mapa do ambiente, ou seja,
     * uma posição que não está ocupada por nenhuma entidade significativa.
     */
    VAZIO('.'),

    /**
     * Representa uma célula ocupada por um robô.
     * A representação visual exata de um robô específico pode variar
     * (ex: maiúscula para ligado, minúscula para desligado, ou letras diferentes para tipos).
     */
    ROBO('R'),

    /**
     * Representa uma célula ocupada por um obstáculo.
     */
    OBSTACULO('O'),

    /**
     * Representa um tipo de entidade desconhecido ou uma célula cujo estado
     * não pode ser determinado. Útil para casos de erro ou inicialização.
     */
    DESCONHECIDO('?');

    // Armazena o caractere de representação visual para este tipo de entidade.
    private final char representacao;

    /**
     * Construtor privado para a enumeração.
     * Associa um caractere de representação a cada constante da enumeração.
     *
     * @param r O caractere de representação visual.
     */
    TipoEntidade(char r) {
        this.representacao = r;
    }

    /**
     * Retorna o caractere de representação visual associado a este tipo de entidade.
     * Este caractere é usado, por exemplo, ao desenhar o mapa do ambiente no console.
     *
     * @return O caractere de representação.
     */
    public char getRepresentacao() {
        return representacao;
    }
}