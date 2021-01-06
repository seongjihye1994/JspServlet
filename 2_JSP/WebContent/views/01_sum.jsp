<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- HTML 주석 -->
	<%-- JSP 주석 --%>
	
	<!-- HTML 주석은 클라이언트에게 전달됨 -->
	<%-- JSP 주석은 클라이언트에게 전달되지 않음 --%> 
	
	<%
	
		// 스크립틀릿 -> 자바 코드 작성
		int sum = 0;
		
		for (int i = 0; i <= 100; i++) {
			sum += i;
		}
		
		System.out.println("덧셈 끝!");
	%>
	
	<p>
		표현식을 통해 화면 출력 : <%= sum %> <br>
		스크립틀릿을 통해 화면에 출력 : <% out.println(sum); %>
	</p>
	
	

</body>
</html>