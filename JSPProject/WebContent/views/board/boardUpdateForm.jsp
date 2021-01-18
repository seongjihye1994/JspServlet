<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="board.model.vo.Board, java.util.*"%>
<%
	// 수정하기 전 원래 값을 받아온거임! (실제로 이 페이지에서는 수정할 수 있음)
	Board board = (Board)request.getAttribute("board");

	String category = board.getCategory();
	
	// 수정하기 전 원래 값이 어떤 것인지를 알아보기 위해
	int cate = 0;
	switch(category){
	case "공통": cate = 10; break; // 원래 수정 전 값이 공통이였다면
	case "운동": cate = 20; break;
	case "등산": cate = 30; break;
	case "게임": cate = 40; break;
	case "낚시": cate = 50; break;
	case "요리": cate = 60; break;
	case "기타": cate = 70; break;
	}
	
	// 아래 체크박스에 0번인덱스부터 값 넣음
	String[] selected = new String[7];
	selected[(cate/10) - 1] = "selected";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
			<h1>게시글 수정</h1>
			<form action="<%= request.getContextPath() %>/board/update" method="post">
				<input type="hidden" name="bId" value="<%= board.getbId() %>"> <!-- 어떤 게시글을 수정하는지에 대한 pk값 hidden으로 넘김 -->
				<h4 class="board_title">카테고리</h4>
				<span class="input_area">
					<select name="category">
						<option>----</option>
						<option value="10" <%= selected[0] %>>공통</option> <!-- 원래 수정 전 값이 '공통이였다면' '공통'이 선택되어 있게 설정 -->
						<option value="20" <%= selected[1] %>>운동</option>
						<option value="30" <%= selected[2] %>>등산</option>
						<option value="40" <%= selected[3] %>>게임</option>
						<option value="50" <%= selected[4] %>>낚시</option>
						<option value="60" <%= selected[5] %>>요리</option>
						<option value="70" <%= selected[6] %>>기타</option>
					</select>
				 </span>
				 <h4 class="board_title">제목</h4>
				<span class="input_area"><input type="text" name="title" maxlength="30" value="<%= board.getbTitle() %>" required></span>
				<h4 class="board_title">내용</h4>													<!-- 원래 내용을 출력 -->
				<textarea name="content" style="resize:none;"><%= board.getbContent() %></textarea>
																<!-- 원래 내용을 출력 -->
				<div class="btnArea">
					<button type="submit">수정</button> <!-- 원래 내용을 지우고 다시 수정버튼을 누르면 /board/update 서블릿으로 post 요청 -->
					<button type="button" onclick="location.href='<%= request.getContextPath() %>/board/detail?bId='+<%= board.getbId() %>">취소</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>