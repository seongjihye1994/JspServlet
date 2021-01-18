<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="board.model.vo.Board"%>
<%
	Board b = (Board)request.getAttribute("board");
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
      min-height:450px;
      padding : 20px;
   }
   
   /* 공지사항 테이블 */
   #listTable{
      border:5px solid white;
       text-align:center;
      margin:auto; 
   }
   
   
   #listTable #content {
      text-align:left;
   }
   
   #listTable td:nth-child(1) {
      width : 100px;
   }
   
   #listTable td:nth-child(2) {
      width : 500px;
   }
   
   #listTable td:nth-child(3) {
      width : 100px;
   }
   
   #listTable td:nth-child(4) {
      width : 100px;
   }
   
   #listTable td:nth-child(5) {
      width : 100px;
   }
   
   #listTable td:nth-child(6) {
      width : 150px;
   }
   
   /* textarea */ 
   textarea {
      width : 600px;
      height : 60px;
      padding : 10px 10px 14px 10px;
      border: solid 1px #dadada;
      resize:none;
   }
   
   /* 버튼 영역 */
   .btnArea {
      text-align:center;
      padding : 50px;
   }
   
   /* 댓글 영역 */
   .replyArea {
      text-align:center;
      padding : 50px;
   }
   
   .replySelectArea {
      padding : 50px;
      width : 600px;
   }
</style>
</head>
<body>
	<%@ include file="../common/menubar.jsp" %>
	
	<div class="outer">
		<div class="tableArea">
			<h1>게시글 상세 페이지</h1>
			<table id="listTable">
				<tr>
					<td>카테고리</td>
					<td><%= b.getCategory() %></td>
					<td>조회수</td>
					<td><%= b.getbCount() %></td>
					<td>작성일</td>
					<td><%= b.getCreateDate() %></td>
				</tr>
				<tr>
					<td>제목</td>
					<td colspan="3"><%= b.getbTitle() %></td>
					<td>작성자</td>
					<td><%= b.getbWriter() %></td>	
				</tr>
				<tr>
					<td colspan="6">
						<!-- 글 작성시 enter를 누르면 <br> : 개행 처리 되도록 -->
						<p id="content"><%= (b.getbContent()).replace("\n", "<br>") %></p>
					</td>
				</tr>
			</table>
		</div>
		
		<div class="btnArea">
			<button id="listBtn">목록으로</button>
			
			<%-- 로그인 유저가 글 작성자인 경우에만 수정 삭제 버튼 보이기 --%>
			<% if (loginUser.getUserNo() == b.getUserNo()) { %> <!-- 현재 로그인 된 유저가 글을 작성한 유저인가? -->
			<button id="updateBtn">수정</button>
			<button id="deleteBtn">삭제</button>
			
			<!-- 수정 /삭제를 위한 form -->
			<form action="" id="bIdForm" method="post">
				<input type="hidden" name="bId" value="<%= b.getbId() %>"> <!-- 값을 hidden으로 숨겨서 서블릿으로 넘김 -->
			</form>
			
			<script>
				// 수정하기 버튼 이벤트
				const updateBtn = document.getElementById('updateBtn');
				updateBtn.addEventListener('click', function() {
					$("#bIdForm").attr("action", "<%= request.getContextPath() %>/board/updateForm"); // 서블릿으로 요청보내기
					$("#bIdForm").submit(); // 폼 제출
					
				});
				
				// 삭제하기 버튼 이벤트
				const deleteBtn = document.getElementById('deleteBtn');
				deleteBtn.addEventListener('click', function() {
					$("#bIdForm").attr("action", "<%= request.getContextPath() %>/board/delete"); // 서블릿으로 요청보내기
					$("#bIdForm").submit(); // 폼 제출
					
				});
			
			</script>
			<% } %>
		</div>
		
		<!-- AJAX 수업 후 완성할 댓글 -->
		
		<!-- 게시글 작성 -->
		<div class="replyArea">
			<div class="replyWriterArea">
				<h4 class="reply_title">댓글 작성</h4>
				<textarea id="replyContent"></textarea>
				<br>
				<button id="addReply">댓글등록</button>
			</div>
			
			<!-- 작성된 게시글 나열 -->
			<div class="replySelectArea">
				<table id="replySelectTable">
					<tr>
						<td colspan="3">작성 된 댓글이 없습니다.</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	

</body>
</html>