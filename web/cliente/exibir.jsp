<%@ page import="br.umc.pizzatech.model.conta.Autenticador" %>
<%@ page import="br.umc.pizzatech.model.pessoa.funcionario.LevelAcesso" %>
<%@ page import="br.umc.pizzatech.model.pessoa.cliente.Cliente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Listar Cliente</title>
</head>
<body>

<%
    if (!Autenticador.hasAccess(session, LevelAcesso.ATENDENTE)) {
%>
<h1>Página disponivel somente para funcionários.</h1>
<%
        return;
    }

    Cliente cliente = (Cliente) session.getAttribute("cliente_pesquisa");

    if(cliente == null){
        response.sendRedirect("admin.jsp");
        return;
    }

%>

<h1>Cliente</h1>

<form action="ClienteController" method="POST">
    <br>
    Id.........: <%=cliente.getId()%> <br>
    Nome.......: <%=cliente.getNome()%> <br>
    Cpf........: <%=cliente.getCpf()%> <br>
    Endereço......: <%=cliente.getEndereco()%> <br>
    Telefone......: <%=cliente.getTelefone()%> <br>

    <br>
    <input type="submit" name="action" value="Voltar">
</form>

</body>
</html>
