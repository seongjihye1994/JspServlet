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
	
	/* 공지사항 목록 영역 */
	.tableArea{
		padding : 20px;
		width:500px;
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
	.input_area select  {
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
		<div class="tableArea">
			<h1>게시글 작성</h1>
			<form action="<%= request.getContextPath() %>/board/insert" method="post">
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
				<h4 class="board_title">내용</h4>		
				<textarea name="content" style="resize:none;"></textarea>
				<div class="btnArea">
					<button type="button" onclick="javascript:history.back();">취소</button>
					<button type="submit">등록</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>