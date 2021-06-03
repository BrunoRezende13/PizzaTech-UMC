package br.umc.pizzatech.model.pessoa.cliente;

import br.umc.pizzatech.model.pessoa.Pessoa;

public class Cliente extends Pessoa {

    private String telefone;
    private String endereco;

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
