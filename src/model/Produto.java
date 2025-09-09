package model;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe que representa um Produto no sistema.
 * Contém informações como nome, descrição, preço, quantidade e tipo de produto.
 * Utiliza Lombok para gerar automaticamente os métodos getters e setters.
 */
@Getter
@Setter
public class Produto {

    /**
     * Identificador único do produto.
     * Anotado com @Generated para indicar que o valor é gerado automaticamente.
     */
    @Generated
    private long id;

    /** Nome do produto */
    private String nome;

    /** Descrição detalhada do produto */
    private String descricao;

    /** Preço do produto */
    private double preco;

    /** Quantidade disponível em estoque */
    private int quantidade;

    /** Tipo do produto (enum TipoProduto) */
    private TipoProduto tipo;

    /**
     * Construtor padrão sem argumentos.
     * Útil para frameworks ou quando se deseja criar o objeto e setar os valores depois.
     */
    public Produto() {
    }

    /**
     * Construtor completo para inicializar todos os atributos do produto.
     *
     * @param id identificador único
     * @param nome nome do produto
     * @param descricao descrição do produto
     * @param preco preço do produto
     * @param quantidade quantidade em estoque
     * @param tipo tipo do produto (enum TipoProduto)
     */
    public Produto(long id, String nome, String descricao, double preco, int quantidade, TipoProduto tipo) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
        this.tipo = tipo;
    }

    /**
     * Sobrescreve o método toString para exibir o produto de forma legível.
     * Útil para logs, debugging e exibição no terminal.
     *
     * @return string formatada com informações do produto
     */
    @Override
    public String toString() {
        return "Produto {\n" +
                "  id = " + id + ",\n" +
                "  nome = '" + nome + "',\n" +
                "  descricao = '" + descricao + "',\n" +
                "  preco = " + preco + ",\n" +
                "  quantidade = " + quantidade + ",\n" +
                "  tipo = " + tipo + "\n" +
                '}';
    }
}
