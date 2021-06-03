package br.umc.pizzatech.dao.pedido;

import br.umc.pizzatech.model.pedido.Pedido;
import br.umc.pizzatech.model.pedido.PedidoFactory;
import br.umc.pizzatech.model.pedido.carrinho.ProdutoCarrinho;
import br.umc.pizzatech.model.produto.Produto;
import br.umc.pizzatech.model.produto.ProdutoFactory;
import br.umc.pizzatech.util.FabricaConexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    public void criarTabela() {
        try {
            Connection connection = FabricaConexao.getConexao();

            try (Statement statement = connection.createStatement()) {
                statement.execute("CREATE SEQUENCE IF NOT EXISTS seq_pedidos;");

                statement.execute("CREATE TABLE IF NOT EXISTS pedidos (" +
                        "id bigint NOT NULL PRIMARY KEY," +
                        "id_funcionario bigint," +
                        "id_cliente bigint," +
                        "anotacao TEXT NOT NULL DEFAULT ''," +
                        "total numeric(8, 2) NOT NULL, " +
                        "status varchar(40) NOT NULL, " +
                        "data date NOT NULL," +
                        "CONSTRAINT fk_funcionario FOREIGN KEY(id_funcionario) REFERENCES funcionarios(id) ON DELETE SET NULL ON UPDATE CASCADE , " +
                        "CONSTRAINT fk_cliente FOREIGN KEY(id_cliente) REFERENCES clientes(id) ON DELETE SET NULL ON UPDATE CASCADE " +
                        ");");

                statement.execute("CREATE TABLE IF NOT EXISTS pedido_produto (" +
                        "id serial NOT NULL PRIMARY KEY," +
                        "id_pedido bigint NOT NULL," +
                        "id_produto bigint NOT NULL, " +
                        "quantidade int NOT NULL DEFAULT 0, " +
                        "total numeric(8, 2) NOT NULL, " +
                        "CONSTRAINT fk_pedido FOREIGN KEY(id_pedido) REFERENCES pedidos(id) ON DELETE CASCADE ON UPDATE CASCADE , " +
                        "CONSTRAINT fk_produto FOREIGN KEY(id_produto) REFERENCES produtos(id) ON DELETE CASCADE ON UPDATE CASCADE " +
                        ");");
            }
            connection.close();
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    public void criar(Pedido pedido) throws SQLException, ClassNotFoundException {
        Connection conexao = FabricaConexao.getConexao();
        long id;
        try (PreparedStatement ps = conexao.prepareStatement("SELECT nextval('seq_pedidos') AS seq;")) {
            try (ResultSet rs = ps.executeQuery()){
                rs.next();
                id = rs.getLong("seq");
            }
        }

        String sql = "INSERT INTO pedidos (id, id_funcionario, id_cliente, anotacao, total, data, status) VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.setLong(2, pedido.getFuncionario().getId());
            ps.setLong(3, pedido.getCliente().getId());
            ps.setString(4, pedido.getAnotacao());
            ps.setDouble(5, pedido.getTotal());
            ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            ps.setString(7, pedido.getStatus().name());
            ps.executeUpdate();
        }

        String sqlProduto = "INSERT INTO pedido_produto (id_pedido, id_produto, quantidade, total) VALUES (?,?,?,?)";
        try (PreparedStatement ps = conexao.prepareStatement(sqlProduto)) {
            ps.setLong(1, id);

            for (ProdutoCarrinho item : pedido.getItens()) {
                ps.setLong(2, item.getProduto().getId());
                ps.setInt(3, item.getQuantidade());
                ps.setDouble(4, item.getTotal());

                ps.addBatch();
            }

            ps.executeBatch();
        }

        conexao.close();
    }


    public void alterarStatus(Pedido pedido) throws SQLException, ClassNotFoundException {
        Connection conexao = FabricaConexao.getConexao();
        String sql = "UPDATE pedidos SET status=? WHERE id=?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, pedido.getStatus().name());
            ps.setLong(2, pedido.getId());
            ps.executeUpdate();
        }
        conexao.close();
    }

    public int deletar(Pedido pedido) throws SQLException, ClassNotFoundException {
        Connection conexao = FabricaConexao.getConexao();
        String sql = "DELETE FROM pedidos WHERE id=?";
        int i;
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, pedido.getId());
            i = ps.executeUpdate();
        }
        conexao.close();
        return i;
    }

    public List<Pedido> buscarTodos() throws SQLException, ClassNotFoundException {
        List<Pedido> pedidos = new ArrayList<>();
        Connection conexao = FabricaConexao.getConexao();
        String sql = "SELECT * FROM pedidos";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    pedidos.add(PedidoFactory.criarPedido(rs));
                }
            }
        }
        conexao.close();
        return pedidos;
    }

    public List<ProdutoCarrinho> buscarTodos(Pedido pedido) throws SQLException, ClassNotFoundException {
        List<ProdutoCarrinho> pedidos = new ArrayList<>();
        Connection conexao = FabricaConexao.getConexao();
        String sql = "SELECT * FROM pedido_produto WHERE id_pedido = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, pedido.getId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProdutoCarrinho produtoCarrinho = PedidoFactory.criarProdutoCarrinho(rs);
                    pedidos.add(produtoCarrinho);
                }
            }
        }
        conexao.close();
        return pedidos;
    }

    public Pedido buscarPorId(Pedido pedido) throws SQLException, ClassNotFoundException {
        Connection conexao = FabricaConexao.getConexao();
        String sql = "SELECT * FROM pedidos where id=?";
        Pedido result;

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, pedido.getId());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    result = PedidoFactory.criarPedido(rs);
                } else {
                    result = null;
                }
            }
        }
        conexao.close();
        return result;
    }

}
