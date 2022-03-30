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
			<table id="inventoryListTable">
				<thead>
					<tr>
						<th id="inventoryListItemName">商品名</th>
						<th class="sort" data-sort="price">価格(円)</th>
						<th>サイズ</th>
						<th>色</th>
						<th>カテゴリー</th>
						<th>sex</th>
						<th class="sort" data-sort="date" id="bestBefore">消費期限</th>
						<th class="sort" data-sort="inventoryCount">在庫数</th>
						<th class="sort" data-sort="shipmentPending">未確定在庫</th>
					</tr>
				</thead>

				<tbody class="list" id="inventoryInsertArea"></tbody>

			</table>
		</div>
		<p>
			<a href="index.jsp">トップページ</a>
		</p>

	</div>





</body>
</html>