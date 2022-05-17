package member.controller;

import static common.JdbcTemplate.close;
import static common.JdbcTemplate.getConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.HelloMvcUtils;
import member.model.dto.Member;
import member.model.dto.MemberRole;
import member.model.service.MemberDao;
import member.model.service.MemberService;

/**
 * Servlet implementation class MemberEnrollServlet
 */
@WebServlet("/member/memberEnroll")
public class MemberEnrollServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService = new MemberService();

	/**
	 * 회원가입 폼 요청
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request
			.getRequestDispatcher("/WEB-INF/views/member/memberEnroll.jsp")
			.forward(request, response);
	}

	/**
	 * 실제 회원 가입 정보 등록 요청
	 * DML처리해야 되는 것은 doPost (insert)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
		// 1. 인코딩
//		request.setCharacterEncoding("utf-8");
		// 2. 사용자 입력 값 처리
		String memberId = request.getParameter("memberId");
		
		// 단방향 암호화 처리
		String password = HelloMvcUtils.encrypt(request.getParameter("password"), memberId);
		String memberName = request.getParameter("memberName");
		String gender = request.getParameter("gender");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");

		String _birthday = request.getParameter("birthday");
		
		
		Date birthday = null;
		if(_birthday != null && !"".equals(_birthday))
			birthday = Date.valueOf(_birthday);
		
		String[] _hobby  = request.getParameterValues("hobby");
		String hobby = null;
		if(_hobby != null)
			hobby = String.join(", ", _hobby);
		
		Member member = new Member(memberId, password, memberName, MemberRole.U, 
				gender, birthday, email, phone, address, hobby, null);
		
		System.out.println("member@MemberEnrollServlet =" + member);
		// 3. 업무로직 (db에 insert)
		
		// insert into member values()
		int result = memberService.insertMember(member);
		String msg = "회원가입 성공";
		

		// 4. 리다이렉트 (DML처리인 경우 url을 변경해서 새로고침 오류를 방지한다.)
		// 사용자 경고창 처리
		// 회원가입 성공 | 회원가입 실패
		request
			.getSession()
			.setAttribute("msg", msg);
		response.sendRedirect(request.getContextPath() + "/");
		} catch(Exception e) {
			// 1. 로깅 및 관리팀 알림
			e.printStackTrace();
			// 2. 예외 던지기 - tomcat에 통보
			throw e;
		}
	}

}
