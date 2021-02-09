//package filter;
//
//import java.io.IOException;
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//
//import wrapper.EncryptWrapper;
//
///**
// * Servlet Filter implementation class EncryptFilter
// */
//// 이 필터는 암호화가 필요한 서블릿에만 적용이 되기 때문에
//// InsertMemberServlet, LoginServlet, UpdatePwdServlet 에 필터 적용이 되어야 함
//// 각 서블릿에 name 속성 값을 맞춰서 명시해줄것
//@WebFilter(filterName = "encrypt", servletNames = {"InsertMemberServlet", 
//												   "LoginServlet",
//												   "UpdatePwdServlet"})
//public class EncryptFilter implements Filter {
//
//    /**
//     * Default constructor. 
//     */
//    public EncryptFilter() {
//        // TODO Auto-generated constructor stub
//    }
//
//	/**
//	 * @see Filter#destroy()
//	 */
//	public void destroy() {
//		// TODO Auto-generated method stub
//	}
//
//	/**
//	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
//	 */
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//		// System.out.println("여기는 암호화 필터입니다.");
//		// Wrapper : 필터에게 넘어온 request, response를 변경할 때 사용
//		// request나 response를 특정한 형태로 감싸서 변형
//		
//		// place your code here
//		EncryptWrapper encWrapper = new EncryptWrapper((HttpServletRequest)request);
//		// 매개변수로 받아 온 ServlsetRequest를 HttpServletRequest로 다운 캐스팅 하여
//		// Wrapper의 생성자로 전달
//		
//		// pass the request along the filter chain
//		// chain.doFilter(request, response);
//		chain.doFilter(encWrapper, response);
//		
//		// 3개의 요청에 대해서 request.getParameter(key)를 수행하면
//		// EncryptWrapper에 오버라이딩 된 getParameter 메소드가 실행 되고
//		// userPwd라는 값은 평문이 아닌 가공 된 문자로 보일 것
//	}
//
//	/**
//	 * @see Filter#init(FilterConfig)
//	 */
//	public void init(FilterConfig fConfig) throws ServletException {
//		// TODO Auto-generated method stub
//	}
//
//}
