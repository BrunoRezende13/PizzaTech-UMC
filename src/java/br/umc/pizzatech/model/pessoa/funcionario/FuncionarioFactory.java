package br.umc.pizzatech.model.pessoa.funcionario;

import br.umc.pizzatech.util.Validar;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FuncionarioFactory {


    public static Funcionario criarFuncionario(long id, String nome, String cpf, String email, String senha, LevelAcesso levelAcesso){
        Funcionario funcionario = new Funcionario();
        funcionario.setId(id);
        funcionario.setNome(nome);
        funcionario.setCpf(cpf);
        funcionario.setEmail(email);
        funcionario.setSenha(senha);
        funcionario.setLevelAcesso(levelAcesso);
        return funcionario;
    }

    public static Funcionario criarFuncionario(ResultSet rs) throws SQLException {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(rs.getLong("id"));
        funcionario.setNome(rs.getString("nome"));
        funcionario.setCpf(rs.getString("cpf"));
        funcionario.setEmail(rs.getString("email"));
        funcionario.setSenha(rs.getString("senha"));
        funcionario.setLevelAcesso(Validar.isLevelAcesso(rs.getString("acesso")));
        return funcionario;
    }

}
