package br.umc.pizzatech.model.pessoa.funcionario;

import br.umc.pizzatech.model.conta.Conta;
import br.umc.pizzatech.model.pessoa.Pessoa;

public class Funcionario extends Pessoa implements Conta {

    private String email;
    private String senha;
    private LevelAcesso levelAcesso;

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getSenha() {
        return senha;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public LevelAcesso getLevelAcesso() {
        return levelAcesso;
    }

    public void setLevelAcesso(LevelAcesso levelAcesso) {
        this.levelAcesso = levelAcesso;
    }

}
