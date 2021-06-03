package br.umc.pizzatech.model.produto.pizza;

public enum TamanhoPizza {

    PEQUENA("Pequena", 4),
    MEDIA("Média", 6),
    GRANDE("Grande", 8),
    FAMILIA("Família",10);

    private final String nome;
    private final int fatias;

    TamanhoPizza(String nome, int fatias) {
        this.nome = nome;
        this.fatias = fatias;
    }

    public String getNome() {
        return nome;
    }

    public int getFatias() {
        return fatias;
    }

}
