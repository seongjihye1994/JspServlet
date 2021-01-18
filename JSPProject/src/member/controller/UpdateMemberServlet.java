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
 * Servlet implementation class UpdateMemberServlet
 */
@WebServlet("/member/update")
public class UpdateMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateMemberServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 한글 값 인코딩
		request.setCharacterEncoding("UTF-8");
		
		// 2. 회원정보 수정에 필요한 값 추출
		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String[] addressArr = request.getParameterValues("address");
		String[] interestArr = request.getParameterValues("interest");
		
		String address = "";
		String interest = "";
		
		if(addressArr != null) {
			address = String.join(",", addressArr);
		}
		
		if(interestArr != null) {
			interest = String.join(",", interestArr);
		}
		
		Member m = new Member(userId, userName, phone, email, address, interest);
		
		// System.out.println("수정하고자 하는 정보 : " + m);
		
		// 3. 비즈니스 로직 수행 (DB update)
		// 개인 정보 수정 후에 session에 저장 된 loginUser 객체의 정보도 수정 되어야 
		// 개인 정보 수정 화면에서 수정 된 loginUser로부터 정보를 받아와서 확인 가능
		Member updateMember = new MemberService().updateMember(m);
		
		// 4. 응답 화면 작성
		if(updateMember != null) {
			// 수정 완료 & 수정 된 값으로 member select
			request.getSession().setAttribute("msg", "회원 정보 수정이 완료되었습니다.");
			// -> 메뉴바의 alert 처리로 확인 가능
			request.getSession().setAttribute("loginUser", updateMember);
			// -> 개인정보 수정사항이 session의 loginUser에 반영 되도록 다시 setAttribute
			// Home으로 redirect
			response.sendRedirect(request.getContextPath());
		}else {
			// 수정 실패시 에러 페이지로 forward
			request.setAttribute("msg", "회원 정보 수정에 실패했습니다.");
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
