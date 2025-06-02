/**
 * Exceção personalizada para indicar que uma operação ou tentativa de acesso
 * ocorreu em uma coordenada que está fora dos limites definidos para o ambiente.
 *
 * Por exemplo, tentar mover um robô para uma posição (x,y,z) que não existe
 * no mapa do ambiente.
 * Herda de Exception, o que a classifica como uma exceção checada.
 */
public class ForaDosLimitesException extends Exception {

    /**
     * Construtor que aceita uma mensagem detalhando qual coordenada ou operação
     * resultou na tentativa de acesso fora dos limites.
     *
     * @param mensagem A descrição do erro, geralmente incluindo as coordenadas problemáticas.
     */
    public ForaDosLimitesException(String mensagem) {
        super(mensagem); // Chama o construtor da superclasse Exception para armazenar a mensagem.
    }

}