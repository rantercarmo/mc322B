/**
 * Exceção personalizada utilizada para sinalizar uma tentativa de executar
 * uma ação em um robô que requer que ele esteja ligado, mas o robô
 * se encontra no estado DESLIGADO.
 *
 * Exemplos incluem tentar mover um robô desligado, acionar seus sensores,
 * ou executar sua tarefa principal.
 * Herda de Exception, classificando-a como uma exceção checada.
 */
public class RoboDesligadoException extends Exception {

    /**
     * Construtor que aceita uma mensagem explicando que a ação não pôde
     * ser realizada porque o robô está desligado.
     *
     * Geralmente, a mensagem pode incluir o ID do robô para facilitar a depuração.
     *
     * @param mensagem A descrição do erro, indicando que o robô está desligado.
     */
    public RoboDesligadoException(String mensagem) {
        super(mensagem); // Chama o construtor da superclasse Exception para definir a mensagem.
    }

}