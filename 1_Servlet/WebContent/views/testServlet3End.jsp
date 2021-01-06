<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- 이 페이지는 java, contentType의 문서형식은 html, 문자셋은 utf-8 -->
<% 
	// 스크립틀릿이라고 하며 이 안에 java 코드를 작성할 수 있음
	// TestServlet3.java에서 request 객체에 setAttribute를 통해 값들을 담아줌
	// 담은 값을 꺼낼 때는 getAttribute를 사용해 꺼냄
	
	// (String) 타입캐스팅을 해주지 않으면 타입미스매치가 남
	// setAttribute로 담을 때 object 타입으로 담겨지기 때문에 가져올 때 변수에
	// 담기 위해서는 알맞은 타입으로 캐스팅 해 주어야 함
	String name = (String) request.getAttribute("name"); // key값을 넣어주면 됨
	String age = (String) request.getAttribute("age");
	String city = (String) request.getAttribute("city");
	String height = (String) request.getAttribute("height");
	String gender = (String) request.getAttribute("gender");
	String food = (String) request.getAttribute("foods");
%>
<!-- 
	getParameter
	: 클라이언트의 HTML 페이지에서 필요한 정보를 얻는데 사용
	    웹 브라우저에서 전송 받은 request 영역에서 name값이 같은 것을 찾아 읽어옴
	    항상 String 타입으로 반환
	
	getAttribute
	: 이전에 다른 JSP 또는 Servlet 페이지에서 설정 된(setAttribute) 매개변수를 가져오는데 사용
	  request.setAttribute()에서 설정해준 값을 가져오며, 설정이 없으면 무조건 null
	  Object 타입으로 반환하므로 필요 시 캐스팅 해서 사용

 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	h2 {color : red;}
	span.name {color : orange; font-weight : bold;}
	span.age {color : yellow; font-weight : bold;}
	span.city {color : green; font-weight : bold;}
	span.height {color : blue; font-weight : bold;}
	span.gender {color : navy; font-weight : bold;}
	span.food {color : purple; font-weight : bold;}
</style>
</head>
<body>
	<h2>개인정보결과 (JSP)</h2>
	<span class='name'><%= name %></span> 님은
	<span class='age'><%= age %></span>이시며,
	<span class='city'><%= city %></span>에 사는
	키 <span class='height'><%= height %></span>cm인
	<span class='gender'><%= gender %></span>입니다.
	좋아하는 음식은 <span class='food'><%= food %></span>입니다.
</body>
</html>