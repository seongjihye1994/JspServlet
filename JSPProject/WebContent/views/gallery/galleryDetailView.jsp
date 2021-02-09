<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="board.model.vo.*, java.util.ArrayList"%>
<%
	Board b = (Board)request.getAttribute("board");
	ArrayList<Attachment> fileList = (ArrayList<Attachment>)request.getAttribute("fileList");


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
      min-width : 900px;
      background: rgb(248, 249, 250);
      box-shadow: rgba(0, 0, 0, 0.06) 0px 0px 4px 0px;
      margin:auto;
      margin-top : 70px;
      margin-bottom : 70px;
      padding-top : 20px;
      padding-bottom : 20px;
   }
   
   .galleryArea{
      width:800px;
      border:5px solid white;
      margin: auto;
      padding : 20px;
   }
   
   .inline {
      width:190px;
      display:inline-block;
   }
   
   .thumbnailImgArea {
      text-align:center;
   }
   .thumbnailImg {
      width:550px;
      height:300px;
   }
   .detailImgAreaOuter {
      text-align:center;
   }
   .detailImgArea {
      display:inline-block;
      text-align:center;
   }
   .detailImg {
      width:300px;
      height:180px;
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
		<h1>사진 게시글 상세 페이지</h1>
		<div class="galleryArea">
		
			<h4 class="board_title">제목</h4>
			<p class="board_content"><%= b.getbTitle() %></p>
			
			<div class="inline">
				<h4 class="board_title">작성자</h4>
				<p class="board_content"><%= b.getbWriter() %></p>
			</div>
			
			<div class="inline">
				<h4 class="board_title">카테고리</h4>
				<p class="board_content"><%= b.getCategory() %></p>
			</div>
			
			<div class="inline">
				<h4 class="board_title">조회수</h4>
				<p class="board_content"><%= b.getbCount() %></p>
			</div>
			
			<div class="inline">
				<h4 class="board_title">작성일</h4>
				<p class="board_content"><%= b.getCreateDate() %></p>
			</div>
			
			<h4 class="board_title">대표 이미지</h4>
			<div class="thumbnailImgArea">작성일
				<img class="thumbnailImg"
				src="<%= request.getContextPath() %><%= fileList.get(0).getFilePath() %><%=fileList.get(0).getChangeName() %>">
				<br>
				<button onclick="location.href='<%= request.getContextPath() %>/gallery/download?fId=<%= fileList.get(0).getfId() %>'">다운로드</button>
			</div>
				
			<h4 class="board_title">사진 메모</h4>
			<p class="board_content"><%= (b.getbContent()).replace("\n", "<br>") %></p>
			
			<div class="detailImgAreaOuter">
				<% for (int i = 1; i < fileList.size(); i++) { %> <!-- int 1부터 시작하는 이유는 0번 인덱스는 썸네일 이미지이기 떄문 -->
					<div class="detailImgArea">
						<img class="detailImg"
						src="<%= request.getContextPath() %><%= fileList.get(i).getFilePath() %><%= fileList.get(i).getChangeName() %>">
						<br>
						<button onclick="location.href='<%= request.getContextPath() %>/gallery/download?fId=<%= fileList.get(i).getfId() %>'">다운로드</button>
					</div>
				<% } %>
			</div>
		</div>
		<div class="btnArea">
			<button id="listBtn" type="button">목록으로</button>
		</div>
	</div>
	

</body>
</html>