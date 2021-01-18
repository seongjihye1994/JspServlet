<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="member.model.vo.Member"%>
<%
	// session 객체에 담긴 loginUser 정보를 변수에 담아두기
	Member loginUser = (Member)session.getAttribute("loginUser");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	/* header 설정 */
	#header {
		height : 200px;
		background : url('<%= request.getContextPath() %>/resources/images/top-header.jpg');
		color : white;
	}
	
	h1 {
		text-align : center;
		padding : 10px;
	}
	
	/* loginArea or userInfo 영역*/
	#loginForm,
	#userInfo {
		float : right;
	}
	
	#userInfo {
		margin-top : 25px;
		margin-right : 10px;
	}
	
	#loginArea table {
		text-align : center;
		padding : 10px;
	}
	
	/* 버튼 관련 스타일 */
	button:hover {
		cursor : pointer;
	}
	
	button {
		width:110px;
		height:25px;
		color:white;
		background:lightblue;
		border:lightblue;
		border-radius:5px;
		margin-top : 5px;
	}
	
	/* 네비게이션 바 */
	.wrap {
		background : black;
		width:100%;
		height:50px;
	}
	
	.nav {
		width : 700px;
		margin : auto;
	}
	
	.menu {
		color:white;
		text-align:center;
		width:150px;
		display:inline-block;
		line-height:50px;
		vertical-align:middle;
	}
	
	.menu:hover {
		cursor:pointer;
		background:lightgray;
		font-weight:bold;
	}
	
</style>
<script src="https://code.jquery.com/jquery-3.5.1.min.js" 
integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
<% if(session.getAttribute("msg") != null) { %>
<script>
	alert('<%= session.getAttribute("msg") %>');
</script>
<%
	session.removeAttribute("msg");
	}
%>
</head>
<body>
	<div id="header">
		<h1>Welcome to JSP World</h1>
		
		<div class="loginArea">
		<%-- 2_1. 로그인이 된 상태와 되지 않은 상태를 구분하기 위해 if문으로 조건식을 추가 --%>
		<% if(loginUser == null){ %>
			<!-- 1_1. 로그인 폼 만들기 -->
			<form id="loginForm" action="<%= request.getContextPath() %>/member/login"
			method="post" onsubmit="return validate();">
				<table>
					<tr>
						<td><label for="userId">ID</label></td>
						<td><input type="text" name="userId" id="userId"
							placeholder="아이디를 입력하세요"></td>
					</tr>
					<tr>
						<td><label for="userPwd">Password</label></td>
						<td><input type="password" name="userPwd" id="userPwd"
							placeholder="비밀번호를 입력하세요"></td>
					</tr>
					<tr>
						<td colspan="2">
							<button id="memberJoinBtn" type="button">회원가입</button>
							<button id="loginBtn" type="submit">로그인</button>
						</td>
					</tr>
				</table>
			</form>
			
			<script>
				// 1_2. validate() 함수 작성 -> 로그인 폼 유효성 검사
				function validate(){
					// 아이디, 패스워드 중 입력 되지 않은 값이 있을 시 alert 후 focus 처리 -> submit X
					if($("#userId").val().trim().length == 0){
						alert('아이디를 입력하세요');
						$("#userId").focus();
						return false;
					}
					
					if($("#userPwd").val().trim().length == 0){
						alert('비밀번호를 입력하세요');
						$("#userPwd").focus();
						return false;
					}
					
					return true;
				}
				
				// 3. 회원가입 버튼 클릭 이벤트 작성
				const memberJoinBtn = document.getElementById('memberJoinBtn');
				memberJoinBtn.addEventListener('click', function(){
						location.href="<%= request.getContextPath() %>/views/member/memberJoinForm.jsp";
				});
				
				
				
			</script>
			<%-- 2_2. 로그인이 성공적으로 되었을 경우 --%>
			<% } else { %>
			<div id="userInfo">
				<table>
					<tr>
						<td><%= loginUser.getUserName() %>님의 방문을 환영합니다.</td>
					</tr>
					<tr>
						<td>
							<button id="myPageBtn">정보수정</button>
							<button id="logoutBtn">로그아웃</button>
						</td>
					</tr>
				</table>
			</div>
			
			<script>
				// 2_3. logout 이벤트 작성
				const logoutBtn = document.getElementById('logoutBtn');
				logoutBtn.addEventListener('click', function(){
					location.href='<%= request.getContextPath() %>/member/logout';
				});
				
				// 4. 정보 수정 버튼 클릭 이벤트
				const myPageBtn = document.getElementById('myPageBtn');
				myPageBtn.addEventListener('click', function(){
					location.href='<%= request.getContextPath() %>/views/member/myPage.jsp';
				});
				
				
			</script>
			
			<% } %>
		</div>
	</div>

	<div class="wrap">
		<div class="nav">
			<div class="menu" id="homeBtn">HOME</div>
			<div class="menu" id="noticeBtn">공지사항</div>
			<div class="menu" id="boardBtn">게시판</div>
			<div class="menu" id="galleryBtn">사진게시판</div>
		</div>
	</div>
	
	<script>
		// Home 버튼
		const homeBtn = document.getElementById('homeBtn');
		homeBtn.addEventListener('click', function(){
			location.href='<%= request.getContextPath() %>';
		});
		
		// 공지사항 버튼
		const noticeBtn = document.getElementById('noticeBtn');
		noticeBtn.addEventListener('click', function(){
			location.href='<%= request.getContextPath() %>/notice/list';
		});
		
		// 게시판 버튼
		const boardBtn = document.getElementById('boardBtn');
		boardBtn.addEventListener('click', function(){
			location.href='<%= request.getContextPath() %>/board/list';
		});
	</script>
	
	
	
	
	
	
	
	
</body>
</html>