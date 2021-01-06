<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

	String pizza = (String) request.getAttribute("pizza"); // 설정한 key값을 통해 가져옴
	String topping = (String) request.getAttribute("topping");
	int total = (int) request.getAttribute("total");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.pizza {color : red;}
	.topping {color : green;}
	.total {text-decoration : underline;}

</style>
</head>
<body>

	<h1>주문 내역</h1>
	<h2>
		피자는  <span class="pizza"><%= pizza %></span>,
		토핑은
		<% if(topping.equals("")) { %> <!-- 토핑 추가하지 않았다면 -->
			<span class="topping">없음</span>
		<% } else { %> <!-- 토핑을 추가했다면 -->
			<span class="topping"><%= topping %></span>
		<% } %>
		으로 주문하셨습니다.
	</h2>
	
	<br><br>
	<h2>총합 : <span class="total"><%= total %></span>원</h2>

</body>
</html>