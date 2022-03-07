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
    <main>

        <h3>出荷処理</h3>

        <form action="ShippingInputServlet" method="post">

            <div class="sendInputArea">
                送り元コード<input type="text" name="sender"> →
                送り先コード<input type="text" name="sendingAddress"><br>
            </div>

            <!-- 入力数がゼロにならないように一つは消さない -->


            <ul id="form_ul">

                <li class="form_li">
                    <input type="text" name="itemCode_0" placeholder='商品コード'>

                    <input type="number" name="shippingCount_0" placeholder='出荷数' min="1" step="1">
                </li>

            </ul>

            <div class="under_area">

                <input type="button" value="入力商品追加" onclick="addForm()">

                <input type="submit" value="確認" onclick="return confi()">

            </div>

        </form>

        <a href="index.jsp">トップページ</a>



        <script src="inventorymanagement.js"></script>
    </main>
</body>
</html>