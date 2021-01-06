package com.kh.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestServlet2
 */
@WebServlet("/test2")
public class TestServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServlet2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet 호출");
		
		// POST 방식으로 넘겨받은 데이터에 한글 값이 있다면 인코딩 처리가 필요함
		// request에서 파라미터를 가져오기 전에 인코딩 처리 하기
		request.setCharacterEncoding("utf-8");
		
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");
		String age = request.getParameter("age");
		String city = request.getParameter("city");
		String height = request.getParameter("height");
		String[] foodArr = request.getParameterValues("food");
		
		System.out.println("name : " + name);
		System.out.println("gender : " + gender);
		System.out.println("age : " + age);
		System.out.println("city : " + city);
		System.out.println("height : " + height);
		if (foodArr != null) {
			for (int i = 0; i < foodArr.length; i++) {
				System.out.println("foodArr[" + i + "]" + foodArr[i]);
			}
		}
		
		// 응답 화면에 대한 설정 -> 문서 형태 html, 문자셋 utf-8
		response.setContentType("text/html; charset=UTF-8");
		
		// 응답 화면을 사용자에게 출력하기 위한 스트림(클라이언트와의 길) 생성
		PrintWriter out = response.getWriter();
		
		// 자바 코드를 통해 응답 화면 만들기
		out.println("<html>");
		out.println("<head>");
		out.println("<title>개인정보출력화면</title>");
		out.println("<style>");
		out.println("h2{color:red;}");
		out.println("span.name{color:orange; font-weight:bold;}");
		out.println("span.gender{yellow; font-weight:bold;}");
		out.println("span.age{color:green; font-weight:bold;}");
		out.println("span.city{color:blue; font-weight:bold;}");
		out.println("span.height{color:navy; font-weight:bold;}");
		out.println("span.food{color:purple; font-weight:bold;}");
		out.println("</style>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h2>개인정보 결과(POST) 화면</h2>");
		out.printf("<span class='name'>%s</span>님은 ", name);
		out.printf("<span class='age'>%s</span>이시며 ", age);
		out.printf("<span class='city'>%s</span>에 사는 ", city);
		out.printf("<span class='height'>%scm</span>의 ", height);
		out.printf("<span class='gender'>%s</span>입니다.", gender);
		out.println("좋아하는 음식은 <span class='food'>");
		
		if (foodArr != null) {
			for (int i = 0; i < foodArr.length; i++) {
				out.printf("%s ", foodArr[i]);
			}
			out.println("</span>입니다.");
		} else {
			out.println("</span>없습니다.");			
		}
		
		out.println("</body>");
		out.println("</html>");
				
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
