<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	String erroMess = (String) request.getAttribute("erroMess");
	String uuidString = (String) session.getAttribute("uuidString");
	String success = (String) session.getAttribute("success");
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

		<div class="messArea">

			<!--出荷伝票を表示更新で何度も表示されないようにセッションから消す。 -->
			<%
			if (uuidString != null) {

				out.print("出荷伝票コード<br>");
				out.print(uuidString);
				session.removeAttribute("uuidString");

			}

			if (success != null) {

				out.print(success);
				session.removeAttribute("success");

			}
		%>

		</div>

		<!--すべて同じサーブレットで上二つは店舗情報に加え商品情報もDBからもってくる  -->
		<ul class="indexUl">

			<li class="indexLi"><a href="StoreListServlet?url=shippingInput.jsp">出荷処理</a></li>

			<li class="indexLi"><a href="StoreListServlet?url=arraivalInput.jsp">入荷処理</a></li>

			<li class="indexLi"><a href="StoreListServlet?url=inventoryList.jsp">在庫一覧</a></li>

			<li class="indexLi"><a href="StoreListServlet?url=slipList.jsp">返品</a></li>

		</ul>

	</div>

</body>
</html>