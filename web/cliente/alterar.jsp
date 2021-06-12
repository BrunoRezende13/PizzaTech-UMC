<%@ page import="br.umc.pizzatech.model.conta.Autenticador" %>
<%@ page import="br.umc.pizzatech.model.pessoa.funcionario.LevelAcesso" %>
<%@ page import="br.umc.pizzatech.model.pessoa.cliente.Cliente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Alterar Cliente</title>
</head>
<body>

<%
    if (!Autenticador.hasAccess(session, LevelAcesso.ATENDENTE)) {
%>
<h1>Página disponivel somente para funcionários logados.</h1>
<a href="../login.jsp">Voltar</a>
<%
        return;
    }

    Cliente cliente = (Cliente) session.getAttribute("cliente_pesquisa");
    if(cliente == null){
        response.sendRedirect("admin.jsp");
        return;
    }

%>

<h1>Cadastrar Cliente</h1>
<form action="ClienteController" method="POST">
    <br>
    Id.......: <%=cliente.getId()%> <br>
    Nome.......: <input type="text" name="txNome" value="<%=cliente.getNome()%>"> <br>
    Cpf........: <input type="text" name="txCpf" value="<%=cliente.getCpf()%>"> <br>
    Endereço......: <input type="text" name="txEndereco" value="<%=cliente.getEndereco()%>"> <br>
    Telefone......: <input type="text" name="txTelefone" value="<%=cliente.getTelefone()%>"> <br>
    <br>
    <br>
    <input type="submit" name="action" value="Alterar">
    <input type="submit" name="action" value="Voltar">
</form>

</body>
</html>
