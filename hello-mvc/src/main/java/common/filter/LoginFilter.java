package common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.dto.Member;

/**
 * Servlet Filter implementation class LoginFilter
 * 로그인 후에만 사용할 수 있게 해주는 필터
 */
@WebFilter({ "/member/memberView", "/member/memberUpdate", "/member/memberDelete" })
public class LoginFilter implements Filter {

    /**
     * Default constructor. 
     */
    public LoginFilter() {
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
		
		
		// 세션 가져오기 다운캐스팅해서
		HttpServletRequest httpReq = (HttpServletRequest) request;
		HttpServletResponse httpRes = (HttpServletResponse) response;
		// 로그인 여부 검사
		HttpSession session = httpReq.getSession();
		Member loginMember = (Member) session.getAttribute("loginMember"); // 다운캐스팅
		
		if(loginMember == null) {
			session.setAttribute("msg", "로그인 후 사용 가능합니다.");
			httpRes.sendRedirect(httpReq.getContextPath() + "/");
			return; // 조기리턴해서 chain.dofilter가 실행되지 않도록 막는다.
		}
			
			
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
