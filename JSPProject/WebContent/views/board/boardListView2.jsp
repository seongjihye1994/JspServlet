<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, board.model.vo.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
         <h1>게시판2</h1>
         <table id="listTable">
            <tr>
               <th>글번호</th>
               <th>카테고리</th>
               <th>글제목</th>
               <th>작성자</th>
               <th>조회수</th>
               <th>작성일</th>
            </tr>
            
           <c:choose>
				<c:when test="${ empty list }"> <!-- if -->
		            <tr>
		               <td colspan="6">조회 된 게시글이 없습니다.</td>
		            </tr>
               </c:when>
            	<c:otherwise> <!-- else -->
	              <c:forEach var="b" items="${ list }">
	               <tr>
	                  <td>${ b.bId }</td>
	                  <td>${ b.category }</td>
	                  <td>${ b.bTitle }</td>
	                  <td>${ b.bWriter }</td>
	                  <td>${ b.bCount }</td>
	                  <td>${ b.createDate }</td>
	               </tr>
	               </c:forEach>
	             </c:otherwise>
            </c:choose>
         </table>
      </div>
      
      <!-- 페이징 바 -->
      <div class="pagingArea">
         <!-- 맨 처음으로 (<<) -->
         <c:choose>
	         <c:when test="${ empty search }"> <!-- if --> <!-- 검색결과가 없다면  -->
	         	<button onclick="location.href='${contextPath}/board/list?currentPage=1'"> &lt;&lt; </button>
	         </c:when>
	         <c:otherwise>
	         	<button onclick="location.href='${contextPath}/board/search?currentPage=1&searchCondition=${ searchCondition }&search=${ search }'"> &lt;&lt; </button>
	         </c:otherwise>
         </c:choose>
         <!-- 이전 페이지로 (<) -->
         <c:choose>
	         <c:when test="${ pi.currentPage == 1 }">
	            <button disabled> &lt; </button>  <!-- 버튼 비활성화 -->         
	         </c:when>
	         <c:when test="${ !empty search }">
	            <button onclick="location.href='${contextPath}/board/list?currentPage=${ pi.currentPage - 1 }'"> &lt; </button>         
	         </c:when>
	         <c:otherwise>
	            <button onclick="location.href='${contextPath}/board/search?currentPage=${ pi.currentPage - 1 }&searchCondition=${ searchCondition }&search=${ search }'"> &lt; </button>         
	         </c:otherwise>
         </c:choose>
	         <%-- if(pi.getCurrentPage() == 1){  <!-- 현재 페이지가 1페이지면 이전 페이지가 없으므로 -->
         <%}else if(s == null) { %>
         <%} else { %>
         <% } --%>
         <!-- 10개의 페이지 목록 -->
	         <c:forEach var="p" begin="${ pi.startPage }" end="${ pi.endPage }">
         		<c:choose>
		         	<c:when test="${ pi.currentPage eq pi.startPage }">
		               <button style="background:lightgray;" disabled> ${ p } </button>         	
		         	</c:when>
		         	<c:when test="${ !empty search }">
		               <button onclick="location.href='${contextPath}/board/list?currentPage=${ p }'"> ${ p } </button>         	
		         	</c:when>
		         	<c:otherwise>
		               <button onclick="location.href='${contextPath}/board/search?currentPage=${ p }&searchCondition=${ searchCondition }&search=${ search }'"> ${ p } </button>         	
		         	</c:otherwise>
		         </c:choose>
	         </c:forEach>
         <%-- for(int p = pi.getStartPage(); p <= pi.getEndPage(); p++){
            <% if(p == pi.getCurrentPage()) { %> <!-- 본인 페이지면 스타일 지정 후 버튼속성 해제 -->
            <%}else if(s == null){ %>
            <%} else {%>
            <% } %>
         <%} --%>
         
         
         <!-- 다음 페이지로(>) -->
         <c:choose>
         	<c:when test="${ pi.currentPage eq pi.maxPage }">
	            <button disabled> &gt; </button>     	
         	</c:when>
         	<c:when test="${ !empty search }">
            	<button onclick="location.href='${contextPath}/board/list?currentPage=${ pi.currentPage + 1 }'"> &gt; </button>   	
         	</c:when>
         	<c:otherwise>
            	<button onclick="location.href='${contextPath}/board/search?currentPage=${ pi.currentPage + 1 }&searchCondition=${ searchCondition }&search=${ search }>'"> &gt; </button>
         	</c:otherwise>
         </c:choose>
         <%--if(pi.getCurrentPage() == pi.getMaxPage()){  <!-- 현재 페이지가 마지막페이지면 다음 페이지가 없으므로 -->
         <%} else if(s == null) { %>
         <%} else { %>
         <% } --%>
         
         <!-- 맨 끝으로(>>) -->
         <c:choose>
	         <c:when test="${ !empty search }">
	         	<button onclick="location.href='${contextPath}/board/list?currentPage=${ pi.maxPage }'"> &gt;&gt; </button>
	         </c:when>
	         <c:otherwise>
	         	<button onclick="location.href='${contextPath}/board/search?currentPage=${ pi.maxPage }&searchCondition=${ searchCondition }&search=${ search }'"> &gt;&gt; </button>
	         </c:otherwise>
         </c:choose>
         <%-- if(s == null){
         <% } else { %>
         <% } --%>
      </div>
      
      <!-- 검색 영역 -->
      <div class="searchArea">
         <form action="${contextPath}/board/search" method="get">
            <select id="searchCondition" name="searchCondition">
               <option>----</option>
               <option value="writer" <c:if test="${ searchCondition == 'bWriter' }">selected</c:if>>작성자</option> <!-- 검색한 컨디션이 selected되어 보여짐 -->
               <option value="title" <c:if test="${ searchCondition == 'bTitle' }">selected</c:if>>제목</option>
               <option value="content" <c:if test="${ searchCondition == 'bContent' }">selected</c:if>>내용</option>
            </select>
            <input type="search" name="search" value="${ serch }"> <!-- 검색결과가 있다면 검색창에 검색한 키워드가 뜸 -->
            <button type="submit">검색하기</button>
            
            <%-- 로그인 한 사람만 글 작성 되도록 --%>
            <c:if test="${ !empty loginUser }">
           	<button id="insertBtn" type="button" onclick="location.href='${contextPath}/views/board/boardInsertForm.jsp'">작성하기</button>
            </c:if>
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
   				// 로그인 한 사람만 게시글 상세 페이지 접근 가능하도록{}"
   			 	<% if (loginUser != null) {%> // 로그인을 했다면
   					location.href='${contextPath}/board/detail?bId=' + bId;
   				<% } else {%> // 비로그인이라면
   					alert('로그인 해야만 게시글 보기가 가능합니다.');
   				<% } %>
   			});
   		});
   	
   </script>

</body>
</html>


