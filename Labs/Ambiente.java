import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Representa o mundo tridimensional onde as entidades (robôs, obstáculos) interagem.
 * Gerencia as entidades, suas posições e o estado de ocupação do espaço.
 */
public class Ambiente {
    // Dimensões fixas do ambiente.
    public final int largura;      // Eixo X
    public final int profundidade; // Eixo Y
    public final int altura;       // Eixo Z

    // Lista de todas as entidades presentes no ambiente.
    private List<Entidade> entidades;
    // Mapa 3D que registra o tipo de entidade ocupando cada célula.
    private TipoEntidade[][][] mapaOcupacao;

    /**
     * Construtor do Ambiente. Define as dimensões e inicializa as estruturas.
     * @param largura Largura do ambiente (X).
     * @param profundidade Profundidade do ambiente (Y).
     * @param altura Altura do ambiente (Z).
     */
    public Ambiente(int largura, int profundidade, int altura) {
        if (largura <= 0 || profundidade <= 0 || altura <= 0) {
            throw new IllegalArgumentException("Dimensões do ambiente devem ser positivas.");
        }
        this.largura = largura;
        this.profundidade = profundidade;
        this.altura = altura;
        this.entidades = new ArrayList<>();
        this.mapaOcupacao = new TipoEntidade[largura][profundidade][altura];
        inicializarMapa(); // Preenche o mapa de ocupação com VAZIO.
    }

    /**
     * Preenche o mapa de ocupação com o tipo VAZIO em todas as células.
     * Chamado uma vez durante a construção do ambiente.
     */
    private void inicializarMapa() {
        for (int x = 0; x < largura; x++) {
            for (int y = 0; y < profundidade; y++) {
                for (int z = 0; z < altura; z++) {
                    mapaOcupacao[x][y][z] = TipoEntidade.VAZIO;
                }
            }
        }
    }

    /**
     * Verifica se as coordenadas fornecidas estão dentro dos limites do ambiente.
     * @return true se as coordenadas são válidas, false caso contrário.
     */
    public boolean dentroDosLimites(int x, int y, int z) {
        return x >= 0 && x < this.largura &&
               y >= 0 && y < this.profundidade &&
               z >= 0 && z < this.altura;
    }

    /**
     * Verifica se uma posição específica no mapa de ocupação está marcada como não VAZIA.
     * Não considera qual entidade específica está lá, apenas se está ocupado.
     */
    public boolean estaOcupadoNoMapa(int x, int y, int z) {
        if (!dentroDosLimites(x, y, z)) {
            return true; // Considera fora dos limites como "ocupado".
        }
        return mapaOcupacao[x][y][z] != TipoEntidade.VAZIO;
    }

    /**
     * Verifica se uma posição está ocupada por uma entidade diferente daquela que está verificando.
     * Essencial para evitar que uma entidade colida consigo mesma ou com outras ao se mover.
     */
    public boolean estaOcupadoPorOutraEntidade(int x, int y, int z, Entidade entidadeVerificando) {
        if (!dentroDosLimites(x, y, z)) {
            return true; // Colisão com os limites do ambiente.
        }
        Optional<Entidade> entidadeNoLocal = getEntidadeEm(x, y, z);
        if (entidadeNoLocal.isPresent()) {
            // Se existe uma entidade no local, verifica se é a mesma que está tentando se mover.
            return !entidadeNoLocal.get().equals(entidadeVerificando);
        }
        // Se não há uma entidade específica listada, mas o mapa de ocupação ainda indica algo,
        // (ex: um obstáculo que só marca o mapa), considera ocupado.
        return mapaOcupacao[x][y][z] != TipoEntidade.VAZIO;
    }

