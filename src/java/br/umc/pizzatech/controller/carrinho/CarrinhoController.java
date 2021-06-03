package br.umc.pizzatech.controller.carrinho;

import br.umc.pizzatech.controller.RollbackServlet;
import br.umc.pizzatech.dao.pedido.PedidoDAO;
import br.umc.pizzatech.dao.pessoa.ClienteDAO;
import br.umc.pizzatech.dao.produto.ProdutoDAO;
import br.umc.pizzatech.model.conta.Autenticador;
import br.umc.pizzatech.model.pedido.Pedido;
import br.umc.pizzatech.model.pedido.PedidoStatus;
import br.umc.pizzatech.model.pedido.carrinho.ProdutoCarrinho;
import br.umc.pizzatech.model.pessoa.cliente.Cliente;
import br.umc.pizzatech.model.produto.Produto;
import br.umc.pizzatech.util.Validar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CarrinhoController", value = "/carrinho/CarrinhoController")
public class CarrinhoController extends RollbackServlet {

    public CarrinhoController() {
        new ClienteDAO().criarTabela();
        new PedidoDAO().criarTabela();

        erroPath = "../" + erroPath;
        sucessoPath = "../" + sucessoPath;
        rollbackPath = "../" + rollbackPath;

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {

            String txId = request.getParameter("txId");

            if (!Validar.isLong(txId)) {
                sendErrorMessage("Esse id é inválido.", request, response);
                return;
            }


            if (action.equals("Iniciar")) {

                ClienteDAO dao = new ClienteDAO();

                Cliente cliente = new Cliente();
                cliente.setId(Long.parseLong(txId));

                Cliente resultado = dao.buscarPorId(cliente);

                if (resultado == null) {
                    sendErrorMessage("Cliente não encontrado.", request, response);
                } else {

                    Pedido pedido = new Pedido();
                    pedido.setCliente(resultado);
                    pedido.setFuncionario(Autenticador.getFuncionario(request.getSession()));
                    pedido.setStatus(PedidoStatus.ABERTO);

                    request.getSession().setAttribute("carrinho", pedido);

                    response.sendRedirect("produtos.jsp");
                }

            } else if (action.equals("ProdutoAdicionar")) {

                Pedido carrinho = (Pedido) request.getSession().getAttribute("carrinho");

                if (carrinho == null) {
                    sendErrorMessage("Carrinho não encontrado.", request, response);
                    return;
                }

                ProdutoDAO produtoDAO = new ProdutoDAO();
                Produto produto = new Produto();
                produto.setId(Long.parseLong(txId));

                Produto resultado = produtoDAO.buscarPorId(produto);

                if (resultado == null) {
                    sendErrorMessage("Produto não encontrado.", request, response);
                    return;
                }

                boolean encontrado = false;
                for (ProdutoCarrinho item : carrinho.getItens()) {
                    if (item.getProduto().isPizza())
                        continue;

                    if (item.getProduto().getId() == resultado.getId()) {
                        item.addQuantidade();
                        encontrado = true;
                        break;
                    }

                }

                if (!encontrado) {
                    ProdutoCarrinho produtoCarrinho = new ProdutoCarrinho();
                    produtoCarrinho.setProduto(resultado);
                    produtoCarrinho.setQuantidade(1);

                    carrinho.addNovoItem(produtoCarrinho);
                }

                response.sendRedirect("carrinho.jsp");

            } else if (action.equals("ProdutoRemover")) {

                Pedido carrinho = (Pedido) request.getSession().getAttribute("carrinho");

                if (carrinho == null) {
                    sendErrorMessage("Carrinho não encontrado.", request, response);
                    return;
                }

                long id = Long.parseLong(txId);

                carrinho.getItens().removeIf(produtoCarrinho -> produtoCarrinho.getProduto().getId() == id);

                response.sendRedirect("carrinho.jsp");

            }


        } catch (Exception exception) {
            exception.printStackTrace();
            sendErrorMessage("Ocorreu um erro ao executar a ação. " + exception.getMessage(), "carrinho/carrinho.jsp", request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            response.setContentType("text/html");
            String action = request.getParameter("action");
            if (action.equals("Cancelar Compra")) {
                request.getSession().removeAttribute("carrinho");
                response.sendRedirect("../admin.jsp");
            } else if (action.equals("Continuar Compra")) {
                response.sendRedirect("produtos.jsp");
            } else if (action.equals("Finalizar Compra")) {
                Pedido carrinho = (Pedido) request.getSession().getAttribute("carrinho");

                if (carrinho == null) {
                    sendErrorMessage("Carrinho não encontrado.", request, response);
                    return;
                }

                for (ProdutoCarrinho item : carrinho.getItens()) {
                    if(!item.verificarEstoque()){
                        sendErrorMessage("Falta de estoque para o produto " + item.getProduto().getNome() + ".", "carrinho.jsp", request, response);
                        return;
                    }
                }

                ProdutoDAO produtoDAO = new ProdutoDAO();

                for (ProdutoCarrinho item : carrinho.getItens()) {
                    item.getProduto().setEstoque(item.getProduto().getEstoque() - item.getQuantidade());
                    produtoDAO.atualizarEstoque(item.getProduto());
                }

                PedidoDAO pedidoDAO = new PedidoDAO();

                carrinho.setStatus(PedidoStatus.EM_ANDAMENTO);
                pedidoDAO.criar(carrinho);

                sendSuccessMessage("Pedido realizado com sucesso!", request, response);

            } else if (action.equals("Salvar Anotação")) {
                Pedido carrinho = (Pedido) request.getSession().getAttribute("carrinho");

                if (carrinho == null) {
                    return;
                }

                String anotacao = request.getParameter("txAnotacao");
                anotacao = anotacao == null ? "" : anotacao; //.replaceAll("\n", "<br>")
                carrinho.setAnotacao(anotacao);
                request.getRequestDispatcher("carrinho.jsp").forward(request, response);
            } else {
                sendErrorMessage("Ação não encontrada.", request, response);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            sendErrorMessage("Ocorreu um erro ao executar a ação. " + exception.getMessage(), request, response);
        }

    }

}
