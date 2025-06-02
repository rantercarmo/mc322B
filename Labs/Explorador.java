public interface Explorador {
    /**
     * Inicia um ciclo de exploração no ambiente.
     * @param ambiente O ambiente a ser explorado.
     * @param alvoX Coordenada X do ponto de interesse ou centro da área de exploração.
     * @param alvoY Coordenada Y do ponto de interesse ou centro da área de exploração.
     * @param alvoZ Coordenada Z do ponto de interesse ou centro da área de exploração.
     * @throws RoboDesligadoException Se o robô estiver desligado.
     * @throws AcaoNaoPermitidaException Se a exploração não for possível.
     * @throws ForaDosLimitesException Se o alvo estiver fora dos limites.
     * @throws ColisaoException Se houver colisão durante o movimento para exploração.
     */
    void explorarArea(Ambiente ambiente, int alvoX, int alvoY, int alvoZ)
            throws RoboDesligadoException, AcaoNaoPermitidaException, ForaDosLimitesException, ColisaoException;

    /**
     * @return Um relatório da última exploração ou dados coletados.
     */
    String getRelatorioExploracao();
}