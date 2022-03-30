<%@page import="bean.StoreBean"%>
<%@page import="org.apache.catalina.session.StoreBase"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<%
	List<StoreBean> storeBeanList = (List<StoreBean>) request.getAttribute("storeBeanList");
%>

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

<title>出荷伝票キャンセル</title>

</head>
<body>

	<div class="centerMain">

		<h3>出荷伝票取り消し</h3>

		店舗コード <select class="select2" id="storeCode" style="width: 200px;">
			<%
				for (StoreBean storeBean : storeBeanList) {

					out.println("<option value='" + storeBean.getStoreCode() + "'>");
					out.println(storeBean.getStoreName() + "</option>");
				}
			%>

		</select>
		<input type="button" value="出荷伝票一覧" onclick="ajaxGetSlip()">

		<form action="SlipDeleteServlet" method="post">

			<table id="slipListArea">
				<tr>
					<th>伝票コード</th>
					<th>発送日</th>
					<th>発送先店舗</th>
				</tr>

				<tbody id="slipInsertArea"></tbody>


			</table>


			<input type="submit" value="取り消し">
		</form>

		<a href="index.jsp">トップページ</a>

	</div>

</body>
</html>