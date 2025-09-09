package model;

import controller.ScannerController;
import service.ProdutoService;

/**
 * Classe responsável por exibir o menu do sistema e interagir com o usuário.
 * Controla o fluxo principal do Gerenciador de Dispensa.
 */
public class Menu {

    // Dependência para entrada de dados pelo usuário
    private final ScannerController scannerController;

    // Serviço que contém a lógica de negócio dos produtos
    private final ProdutoService produtoService;

    // Flag para controle do loop principal do sistema
    private boolean sistemaAtivo = true;

    /**
     * Construtor da classe Menu.
     * Inicializa as dependências e inicia o sistema.
     *
     * @param produtoService serviço para manipulação de produtos
     * @param scannerController controlador de entrada de dados
     */
    public Menu(ProdutoService produtoService, ScannerController scannerController) {
        this.produtoService = produtoService;
        this.scannerController = scannerController;
        iniciarSistema();
    }

    /**
     * Inicia o sistema, exibindo boas-vindas e entrando no loop principal do menu.
     */
    private void iniciarSistema() {
        exibirBoasVindas();

        while (sistemaAtivo) {
            try {
                int opcao = exibirMenuPrincipal();  // Exibe o menu e captura a opção do usuário
                processarOpcao(opcao);              // Processa a opção escolhida
            } catch (Exception e) {
                System.err.println("Erro inesperado: " + e.getMessage());
                System.out.println("Voltando ao menu principal...\n");
            }
        }

        encerrarSistema();  // Encerra o sistema quando o loop termina
    }

    /**
     * Exibe mensagem de boas-vindas ao usuário.
     */
    private void exibirBoasVindas() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║    BEM-VINDO AO GERENCIADOR DE       ║");
        System.out.println("║           PRODUTOS                   ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println();
    }

    /**
     * Exibe o menu principal e lê a opção do usuário.
     *
     * @return opção escolhida
     */
    private int exibirMenuPrincipal() {
        System.out.println("┌─────────────── MENU PRINCIPAL ───────────────┐");
        System.out.println("│  1 - Listar produtos                         │");
        System.out.println("│  2 - Cadastrar produto                       │");
        System.out.println("│  3 - Atualizar quantidade de produto         │");
        System.out.println("│  4 - Buscar produto                          │");
        System.out.println("│  5 - Excluir produto                         │");
        System.out.println("│  6 - Gerar relatório                         │");
        System.out.println("│  7 - Sair do sistema                         │");
        System.out.println("└──────────────────────────────────────────────┘");

        return scannerController.lerOpcaoMenu(1, 7);
    }

    /**
     * Processa a opção escolhida pelo usuário no menu.
     *
     * @param opcao número da opção
     */
    private void processarOpcao(int opcao) {
        System.out.println(); // Linha em branco para melhor visualização

        switch (opcao) {
            case 1 -> {
                System.out.println("📋 === LISTANDO PRODUTOS ===");
                listarProdutos();
            }
            case 2 -> {
                System.out.println("➕ === CADASTRANDO PRODUTO ===");
                cadastrarProduto();
            }
            case 3 -> {
                System.out.println("📊 === ATUALIZANDO QUANTIDADE ===");
                atualizarQuantidade();
            }
            case 4 -> {
                System.out.println("🔍 === BUSCANDO PRODUTO ===");
                buscarProduto();
            }
            case 5 -> {
                System.out.println("🗑️ === EXCLUINDO PRODUTO ===");
                excluirProduto();
            }
            case 6 -> {
                System.out.println("📊 === GERANDO RELATÓRIO ===");
                gerarRelatorio();
            }
            case 7 -> confirmarSaida();
            default -> System.out.println("❌ Opção inválida! Tente novamente.\n");
        }
    }

    /**
     * Lista todos os produtos cadastrados e oferece opção de voltar ou continuar.
     */
    private void listarProdutos() {
        try {
            produtoService.listarProdutos();
        } catch (Exception e) {
            System.err.println("❌ Erro ao listar produtos: " + e.getMessage());
        }

        voltarOuContinuar("📋 Listar produtos",
                () -> {},
                this::listarProdutos);
    }

    /**
     * Cadastra um novo produto e oferece opção de cadastrar outro.
     */
    private void cadastrarProduto() {
        try {
            produtoService.cadastrarProduto();
        } catch (Exception e) {
            System.err.println("❌ Erro ao cadastrar produto: " + e.getMessage());
        }

        voltarOuContinuar("➕ Cadastrar outro produto",
                () -> {},
                this::cadastrarProduto);
    }

