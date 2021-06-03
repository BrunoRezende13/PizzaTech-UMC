package br.umc.pizzatech.model.pedido;

public enum PedidoStatus {

    ABERTO("Aberto"),
    EM_ANDAMENTO("Em Andamento"),
    FINALIZADO("Finalizado");

    private final String nome;

    PedidoStatus(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
