/**
 * Interface que define as capacidades de comunicação para entidades.
 * Permite que entidades enviem e recebam mensagens, interagindo
 * com uma CentralComunicacao para registrar o histórico.
 */
public interface Comunicavel {

    /**
     * Envia uma mensagem para outra entidade comunicável através de uma central de comunicação.
     *
     * @param destinatario A entidade comunicável que receberá a mensagem.
     * @param mensagem O conteúdo da mensagem a ser enviada.
     * @param central A instância da CentralComunicacao que mediará e registrará a mensagem.
     * @throws RoboDesligadoException Se a entidade remetente estiver desligada e não puder enviar.
     * @throws ErroComunicacaoException Se ocorrer um erro durante o processo de comunicação
     *                                  (ex: destinatário inválido, falha na central).
     */
    void enviarMensagem(Comunicavel destinatario, String mensagem, CentralComunicacao central)
            throws RoboDesligadoException, ErroComunicacaoException;

    /**
     * Processa uma mensagem recebida de outra entidade.
     * A lógica de como a mensagem é interpretada e quais ações são tomadas
     * é específica da classe que implementa esta interface.
     *
     * @param remetenteId O ID da entidade que enviou a mensagem.
     * @param mensagem O conteúdo da mensagem recebida.
     * @throws RoboDesligadoException Se a entidade receptora estiver desligada e não puder
     *                                processar a mensagem (a menos que a mensagem seja para ligá-la).
     */
    void receberMensagem(String remetenteId, String mensagem) throws RoboDesligadoException;

    /**
     * Retorna o identificador único da entidade comunicável.
     * Este ID é usado pela CentralComunicacao para identificar remetentes e destinatários
     * e também pode ser usado por outras entidades para direcionar mensagens.
     *
     * @return O ID único da entidade.
     */
    String getId();
}