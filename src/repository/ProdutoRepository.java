package repository;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import model.Produto;
import model.TipoProduto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositório para operações de CRUD em produtos
 * e geração de relatórios em PDF.
 */
public class ProdutoRepository {

    private final String url = System.getenv("DB_URL");
    private final String user = System.getenv("DB_USER");
    private final String password = System.getenv("DB_PASSWORD");
    private final String query = "SELECT nome, preco, quantidade FROM produtos";

    /**
     * Salva um produto no banco de dados.
     */
    public boolean salvarProduto(Produto produto) {
        String sql = "INSERT INTO produtos (nome, descricao, preco, quantidade, tipo) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setDouble(3, produto.getPreco());
            stmt.setInt(4, produto.getQuantidade());
            stmt.setString(5, produto.getTipo().name());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao salvar produto: " + e.getMessage());
        }
        return false;
    }

    /**
     * Adiciona quantidade ao estoque do produto.
     */
    public boolean adicionarQuantidade(Long id, int quantidadeAdicionar) {
        String sql = "UPDATE produtos SET quantidade = quantidade + ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, quantidadeAdicionar);
            stmt.setLong(2, id);

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao adicionar quantidade: " + e.getMessage());
        }
        return false;
    }

    /**
     * Remove quantidade do estoque do produto, se houver suficiente.
     */
    public boolean removerQuantidade(Long id, int quantidadeRemover) {
        String sql = "UPDATE produtos SET quantidade = quantidade - ? WHERE id = ? AND quantidade >= ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, quantidadeRemover);
            stmt.setLong(2, id);
            stmt.setInt(3, quantidadeRemover);

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao remover quantidade: " + e.getMessage());
        }
        return false;
    }

    /**
     * Retorna a lista completa de produtos do banco.
     */
    public List<Produto> listaProdutos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produtos";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Produto p = new Produto(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getDouble("preco"),
                        rs.getInt("quantidade"),
                        TipoProduto.valueOf(rs.getString("tipo"))
                );
                produtos.add(p);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar produtos: " + e.getMessage());
        }

        return produtos;
    }

    /**
     * Busca um produto pelo nome.
     */
    public Produto buscarProduto(String nome) {
        String sql = "SELECT * FROM produtos WHERE nome = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Produto(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getDouble("preco"),
                        rs.getInt("quantidade"),
                        TipoProduto.valueOf(rs.getString("tipo"))
                );
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar produto: " + e.getMessage());
        }
        return null;
    }

    /**
     * Exclui um produto pelo ID.
     */
    public boolean excluirProduto(long id) {
        String sql = "DELETE FROM produtos WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao excluir produto: " + e.getMessage());
        }
        return false;
    }

    /**
     * Gera um relatório PDF com todos os produtos.
     */
    public boolean gerarRelatorio() {
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query);
             PdfWriter writer = new PdfWriter("C:/Users/Usuario/Documents/relatorio_produtos.pdf");
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            document.add(new Paragraph("Relatório de Produtos").setBold().setFontSize(14));

            Table table = new Table(UnitValue.createPercentArray(new float[]{1, 3, 2}));
            table.setWidth(UnitValue.createPercentValue(100));

            table.addHeaderCell("Nome");
            table.addHeaderCell("Preço");
            table.addHeaderCell("Quantidade");

            while (rs.next()) {
                table.addCell(rs.getString("nome"));
                table.addCell("R$ " + rs.getDouble("preco"));
                table.addCell(String.valueOf(rs.getInt("quantidade")));
            }

            document.add(table);
            System.out.println("Relatório PDF gerado com sucesso!");
            return true;

        } catch (Exception e) {
            System.err.println("Erro ao gerar relatório: " + e.getMessage());
        }
        return false;
    }

}
