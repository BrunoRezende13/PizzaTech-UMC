<%@ page import="br.umc.pizzatech.model.conta.Autenticador" %>
<%@ page import="br.umc.pizzatech.model.pessoa.funcionario.LevelAcesso" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Página do Administrador</title>
</head>
<body>

<%
    if (!Autenticador.isLogged(session)) {
%>
<h1>Página disponivel somente para funcionários logados.</h1>
<%
        return;
    }
%>
<h1>Pizza Tech</h1>

<form name="ExtraForm" method="post" action="AdminController">
    <input type="Submit" name="action-e" value="Sair" />
</form>

<%
    if(Autenticador.hasAccess(session, LevelAcesso.GERENTE)){

%>

<fieldset>
    <legend>Funcionário</legend>
    <form name="FuncForm" method="post" action="AdminController">
        <br/>
        <input type="Submit" name="action-f" value="Cadastrar" />
        <input type="Submit" name="action-f" value="Alterar"/>
        <input type="Submit" name="action-f" value="Remover"/>
        <input type="Submit" name="action-f" value="Pesquisar"/>
        <input type="Submit" name="action-f" value="Listar todos"/>
    </form>
</fieldset>
<br>
<br>
<br>
<%
    }
%>

<fieldset>
    <legend>Cliente</legend>
    <form name="ClienteForm" method="post" action="AdminController">
        <br/>
        <input type="Submit" name="action-c" value="Cadastrar" />
        <input type="Submit" name="action-c" value="Alterar"/>
        <input type="Submit" name="action-c" value="Remover"/>
        <input type="Submit" name="action-c" value="Pesquisar"/>
        <input type="Submit" name="action-c" value="Listar todos"/>
    </form>
</fieldset>
<br>
<br>
<br>
<fieldset>
    <legend>Produto</legend>
    <form name="ProdutoForm" method="post" action="AdminController">
        <br/>
        <input type="Submit" name="action-p" value="Cadastrar" />
        <input type="Submit" name="action-p" value="Alterar"/>
        <input type="Submit" name="action-p" value="Remover"/>
        <input type="Submit" name="action-p" value="Pesquisar"/>
        <input type="Submit" name="action-p" value="Listar todos"/>
    </form>
</fieldset>
<br>
<br>
<br>
<fieldset>
    <legend>Pedidos</legend>
    <form name="PedidoForm" method="post" action="AdminController">
        <br/>
        <input type="Submit" name="action-o" value="Iniciar Pedido" />
        <input type="Submit" name="action-o" value="Remover"/>
        <input type="Submit" name="action-o" value="Pesquisar"/>
        <input type="Submit" name="action-o" value="Listar todos"/>

    </form>
</fieldset>
<br>
<br>
<br>
<fieldset>
    <legend>Análise</legend>
    <form name="InfoForm" method="post" action="AdminController">
        <br/>
        <input type="Submit" name="action-i" value="Informações" />
    </form>
</fieldset>
</body>
</html>
