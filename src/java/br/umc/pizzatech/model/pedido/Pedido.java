package br.umc.pizzatech.model.pedido;

import br.umc.pizzatech.model.pedido.carrinho.ProdutoCarrinho;
import br.umc.pizzatech.model.pessoa.cliente.Cliente;
import br.umc.pizzatech.model.pessoa.funcionario.Funcionario;

import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private long id;
    private Cliente cliente;
    private Funcionario funcionario;
    private String anotacao = "";
    private PedidoStatus status;
    private List<ProdutoCarrinho> itens = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public String getAnotacao() {
        return anotacao;
    }

    public void setAnotacao(String anotacao) {
        this.anotacao = anotacao;
    }

    public PedidoStatus getStatus() {
        return status;
    }

    public void setStatus(PedidoStatus status) {
        this.status = status;
    }

    public void addNovoItem(ProdutoCarrinho item){
        this.itens.add(item);
    }

    public void setItens(List<ProdutoCarrinho> itens) {
        this.itens = itens;
    }

    public void removerItem(ProdutoCarrinho itemRemove){
        this.itens.remove(itemRemove);
    }

    public List<ProdutoCarrinho> getItens() {
        return itens;
    }

    public double getTotal() {
        double total = 0;
        for (ProdutoCarrinho item : itens) {
            total += item.calcularTotal();
        }
        return total;
    }



}
