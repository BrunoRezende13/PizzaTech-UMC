package br.umc.pizzatech.model.conta;

import br.umc.pizzatech.dao.pessoa.FuncionarioDAO;
import br.umc.pizzatech.model.pessoa.funcionario.Funcionario;
import br.umc.pizzatech.model.pessoa.funcionario.FuncionarioFactory;
import br.umc.pizzatech.model.pessoa.funcionario.LevelAcesso;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class Autenticador {

    private static final boolean teste = false;

    public static Conta autentificar(Conta login) throws SQLException, ClassNotFoundException {
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        return funcionarioDAO.autentificar(login);
    }

    public static void logged(HttpSession session, Conta conta){
        session.setAttribute("Conta", conta);
    }

    public static boolean isLogged(HttpSession session){
        if(teste) {
            return true;
        }
        if(session.isNew())
            return false;
        Object conta = session.getAttribute("Conta");
        return conta != null;
    }

    public static boolean hasAccess(HttpSession session, LevelAcesso levelAcesso){
        if(teste)
            return true;
        if(session.isNew())
            return false;
        Conta conta = (Conta) session.getAttribute("Conta");
        return conta != null && conta.getLevelAcesso() != null && conta.getLevelAcesso().igualOuSuperior(levelAcesso);
    }

    public static Funcionario getFuncionario(HttpSession session){
        if(session.isNew())
            return null;
        return (Funcionario) session.getAttribute("Conta");
    }


}
