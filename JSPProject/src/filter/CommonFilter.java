package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class CommonFilter
 */
// 1. 어노테이션을 통한 필터 매핑
// @WebFilter(filterName = "encoding", urlPatterns="/*")
// fileterName을 통해서 어떤 역할을 하는 필터인지 지정해주고
// urlPatterns 를 통해서 어떤 요청을 처리하기 전에 거칠 것인지를 결정
// --> */로 지정하게 되면 모든 요청을 뜻함

// 2. web.xml 파일에 필터 등록하는 방식
// (필터를 여러개 사용할 대 어떤 필터를 먼저 거칠지는 web.xml 안에 등록 된 순서에 따름,
// 어노테이션 방식의 경우는 지정 불가)
public class CommonFilter implements Filter {
	// 서블릿 필터는 request, response가 서블릿이나 JSP 등 리소스에 도달하기 전 필요한 전/후 처리 작업을 맡는다.
	// 필터는 FilterChain을 통해 여러 개 혹은 연쇄적으로 사용 가능하다.
	

    /**
     * Default constructor. 
     */
    public CommonFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// 컨테이너가 필터 인스턴스를 제거할 때 호출 - 서버 종료
		// System.out.println("필터 인스턴스가 소멸됩니다.");
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 컨테이너가 현재 요청에 필터를 적용하겠다 판단 되었을 때 호출
		
		// 서블릿 수행 전 필터 동작
		// System.out.println("doFilter() 동작합니다.");
		
		// place your code here -> 필터링을 통해 수행하고자 하는 코드 작성
		// 전송 방식이 post일 때 반드시 request에 대해서 인코딩
		HttpServletRequest hrequest = (HttpServletRequest)request;
		
		// 'post' 방식으로 들어온 요청인지 확인
		if (hrequest.getMethod().equalsIgnoreCase("post")) {
			// System.out.println("post 전송 시에만 encoding 됨");
			request.setCharacterEncoding("utf-8");
		}
		// -> 적용 확인을 위해 일반 게시판 글 작성(/board/insert) 서블릿으로 가서
		// 	    인코딩 구문 삭제해보기

		// pass the request along the filter chain
		// FilterChain의 doFilter()는 다음 필터를 호출하거나, 마지막 필터라면 서블릿으로 넘어감
		chain.doFilter(request, response);
		
		// 서빌릿 수행 후 필터 동작
		// System.out.println("doFilter() 이후 처리 되는 동작입니다.");
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// 컨테이너가 필터를 인스턴스화 할 때 호출
		// System.out.println("CommonFilter 초기화 되었습니다.");
	}

}
