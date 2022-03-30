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

	<div class="centerMain">


		<%
			if (erroMess != null) {

				out.print(erroMess);

			}
		%>

		<h2>在庫管理システム</h2>

		<!--すべて同じサーブレットで上二つは店舗情報に加え商品情報もDBからもってくる  -->
		<ul class="indexUl">

			<li class="indexLi"><a href="StoreListServlet?url=shippingInput.jsp">出荷処理</a></li>

			<li class="indexLi"><a href="StoreItemListServlet?url=arraivalInput.jsp">入荷処理</a></li>

			<li class="indexLi"><a href="StoreListServlet?url=inventoryList.jsp">在庫一覧</a></li>

			<li class="indexLi"><a href="StoreListServlet?url=slipList.jsp">返品</a></li>

		</ul>

	</div>

</body>
</html>