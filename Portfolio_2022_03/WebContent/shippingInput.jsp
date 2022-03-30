<%@page import="bean.StoreBean"%>
<%@page import="org.apache.catalina.session.StoreBase"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
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
<link rel="stylesheet" href="style.css">
<title>Insert title here</title>
</head>
<body>

	<div class="centerMain">

		<h3>出荷処理</h3>

		<form action="ShippingInputServlet" method="post">

			<div class="sendInputArea">
				送り元店舗 <select class="select2" id="sender" name="sender" style="width: 200px;">
					<%
						for (StoreBean storeBean : storeBeanList) {

							out.println("<option value='" + storeBean.getStoreCode() + "'>");
							out.println(storeBean.getStoreName() + "</option>");
						}
					%>

				</select> →送り先店舗 <select class="select2"  name="sendingAddress" style="width: 200px;">
					<%
						for (StoreBean storeBean : storeBeanList) {

							out.println("<option value='" + storeBean.getStoreCode() + "'>");
							out.println(storeBean.getStoreName() + "</option>");
						}
					%>

				</select>

				<input type="button" onclick="itemSelect()" value="確定">
			</div>

			<!-- 入力数がゼロにならないように一つは消さない -->

			<div id="itemInputArea">

				<div id="firstUl">
					<ul id="formUl">

						<li class="formLi"><input type="text" name="itemCode" placeholder='商品コード'> <input type="number" name="itemCount" placeholder='出荷数' min="1" step="1"></li>

					</ul>
				</div>

				<div class="underArea">

					<input type="button" value="入力商品追加" onclick="addForm()">

					<input type="submit" value="確認" onclick="return confi()">

				</div>
			</div>

		</form>

		<a href="index.jsp">トップページ</a>
		<script src="inventorymanagement.js"></script>

	</div>
</body>
</html>