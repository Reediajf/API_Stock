package controller;

import model.TipoProduto;

import java.io.IOException;
import java.util.Scanner;

public class ScannerController {

    Scanner scanner = new Scanner(System.in);

    /**
     * Lê um texto do usuário e valida se não está vazio.
     * @param mensagemErro Mensagem exibida se o texto estiver vazio.
     * @return O texto digitado pelo usuário, já trim().
     */
    public String lerTextoValido(String mensagemErro) {
        String texto;
        do {
            texto = scanner.nextLine().trim();
            if (texto.isEmpty() && !mensagemErro.isEmpty()) {
                System.out.println(mensagemErro + " Tente novamente:");
            }
        } while (texto.isEmpty() && !mensagemErro.isEmpty());
        return texto;
    }

    /**
     * Lê um ID válido (long) maior que zero.
     * @return ID válido.
     */
    public long lerIdValido() {
        long id;
        do {
            id = scanner.nextLong();
            if (id <= 0) {
                System.out.println("Insira um id válido");
            }
        } while (id <= 0);
        return id;
    }

    /**
     * Lê um preço válido (double) maior que zero.
     * Trata entradas inválidas e limpa buffer do scanner.
     * @return preço válido
     */
    public double lerPrecoValido() {
        double preco = -1;
        while (preco <= 0) {
            try {
                if (scanner.hasNextDouble()) {
                    preco = scanner.nextDouble();
                    scanner.nextLine(); // limpa buffer
                    if (preco <= 0) {
                        System.out.print("O preço deve ser maior que zero. Digite novamente: R$ ");
                    }
                } else {
                    System.out.print("Entrada inválida! Digite um número válido: R$ ");
                    scanner.nextLine();
                }
            } catch (Exception e) {
                System.out.print("Erro na entrada! Digite um preço válido: R$ ");
                scanner.nextLine();
            }
        }
        return preco;
    }

    /**
     * Lê uma quantidade inteira válida (>0) do usuário.
     * @return quantidade válida
     */
    public int lerQuantidadeValida() {
        int quantidade = -1;
        while (quantidade <= 0) {
            try {
                if (scanner.hasNextInt()) {
                    quantidade = scanner.nextInt();
                    scanner.nextLine(); // limpa buffer
                    if (quantidade <= 0) {
                        System.out.print("A quantidade deve ser maior que zero. Digite novamente: ");
                    }
                } else {
                    System.out.print("Entrada inválida! Digite um número inteiro: ");
                    scanner.nextLine();
                }
            } catch (Exception e) {
                System.out.print("Erro na entrada! Digite uma quantidade válida: ");
                scanner.nextLine();
            }
        }
        return quantidade;
    }

    /**
     * Lê o tipo de produto válido a partir do enum TipoProduto.
     * Mostra todos os tipos disponíveis.
     * @return TipoProduto escolhido
     */
    public TipoProduto lerTipoProdutoValido() {
        TipoProduto tipoProduto = null;
        while (tipoProduto == null) {
            try {
                System.out.println("Tipos disponíveis:");
                for (TipoProduto tipo : TipoProduto.values()) {
                    System.out.println("- " + tipo.name());
                }
                System.out.print("Escolha o tipo de Produto: ");
                String tipoStr = scanner.nextLine().trim().toUpperCase();

                tipoProduto = TipoProduto.valueOf(tipoStr);

            } catch (IllegalArgumentException e) {
                System.out.println("Tipo inválido! Tente novamente.");
            } catch (Exception e) {
                System.out.println("Erro na entrada! Tente novamente.");
            }
        }
        return tipoProduto;
    }

    /**
     * Lê a opção de adicionar ou remover quantidade de um produto.
     * @return 1 para adicionar, 2 para remover
     */
    public int lerOpcaoQuantidade() {
        int opcaoQuantidade = 0;
        while (opcaoQuantidade != 1 && opcaoQuantidade != 2) {
            try {
                System.out.println("\nVocê deseja adicionar ou remover um valor?");
                System.out.println("1 - Para adicionar");
                System.out.println("2 - Para remover");
                System.out.print("Escolha: ");
                if (scanner.hasNextInt()) {
                    limparBuffer();
                    opcaoQuantidade = scanner.nextInt();
                    if (opcaoQuantidade == 1) {
                        System.out.println("Digite o valor a adicionar: ");
                    } else {
                        System.out.println("Digite o valor a remover: ");
                    }
                    scanner.nextLine();
                    if (opcaoQuantidade != 1 && opcaoQuantidade != 2) {
                        System.out.println("Opção inválida! Digite 1 ou 2.\n");
                    }
                } else {
                    System.out.println("Entrada inválida! Digite um número.\n");
                    scanner.nextLine();
                }
            } catch (Exception e) {
                System.out.println("Erro na entrada! Tente novamente.\n");
                scanner.nextLine();
            }
        }
        return opcaoQuantidade;
    }

    /**
     * Pergunta ao usuário se deseja continuar a operação ou voltar.
     * @param operacao Descrição da operação
     * @return true se continuar, false se voltar
     */
    public boolean perguntarContinuarOperacao(String operacao) {
        int opcao = 0;
        while (opcao != 1 && opcao != 2) {
            try {
                System.out.println("\nO que deseja fazer?");
                System.out.println("1 - Continuar " + operacao);
                System.out.println("2 - Voltar ao menu anterior");
                System.out.print("Escolha: ");

                if (scanner.hasNextInt()) {
                    opcao = scanner.nextInt();
                    scanner.nextLine();
                    if (opcao != 1 && opcao != 2) {
                        System.out.println("Opção inválida! Digite 1 ou 2.");
                    }
                } else {
                    System.out.println("Entrada inválida! Digite um número.");
                    scanner.nextLine();
                }
            } catch (Exception e) {
                System.out.println("Erro na entrada! Tente novamente.");
                scanner.nextLine();
            }
        }
        return opcao == 1;
    }

