<%@page import="bean.ItemInfoBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Set"%>
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
<!-- list.js -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/list.js/1.5.0/list.min.js"></script>
<!-- 自分のjs -->
<script src="inventorymanagement.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Insert title here</title>

</head>

<body>

	<div class="centerMain">

		<h3>在庫表示店舗選択</h3>


		店舗名 <select class="select2" id="storeCode" style="width: 200px;">
			<%
				for (StoreBean storeBean : storeBeanList) {

					out.println("<option value='" + storeBean.getStoreCode() + "'>");
					out.println(storeBean.getStoreName() + "</option>");
				}
			%>

		</select>

		<input type="button" value="在庫一覧表示" onclick="ajaxGetInventory()">

		<div id="users">
			<div class="searchArea">
				テーブル検索
				<input class="search" placeholder="Search" />
			</div>

			<div class="contentArea">

				<div class="contentLeft">

					<h5 class="pointer" id="filterItemName" onclick="areaBlock(this)">商品名</h5>
					<div class="filter filterItemName">
						<ul class="contentLeftUl">

							<%
								for (String itemName : itemInfoBean.getItemNames()) {
									out.print("<li>");
									out.print("<label><input type='checkbox' name='itemName' value='" + itemName + "'>");
									out.print(itemName + "</label>");
									out.print("</li>");
								}
							%>
						</ul>
					</div>

					<h5 class="pointer" id="filterSize" onclick="areaBlock(this)">サイズ</h5>
					<div class="filter filterSize">
						<ul class="contentLeftUl">

							<%
								for (String size : itemInfoBean.getSizes()) {
									out.print("<li>");
									out.print("<label><input type='checkbox' name='size' value='" + size + "'>");
									out.print(size + "</label>");
									out.print("</li>");
								}
							%>
						</ul>
					</div>

					<h5 class="pointer" id="filterColor" onclick="areaBlock(this)">色</h5>
					<div class="filter filterColor">
						<ul class="contentLeftUl">

							<%
								for (String color : itemInfoBean.getColors()) {
									out.print("<li>");
									out.print("<label><input type='checkbox' name='color' value='" + color + "'>");
									out.print(color + "</label>");
									out.print("</li>");
								}
							%>
						</ul>
					</div>

					<h5 class="pointer" id="filterCategory" onclick="areaBlock(this)">カテゴリー</h5>
					<div class="filter filterCategory">
						<ul class="contentLeftUl">

							<%
								for (String category : itemInfoBean.getCategorys()) {
									out.print("<li>");
									out.print("<label><input type='checkbox' name='category' value='" + category + "'>");
									out.print(category + "</label>");
									out.print("</li>");
								}
							%>
						</ul>
					</div>

					<h5 class="pointer" id="filterSex" onclick="areaBlock(this)">性別</h5>
					<div class="filter filterSex">
						<ul class="contentLeftUl">

							<%
								for (String sex : itemInfoBean.getSexs()) {
									out.print("<li>");
									out.print("<label><input type='checkbox' name='sex' value='" + sex + "'>");
									out.print(sex + "</label>");
									out.print("</li>");
								}
							%>
						</ul>
					</div>

					<input type="button" value="絞り込み" onclick="filter()">
				</div>

				<div class="contentRight">
					<table id="inventoryListTable">
						<thead>
							<tr>
								<th id="inventoryListItemName" data-sort="itemName">商品名</th>
								<th class="pointer sort" data-sort="price">価格(円)</th>
								<th data-sort="size">サイズ</th>
								<th data-sort="color">色</th>
								<th data-sort="category">カテゴリー</th>
								<th data-sort="sex">sex</th>
								<th class="pointer sort" data-sort="date" id="bestBefore">消費期限</th>
								<th class="pointer sort" data-sort="inventoryCount">在庫数</th>
								<th class="pointer sort" data-sort="shipmentPending">未確定在庫</th>
							</tr>
						</thead>

						<tbody class="list" id="inventoryInsertArea"></tbody>

					</table>

					<p>
						<a href="index.jsp">トップページ</a>
					</p>

				</div>
			</div>
		</div>

	</div>





</body>
</html>