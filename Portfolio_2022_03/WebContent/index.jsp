<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	String erroMess = (String) request.getAttribute("erroMess");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="style.css">
<title>Insert title here</title>
</head>
<body>

	<main>


	<%
		if (erroMess != null) {

			out.print(erroMess);


		}
	%>



	<h2>在庫管理システム</h2>


	<ul>

		<li><a href="shippingInput.jsp">出荷処理</a></li>

		<li><a href="arraivalInput.jsp">入荷処理</a></li>

		<li><a href="StoreListServlet">在庫一覧</a></li>

		<li><a href="slipList.jsp">返品</a></li>

	</ul>

	</main>

</body>
</html>