package service;

import controller.ScannerController;
import model.Produto;
import model.TipoProduto;
import repository.ProdutoRepository;

import java.util.List;
import java.util.Scanner;

/**
 * Serviço responsável pelas regras de negócio relacionadas a produtos.
 * Atua como intermediário entre o repositório e a interface (Menu/Controller).
 */
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final Scanner scanner = new Scanner(System.in); // Scanner direto (pode ser substituído pelo ScannerController)
    private final ScannerController scannerController;

    public ProdutoService(ProdutoRepository produtoRepository, ScannerController scannerController) {
        this.produtoRepository = produtoRepository;
        this.scannerController = scannerController;
    }

    /**
     * Método para cadastrar produtos.
     * Lê dados do usuário, valida e salva no banco.
     */
    public void cadastrarProduto() {
        boolean continuar = true;

        while (continuar) {
            try {
                Produto produto = new Produto();

                // Solicita informações ao usuário
                System.out.print("Digite o nome do Produto: ");
                String nome = scannerController.lerTextoValido("Nome não pode ser vazio!");
                produto.setNome(nome);

                System.out.print("Digite a descrição do Produto: ");
                String descricao = scannerController.lerTextoValido("Descrição não pode ser vazia!");
                produto.setDescricao(descricao);

                System.out.print("Digite o preço do Produto: R$ ");
                double preco = scannerController.lerPrecoValido();
                produto.setPreco(preco);

                System.out.print("Digite a quantidade do Produto: ");
                int quantidade = scannerController.lerQuantidadeValida();
                produto.setQuantidade(quantidade);

                TipoProduto tipoProduto = scannerController.lerTipoProdutoValido();
                produto.setTipo(tipoProduto);

                // Salva produto no banco
                produtoRepository.salvarProduto(produto);
                System.out.println("Produto cadastrado com sucesso!");

            } catch (Exception e) {
                System.err.println("Erro ao cadastrar produto: " + e.getMessage());
            }

            // Pergunta se o usuário deseja cadastrar outro produto
            continuar = scannerController.perguntarContinuarOperacao("cadastrar outro produto");
        }
    }

    /**
     * Método para deletar um produto pelo ID.
     * Pergunta ao usuário qual produto deseja excluir.
     */
    public void deletarProduto() {
        boolean continuar = true;

        while (continuar) {
            try {
                System.out.print("Digite o ID do Produto para deletar: ");
                long id = scannerController.lerIdValido();

                boolean deletado = produtoRepository.excluirProduto(id);
                if (deletado) {
                    System.out.println("Produto deletado com sucesso!");
                } else {
                    System.out.println("Produto não encontrado!");
                }

            } catch (Exception e) {
                System.err.println("Erro ao deletar produto: " + e.getMessage());
            }

            continuar = scannerController.perguntarContinuarOperacao("deletar outro produto");
        }
    }

    /**
     * Lista todos os produtos cadastrados.
     */
    public void listarProdutos() {
        try {
            List<Produto> produtos = produtoRepository.listaProdutos();

            if (produtos == null || produtos.isEmpty()) {
                System.out.println("Nenhum produto encontrado!");
            } else {
                System.out.println("\n=== LISTA DE PRODUTOS ===");
                for (Produto produto : produtos) {
                    System.out.println(produto.toString());
                    System.out.println("-------------------------");
                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao listar produtos: " + e.getMessage());
        }

        // Pausa o sistema usando ScannerController
        scannerController.pausarSistema("Pressione Enter para continuar...");
    }

    /**
     * Busca um produto pelo nome.
     */
    public void buscarProduto() {
        boolean continuar = true;

        while (continuar) {
            try {
                System.out.print("Digite o nome do Produto para buscar: ");
                String nome = scannerController.lerTextoValido("Nome não pode ser vazio!");

                Produto produto = produtoRepository.buscarProduto(nome);
                if (produto != null) {
                    System.out.println("\n=== PRODUTO ENCONTRADO ===");
                    System.out.println(produto.toString());
                } else {
                    System.out.println("Produto não encontrado!");
                }

            } catch (Exception e) {
                System.err.println("Erro ao buscar produto: " + e.getMessage());
            }

            continuar = scannerController.perguntarContinuarOperacao("buscar outro produto");
        }
    }

    /**
     * Atualiza a quantidade de um produto no estoque.
     * Permite adicionar ou remover unidades.
     */
    public void atualizarQuantidade() {
        boolean continuar = true;

        while (continuar) {
            try {
                System.out.print("Digite o nome do Produto para atualizar quantidade: ");
                String nome = scannerController.lerTextoValido("Nome não pode ser vazio!");

                Produto produto = produtoRepository.buscarProduto(nome);
                if (produto == null) {
                    System.out.println("Produto não encontrado!");
                    continuar = scannerController.perguntarContinuarOperacao("tentar novamente");
                    continue;
                }

                System.out.println("Produto encontrado: " + produto.getNome());
                System.out.println("Quantidade atual: " + produto.getQuantidade());

                int opcaoQuantidade = scannerController.lerOpcaoQuantidade();
                int quantidade = scannerController.lerQuantidadeValida();

                boolean sucesso = false;
                switch (opcaoQuantidade) {
                    case 1 -> {
                        sucesso = produtoRepository.adicionarQuantidade(produto.getId(), quantidade);
                        if (sucesso) {
                            System.out.println("Quantidade adicionada com sucesso!");
                        }
                    }
                    case 2 -> {
                        if (produto.getQuantidade() < quantidade) {
                            System.out.println("Quantidade insuficiente! Quantidade atual: " + produto.getQuantidade());
                        } else {
                            sucesso = produtoRepository.removerQuantidade(produto.getId(), quantidade);
                            if (sucesso) {
                                System.out.println("Quantidade removida com sucesso!");
                            }
                        }
                    }
                }

                if (!sucesso && opcaoQuantidade != 2) {
                    System.out.println("Falha ao atualizar quantidade!");
                }

            } catch (Exception e) {
                System.err.println("Erro ao atualizar quantidade: " + e.getMessage());
            }

            continuar = scannerController.perguntarContinuarOperacao("atualizar quantidade de outro produto");
        }
    }

    /**
     * Gera relatório PDF com todos os produtos.
     */
    public void gerarRelatorio() {
        try {
            produtoRepository.gerarRelatorio();
            System.out.println("✅ Relatório gerado com sucesso!");
        } catch (Exception e) {
            System.out.println("❌ Houve um erro ao gerar o relatório:");
            e.printStackTrace();
        }
    }

}
