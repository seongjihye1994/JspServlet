package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class LoginServlet
 */
// @WebServlet("/member/login")
@WebServlet(name="LoginServlet", urlPatterns="/member/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. POST 방식으로 전송된 값에 한글 값이 있을 경우 인코딩 처리
		// -> 로그인 시 넘어오는 값에 한글이 없기 때문에 스킵
		
		// 2. 전송 값 꺼내서 변수에 담기
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		
		// Member VO class 작성하기
		Member m = new Member(userId, userPwd);
		// System.out.println("로그인 값 넘기기 : " + m);
		
		// 3. 비지니스 로직을 처리하는 서비스 클래스의 메소드 호출하여 그 결과를 리턴 받음
		Member loginUser = new MemberService().loginMember(m);
		// System.out.println("loginUser 리턴 값 : " + loginUser);
		
		// 4. 로그인이 된 경우와 로그인에 실패한 경우의 응답 컨트롤
		if(loginUser != null) {
			// 로그인 성공 -> loginUser 정보를 보관하고 home으로 이동
			
			/* 1. application : application 단위로 동작하므로 어떤 페이지에서든 접근해서 사용 가능 
			 * 2. session : 브라우저 단위로 동작하므로 브라우저 단위가 유지 되는 동안 사용 가능
			 * 3. request : 해당 요청을 받은 대상만 사용 가능
			 * 4. page : 자기 자신 page에서만 사용 가능
			 * 
			 * 위 4개의 객체 모두 setAttribute(key, value)를 이용해 객체를 저장할수 있으며
			 * 꺼낼 때는 getAttribute(key)를 사용함
			 * 삭제할 때는 removeAttribute(key)를 사용
			 */
			
			// session에 loginUser를 저장해 두면 어떤 페이지에서든 session에 담겨있는 loginUser에 대한
			// 정보를 사용할 수 있음
			
			// 해당 클라이언트에 대한 세션 객체 가져오기
			HttpSession session = request.getSession();
			// session.setMaxInactiveInterval(600);
			// -> 10분 뒤 세션 객체 만료 -> 10분 뒤 자동 로그아웃
			session.setAttribute("loginUser", loginUser);
			
			// Home으로 이동(기존 요청 url을 남기지 않기 위해 서버에 재요청 처리)
			// 1. forward -> 응답 위임 -> 요청 url, request, response 보존
			// 2. sendRedirect -> 서버에 재요청 -> 기존 요청 url, request, response 모두 사라지고
			//                    새로운 request, response 객체 생성
			response.sendRedirect(request.getContextPath());
		} else {
			// 로그인 실패 -> errorPage.jsp로 실패 메시지 담아서 forwarding
			request.setAttribute("msg", "로그인에 실패하였습니다.");
			RequestDispatcher view = request.getRequestDispatcher("/views/common/errorPage.jsp");
			view.forward(request, response);
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
