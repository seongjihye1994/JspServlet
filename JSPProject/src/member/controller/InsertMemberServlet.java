package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class InsertMemberServlet
 */
@WebServlet("/member/insert")
public class InsertMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertMemberServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 한글이 있을 경우 인코딩 처리
		request.setCharacterEncoding("UTF-8");
		
		// 2. request에 담겨 있는 값을 꺼내서 변수에 저장 및 객체 생성
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		String userName = request.getParameter("userName");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		
		String[] addressArr = request.getParameterValues("address");
		String[] interestArr = request.getParameterValues("interest");
		
		// 배열 값은 문자열 합치기를 통해 하나의 문자열로 변경
		String address = "";
		String interest = "";
		
		if(addressArr != null)
			address = String.join(",", addressArr);
		if(interestArr != null)
			interest = String.join(",", interestArr);
		
		Member mem = new Member(userId, userPwd, userName, phone, email, address, interest);
		System.out.println("잘 넘어오는지 확인 : " + mem);
		
		// 3. 비즈니스 로직을 수행할 서비스 메소드 호출하고 결과 값 받기
		int result = new MemberService().insertMember(mem);
		System.out.println("result : " + result);
		
		// 4. 받은 결과에 따라 성공 / 실패 페이지 내보내기
		if(result > 0) {
			// request.setAttribute("msg", "회원 가입이 완료되었습니다.");
			// -> sendRedirect 시 request 객체는 새로 생성 되므로 msg 보존할 수 없음
			request.getSession().setAttribute("msg", "회원 가입이 완료 되었습니다.");
			// -> 세션 객체를 가져와 메세지를 담으면 sendRedirect 이후에도 session 영역에 msg 보존
			// 서버에 재요청하여 Home으로 이동
			response.sendRedirect(request.getContextPath());
		} else {
			request.setAttribute("msg", "회원 가입에 실패하였습니다.");
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
