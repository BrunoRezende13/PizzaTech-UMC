package br.umc.pizzatech.model.conta;

import br.umc.pizzatech.model.pessoa.funcionario.LevelAcesso;

public interface Conta {

    String getEmail();

    String getSenha();

    LevelAcesso getLevelAcesso();

}
