<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.Date, java.text.SimpleDateFormat"%>
<%
	Date date = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String today = sdf.format(date);
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
	.input_area input {
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
			<h1>공지사항 작성</h1>
			<form action="<%= request.getContextPath() %>/notice/insert" method="post">
				<h4 class="notice_title">제목</h4>
				<span class="input_area">
					<input type="text" name="title" maxlength="30" required>
				</span>
				
				<h4 class="notice_title">작성자</h4>
				<span class="input_area">
					<input type="text" name="writer" value="<%= loginUser.getUserId() %>" readonly>
				</span>
				
				<h4 class="notice_title">작성일</h4>
				<span class="input_area">
					<input type="date" name="date" value="<%= today %>" readonly>
				</span>
				
				<h4 class="notice_title">내용</h4>
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