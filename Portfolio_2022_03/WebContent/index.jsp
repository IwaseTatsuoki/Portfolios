<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <%

    String erroMess = (String)request.getAttribute("erroMess");

    %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <link rel="stylesheet" href="style.css">
<title>Insert title here</title>
</head>
<body>

<%
		if (erroMess != null) {

			out.print(erroMess);

		}
	%>

	<main>

	<h2>在庫管理システム</h2>


        <ul>

            <li><a href="shippingInput.jsp">出荷処理</a></li>

            <li><a href="Arrival.jsp">入荷処理</a></li>

            <li><a href="InventoryListServlet">在庫一覧</a></li>

            <li><a href="ReturnServlet">返品</a></li>

        </ul>

    </main>

</body>
</html>