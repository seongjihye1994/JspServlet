<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*" errorPage="error.jsp"%>
<%-- 여러 클래스 import 시 , 를 통해 나열 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<%
		ArrayList<String> list = new ArrayList<>();
		// 지시어 page 속성 중 import를 통해 선언
		
		list.add("HTML");
		list.add("Servlet");
		list.add("JSP");
		
		// 일부러 오류 발생시켜보기
		// java.lang.IndexOutOfBoundsException: Index: 10, Size: 3
		list.add(10, "오류날껄");
		// HTTP 상태 500 – 내부 서버 오류 -> 자바 코드 상의 에러
		// HTTP 상태 404 에러 : 경로상의 에러
		
		// 에러 페이지 컨트롤 방법
		// 1. page 지시자 errorPage를 이용한 방법 -> error.jsp 만들기
		// 2. web.xml에 error-page 태그를 등록하는 방법 -> 404error.jsp 만들기
		
		Date today = new Date();
	%>
	
	<p>리스트 길이 : <%= list.size() %> </p>
	<p>오늘 날짜 : <%= today %> </p>

</body>
</html>