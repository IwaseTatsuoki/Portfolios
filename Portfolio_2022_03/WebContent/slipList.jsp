<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- jquery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<!-- Select2.css -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.5/css/select2.min.css">
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

	店舗コード
	<select class="select2" id="storeCode" style="width: 200px;">
		<option value=""></option>
		<option value="sw1">名古屋</option>
		<option value="sw2">大阪</option>
		<option value="sw3">東京</option>
		<option value="sw4">倉庫</option>
	</select>


	<input type="button" value="test" onclick="ajaxGetSlip()">

	<form action="" SlipDeleteServlet method="get">

		<table id="slipListArea">
			<tr>
				<th>伝票コード</th>
				<th>発送日</th>
				<th>発送先店舗</th>
			</tr>
		</table>


		<input type="submit" value="取り消し">
	</form>



</body>
</html>