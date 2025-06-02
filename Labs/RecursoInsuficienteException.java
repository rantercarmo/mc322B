/**
 * Exceção personalizada para indicar que uma operação não pôde ser concluída
 * devido à falta de um recurso necessário.
 *
 * Exemplos de uso incluem:
 * - Um robô tentando disparar uma arma sem munição.
 * - Um robô coletor tentando carregar um item que excede sua capacidade de carga.
 * - Uma ação que consome energia quando o robô não tem energia suficiente.
 *
 * Herda de Exception, tornando-a uma exceção checada.
 */
public class RecursoInsuficienteException extends Exception {

    /**
     * Construtor que aceita uma mensagem detalhando qual recurso está faltando
     * ou por que ele é insuficiente para a operação tentada.
     *
     * @param mensagem A descrição do erro de recurso insuficiente.
     */
    public RecursoInsuficienteException(String mensagem) {
        super(mensagem); // Chama o construtor da superclasse Exception para definir a mensagem.
    }
}