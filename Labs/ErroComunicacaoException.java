/**
 * Exceção personalizada para sinalizar problemas ocorridos durante
 * o processo de comunicação entre entidades.
 *
 * Isso pode incluir cenários como um destinatário não encontrado,
 * falha ao enviar a mensagem, ou incapacidade de processar uma mensagem recebida.
 * Herda de Exception, sendo, portanto, uma exceção checada.
 */
public class ErroComunicacaoException extends Exception {

    /**
     * Construtor que aceita uma mensagem descrevendo o erro de comunicação.
     *
     * @param mensagem A descrição detalhada da falha na comunicação.
     */
    public ErroComunicacaoException(String mensagem) {
        super(mensagem); // Passa a mensagem para o construtor da classe base Exception.
    }

    /**
     * Construtor que aceita uma mensagem e uma exceção original (causa).
     * Permite o encadeamento de exceções, o que é útil para rastrear a origem
     * de um erro de comunicação que pode ter sido provocado por um problema anterior.
     *
     * @param mensagem A descrição da falha na comunicação.
     * @param causa A exceção raiz que levou a este erro de comunicação.
     */
    public ErroComunicacaoException(String mensagem, Throwable causa) {
        super(mensagem, causa); // Passa a mensagem e a causa para o construtor da classe base Exception.
    }
}