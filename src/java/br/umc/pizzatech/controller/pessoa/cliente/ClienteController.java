package br.umc.pizzatech.controller.pessoa.cliente;

import br.umc.pizzatech.controller.RollbackServlet;
import br.umc.pizzatech.dao.pessoa.ClienteDAO;
import br.umc.pizzatech.model.pessoa.cliente.Cliente;
import br.umc.pizzatech.util.ValidaCpf;
import br.umc.pizzatech.util.Validar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ClienteController", value = "/ClienteController")
public class ClienteController extends RollbackServlet {

    public ClienteController() {
        new ClienteDAO().criarTabela();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String action = request.getParameter("action");

            if (action.equals("Cadastrar")) {
                String nome = request.getParameter("txNome");
                String cpf = request.getParameter("txCpf");
                String endereco = request.getParameter("txEndereco");
                String telefone = request.getParameter("txTelefone");

                Cliente cliente = new Cliente();
                cliente.setNome(nome);
                cliente.setCpf(cpf);
                cliente.setEndereco(endereco);
                cliente.setTelefone(telefone);

                String validar = verificar(cliente);
                if(validar != null) {
                    sendErrorMessage(validar, request, response);
                    return;
                }

                ClienteDAO dao = new ClienteDAO();
                dao.cadastrar(cliente);
                sendSuccessMessage("Cliente cadastrado com sucesso!", request, response);

            } else if (action.equals("Remover")) {
                String txId = request.getParameter("txId");

                if (!Validar.isLong(txId)) {
                    sendErrorMessage("Esse id é inválido.", request, response);
                    return;
                }

                ClienteDAO dao = new ClienteDAO();

                Cliente cliente = new Cliente();
                cliente.setId(Long.parseLong(txId));

                int deletar = dao.deletar(cliente);

                if(deletar == 0){
                    sendErrorMessage("Cliente não encontrado.", request, response);
                } else {
                    sendSuccessMessage("Cliente removido com sucesso!", request, response);
                }

            } else if (action.equals("Alterar")) {
                Object funcionarioPesquisa = request.getSession().getAttribute("cliente_pesquisa");
                if(funcionarioPesquisa == null){
                    sendErrorMessage("Cliente inválido", request, response);
                    return;
                }

                Cliente pesquisado = (Cliente) funcionarioPesquisa;

                String nome = request.getParameter("txNome");
                String cpf = request.getParameter("txCpf");
                String endereco = request.getParameter("txEndereco");
                String telefone = request.getParameter("txTelefone");

                Cliente cliente = new Cliente();
                cliente.setId(pesquisado.getId());
                cliente.setNome(nome);
                cliente.setCpf(cpf);
                cliente.setEndereco(endereco);
                cliente.setTelefone(telefone);

                String validar = verificar(cliente);
                if(validar != null) {
                    sendErrorMessage(validar, request, response);
                    return;
                }

                ClienteDAO dao = new ClienteDAO();

                dao.alterar(cliente);
                sendSuccessMessage("Cliente alterado com sucesso!", request, response);
            } else if (action.equals("Pesquisar")) {
                String txId = request.getParameter("txId");

                if (!Validar.isLong(txId)) {
                    sendErrorMessage("Esse id é inválido.", request, response);
                    return;
                }

                ClienteDAO dao = new ClienteDAO();

                Cliente funcionario = new Cliente();
                funcionario.setId(Long.parseLong(txId));

                Cliente resultado = dao.buscarPorId(funcionario);

                if(resultado == null){
                    sendErrorMessage("Cliente não encontrado.", request, response);
                } else {
                    request.getSession().setAttribute("cliente_pesquisa", resultado);

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

    private String verificar(Cliente cliente) {

        if(cliente.getCpf() == null)
            cliente.setCpf("");

        if (!cliente.getCpf().isEmpty() && !ValidaCpf.isCPF(cliente.getCpf())) {
            return "Cpf é inválido.";
        }

        return null;
    }


}
