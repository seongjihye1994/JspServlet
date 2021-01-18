<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, notice.model.vo.Notice"%>
<%
	ArrayList<Notice> list = (ArrayList<Notice>)request.getAttribute("list");

	String searchCondition = (String)request.getAttribute("searchCondition");
	String search = (String)request.getAttribute("search");
	String[] searchSelected = new String[2];
	
	if(searchCondition != null){
		if(searchCondition.equals("title")){
			searchSelected[0] = "selected";
		} else { // 제목이 아닌 내용을 검색했다면
			searchSelected[1] = "selected";
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
		margin-bottom : 70px;
	}
	
	/* 공지사항 목록 영역 */
	.tableArea{
		min-height : 500px;
		padding : 20px;
	}
	
	/* 공지사항 테이블 */
	#listTable{
		border:5px solid white;
	 	text-align:center;
		margin:auto; 
	}
	
	#listTable th:nth-child(1) {
		width : 150px;
	}
	
	#listTable th:nth-child(2) {
		width : 400px;
	}
	
	#listTable th:nth-child(3) {
		width : 200px;
	}
	
	#listTable th:nth-child(4) {
		width : 150px;
	}
	
	#listTable th:nth-child(5) {
		width : 100px;
	}
	
	/* 검색하기 영역 */
	.searchArea {
		width : 100%;
		text-align:center;
		padding:20px;
	}

</style>
</head>
<body>
	<%@ include file="../common/menubar.jsp" %>
	<div class="outer">
		<div class="tableArea">
			<h1>공지사항</h1>
			<table id="listTable">
				<tr>
					<th>글번호</th>
					<th>글제목</th>
					<th>작성자</th>
					<th>조회수</th>
					<th>작성일</th>
				</tr>
				<!-- 공지사항에 글이 존재하지 않을 수도 있음
				     list는 DAO에서 무조건 객체로 생성 되어 반환 되므로 null이 아님
				     list가 비어 있는지 아닌지로 판단 -->
				<% if(list.isEmpty()){ %>
				<tr>
					<td colspan="5">존재하는 공지사항이 없습니다.</td>
				</tr>
				<% } else { %>
					<% for(Notice n:list){%>
					<tr>
						<td><%= n.getnNo() %></td>
						<td><%= n.getnTitle() %></td>
						<td><%= n.getnWriter() %></td>
						<td><%= n.getnCount() %></td>
						<td><%= n.getnDate() %></td>
					</tr>
					<% } %>
				<% } %>
			</table>
		</div>
		
		<div class="searchArea">
		<form action="<%= request.getContextPath() %>/notice/search" method="get"
		onsubmit="return checkSearchCondition();">
			<select id="searchCondition" name="searchCondition">
				<option value="----">----</option>
				<option value="title" <%= searchSelected[0] %>>제목</option>
				<option value="content" <%= searchSelected[1] %>>내용</option>
			</select>
			<% if(search != null) { %>
			<input type="search" name="search" value="<%= search %>">
			<% } else { %>
			<input type="search" name="search">
			<% } %>
			<button type="submit">검색하기</button>
			<!-- 2. 공지사항 글쓰기 기능 : 관리자만 사용하는 기능 (로그인 유저가 관리자일 때만 보여줌) -->
			<% if(loginUser != null && loginUser.getUserId().equals("admin")) { %>
			<button id="noticeInsert" type="button">작성하기</button>
			<script>
			// 공지사항 작성하기
			const noticeInsert = document.getElementById('noticeInsert');
			noticeInsert.addEventListener('click', function(){
				location.href='<%= request.getContextPath() %>/views/notice/noticeInsertForm.jsp';
			});
			</script>
			<% } %>
		</form>
		
		</div>
	</div>
	<script>
		// 3. 공지사항 상세보기 기능(jquery를 통해 작업)
		$(function(){
			$("#listTable td").mouseenter(function(){
				$(this).parent().css({"background":"lightgray", "cursor":"pointer"});	
			}).mouseout(function(){
				$(this).parent().css("background", "rgb(248, 249, 250)");
			}).click(function(){
				var num = $(this).parent().children().eq(0).text();
				// 쿼리 스트링을 이용하여 get 방식으로 글 번호를 전달
				location.href="<%= request.getContextPath() %>/notice/detail?nno=" + num;
			});
		});	
		
		function checkSearchCondition(){
			if($("#searchCondition option:selected").val() == '----'){
				return false;
			}
			return true;
		}
		
	</script>
	
	
	
	
	
	
	
	
	
	
	
















</body>
</html>