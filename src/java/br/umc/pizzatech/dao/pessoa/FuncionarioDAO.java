package br.umc.pizzatech.dao.pessoa;

import br.umc.pizzatech.model.conta.Conta;
import br.umc.pizzatech.model.pessoa.funcionario.Funcionario;
import br.umc.pizzatech.model.pessoa.funcionario.FuncionarioFactory;
import br.umc.pizzatech.util.FabricaConexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    public void criarTabela() {
        try {
            Connection connection = FabricaConexao.getConexao();

            try (Statement statement = connection.createStatement()) {
                statement.execute("CREATE TABLE IF NOT EXISTS funcionarios (" +
                        "id serial NOT NULL PRIMARY KEY," +
                        "nome VARCHAR(60) NOT NULL," +
                        "cpf VARCHAR(20) NOT NULL UNIQUE," +
                        "email VARCHAR(255) NOT NULL UNIQUE," +
                        "senha VARCHAR(255) NOT NULL," +
                        "acesso INT NOT NULL" +
                        ")");
            }
            connection.close();
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    public void cadastrar(Funcionario funcionario) throws SQLException, ClassNotFoundException {
        Connection conexao = FabricaConexao.getConexao();
        String sql = "INSERT INTO funcionarios (nome, cpf, email, senha, acesso) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, funcionario.getNome());
            ps.setString(2, funcionario.getCpf());
            ps.setString(3, funcionario.getEmail());
            ps.setString(4, funcionario.getSenha());
            ps.setInt(5, funcionario.getLevelAcesso().getId());
            ps.executeUpdate();
        }
        conexao.close();
    }

    public void alterar(Funcionario funcionario) throws SQLException, ClassNotFoundException {
        Connection conexao = FabricaConexao.getConexao();
        String sql = "UPDATE funcionarios SET nome=?, cpf=?, email=?, senha=?, acesso=? WHERE id=?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, funcionario.getNome());
            ps.setString(2, funcionario.getCpf());
            ps.setString(3, funcionario.getEmail());
            ps.setString(4, funcionario.getSenha());
            ps.setInt(5, funcionario.getLevelAcesso().getId());
            ps.setLong(6, funcionario.getId());
            ps.executeUpdate();
        }
        conexao.close();
    }

    public int deletar(Funcionario funcionario) throws SQLException, ClassNotFoundException {
        int i;
        Connection conexao = FabricaConexao.getConexao();
        String sql = "DELETE FROM funcionarios WHERE id=?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, funcionario.getId());
            i = ps.executeUpdate();
        }
        conexao.close();
        return i;
    }

    public List<Funcionario> buscarTodos() throws SQLException, ClassNotFoundException {
        List<Funcionario> funcionarios = new ArrayList<>();
        Connection conexao = FabricaConexao.getConexao();
        String sql = "SELECT * FROM funcionarios";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Funcionario funcionario = FuncionarioFactory.criarFuncionario(rs);
                    funcionarios.add(funcionario);
                }
            }
        }
        conexao.close();
        return funcionarios;
    }

    public Funcionario buscarPorId(Funcionario pesquisa) throws SQLException, ClassNotFoundException {
        Connection conexao = FabricaConexao.getConexao();
        String sql = "SELECT * FROM funcionarios where id=?";
        Funcionario funcionario;

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, pesquisa.getId());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    funcionario = FuncionarioFactory.criarFuncionario(rs);
                } else {
                    funcionario = null;
                }
            }
        }
        conexao.close();
        return funcionario;
    }

    public Funcionario autentificar(Conta pesquisa) throws SQLException, ClassNotFoundException {
        Connection conexao = FabricaConexao.getConexao();
        String sql = "SELECT * FROM funcionarios where email=? AND senha=?";
        Funcionario funcionario;

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, pesquisa.getEmail());
            ps.setString(2, pesquisa.getSenha());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    funcionario = FuncionarioFactory.criarFuncionario(rs);
                } else {
                    funcionario = null;
                }
            }
        }
        conexao.close();
        return funcionario;
    }

}
