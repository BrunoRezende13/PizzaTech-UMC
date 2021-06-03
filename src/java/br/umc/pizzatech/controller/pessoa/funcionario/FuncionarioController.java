package br.umc.pizzatech.controller.pessoa.funcionario;

import br.umc.pizzatech.controller.RollbackServlet;
import br.umc.pizzatech.dao.pessoa.FuncionarioDAO;
import br.umc.pizzatech.model.pessoa.funcionario.Funcionario;
import br.umc.pizzatech.model.pessoa.funcionario.LevelAcesso;
import br.umc.pizzatech.util.ValidaCpf;
import br.umc.pizzatech.util.Validar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "FuncionarioController", value = "/FuncionarioController")
public class FuncionarioController extends RollbackServlet {

    public FuncionarioController() {
        new FuncionarioDAO().criarTabela();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String action = request.getParameter("action");

            if (action.equals("Cadastrar")) {
                String nome = request.getParameter("txNome");
                String cpf = request.getParameter("txCpf");
                String email = request.getParameter("txEmail");
                String senha = request.getParameter("txSenha");
                LevelAcesso selLevel = Validar.isLevelAcesso(request.getParameter("selLevel"));

                if(selLevel == null){
                    sendErrorMessage("Level de acesso inválido", request, response);
                    return;
                }

                Funcionario funcionario = new Funcionario();
                funcionario.setNome(nome);
                funcionario.setCpf(cpf);
                funcionario.setEmail(email);
                funcionario.setSenha(senha);
                funcionario.setLevelAcesso(selLevel);

                String validar = validarFuncionario(funcionario);
                if(validar != null) {
                    sendErrorMessage(validar, request, response);
                    return;
                }

                FuncionarioDAO dao = new FuncionarioDAO();
                dao.cadastrar(funcionario);
                sendSuccessMessage("Funcionário cadastrado com sucesso!", "admin.jsp", request, response);

            } else if (action.equals("Remover")) {
                String txId = request.getParameter("txId");

                if (!Validar.isLong(txId)) {
                    sendErrorMessage("Esse id é inválido.", request, response);
                    return;
                }

                FuncionarioDAO dao = new FuncionarioDAO();

                Funcionario funcionario = new Funcionario();
                funcionario.setId(Long.parseLong(txId));

                int deletar = dao.deletar(funcionario);

                if(deletar == 0){
                    sendErrorMessage("Funcionário não encontrado.", request, response);
                } else {
                    sendSuccessMessage("Funcionário removido com sucesso!", "admin.jsp", request, response);
                }

            } else if (action.equals("Alterar")) {
                Object funcionarioPesquisa = request.getSession().getAttribute("funcionario_pesquisa");
                if(funcionarioPesquisa == null){
                    sendErrorMessage("Funcionário inválido", request, response);
                    return;
                }

                Funcionario pesquisado = (Funcionario) funcionarioPesquisa;

                String nome = request.getParameter("txNome");
                String cpf = request.getParameter("txCpf");
                String email = request.getParameter("txEmail");
                String senha = request.getParameter("txSenha");
                LevelAcesso selLevel = Validar.isLevelAcesso(request.getParameter("selLevel"));

                if(selLevel == null){
                    sendErrorMessage("Level de acesso inválido", request, response);
                    return;
                }

                Funcionario funcionario = new Funcionario();
                funcionario.setId(pesquisado.getId());
                funcionario.setNome(nome);
                funcionario.setCpf(cpf);
                funcionario.setEmail(email);
                funcionario.setSenha(senha);
                funcionario.setLevelAcesso(selLevel);

                String validar = validarFuncionario(funcionario);
                if(validar != null) {
                    sendErrorMessage(validar, request, response);
                    return;
                }

                FuncionarioDAO dao = new FuncionarioDAO();

                dao.alterar(funcionario);
                sendSuccessMessage("Funcionário alterado com sucesso!", "admin.jsp", request, response);
            } else if (action.equals("Pesquisar")) {
                String txId = request.getParameter("txId");

                if (!Validar.isLong(txId)) {
                    sendErrorMessage("Esse id é inválido.", request, response);
                    return;
                }

                FuncionarioDAO dao = new FuncionarioDAO();

                Funcionario funcionario = new Funcionario();
                funcionario.setId(Long.parseLong(txId));

                Funcionario resultado = dao.buscarPorId(funcionario);

                if(resultado == null){
                    sendErrorMessage("Funcionário não encontrado.", request, response);
                } else {
                    request.getSession().setAttribute("funcionario_pesquisa", resultado);

                    String dispatcher = (String) request.getSession().getAttribute("dispatcher");
                    request.getRequestDispatcher(dispatcher).forward(request, response);
                }

            } else if (action.equals("Voltar")) {
                response.sendRedirect("admin.jsp");
            } else {
                sendErrorMessage("Ação não encontrada.", request, response);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            sendErrorMessage("Ocorreu um erro ao executar a ação. " + exception.getMessage(), request, response);
        }

    }

    private String validarFuncionario(Funcionario funcionario) {

        if (!Validar.isEmail(funcionario.getEmail())) {
            return "Esse email é inválido.";
        }

        if (!ValidaCpf.isCPF(funcionario.getCpf())) {
            return "Cpf é inválido.";
        }

        if (funcionario.getSenha().length() < 4 || funcionario.getSenha().length() > 30) {
            return "A senha deve ter entre 4-30 caracteres.";
        }

        if (funcionario.getNome().isEmpty() || funcionario.getNome().length() > 60) {
            return "O nome é muito grande ou inexistente.";
        }

        return null;
    }


}
