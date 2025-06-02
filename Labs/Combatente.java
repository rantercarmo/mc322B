public interface Combatente {
    /**
     * Define um alvo para o robô combatente.
     * @param alvo A entidade a ser definida como alvo.
     * @throws AcaoNaoPermitidaException Se o alvo for inválido.
     */
    void definirAlvo(Entidade alvo) throws AcaoNaoPermitidaException;

    /**
     * Executa uma ação de combate contra o alvo definido ou em uma direção.
     * @param ambiente O ambiente onde a ação ocorre.
     * @throws RoboDesligadoException Se o robô estiver desligado.
     * @throws AcaoNaoPermitidaException Se não houver alvo ou a ação for inválida.
     * @throws RecursoInsuficienteException Se não houver munição/energia para a ação.
     */
    void atacar(Ambiente ambiente) throws RoboDesligadoException, AcaoNaoPermitidaException, RecursoInsuficienteException;

    /**
     * @return Uma descrição do status de combate (ex: munição, alvo atual).
     */
    String getStatusCombate();
}