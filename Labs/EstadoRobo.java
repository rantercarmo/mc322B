/**
 * Enumeração que define os possíveis estados operacionais de um robô.
 *
 * Utilizada para controlar e verificar se um robô está ativo e apto
 * a realizar certas ações, como movimentar-se ou usar sensores.
 */
public enum EstadoRobo {
    /**
     * Indica que o robô está ligado e operacional.
     * Geralmente, ações como movimento e uso de sensores são permitidas neste estado.
     */
    LIGADO,

    /**
     * Indica que o robô está desligado e inoperante.
     * A maioria das ações é restrita ou impossível neste estado, podendo
     * resultar no lançamento de exceções como {@link RoboDesligadoException}.
     */
    DESLIGADO;
}