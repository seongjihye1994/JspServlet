<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Member m = (Member)session.getAttribute("loginUser");
	
	String userId = m.getUserId();
	String userPwd = m.getUserPwd();
	String userName = m.getUserName();
	// 필수 정보가 아닌 값들은 null로 리턴되므로 ""로 처리
	String phone = (m.getPhone() != null) ? m.getPhone() : "";
	String email = (m.getEmail() != null) ? m.getEmail() : "";
	// ,로 문자열 합치기 되어 있는 값들은 다시 , 기준으로 배열로
	String[] address = new String[3];
	if(m.getAddress() != null)
		address = m.getAddress().split(",");
	
	// 체크 된 체크박스에 해당하는 인덱스에 checked 문자열을 담기 
	String[] checkedInterest = new String[6];
	
	if(m.getInterest() != null) {
		// 회원이 가지고 있는 취미 값 배열에 담기
		String[] interests = m.getInterest().split(",");
		for (int i = 0; i < interests.length; i++){
			switch(interests[i]){
			case "운동" : checkedInterest[0] = "checked"; break;
			case "등산" : checkedInterest[1] = "checked"; break;
			case "낚시" : checkedInterest[2] = "checked"; break;
			case "요리" : checkedInterest[3] = "checked"; break;
			case "게임" : checkedInterest[4] = "checked"; break;
			case "기타" : checkedInterest[5] = "checked"; break;
			}
		}
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	/* 바깥 영역 */
	.outer{
		width:60%;
		min-width : 650px;
		background: rgb(248, 249, 250);
		box-shadow: rgba(0, 0, 0, 0.06) 0px 0px 4px 0px;
		margin:auto;
		margin-top : 70px;
		margizn-bottom : 70px;
	}
	
	/* form 태그 */
	#updateForm {
		width : 400px;
		margin: auto;
		padding: 10px;
	}
	
	/* span 태그 */
	.input_area {
	    border: solid 1px #dadada;
	    padding : 10px 10px 14px 10px;
	    background : white;
	}
	
	/* input 태그 */
	.input_area input:not([type=checkbox]) {
		width : 250px;
		height : 30px;
		border: 0px;
	}
	
	
	/* 버튼 영역 */
	.btnArea {
		text-align:center;
		padding : 50px;
	}
	
</style>
</head>
<body>
<!-- 페이지를 이동해도 menubar는 계속 상단에 노출되게끔 -->
	<%@ include file="../common/menubar.jsp" %>
	
	<!-- memberJoinForm.jsp 복사 후 변경 -->
	<div class="outer">
		<div id="joinInfoArea">
			<form id="updateForm" action="<%= request.getContextPath() %>/member/update"
			method="post"> <!-- 유효성 검사 필요 -->
				<h1>회원 정보 수정</h1>
				
				<h4 class="join_title">* 아이디</h4>
				<span class="input_area"><input type="text" maxlength="13" name="userId"
				value="<%= userId %>" readonly></span>
				
				<h4 class="join_title">* 비밀번호</h4>
				<span class="input_area"><input type="password" maxlength="15" name="userPwd" 
				value="<%= userPwd %>" readonly></span>
				<button id="pwdUpdateBtn" type="button">비밀번호 변경</button>
				<!-- 팝업창 띄워서 별도의 프로세스로 진행 -->
				
				<h4 class="join_title">* 이름</h4>
				<span class="input_area"><input type="text" maxlength="5" name="userName" 
				value="<%= userName %>" required></span>
				
				<h4 class="join_title">연락처</h4>
				<span class="input_area"><input type="tel" maxlength="11" name="phone"
						value="<%= phone %>" placeholder="(-없이)01012345678"></span>
										
				<h4 class="join_title">이메일</h4>
				<span class="input_area"><input type="email" name="email"
				value="<%= email %>"></span>
				
				<h4 class="join_title">우편번호</h4>
				<span class="input_area"><input type="text" name="address" 
				class="postcodify_postcode5" value="<%= address[0] %>" readonly></span>
				<button id="postcodify_search_button" type="button">검색</button>
				<h4 class="join_title">도로명주소</h4>
				<span class="input_area"><input type="text" name="address" 
				class="postcodify_address" value="<%= address[1] %>" readonly></span>
				<h4 class="join_title">상세주소</h4>
				<span class="input_area"><input type="text" name="address" 
				class="postcodify_details" value="<%= address[2] %>"></span>
				
				<h4 class="join_title">관심분야</h4>
				<span class="input_area">
					<input type="checkbox" id="sports" name="interest" value="운동" <%= checkedInterest[0] %>>
					<label for="sports">운동</label>
					<input type="checkbox" id="climbing" name="interest" value="등산" <%= checkedInterest[1] %>>
					<label for="climbing">등산</label>
					<input type="checkbox" id="fishing" name="interest" value="낚시" <%= checkedInterest[2] %>>
					<label for="fishing">낚시</label>
					<input type="checkbox" id="cooking" name="interest" value="요리" <%= checkedInterest[3] %>>
					<label for="cooking">요리</label>
					<input type="checkbox" id="game" name="interest" value="게임" <%= checkedInterest[4] %>>
					<label for="game">게임</label>
					<input type="checkbox" id="etc" name="interest" value="기타" <%= checkedInterest[5] %>>
					<label for="etc">기타</label>
				</span>
				<div class="btnArea">
					<button id="goMainBtn" type="button">메인으로</button>
					<button id="updateBtn">수정하기</button>
					<button id="deleteBtn" type="button">탈퇴하기</button>
				</div>
			</form>
		</div>
	</div>
	<!-- jQuery가 포함 된 상태에서 postcodify 스크립트 포함 -->
	<script src="//d1p7wdleee1q2z.cloudfront.net/post/search.min.js"></script>
	<!-- 검색 버튼 클릭 시 팝업 레이어 열리도록 -->
	<script> $(function() { $("#postcodify_search_button").postcodifyPopUp(); }); </script>
	
	<script>
		// 1. 메인으로 돌아가기
		const goMainBtn = document.getElementById('goMainBtn');
		goMainBtn.addEventListener('click', function(){
			location.href="<%= request.getContextPath() %>";
		});
		
		// 2. 비밀번호 변경 창 띄우기
		const pwdUpdateBtn = document.getElementById('pwdUpdateBtn');
		pwdUpdateBtn.addEventListener('click', function(){
			window.open("pwdUpdateForm.jsp", "비밀번호 변경 창", "width=500, height=300");
		});
		
		// 3. 탈퇴하기 버튼 클릭 이벤트
		const deleteBtn = document.getElementById('deleteBtn');
		deleteBtn.addEventListener('click', function(){
			if(confirm("정말로 탈퇴하시겠습니까?")){
				
				// 기존 form의 action 속성을 변경해서 submit
				// 기존 form에 정의 된 방식으로 submit 되며, form 태그 안에 있는 모든 정보 submit
				$("#updateForm").attr("action", "<%= request.getContextPath() %>/member/delete");
				$("#updateForm").submit();
			}
		});
	</script>
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

</body>
</html>