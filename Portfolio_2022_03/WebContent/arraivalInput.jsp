<%@page import="bean.EntryItemBean"%>
<%@page import="bean.StoreBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	String erroMess = (String) request.getAttribute("erroMess");
	List<StoreBean> storeBeanList = (List<StoreBean>) request.getAttribute("storeBeanList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<!-- 自分のCSS -->
<link rel="stylesheet" href="style.css">
<!-- Select2.css -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.5/css/select2.min.css">
<!-- jquery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<!-- Select2本体 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.5/js/select2.min.js"></script>
<!-- Select2日本語化 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.5/js/i18n/ja.js"></script>
<!-- 自分のjs -->
<script src="inventorymanagement.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Insert title here</title>
</head>
<body>

	<div class="centerMain">

		<%
			if (erroMess != null) {

				out.print(erroMess);

			}
		%>

		<h3>入荷処理</h3>

		<form action="ArrivalInputServlet" method="post">

			<div class="slipCode">

				伝票コード <select class="select2" id="storeCode" style="width: 200px;">
					<%
						for (StoreBean storeBean : storeBeanList) {

							out.println("<option value='" + storeBean.getStoreCode() + "'>");
							out.println(storeBean.getStoreName() + "</option>");
						}
					%>
				</select>

				<br>

				受け取り店舗<select class="select2" id="storeCode" style="width: 200px;">
					<%
						for (StoreBean storeBean : storeBeanList) {

							out.println("<option value='" + storeBean.getStoreCode() + "'>");
							out.println(storeBean.getStoreName() + "</option>");
						}
					%>
				</select>

			</div>

			<!-- 入力数がゼロにならないように一つは消さない -->
			<ul id="formUl">

				<li class="formLi"><input type="text" name="itemCode" placeholder='商品コード'> <input type="number" name="itemCount" placeholder='個数' min="1" step="1"></li>

			</ul>

			<div class="underArea">

				<input type="button" value="入力商品追加" onclick="addForm()">

				<input type="submit" value="確認" onclick="return confi()">

			</div>

		</form>

		<a href="index.jsp">トップページ</a>


	</div>
</body>
</html>