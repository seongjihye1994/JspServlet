<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, board.model.vo.*"%>
<%
   ArrayList<Board> list = (ArrayList<Board>)request.getAttribute("list");
   PageInfo pi = (PageInfo)request.getAttribute("pi");
   
   Search s = (Search)request.getAttribute("search");
   String search = "";
   String searchCondition = "";
   String[] selected = new String[3];
   if(s != null){
      search = s.getSearch();
      searchCondition = s.getSearchCondition();
      if(searchCondition.equals("writer")){
         selected[0] = "selected";
      }else if(searchCondition.equals("title")){
         selected[1] = "selected";
      }else{
         selected[2] = "selected";
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
      min-height:450px;
      padding : 20px;
   }
   
   /* 공지사항 테이블 */
   #listTable{
      border:5px solid white;
       text-align:center;
      margin:auto; 
   }
   
   #listTable th:nth-child(1) {
      width : 100px;
   }
   
   #listTable th:nth-child(2) {
      width : 100px;
   }
   
   #listTable th:nth-child(3) {
      width : 500px;
   }
   
   #listTable th:nth-child(4) {
      width : 100px;
   }
   
   #listTable th:nth-child(5) {
      width : 100px;
   }
   
   #listTable th:nth-child(6) {
      width : 150px;
   }
   
   /* 페이징바 영역 */
   .pagingArea {
      text-align:center;
   }
   .pagingArea button {
      width:25px;
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
         <h1>게시판</h1>
         <table id="listTable">
            <tr>
               <th>글번호</th>
               <th>카테고리</th>
               <th>글제목</th>
               <th>작성자</th>
               <th>조회수</th>
               <th>작성일</th>
            </tr>
            
            <% if(list.isEmpty()) { %>
            <tr>
               <td colspan="6">조회 된 게시글이 없습니다.</td>
            </tr>
            <% } else { %>
               <% for(Board b : list) { %>
               <tr>
                  <td><%= b.getbId() %></td>
                  <td><%= b.getCategory() %></td>
                  <td><%= b.getbTitle() %></td>
                  <td><%= b.getbWriter() %></td>
                  <td><%= b.getbCount() %></td>
                  <td><%= b.getCreateDate() %></td>
               </tr>
               <% } %>
            <% } %>
         </table>
      </div>
      
      <!-- 페이징 바 -->
      <div class="pagingArea">
         <!-- 맨 처음으로 (<<) -->
         <% if(s == null){ %> <!-- 검색결과가 없다면  -->
         <button onclick="location.href='<%= request.getContextPath() %>/board/list?currentPage=1'"> &lt;&lt; </button>
         <% } else { %>
         <button onclick="location.href='<%= request.getContextPath() %>/board/search?currentPage=1&searchCondition=<%= searchCondition %>&search=<%= search %>'"> &lt;&lt; </button>
         <% } %>
         
         <!-- 이전 페이지로 (<) -->
         <% if(pi.getCurrentPage() == 1){ %> <!-- 현재 페이지가 1페이지면 이전 페이지가 없으므로 -->
            <button disabled> &lt; </button>  <!-- 버튼 비활성화 -->
         <%}else if(s == null) { %>
            <button onclick="location.href='<%= request.getContextPath() %>/board/list?currentPage=<%= pi.getCurrentPage() - 1 %>'"> &lt; </button>
         <%} else { %>
            <button onclick="location.href='<%= request.getContextPath() %>/board/search?currentPage=<%= pi.getCurrentPage() - 1 %>&searchCondition=<%= searchCondition %>&search=<%= search %>'"> &lt; </button>
         <% } %>
         
         <!-- 10개의 페이지 목록 -->
         <% for(int p = pi.getStartPage(); p <= pi.getEndPage(); p++){ %>
            <% if(p == pi.getCurrentPage()) { %> <!-- 본인 페이지면 스타일 지정 후 버튼속성 해제 -->
               <button style="background:lightgray;" disabled> <%= p %> </button>
            <%}else if(s == null){ %>
               <button onclick="location.href='<%= request.getContextPath() %>/board/list?currentPage=<%= p %>'"> <%= p %> </button>
            <%} else {%>
               <button onclick="location.href='<%= request.getContextPath() %>/board/search?currentPage=<%= p %>&searchCondition=<%= searchCondition %>&search=<%= search %>'"> <%= p %> </button>
            <% } %>
         <%} %>
         
         <!-- 다음 페이지로(>) -->
         <%if(pi.getCurrentPage() == pi.getMaxPage()){ %>  <!-- 현재 페이지가 마지막페이지면 다음 페이지가 없으므로 -->
            <button disabled> &gt; </button>
         <%} else if(s == null) { %>
            <button onclick="location.href='<%= request.getContextPath() %>/board/list?currentPage=<%= pi.getCurrentPage() + 1 %>'"> &gt; </button>
         <%} else { %>
            <button onclick="location.href='<%= request.getContextPath() %>/board/search?currentPage=<%= pi.getCurrentPage() + 1 %>&searchCondition=<%= searchCondition %>&search=<%= search %>'"> &gt; </button>
         <% } %>
         
         <!-- 맨 끝으로(>>) -->
         <% if(s == null){ %>
         <button onclick="location.href='<%= request.getContextPath() %>/board/list?currentPage=<%= pi.getMaxPage() %>'"> &gt;&gt; </button>
         <% } else { %>
         <button onclick="location.href='<%= request.getContextPath() %>/board/search?currentPage=<%= pi.getMaxPage() %>&searchCondition=<%= searchCondition %>&search=<%= search %>'"> &gt;&gt; </button>
         <% } %>
      </div>
      
      <!-- 검색 영역 -->
      <div class="searchArea">
         <form action="<%= request.getContextPath() %>/board/search" method="get">
            <select id="searchCondition" name="searchCondition">
               <option>----</option>
               <option value="writer" <%= selected[0] %>>작성자</option> <!-- 검색한 컨디션이 selected되어 보여짐 -->
               <option value="title" <%= selected[1] %>>제목</option>
               <option value="content" <%= selected[2] %>>내용</option>
            </select>
            <input type="search" name="search" value="<%= search %>"> <!-- 검색결과가 있다면 검색창에 검색한 키워드가 뜸 -->
            <button type="submit">검색하기</button>
            
            <%-- 로그인 한 사람만 글 작성 되도록 --%>
            <% if (loginUser != null) { %>
           	<button id="insertBtn" type="button" onclick="location.href='<%= request.getContextPath() %>/views/board/boardInsertForm.jsp'">작성하기</button>
            <% } %>
         </form>
      </div>
   </div>
   
   <script>
   		// 게시판 상세보기 기능
   		$(function() {
   			$("#listTable td").mouseenter(function() { // 마우스가 들어갔을 때
   				$(this).parent().css({"background" : "lightgray", "cursor" : "pointer"});
   			}).mouseout(function() { // 마우스가 빠졌을 때
   				$(this).parent().css("background", "rgb(248, 249, 250)");
   			}).click(function () {
   				var bId = $(this).parent().children().eq(0).text(); // eq(0) : 테이블의 첫 칸
   				console.log(bId);
   				// 로그인 한 사람만 게시글 상세 페이지 접근 가능하도록
   				<% if (loginUser != null) {%> // 로그인을 했다면
   					location.href='<%= request.getContextPath() %>/board/detail?bId=' + bId;
   				<% } else {%> // 비로그인이라면
   					alert('로그인 해야만 게시글 보기가 가능합니다.');
   				<% } %>
   			});
   		});
   	
   </script>

</body>
</html>