    /**
     * Adiciona uma entidade ao ambiente.
     * Verifica limites, colisões e se uma entidade similar (ex: robô com mesmo ID) já existe.
     * Atualiza a lista de entidades e o mapa de ocupação.
     */
    public void adicionarEntidade(Entidade entidade)
            throws ColisaoException, ForaDosLimitesException, EntidadeJaExisteException {
        // Validações iniciais (nulidade, duplicidade de ID para robôs, etc.).
        if (entidade == null) { //...
            throw new IllegalArgumentException("Entidade a ser adicionada não pode ser nula.");
        }
        if (entidade instanceof Robo) { //...
            Robo roboParaAdicionar = (Robo) entidade;
            for (Entidade eExistente : entidades) {
                if (eExistente instanceof Robo && ((Robo) eExistente).getId().equals(roboParaAdicionar.getId())) {
                    throw new EntidadeJaExisteException("Robo com ID '" + roboParaAdicionar.getId() + "' já existe no ambiente.");
                }
            }
        } else if (entidades.contains(entidade)) { //...
             throw new EntidadeJaExisteException("Entidade '" + entidade.getDescricao() + "' (ou uma igual) já existe na lista.");
        }

        int x = entidade.getX();
        int y = entidade.getY();
        int z = entidade.getZ();

        // Verifica se a posição está dentro dos limites e se não está já ocupada no mapa.
        if (!dentroDosLimites(x, y, z)) { //...
            throw new ForaDosLimitesException("Posição (" + x + "," + y + "," + z + ") fora dos limites ao adicionar " + entidade.getDescricao());
        }
        if (mapaOcupacao[x][y][z] != TipoEntidade.VAZIO) { //...
            Optional<Entidade> entExistente = getEntidadeEm(x,y,z);
            String ocupante = entExistente.isPresent() ? entExistente.get().getDescricao() : "tipo " + mapaOcupacao[x][y][z];
            throw new ColisaoException("Posição (" + x + "," + y + "," + z + ") já ocupada por "+ ocupante +" ao tentar adicionar " + entidade.getDescricao());
        }

        // Adiciona à lista e atualiza o mapa.
        entidades.add(entidade);
        mapaOcupacao[x][y][z] = entidade.getTipo();
        System.out.println(entidade.getDescricao() + " adicionada ao ambiente em (" + x + "," + y + "," + z + ").");
    }

    /**
     * Remove uma entidade do ambiente.
     * Atualiza a lista de entidades e marca a posição anterior da entidade como VAZIA no mapa.
     */
    public void removerEntidade(Entidade entidade) {
        if (entidade == null) return;
        if (entidades.remove(entidade)) {
            if (dentroDosLimites(entidade.getX(), entidade.getY(), entidade.getZ())) {
                 mapaOcupacao[entidade.getX()][entidade.getY()][entidade.getZ()] = TipoEntidade.VAZIO;
            }
            System.out.println(entidade.getDescricao() + " removida do ambiente.");
        } else {
            System.out.println("Entidade " + entidade.getDescricao() + " não encontrada para remoção.");
        }
    }

    /**
     * Atualiza o mapa de ocupação quando uma entidade se move.
     * Limpa a posição antiga e marca a nova posição.
     */
    public void atualizarPosicaoEntidadeNoMapa(Entidade entidade, int novoX, int novoY, int novoZ) {
        if (dentroDosLimites(entidade.getX(), entidade.getY(), entidade.getZ())) {
             mapaOcupacao[entidade.getX()][entidade.getY()][entidade.getZ()] = TipoEntidade.VAZIO;
        }
        if (dentroDosLimites(novoX, novoY, novoZ)) {
            mapaOcupacao[novoX][novoY][novoZ] = entidade.getTipo();
        }
    }

    /**
     * Facilita o movimento de um robô, delegando a ação para o próprio robô.
     * O robô, por sua vez, usará o ambiente para verificar colisões e limites.
     */
    public void moverEntidade(Robo robo, int novoX, int novoY, int novoZ)
            throws ColisaoException, ForaDosLimitesException, RoboDesligadoException, AcaoNaoPermitidaException {
        if (robo == null) throw new IllegalArgumentException("Robô para mover não pode ser nulo.");
        robo.moverPara(novoX, novoY, novoZ, this);
        System.out.println("Ambiente: Robo " + robo.getId() + " movido para (" + robo.getX() + "," + robo.getY() + "," + robo.getZ() + ")");
    }

