/**
 * Interface que define o comportamento de entidades capazes de coletar,
 * carregar e descarregar itens ou recursos.
 *
 * As classes que implementam esta interface devem fornecer a lógica específica
 * para gerenciar sua carga e capacidade.
 */
public interface Coletor {

    /**
     * Tenta carregar um item especificado com um determinado peso.
     *
     * @param nomeItem O nome ou identificador do item a ser carregado.
     * @param pesoItem O peso do item a ser carregado.
     * @return true se o item foi carregado com sucesso, false caso contrário.
     * @throws RoboDesligadoException Se a entidade coletora estiver desligada.
     * @throws AcaoNaoPermitidaException Se a ação de carregar não for permitida no momento
     *                                   (ex: já carregando algo, item inválido).
     * @throws RecursoInsuficienteException Se o peso do item exceder a capacidade de carga restante.
     */
    boolean carregarItem(String nomeItem, int pesoItem)
            throws RoboDesligadoException, AcaoNaoPermitidaException, RecursoInsuficienteException;

    /**
     * Descarrega a carga atual da entidade coletora.
     *
     * @return Uma String descrevendo o resultado da descarga (ex: itens descarregados ou se estava vazio).
     * @throws RoboDesligadoException Se a entidade coletora estiver desligada.
     */
    String descarregarCarga() throws RoboDesligadoException;

    /**
     * Retorna o peso atual da carga que a entidade está transportando.
     *
     * @return O peso da carga atual.
     */
    int getCargaAtual();

    /**
     * Retorna a capacidade máxima de carga que a entidade pode transportar.
     *
     * @return A capacidade máxima de carga.
     */
    int getCapacidadeMaximaCarga();
}