package board.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.service.BoardService;
import board.model.vo.Board;

/**
 * Servlet implementation class BoardDetailServlet
 */
@WebServlet("/board/detail")
public class BoardDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int bId = Integer.parseInt(request.getParameter("bId"));
		
		// 동일 게시글에 대한 조회수 무한 증가 방지 처리 -> cookie 사용
		
		/* HTTP 프로토콜
		 * 요청 -> 연결 -> 응답 -> 연결 해제
		 * 따라서 해제 된 이후 서버에서는 session을 통해 사용자를 구분함 (ex. 로그인 상태 관리)
		 * 
		 * cookie
		 * 클라이언트 측에 저장
		 * 팝업창 오늘 다시 보지 않기, 쇼핑몰의 오늘 본 상품
		 * 
		 * session
		 * 서버에서 생성한 session-id를 클라이언트에서는 쿠키를 사용하여 저장
		 * session-id에 따른 관련 자원은 모두 서버에서 관리
		 * (중요한 정보는 세션에 저장하는 것이 보안상 좋음)
		 * 
		 * 순서
		 * 1) board/detail 호출시마다 해당 bId가 cookies에 존재하는지 확인하여 존재하지 않는다면
		 *    이는 처음 읽는 글이므로 Cookie 객체를 생성하여 bId를 저장하고 추가 -> 조회수 증가 + 게시글 조회
		 * 2) 이 응답에 실린 쿠키를 이제는 클라이언트 측(브라우저)에서 관리함  
		 * 3) 다음 요청 때 이 쿠키를 함께 전송함
		 * 4) board/detail 호출 시 해당 bId가 cookies에 존재하는지 확인하여 존재한다면
		 *    -> 조회수 증가처리 하지 않고 게시글 조회
		 */
		
		// bList라는 이름의 쿠키가 있는지 확인하는 변수
		boolean flagbList = false;
		// 해당 bId가 bList 쿠키 value 안에 있는지 확인하는 변수
		boolean flagbId = false;
		
		// 요청으로부터 cookie 정보들을 얻어옴
		Cookie[] cookies = request.getCookies();
		
		// 쿠키가 잘 넘어왔다면
		if(cookies != null) {
			// 넘어온 쿠키 값 배열을 처음부터 끝까지 반복하며 탐색
			for(Cookie c : cookies) {
				// 읽은 게시물 정보를 저장 해 두는 쿠키의 이름 bList가 있는지 확인
				if(c.getName().equals("bList")) {   // 1,3,10,20
					flagbList = true;
					
					// 기존 쿠키 value를 가져와서 decode함(, 등의 특수문자를 인코딩해서 넘기므로)
					String bList = URLDecoder.decode(c.getValue(), "UTF-8");
					// , 구분자 기준으로 나누기
					String[] list = bList.split(",");
					for(String st : list) {
						// 해당 게시글을 읽었다는 정보가 포함되어 있으므로 읽었음을 표시
						if(st.equals(String.valueOf(bId)))
							flagbId = true;
					}
					// 해당 게시글을 읽지 않았다면 쿠키에 해당 게시글 번호를 붙여 다시 인코딩해서 value 세팅
					if(!flagbId) {
						c.setValue(URLEncoder.encode(bList + "," + bId, "UTF-8"));
						response.addCookie(c); // 응답에 담아 보냄
					}
				}
			}
		}
		
		BoardService bs = new BoardService();
		Board b = null;
		
		// bList라는 이름의 쿠키가 없다면 
		if(!flagbList) {
			Cookie c = new Cookie("bList", String.valueOf(bId));
			c.setMaxAge(1 * 24 * 60 * 60);
			response.addCookie(c);
			// 조회수 증가 후 게시글 조회
			b = bs.selectBoard(bId);
		} else if(!flagbId) {
			// bList는 있지만 bId는 없으므로 조회수 증가 후 게시글 조회
			b = bs.selectBoard(bId);
		} else {
			// 조회수 증가 없이 게시글 조회
			b = bs.selectBoardNoCnt(bId);
		}
		
		if(b != null) {
			request.setAttribute("board", b);
			request.getRequestDispatcher("/views/board/boardDetailView.jsp").forward(request, response);
		} else {
			request.setAttribute("msg", "게시글 상세 조회에 실패하였습니다.");
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
