/**
 * Interface fundamental que representa qualquer objeto que pode existir
 * e ser gerenciado dentro do ambiente da simulação.
 *
 * Define um contrato mínimo para todas as entidades, incluindo sua posição,
 * tipo, descrição textual e representação visual no mapa.
 */
public interface Entidade {

    /**
     * Retorna a coordenada X atual da entidade no ambiente.
     *
     * @return A coordenada X.
     */
    int getX();

    /**
     * Retorna a coordenada Y atual da entidade no ambiente.
     * (Frequentemente referida como profundidade).
     *
     * @return A coordenada Y.
     */
    int getY();

    /**
     * Retorna a coordenada Z atual da entidade no ambiente.
     * (Frequentemente referida como altura ou altitude).
     *
     * @return A coordenada Z.
     */
    int getZ();

    /**
     * Retorna o tipo da entidade, conforme definido pela enumeração {@link TipoEntidade}.
     * (Ex: VAZIO, ROBO, OBSTACULO).
     *
     * @return O tipo da entidade.
     */
    TipoEntidade getTipo();

    /**
     * Retorna uma descrição textual da entidade.
     * Pode incluir informações como ID, tipo específico, estado, etc.
     *
     * @return Uma String descrevendo a entidade.
     */
    String getDescricao();

    /**
     * Retorna o caractere que representa visualmente a entidade no mapa do console.
     * A representação pode variar dependendo do tipo e estado da entidade.
     *
     * @return O caractere de representação visual.
     */
    char getRepresentacao();
}