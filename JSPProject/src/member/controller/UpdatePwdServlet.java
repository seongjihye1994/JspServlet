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
 * Servlet implementation class UpdatePwdServlet
 */
@WebServlet("/member/updatePwd")
public class UpdatePwdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdatePwdServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 멤버를 식별할 userId가 필요함 -> 세션에 지정 된 loginUser를 통해서 알아올 수 있음
		HttpSession session = request.getSession(); // 현재 로그인 된 회원의 세션 객체 얻어오기
		Member loginUser = (Member) session.getAttribute("loginUser"); // 세션으로부터 로그인 유저 가져오기
		String userId = loginUser.getUserId(); // 유저 아이디 가져오기
		String userPwd = request.getParameter("userPwd"); // 현재 비밀번호
		String newPwd = request.getParameter("newPwd"); // 변경할 비밀번호
		
		// System.out.println(userId + " " + userPwd + " " + newPwd);
		
		Member updateMember = new MemberService().updatePwd(userId, userPwd, newPwd);
		
		// System.out.println(updateMember);
		
		if (updateMember != null) {
			// 비밀번호 수정이 잘 되었음을 result-success로 표시
			request.setAttribute("result","success");
			// 수정 된 객체 다시 loginUser에 저장
			session.setAttribute("loginUser", updateMember);
		} else {
			request.setAttribute("result", "fail");
		}
		
		RequestDispatcher view = request.getRequestDispatcher("/views/member/pwdUpdateForm.jsp");
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
