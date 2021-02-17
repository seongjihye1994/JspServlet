package board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.service.BoardService;
import board.model.vo.Board;
import board.model.vo.PageInfo;
import board.model.vo.Search;

/**
 * Servlet implementation class BoardSearchServlet
 */
@WebServlet("/board/search")
public class BoardSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 방법 1
		// 넘어온 검색 기준 추출
		//String searchCondition = request.getParameter("searchCondition");
		// 넘어온 검색 키워드 추출
		//String search = request.getParameter("search");
		
		// 방법 2
		// 객체화 시켜서 묶고 넘기기
		Search s = new Search(request.getParameter("searchCondition"), request.getParameter("search"));
		
		// 페이징 처리
		// 기본적으로 게시판은 1페이지부터 시작
		int currentPage = 1;
		
		// 페이지 전환 시 전달 받은 currentPage 값이 있을 경우 적용
		if (request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		BoardService bs = new BoardService();
		// 검색 기준의 검색 결과의 총 갯수 구하기
		int listCount = bs.getSearchListCount(s);
		// 검색기준을 서비스로 넘겨 값 받아오기
		
		// System.out.println("검색 기준 : " + s);
		// System.out.println("검색 기준 listCount : " + listCount);
		
		// 검색 기준으로 페이징 처리
		int pageLimit = 10;
		int boardLimit = 10;
		
		PageInfo pi = new PageInfo(currentPage, listCount, pageLimit, boardLimit);
		
		ArrayList<Board> list = bs.selectSearchList(pi, s);
		
		request.setAttribute("list", list); // 검색으로 걸러진 리스트
		request.setAttribute("pi", pi); // 페이지 인포메이션
		request.setAttribute("search", s); // 검색창에 남아있게 할 키워드
		
		RequestDispatcher view = request.getRequestDispatcher("/views/board/boardListView2.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
