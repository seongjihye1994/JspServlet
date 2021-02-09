<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- galleryListView에서 '작성하기' 버튼 클릭시 해당 페이지로 넘어옴 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>
   /* 바깥 영역 */
   .outer{
      width:60%;
      min-width : 700px;
      background: rgb(248, 249, 250);
      box-shadow: rgba(0, 0, 0, 0.06) 0px 0px 4px 0px;
      margin:auto;
      margin-top : 70px;
      margin-bottom : 70px;
   }
   
   /* 입력 영역 */
   .insertArea{
      padding:20px;
      width:600px;
      margin:auto;
   }
   
   /* span 태그 */
   .input_area {
       border: solid 1px #dadada;
       padding : 10px 10px 14px 10px;
       background : white;
   }
   
   /* input 태그 */
   .input_area input,
   .input_area select {
      width : 550px;
      height : 30px;
      border: 0px;
   }
   
   /* textarea */ 
   textarea {
      width : 550px;
      height : 300px;
      padding : 10px 10px 14px 10px;
      border: solid 1px #dadada;
      resize:none;
   }
   
   [type=file] {
      display : block;
   }
   
   #thumbnailImgArea,
   #contentImgArea1, 
   #contentImgArea2 {
      display :inline-block;
      margin:15px;
   }
   
   /* 버튼 영역 */
   .btnArea {
      text-align:center;
      padding : 50px;
   }
   
</style>

</head>
<body>
	<%@ include file="../common/menubar.jsp" %>
	<div class="outer">
	<!-- 파일 업로드를 위해서는 반드시 enctype="multipart/form-data 을 지정해주어야 함 -->
		<form action="<%= request.getContextPath() %>/gallery/insert" method="post" enctype="multipart/form-data">
			<div class="insertArea">
				<h1>사진 게시글 작성</h1>
				
				<h4 class="board_title">카테고리</h4>
					<span class="input_area">
						<select name="category">
							<option>----</option>
							<option value="10">공통</option>
							<option value="20">운동</option>
							<option value="30">등산</option>
							<option value="40">게임</option>
							<option value="50">낚시</option>
							<option value="60">요리</option>
							<option value="70">기타</option>					
						</select>				
					</span>
				<h4 class="board_title">제목</h4>
				<span class="input_area"><input type="text" name="title" maxlength="30" required></span>
				
				<h4 class="board+title">사진 메모</h4>
				<textarea name="content"></textarea>
				
				<h4 class="board_title">대표 이미지 첨부</h4>
				<input type="file" name="thumbnailImg" required>
				
				<div id="thumbnailImgArea">
					<img id="thumbnail">
				</div>
				
				<h4 class="board_title">내용 이미지 첨부 (최대 2개)</h4>
				<input type="file" name="contentImg1">
				<input type="file" name="contentImg2">
				
				<div id="contentImgArea1">
					<img id="content1">
				</div>
				<div id="contentImgArea2">
					<img id="content2">
				</div>		
			</div>
			
			<script>
				$(function () {
					// input type="file" 태그에 파일이 첨부될 때 동작하는 이벤트
					$("[type=file]").change(function() { 
						loadImg(this); // 이 메소드 실행
					});
				});
				
				function loadImg(element) { 
					// 첨부한 파일(element)을 판별해서 알맞은 위치에 preview 표현하기
					// console.log(element.name);
					
					// input type="file" 엘리먼트에 첨부 파일 속성, 첨부파일이 잘 존재하는지 확인
					if (element.files && element.files[0]) {
						// 파일을 읽어들일 FileReader 객체 생성
						var reader = new FileReader();
						
						// 파일 읽기가 다 완료 되었을 때 실행 되는 메소드
						reader.onload = function(e) {
							
							var selector;
							var size;
							
							// 셀렉터 선택
							switch(element.name) {
							case "thumbnailImg" :
								selector = "#thumbnail";
								size = {width:"550px", height:"300px", border:"solid 1px #dadada"};
								break;
							case "contentImg1" :
								selector = "#content1";
								size = {width:"250px", height:"150px", border:"solid 1px #dadada"};
								break;
							case "contentImg2" :
								selector = "#content2";
								size = {width:"250px", height:"150px", border:"solid 1px #dadada"};
								break;
							}
							
							$(selector).attr("src", e.target.result).css(size); // e.target.result : 이미지 경로, size : 미리 지정한 이미지 사이즈
							
							console.log(e);
							console.log(e.target);
							console.log(e.target.result);
						}
						
						// 파일 읽기 하는 메소드
						reader.readAsDataURL(element.files[0]); // element.files[0] : 내가 첨부한 파일
					}
				}
			
			</script>
			
			<div class="btnArea">
				<button type="button" onclick="javascript:history.back();">취소하기</button>&nbsp;
				<button type="submit">작성완료</button>
			</div>
		</form>	
	</div>

</body>
</html>