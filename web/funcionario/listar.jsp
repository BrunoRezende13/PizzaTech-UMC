<%@ page import="br.umc.pizzatech.model.conta.Autenticador" %>
<%@ page import="br.umc.pizzatech.model.pessoa.funcionario.Funcionario" %>
<%@ page import="java.util.List" %>
<%@ page import="br.umc.pizzatech.model.pessoa.funcionario.LevelAcesso" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Listar Funcionários</title>
</head>
<body>

<%
    if (!Autenticador.hasAccess(session, LevelAcesso.GERENTE)) {
%>
<h1>Página disponivel somente para gerentes logados.</h1>
<a href="../login.jsp">Voltar</a>
<%
        return;
    }
%>

<h1>Listar Funcionarios</h1>

<table border="1">
    <tr><th>Id</th><th>Nome</th><th>Cpf</th><th>Email</th><th>Level</th><th>Alterar</th><th>Remover</th></tr>

    <%
        request.getSession().setAttribute("dispatcher", "funcionario/alterar.jsp");

        List<Funcionario> list = (List<Funcionario>) request.getAttribute("lista");

        for (Funcionario funcionario : list) {
    %>

    <tr>
        <td><%= funcionario.getId()%></td>
        <td><%= funcionario.getNome()%></td>
        <td><%= funcionario.getCpf()%></td>
        <td><%= funcionario.getEmail()%></td>
        <td><%= funcionario.getLevelAcesso().getName()%></td>
        <td>
            <a href="${pageContext.request.contextPath}/FuncionarioListarController?txId=<%=funcionario.getId()%>&action=Alterar">
                <img src="img/editar.png" style="width:40px;height:40px;">
            </a>
        </td>
        <td>
            <a href="${pageContext.request.contextPath}/FuncionarioListarController?txId=<%=funcionario.getId()%>&action=Remover">
                <img src="img/deletar.png" style="width:40px;height:40px;">
            </a>
        </td>
    </tr>

    <% }%>
</table>
<br>
<br>
<form action="FuncionarioController" method="POST">
    <input type="submit" name="action" value="Voltar">
</form>

</body>
</html>
