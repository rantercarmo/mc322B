/**
 * Exceção personalizada utilizada para indicar uma tentativa de adicionar
 * uma entidade ao ambiente que já existe ou conflita com uma entidade existente
 * (por exemplo, um robô com o mesmo ID).
 *
 * Herda de Exception, tornando-a uma exceção checada (checked exception).
 */
public class EntidadeJaExisteException extends Exception {

    /**
     * Construtor que aceita uma mensagem detalhando a natureza do conflito
     * da entidade existente.
     *
     * @param mensagem A descrição do erro, geralmente indicando qual entidade
     *                 ou identificador já está em uso.
     */
    public EntidadeJaExisteException(String mensagem) {
        super(mensagem); // Chama o construtor da superclasse Exception para definir a mensagem.
    }

}