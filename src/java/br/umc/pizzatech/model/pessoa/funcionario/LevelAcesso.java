package br.umc.pizzatech.model.pessoa.funcionario;

public enum LevelAcesso {

    ATENDENTE(1, "Atendente"),
    GERENTE(2, "Gerente");

    private final int id;
    private final String name;

    LevelAcesso(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean igualOuSuperior(LevelAcesso levelAcesso){
        return ordinal() >= levelAcesso.ordinal();
    }

    public static LevelAcesso getById(int id){
        for (LevelAcesso acesso : values()) {
            if(id == acesso.getId()){
                return acesso;
            }
        }
        return null;
    }

}