    /**
     * Lê uma opção de menu dentro de um range específico.
     * @param minimo menor valor permitido
     * @param maximo maior valor permitido
     * @return opção válida
     */
    public int lerOpcaoMenu(int minimo, int maximo) {
        int opcao = 0;
        while (opcao < minimo || opcao > maximo) {
            try {
                if (scanner.hasNextInt()) {
                    opcao = scanner.nextInt();
                    scanner.nextLine();
                    if (opcao < minimo || opcao > maximo) {
                        System.out.printf("❌ Opção inválida! Escolha um número entre %d e %d.\n\n", minimo, maximo);
                        System.out.print("Escolha uma opção: ");
                    }
                } else {
                    System.out.println("❌ Entrada inválida! Digite apenas números.\n");
                    System.out.print("Escolha uma opção: ");
                    scanner.nextLine();
                }
            } catch (Exception e) {
                System.out.println("❌ Erro na entrada! Tente novamente.\n");
                System.out.print("Escolha uma opção: ");
                scanner.nextLine();
            }
        }
        return opcao;
    }

    /**
     * Pergunta ao usuário uma confirmação personalizada com duas opções.
     * @param pergunta Pergunta a ser exibida
     * @param opcao1 Texto da primeira opção
     * @param opcao2 Texto da segunda opção
     * @return true se escolher a primeira opção, false se escolher a segunda
     */
    public boolean perguntarConfirmacao(String pergunta, String opcao1, String opcao2) {
        int escolha = 0;
        boolean opcaoValida = false;

        while (!opcaoValida) {
            try {
                System.out.println(pergunta);
                System.out.println("1 - " + opcao1);
                System.out.println("2 - " + opcao2);
                System.out.print("Escolha: ");

                if (scanner.hasNextInt()) {
                    escolha = scanner.nextInt();
                    scanner.nextLine();

                    if (escolha == 1 || escolha == 2) {
                        opcaoValida = true;
                    } else {
                        System.out.println("❌ Opção inválida! Digite 1 ou 2.\n");
                    }
                } else {
                    System.out.println("❌ Entrada inválida! Digite um número.\n");
                    scanner.nextLine();
                }
            } catch (Exception e) {
                System.out.println("❌ Erro na entrada! Tente novamente.\n");
                scanner.nextLine();
            }
        }

        return escolha == 1;
    }

    /**
     * Lê um número inteiro dentro de um intervalo definido.
     * @param minimo valor mínimo permitido
     * @param maximo valor máximo permitido
     * @param mensagem mensagem exibida para o usuário
     * @return número válido
     */
    public int lerInteiroValido(int minimo, int maximo, String mensagem) {
        int numero = minimo - 1;
        while (numero < minimo || numero > maximo) {
            try {
                System.out.print(mensagem + ": ");
                if (scanner.hasNextInt()) {
                    numero = scanner.nextInt();
                    scanner.nextLine();
                    if (numero < minimo || numero > maximo) {
                        System.out.printf("❌ Número deve estar entre %d e %d!\n", minimo, maximo);
                    }
                } else {
                    System.out.println("❌ Entrada inválida! Digite um número inteiro.");
                    scanner.nextLine();
                }
            } catch (Exception e) {
                System.out.println("❌ Erro na entrada! Digite um número válido.");
                scanner.nextLine();
            }
        }
        return numero;
    }

    /**
     * Lê um número inteiro positivo (>0)
     * @param mensagem mensagem para solicitar entrada
     * @return número positivo válido
     */
    public int lerInteiroPositivo(String mensagem) {
        int numero = -1;
        while (numero <= 0) {
            try {
                System.out.print(mensagem + ": ");
                if (scanner.hasNextInt()) {
                    numero = scanner.nextInt();
                    scanner.nextLine();
                    if (numero <= 0) {
                        System.out.println("❌ O número deve ser maior que zero!");
                    }
                } else {
                    System.out.println("❌ Entrada inválida! Digite um número inteiro.");
                    scanner.nextLine();
                }
            } catch (Exception e) {
                System.out.println("❌ Erro na entrada! Digite um número válido.");
                scanner.nextLine();
            }
        }
        return numero;
    }

    /**
     * Pausa a execução até que o usuário pressione Enter.
     * @param mensagem mensagem exibida antes da pausa
     */
    public void pausarSistema(String mensagem) {
        System.out.print(mensagem);
        try {
            scanner.nextLine();
        } catch (Exception e) {
            // Ignora erros ao pausar
        }
    }

    /**
     * Limpa o buffer do scanner em caso de entradas inválidas
     */
    public void limparBuffer() {
        try {
            if (scanner != null && System.in.available() > 0) {
                while (scanner.hasNextLine()) {
                    String linha = scanner.nextLine();
                    if (linha.trim().isEmpty()) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Aviso: Problema ao limpar buffer - " + e.getMessage());
        }
    }

    /**
     * Pergunta sim/não de forma simplificada
     * @param pergunta pergunta exibida ao usuário
     * @return true se usuário responder sim, false se não
     */
    public boolean perguntarSimNao(String pergunta) {
        return perguntarConfirmacao(pergunta, "Sim", "Não");
    }

    /**
     * Lê uma string do usuário (pode ser vazia)
     * @param mensagem mensagem exibida
     * @return texto digitado pelo usuário
     */
    public String lerTexto(String mensagem) {
        System.out.print(mensagem + ": ");
        String texto = scanner.nextLine();
        return texto != null ? texto.trim() : "";
    }

    /**
     * Fecha o scanner (bom para finalizar a aplicação)
     */
    public void fecharScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }
}
