import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Gerencia o registro e a exibição de mensagens trocadas entre entidades
 * ou logs gerados por robôs.
 * Funciona como um hub central para o histórico de comunicação.
 */
public class CentralComunicacao {
    // Lista para armazenar todas as mensagens e logs registrados.
    private List<String> logMensagens;
    // Formato padrão para timestamps nas mensagens.
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Construtor. Inicializa a lista de logs de mensagens.
     */
    public CentralComunicacao() {
        this.logMensagens = new ArrayList<>();
    }

    /**
     * Registra uma mensagem enviada de um remetente para um destinatário.
     * O método é sincronizado para evitar problemas se múltiplas entidades
     * tentarem registrar mensagens concorrentemente (embora não seja o foco principal aqui).
     *
     * @param remetenteId ID do remetente.
     * @param destinatarioId ID do destinatário.
     * @param msg A mensagem enviada.
     */
    public synchronized void registrarMensagem(String remetenteId, String destinatarioId, String msg) {
        String timestamp = LocalDateTime.now().format(formatter); // Pega o momento atual.
        // Formata a string do log incluindo timestamp, remetente, destinatário e a mensagem.
        String log = String.format("[%s] De: %s -> Para: %s | Msg: \"%s\"",
                                 timestamp, remetenteId, destinatarioId, msg);
        logMensagens.add(log); // Adiciona o log formatado à lista.
    }

    /**
     * Registra um log geral ou uma mensagem de status de um robô para a central.
     * Útil para eventos que não são direcionados a outro robô específico.
     *
     * @param roboId ID do robô que está gerando o log.
     * @param msg A mensagem de log ou status.
     */
    public synchronized void registrarLogRobo(String roboId, String msg) {
        String timestamp = LocalDateTime.now().format(formatter);
        // Formata a string do log indicando que é um log de um robô específico.
        String log = String.format("[%s] Log Robo %s: \"%s\"",
                                 timestamp, roboId, msg);
        logMensagens.add(log);
    }

    /**
     * Exibe todas as mensagens e logs registrados no console.
     * Se não houver mensagens, informa o usuário.
     */
    public void exibirMensagens() {
        System.out.println("\n--- Histórico de Comunicações (Central) ---");
        if (logMensagens.isEmpty()) {
            System.out.println("Nenhuma mensagem registrada.");
        } else {
            // Itera sobre a lista e imprime cada log.
            for (String log : logMensagens) {
                System.out.println(log);
            }
        }
        System.out.println("-------------------------------------------");
    }
}