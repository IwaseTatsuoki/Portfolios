<%@page import="bean.InventoryBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	List<InventoryBean> inventoryBeanList = (List<InventoryBean>) request.getAttribute("inventoryBeanList");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="style.css">
<title>在庫一覧</title>
</head>
<body>

	<main>

	<h3>在庫一覧</h3>

	<table class="inventoryListTable">

		<tr>
			<th>商品名</th>
			<th>価格</th>
			<th>サイズ</th>
			<th>色</th>
			<th>在庫数</th>
			<th>未確定在庫</th>
		</tr>

		<%
			for (InventoryBean inventoryBean : inventoryBeanList) {

				out.print("<tr>");

				out.print("<td>");
				out.print(inventoryBean.getItemName());
				out.print("</td>");

				out.print("<td>");
				out.print(inventoryBean.getPrice() + "円");
				out.print("</td>");

				out.print("<td>");
				out.print(inventoryBean.getSize());
				out.print("</td>");

				out.print("<td>");
				out.print(inventoryBean.getColor());
				out.print("</td>");

				out.print("<td>");
				out.print(inventoryBean.getInventoryCount());
				out.print("</td>");

				out.print("<td>");
				out.print(inventoryBean.getShipmentPending());
				out.print("</td>");

				out.print("</tr>");

			}
		%>


	</table>



	</main>

</body>
</html>