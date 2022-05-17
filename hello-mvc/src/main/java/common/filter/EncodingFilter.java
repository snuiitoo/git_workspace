package common.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Servlet Filter implementation class EncodingFilter
 * 
 * 필터 처리 순서
 * web.xml에 등록된 순서대로 처리된다
 * web.xml에 등록된 필터가 @WEBFilter보다 우선 순위가 높다.
 * 정확하게 하고 싶다면 둘 다 등록하면된다.
 * 로그 다음 인코딩 필터가 되고 싶다면 @web으로 등록하는게 아니라
 */
//@WebFilter("/*")
public class EncodingFilter implements Filter {

    /**
     * Default constructor. 
     */
    public EncodingFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 전처리
		request.setCharacterEncoding("utf-8"); // 모든 요청전(서블릿가기전)에 이 코드가 실행이 될 것
//		System.out.println("> 인코딩(utf-8) 처리 완료");
		chain.doFilter(request, response);
		
		// 후처리 - 할 거 없으면 안해도됨
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
