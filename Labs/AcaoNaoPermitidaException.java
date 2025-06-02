/**
 * Exceção personalizada para indicar que uma ação tentada não é permitida
 * ou válida dentro do contexto atual da simulação.
 *
 * Herda de Exception, tornando-a uma exceção checada (checked exception).
 */
public class AcaoNaoPermitidaException extends Exception {

    /**
     * Construtor que aceita uma mensagem detalhando a natureza da ação não permitida.
     *
     * @param mensagem A descrição do erro.
     */
    public AcaoNaoPermitidaException(String mensagem) {
        super(mensagem); // Chama o construtor da superclasse Exception para definir a mensagem.
    }

    /**
     * Construtor que aceita uma mensagem e uma causa raiz.
     * Útil para encadear exceções, preservando a pilha de chamadas da exceção original
     * que pode ter levado a esta AcaoNaoPermitidaException.
     *
     * @param mensagem A descrição do erro.
     * @param causa A exceção original que causou esta exceção.
     */
    public AcaoNaoPermitidaException(String mensagem, Throwable causa) {
        super(mensagem, causa); // Chama o construtor da superclasse Exception para definir a mensagem e a causa.
    }
}