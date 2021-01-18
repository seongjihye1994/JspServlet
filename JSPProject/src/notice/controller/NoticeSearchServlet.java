package notice.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import notice.model.service.NoticeService;
import notice.model.vo.Notice;

/**
 * Servlet implementation class NoticeSearchServlet
 */
@WebServlet("/notice/search")
public class NoticeSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1. 제목인지 내용인지 구분
		String searchCondition = request.getParameter("searchCondition");
		
		// 2. 검색한 값
		String search = request.getParameter("search");
		
		ArrayList<Notice> list = new NoticeService().selectList(search, searchCondition);
			
		//System.out.println("검색 결과 list : " + list);
		
		// 검색결과 list를 가지고 기존에 가지고 있던 noticeListView.jsp로 forward
		request.setAttribute("list", list);
		// 검색창에 검색한 키워드가 남아지게 하기위해 묶음
		request.setAttribute("searchCondition", searchCondition);
		request.setAttribute("search", search);
		
		request.getRequestDispatcher("/views/notice/noticeListView.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
