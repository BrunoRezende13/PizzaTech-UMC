package br.umc.pizzatech.teste;

import br.umc.pizzatech.dao.pessoa.FuncionarioDAO;
import br.umc.pizzatech.model.pessoa.funcionario.Funcionario;

import java.sql.SQLException;

public class TesteConexao {

    public static void main(String[] args) {

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        try {
            Funcionario funcionario = new Funcionario();
            funcionario.setNome("Teste");
            funcionario.setCpf("81246638665");
            funcionario.setSenha("6551ds");
            funcionario.setEmail("email.bruno@gmai.com");
            funcionarioDAO.cadastrar(funcionario);


        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