    /**
     * Realiza uma verificação geral de colisões entre todas as entidades na lista.
     * Útil para detectar estados inconsistentes onde duas entidades ocupam o mesmo espaço.
     */
    public void verificarColisoesGeral() throws ColisaoException {
        for (Entidade e1 : entidades) {
            for (Entidade e2 : entidades) {
                if (e1 == e2) continue;
                if (e1.getX() == e2.getX() && e1.getY() == e2.getY() && e1.getZ() == e2.getZ()) {
                    throw new ColisaoException("COLISÃO DETECTADA em ("+e1.getX()+","+e1.getY()+","+e1.getZ()+") entre " +
                                               e1.getDescricao() + " e " + e2.getDescricao());
                }
            }
        }
        System.out.println("Verificação de colisões gerais completa. Nenhuma colisão entre entidades na lista.");
    }

    /**
     * Exibe uma representação textual 2D de uma camada (plano Z) do ambiente no console.
     * Mostra a representação de cada entidade ou o tipo de ocupação do mapa.
     */
    public void visualizarAmbiente(int camadaZ) {
        // Verifica se a camada é válida.
        if (!dentroDosLimites(0,0,camadaZ)) { //...
            System.out.println("Camada Z=" + camadaZ + " inválida para visualização. Deve ser entre 0 e " + (altura - 1) + ".");
            return;
        }
        // Lógica de impressão do mapa no console.
        System.out.println("\n--- Visualização do Ambiente (Plano XY na Camada Z=" + camadaZ + ") ---"); //...
        System.out.print(" Y\\X"); //...
        for (int x = 0; x < largura; x++) { //...
            System.out.printf("%3d", x); //...
        } //...
        System.out.println(); //...
        System.out.print("----"); //...
        for (int x = 0; x < largura; x++) { //...
            System.out.print("---"); //...
        } //...
        System.out.println(); //...

        for (int y = 0; y < profundidade; y++) { //...
            System.out.printf("%3d|", y); //...
            for (int x = 0; x < largura; x++) { //...
                char charParaMostrar = mapaOcupacao[x][y][camadaZ].getRepresentacao(); 

                for (Entidade ent : entidades) { //...
                    if (ent.getX() == x && ent.getY() == y && ent.getZ() == camadaZ) { //...
                        charParaMostrar = ent.getRepresentacao(); 
                        break; //...
                    } //...
                } //...
                System.out.printf("%3c", charParaMostrar); //...
            } //...
            System.out.println(); //...
        } //...
        System.out.println("------------------------------------------"); //...
        System.out.println("Legenda: R/r=Robo(Ligado/Desligado), O=Obstáculo, A/a=Aéreo, etc. '.'=Vazio"); //...
    }

    /**
     * @return Uma cópia da lista de todas as entidades no ambiente.
     */
    public List<Entidade> getEntidades() {
        return new ArrayList<>(entidades);
    }

    /**
     * Procura e retorna uma entidade específica presente nas coordenadas (x,y,z).
     * @return Um Optional contendo a entidade se encontrada, ou Optional.empty() caso contrário.
     */
    public Optional<Entidade> getEntidadeEm(int x, int y, int z) {
        if (!dentroDosLimites(x, y, z)) {
            return Optional.empty();
        }
        for (Entidade ent : entidades) {
            if (ent.getX() == x && ent.getY() == y && ent.getZ() == z) {
                return Optional.of(ent);
            }
        }
        return Optional.empty();
    }

    /**
     * Retorna o tipo de entidade registrado no mapa de ocupação para uma dada coordenada.
     */
    public TipoEntidade getTipoEntidadeNoMapa(int x, int y, int z) {
        if (!dentroDosLimites(x, y, z)) {
            return TipoEntidade.DESCONHECIDO;
        }
        return mapaOcupacao[x][y][z];
    }

    /**
     * Procura um robô na lista de entidades pelo seu ID.
     * @param id O ID do robô a ser procurado.
     * @return O objeto Robo se encontrado, ou null caso contrário.
     */
    public Robo getRoboPeloId(String id) {
        if (id == null) return null;
        for (Entidade ent : entidades) {
            if (ent instanceof Robo) {
                Robo robo = (Robo) ent;
                if (robo.getId().equalsIgnoreCase(id.trim())) {
                    return robo;
                }
            }
        }
        return null;
    }

    /**
     * @return Uma lista contendo todos os robôs presentes no ambiente.
     */
    public List<Robo> getTodosRobos() {
        return entidades.stream()
                .filter(Robo.class::isInstance)
                .map(Robo.class::cast)
                .collect(Collectors.toList());
    }
}