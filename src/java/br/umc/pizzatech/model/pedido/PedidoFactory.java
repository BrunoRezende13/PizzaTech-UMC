package br.umc.pizzatech.model.pedido;

import br.umc.pizzatech.dao.pessoa.ClienteDAO;
import br.umc.pizzatech.dao.pessoa.FuncionarioDAO;
import br.umc.pizzatech.dao.produto.ProdutoDAO;
import br.umc.pizzatech.model.pedido.carrinho.ProdutoCarrinho;
import br.umc.pizzatech.model.pessoa.cliente.Cliente;
import br.umc.pizzatech.model.pessoa.funcionario.Funcionario;
import br.umc.pizzatech.model.produto.Produto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PedidoFactory {

    public static Pedido criarPedido(ResultSet rs) throws SQLException, ClassNotFoundException {
        Pedido pedido = new Pedido();
        pedido.setId(rs.getLong("id"));
        pedido.setStatus(PedidoStatus.valueOf(rs.getString("status")));
        pedido.setAnotacao(rs.getString("anotacao"));

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        Funcionario funcionario = new Funcionario();
        funcionario.setId(rs.getLong("id_funcionario"));
        pedido.setFuncionario(funcionarioDAO.buscarPorId(funcionario));

        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = new Cliente();
        cliente.setId(rs.getLong("id_cliente"));
        pedido.setCliente(clienteDAO.buscarPorId(cliente));

        return pedido;
    }

    public static ProdutoCarrinho criarProdutoCarrinho(ResultSet rs) throws SQLException, ClassNotFoundException {
        ProdutoCarrinho produto = new ProdutoCarrinho();
        produto.setId(rs.getLong("id"));
        produto.setQuantidade(rs.getInt("quantidade"));
        produto.setTotal(rs.getDouble("total"));

        ProdutoDAO produtoDAO = new ProdutoDAO();
        Produto produto1 = new Produto();
        produto1.setId(rs.getLong("id_produto"));

        produto.setProduto(produtoDAO.buscarPorId(produto1));

        return produto;
    }

}
