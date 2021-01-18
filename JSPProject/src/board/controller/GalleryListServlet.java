package board.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.service.BoardService;

/**
 * Servlet implementation class GalleryListServlet
 */
@WebServlet("/gallery/list")
public class GalleryListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GalleryListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 사진 게시판 정보에는 btype이 2인 게시글 정보와 첨부파일 테이블의 정보가 모두 필요
		BoardService bs = new BoardService();
		
		// 게시글과 사진을 한번에 들고오기 위해 map으로 묶어줌
		Map<String, Object> map = bs.selectGalleryList();
		
		// 게시글
		// System.out.println("bList : " + map.get("bList"));
		// 사진
		// System.out.println("fList : " + map.get("fList"));
		
		if (map.get("bList") != null && map.get("fList") != null) {
			request.setAttribute("bList", map.get("bList"));
			request.setAttribute("fList", map.get("fList"));
			request.getRequestDispatcher("/views/gallery/galleryListView.jsp").forward(request, response);
		} else {
			request.setAttribute("msg", "사진게시판 조회에 실패하였습니다.");
			request.getRequestDispatcher("/views/common/errorPage.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
