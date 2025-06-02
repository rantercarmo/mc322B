/**
 * Interface que define a capacidade de uma entidade
 * de possuir e operar sensores.
 *
 * As entidades que implementam esta interface podem ter seus sensores ativados
 * e podem fornecer uma leitura dos dados coletados por esses sensores,
 * considerando o ambiente ao redor.
 */
public interface Sensoreavel {

    /**
     * Ativa ou prepara os sensores da entidade para leitura.
     * Esta ação pode envolver uma inicialização interna dos componentes do sensor
     * ou simplesmente sinalizar que os sensores estão prontos para fornecer dados.
     *
     * @throws RoboDesligadoException Se a entidade (robô) estiver desligada e, portanto,
     *                                não puder acionar seus sensores.
     */
    void acionarSensores() throws RoboDesligadoException;

    /**
     * Lê e retorna os dados coletados pelos sensores da entidade,
     * levando em consideração o ambiente atual.
     *
     * A natureza e o formato dos dados retornados dependerão do(s) tipo(s)
     * de sensor(es) que a entidade possui.
     *
     * @param ambiente A instância do {@link Ambiente} onde a entidade e seus sensores estão operando.
     *                 Os sensores podem usar informações do ambiente para suas leituras (ex: detectar obstáculos).
     * @return Uma String contendo a representação dos dados lidos pelos sensores.
     * @throws RoboDesligadoException Se a entidade (robô) estiver desligada e não puder
     *                                ler os dados dos sensores.
     */
    String lerDadosSensor(Ambiente ambiente) throws RoboDesligadoException;
}