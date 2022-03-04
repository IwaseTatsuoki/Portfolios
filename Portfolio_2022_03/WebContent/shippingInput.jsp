<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="style.css">
<title>Insert title here</title>
</head>
<body>

  <h3>出荷処理</h3>
    <form action="ShippingInputServlet" method="post">
        送り元コード<input type="text" name="sender"> →
        送り先コード<input type="text" name="sendingAddress"><br>

        <!-- 入力数がゼロにならないように一つは消さない -->
        <input type="text" name="itemCode_0" placeholder='商品コード'>

        <input type="number" name="shippingCount_0" placeholder='出荷数' min="1" step="1">

        <br>

        <!-- 下記のエリアにフォームが追加されていく -->
        <div id="form_area">

        </div>

        <input type="button" value="入力商品追加" onclick="addForm()">

        <input type="submit" value="確認" onclick="return confi()">

    </form>


    <a href="index.jsp">トップページ</a>


    <script src="inventorymanagement.js"></script>

</body>
</html>