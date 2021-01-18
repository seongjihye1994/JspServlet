<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="notice.model.vo.Notice" %>
<%
	Notice n = (Notice)request.getAttribute("notice");
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
	
	<%@ include file = "../common/menubar.jsp" %>

	<div class="outer">
		<div class="tableArea">
			<h1>공지사항 상세 페이지</h1>
			
				<h4 class="notice_title">제목</h4>
				<span class="input_area">
					<input type="text" name="title" value="<%= n.getnTitle() %>" readonly>
				</span>
				
				<h4 class="notice_title">작성자</h4>
				<span class="input_area">
					<input type="text" name="writer" value="<%= n.getnWriter() %>" readonly>
				</span>
				
				<h4 class="notice_title">작성일</h4>
				<span class="input_area">
					<input type="date" name="date" value="<%= n.getnDate() %>" readonly>
				</span>
				
				<h4 class="notice_title">내용</h4>
				<textarea name="content" style="resize:none;" readonly><%= n.getnContent() %></textarea>
				
				<div class="btnArea">
					<button type="button" id="listBtn">목록으로</button>
					<% if(loginUser != null && loginUser.getUserId().equals("admin")) { %>
					<button type="button" id="updateBtn">수정하기</button>
					<button type="button" id="deleteBtn">삭제하기</button>
					
					<!-- form 태그를 post 방식으로 제출
					nno를 화면에 드러내지 않고 form을 submit 하면서 넘길 수 있음 -->
					<form id="nnoForm" method="post">
						<input type="hidden" name="nno" value="<%= n.getnNo() %>"> 
					</form>
					
					<script>
					// 수정하기 버튼 이벤트
					const updateBtn = document.getElementById('updateBtn');
					updateBtn.addEventListener('click', function(){
						$("#nnoForm").attr("action", "<%= request.getContextPath() %>/notice/updateForm");
						$("#nnoForm").submit();
					});
					// 삭제하기 버튼 이벤트
					const deleteBtn = document.getElementById('deleteBtn');
					deleteBtn.addEventListener('click', function(){
						$("#nnoForm").attr("action", "<%= request.getContextPath() %>/notice/delete");
						$("#nnoForm").submit();
					});
					</script>
					<% } %>
				</div>
		
		</div>
	</div>

	<script>
		// 목록으로 버튼 이벤트
		const listBtn = document.getElementById('listBtn');
		listBtn.addEventListener('click', function(){
			location.href='<%= request.getContextPath() %>/notice/list';
		});
	</script>
















</body>
</html>