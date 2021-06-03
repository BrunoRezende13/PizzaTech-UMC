package br.umc.pizzatech.model.pedido.carrinho;

import br.umc.pizzatech.model.produto.Produto;

public class ProdutoCarrinho {

    private long id;
    private Produto produto;
    private int quantidade;
    private double total;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void addQuantidade(){
        quantidade++;
    }

    public double getTotal(){
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double calcularTotal(){
        total = produto.getValor() * quantidade;
        return getTotal();
    }

    public boolean verificarEstoque(){
        return quantidade <= produto.getEstoque();
    }

}
