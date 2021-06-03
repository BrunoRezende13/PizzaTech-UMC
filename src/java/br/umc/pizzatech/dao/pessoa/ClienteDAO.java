package br.umc.pizzatech.dao.pessoa;

import br.umc.pizzatech.model.pessoa.cliente.Cliente;
import br.umc.pizzatech.model.pessoa.cliente.ClienteFactory;
import br.umc.pizzatech.util.FabricaConexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public void criarTabela() {
        try {
            Connection connection = FabricaConexao.getConexao();

            try (Statement statement = connection.createStatement()) {
                statement.execute("CREATE TABLE IF NOT EXISTS clientes (" +
                        "id serial NOT NULL PRIMARY KEY," +
                        "nome VARCHAR(60) NOT NULL," +
                        "cpf VARCHAR(20) UNIQUE," +
                        "telefone VARCHAR(255) NOT NULL," +
                        "endereco VARCHAR(255) NOT NULL" +
                        ")");
            }
            connection.close();
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    public void cadastrar(Cliente cliente) throws SQLException, ClassNotFoundException {
        Connection conexao = FabricaConexao.getConexao();
        String sql = "INSERT INTO clientes (nome, cpf, telefone, endereco) VALUES (?,?,?,?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCpf());
            ps.setString(3, cliente.getTelefone());
            ps.setString(4, cliente.getEndereco());
            ps.executeUpdate();
        }
        conexao.close();
    }

    public void alterar(Cliente cliente) throws SQLException, ClassNotFoundException {
        Connection conexao = FabricaConexao.getConexao();
        String sql = "UPDATE clientes SET nome=?, cpf=?, telefone=?, endereco=? WHERE id=?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCpf());
            ps.setString(3, cliente.getTelefone());
            ps.setString(4, cliente.getEndereco());
            ps.setLong(5, cliente.getId());
            ps.executeUpdate();
        }
        conexao.close();
    }

    public int deletar(Cliente cliente) throws SQLException, ClassNotFoundException {
        int i;
        Connection conexao = FabricaConexao.getConexao();
        String sql = "DELETE FROM clientes WHERE id=?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, cliente.getId());
            i = ps.executeUpdate();
        }
        conexao.close();
        return i;
    }

    public List<Cliente> buscarTodos() throws SQLException, ClassNotFoundException {
        List<Cliente> clientes = new ArrayList<>();
        Connection conexao = FabricaConexao.getConexao();
        String sql = "SELECT * FROM clientes";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Cliente funcionario = ClienteFactory.criarCliente(rs);
                    clientes.add(funcionario);
                }
            }
        }
        conexao.close();
        return clientes;
    }

    public Cliente buscarPorId(Cliente pesquisa) throws SQLException, ClassNotFoundException {
        Connection conexao = FabricaConexao.getConexao();
        String sql = "SELECT * FROM clientes where id=?";
        Cliente cliente;

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, pesquisa.getId());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cliente = ClienteFactory.criarCliente(rs);
                } else {
                    cliente = null;
                }
            }
        }
        conexao.close();
        return cliente;
    }

    public List<Cliente> buscarPorNomeCpfTelefone(String pesquisa) throws SQLException, ClassNotFoundException {
        Connection conexao = FabricaConexao.getConexao();
        String sql = "SELECT * FROM clientes where nome LIKE ? OR cpf LIKE ? OR telefone LIKE ?";
        ArrayList<Cliente> clientes = new ArrayList<>();

        pesquisa = "%" + pesquisa + "%";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, pesquisa);
            ps.setString(2, pesquisa);
            ps.setString(3, pesquisa);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    clientes.add(ClienteFactory.criarCliente(rs));
                }
            }
        }
        conexao.close();
        return clientes;
    }

}
