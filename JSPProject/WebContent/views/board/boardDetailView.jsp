<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="board.model.vo.*, java.util.ArrayList"%>
<%
	Board b = (Board)request.getAttribute("board");

	// AJAX 학습 후 rList 추가
	ArrayList<Reply> rList = (ArrayList<Reply>)request.getAttribute("rList");
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
	
	#replySelectTable {
		width : 100%;
	}
	
	#replySelectTable td:nth-child(1),
	#replySelectTable td:nth-child(3) {
		width : 100px;
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
						<p id="content"><%= (b.getbContent()).replace("\n", "<br>") %></p>
					</td>
				</tr>
			</table>
		</div>
		
		<div class="btnArea">
			<button id="listBtn">목록으로</button>
		
			<%-- 로그인 유저가 글 작성자인 경우에만 수정 삭제 버튼 보이기 --%>
			<% if(loginUser.getUserNo() == b.getUserNo()) { %>
			<button id="updateBtn">수정</button>
			<button id="deleteBtn">삭제</button>
			
			<!-- 수정/삭제를 위한 form -->
			<form action="" id="bIdForm" method="post">
				<input type="hidden" name="bId" value="<%= b.getbId() %>">
			</form>
			
			<script>
				// 수정하기 버튼 이벤트
				const updateBtn = document.getElementById('updateBtn');
				updateBtn.addEventListener('click', function(){
					$("#bIdForm").attr("action", "<%= request.getContextPath() %>/board/updateForm");
					$("#bIdForm").submit();
				});
				
				// 삭제하기 버튼 이벤트
				const deleteBtn = document.getElementById('deleteBtn');
				deleteBtn.addEventListener('click', function(){
					$("#bIdForm").attr("action", "<%= request.getContextPath() %>/board/delete");
					$("#bIdForm").submit();
				});
			</script>
			<% } %>
		</div>
		
		<!-- AJAX 수업 후 완성할 댓글 -->
		<div class="replyArea">
		
			<div class="replyWriterArea">
				<h4 class="reply_title">댓글작성</h4>
				<textarea id="replyContent"></textarea>
				<br>
				<button id="addReply">댓글등록</button>
			</div>
		
			<div class="replySelectArea">
				<table id="replySelectTable">
				<% if(rList != null && !rList.isEmpty()) { %>
					<% for(Reply r : rList) { %>
					<tr>
						<td><%= r.getUserName() %></td>
						<td><%= r.getrContent() %></td>
						<td><%= r.getCreateDate() %></td>
					</tr>
					<% } %>
				<% } else { %>
				<tr>
					<td colspan="3">작성 된 댓글이 없습니다.</td>
				</tr>
				<% } %>
				</table>
			</div>
		</div>
	</div>
	
	<script>
		// 목록으로 버튼 이벤트
		const listBtn = document.getElementById('listBtn');
		listBtn.addEventListener('click', function(){
			location.href='<%= request.getContextPath() %>/board/list';
		});
		
		$(function(){
			// addReply 버튼 클릭 시 댓글 달기(insert) 기능 수행 후 
			// 비동기적으로 새로 갱신 된 rList를 테이블에 적용시키는 ajax 통신
			$("#addReply").click(function(){
				var writer = <%= loginUser.getUserNo() %>;
				var bId = <%= b.getbId() %>;
				var content = $("#replyContent").val();
				
				$.ajax({
					url : "<%= request.getContextPath() %>/board/insertReply",
					type : "post",
					dataType : "json",
					data : {writer : writer, bId : bId, content : content},
					success : function(data){
						// 갱신 된 rList를 테이블에 다시 적용
						
						
						replyTable = $("#replySelectTable");
						replyTable.html(""); // 기존 테이블 정보 초기화
						
						// 새로 받아온 갱신된 댓글리스트들을 for문을 통해 다시 table에 추가
						for(var key in data){
							console.log(key); // 배열이므로 key 값은 인덱스
							console.log(data[key].createDate);
							var tr = $("<tr>");
							var writerTd = $("<td>").text(data[key].userName);
							var contentTd = $("<td>").text(data[key].rContent);
							var dateTd = $("<td>").text(data[key].createDate);
					
							tr.append(writerTd, contentTd, dateTd);
							
							replyTable.append(tr);
						}
						
						// +) 댓글 작성 부분 리셋
						$("#replyContent").val("");
					},
					error : function(e){
						console.log(e);
					}
				});
				
			});
		});
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	</script>
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
</body>
</html>