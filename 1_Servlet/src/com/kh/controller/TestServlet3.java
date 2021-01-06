package com.kh.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestServlet3
 */
@WebServlet("/test3")
public class TestServlet3 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServlet3() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 한글 값을 위한 인코딩 처리 => post에서는 적용해주어야 함
		request.setCharacterEncoding("utf-8");
		
		// 값 받아오기
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");
		String age = request.getParameter("age");
		String city = request.getParameter("city");
		String height = request.getParameter("height");
		// 여러개의 값이 넘어올 때는 배열로 => getParameterValues();
		String[] foodArr = request.getParameterValues("food");
		
		// servlet 안에서 응답 처리(view)를 하지 않고 jsp로 위임하기
		// request 속성에 변수 지정
		// request.setAttribute(key, value);
		// setAttrubute로 담길 때는 object 타입으로 담김
		request.setAttribute("name", name); // key : "name", value : 위에서 받아온 변수 name 으로 셋팅해서 jsp에 넘기기 위해 request 객체 안에 저장
		request.setAttribute("gender", gender);
		request.setAttribute("age", age);
		request.setAttribute("city", city);
		request.setAttribute("height", height);
		
		// 배열은 문자열로 합쳐 보내기
		String foods = "";
		if (foodArr != null) {
			foods = String.join(", ", foodArr); // ,를 기준으로 foodArr에 있는 문자열 합치기
		} else {
			foods = "없음";
		}
		
		request.setAttribute("foods", foods);
		
		// 위임 객체 > RequestDispatcher를 이용해 이동하고자 하는 view 경로 지정
		// request.getRequestDispatcher(jsp파일경로)
		RequestDispatcher view = request.getRequestDispatcher("views/testServlet3End.jsp"); 
								// request가 RequestDispatcher 호출하여 변수에 담음
		view.forward(request, response); // 요청하는 속성값과, 응답하는 객체를 둘 다 위임하기 위해 view에 담음
		
		// 1. forword => 기존 파라미터를 유지하면서 페이지 전환
		// Web Container 차원에서 페이지 이동만 있고
		// 실제로 웹 브라우저는 다른 페이지로 이동했음을 알 수 없어
		// 웹 브라우저에는 최초에 호출한 URL이 표시되고 이동한 페이지의 경로는 볼 수 없음
		// 즉, 웹컨테이너 내부에서만 페이지가 이동되었지, 클라이언트가 요청한 url은 변동이 없기 때문
	
		// 2. sendRedirect 방식
		// 모든 파라미터 정보를 제외하고 서버에 재호출
		// 새로운 페이지에서 기존 request, response는 사라지고 새로운 request, response가 생성됨
		// response.sendRedirect("views/testServlet3End.jsp");
		// 데이터를 가지고 이동하는게 아닌, 지금 가지고 있는 데이터를 끊고 해당 페이지로 이동하고 싶을 때 사용
		// 서버 재시작후 실행해보면 값들이 모두 null로 되어있고, url에서 views/testServlet3End.jsp로 되어있는것을 볼 수 있음
		// forword는 클라이언트가 요청한 url이 그대로지만, sendRedirect는 데이터와 클라이언트 요청 모두 사라지고 지정한 url로 변경됨
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response); // post방식으로 post메소드를 호출해도
									// post 메소드 안에서 다시 doGet 메소드를 호출하고 있으므로
									// post나 get 모두 doGet메소드 안에서 처리하면 됨
	}

}
