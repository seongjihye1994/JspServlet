<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, board.model.vo.*"%>
<%
	// 조회해 온 사진 게시글
	ArrayList<Board> bList = (ArrayList<Board>) request.getAttribute("bList");
	// 조회해 온 사진 첨부파일
	ArrayList<Attachment> fList = (ArrayList<Attachment>) request.getAttribute("fList");

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
      min-width : 850px;
      background: rgb(248, 249, 250);
      box-shadow: rgba(0, 0, 0, 0.06) 0px 0px 4px 0px;
      margin:auto;
      margin-top : 70px;
      margin-bottom : 70px;
   }
   
   .galleryArea {
      padding:20px;
      width:830px;
      min-height:400px;
      margin:auto;
   }
   
   .gallery_list {
      width:220px;
      border:5px solid white;
      display:inline-block;
      padding:10px;
      margin:10px;
      text-align:center;
   }
   
   .gallery_list:hover {
      opacity:0.8;
      cursor:pointer;
   }

   .gallery_title {
      height : 42px;
      overflow:hidden;
   }
   
   .btnArea {
      text-align:center;
      padding : 50px;
   }
   
</style>

</head>
<body>

	<%@ include file="../common/menubar.jsp" %>
	<div class="outer">
		<div class="galleryArea">
			<h1>사진 게시판</h1>
			<% for (Board b : bList) { %>
			<div class="gallery_list">
			<input type="hidden" value="<%= b.getbId() %>">
				<div>
					<% for (Attachment at : fList) { %>
						<% if(b.getbId() == at.getbId()) { %>
						<img src="<%= request.getContextPath() %><%= at.getFilePath() %><%= at.getChangeName()%>"
						width="200px" height="150px">
						<% } %>
					<% } %>
				</div>
				<h6>No. <%= b.getbId() %></h6>
				<h4 class="gallery_title"><%= b.getbTitle() %></h4>
				<h6>조회수 : <%= b.getbCount() %></h6>
			</div>
			<% } %>
		</div>
		
		<% if (loginUser != null) { %>
		<div class="btnArea">
			<button onclick="location.href='<%= request.getContextPath() %>/views/gallery/galleryInsertForm.jsp'">작성하기</button>
		</div>
		<% } %>
	</div>
	
	<script>
		$(function() {
			$(".gallery_list").click(function() {
				var bId = $(this).children().eq(0).val();
				location.href='<%= request.getContextPath() %>/gallery/detail?bId=' + bId;
			});
		});
	
	</script>

</body>
</html>