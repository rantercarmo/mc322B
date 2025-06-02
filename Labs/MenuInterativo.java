import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MenuInterativo {
    private Ambiente ambiente;
    private CentralComunicacao centralComunicacao;
    private Scanner scanner;
    private Robo roboSelecionado = null;

    public MenuInterativo(Ambiente ambiente, CentralComunicacao centralComunicacao, Scanner scanner) {
        this.ambiente = ambiente;
        this.centralComunicacao = centralComunicacao;
        this.scanner = scanner;
    }

    public void iniciar() {
        boolean executando = true;
        System.out.println("\n=== BEM-VINDO AO SIMULADOR DE ROBÔS ==="); // Saudação inicial
        while (executando) {
            exibirMenuPrincipal();
            String comando = scanner.nextLine().trim().toLowerCase();
            System.out.println(); // Linha em branco após a entrada do comando

            try {
                switch (comando) {
                    case "1": listarRobos(); break;
                    case "2": escolherRobo(); break;
                    case "3": visualizarStatusGeral(); break;
                    case "4": visualizarMapa2D(); break;
                    case "5": menuFuncionalidadesRobo(); break;
                    case "6": menuControleMovimentoRobo(); break;
                    case "7": alterarEstadoRoboSelecionado(); break;
                    case "8": centralComunicacao.exibirMensagens(); break;
                    case "9": ambiente.verificarColisoesGeral(); break;
                    case "0": executando = false; System.out.println("Encerrando simulador..."); break;
                    default: System.out.println("(!) Comando inválido. Tente novamente.");
                }
            } catch (RoboDesligadoException | ColisaoException | ForaDosLimitesException |
                     ErroComunicacaoException | AcaoNaoPermitidaException |
                     RecursoInsuficienteException e) {
                System.err.println("\n(!) ERRO: " + e.getMessage());
            } catch (InputMismatchException e) {
                System.err.println("\n(!) ERRO: Entrada inválida. Era esperado um número.");
            } catch (NumberFormatException e) {
                System.err.println("\n(!) ERRO: Formato numérico inválido.");
            } catch (Exception e) {
                System.err.println("\n(!) Ocorreu um erro inesperado: " + e.getClass().getSimpleName() + " - " + e.getMessage());
                e.printStackTrace();
            }

            if (executando) {
                System.out.print("\n-- Pressione Enter para continuar --");
                scanner.nextLine(); // Pausa para o usuário ler a saída
            }
        }
    }

    private void exibirMenuPrincipal() {
        System.out.println("\n------------------------------------");
        System.out.println("        MENU PRINCIPAL");
        System.out.println("------------------------------------");
        if (roboSelecionado != null) {
            System.out.println("Robô Selecionado: " + roboSelecionado.getId() +
                               " (" + roboSelecionado.getClass().getSimpleName() +
                               ") Estado: " + roboSelecionado.getEstado() +
                               " em (" + roboSelecionado.getX() + "," + roboSelecionado.getY() + "," + roboSelecionado.getZ() + ")");
        } else {
            System.out.println("Nenhum robô selecionado.");
        }
        System.out.println("------------------------------------");
        System.out.println("1. Listar Robôs (com filtros)");
        System.out.println("2. Escolher/Selecionar Robô");
        System.out.println("3. Visualizar Status (Robô/Ambiente)");
        System.out.println("4. Visualizar Mapa 2D (por camada Z)");
        System.out.println("5. Funcionalidades do Robô Selecionado");
        System.out.println("6. Movimentar Robô Selecionado");
        System.out.println("7. Ligar/Desligar Robô Selecionado");
        System.out.println("8. Histórico de Comunicação");
        System.out.println("9. Verificar Colisões Gerais");
        System.out.println("0. Sair");
        System.out.println("------------------------------------");
        System.out.print("Escolha uma opção: ");
    }

    private void listarRobos() {
        System.out.println("\n--- Lista de Robôs ---");
        List<Robo> robos = ambiente.getTodosRobos();

        if (robos.isEmpty()) {
            System.out.println("Nenhum robô no ambiente.");
            return;
        }
        System.out.println("\nFiltrar por:");
        System.out.println("  1. Todos");
        System.out.println("  2. Tipo específico (ex: RoboTerrestre)");
        System.out.println("  3. Estado (LIGADO/DESLIGADO)");
        System.out.print("Opção de filtro (ou Enter para listar todos): ");
        String filtroOpt = scanner.nextLine().trim();
        List<Robo> robosFiltrados = new ArrayList<>(robos);

        switch (filtroOpt) {
            case "2":
                System.out.print("Digite o nome da classe do tipo de robô: ");
                String tipoClasse = scanner.nextLine().trim();
                robosFiltrados = robos.stream()
                     .filter(r -> r.getClass().getSimpleName().equalsIgnoreCase(tipoClasse))
                     .collect(Collectors.toList());
                break;
            case "3":
                System.out.print("Digite o estado (LIGADO ou DESLIGADO): ");
                String estadoStr = scanner.nextLine().trim().toUpperCase();
                try {
                    EstadoRobo estadoFiltro = EstadoRobo.valueOf(estadoStr);
                    robosFiltrados = robos.stream()
                         .filter(r -> r.getEstado() == estadoFiltro)
                         .collect(Collectors.toList());
                } catch (IllegalArgumentException e) {
                    System.out.println("(!) Estado inválido. Listando sem filtro de estado.");
                }
                break;
            case "1":
            default:
                break;
        }

        System.out.println("\n--- Robôs Encontrados ---");
        if (robosFiltrados.isEmpty()) {
            System.out.println("Nenhum robô encontrado com os filtros aplicados.");
        } else {
            robosFiltrados.forEach(r -> System.out.println("- " + r.getDescricao()));
        }
    }

    private void escolherRobo() {
        System.out.println("\n--- Escolher Robô ---");
        List<Robo> robos = ambiente.getTodosRobos();
        if (robos.isEmpty()) {
            System.out.println("Nenhum robô disponível para seleção.");
            roboSelecionado = null;
            return;
        }
        System.out.println("\nRobôs disponíveis:");
        for (int i = 0; i < robos.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + robos.get(i).getId() + " (" + robos.get(i).getClass().getSimpleName() + ")");
        }
        System.out.print("\nDigite o número do robô (ou ID, ou 0 para deselecionar): ");
        String input = scanner.nextLine().trim();
        try {
            int escolhaNum = Integer.parseInt(input);
            if (escolhaNum == 0) {
                roboSelecionado = null;
                System.out.println("Nenhum robô selecionado.");
            } else if (escolhaNum > 0 && escolhaNum <= robos.size()) {
                roboSelecionado = robos.get(escolhaNum - 1);
                System.out.println(">> Robô " + roboSelecionado.getId() + " selecionado. <<");
            } else {
                System.out.println("(!) Seleção numérica inválida.");
            }
        } catch (NumberFormatException e) {
            Robo encontradoPorId = ambiente.getRoboPeloId(input);
            if (encontradoPorId != null) {
                roboSelecionado = encontradoPorId;
                System.out.println(">> Robô " + roboSelecionado.getId() + " selecionado pelo ID. <<");
            } else {
                 System.out.println("(!) Entrada inválida. Nenhum robô com esse número ou ID.");
            }
        }
    }

    private void visualizarStatusGeral() {
        System.out.println("\n--- Status Geral ---");
        if (roboSelecionado != null) {
            System.out.println("\n-- Robô Selecionado --");
            System.out.println("  " + roboSelecionado.getDescricao());
            if (roboSelecionado instanceof Sensoreavel) {
                try {
                    System.out.println("\n  -- Dados dos Sensores --");
                    // Adiciona um recuo para a saída do sensor para melhor leitura
                    String dadosSensor = ((Sensoreavel) roboSelecionado).lerDadosSensor(ambiente);
                    for (String linha : dadosSensor.split("\n")) {
                        System.out.println("    " + linha);
                    }
                } catch (RoboDesligadoException e) {
                    System.out.println("  (!) Sensores não puderam ser lidos: " + e.getMessage());
                }
            }
            if (roboSelecionado instanceof Combatente) {
                System.out.println("  " + ((Combatente) roboSelecionado).getStatusCombate());
            }
             if (roboSelecionado instanceof Coletor) {
                Coletor col = (Coletor) roboSelecionado;
                System.out.println("  Carga: " + col.getCargaAtual() + "/" + col.getCapacidadeMaximaCarga() + "kg");
            }
            if (roboSelecionado instanceof Explorador) {
                System.out.println("  Último Relatório de Exploração (resumo): " + ((Explorador) roboSelecionado).getRelatorioExploracao().substring(0, Math.min(100, ((Explorador) roboSelecionado).getRelatorioExploracao().length())).replace("\n", " ") + "...");
            }
        } else {
            System.out.println("\nNenhum robô selecionado para exibir status detalhado.");
        }
        System.out.println("\n-- Status do Ambiente --");
        System.out.println("  Dimensões: " + ambiente.largura + "(X) x " + ambiente.profundidade + "(Y) x " + ambiente.altura + "(Z)");
        System.out.println("  Total de entidades: " + ambiente.getEntidades().size());
        long numRobos = ambiente.getEntidades().stream().filter(Robo.class::isInstance).count();
        long numObstaculos = ambiente.getEntidades().stream().filter(Obstaculo.class::isInstance).count();
        System.out.println("    Robôs no ambiente: " + numRobos);
        System.out.println("    Obstáculos no ambiente: " + numObstaculos);
    }

    private void visualizarMapa2D() {
        System.out.print("\nDigite a camada Z para visualizar (0 a " + (Math.max(0, ambiente.altura - 1)) + "): ");
        try {
            int camadaZ = Integer.parseInt(scanner.nextLine());
            ambiente.visualizarAmbiente(camadaZ); // Este método já tem formatação interna
        } catch (NumberFormatException e) {
            System.out.println("(!) Entrada numérica inválida para camada Z.");
        }
    }

    private void alterarEstadoRoboSelecionado() {
        if (roboSelecionado == null) { System.out.println("\n(!) Nenhum robô selecionado."); return; }
        System.out.println("\n--- Alterar Estado do Robô " + roboSelecionado.getId() + " ---");
        System.out.println("Robô " + roboSelecionado.getId() + " está atualmente: " + roboSelecionado.getEstado());
        System.out.print("Deseja (L)igar ou (D)esligar? ");
        String acao = scanner.nextLine().trim().toUpperCase();
        if ("L".equals(acao)) roboSelecionado.ligar();
        else if ("D".equals(acao)) roboSelecionado.desligar();
        else System.out.println("(!) Ação inválida. Estado não alterado.");
    }

    private void menuControleMovimentoRobo() throws RoboDesligadoException, ColisaoException, ForaDosLimitesException, AcaoNaoPermitidaException {
        if (roboSelecionado == null) { System.out.println("\n(!) Nenhum robô selecionado para mover."); return; }
        if (roboSelecionado.getEstado() == EstadoRobo.DESLIGADO) {
            throw new RoboDesligadoException("Robô " + roboSelecionado.getId() + " está desligado e não pode se mover.");
        }

        System.out.println("\n--- Movimentar Robô " + roboSelecionado.getId() + " ---");
        System.out.println("Posição atual: (" + roboSelecionado.getX() + "," + roboSelecionado.getY() + "," + roboSelecionado.getZ() + ")");
        System.out.println("\nControles de Movimento (incremento de 1 unidade):");
        System.out.println("  (F)rente (Y+1)    (T)rás (Y-1)");
        System.out.println("  (D)ireita (X+1)   (E)squerda (X-1)");
        if (roboSelecionado instanceof RoboAereo) {
             System.out.println("  (C)ima (Z+1)      (B)aixo (Z-1)");
        }
        System.out.print("\nComando de movimento: ");
        String cmd = scanner.nextLine().trim().toUpperCase();
        int nX = roboSelecionado.getX(), nY = roboSelecionado.getY(), nZ = roboSelecionado.getZ();
        boolean movimentoValido = true;
        switch (cmd) {
            case "F": nY++; break; case "T": nY--; break;
            case "D": nX++; break; case "E": nX--; break;
            case "C": if (roboSelecionado instanceof RoboAereo) nZ++; else { System.out.println("(!) Robô não aéreo não pode mover no eixo Z."); movimentoValido = false; } break;
            case "B": if (roboSelecionado instanceof RoboAereo) nZ--; else { System.out.println("(!) Robô não aéreo não pode mover no eixo Z."); movimentoValido = false; } break;
            default: System.out.println("(!) Comando de movimento inválido."); movimentoValido = false;
        }
        if (movimentoValido) {
            ambiente.moverEntidade(roboSelecionado, nX, nY, nZ);
            System.out.println(">> Movimento para (" + roboSelecionado.getX() + "," + roboSelecionado.getY() + "," + roboSelecionado.getZ() + ") realizado (ou tentado).");
        }
    }

    private void menuFuncionalidadesRobo()
            throws RoboDesligadoException, AcaoNaoPermitidaException, ColisaoException,
                   ForaDosLimitesException, ErroComunicacaoException, RecursoInsuficienteException {
        if (roboSelecionado == null) { System.out.println("\n(!) Nenhum robô selecionado."); return; }

        System.out.println("\n--- Funcionalidades de " + roboSelecionado.getId() + " (" + roboSelecionado.getClass().getSimpleName() + ") ---");
        int optIndex = 1;
        List<String> opcoesMenu = new ArrayList<>();
        List<Runnable> acoesMenu = new ArrayList<>();

        // Tarefa Principal
        opcoesMenu.add(optIndex++ + ". Executar Tarefa Principal");
        acoesMenu.add(() -> {
            try {
                System.out.println("\n-- Executando Tarefa Principal para " + roboSelecionado.getId() + " --");
                roboSelecionado.executarTarefa(ambiente, centralComunicacao);
                System.out.println("-- Tarefa Principal Concluída --");
            }
            catch (Exception e) { System.err.println("(!) Erro ao executar tarefa: " + e.getMessage()); }
        });

        // Sensoreavel
        if (roboSelecionado instanceof Sensoreavel) {
            Sensoreavel s = (Sensoreavel) roboSelecionado;
            opcoesMenu.add(optIndex++ + ". Acionar e Ler Dados dos Sensores");
            acoesMenu.add(() -> {
                try {
                    System.out.println("\n-- Acionando Sensores de " + roboSelecionado.getId() + " --");
                    s.acionarSensores();
                    System.out.println("\n-- Lendo Dados dos Sensores --");
                    // Adiciona recuo para a saída do sensor
                    String dadosSensor = s.lerDadosSensor(ambiente);
                    for (String linha : dadosSensor.split("\n")) {
                        System.out.println("  " + linha);
                    }
                } catch (RoboDesligadoException e) { System.err.println("(!) " + e.getMessage()); }
            });
        }

        // Comunicavel
        if (roboSelecionado instanceof Comunicavel) {
            Comunicavel c = (Comunicavel) roboSelecionado;
            opcoesMenu.add(optIndex++ + ". Enviar Mensagem");
            acoesMenu.add(() -> {
                try {
                    System.out.println("\n-- Enviar Mensagem --");
                    System.out.print("ID do robô destinatário: "); String idDest = scanner.nextLine().trim();
                    Robo destRobo = ambiente.getRoboPeloId(idDest);
                    if (destRobo == null) { System.out.println("(!) Robô destinatário com ID '" + idDest + "' não encontrado."); return; }
                    if (!(destRobo instanceof Comunicavel)) { System.out.println("(!) Destinatário '" + idDest + "' não é comunicável."); return; }
                    System.out.print("Digite a mensagem: "); String msg = scanner.nextLine();
                    c.enviarMensagem((Comunicavel) destRobo, msg, centralComunicacao);
                } catch (RoboDesligadoException | ErroComunicacaoException e) { System.err.println("(!) " + e.getMessage()); }
            });
        }

        // Coletor
        if (roboSelecionado instanceof Coletor) {
            Coletor col = (Coletor) roboSelecionado;
            opcoesMenu.add(optIndex++ + ". Carregar Item");
            acoesMenu.add(() -> {
                try {
                    System.out.println("\n-- Carregar Item --");
                    System.out.print("Nome do item a carregar: "); String item = scanner.nextLine();
                    System.out.print("Peso do item: "); int peso = Integer.parseInt(scanner.nextLine());
                    col.carregarItem(item, peso);
                } catch (NumberFormatException e) {System.err.println("(!) Peso inválido.");}
                  catch (RoboDesligadoException | AcaoNaoPermitidaException | RecursoInsuficienteException e) { System.err.println("(!) " + e.getMessage());}
            });
            opcoesMenu.add(optIndex++ + ". Descarregar Carga");
            acoesMenu.add(() -> {
                try {
                    System.out.println("\n-- Descarregar Carga --");
                    System.out.println(col.descarregarCarga());
                }
                catch (RoboDesligadoException e) { System.err.println("(!) " + e.getMessage());}
            });
        }

        // Combatente
        if (roboSelecionado instanceof Combatente) {
            Combatente com = (Combatente) roboSelecionado;
            opcoesMenu.add(optIndex++ + ". Definir Alvo (por ID)");
            acoesMenu.add(() -> {
                try {
                    System.out.println("\n-- Definir Alvo --");
                    System.out.print("ID do robô alvo (ou 'nenhum' para limpar): "); String idAlvo = scanner.nextLine().trim();
                    if (idAlvo.equalsIgnoreCase("nenhum") || idAlvo.isEmpty()) {
                        com.definirAlvo(null); return;
                    }
                    Robo alvo = ambiente.getRoboPeloId(idAlvo);
                    if (alvo == null) { System.out.println("(!) Alvo com ID '"+idAlvo+"' não encontrado."); return;}
                    com.definirAlvo(alvo);
                } catch (AcaoNaoPermitidaException e) { System.err.println("(!) " + e.getMessage()); }
            });
            opcoesMenu.add(optIndex++ + ". Atacar Alvo Definido");
            acoesMenu.add(() -> {
                 try {
                    System.out.println("\n-- Atacar Alvo --");
                    com.atacar(ambiente);
                 }
                 catch (RoboDesligadoException | AcaoNaoPermitidaException | RecursoInsuficienteException e) { System.err.println("(!) " + e.getMessage());}
            });
             if (roboSelecionado instanceof DroneDeGuerra) {
                opcoesMenu.add(optIndex++ + ". Recarregar Munição (Drone de Guerra)");
                acoesMenu.add(() -> {
                    try {
                        System.out.println("\n-- Recarregar Munição --");
                        System.out.print("Quantidade de munição a recarregar: ");
                        int qtd = Integer.parseInt(scanner.nextLine());
                        ((DroneDeGuerra) roboSelecionado).recarregar(qtd);
                    } catch (NumberFormatException e) { System.err.println("(!) Quantidade inválida.");}
                });
            }
        }

        // Explorador
        if (roboSelecionado instanceof Explorador) {
            Explorador exp = (Explorador) roboSelecionado;
            opcoesMenu.add(optIndex++ + ". Explorar Área Específica");
            acoesMenu.add(() -> {
                try {
                    System.out.println("\n-- Explorar Área --");
                    System.out.print("Coordenada X do alvo da exploração: "); int ax = Integer.parseInt(scanner.nextLine());
                    System.out.print("Coordenada Y do alvo da exploração: "); int ay = Integer.parseInt(scanner.nextLine());
                    System.out.print("Coordenada Z do alvo da exploração: "); int az = Integer.parseInt(scanner.nextLine());
                    exp.explorarArea(ambiente, ax, ay, az);
                    System.out.println("\n-- Relatório da Exploração --");
                    // Adiciona recuo para a saída do relatório
                    String relatorio = exp.getRelatorioExploracao();
                    for (String linha : relatorio.split("\n")) {
                        System.out.println("  " + linha);
                    }
                } catch (NumberFormatException e) {System.err.println("(!) Coordenada inválida.");}
                  catch (RoboDesligadoException | AcaoNaoPermitidaException | ForaDosLimitesException | ColisaoException e) {System.err.println("(!) " + e.getMessage());}
            });
        }

        if (opcoesMenu.isEmpty() || optIndex == 1) { // Se só tem a tarefa padrão ou nenhuma
             System.out.println("\nNenhuma funcionalidade específica adicional listada para este robô.");
             // Se a tarefa padrão é a única, pode-se optar por executá-la ou não aqui.
             // apenas infotma.
             return;
        }
        System.out.println(); // Espaço antes das opções
        opcoesMenu.forEach(System.out::println);
        System.out.println("0. Voltar ao menu principal");
        System.out.println("------------------------------------");
        System.out.print("Escolha uma funcionalidade: ");
        try {
            int escolhaFunc = Integer.parseInt(scanner.nextLine());
            if (escolhaFunc > 0 && escolhaFunc <= acoesMenu.size()) {
                acoesMenu.get(escolhaFunc - 1).run();
            } else if (escolhaFunc != 0) {
                System.out.println("(!) Opção de funcionalidade inválida.");
            }
        } catch (NumberFormatException e) {
            System.out.println("(!) Entrada numérica inválida para funcionalidade.");
        }
    }
}