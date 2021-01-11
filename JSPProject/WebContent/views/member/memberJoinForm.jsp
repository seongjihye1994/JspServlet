<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
      margin-bottom : 70px;
   }
   
   /* form 태그 */
   #joinForm {
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
	<!-- 페이지를 이동해도 memubar는 계속 상단에 노출되게끔 -->
	<%@ include file="../common/menubar.jsp" %>
	
	<!-- 1. 회원 가입 -->
	<!-- 1_1. 회원 가입 폼 작성 -->
	<div class='outer'>
		<div id='joinInfoArea'>
			<form id='joinForm' action="<%= request.getContextPath() %>/member/insert"
			method="post" onsubmit="return joinValidate();">
				
				<h1>회원 가입</h1>
				<h4 class='join_title'>* 아이디</h4>
				<span class='input_area'><input type='text' maxlength='13' name='userId' required></span>
				<!-- AJAX(비동기통신)을 통해 추후 구현할 예정 -->
				<button id='idCheck' type='button'>중복확인</button>
				
				<h4 class='join_title'>* 비밀번호</h4>
				<span class='input_area'><input type='password' maxlength='15' name='userPwd' required></span>
				
				<h4 class='join_title'>* 비밀번호 확인</h4>
				<span class='input_area'><input type='password' maxlength='15' name='userPwd2' required></span>
				<label id="pwdResult"></label>
				
				<h4 class='join_title'>* 이름</h4>
				<span class='input_area'><input type='text' name="userName" required></span>
				
				<h4 class='join_title'>연락처</h4>
				<span class='input_area'><input type='tel' maxlength='11' name='phone'
											placeholder='(-없이)01012345678'></span>
				
				<h4 class='join_title'>이메일</h4>
				<span class='input_area'><input type='email' name='email'></span>
				
				<h4 class='join_title'>우편번호</h4>
				<span class='input_area'><input type='text' name='address' class="postcodify_postcode5" readonly></span>
				<button type='button' id="postcodify_search_button">검색</button>
				<h4 class='join_title'>도로명주소</h4>
				<span class='input_area'><input type='text' name='address' class="postcodify_address" readonly></span>
				<h4 class='join_title'>상세주소</h4>
				<span class='input_area'><input type='text' name='address' class="postcodify_details"></span>
				
				<h4 class='join_title'>관심분야</h4>
				<span class='input_area'>
					<input type='checkbox' id='sports' name='interest' value='운동'>
					<label for='sports'>운동</label>
					<input type='checkbox' id='climbing' name='interest' value='등산'>
					<label for='climbing'>등산</label>
					<input type='checkbox' id='fishing' name='interest' value='낚시'>
					<label for='fishing'>낚시</label>
					<input type='checkbox' id='cooking' name='interest' value='요리'>
					<label for='cooking'>요리</label>
					<input type='checkbox' id='game' name='interest' value='게임'>
					<label for='game'>게임</label>
					<input type='checkbox' id='etc' name='interest' value='기타'>
					<label for='etc'>기타</label>
				</span>
				<div class='btnArea'>
					<button id='goMainBtn' type='button'>메인으로</button>				
					<button id='joinBtn'>가입하기</button>				
				</div>
			</form>	
		</div>
	</div>
	<!-- jQuery가 포함 된 상태에서 postcodify 스크립트 포함 -->
	<script src="//d1p7wdleee1q2z.cloudfront.net/post/search.min.js"></script>
	<!-- 검색 버튼 클릭 시 팝업 레이어 열리도록  -->
	<script> $(function() { $("#postcodify_search_button").postcodifyPopUp(); }); </script>
	<script>
		// 1. 메인으로 돌아가기
		const goMainBtn = document.getElementById('goMainBtn');
		goMainBtn.addEventListener('click', function() {
			location.href='<%= request.getContextPath() %>';
		});
		
		// 2. 유효성 검사
		function joinValidate() {
			// 아이디 - 영소문자로 시작해서 4 ~ 12자 입력(숫자 포함 가능)
			if (!(/^[a-z][a-z\d]{3,11}$/.test($("#joinForm input[name=userId]").val()))) {
				alert('아이디는 영소문자로 시작해서 4~12자 입력 (숫자 포함 가능)');
				$("#joinForm input[name=userId]").select();
				return false;
			}
			// 비밀번호 - 비밀번호 확인 일치 여부
			if ($('#joinForm input[name=userPwd]').val() != $('#joinForm input[name=userPwd2]').val()) {
				$('#pwdResult').text('비밀번호 불일치').css("color", "red");
			}
			
			// 이름 - 한글 값 2글자 이상 입력
			if (!(/^[가-힣]{2,}$/.test($("#joinForm input[name=userName]").val()))) {
				alert('이름은 한글로 2글자 이상 입력');
				$("#joinForm input[name=userName]").select();
				return false;
			}
		}
	</script>


</body>
</html>