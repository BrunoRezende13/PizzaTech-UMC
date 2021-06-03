package br.umc.pizzatech.model.produto.pizza;

import br.umc.pizzatech.model.produto.Produto;

public class ProdutoPizza extends Produto {

    @Override
    public boolean isPizza(){
        return true;
    }

    @Override
    public String getTipo() {
        return "Pizza";
    }

}
