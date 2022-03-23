<%@page import="bean.ItemInfoBean"%>
<%@page import="java.util.List"%>
<%@page import="bean.StoreBean"%>
<%@page import="bean.InventoryBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%
	List<StoreBean> storeBeanList = (List<StoreBean>) request.getAttribute("storeBeanList");

	ItemInfoBean itemInfoBean = (ItemInfoBean) request.getAttribute("itemInfoBean");
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

		<h3>在庫表示店舗選択</h3>

		<div class="area">

			価格

			<ul class="filterUl">
				<li><label><input type="checkbox" name="filterPrice" value="under3000">1円~3,000円</label>
				<li><label><input type="checkbox" name="filterPrice" value="under5000">3,000円~5,000円</label>
				<li><label><input type="checkbox" name="filterPrice" value="under10000">5,000円~10,000円</label>
			</ul>

			サイズ

			<ul class="filterUl">

				<%
					for (String size : itemInfoBean.getSizes()) {
						out.println(
								"<li><label><input type='checkbox' name='filterSize' value=" + size + ">" + size + "</label>");
					}
				%>

			</ul>


			色

			<ul class="filterUl">

				<%
					for (String color : itemInfoBean.getColors()) {
						out.println("<li><label><input type='checkbox' name='filterColor' value=" + color + ">" + color
								+ "</label>");
					}
				%>

			</ul>

			カテゴリー

			<ul class="filterUl">

				<%
					for (String category : itemInfoBean.getCategorys()) {
						out.println("<li><label><input type='checkbox' name='filterCategory' value=" + category + ">" + category
								+ "</label>");
					}
				%>

			</ul>

			性別

			<ul class="filterUl">

				<%
					for (String sex : itemInfoBean.getSexs()) {
						out.println(
								"<li><label><input type='checkbox' name='filterSex' value=" + sex + ">" + sex + "</label>");
					}
				%>

			</ul>

		</div>

		<div class="area">

			店舗コード <select class="select2" id="storeCode" style="width: 200px;">
				<%
					for (StoreBean storeBean : storeBeanList) {

						out.println("<option value='" + storeBean.getStoreCode() + "'>");
						out.println(storeBean.getStoreName() + "</option>");
					}
				%>

			</select>

			<input type="button" value="在庫一覧表示" onclick="ajaxGetInventory()">


			<table id="inventoryListTable">

				<tr>
					<th id="inventoryListItemName">商品名</th>
					<th>価格</th>
					<th>サイズ</th>
					<th>色</th>
					<th>カテゴリー</th>
					<th>sex</th>
					<th>消費期限</th>
					<th>在庫数</th>
					<th>未確定在庫</th>
				</tr>

				<tbody id="inventoryInsertArea"></tbody>
			</table>

			<p>
				<a href="index.jsp">トップページ</a>
			</p>

		</div>

		<div style="clear: both;"></div>



	</div>




</body>
</html>