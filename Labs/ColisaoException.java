/**
 * Exceção personalizada para indicar que ocorreu uma colisão entre entidades
 * ou entre uma entidade e os limites do ambiente, ou ao tentar ocupar
 * uma posição já ocupada.
 *
 * Herda de Exception, o que a torna uma exceção checada (checked exception).
 */
public class ColisaoException extends Exception {

    /**
     * Construtor que aceita uma mensagem descrevendo os detalhes da colisão.
     *
     * @param mensagem A descrição do evento de colisão.
     */
    public ColisaoException(String mensagem) {
        super(mensagem); // Chama o construtor da superclasse Exception para definir a mensagem.
    }
}