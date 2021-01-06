package com.kh.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestServlet1
 */
@WebServlet("/test1")
public class TestServlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServlet1() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 첫번째 파라미터 HttpServletRequest request : 웹 브라우저에서 사용자가 요청한 내용을 받아주는 용도
		// 두번째 파라미터 HttpServletResponse response :request를 통해 처리한 결과를 웹 브라우저의 사용자에게 보여주기 위한 용도 (응답)
		
		// 사용자가 요청에 보낸 정보들을 추출하기 위한 메소드
		// request.getParameter("name속성값) --> 해당 value 값 리턴 (무조건 String 타입)
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");
		String age = request.getParameter("age");
		String city = request.getParameter("city");
		String height = request.getParameter("height");
		
		// checkbox는 여러개의 값이 넘어오는 경우(다중 선택 가능)는 name 값을 기준으로 배열로 받아야 함
		// request.getParameterValues("name속성값") --> 해당 value 값들 리턴 (String 배열 타입)
		String[] foodArr = request.getParameterValues("food");
		
		// 사용자가 요청을 통해 전달한 값을 변수에 담았으므로 콘솔에 출력해서 확인
		System.out.println("name : " + name);
		System.out.println("gender : " + gender);
		System.out.println("age : " + age);
		System.out.println("city : " + city);
		System.out.println("height : " + height);
		
		// name값 불일치 또는 checkbox 미선택 시 값이 넘어오지 않아 null일 수도 있음
		// NullPointerException 방지 처리
		if (foodArr != null) {
			for (int i = 0; i < foodArr.length; i++) {
				System.out.println("foodArr[" + i + "]" + foodArr[i]);
			}			
		}
		
		// 사용자가 보낸 요청에 대한 처리 (DB insert 등)를 마치고 나면 보여져야 할 결과 화면을 응답해야 함
		// response 객체를 통해 사용자에게 응답 처리
		
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
		out.println("<h2>개인정보 결과(GET) 화면</h2>");
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
