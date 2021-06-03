<%@ page import="br.umc.pizzatech.model.conta.Autenticador" %>
<%@ page import="br.umc.pizzatech.model.pessoa.funcionario.LevelAcesso" %>
<%@ page import="br.umc.pizzatech.model.pedido.Pedido" %>
<%@ page import="java.util.List" %>
<%@ page import="br.umc.pizzatech.model.pedido.PedidoStatus" %>
<%@ page import="br.umc.pizzatech.dao.pedido.PedidoDAO" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Exibir Informações</title>
</head>
<body>

<%
    if (!Autenticador.hasAccess(session, LevelAcesso.GERENTE)) {
%>
<h1>Página disponivel somente para gerentes.</h1>
<%
        return;
    }


    PedidoDAO pedidoDAO = new PedidoDAO();
    List<Pedido> pedidos;
    try {
        pedidos = pedidoDAO.buscarTodos();

        if (pedidos == null) {
            response.sendRedirect("admin.jsp");
            return;
        }

        for (Pedido pedido : pedidos) {
            pedido.setItens(pedidoDAO.buscarTodos(pedido));
        }

    } catch (Exception exception) {
        exception.printStackTrace();
%><h1>Erro.</h1><%
        return;
    }

%>

<fieldset>
    <legend>Pedidos</legend>
    <form action="PedidoController" method="POST">
        <fieldset>
            <legend>Quantidade</legend>
            <br>
            Total.........: <%= pedidos.size() %> <br>

            <%

                for (PedidoStatus status : PedidoStatus.values()) {
                    int i = 0;
                    for (Pedido pedido : pedidos) {
                        if (pedido.getStatus() == status) {
                            i++;
                        }
                    }

            %>
            <%=status.getNome()%>...: <%=i%><br>
            <%
                }

            %>
        </fieldset>
    </form>

    <fieldset>
        <legend>Financeiro</legend>
        <form action="PedidoController" method="POST">
            <br>
            <%
                double total = 0;
                for (Pedido pedido : pedidos) {
                    total += pedido.getTotal();
                }

            %>
            Total.........: R$<%= total %> <br>

            <%
                for (PedidoStatus status : PedidoStatus.values()) {
                    total = 0;
                    for (Pedido pedido : pedidos) {
                        if (pedido.getStatus() == status) {
                            total += pedido.getTotal();
                        }
                    }

            %>
            <%=status.getNome()%>...: R$<%=total%><br>
            <%
                }

            %>
        </form>
    </fieldset>

</fieldset>
<br>
<br>
<form action="PedidoController" method="POST">
    <input type="submit" name="action" value="Voltar">
</form>


</body>
</html>
