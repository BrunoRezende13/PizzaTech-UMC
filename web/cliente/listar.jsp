<%@ page import="br.umc.pizzatech.model.conta.Autenticador" %>
<%@ page import="java.util.List" %>
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
%>

<h1>Listar Cliente</h1>

<table border="1">
    <tr><th>Id</th><th>Nome</th><th>Cpf</th><th>Telefone</th><th>Endereco</th><th>Carrinho</th><th>Alterar</th><th>Remover</th></tr>

    <%
        request.getSession().setAttribute("dispatcher", "cliente/alterar.jsp");

        List<Cliente> list = (List<Cliente>) request.getAttribute("lista");

        if(list == null || list.isEmpty()){
            %>
            <p>Nenhum Cliente encontrado.</p>
            <%
            return;
        }

        for (Cliente cliente : list) {
    %>

    <tr>
        <td><%= cliente.getId()%></td>
        <td><%= cliente.getNome()%></td>
        <td><%= cliente.getCpf()%></td>
        <td><%= cliente.getTelefone()%></td>
        <td><%= cliente.getEndereco()%></td>
        <td>
            <a href="${pageContext.request.contextPath}/carrinho/CarrinhoController?txId=<%=cliente.getId()%>&action=Iniciar">
                <img src="img/carrinho.png" style="width:40px;height:40px;">
            </a>
        </td>
        <td>
            <a href="${pageContext.request.contextPath}/ClienteController?txId=<%=cliente.getId()%>&action=Alterar">
                <img src="img/editar.png" style="width:40px;height:40px;">
            </a>
        </td>
        <td>
            <a href="${pageContext.request.contextPath}/ClienteController?txId=<%=cliente.getId()%>&action=Remover">
                <img src="img/deletar.png" style="width:40px;height:40px;">
            </a>
        </td>
    </tr>

    <% }%>
</table>
<br>
<br>
<form action="ClienteController" method="POST">
    <input type="submit" name="action" value="Voltar">
</form>

</body>
</html>