    /**
     * Atualiza a quantidade de um produto e oferece opção de continuar a operação.
     */
    private void atualizarQuantidade() {
        try {
            produtoService.atualizarQuantidade();
        } catch (Exception e) {
            System.err.println("❌ Erro ao atualizar quantidade: " + e.getMessage());
        }

        voltarOuContinuar("📊 Atualizar quantidade",
                () -> {},
                this::atualizarQuantidade);
    }

    /**
     * Busca um produto por nome e oferece opção de continuar a operação.
     */
    private void buscarProduto() {
        try {
            produtoService.buscarProduto();
        } catch (Exception e) {
            System.err.println("❌ Erro ao buscar produto: " + e.getMessage());
        }

        voltarOuContinuar("🔍 Buscar outro produto",
                () -> {},
                this::buscarProduto);
    }

    /**
     * Exclui um produto e oferece opção de continuar a operação.
     */
    private void excluirProduto() {
        try {
            produtoService.deletarProduto();
        } catch (Exception e) {
            System.err.println("❌ Erro ao excluir produto: " + e.getMessage());
        }

        voltarOuContinuar("🗑️ Excluir outro produto",
                () -> {},
                this::excluirProduto);
    }

    /**
     * Gera relatório de produtos e oferece opção de gerar outro relatório.
     */
    private void gerarRelatorio() {
        try {
            produtoService.gerarRelatorio();
        } catch (Exception e) {
            System.err.println("❌ Erro ao gerar relatório: " + e.getMessage());
            e.printStackTrace();
        }

        voltarOuContinuar("📊 Gerar outro relatório",
                () -> {},
                this::gerarRelatorio);
    }

    /**
     * Pergunta ao usuário se deseja realmente sair do sistema.
     * Caso afirmativo, desativa o loop principal.
     */
    private void confirmarSaida() {
        boolean confirmarSair = scannerController.perguntarConfirmacao(
                "🚪 Tem certeza que deseja sair do sistema?",
                "Sim, sair do sistema",
                "Não, voltar ao menu principal"
        );

        if (confirmarSair) {
            sistemaAtivo = false;
        }
    }

    /**
     * Pergunta se deseja voltar ao menu principal ou continuar a operação atual.
     *
     * @param nomeOperacao nome da operação atual
     * @param voltarMenu ação a executar se voltar ao menu
     * @param continuarOperacao ação a executar se continuar operação
     */
    private void voltarOuContinuar(String nomeOperacao, Runnable voltarMenu, Runnable continuarOperacao) {
        boolean voltarAoMenu = scannerController.perguntarConfirmacao(
                "\n┌─── O QUE DESEJA FAZER? ───┐",
                "Voltar ao menu principal",
                nomeOperacao
        );

        System.out.println();

        if (voltarAoMenu) {
            voltarMenu.run();
        } else {
            continuarOperacao.run();
        }
    }

    /**
     * Exibe mensagem de despedida e encerra o sistema.
     */
    private void encerrarSistema() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║         OBRIGADO POR USAR O          ║");
        System.out.println("║       GERENCIADOR DE PRODUTOS!       ║");
        System.out.println("║            ATÉ A PRÓXIMA!            ║");
        System.out.println("╚══════════════════════════════════════╝");

        // Fechar recursos, como scanner
        fecharRecursos();
    }

    /**
     * Fecha recursos utilizados pelo sistema, como o ScannerController.
     */
    private void fecharRecursos() {
        try {
            if (scannerController != null) {
                scannerController.fecharScanner();
            }
        } catch (Exception e) {
            System.err.println("Erro ao fechar recursos: " + e.getMessage());
        }
    }

    /**
     * Permite reiniciar o sistema caso ele esteja inativo.
     */
    public void reiniciar() {
        if (!sistemaAtivo) {
            boolean reiniciarSistema = scannerController.perguntarSimNao(
                    "🔄 Deseja reiniciar o sistema?"
            );

            if (reiniciarSistema) {
                sistemaAtivo = true;
                iniciarSistema();
            }
        }
    }

    /**
     * Pausa o sistema até o usuário pressionar ENTER.
     */
    private void pausarSistema() {
        scannerController.pausarSistema("\n⏸️  Pressione ENTER para continuar...");
    }

    /**
     * Exibe mensagem de erro e pausa o sistema.
     * @param mensagem mensagem de erro a exibir
     */
    private void exibirErroEPausar(String mensagem) {
        System.err.println("❌ " + mensagem);
        pausarSistema();
    }
}
