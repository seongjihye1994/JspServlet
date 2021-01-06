<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.Date"%>
    
<%
	Date now = new Date();
	// 포멧팅하기
	String today = String.format("%tY년 %tm월 %td일 %tA", now, now, now, now);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<%= today %>
	<!-- 2021년 01월 06일 수요일 -->

</body>
</html>