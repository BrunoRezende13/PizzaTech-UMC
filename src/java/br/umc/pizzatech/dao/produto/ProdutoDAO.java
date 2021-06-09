package br.umc.pizzatech.dao.produto;

import br.umc.pizzatech.model.produto.Produto;
import br.umc.pizzatech.model.produto.ProdutoFactory;
import br.umc.pizzatech.util.FabricaConexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public void criarTabela() {
        try {
            Connection connection = FabricaConexao.getConexao();

            try (Statement statement = connection.createStatement()) {
                statement.execute("CREATE TABLE IF NOT EXISTS produtos (" +
                        "id serial NOT NULL PRIMARY KEY," +
                        "nome VARCHAR(60) NOT NULL," +
                        "descricao VARCHAR(255) DEFAULT ''," +
                        "ingredientes TEXT NOT NULL DEFAULT ''," +
                        "valor numeric(8, 2) NOT NULL," +
                        "estoque INTEGER NOT NULL," +
                        "pizza BOOLEAN NOT NULL" +
                        ")");
            }
            connection.close();
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    public void cadastrar(Produto produto) throws SQLException, ClassNotFoundException {
        Connection conexao = FabricaConexao.getConexao();
        String sql = "INSERT INTO produtos (nome, descricao, ingredientes, valor, estoque, pizza) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, produto.getNome());
            ps.setString(2, produto.getDescricao());
            ps.setString(3, produto.getIngredientes());
            ps.setDouble(4, produto.getValor());
            ps.setInt(5, produto.getEstoque());
            ps.setBoolean(6, produto.isPizza());
            ps.executeUpdate();
        }
        conexao.close();
    }


    public void alterar(Produto produto) throws SQLException, ClassNotFoundException {
        Connection conexao = FabricaConexao.getConexao();
        String sql = "UPDATE produtos SET nome=?, descricao=?, ingredientes=?, valor=?, estoque=?, pizza=? WHERE id=?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, produto.getNome());
            ps.setString(2, produto.getDescricao());
            ps.setString(3, produto.getIngredientes());
            ps.setDouble(4, produto.getValor());
            ps.setInt(5, produto.getEstoque());
            ps.setBoolean(6, produto.isPizza());
            ps.setLong(7, produto.getId());
            ps.executeUpdate();
        }
        conexao.close();
    }

    public void atualizarEstoque(Produto produto) throws SQLException, ClassNotFoundException {
        Connection conexao = FabricaConexao.getConexao();
        String sql = "UPDATE produtos SET estoque=? WHERE id=?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, produto.getEstoque());
            ps.setLong(2, produto.getId());
            ps.executeUpdate();
        }
        conexao.close();
    }

    public int deletar(Produto produto) throws SQLException, ClassNotFoundException {
        Connection conexao = FabricaConexao.getConexao();
        String sql = "DELETE FROM produtos WHERE id=?";
        int i;
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, produto.getId());
            i = ps.executeUpdate();
        }
        conexao.close();
        return i;
    }

    public List<Produto> buscarTodos() throws SQLException, ClassNotFoundException {
        List<Produto> produtos = new ArrayList<>();
        Connection conexao = FabricaConexao.getConexao();
        String sql = "SELECT * FROM produtos";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Produto produto = ProdutoFactory.criarProduto(rs);
                    produtos.add(produto);
                }
            }
        }
        conexao.close();
        return produtos;
    }

    public Produto buscarPorId(Produto produto) throws SQLException, ClassNotFoundException {
        Connection conexao = FabricaConexao.getConexao();
        String sql = "SELECT * FROM produtos where id=?";
        Produto funcionario;

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, produto.getId());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    funcionario = ProdutoFactory.criarProduto(rs);
                } else {
                    funcionario = null;
                }
            }
        }
        conexao.close();
        return funcionario;
    }

}
