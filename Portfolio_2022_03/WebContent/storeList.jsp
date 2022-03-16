<%@page import="java.util.List"%>
<%@page import="bean.StoreBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%
	List<StoreBean> storeBeanList = (List<StoreBean>) request.getAttribute("storeBeanList");
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="style.css">
<title>Insert title here</title>
</head>
<body>

	  <main>
        <h3>在庫表示店舗選択</h3>

        <ul>

           <%

           for(StoreBean storeBean : storeBeanList){
        	   out.print("<li>");
        	   out.print("<a href='InventoryListServlet?storeCode=" + storeBean.getStoreCode() + "'>");
        	   out.print(storeBean.getStoreName() + "</a></li>");

           }

           %>

        </ul>
    </main>

</body>
</html>