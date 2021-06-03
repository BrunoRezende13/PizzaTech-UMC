package br.umc.pizzatech.model.produto;

import br.umc.pizzatech.model.produto.pizza.ProdutoPizza;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdutoFactory {


    public static Produto criarProduto(String nome, String descricao, String ingredientes, double valor, int estoque, String tipo){
        Produto produto;
        if(tipo.equals("Pizza")){
            produto = new ProdutoPizza();
        } else {
            produto = new Produto();
        }
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setIngredientes(ingredientes);
        produto.setValor(valor);
        produto.setEstoque(estoque);
        return produto;
    }

    public static Produto criarProduto(ResultSet resultSet) throws SQLException {
        Produto produto;
        if(resultSet.getBoolean("pizza")){
            produto = new ProdutoPizza();
        } else {
            produto = new Produto();
        }
        produto.setId(resultSet.getLong("id"));
        produto.setNome(resultSet.getString("nome"));
        produto.setDescricao(resultSet.getString("descricao"));
        produto.setIngredientes(resultSet.getString("ingredientes"));
        produto.setValor(resultSet.getDouble("valor"));
        produto.setEstoque(resultSet.getInt("estoque"));
        return produto;
    }

}
